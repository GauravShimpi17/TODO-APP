package com.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.todo.dao.TodoDao
import com.todo.model.TodoData

@Database(entities = [TodoData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TodoDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "task_database"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE!!
        }
    }
}