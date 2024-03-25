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
    var gameVenue by mutableStateOf("")
}