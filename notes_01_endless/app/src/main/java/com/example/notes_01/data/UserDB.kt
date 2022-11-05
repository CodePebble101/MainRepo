package com.example.demo_notes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notes_01.model.User


@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDB: RoomDatabase() {

    abstract  fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDB? = null

        fun getDB(context: Context): UserDB{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return  tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDB::class.java,
                    "user_database4"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}