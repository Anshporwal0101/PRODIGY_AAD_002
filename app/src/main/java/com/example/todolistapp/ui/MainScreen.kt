package com.example.todolistapp.ui


import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.todolistapp.data.ToDoItem

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun mainScreen(
    appViewModel: AppViewModel,
    navController: NavHostController
    )
{
    val context = LocalContext.current
    val newItem by appViewModel.newItems.collectAsState()
    val popUp by appViewModel.popUp.collectAsState()
    val title by appViewModel.itemTitle.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(204, 255, 255),
                        Color(255, 204, 204)
                    ),
                    startY = 0f,
                    endY = 2000f
                )
            ),
        contentAlignment = Alignment.Center
    )
    {
        Box (
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .background(Color.Transparent)
        ){
            LazyColumn {
                items(newItem){ItemCards(appViewModel,newItem = it,navController)}
            }
        }
    }
    if(popUp)
    {
        Dialog(onDismissRequest = {appViewModel.showPopUp(false)})
        {
            Box(contentAlignment = Alignment.Center) {
                Box(contentAlignment = Alignment.Center) {
                    Card(
                        shape = CardDefaults.elevatedShape,
                        elevation = CardDefaults.cardElevation(10.dp),
                        modifier = Modifier
                            .size(250.dp)
                            .border(1.dp, Color.Black),
                        colors = CardDefaults.cardColors(Color(222, 239, 245))

                    ){
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ){
                            OutlinedTextField(
                                value = title,
                                onValueChange = { appViewModel.setTitleName(it) },
                                label = {
                                    Text(
                                        text = "Add Title",
                                        style = TextStyle(
                                            fontSize = 20.sp,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Medium,
                                        )
                                    )
                                }
                            )
                            Button(
                                onClick =
                                {
                                    if (title.isEmpty()) {
                                        Toast.makeText(
                                            context,
                                            "Please Enter a Title",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        appViewModel.showPopUp(false)
                                        appViewModel.addItem(ToDoItem(title, 1, false))
                                        appViewModel.setTitleName("")
                                        Toast.makeText(context,title +" Added",Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier
                                    .width(280.dp),
                                colors = ButtonDefaults.buttonColors(Color(110, 110, 250))
                            ) {
                                Text(
                                    text = "Add",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Medium,
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemCards(
    appViewModel: AppViewModel,
    newItem : ToDoItem,
    navController : NavHostController
)
{
    val context = LocalContext.current
    val menuEnabled by appViewModel.menuEnabled.collectAsState()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .size(60.dp)
            .clickable{navController.navigate(ToDoApp.Content_Screen.name)},
        colors = CardDefaults.cardColors(Color(110, 110, 250))
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ){
            Text(
                text = newItem.title,
                style = TextStyle(
                    fontSize = 30.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Cursive
                )
            )
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "Options",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {appViewModel.setMenuEnabled(true)},
                tint = Color.White
            )
        }
    }
    if(menuEnabled)
    {
        DropdownMenu(
            expanded = true,
            onDismissRequest = {appViewModel.setMenuEnabled(false)}
        )
        {
            DropdownMenuItem(text = {Text(text = "Update")}, onClick = { /*TODO*/ })
            DropdownMenuItem(
                text = {Text(text = "Remove")},
                onClick = {
                    appViewModel.removeItem(newItem)
                    appViewModel.setMenuEnabled(false)
                    Toast.makeText(context,newItem.title +" Removed",Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}
