package com.example.todolistapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun addingScreen()
{
    //val todoitems by viewModel.toDoItem.collectAsState(initial = emptyList())
    //val size = todoitems.size
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(251, 206, 177),
                        Color(248, 131, 121)
                    ),
                    startY = 0f,
                    endY = 2300f // Adjust the end position based on your requirement
                )
            ),
        contentAlignment = Alignment.Center
    ){
        Box(
            modifier = Modifier
                .height(400.dp)
                .width(350.dp)
                .background(Color.White, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Todo List",
                    style = TextStyle(
                        color = Color(248, 131, 121),
                        fontSize = 80.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Cursive
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.buttonElevation(10.dp),
                    modifier = Modifier
                        .height(45.dp)
                        .width(200.dp)
                )
                {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
                    Text(text = "Add New Items")
                }
            }
        }
    }
}