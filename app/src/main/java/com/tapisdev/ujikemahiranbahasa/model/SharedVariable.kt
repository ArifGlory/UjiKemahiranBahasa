package com.tapisdev.myapplication.model


class SharedVariable {

    companion object {
        var activePaket = 0
        var countInterStitialAds = 1
        var countRewardedAds = 1
        val nilaiJawabBenar = 7.61
        var activeSkorMendengarkan = 0.0
        var activeSkorKaidah = 0.0
        var activeSkorMembaca = 0.0

        open fun resetScore(){
            activeSkorMendengarkan = 0.0
            activeSkorKaidah = 0.0
            activeSkorMembaca = 0.0
        }

    }


}