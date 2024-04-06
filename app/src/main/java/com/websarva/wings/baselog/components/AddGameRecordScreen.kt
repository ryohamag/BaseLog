package com.websarva.wings.baselog.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.health.connect.datatypes.units.Length
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.websarva.wings.baselog.R
import com.websarva.wings.baselog.ViewModels.AddGameRecordScreenViewModel
import me.saket.cascade.CascadeDropdownMenu
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGameRecordScreen(
    viewModel: AddGameRecordScreenViewModel = hiltViewModel(),
    listLength: Int? = viewModel.openedResult.value
) {
    Log.d("tag", viewModel.hittingResultList.toString())
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
            Modifier.fillMaxWidth(0.5f)
        ) {
            Text(
                text = "日時",
                modifier = Modifier
                    .weight(1f)
                    .align(CenterVertically)
            )
            DatePickerExample()
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            Modifier.fillMaxWidth(0.7f)
        ) {
            Text(
                text = "試合開始時間",
                modifier = Modifier
                    .weight(0.5f)
                    .align(CenterVertically)
            )
            TimePickerExample()
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

        Row( //先攻後攻切り替えボタン Boxを使うと良い説ある、、、？
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

        Spacer(modifier = Modifier.height(20.dp))

        Row( //打順,ポジション入力欄
            Modifier.fillMaxWidth()
        ) {
            Text(
                text = "打順",
                modifier = Modifier
                    .align(CenterVertically)
                    .weight(1f)
            )
            OutlinedTextField(
                value = viewModel.battingOrder,
                onValueChange = { viewModel.battingOrder = it },
                modifier = Modifier.weight(2f)
            )
            Text(
                text = "番",
                modifier = Modifier
                    .align(CenterVertically)
                    .weight(1f)
            )
            Text(
                text = "ポジション",
                modifier = Modifier
                    .align(CenterVertically)
                    .weight(2f),
                fontSize = 14.sp
            )
            PositionDropdownMenu()
        }



        Spacer(modifier = Modifier.height(20.dp))

        Row() {
            Text(text = "打撃結果", modifier = Modifier.align(CenterVertically))
            TextButton(onClick = {
                viewModel.addResult()
                viewModel.openedResult.value = viewModel.openedResult.value!! + 1
            }) {
                Text(text = "1打席追加")
            }
        }

        // 状態に基づいてコンポーネントを動的に追加
        Log.d("numOfResult", "${viewModel.numOfResult.value}")
        Log.d("openedResult", "${viewModel.openedResult.value}")
        Log.d("count", "${viewModel.hoge}")
        for (i in 1 .. viewModel.numOfResult.value) {
            if(i == viewModel.openedResult.value) {
                showHittingResult(atBatNumber = i, isShowCascade = true) {
                    viewModel.showDetail(i)
                }
            } else {
                showHittingResult(atBatNumber = i, isShowCascade =  false) {
                    viewModel.showDetail(i)
                }
            }
        }


        Spacer(modifier = Modifier.height(64.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PositionDropdownMenu() { //ポジションを選択するコンポーネント
    val context = LocalContext.current
    val positions = arrayOf("ピッチャー", "キャッチャー", "ファースト", "セカンド", "サード", "ショート", "レフト", "センター", "ライト", "DH")
    var expanded by remember { mutableStateOf(false) }
    var selectedPosition by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedPosition,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                positions.forEach { position ->
                    DropdownMenuItem(
                        text = { Text(text = position) },
                        onClick = {
                            selectedPosition = position
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun hittingResultCascade( //打撃結果を入力するメニュー
    viewModel: AddGameRecordScreenViewModel = hiltViewModel(),
    isShowCascade: Boolean = false
) {
    val positions = mapOf(
        "ピッチャー" to "投",
        "キャッチャー" to "捕",
        "ファースト" to "一",
        "セカンド" to "二",
        "サード" to "三",
        "ショート" to "遊",
        "レフト" to "左",
        "センター" to "中",
        "ライト" to "右"
    )
    val noBattedBall = mapOf(
        "空振り三振" to "空振",
        "見逃し三振" to "見逃",
        "振り逃げ" to "振逃",
        "フォアボール" to "四球",
        "デッドボール" to "死球",
        "打撃妨害" to "打妨"
    )
    val battedBall = mapOf(
        "ゴロ" to "ゴ",
        "ライナー" to "直",
        "フライ" to "飛",
        "ファールフライ" to "邪飛",
        "ヒット" to "安",
        "ツーベース" to "2",
        "スリーベース" to "3",
        "ホームラン" to "本",
        "ランニングホームラン" to "走本",
        "バント" to "ギ",
        "犠牲フライ" to "犠",
        "エラー" to "失",
        "併殺打" to "併",
        "フィルダースチョイス" to "選"
    )
    Box() {
        CascadeDropdownMenu(
            expanded = isShowCascade,
            onDismissRequest = { viewModel.closeCascade() },
        ) {

            positions.forEach { (position, abbPosition) ->
                DropdownMenuItem(
                    text = { Text(position) },
                    children = {
                        battedBall.forEach { (battedBall, abbBattedBall) ->
                            DropdownMenuItem(
                                text = { Text(battedBall) },
                                onClick = {
                                    viewModel.selectedHittingResult(abbPosition, abbBattedBall)
                                    viewModel.showHittingResultText = true
                                    viewModel.showNoHittingResultText = false
                                    viewModel.isCascadeVisible = false
                                    viewModel.hittingResultList.add(viewModel.hittingResultCount.value, abbPosition + abbBattedBall)
                                }
                            )
                        }
                    },
                )
            }
            noBattedBall.forEach { (noBattedBall, abbNoBattedBall) ->
                DropdownMenuItem(
                    text = { Text(text = noBattedBall) },
                    onClick = {
                        viewModel.selectedNoHittingResult(abbNoBattedBall)
                        viewModel.showNoHittingResultText = true
                        viewModel.showHittingResultText = false
                        viewModel.isCascadeVisible = false
                        viewModel.hittingResultList.add(viewModel.hittingResultCount.value, abbNoBattedBall)
                    }
                )
            }
        }
    }
}

@Composable
fun hittingResultText(
    viewModel: AddGameRecordScreenViewModel = hiltViewModel(),
    atBatNumber: Int,
    isShowCascade: Boolean = false
) {
    TextButton(onClick = { viewModel.isCascadeVisible = true }) {
        if(isShowCascade&&viewModel.isCascadeVisible/*&& viewModel.hittingResultList[atBatNumber - 1] === null*/) {
            hittingResultCascade(isShowCascade = true)
        } else {
            viewModel.hittingResultList[atBatNumber - 1]?.let { Text(text = it) }
        }
    }
}

@Composable
fun noHittingResultText(
    viewModel: AddGameRecordScreenViewModel = hiltViewModel(),
    atBatNumber: Int,
    isShowCascade: Boolean = false
) {
    TextButton(onClick = { viewModel.isCascadeVisible = true }) {
        if(isShowCascade&&viewModel.isCascadeVisible /*&& viewModel.hittingResultList[atBatNumber - 1] === null*/) {
            hittingResultCascade(isShowCascade = true)
        } else {
            viewModel.hittingResultList[atBatNumber - 1]?.let { Text(text = it) }
        }
    }
}

@Composable
fun DatePickerExample() {
    var selectedDate by remember { mutableStateOf("") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    Button(onClick = {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                // 月は0始まりなので、表示目的では1を足す
                selectedDate = "$year/${month + 1}/$dayOfMonth"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }) {
        Text(if (selectedDate.isEmpty()) "日付を選択" else selectedDate)
    }
}

@Composable
fun TimePickerExample() {
    var selectedTime by remember { mutableStateOf("") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    Button(onClick = {
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                // 選択された時間を文字列として保存
                selectedTime = String.format("%02d:%02d" + "～", hourOfDay, minute)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true // 24時間表示かどうか
        ).show()
    },
    
    ) {
        Text(if (selectedTime.isEmpty()) "時間を選択" else selectedTime)
    }
}

@Composable
fun showHittingResult(
    viewModel: AddGameRecordScreenViewModel = hiltViewModel(),
    atBatNumber: Int,
    isShowCascade: Boolean = false,
    showResultDetail: () -> Unit = {}
) {

    Row(
        Modifier.fillMaxWidth(),
    ) {
        Text(
            text = "${atBatNumber}打席目",
            modifier = Modifier
                .align(CenterVertically)
        )
        Spacer(modifier = Modifier.width(20.dp))
        if(viewModel.showHittingResultText) {
            viewModel.selectedAbbPosition?.let {
                viewModel.selectedAbbBattedBall?.let {
                    hittingResultText(
                        atBatNumber = atBatNumber,
                        isShowCascade = isShowCascade
                    )
                }
            }
        } else if(viewModel.showNoHittingResultText) {
            viewModel.selectedAbbNoBattedBall?.let { noHittingResultText(atBatNumber = atBatNumber, isShowCascade = isShowCascade) }
        } else {
            TextButton(
                onClick = {
                    showResultDetail()
                    Log.d("atBatNumber", "${atBatNumber}")
//                    viewModel.showDetail(atBatNumber)
                          },
            ) {
                if (isShowCascade) {
                    hittingResultCascade(isShowCascade = isShowCascade)
                } else {
                    Text(text = "追加")
                }
            }
        }
    }
}