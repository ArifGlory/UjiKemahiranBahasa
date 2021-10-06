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
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_playing_audio)

        val btnEndAudio = dialog.findViewById(R.id.btnEndAudio) as Button
        val tvInfoAudio = dialog.findViewById(R.id.tvInfoAudio) as TextView

        setupAudio()
        setupInfoAudio()

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
        tvInfoSoal.setText("Soal ke - "+currentSoal+1)

        btnJwbA.setText(listSoal.get(currentSoal).jawaban_a)
        btnJwbB.setText(listSoal.get(currentSoal).jawaban_b)
        btnJwbC.setText(listSoal.get(currentSoal).jawaban_c)
        btnJwbD.setText(listSoal.get(currentSoal).jawaban_d)
    }

    fun setupAudio(){
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.stop()
                mPlayer.reset()
            }
        }

        Log.d(TAG_CHECK_AUDIO,"audio nya : "+listSoal.get(currentSoal).dialog)
        mPlayer = MediaPlayer.create(this, setMusic(listSoal.get(currentSoal).dialog))
        /*mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength())*/
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


}