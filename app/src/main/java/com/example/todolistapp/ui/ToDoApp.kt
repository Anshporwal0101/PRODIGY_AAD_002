package com.example.todolistapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.todolistapp.data.ToDoItem

enum class ToDoApp(val title : String){
    Main_Screen("List Items"),
    Content_Screen("Content")
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun toDoApp(
    appViewModel: AppViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
    )
{
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = ToDoApp.valueOf(
        backStackEntry?.destination?.route ?: ToDoApp.Main_Screen.name
    )
    var canNavigate = navController.previousBackStackEntry != null
    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(
                    text = currentScreen.title,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                )},
                navigationIcon = {
                    if(canNavigate){
                        IconButton(onClick = {navController.navigateUp()}) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White,
                                modifier = Modifier.size(30.dp),
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(Color(110, 110, 250))
            )

                 },
        floatingActionButton =
        {
            FloatingActionButton(
                onClick = { appViewModel.showPopUp(true)},
                containerColor = Color(110, 110, 250),
                modifier = Modifier.size(70.dp),
                )
            {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add",tint = Color.White)
            }
        }
    ){
        NavHost(
            navController = navController,
            startDestination = ToDoApp.Main_Screen.name,
            modifier = Modifier.padding(it)
        )

        {
            composable(ToDoApp.Main_Screen.name)
            {
                mainScreen(appViewModel,navController)
            }
            composable(ToDoApp.Content_Screen.name)
            {
                ItemContent(appViewModel)
            }
        }
    }
}
