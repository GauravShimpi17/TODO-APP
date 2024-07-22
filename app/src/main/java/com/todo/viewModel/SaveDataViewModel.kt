package com.todo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todo.model.TodoData
import com.todo.repo.TodoRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class SaveDataViewModel(private val repo: TodoRepo) : ViewModel() {

    fun insertData(todoData: TodoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertData(todoData)
        }
    }

    /*    suspend fun deleteData(todoData: TodoData){
            repo.deleteData(todoData)
        }*/

    fun getById(id: Long): TodoData {
        return repo.getById(id)
    }

    fun getAllData(): LiveData<List<TodoData>> {
        return repo.getAll()
    }

    fun setComplete(todoData: TodoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertData(todoData.copy(isCompleted = true))
        }
    }

    fun deleteToto(todoData: TodoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteData(todoData)
        }
    }

    fun getByDate(date : Long) : LiveData<List<TodoData>>{
        return repo.getByDate(date)
    }

}