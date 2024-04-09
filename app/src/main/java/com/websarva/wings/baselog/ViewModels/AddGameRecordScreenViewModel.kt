package com.websarva.wings.baselog.ViewModels

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    var selectedDate by mutableStateOf("") //試合日時
    var selectedTime by mutableStateOf("") //試合開始時間

    var ownTeamName by mutableStateOf("") //自チーム名
    var opposingTeamName by mutableStateOf("") //相手チーム名

    var tournamentName by mutableStateOf("") //大会名

    var ownTeamScore by mutableStateOf("") //自チーム得点
    var opposingTeamScore by mutableStateOf("") //相手チーム得点

    var gameVenue by mutableStateOf("") //試合会場

    var battingOrder by mutableStateOf("") //打順

    var selectedPosition by mutableStateOf("") //ポジション

    var isCascadeVisible by mutableStateOf(false) //打撃結果入力メニューの制御
    var showHittingResultText by mutableStateOf(false)
    var showNoHittingResultText by mutableStateOf(false)
    var selectedAbbPosition: String? by mutableStateOf(null) //選択された打撃結果のポジション
    var selectedAbbBattedBall: String? by mutableStateOf(null) //選択された打撃結果の打球
    var selectedAbbNoBattedBall: String? by mutableStateOf(null) //選択された三振、四球等の打撃結果
    var hittingResultList: MutableList<String?> by mutableStateOf(mutableListOf("追加")) //打撃記録を格納するリスト
    var numOfResult = mutableStateOf(1) //打席の数
    var openedResult = mutableStateOf<Int?>(null) //開かれている打席

    var RBI by mutableStateOf("0") //打点
    var run by mutableStateOf("0") //得点
    var stealSuccess by mutableStateOf("0") //盗塁数
    var stealFailed by mutableStateOf("0") //盗塁死数

    var memo by mutableStateOf("") //メモ

    var imageUris by mutableStateOf(listOf<Uri>()) //試合の写真
    var showSetThumbnailDialog by mutableStateOf(false) //サムネイル設定ダイアログの制御
    var showDeleteDialog by mutableStateOf(false) //画像削除ダイアログの制御
    var uriToSetThumbnail by mutableStateOf<Uri?>(null) //サムネイルに設定する画像
    var uriToDelete by mutableStateOf<Uri?>(null) //削除する画像
    var gameThumbnail by mutableStateOf<Uri?>(null) //サムネイルに設定された画像


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

    //先攻後攻の管理
    var isOwnTeamFirst by mutableStateOf(true)

    fun toggleFirstAttack() {
        isOwnTeamFirst = !isOwnTeamFirst
    }

    //試合種別の管理
    var selectedMatchType by mutableStateOf<MatchType?>(null)


    fun setMatchType(matchType: MatchType) {
        selectedMatchType = matchType
    }

    fun showDetail(menuNum: Int) {
//        if(menuNum == openedResult.value) {
//            // 既に開かれているメニューを再度クリックした場合は非表示にする
//            openedResult.value = 0
//        }else{
//            // メニュー番号を設定
//            openedResult.value = menuNum
//        }
        openedResult.value = menuNum
    }

    fun addResult() {
        numOfResult.value++ //付け焼き刃
        hittingResultCount.value++
    }

    fun closeCascade() {
        openedResult.value = 0
    }
}

enum class MatchType {
    OFFICIAL, // 公式戦
    PRACTICE  // 練習試合
}