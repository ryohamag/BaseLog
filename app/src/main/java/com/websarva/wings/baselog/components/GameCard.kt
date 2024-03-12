package com.websarva.wings.baselog.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.websarva.wings.baselog.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameCard(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        onClick = { navController.navigate("ShowGameRecordScreen")}
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.eximage), 
                contentDescription = "サムネ写真",
                modifier = Modifier
                    .weight(1f)
            )
            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(5.dp)
            ) {
                Text(text = "2024/3/11 名工大千種G")
                Text(text = "練習試合 vs岐阜協立大学 11-4 Win")
                Text(text = "遊失 四球 三振 四球")
            }
        }
    }
}