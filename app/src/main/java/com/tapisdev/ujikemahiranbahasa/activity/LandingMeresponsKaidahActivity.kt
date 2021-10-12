package com.tapisdev.ujikemahiranbahasa.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.ujikemahiranbahasa.R

class LandingMeresponsKaidahActivity : BaseActivity() {

    lateinit var startTesKaidah : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_merespons_kaidah)

        startTesKaidah = findViewById(R.id.startTesKaidah)

        startTesKaidah.setOnClickListener {
            val i = Intent(this,QuizMeresponsKaidahActivity::class.java)
            startActivity(i)
        }
    }
}