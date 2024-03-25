package com.websarva.wings.baselog

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.websarva.wings.baselog.ViewModels.AddGameRecordScreenViewModel
import com.websarva.wings.baselog.components.AddGameRecordScreen
import com.websarva.wings.baselog.components.AnalysisScreen
import com.websarva.wings.baselog.components.GameCard
import com.websarva.wings.baselog.components.SettingsScreen
import com.websarva.wings.baselog.components.ShowGameRecordScreen
import com.websarva.wings.baselog.components.StatisticsScreen
import com.websarva.wings.baselog.ui.theme.BaseLogTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseLogTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: AddGameRecordScreenViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(
        floatingActionButton = {
            if (currentRoute == "HomeScreen") { //ホームスクリーンならFABを表示させる
                FloatingActionButton(onClick = {
                    navController.navigate("AddGameRecordScreen")
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "新規試合データ作成")
                }
            }
        },
        topBar = {
                 if (currentRoute == "AddGameRecordScreen") {
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
                         actions = { IconButton(onClick = { /*TODO*/ }) {
                             Icon(imageVector = Icons.Default.Add, contentDescription = "追加ボタン")
                         }}
                     )
                 }
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        NavHost(navController = navController, startDestination = "HomeScreen") {
            composable(route = "HomeScreen") {
                GameCard(navController)
            }
            composable(route = "AddGameRecordScreen") {
                AddGameRecordScreen()
            }
            composable(route = "ShowGameRecordScreen") {
                ShowGameRecordScreen()
            }
            composable(route = "StatisticsScreen") {
                StatisticsScreen()
            }
            composable(route = "AnalysisScreen") {
                AnalysisScreen()
            }
            composable(route = "SettingsScreen") {
                SettingsScreen()
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    BottomAppBar(
        actions = {
            IconButton(onClick = { navController.navigate("HomeScreen") }, modifier = Modifier.weight(1f)) {
                Icon(imageVector = Icons.Default.Home, contentDescription = "ホームボタン", modifier = Modifier.size(32.dp))
            }
            IconButton(onClick = { navController.navigate("StatisticsScreen") }, modifier = Modifier.weight(1f)) {
                Icon(painter = painterResource(id = R.drawable.baseline_storage_24), contentDescription = "打撃成績ボタン", modifier = Modifier.size(32.dp))
            }
            IconButton(onClick = { navController.navigate("AnalysisScreen") }, modifier = Modifier.weight(1f)) {
                Icon(painter = painterResource(id = R.drawable.baseline_show_chart_24), contentDescription = "打撃分析ボタン", modifier = Modifier.size(32.dp))
            }
            IconButton(onClick = { navController.navigate("SettingsScreen") }, modifier = Modifier.weight(1f)) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "設定ボタン", modifier = Modifier.size(32.dp))
            }
        }
    ) 
}