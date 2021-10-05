package com.tapisdev.ujikemahiranbahasa.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.annotation.RequiresApi
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.ujikemahiranbahasa.R
import com.tapisdev.ujikemahiranbahasa.model.SeksiMendengarkan
import com.tapisdev.ujikemahiranbahasa.utility.DBAdapter
import es.dmoral.toasty.Toasty
import java.util.ArrayList

class QuizMendengarkanActivity : BaseActivity() {

    lateinit var i : Intent
    lateinit var seksiMendengarkan  : SeksiMendengarkan
    var mDB: DBAdapter? = null
    var listSoal : ArrayList<SeksiMendengarkan> = ArrayList<SeksiMendengarkan>()
    var listSoalTemp : ArrayList<SeksiMendengarkan> = ArrayList<SeksiMendengarkan>()
     var idPaket : Int = 1

    var TAG_MENDENGAR = "seksiMendengarkan"

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_mendengarkan)
        i = intent
        //seksiMendengarkan = i.getParcelableExtra<SeksiMendengarkan>("sm")!!
        idPaket =  i.getIntExtra("randomPaket",1)
        Toasty.success(this,"paket yang aktif : "+idPaket,Toasty.LENGTH_SHORT).show()

        mDB = DBAdapter.getInstance(this)
        listSoalTemp = mDB!!.getSoalMendengarkan()
        filterSoal(idPaket)


    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun filterSoal(paketAktif : Int){
        for (i in 0 until  listSoalTemp.size){
            var quiz = listSoalTemp.get(i)
           // Log.d(TAG_MENDENGAR,"index ke $i : "+quiz)
            if (quiz.id_paket == paketAktif){
                listSoal.add(quiz)
            }
        }

        for (i in 0 until listSoal.size){
            var quiz = listSoal.get(i)
             Log.d(TAG_MENDENGAR,"index ke $i : "+quiz)
        }

    }


}