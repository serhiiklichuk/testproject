package com.example.cloud.service

import com.example.cloud.model.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface IMoviesService {

	companion object {
		val Null = object : IMoviesService {}
	}

	@GET("movie/popular")
	suspend fun getMovies(
		@Query("api_key") api_key: String = "0d988a26e2a9d227ba4785175db9a891",
		@Query("page") page: Int
	): PopularMoviesResponse = throw NotImplementedError()
}