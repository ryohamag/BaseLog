package com.websarva.wings.baselog.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.ImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.google.gson.Gson
import com.websarva.wings.baselog.Log
import com.websarva.wings.baselog.R
import com.websarva.wings.baselog.ViewModels.AddGameRecordScreenViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun GameCard(
    navController: NavController,
    logs: Log,
    viewModel: AddGameRecordScreenViewModel = hiltViewModel()
) {
    val gson = Gson()
    val logJson = gson.toJson(logs)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = CardDefaults.cardElevation(5.dp),
//        onClick = { navController.navigate("ShowGameRecordScreen")},
    ) {
        Row(
            modifier = Modifier
                .combinedClickable(
                    onClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("selectedLog", logJson)
                        navController.navigate("ShowGameRecordScreen")
                              },
                    onLongClick = {
                        viewModel.showLogDeleteDialog = true
                    },
                )
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val uri = Uri.parse(logs.gameThumbnail)

            android.util.Log.d("GameCard", "uri: ${logs.gameThumbnail}")
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(uri)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = "サムネ写真",
                modifier = Modifier.weight(1f)
            )

            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = logs.date)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "@" + logs.gameVenue)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = logs.matchType)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = logs.tournamentName)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "vs " + logs.opposingTeamName)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = logs.ownTeamScore.toString() + "-" + logs.opposingTeamScore.toString())
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = logs.winOrLose)
                }
                Text(text = logs.hittingResult)
            }
        }
    }

    if (viewModel.showLogDeleteDialog) {
        showLogDeleteDialog()
    }
}

@Composable
fun showLogDeleteDialog(
    viewModel: AddGameRecordScreenViewModel = hiltViewModel()
) {
    AlertDialog(
        onDismissRequest = {
            viewModel.showLogDeleteDialog = false
        },
        title = { Text("確認") },
        text = { Text("このログを削除しますか？") },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.showLogDeleteDialog = false
                }
            ) {
                Text("はい")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    viewModel.showLogDeleteDialog = false
                }
            ) {
                Text("いいえ")
            }
        }
    )
}