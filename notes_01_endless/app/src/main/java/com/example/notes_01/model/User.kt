package com.example.notes_01.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "new_users_table_of_notes2")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id:Int,

    val note_title: String,
    val note_text:String,
    val note_date: String,
    val note_time: String,

    @ColumnInfo(defaultValue = "Пусто")
    val note_update_day:String,

    @ColumnInfo(defaultValue = "Пусто")
    val note_update_time:String,





):Parcelable