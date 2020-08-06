package com.example.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.local.model.Genre
import kotlinx.coroutines.flow.Flow

@Dao
abstract class GenresDao : BaseDao<Genre>(Genre::class.java){

	@Query("SELECT * FROM genre ")
	abstract fun observeGenres(): Flow<List<Genre>>
}