package com.tapisdev.ujikemahiranbahasa.activity

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.airbnb.lottie.LottieAnimationView
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.myapplication.model.SharedVariable
import com.tapisdev.ujikemahiranbahasa.MainActivity
import com.tapisdev.ujikemahiranbahasa.R
import com.tapisdev.ujikemahiranbahasa.model.SeksiMembaca
import com.tapisdev.ujikemahiranbahasa.model.SeksiMeresponsKaidah
import com.tapisdev.ujikemahiranbahasa.utility.DBAdapter
import java.util.ArrayList

class QuizMembacaActivity : BaseActivity() {
    var mDB: DBAdapter? = null
    var listSoal : ArrayList<SeksiMembaca> = ArrayList<SeksiMembaca>()
    var listSoalTemp : ArrayList<SeksiMembaca> = ArrayList<SeksiMembaca>()

    var TAG_MEMBACA = "seksiMembaca"
    var TAG_CHECK_ANSWER = "cekAnswer"
    var TAG_CHECK_BACAAN = "cekBacaan"
    var textInfoBacaan = ""
    var getAnswer = ""
    var currentSoal = 0
    var countNextSoal = 0
    var duration : Long = 600000
    var totalSkor  = 0

    var listener: DialogInterface.OnClickListener? = null
    lateinit var btnJwbA : Button
    lateinit var btnJwbB : Button
    lateinit var btnJwbC : Button
    lateinit var btnJwbD : Button
    lateinit var imgShowBacaan : ImageView
    lateinit var tvSoalMembaca : TextView

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_membaca)
        setView()

        mDB = DBAdapter.getInstance(this)
        listSoalTemp = mDB!!.getSoalMembaca()
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


        disableAllButton()
        startQuiz()
    }

    fun startQuiz(){
        showDialogMembaca()
    }

    fun nextSoal(){
        Log.d(TAG_CHECK_ANSWER," Soal : "+listSoal.get(currentSoal).soal)
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
        //jika sudah mencapai 40 soal
        if (countNextSoal < 40){
            var activeSoal = currentSoal
            if (activeSoal % 8 == 0){
                showDialogMembaca()
            }else{
                setupSoal()
            }

        }else{
            disableAllButton()

            var skorUser = ""+totalSkor
            SharedVariable.activeSkorMembaca = SharedVariable.nilaiJawabBenar * totalSkor
            //showSuccessMessage("Tes Mendengarkan Selesai !, jawaban benar : "+skorUser)
            Log.d(TAG_MEMBACA,"Tes Mendengarkan Selesai !, skor benar : "+SharedVariable.activeSkorMembaca)
            val i = Intent(this,ResultActivity::class.java)
            startActivity(i)
        }
    }

    fun setupSoal(){
        enableAllButton()
        tvSoalMembaca.setText("Soal ke - "+(currentSoal + 1)+" \n"+listSoal.get(currentSoal).soal)

        btnJwbA.setText("A. "+listSoal.get(currentSoal).jawaban_a)
        btnJwbB.setText("B. "+listSoal.get(currentSoal).jawaban_b)
        btnJwbC.setText("C. "+listSoal.get(currentSoal).jawaban_c)
        btnJwbD.setText("D. "+listSoal.get(currentSoal).jawaban_d)
    }

    fun showDialogMembaca(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_reading)

        var btnEndReading = dialog.findViewById(R.id.btnEndReading) as Button
        var tvInfoBacaan = dialog.findViewById(R.id.tvInfoBacaan) as TextView
        var tvBacaan = dialog.findViewById(R.id.tvBacaan) as TextView


        var textBacaan = listSoal.get(currentSoal).bacaan
        tvBacaan.setText(textBacaan)

        setupInfoAudio()
        tvInfoBacaan.setText(textInfoBacaan)

        btnEndReading.setOnClickListener {
            dialog.dismiss()
            setupSoal()
        }

        dialog.show()
    }

    fun setupInfoAudio(){
        var activeSoal = currentSoal + 1

        if ((1..8).contains(activeSoal)){
            textInfoBacaan = "Teks berikut ini adalah untuk Soal 1 - 8"
        }else if ((9..16).contains(activeSoal)){
            textInfoBacaan = "Teks berikut ini adalah untuk Soal 9 - 16"
        }else if ((17..24).contains(activeSoal)){
            textInfoBacaan = "Teks berikut ini adalah untuk Soal 17 - 24"
        }else if ((25..32).contains(activeSoal)){
            textInfoBacaan = "Teks berikut ini adalah untuk Soal 25 - 32"
        }else if ((33..40).contains(activeSoal)){
            textInfoBacaan = "Teks berikut ini adalah untuk Soal 33 - 40"
        }
    }


    fun setView(){
        tvSoalMembaca = findViewById(R.id.tvSoalMembaca)
        imgShowBacaan = findViewById(R.id.imgShowBacaan)
        btnJwbA = findViewById(R.id.btnJwbA)
        btnJwbB = findViewById(R.id.btnJwbB)
        btnJwbC = findViewById(R.id.btnJwbC)
        btnJwbD = findViewById(R.id.btnJwbD)
    }

    fun disableAllButton(){
        imgShowBacaan.isEnabled = false
        btnJwbA.isEnabled = false
        btnJwbB.isEnabled = false
        btnJwbC.isEnabled = false
        btnJwbD.isEnabled = false
    }

    fun enableAllButton(){
        imgShowBacaan.isEnabled = true
        btnJwbA.isEnabled = true
        btnJwbB.isEnabled = true
        btnJwbC.isEnabled = true
        btnJwbD.isEnabled = true
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
            Log.d(TAG_MEMBACA,"index ke $i : "+quiz)
        }
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