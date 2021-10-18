package com.tapisdev.ujikemahiranbahasa.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.myapplication.model.SharedVariable
import com.tapisdev.ujikemahiranbahasa.MainActivity
import com.tapisdev.ujikemahiranbahasa.R

class ResultActivity : BaseActivity() {

    lateinit var tvPredikat : TextView
    lateinit var tvSkor : TextView
    lateinit var btnKeHome : Button
    var predikat = ""
    var listener: DialogInterface.OnClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        btnKeHome = findViewById(R.id.btnKeHome)
        tvPredikat = findViewById(R.id.tvPredikat)
        tvSkor = findViewById(R.id.tvSkor)

        btnKeHome.setOnClickListener {
            SharedVariable.resetScore()
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }

        countScore()
    }

    fun countScore(){
        var totalScore = SharedVariable.activeSkorMendengarkan + SharedVariable.activeSkorKaidah + SharedVariable.activeSkorMembaca
        tvSkor.setText(""+totalScore.toInt())

        checkPredikat(totalScore)
    }

    fun checkPredikat(score : Double){
        var skor = score.toInt()

        if (skor in 725..800){
            predikat = "Istimewa"
        }else if(skor in 641..724){
            predikat = "Sangat Unggul"
        }else if(skor in 578..640){
            predikat = "Unggul"
        }else if(skor in 482..577){
            predikat = "Madya"
        }else if(skor in 405..481){
            predikat = "Semenjana"
        }else if(skor in 326..404){
            predikat = "Marginal"
        }else if(skor in 251..325){
            predikat = "Terbatas"
        }else if (skor < 251){
            predikat = "Terbatas"
        }

        tvPredikat.setText("Predikat Anda : "+predikat)
    }

    override fun onBackPressed() {
        val builder =
            AlertDialog.Builder(this)
        builder.setMessage("Apakah anda ingin keluar dari Tes yang sedang berlangsung ?")
        builder.setCancelable(false)

        listener = DialogInterface.OnClickListener { dialog, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                SharedVariable.resetScore()
                startActivity(Intent(this, MainActivity::class.java))
            }
            if (which == DialogInterface.BUTTON_NEGATIVE) {
                dialog.cancel()
            }
        }
        builder.setPositiveButton("Yes", listener)
        builder.setNegativeButton("No", listener)
        builder.show()
    }
}