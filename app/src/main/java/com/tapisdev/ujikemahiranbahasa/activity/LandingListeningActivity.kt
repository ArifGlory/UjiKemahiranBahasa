package com.tapisdev.ujikemahiranbahasa.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.myapplication.model.SharedVariable
import com.tapisdev.ujikemahiranbahasa.MainActivity
import com.tapisdev.ujikemahiranbahasa.R
import com.tapisdev.ujikemahiranbahasa.model.SeksiMendengarkan
import java.io.Serializable
import java.util.*

class LandingListeningActivity : BaseActivity() {

    lateinit var startTesListening : Button
    var listener: DialogInterface.OnClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_listening)

        //coba kirim OBJ pake parcelize
        startTesListening = findViewById(R.id.startTesListening)
        var sM = SeksiMendengarkan(1,"coba dialog","soal 1")


        startTesListening.setOnClickListener {
            //random paket nya dulu
            var randomPaket = (1..2).random()
            Log.d("randomPaket"," : "+randomPaket)
            SharedVariable.activePaket = randomPaket

            val i = Intent(this,QuizMembacaActivity::class.java)
           // i.putExtra("sm",sM as Parcelable)
            i.putExtra("randomPaket",randomPaket)
            startActivity(i)


        }
    }

    override fun onBackPressed() {
        val builder =
            AlertDialog.Builder(this)
        builder.setMessage("Apakah anda ingin keluar dari Tes yang sedang berlangsung ?")
        builder.setCancelable(false)

        listener = DialogInterface.OnClickListener { dialog, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
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