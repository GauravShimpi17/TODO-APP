package com.todo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.todo.repo.TodoRepo

class SaveViewModelFactory(private val repo: TodoRepo) : ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SaveDataViewModel(repo) as T
    }
}