package com.tapisdev.ujikemahiranbahasa.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.ujikemahiranbahasa.MainActivity
import com.tapisdev.ujikemahiranbahasa.R

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val i = Intent(this,MainActivity::class.java)

        object : CountDownTimer(2500, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                startActivity(i)
            }
        }.start()
    }
}