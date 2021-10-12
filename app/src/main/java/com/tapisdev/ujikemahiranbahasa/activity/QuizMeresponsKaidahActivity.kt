package com.tapisdev.ujikemahiranbahasa.activity

import android.animation.ObjectAnimator
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.myapplication.model.SharedVariable
import com.tapisdev.ujikemahiranbahasa.MainActivity
import com.tapisdev.ujikemahiranbahasa.R
import com.tapisdev.ujikemahiranbahasa.model.SeksiMeresponsKaidah
import com.tapisdev.ujikemahiranbahasa.utility.DBAdapter
import java.util.ArrayList

class QuizMeresponsKaidahActivity : BaseActivity() {

    var mDB: DBAdapter? = null
    var listSoal : ArrayList<SeksiMeresponsKaidah> = ArrayList<SeksiMeresponsKaidah>()
    var listSoalTemp : ArrayList<SeksiMeresponsKaidah> = ArrayList<SeksiMeresponsKaidah>()

    var TAG_KAIDAH = "seksiKaidah"
    var TAG_CHECK_ANSWER = "cekAnswer"
    var getAnswer = ""
    var currentSoal = 0
    var countNextSoal = 0
    var duration : Long = 600000
    var totalSkor  = 0

    var listener: DialogInterface.OnClickListener? = null
    lateinit var progress_bar : ProgressBar
   // lateinit var mAnimation : ObjectAnimator
    lateinit var btnJwbA : Button
    lateinit var btnJwbB : Button
    lateinit var btnJwbC : Button
    lateinit var btnJwbD : Button
    lateinit var tvMonolog : TextView
    lateinit var tvDialog1 : TextView
    lateinit var tvDialog2 : TextView


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_merespons_kaidah_dialog)

        mDB = DBAdapter.getInstance(this)
        listSoalTemp = mDB!!.getSoalMeresponsKaidah()
        filterSoal(SharedVariable.activePaket)




        btnJwbA.setOnClickListener {
            getAnswer = "A"
            nextSoal()
        }
        btnJwbB.setOnClickListener {
            getAnswer = "B"
            nextSoal()
        }
        btnJwbC.setOnClickListener {
            getAnswer = "C"
            nextSoal()
        }
        btnJwbD.setOnClickListener {
            getAnswer = "D"
            nextSoal()
        }

        startQuiz()

    }

    fun startQuiz(){
        setupSoal()
    }

    fun setupSoal(){
        var quiz = listSoal.get(currentSoal)
        if (quiz.tipe_soal.equals("dialog")){
            setViewDialog()

            tvDialog1.setText(listSoal.get(currentSoal).dialog_1)
            tvDialog2.setText(listSoal.get(currentSoal).dialog_2)
            btnJwbA.setText(listSoal.get(currentSoal).jawaban_a)
            btnJwbB.setText(listSoal.get(currentSoal).jawaban_b)
            btnJwbC.setText(listSoal.get(currentSoal).jawaban_c)
            btnJwbD.setText(listSoal.get(currentSoal).jawaban_d)
        }else{
            setViewMonolog()

            tvMonolog.setText(listSoal.get(currentSoal).monolog)
            btnJwbA.setText(listSoal.get(currentSoal).jawaban_a)
            btnJwbB.setText(listSoal.get(currentSoal).jawaban_b)
            btnJwbC.setText(listSoal.get(currentSoal).jawaban_c)
            btnJwbD.setText(listSoal.get(currentSoal).jawaban_d)
        }
    }

    fun nextSoal(){
        if (listSoal.get(currentSoal).tipe_soal.equals("dialog")){
            Log.d(TAG_CHECK_ANSWER," dialog 1 : "+listSoal.get(currentSoal).dialog_1)
            Log.d(TAG_CHECK_ANSWER," dialog 2 : "+listSoal.get(currentSoal).dialog_2)
        }else{
            Log.d(TAG_CHECK_ANSWER," monolog : "+listSoal.get(currentSoal).monolog)
        }

        Log.d(TAG_CHECK_ANSWER," getAnswer : "+getAnswer)
        Log.d(TAG_CHECK_ANSWER," Jawaban benar : "+listSoal.get(currentSoal).jawaban_benar)

        if (getAnswer.equals(listSoal.get(currentSoal).jawaban_benar)){
            totalSkor  = totalSkor + 1
        }
        countNextSoal++
        currentSoal++

        checkNextOrFinish()
    }

    fun checkNextOrFinish(){

    }

    fun setViewMonolog(){
        btnJwbA = findViewById(R.id.btnJwbA)
        btnJwbB = findViewById(R.id.btnJwbB)
        btnJwbC = findViewById(R.id.btnJwbC)
        btnJwbD = findViewById(R.id.btnJwbD)
        tvMonolog = findViewById(R.id.tvMonolog)

    }

    fun setViewDialog(){
        btnJwbA = findViewById(R.id.btnJwbA)
        btnJwbB = findViewById(R.id.btnJwbB)
        btnJwbC = findViewById(R.id.btnJwbC)
        btnJwbD = findViewById(R.id.btnJwbD)
        tvDialog1 = findViewById(R.id.tvDialog1)
        tvDialog2 = findViewById(R.id.tvDialog2)
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
            Log.d(TAG_KAIDAH,"index ke $i : "+quiz)
        }
    }

    override fun onBackPressed() {
        val builder =
            AlertDialog.Builder(this)
        builder.setMessage("Apakah anda ingin keluar dari Tes yang sedang berlangsung ?")
        builder.setCancelable(false)

        listener = DialogInterface.OnClickListener { dialog, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
               // mAnimation.end()
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