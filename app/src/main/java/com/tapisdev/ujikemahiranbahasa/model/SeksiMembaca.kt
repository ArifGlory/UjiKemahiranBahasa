package com.tapisdev.ujikemahiranbahasa.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize



@Parcelize
data class SeksiMembaca(
    var id_soal : Int = 0,
    var id_bacaan : Int = 0,
    var soal : String = "",
    var bacaan : String = "",
    var gambar : String = "",
    var jawaban_a : String = "",
    var jawaban_b : String = "",
    var jawaban_c : String = "",
    var jawaban_d : String = "",
    var jawaban_benar : String = "",
    var id_paket : Int = 0,
) : Parcelable