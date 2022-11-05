package com.example.demo_notes.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notes_01.model.User
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add_note(note: User)

    @Query("SELECT * FROM new_users_table_of_notes2 ORDER BY id ASC")
    fun ReadEverething(): LiveData<List<User>>

    @Query("Delete from new_users_table_of_notes2")
    suspend fun DeleteAllNotes()

    @Query("Select * from new_users_table_of_notes2 Where note_title like :searchQuery or note_date like :searchQuery or note_text like :searchQuery")
    fun searchDatabase(searchQuery:String): Flow<List<User>>

    @Query("SELECT * FROM new_users_table_of_notes2 ORDER BY note_title ASC")
    fun sortByTitle(): LiveData<List<User>>

    @Query("SELECT * FROM new_users_table_of_notes2 ORDER BY note_date ASC")
    fun sortByDate(): LiveData<List<User>>

    @Update()
    suspend fun UpdateNote(note:User)

    @Delete
    suspend fun DeeleteNote(note:User)
}