package com.todo.repo

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

    fun getByDate(end : Long) : LiveData<List<TodoData>>{
//        val start = date.time
        val start = end - 1.days.inWholeMilliseconds
        return appData.taskDao().getByDate(start, end)
    }
}