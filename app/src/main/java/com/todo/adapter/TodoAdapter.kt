package com.todo.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.todo.R
import com.todo.databinding.TodoCardBinding
import com.todo.model.TodoData


interface TaskAction {
    fun onEditClick(todoData: TodoData)
    fun onDeleteClick(todoData: TodoData)
    fun onCompleteClick(todoData: TodoData)
}

object DefaultClickBehaviour:TaskAction{
    override fun onEditClick(todoData: TodoData) {}
    override fun onDeleteClick(todoData: TodoData) {}
    override fun onCompleteClick(todoData: TodoData) {}

}

class TodoAdapter() :
    RecyclerView.Adapter<TodoAdapter.MyViewHolder>() {

    private var todoList : List<TodoData> = mutableListOf()
    private var taskAction: TaskAction = DefaultClickBehaviour
    fun setClickListener(taskAction: TaskAction){
        this.taskAction = taskAction
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            TodoCardBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }
    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemPosition = todoList[position]
        holder.binding.apply {
            cardTitle.text = itemPosition.title
            cardSubTitle.text = itemPosition.subtitle

            if (itemPosition.isCompleted){
                btnGroup.visibility = View.GONE
                cardConstraint.setBackgroundResource(R.drawable.complete_bg)
            }else{
                btnGroup.visibility = View.VISIBLE
                cardConstraint.background = null
            }
        }
    }

    fun setData(list : List<TodoData>){
        todoList = list
        notifyDataSetChanged()
        Log.e("hello", list.toString())
    }

    inner class MyViewHolder(val binding: TodoCardBinding) : ViewHolder(binding.root) {
        init {
            binding.editButton.setOnClickListener{
                if (adapterPosition == NO_POSITION){
                    return@setOnClickListener
                }
                taskAction.onEditClick(todoList[adapterPosition])
            }

            binding.deleteButton.setOnClickListener {
                if(adapterPosition == NO_POSITION){
                    return@setOnClickListener
                }
                taskAction.onDeleteClick(todoList[adapterPosition])
            }

            binding.completeButton.setOnClickListener{
                if (adapterPosition == NO_POSITION){
                    return@setOnClickListener
                }
                taskAction.onCompleteClick(todoList[adapterPosition])
            }
        }
    }

}