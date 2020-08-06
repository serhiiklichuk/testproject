package com.example.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.local.dao.GenresDao
import com.example.local.dao.PopularMoviesDao
import com.example.local.model.Genre
import com.example.local.model.Movies

@TypeConverters(DatabaseConverters::class)
@Database(entities = [Genre::class, Movies::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
	abstract val genresDao: GenresDao
	abstract val moviesDao: PopularMoviesDao
}