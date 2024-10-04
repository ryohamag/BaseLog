package com.websarva.wings.baselog.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.websarva.wings.baselog.Log

@Composable
fun LogList(
    logs: List<Log>,
    navController: NavController
) {
    LazyColumn {
        items(logs.size) { index ->
            GameCard(navController = navController, logs = logs[index])
        }
        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}