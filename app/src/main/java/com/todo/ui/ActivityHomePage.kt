package com.todo.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.todo.R
import com.todo.adapter.TaskAction
import com.todo.adapter.TodoAdapter
import com.todo.database.AppDatabase
import com.todo.databinding.ActivityHomePageBinding
import com.todo.model.TodoData
import com.todo.repo.TodoRepo
import com.todo.viewModel.SaveDataViewModel
import com.todo.viewModel.SaveViewModelFactory
import java.util.*

class ActivityHomePage : AppCompatActivity() {

    private val binding: ActivityHomePageBinding by lazy {
        ActivityHomePageBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: TodoAdapter
    private lateinit var viewModel: SaveDataViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpRecyclerView()
        getAllData()
        clearFilter()


        binding.fabAddButton.setOnClickListener {
            Intent(this, ActivityAddTask::class.java).also {
                startActivity(it)
            }
        }
        setSupportActionBar(binding.tbHomePage.toolbarHomePage)

        binding.bottomNavigationView.setOnItemSelectedListener { it ->
            when (it.itemId) {
                R.id.completed -> {
                    Intent(this@ActivityHomePage, ActivityCompletedList::class.java).also {
                        startActivity(it)
                        false
                    }
                }
            }
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.calender -> {
                showCalendarPicker()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showCalendarPicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                viewModel.getByDate(selectedDate.timeInMillis).observe(this, Observer {
                    binding.fabClearFilter.visibility = View.VISIBLE
                    adapter.setData(it)
                    Toast.makeText(
                        this,
                        "Selected date: $dayOfMonth/${month + 1}/$year",
                        Toast.LENGTH_SHORT
                    ).show()
                })
            }, year, month, dayOfMonth
        )

        datePickerDialog.show()
    }

    private fun clearFilter(){
        binding.fabClearFilter.setOnClickListener{
            Toast.makeText(this, "Filter Cleared" , Toast.LENGTH_SHORT).show()
            getAllData()
            binding.fabClearFilter.visibility = View.GONE
        }
    }


    private fun setUpRecyclerView() {
        adapter = TodoAdapter()
        adapter.setClickListener(object : TaskAction {
            override fun onEditClick(todoData: TodoData) {
                Intent(this@ActivityHomePage, ActivityEditTask::class.java).also {
                    it.putExtra("id", todoData.id)
                    startActivity(it)
                }
            }

            override fun onDeleteClick(todoData: TodoData) {
                alertDelete(todoData)
            }

            override fun onCompleteClick(todoData: TodoData) {
                alertIsComplete(todoData)
            }
        })
        val recycler = binding.rvAddTask
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        val dao = AppDatabase.getDatabase(this)
        val repo = TodoRepo(dao)
        viewModel = ViewModelProvider(
            this, SaveViewModelFactory(repo)
        )[SaveDataViewModel::class.java]
    }

    private fun getAllData(){
        viewModel.getAllData().observe(this, Observer {
            it?.let {
                adapter.setData(it)
            }
        })
    }

    fun alertIsComplete(todoData: TodoData) {
        AlertDialog.Builder(this).apply {
            setTitle("Add to completed")
            setMessage("Are you sure you want to add this todo to completed")
            setPositiveButton("Yes") { _, _ ->
                viewModel.setComplete(todoData)
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            setCancelable(false)
        }.show()
    }

    fun alertDelete(todoData: TodoData) {
        AlertDialog.Builder(this).apply {
            setTitle("Delete Task")
            setMessage("Are you sure you want to delete this task")
            setPositiveButton("Yes") { _, _ ->
                viewModel.deleteToto(todoData)
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            setCancelable(false)
        }.show()
    }


    /*override fun onDestroy() {
        super.onDestroy()
        finishAffinity()
        exitProcess(0)
    }*/


}