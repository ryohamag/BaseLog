package com.websarva.wings.baselog.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddGameRecordScreenViewModel @Inject constructor() : ViewModel() {
    var ownTeamName by mutableStateOf("") //自チーム名
    var opposingTeamName by mutableStateOf("") //相手チーム名
    var tournamentName by mutableStateOf("") //大会名
    var ownTeamScore by mutableStateOf("") //自チーム得点
    var opposingTeamScore by mutableStateOf("") //相手チーム得点
    var gameVenue by mutableStateOf("") //試合会場
    var battingOrder by mutableStateOf("") //打順
    var isCascadeVisible by mutableStateOf(false) //打撃結果入力メニューの制御
    var showHittingResultText by mutableStateOf(false)
    var showNoHittingResultText by mutableStateOf(false)
    var selectedAbbPosition: String? by mutableStateOf(null) //選択された打撃結果のポジション
    var selectedAbbBattedBall: String? by mutableStateOf(null) //選択された打撃結果の打球
    var selectedAbbNoBattedBall: String? by mutableStateOf(null) //選択された三振、四球等の打撃結果

    // 選択された打撃結果を設定する関数
    fun selectedHittingResult(position: String, abbBattedBall: String) {
        selectedAbbPosition = position
        selectedAbbBattedBall = abbBattedBall
    }

    fun selectedNoHittingResult(status: String) {
        selectedAbbNoBattedBall = status
    }

    //打席を追加する関数
    fun addHittingResult() {

    }
}