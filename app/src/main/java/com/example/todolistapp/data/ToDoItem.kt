package com.example.todolistapp.data

import kotlinx.serialization.Serializable

@Serializable
data class ToDoItem(
    val title : String,
    val id : Int,
    val description : Boolean
)
