package com.todo.repo

import androidx.lifecycle.LiveData
import com.todo.database.AppDatabase
import com.todo.model.TodoData


class TodoRepo(private val appData : AppDatabase) {

    fun insertData(todoData: TodoData){
        appData.taskDao().insertData(todoData)
    }

    fun deleteData(todoData: TodoData){
        appData.taskDao().deleteData(todoData)
    }

    fun getById(id : Long) : TodoData{
        return appData.taskDao().getById(id)
    }

    fun getAll() : LiveData<List<TodoData>> {
        return appData.taskDao().getAllData()
    }
}