package com.todo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Todo_Task")
data class TodoData(
    val title : String,
    val subtitle : String,
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val isCompleted : Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

