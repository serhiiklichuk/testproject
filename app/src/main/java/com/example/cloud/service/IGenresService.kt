package com.example.cloud.service

import com.example.cloud.model.GenresResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface IGenresService {

	companion object {
		val Null = object : IGenresService {}
	}

	@GET("genre/movie/list")
	suspend fun getGenres(
		@Query("api_key") api_key: String = "0d988a26e2a9d227ba4785175db9a891"): GenresResponse = throw NotImplementedError()
}