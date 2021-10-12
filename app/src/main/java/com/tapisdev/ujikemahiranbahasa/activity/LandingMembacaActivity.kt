package com.tapisdev.ujikemahiranbahasa.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.ujikemahiranbahasa.R

class LandingMembacaActivity : BaseActivity() {

    lateinit var startTesMembaca : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_membaca)

        startTesMembaca = findViewById(R.id.startTesMembaca)

        startTesMembaca.setOnClickListener {

        }
    }
}