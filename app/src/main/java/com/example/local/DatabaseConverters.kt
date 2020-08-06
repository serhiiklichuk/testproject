package com.example.local

import androidx.room.TypeConverter
import com.example.local.model.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class DatabaseConverters {

	@TypeConverter
	fun fromGenreList(value: List<Int>): String {
		val gson = Gson()
		return gson.toJson(value)
	}

	@TypeConverter
	fun toGenreList(value: String): List<Int> {
		val type = object : TypeToken<List<Int>>() {}.type
		return Gson().fromJson(value, type)
	}

	@TypeConverter
	fun fromMovieList(value: List<Movie>): String {
		val gson = Gson()
		return gson.toJson(value)
	}

	@TypeConverter
	fun toMovieList(value: String): List<Movie> {
		val type = object : TypeToken<List<Movie>>() {}.type
		return Gson().fromJson(value, type)
	}
}