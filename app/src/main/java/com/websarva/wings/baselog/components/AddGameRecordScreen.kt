package com.websarva.wings.baselog.components

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.health.connect.datatypes.units.Length
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.websarva.wings.baselog.R
import com.websarva.wings.baselog.ViewModels.AddGameRecordScreenViewModel
import com.websarva.wings.baselog.ViewModels.MatchType
import me.saket.cascade.CascadeDropdownMenu
import java.util.Calendar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGameRecordScreen(
    viewModel: AddGameRecordScreenViewModel = hiltViewModel(),
) {
    // 枠線の色を定義
    val defaultBorderColor = Color.Gray
    val selectedBorderColor = Color.Blue

    // ここでのselectedOptionはViewModelのselectedMatchTypeを参照する
    val selectedMatchType = viewModel.selectedMatchType
    val setSelectedMatchType: (MatchType) -> Unit = { viewModel.setMatchType(it) }

    if(viewModel.showCreateLogDialog) {
        CreateLogDialog()
    }

    if(viewModel.showErrorToast) {
        val context = LocalContext.current
        Toast.makeText(context, "入力内容に不備があります", Toast.LENGTH_LONG).show()
        viewModel.showErrorToast = false
    }

    val navController = rememberNavController()
    Scaffold(
        topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    navigationIcon = {
                        IconButton(
                            onClick = {navController.navigate("HomeScreen")},
                        ) {
                            Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "戻るボタン")
                        }},
                    title = { Text(text = "試合情報の追加") },
                    actions = { IconButton(onClick = {
                        if(viewModel.checkInputs()) {
                            viewModel.showCreateLogDialog = true
                        } else {
                            viewModel.showErrorToast = true
                        }

                    }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "追加ボタン")
                    }}
                )
        },
    ) {
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
                    text = "日付",
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
                        .align(CenterVertically)
                )
                OutlinedTextField(
                    value = viewModel.ownTeamName,
                    onValueChange = { viewModel.ownTeamName = it },
                    modifier = Modifier
                        .weight(3f)
                        .align(CenterVertically),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(30.dp))
                Text(
                    text = if (viewModel.isOwnTeamFirst) "先攻" else "後攻",
                    modifier = Modifier
                        .weight(1f)
                        .align(CenterVertically)
                )
            }

            Spacer(modifier = Modifier.height(1.dp))

            Row( //先攻後攻切り替えボタン
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.weight(5f))
                IconButton(
                    onClick = {
                        Log.d("LogTag", "ownTeamScore: ${viewModel.ownTeamScore}")
                        viewModel.toggleFirstAttack()
                        viewModel.createLog()
                    },
                    modifier = Modifier
                        .weight(2f)
                        .align(CenterVertically)
                ) {
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
                        .align(CenterVertically),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(30.dp))
                Text(
                    text = if (!viewModel.isOwnTeamFirst) "先攻" else "後攻",
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
                    onClick = { setSelectedMatchType(MatchType.OFFICIAL) },
                    // 選択されているかによって枠線の色を変更
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Gray,
                    ),
                    border = if (selectedMatchType == MatchType.OFFICIAL) BorderStroke(1.dp, selectedBorderColor) else BorderStroke(1.dp, defaultBorderColor),
                    shape = RoundedCornerShape(50), // 枠線の角を丸くする
                    modifier = Modifier.weight(4f)
                ) {
                    Text("公式戦")
                }

                Spacer(modifier = Modifier.weight(1f))

                OutlinedButton( //練習試合ボタン
                    onClick = { setSelectedMatchType(MatchType.PRACTICE) },
                    // 選択されているかによって枠線の色を変更
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Gray
                    ),
                    border = if (selectedMatchType == MatchType.PRACTICE) BorderStroke(1.dp, selectedBorderColor) else BorderStroke(1.dp, defaultBorderColor),
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
                    Modifier.weight(4f),
                    singleLine = true
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
                    Modifier.weight(2f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
                Log.d("score", viewModel.ownTeamScore)

                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "-",
                    modifier = Modifier
                        .weight(1f)
                        .align(CenterVertically)
                        .wrapContentWidth(CenterHorizontally),
                    fontSize = 48.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                OutlinedTextField(value = viewModel.opposingTeamScore, //相手チームの得点
                    onValueChange = { viewModel.opposingTeamScore = it },
                    Modifier.weight(2f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                modifier = Modifier.fillMaxWidth(0.4f),
                horizontalArrangement = Arrangement.Start
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
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
                Text(
                    text = "番",
                    modifier = Modifier
                        .align(CenterVertically)
                        .weight(1f)
                )

            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.Start
            ) {
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
                TextButton(onClick = { viewModel.addResult() }) {
                    Text(text = "1打席追加")
                }
                TextButton(onClick = { viewModel.deleteResult() }) {
                    Text(text = "1打席削除")
                }
            }

            Log.d("List", viewModel.hittingResultList.toString())
            // 状態に基づいてコンポーネントを動的に追加
            for (i in 1 .. viewModel.numOfResult.value) {
                if(i == viewModel.openedResult.value) {
                    showHittingResult(atBatNumber = i, isShowCascade = true) {
                        viewModel.setOpenedResult(i)
                    }
                } else {
                    showHittingResult(atBatNumber = i, isShowCascade =  false) {
                        viewModel.setOpenedResult(i)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row( //打点、得点、盗塁、盗塁死入力欄
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "打点",
                    modifier = Modifier
                        .weight(1f),
                    fontSize = 14.sp
                )
                OutlinedTextField(
                    value = viewModel.RBI,
                    onValueChange = { viewModel.RBI = it },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "得点",
                    modifier = Modifier
                        .weight(1f),
                    fontSize = 14.sp
                )
                OutlinedTextField(
                    value = viewModel.run,
                    onValueChange = { viewModel.run = it },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "盗塁",
                    modifier = Modifier
                        .weight(1f),
                    fontSize = 14.sp
                )
                OutlinedTextField(
                    value = viewModel.stealSuccess,
                    onValueChange = { viewModel.stealSuccess = it },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "盗塁死",
                    modifier = Modifier
                        .weight(1f),
                    fontSize = 12.sp
                )
                OutlinedTextField(
                    value = viewModel.stealFailed,
                    onValueChange = { viewModel.stealFailed = it },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "試合メモ")
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = viewModel.memo,
                onValueChange = { viewModel.memo = it },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                placeholder = { Text(text = "天候,試合時間など") }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "写真")
            Spacer(modifier = Modifier.height(10.dp))
            ImagePickerAndDisplay()
            Text(text = "画像タップでサムネイルに設定、長押しで削除できます。", fontSize = 12.sp)


            Spacer(modifier = Modifier.height(64.dp)) //bottomBarと被らないようにするためのスペース
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PositionDropdownMenu(
    viewModel: AddGameRecordScreenViewModel = hiltViewModel()
) { //ポジションを選択するコンポーネント
    val positions = arrayOf("ピッチャー", "キャッチャー", "ファースト", "セカンド", "サード", "ショート", "レフト", "センター", "ライト", "DH")
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth(0.7f)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = viewModel.selectedPosition,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor(),
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                positions.forEach { position ->
                    DropdownMenuItem(
                        text = { Text(text = position) },
                        onClick = {
                            viewModel.selectedPosition = position
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
    isShowCascade: Boolean = false,
    atBatNumber: Int
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
    Log.d("openedResult", "${viewModel.openedResult.value}")
    Log.d("showResultText", viewModel.showResultText.toString())
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
                                    if(viewModel.hittingResultList[viewModel.openedResult.value!! - 1] == null || viewModel.hittingResultList[viewModel.openedResult.value!! - 1] == "false") {
                                        viewModel.hittingResultList[viewModel.openedResult.value!! - 1] = abbPosition + abbBattedBall
                                        viewModel.showResultText[atBatNumber - 1] = true
                                    } else {
                                        viewModel.hittingResultList[viewModel.openedResult.value!! - 1] = abbPosition + abbBattedBall
                                        viewModel.showResultText[atBatNumber - 1] = true
                                    }
                                    viewModel.closeCascade()
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
                        if(viewModel.hittingResultList[viewModel.openedResult.value!! - 1] == null || viewModel.hittingResultList[viewModel.openedResult.value!! - 1] == "true") {
                            viewModel.hittingResultList[viewModel.openedResult.value!! - 1] = abbNoBattedBall
                            viewModel.showResultText[atBatNumber - 1] = false
                        } else {
                            viewModel.hittingResultList[viewModel.openedResult.value!! - 1] = abbNoBattedBall
                            viewModel.showResultText[atBatNumber - 1] = false
                        }
                        viewModel.closeCascade()
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
    TextButton(onClick = {
        viewModel.setOpenedResult(atBatNumber)
        viewModel.isCascadeVisible = true
    }) {
        if(isShowCascade && atBatNumber == viewModel.openedResult.value && viewModel.isCascadeVisible) {
            hittingResultCascade(isShowCascade = true, atBatNumber = atBatNumber)
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
    TextButton(onClick = {
        viewModel.setOpenedResult(atBatNumber)
        viewModel.isCascadeVisible = true
    }) {
        if(isShowCascade && atBatNumber == viewModel.openedResult.value && viewModel.isCascadeVisible) {
            hittingResultCascade(isShowCascade = true, atBatNumber = atBatNumber)
        } else {
            viewModel.hittingResultList[atBatNumber - 1]?.let { Text(text = it) }
        }
    }
}

@Composable
fun DatePickerExample(
    viewModel: AddGameRecordScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    Button(onClick = {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                // 月は0始まりなので、表示目的では1を足す
                viewModel.selectedDate = "$year/${month + 1}/$dayOfMonth"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }) {
        Text(if (viewModel.selectedDate.isEmpty()) "日付を選択" else viewModel.selectedDate)
    }
}

@Composable
fun TimePickerExample(
    viewModel: AddGameRecordScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    Button(onClick = {
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                // 選択された時間を文字列として保存
                viewModel.selectedTime = String.format("%02d:%02d" + "～", hourOfDay, minute)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true // 24時間表示かどうか
        ).show()
    },
    
    ) {
        Text(if (viewModel.selectedTime.isEmpty()) "時間を選択" else viewModel.selectedTime)
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
        Log.d("atBatNumber", "$atBatNumber")
        Log.d("showHittingResultText", "${viewModel.showHittingResultText}")
        Log.d("showNoHittingResultText", "${viewModel.showNoHittingResultText}")
        Text(
            text = "${atBatNumber}打席目",
            modifier = Modifier
                .align(CenterVertically)
        )
        Spacer(modifier = Modifier.width(20.dp))
        if(viewModel.showResultText[atBatNumber - 1] == true) {
            viewModel.selectedAbbPosition?.let {
                viewModel.selectedAbbBattedBall?.let {
                    Log.d("show", "動作")
                    hittingResultText(
                        atBatNumber = atBatNumber,
                        isShowCascade = isShowCascade
                    )
                }
            }
        } else if(viewModel.showResultText[atBatNumber - 1] == false) {
            Log.d("show", "動作した")
            viewModel.selectedAbbNoBattedBall?.let {
                noHittingResultText(
                    atBatNumber = atBatNumber,
                    isShowCascade = isShowCascade
                )
            }
        } else if(viewModel.showResultText[atBatNumber - 1] == null) {
            Log.d("show", "動作したぞ")
            TextButton(
                onClick = {
                    showResultDetail()
                    viewModel.setOpenedResult(atBatNumber)
                          },
            ) {
                if (isShowCascade) {
                    hittingResultCascade(isShowCascade = true, atBatNumber = atBatNumber)
                } else {
                    Text(text = "追加")
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagePickerAndDisplay(
    viewModel: AddGameRecordScreenViewModel = hiltViewModel()
) {
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments(),
        onResult = { uris: List<Uri> ->
            uris.let {
                // 既存のリストに新しいURIを追加
                viewModel.imageUris = viewModel.imageUris + it
            }
        }
    )

    Button(onClick = {
        pickImageLauncher.launch(arrayOf("image/*"))
    }) {
        Text("画像を追加")
    }

    // LazyRowを使って選択された画像を横スクロールで表示
    LazyRow {
        items(viewModel.imageUris) { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp) // 例として200.dpを指定
                    .combinedClickable(
                        onClick = { //タップでサムネイルに設定
                            viewModel.uriToSetThumbnail = uri
                            viewModel.showSetThumbnailDialog = true
                        },
                        onLongClick = { //長押しで削除
                            viewModel.uriToDelete = uri
                            viewModel.showDeleteDialog = true
                        }
                    )
            )
        }
    }

    if (viewModel.showSetThumbnailDialog) {
        AlertDialog(
            onDismissRequest = {
                viewModel.showSetThumbnailDialog = false
            },
            title = { Text("確認") },
            text = { Text("この画像をサムネイルに設定しますか？") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.uriToSetThumbnail?.let { uri ->
                            viewModel.gameThumbnail = uri
                            viewModel.uriToSetThumbnail = null
                        }
                        viewModel.showSetThumbnailDialog = false
                    }
                ) {
                    Text("はい")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        viewModel.showSetThumbnailDialog = false
                    }
                ) {
                    Text("いいえ")
                }
            }
        )
    }

    if (viewModel.showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                viewModel.showDeleteDialog = false
            },
            title = { Text("確認") },
            text = { Text("この画像を削除しますか？") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.uriToDelete?.let { uri ->
                            viewModel.imageUris = viewModel.imageUris - uri
                            viewModel.uriToDelete = null
                        }
                        viewModel.showDeleteDialog = false
                    }
                ) {
                    Text("はい")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        viewModel.showDeleteDialog = false
                    }
                ) {
                    Text("いいえ")
                }
            }
        )
    }
}

@Composable
fun CreateLogDialog(
    viewModel: AddGameRecordScreenViewModel = hiltViewModel()
) {
    AlertDialog(
        onDismissRequest = {
            viewModel.showCreateLogDialog = false
        },
        title = { Text("確認") },
        text = { Text("この内容で記録しますか？") },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.createLog()
                    viewModel.showCreateLogDialog = false
                }
            ) {
                Text("はい")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    viewModel.showCreateLogDialog = false
                }
            ) {
                Text("いいえ")
            }
        }
    )
}
