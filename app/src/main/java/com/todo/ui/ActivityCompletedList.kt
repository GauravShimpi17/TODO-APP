package com.todo.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.todo.R
import com.todo.adapter.TodoAdapter
import com.todo.database.AppDatabase
import com.todo.databinding.ActivityCompletedListBinding
import com.todo.repo.TodoRepo
import com.todo.viewModel.SaveDataViewModel
import com.todo.viewModel.SaveViewModelFactory

class ActivityCompletedList : AppCompatActivity() {

    private val binding : ActivityCompletedListBinding by lazy {
        ActivityCompletedListBinding.inflate(layoutInflater)
    }
    lateinit var viewModel: SaveDataViewModel
    lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpToolBar()
        makeRecycler()

    }
    private fun setUpToolBar(){
        setSupportActionBar(binding.tbCompletedList.tbEditTask)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
            title = "Completed Task"
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }
    }

    private fun makeRecycler(){
        adapter = TodoAdapter()

        binding.rvCompletedTask.adapter = adapter
        binding.rvCompletedTask.layoutManager = LinearLayoutManager(this)
        val dao = AppDatabase.getDatabase(this)
        val repo = TodoRepo(dao)
        viewModel = ViewModelProvider(
            this,
            SaveViewModelFactory(repo)
        )[SaveDataViewModel::class.java]

        viewModel.getAllData().observe(this, Observer {
            it?.let {
                val filteredData = it.filter { todoData -> todoData.isCompleted }
                adapter.setData(filteredData)

            }
        })
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