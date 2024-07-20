package com.todo.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.todo.R
import com.todo.database.AppDatabase
import com.todo.databinding.ActivityEditTaskBinding
import com.todo.model.TodoData
import com.todo.repo.TodoRepo
import com.todo.viewModel.SaveDataViewModel
import com.todo.viewModel.SaveViewModelFactory

class ActivityEditTask : AppCompatActivity() {

    val binding: ActivityEditTaskBinding by lazy {
        ActivityEditTaskBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: SaveDataViewModel
    private var id: Long = 0
    private lateinit var todoData : TodoData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setToolBar()
        getDataById()
        updateData()
        cancelEdit()
    }


    fun setToolBar() {
        setSupportActionBar(binding.editTaskToolbar.editTaskToolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
            title = "Edit Task"
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }
    }

    fun getDataById() {
        val dao = AppDatabase.getDatabase(this)
        val repo = TodoRepo(dao)

        viewModel = ViewModelProvider(
            this,
            SaveViewModelFactory(repo)
        )[SaveDataViewModel::class.java]

        id = intent.getLongExtra("id", 0)
        todoData =  viewModel.getById(id)
        binding.editTaskTitle.setText(todoData.title)
        binding.editTaskSubtitle.setText(todoData.subtitle)
    }

    private fun updateData(){
        binding.updateEdit.setOnClickListener {
            val title = binding.editTaskTitle.text.toString()
            val subTitle = binding.editTaskSubtitle.text.toString()
            val data = TodoData(title, subTitle,id)
            alertEditTask(data)

        }
    }

    private fun cancelEdit(){
        binding.cancelEdit.setOnClickListener {
            finish()
        }
    }

    fun alertEditTask(todoData: TodoData){
        AlertDialog.Builder(this).apply {
            setTitle("Update Task")
            setMessage("Are you sure you want to update the task")
            setPositiveButton("Yes") { _, _ ->
                viewModel.insertData(todoData)
                Toast.makeText(this@ActivityEditTask, "Data Updated", Toast.LENGTH_SHORT).show()
                finish()
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            setCancelable(false)
        }.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}