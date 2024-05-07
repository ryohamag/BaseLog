package com.websarva.wings.baselog.ViewModels

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.websarva.wings.baselog.Log
import com.websarva.wings.baselog.LogDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class AddGameRecordScreenViewModel @Inject constructor(private val logDao: LogDao) : ViewModel() {
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
    var showResultText: MutableList<Boolean?> by mutableStateOf(mutableListOf(null))
    var selectedAbbPosition: String? by mutableStateOf(null) //選択された打撃結果のポジション
    var selectedAbbBattedBall: String? by mutableStateOf(null) //選択された打撃結果の打球
    var selectedAbbNoBattedBall: String? by mutableStateOf(null) //選択された三振、四球等の打撃結果
    var hittingResultList: MutableList<String?> by mutableStateOf(mutableListOf(null)) //打撃記録を格納するリスト
    var numOfResult = mutableStateOf(1) //打席の数
    var openedResult = mutableStateOf<Int?>(null) //開かれている打席

    var RBI by mutableStateOf("0") //打点
    var run by mutableStateOf("0") //得点
    var stealSuccess by mutableStateOf("0") //盗塁数
    var stealFailed by mutableStateOf("0") //盗塁死数

    var memo by mutableStateOf("") //メモ

    var imageUris by mutableStateOf(listOf<Uri?>(null)) //試合の写真
    var showSetThumbnailDialog by mutableStateOf(false) //サムネイル設定ダイアログの制御
    var showDeleteDialog by mutableStateOf(false) //画像削除ダイアログの制御
    var uriToSetThumbnail by mutableStateOf<Uri?>(null) //サムネイルに設定する画像
    var uriToDelete by mutableStateOf<Uri?>(null) //削除する画像
    var gameThumbnail by mutableStateOf<Uri?>(null) //

    var showCreateLogDialog by mutableStateOf(false) //ログ作成確認ダイアログ
    var showErrorToast by mutableStateOf(false) //ログ入力不備トースト表示


    fun createLog() {
        val defaultThumbnailUri = "res/drawable/noimage.jpg"
        val newLog = Log(
            date = selectedDate,
            startTime = selectedTime,
            ownTeamName = ownTeamName,
            opposingTeamName = opposingTeamName,
            isOwnTeamFirst = if(isOwnTeamFirst) "先攻" else "後攻",
            matchType = matchTypeInfo,
            tournamentName = tournamentName,
            ownTeamScore = ownTeamScore.toInt(),
            opposingTeamScore = opposingTeamScore.toInt(),
            gameVenue = gameVenue,
            battingOrder = battingOrder,
            position = selectedPosition,
            hittingResult = hittingResultList.joinToString(),
            RBI = RBI.toInt(),
            run = run.toInt(),
            stealSuccess = stealSuccess.toInt(),
            stealFailed = stealFailed.toInt(),
            memo = memo,
            photoList = imageUris.joinToString(),
            gameThumbnail = gameThumbnail?.toString() ?: defaultThumbnailUri // gameThumbnailがnullの場合はデフォルトURIを使用
        )
        viewModelScope.launch {
            logDao.insertLog(newLog)
            android.util.Log.d("createLog", "success")
        }
    }


    // 選択された打撃結果を設定する関数
    fun selectedHittingResult(position: String, abbBattedBall: String) {
        selectedAbbPosition = position
        selectedAbbBattedBall = abbBattedBall
    }

    fun selectedNoHittingResult(status: String) {
        selectedAbbNoBattedBall = status
    }

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

    // matchTypeに応じた特別な処理
    private val matchTypeInfo = when (selectedMatchType) {
        MatchType.OFFICIAL -> "公式戦"
        MatchType.PRACTICE -> "練習試合"
        null -> ""
    }

    //選択された打撃結果の番号
    fun setOpenedResult(menuNum: Int) {
        openedResult.value = menuNum
    }

    //1打席追加
    fun addResult() {
        numOfResult.value++
        hittingResultList.add(numOfResult.value - 1, null)
        showResultText.add(numOfResult.value - 1, null)
    }

    //1打席削除
    fun deleteResult() {
        if(numOfResult.value >= 1) {
            hittingResultList.removeAt(numOfResult.value - 1)
            showResultText.removeAt(numOfResult.value - 1)
            numOfResult.value--
        }
    }

    //カスケードを閉じる
    fun closeCascade() {
        openedResult.value = 0
    }

    fun checkInputs(): Boolean {
        // 必須項目が入力されているかチェック
        if (selectedDate.isBlank() || selectedTime.isBlank() || ownTeamName.isBlank() || opposingTeamName.isBlank() ||
            selectedMatchType == null || tournamentName.isBlank() || gameVenue.isBlank() || battingOrder.isBlank() ||
            selectedPosition.isBlank()) {
            return false
        }

        if(battingOrder.toInt() < 1 || 9 < battingOrder.toInt()) {
            return false
        }

        if (ownTeamScore.toInt() < 0 || opposingTeamScore.toInt() < 0 || RBI.toInt() < 0 || run.toInt() < 0 || stealSuccess.toInt() < 0 || stealFailed.toInt() < 0) {
            return false
        }

        // 他にもチェックすべき条件があればここに追加

        return true // すべてのチェックを通過した場合
    }


}

enum class MatchType {
    OFFICIAL, // 公式戦
    PRACTICE  // 練習試合
}