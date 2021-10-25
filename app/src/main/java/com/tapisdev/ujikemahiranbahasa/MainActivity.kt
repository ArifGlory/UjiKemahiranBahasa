package com.tapisdev.ujikemahiranbahasa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.ujikemahiranbahasa.activity.LandingListeningActivity
import com.tapisdev.ujikemahiranbahasa.activity.TentangActivity

class MainActivity : BaseActivity() {
    lateinit var startButton : Button
    lateinit var btnTentang : Button
    lateinit var btnKeluar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById(R.id.startButton)
        btnTentang = findViewById(R.id.btnTentang)
        btnKeluar = findViewById(R.id.btnKeluar)

        startButton.setOnClickListener {
            val i = Intent(this,LandingListeningActivity::class.java)
            startActivity(i)
        }
        btnTentang.setOnClickListener {
            val i = Intent(this,TentangActivity::class.java)
            startActivity(i)
        }
        btnKeluar.setOnClickListener {
           finishAffinity()
        }



    }
}