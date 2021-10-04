package com.tapisdev.ujikemahiranbahasa.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.ujikemahiranbahasa.R

class LandingListeningActivity : BaseActivity() {

    lateinit var startTesListening : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_listening)

        startTesListening = findViewById(R.id.startTesListening)

        startTesListening.setOnClickListener {

        }
    }
}