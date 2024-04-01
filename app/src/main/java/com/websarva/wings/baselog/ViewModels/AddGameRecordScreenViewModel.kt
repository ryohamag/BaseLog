package com.websarva.wings.baselog.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddGameRecordScreenViewModel @Inject constructor() : ViewModel() {
    var year by mutableStateOf(Year.now().value.toString()) //年
    var month by mutableStateOf(LocalDate.now().monthValue.toString()) //月
    var date by mutableStateOf(LocalDate.now().dayOfMonth.toString()) //日
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
    var hittingResultList: MutableList<String?> by mutableStateOf(mutableListOf("追加")) //打撃記録を格納するリスト

    // 選択された打撃結果を設定する関数
    fun selectedHittingResult(position: String, abbBattedBall: String) {
        selectedAbbPosition = position
        selectedAbbBattedBall = abbBattedBall
    }

    fun selectedNoHittingResult(status: String) {
        selectedAbbNoBattedBall = status
    }

    // コンポーネントの数を管理する状態
    var hittingResultCount = mutableStateOf(0)
        private set

    // コンポーネントの数を増やすメソッド
    fun addHittingResultComponent() {
        hittingResultCount.value += 1
    }

    // 各コンポーネントのカスケード表示状態を管理するマップ
    var cascadeVisibilityMap = mutableStateMapOf<Int, Boolean>()
        private set

    // カスケードの表示状態を切り替える関数
    fun toggleCascadeVisibility(atBatNumber: Int) {
        val currentValue = cascadeVisibilityMap[atBatNumber] ?: false
        cascadeVisibilityMap[atBatNumber] = !currentValue
    }

    // 特定のatBatNumberのカスケードの表示状態を設定する関数
    fun setCascadeVisibility(atBatNumber: Int, isVisible: Boolean) {
        cascadeVisibilityMap[atBatNumber] = isVisible
    }

}