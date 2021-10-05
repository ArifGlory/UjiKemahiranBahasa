package com.tapisdev.ujikemahiranbahasa.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.ujikemahiranbahasa.R
import com.tapisdev.ujikemahiranbahasa.model.SeksiMendengarkan
import java.io.Serializable
import java.util.*

class LandingListeningActivity : BaseActivity() {

    lateinit var startTesListening : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_listening)

        //coba kirim OBJ pake parcelize
        startTesListening = findViewById(R.id.startTesListening)
        var sM = SeksiMendengarkan(1,"coba dialog","soal 1")


        startTesListening.setOnClickListener {
            //random paket nya dulu
            val randomPaket = (1..2).random()
            Log.d("randomPaket"," : "+randomPaket)

            val i = Intent(this,QuizMendengarkanActivity::class.java)
           // i.putExtra("sm",sM as Parcelable)
            i.putExtra("randomPaket",randomPaket)
            startActivity(i)


        }
    }
}