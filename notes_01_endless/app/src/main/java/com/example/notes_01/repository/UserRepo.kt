package com.example.notes_01.repository

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.demo_notes.data.UserDao
import com.example.notes_01.model.User
import kotlinx.coroutines.flow.Flow

class UserRepo(private  val userDao: UserDao) {
    val readAllData : LiveData<List<User>> = userDao.ReadEverething()
    val sortByTitle : LiveData<List<User>> = userDao.sortByTitle()
    val sortByDate : LiveData<List<User>> = userDao.sortByDate()
    suspend fun add_note(note: User){
        userDao.add_note(note)
    }
    suspend fun update_note(note: User){
        userDao.UpdateNote(note)
    }

    suspend fun delete_note(note:User)
    {
        userDao.DeeleteNote(note)
    }

    suspend fun delete_all_notes()
    {
        userDao.DeleteAllNotes()
    }

    fun searchDataBase(searchQuery: String): Flow<List<User>>{
        return userDao.searchDatabase(searchQuery)
    }

}