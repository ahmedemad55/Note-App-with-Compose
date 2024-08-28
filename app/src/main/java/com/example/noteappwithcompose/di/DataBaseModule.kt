package com.example.noteappwithcompose.di

import android.content.Context
import androidx.room.Room
import com.example.noteappwithcompose.data.db.NoteDao
import com.example.noteappwithcompose.data.db.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideNoteDao(noteDatabase: NoteDatabase) : NoteDao {
        return noteDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "user_database"
        ).build()

    }
}