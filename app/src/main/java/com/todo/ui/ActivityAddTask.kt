package com.todo.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.todo.R
import com.todo.adapter.TodoAdapter
import com.todo.database.AppDatabase
import com.todo.databinding.ActivityAddTaskBinding
import com.todo.model.TodoData
import com.todo.repo.TodoRepo
import com.todo.viewModel.SaveDataViewModel
import com.todo.viewModel.SaveViewModelFactory

class ActivityAddTask : AppCompatActivity() {

    private val binding: ActivityAddTaskBinding by lazy {
        ActivityAddTaskBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: SaveDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        appbar()
        addOnClick()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun appbar() {
        val toolbar = binding.addTaskToolbar.editTaskToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
            title = "Add Task"
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }
    }

    private fun addOnClick() {
        val dao = AppDatabase.getDatabase(this)
        val repo = TodoRepo(dao)
        viewModel = ViewModelProvider(
            this, SaveViewModelFactory(repo)
        )[SaveDataViewModel::class.java]

        binding.addTaskBtn.setOnClickListener {
            val title = binding.addTaskTitle.text.toString()
            val subtitle = binding.addTaskSubtitle.text.toString()
            val data = TodoData(title, subtitle)
            viewModel.insertData(data)
            Snackbar.make(binding.root, "Data Saved", Snackbar.LENGTH_LONG).show()
            finish()
        }
    }


}
