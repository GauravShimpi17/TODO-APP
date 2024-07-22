package com.todo.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.todo.model.TodoData


@Dao
interface TodoDao {

    @Upsert
    suspend fun insertData(todoData: TodoData)

    @Delete
    suspend fun deleteData(todoData: TodoData)

    @Query("SELECT * FROM Todo_Task ORDER BY isCompleted ASC, timestamp DESC ")
    fun getAllData() : LiveData<List<TodoData>>

    @Query("SELECT * FROM Todo_Task WHERE id = :id")
    fun getById(id : Long) : TodoData

    @Query("SELECT * FROM Todo_Task WHERE timestamp >= :start and timestamp<= :end")
    fun getByDate(start : Long, end : Long) : LiveData<List<TodoData>>
}