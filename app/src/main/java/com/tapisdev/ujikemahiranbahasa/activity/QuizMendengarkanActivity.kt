package com.tapisdev.ujikemahiranbahasa.activity

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Window
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.ujikemahiranbahasa.R
import com.tapisdev.ujikemahiranbahasa.model.SeksiMendengarkan
import com.tapisdev.ujikemahiranbahasa.utility.DBAdapter
import es.dmoral.toasty.Toasty
import java.util.ArrayList
import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.airbnb.lottie.LottieAnimationView
import com.tapisdev.ujikemahiranbahasa.MainActivity


class QuizMendengarkanActivity : BaseActivity() {

    lateinit var i : Intent
    lateinit var seksiMendengarkan  : SeksiMendengarkan
    var mDB: DBAdapter? = null
    var listSoal : ArrayList<SeksiMendengarkan> = ArrayList<SeksiMendengarkan>()
    var listSoalTemp : ArrayList<SeksiMendengarkan> = ArrayList<SeksiMendengarkan>()
    var idPaket : Int = 1

    var TAG_LIST_QUIZ = "listQuiz"
    var TAG_MENDENGAR = "seksiMendengarkan"
    var TAG_CHECK_ANSWER = "cekAnswer"
    var TAG_CHECK_AUDIO = "cekAudio"
    var textInfoAudio = ""
    var getAnswer = ""
    var currentSoal = 0
    var countNextSoal = 0
    var duration : Long = 600000
    var totalSkor  = 0
    var isUseNextBtn = false

    var listener: DialogInterface.OnClickListener? = null
    var mPlayer : MediaPlayer = MediaPlayer()
    lateinit var mAnimation : ObjectAnimator

    lateinit var tvInfoSoal : TextView
    lateinit var imgPlay : ImageView
    lateinit var btnJwbA : Button
    lateinit var btnJwbB : Button
    lateinit var btnJwbC : Button
    lateinit var btnJwbD : Button
    lateinit var progress_bar : ProgressBar


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_mendengarkan)
        i = intent
        //seksiMendengarkan = i.getParcelableExtra<SeksiMendengarkan>("sm")!!
        idPaket =  i.getIntExtra("randomPaket",1)
        Toasty.success(this,"paket yang aktif : "+idPaket,Toasty.LENGTH_SHORT).show()

        setView()


        mDB = DBAdapter.getInstance(this)
        listSoalTemp = mDB!!.getSoalMendengarkan()
        filterSoal(idPaket)

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
        setupProgressBarAnimation()
        showDialogAudio()
    }

    fun setView(){
        tvInfoSoal = findViewById(R.id.tvInfoSoal)
        imgPlay = findViewById(R.id.imgPlay)
        btnJwbA = findViewById(R.id.btnJwbA)
        btnJwbB = findViewById(R.id.btnJwbB)
        btnJwbC = findViewById(R.id.btnJwbC)
        btnJwbD = findViewById(R.id.btnJwbD)
        progress_bar = findViewById(R.id.progress_bar)
    }

    fun showDialogAudio(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_playing_audio)

        var btnEndAudio = dialog.findViewById(R.id.btnEndAudio) as Button
        var tvInfoAudio = dialog.findViewById(R.id.tvInfoAudio) as TextView
        var animation_view_audio = dialog.findViewById(R.id.animation_view_audio) as LottieAnimationView


        setupAudio()
        setupInfoAudio()


        animation_view_audio.visibility = View.VISIBLE
        animation_view_audio.setAnimation(R.raw.listening)
        animation_view_audio.playAnimation()
        animation_view_audio.loop(true)

        tvInfoAudio.setText(textInfoAudio)
        btnEndAudio.setOnClickListener {
            mPlayer.stop()
            mPlayer.reset()
            dialog.dismiss()

            setupSoal()
        }

        dialog.show()
    }

    fun setupInfoAudio(){
        var activeSoal = currentSoal + 1

        if ((1..5).contains(activeSoal)){
            textInfoAudio = "Audio berikut ini adalah untuk Soal 1 - 5"
        }else if ((6..10).contains(activeSoal)){
            textInfoAudio = "Audio berikut ini adalah untuk Soal 6 - 10"
        }else if ((11..15).contains(activeSoal)){
            textInfoAudio = "Audio berikut ini adalah untuk Soal 11 - 15"
        }else if ((16..20).contains(activeSoal)){
            textInfoAudio = "Audio berikut ini adalah untuk Soal 16 - 20"
        }else if ((21..25).contains(activeSoal)){
            textInfoAudio = "Audio berikut ini adalah untuk Soal 21 - 25"
        }else if ((26..30).contains(activeSoal)){
            textInfoAudio = "Audio berikut ini adalah untuk Soal 26 - 30"
        }else if ((31..35).contains(activeSoal)){
            textInfoAudio = "Audio berikut ini adalah untuk Soal 31 - 35"
        }else if ((36..40).contains(activeSoal)){
            textInfoAudio = "Audio berikut ini adalah untuk Soal 36 - 40"
        }
    }

    fun setupProgressBarAnimation(){
        mAnimation = ObjectAnimator.ofInt(progress_bar, "progress", 0, 100)
        mAnimation.setDuration(duration)
        mAnimation.interpolator = DecelerateInterpolator()
        mAnimation.start()
    }

    fun setupSoal(){
        enableAllButton()
        tvInfoSoal.setText("Soal ke - "+(currentSoal + 1)+" \n"+listSoal.get(currentSoal).soal)

        btnJwbA.setText(listSoal.get(currentSoal).jawaban_a)
        btnJwbB.setText(listSoal.get(currentSoal).jawaban_b)
        btnJwbC.setText(listSoal.get(currentSoal).jawaban_c)
        btnJwbD.setText(listSoal.get(currentSoal).jawaban_d)
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
            if (activeSoal % 5 == 0){
                showDialogAudio()
            }else{
                setupSoal()
            }

        }else{
            mAnimation.end()
            disableAllButton()

            var skorUser = ""+totalSkor
            showSuccessMessage("Tes Mendengarkan Selesai !, jawaban benar : "+skorUser)
            Log.d(TAG_MENDENGAR,"Tes Mendengarkan Selesai !, jawaban benar : "+skorUser)
            /*val i = Intent(this,ResultActivity::class.java)
            i.putExtra("skor",skorUser)
            startActivity(i)*/
        }
    }

    fun setupAudio(){
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.stop()
                mPlayer.reset()
            }
        }

        Log.d(TAG_CHECK_AUDIO,"audio nya : "+listSoal.get(currentSoal).dialog)

        var assetMng : AssetManager = resources.assets
        var afd2 = assetMng.openFd(listSoal.get(currentSoal).dialog)

      //  var afd = assets.openFd(listSoal.get(currentSoal).dialog)
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mPlayer.setDataSource(afd2.getFileDescriptor(), afd2.getStartOffset(), afd2.getLength())
        mPlayer.prepare()
        mPlayer.start()

        mPlayer.setOnCompletionListener(MediaPlayer.OnCompletionListener {
            mPlayer.stop()
            mPlayer.reset()
        })
    }

    fun setMusic(mMusic: String?): Int {
        return this.resources.getIdentifier(mMusic, "raw", this.packageName)
    }

    fun disableAllButton(){
        // imgPlay.isEnabled = false
        btnJwbA.isEnabled = false
        btnJwbB.isEnabled = false
        btnJwbC.isEnabled = false
        btnJwbD.isEnabled = false
    }

    fun enableAllButton(){
        //   imgPlay.isEnabled = true
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
             Log.d(TAG_MENDENGAR,"index ke $i : "+quiz)
        }

    }

    override fun onBackPressed() {
        val builder =
            AlertDialog.Builder(this)
        builder.setMessage("Apakah anda ingin keluar dari Tes yang sedang berlangsung ?")
        builder.setCancelable(false)

        listener = DialogInterface.OnClickListener { dialog, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                mAnimation.end()
                if (mPlayer.isPlaying()) {
                    mPlayer.stop()
                    mPlayer.reset()
                }

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