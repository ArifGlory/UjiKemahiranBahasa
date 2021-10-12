package com.tapisdev.ujikemahiranbahasa.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize



@Parcelize
data class SeksiMeresponsKaidah(
    var id_soal : Int = 0,
    var tipe_soal : String = "",
    var dialog_1 : String = "",
    var dialog_2 : String = "",
    var monolog : String = "",
    var jawaban_a : String = "",
    var jawaban_b : String = "",
    var jawaban_c : String = "",
    var jawaban_d : String = "",
    var jawaban_benar : String = "",
    var id_paket : Int = 0,
) : Parcelable