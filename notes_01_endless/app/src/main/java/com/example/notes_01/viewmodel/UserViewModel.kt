package com.example.notes_01.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.demo_notes.data.UserDB
import com.example.notes_01.repository.UserRepo
import com.example.notes_01.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application):AndroidViewModel(application) {
    val readAllDAta: LiveData<List<User>>
    val sortByTitle: LiveData<List<User>>
    val sortByDate: LiveData<List<User>>
    private val repository: UserRepo
    init {
        val userDao = UserDB.getDB(application).userDao()
        repository = UserRepo(userDao)
        readAllDAta = repository.readAllData
        sortByTitle = repository.sortByTitle
        sortByDate = repository.sortByDate
    }

    fun add_note(note: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.add_note(note)
        }
    }
    fun updateNote(note:User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.update_note(note)
        }
    }

    fun deleteNote(note:User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete_note(note)
        }
    }

    fun deleteAllNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete_all_notes()
        }
    }
    fun searchDatabase(searchQuery: String):LiveData<List<User>>{
        return  repository.searchDataBase(searchQuery).asLiveData()
    }

}