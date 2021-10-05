package com.tapisdev.ujikemahiranbahasa.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.ujikemahiranbahasa.R
import com.tapisdev.ujikemahiranbahasa.model.SeksiMendengarkan
import es.dmoral.toasty.Toasty

class QuizMendengarkanActivity : BaseActivity() {

    lateinit var i : Intent
    lateinit var seksiMendengarkan  : SeksiMendengarkan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_mendengarkan)

        i = intent
        seksiMendengarkan = i.getParcelableExtra<SeksiMendengarkan>("sm")!!

        Toasty.success(this,"nama soal : "+seksiMendengarkan.soal,Toasty.LENGTH_SHORT).show()

    }
}