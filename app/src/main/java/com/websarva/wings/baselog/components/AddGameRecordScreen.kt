package com.websarva.wings.baselog.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.websarva.wings.baselog.R
import com.websarva.wings.baselog.ViewModels.AddGameRecordScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGameRecordScreen(
    viewModel: AddGameRecordScreenViewModel = hiltViewModel()
) {
    // 選択状態を保持するための状態変数
    val (selectedOption, setSelectedOption) = remember { mutableStateOf("none") }

    // 枠線の色を定義
    val defaultBorderColor = Color.Gray
    val selectedBorderColor = Color.Blue
    Column( //親column
        modifier = Modifier
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(64.dp))

        Row( //日時、試合開始時間入力欄
            Modifier.fillMaxWidth()
        ) {
            Text(text = "日時", modifier = Modifier.weight(1f))
            Text(text = "2024年",modifier = Modifier.weight(1f))
            Text(text = "3月", modifier = Modifier.weight(1f))
            Text(text = "8日", modifier = Modifier.weight(1f))
            Text(text = "14:00~", modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row( //自チーム入力欄
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "チーム",
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )
            OutlinedTextField(
                value = viewModel.ownTeamName,
                onValueChange = { viewModel.ownTeamName = it },
                modifier = Modifier
                    .weight(3f)
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(30.dp))
            Text(
                text = "先攻",
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )
        }

        Spacer(modifier = Modifier.height(1.dp))

        Row( //先攻後攻切り替えボタン
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.weight(4f))
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                Icon(painter = painterResource(id = R.drawable.baseline_sync_24), contentDescription = "先攻後攻切り替え")
            }
        }

        Row( //相手チーム入力欄
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "相手チーム",
                modifier = Modifier
                    .weight(1f)
                    .align(CenterVertically),
                fontSize = 12.sp
            )
            OutlinedTextField(
                value = viewModel.opposingTeamName,
                onValueChange = { viewModel.opposingTeamName = it },
                modifier = Modifier
                    .weight(3f)
                    .align(CenterVertically)
            )
            Spacer(modifier = Modifier.width(30.dp))
            Text(
                text = "後攻",
                modifier = Modifier
                    .weight(1f)
                    .align(CenterVertically)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row( //試合種別選択
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "試合種別",
                modifier = Modifier
                    .weight(2f)
                    .align(CenterVertically),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton( //公式戦ボタン
                onClick = { setSelectedOption("option1") },
                // 選択されているかによって枠線の色を変更
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Gray,
                ),
                border = if (selectedOption == "option1") BorderStroke(1.dp, selectedBorderColor) else BorderStroke(1.dp, defaultBorderColor),
                shape = RoundedCornerShape(50), // 枠線の角を丸くする
                modifier = Modifier.weight(4f)
            ) {
                Text("公式戦")
            }

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton( //練習試合ボタン
                onClick = { setSelectedOption("option2") },
                // 選択されているかによって枠線の色を変更
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Gray
                ),
                border = if (selectedOption == "option2") BorderStroke(1.dp, selectedBorderColor) else BorderStroke(1.dp, defaultBorderColor),
                shape = RoundedCornerShape(50), // 枠線の角を丸くする
                modifier = Modifier.weight(4f)
            ) {
                Text("練習試合")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row( //大会名入力欄
            Modifier.fillMaxWidth()
        ) {
            Text(text = "大会名",
                modifier = Modifier
                    .weight(1f)
                    .align(CenterVertically)
            )
            OutlinedTextField(
                value = viewModel.tournamentName,
                onValueChange = { viewModel.tournamentName = it },
                Modifier.weight(4f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row( //試合結果入力欄
            Modifier.fillMaxWidth(0.7f),
        ) {
            Text(text = "試合結果",
                fontSize = 12.sp,
                modifier = Modifier
                    .weight(2f)
                    .align(CenterVertically)
            )
            Spacer(modifier = Modifier.weight(1f))
            OutlinedTextField(value = viewModel.ownTeamScore, //自チームの得点
                onValueChange = { viewModel.ownTeamScore = it },
                Modifier.weight(2f)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "-",
                modifier = Modifier
                    .weight(1f)
                    .align(CenterVertically)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                fontSize = 48.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            OutlinedTextField(value = viewModel.opposingTeamScore, //相手チームの得点
                onValueChange = { viewModel.opposingTeamScore = it },
                Modifier.weight(2f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row( //試合会場入力欄
            Modifier.fillMaxWidth()
        ) {
           Text(
               text = "試合会場",
               modifier = Modifier
                   .align(CenterVertically)
                   .weight(1f)
           )
           OutlinedTextField(
               value = viewModel.gameVenue,
               onValueChange = { viewModel.gameVenue = it },
               modifier = Modifier.weight(4f)
           )
        }
    }
}