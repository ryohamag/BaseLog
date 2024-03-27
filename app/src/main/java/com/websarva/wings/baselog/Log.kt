package com.websarva.wings.baselog

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Log(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var year: Int, //西暦
    var month: Int, //月
    var date: Int, //日
    var startTime: Int, //試合開始時間
    var ownTeamName: String, //自チーム名
    var opposingTeamName: String, //相手チーム名
    var matchType: String, //試合種別
    var tournamentName: String, //大会名
    var ownTeamScore: Int, //自チーム得点
    var opposingTeamScore: Int, //相手チーム得点
    var gameVenue: String, //試合会場
    var battingOrder: Int, //打順
    var position: String, //ポジション
    var hittingResult: String, //打撃結果
    var RBI: Int, //打点
    var run: Int, //得点
    var stealSuccess: Int, //盗塁数
    var stealFailed: Int, //盗塁死数
    var memo: String, //メモ欄
    var photoList: String, //保存された写真
)
