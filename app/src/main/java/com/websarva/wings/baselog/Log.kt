package com.websarva.wings.baselog

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Log(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String, //日付
    val startTime: String, //試合開始時間
    val ownTeamName: String, //自チーム名
    val opposingTeamName: String, //相手チーム名
    val isOwnTeamFirst: String, //先攻か後攻か
    val matchType: String, //試合種別
    val tournamentName: String, //大会名
    val ownTeamScore: Int, //自チーム得点
    val opposingTeamScore: Int, //相手チーム得点
    val winOrLose: String, //勝敗
    val gameVenue: String, //試合会場
    val battingOrder: String, //打順
    val position: String, //ポジション
    val hittingResult: String, //打撃結果
    val RBI: Int, //打点
    val run: Int, //得点
    val stealSuccess: Int, //盗塁数
    val stealFailed: Int, //盗塁死数
    val memo: String, //メモ欄
    var photoList: String?, //保存された写真
    val gameThumbnail: String //サムネイルの写真
)