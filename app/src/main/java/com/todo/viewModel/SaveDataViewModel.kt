package com.todo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.todo.model.TodoData
import com.todo.repo.TodoRepo

class SaveDataViewModel(private val repo: TodoRepo) : ViewModel() {

    fun insertData(todoData: TodoData){
        repo.insertData(todoData)
    }

    fun deleteData(todoData: TodoData){
        repo.deleteData(todoData)
    }

    fun getById(id : Long) : TodoData{
        return repo.getById(id)
    }

    fun getAllData() : LiveData<List<TodoData>> {
        return repo.getAll()
    }

    fun setComplete(todoData: TodoData) {
            repo.insertData(todoData.copy(isCompleted = true))
    }

    fun deleteToto(todoData: TodoData){
        repo.deleteData(todoData)
    }

    fun updateData(){

    }

}