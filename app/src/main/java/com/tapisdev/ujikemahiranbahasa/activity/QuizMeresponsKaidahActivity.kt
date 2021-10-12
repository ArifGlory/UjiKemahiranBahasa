package com.tapisdev.ujikemahiranbahasa.activity

import android.animation.ObjectAnimator
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.myapplication.model.SharedVariable
import com.tapisdev.ujikemahiranbahasa.MainActivity
import com.tapisdev.ujikemahiranbahasa.R
import com.tapisdev.ujikemahiranbahasa.model.SeksiMendengarkan
import com.tapisdev.ujikemahiranbahasa.model.SeksiMeresponsKaidah
import com.tapisdev.ujikemahiranbahasa.utility.DBAdapter
import java.util.ArrayList

class QuizMeresponsKaidahActivity : BaseActivity() {

    var mDB: DBAdapter? = null
    var listSoal : ArrayList<SeksiMeresponsKaidah> = ArrayList<SeksiMeresponsKaidah>()
    var listSoalTemp : ArrayList<SeksiMeresponsKaidah> = ArrayList<SeksiMeresponsKaidah>()

    var TAG_KAIDAH = "seksiKaidah"
    var TAG_CHECK_ANSWER = "cekAnswer"
    var getAnswer = ""
    var currentSoal = 0
    var countNextSoal = 0
    var duration : Long = 600000
    var totalSkor  = 0

    var listener: DialogInterface.OnClickListener? = null
    lateinit var progress_bar : ProgressBar
    lateinit var mAnimation : ObjectAnimator

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_merespons_kaidah)

        mDB = DBAdapter.getInstance(this)
        listSoalTemp = mDB!!.getSoalMeresponsKaidah()
        filterSoal(SharedVariable.activePaket)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun filterSoal(paketAktif : Int){
        for (i in 0 until  listSoalTemp.size){
            var quiz = listSoalTemp.get(i)
            // Log.d(TAG_MENDENGAR,"index ke $i : "+quiz)
            if (quiz.id_paket == paketAktif){
                listSoal.add(quiz)
            }
        }

        for (i in 0 until listSoal.size){
            var quiz = listSoal.get(i)
            Log.d(TAG_KAIDAH,"index ke $i : "+quiz)
        }
    }

    override fun onBackPressed() {
        val builder =
            AlertDialog.Builder(this)
        builder.setMessage("Apakah anda ingin keluar dari Tes yang sedang berlangsung ?")
        builder.setCancelable(false)

        listener = DialogInterface.OnClickListener { dialog, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                mAnimation.end()
                startActivity(Intent(this, MainActivity::class.java))
            }
            if (which == DialogInterface.BUTTON_NEGATIVE) {
                dialog.cancel()
            }
        }
        builder.setPositiveButton("Yes", listener)
        builder.setNegativeButton("No", listener)
        builder.show()
    }
}