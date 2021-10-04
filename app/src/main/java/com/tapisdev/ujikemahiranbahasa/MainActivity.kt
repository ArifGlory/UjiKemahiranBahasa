package com.tapisdev.ujikemahiranbahasa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.ujikemahiranbahasa.activity.LandingListeningActivity

class MainActivity : BaseActivity() {
    lateinit var startButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById(R.id.startButton)

        startButton.setOnClickListener {
            val i = Intent(this,LandingListeningActivity::class.java)
            startActivity(i)
        }


    }
}