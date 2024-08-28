package com.example.noteappwithcompose.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title :String,
    val description :String,
    val color :Long,
    val isFavorite :Boolean
)
