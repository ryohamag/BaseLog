package com.websarva.wings.baselog.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.gson.Gson
import com.websarva.wings.baselog.Log

@Composable
fun ShowGameRecordScreen(navController: NavController) {
    val gson = Gson()
    val logJson = navController.previousBackStackEntry?.savedStateHandle?.get<String>("selectedLog")
    val log = gson.fromJson(logJson, Log::class.java)
    android.util.Log.d("ShowGameRecordScreen", "log: $log")
    if (log != null) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row() {
                Text(text = log.date + log.startTime + "試合開始")
                if (log.matchType == "公式戦") {
                    Text(text = "公式戦")
                    Text(text = log.tournamentName)
                } else {
                    Text(text = "練習試合")
                }
            }

            Spacer(modifier = Modifier.padding(20.dp))

            Text(text = log.isOwnTeamFirst)

            Spacer(modifier = Modifier.padding(20.dp))

            Text(text = log.ownTeamName + " vs " + log.opposingTeamName)

            Spacer(modifier = Modifier.padding(20.dp))

            Text(text = log.ownTeamScore.toString() + " - " + log.opposingTeamScore.toString())

            Spacer(modifier = Modifier.padding(20.dp))

            Text(text = log.battingOrder + "番 " + log.position)

            Spacer(modifier = Modifier.padding(20.dp))

            val hittingResult = log.hittingResult.split("/")
            hittingResult.forEachIndexed { index, it ->
                Text(text = "第${index + 1}打席: $it")
            }

            Spacer(modifier = Modifier.padding(20.dp))

            Row {
                Text(text = "打点: ${log.RBI}点")
                Text(text = "得点: ${log.run}点")
                Text(text = "盗塁成功: ${log.stealSuccess}回")
                Text(text = "盗塁死: ${log.stealFailed}回")
            }

            Spacer(modifier = Modifier.padding(20.dp))

            Text(text = log.memo)

            Spacer(modifier = Modifier.padding(20.dp))

            val imageUris = log.photoList?.split(",")?.map { it.toUri() } ?: emptyList()
            LazyRow {
                items(imageUris) { uri ->
                    Image(
                        painter = rememberAsyncImagePainter(uri),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp) // 例として200.dpを指定
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}