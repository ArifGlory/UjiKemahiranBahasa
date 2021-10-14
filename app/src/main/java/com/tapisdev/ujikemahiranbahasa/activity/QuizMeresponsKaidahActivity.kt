package com.tapisdev.ujikemahiranbahasa.activity

import android.animation.ObjectAnimator
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.myapplication.model.SharedVariable
import com.tapisdev.ujikemahiranbahasa.R
import com.tapisdev.ujikemahiranbahasa.model.SeksiMendengarkan
import com.tapisdev.ujikemahiranbahasa.model.SeksiMeresponsKaidah
import com.tapisdev.ujikemahiranbahasa.utility.DBAdapter
import java.util.ArrayList

class QuizMeresponsKaidahActivity : BaseActivity() {

    var mDB: DBAdapter? = null
    var listSoal : ArrayList<SeksiMeresponsKaidah> = ArrayList<SeksiMeresponsKaidah>()
    var listSoalTemp : ArrayList<SeksiMeresponsKaidah> = ArrayList<SeksiMeresponsKaidah>()
    var idPaket : Int = 1

    var TAG_KAIDAH = "seksiKaidah"
    var TAG_CHECK_ANSWER = "cekAnswer"
    var TAG_CHECK_AUDIO = "cekAudio"
    var getAnswer = ""
    var currentSoal = 0
    var countNextSoal = 0
    var duration : Long = 600000
    var totalSkor  = 0

    var listener: DialogInterface.OnClickListener? = null
    lateinit var mAnimation : ObjectAnimator
    lateinit var progress_bar : ProgressBar
    lateinit var btnJwbA : Button
    lateinit var btnJwbB : Button
    lateinit var btnJwbC : Button
    lateinit var btnJwbD : Button

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_merespons_kaidah_dialog)


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
}