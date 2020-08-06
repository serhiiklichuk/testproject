package com.example.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "all_movies")
data class Movies(

	@PrimaryKey
	@ColumnInfo(name = "id")
	val id: Int = 0,

	@ColumnInfo(name = "movies")
	var results: List<Movie> = emptyList()
) {
	companion object {
		val Null = Movies ()
	}
}