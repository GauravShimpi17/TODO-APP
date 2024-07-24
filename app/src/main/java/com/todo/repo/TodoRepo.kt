package com.todo.repo

import android.util.Log
import androidx.lifecycle.LiveData
import com.todo.database.AppDatabase
import com.todo.model.TodoData
import java.util.Date
import kotlin.time.Duration.Companion.days


class TodoRepo(private val appData : AppDatabase) {

    suspend fun insertData(todoData: TodoData){
        appData.taskDao().insertData(todoData)
    }

    suspend fun deleteData(todoData: TodoData){
        appData.taskDao().deleteData(todoData)
    }

    fun getById(id : Long) : TodoData{
        return appData.taskDao().getById(id)
    }

    fun getAll() : LiveData<List<TodoData>> {
        return appData.taskDao().getAllData()
    }

    fun getByDate(start : Long) : LiveData<List<TodoData>>{
//        val start = date.time
        val end = start + 1.days.inWholeMilliseconds
        Log.d("Tag", "$start ---  $end")
        return appData.taskDao().getByDate(start, end)
    }
}