package com.tapisdev.ujikemahiranbahasa.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.ujikemahiranbahasa.R

class TentangActivity : BaseActivity() {

    lateinit var tvTentang1 : TextView
    lateinit var tvTentang2 : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tentang)

        tvTentang1 = findViewById(R.id.tvTentang1)
        tvTentang2 = findViewById(R.id.tvTentang2)


    }
}