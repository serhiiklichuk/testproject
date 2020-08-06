package com.example.cloud

import com.example.cloud.service.GenresService
import com.example.cloud.service.IGenresService
import com.example.cloud.service.IMoviesService
import com.example.cloud.service.MoviesService
import com.example.utils.Constants
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CloudApi {

	val movies: MoviesService
	val genres: GenresService

	init {
		val gson = GsonBuilder()
			.setLenient()
			.create()

		val okHttp = OkHttpClient.Builder()
			.readTimeout(30, TimeUnit.SECONDS)
			.writeTimeout(30, TimeUnit.SECONDS)
			.connectTimeout(30, TimeUnit.SECONDS)
			.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
			.build()

		val retrofit = Retrofit.Builder()
			.baseUrl(Constants.BASE_URL)
			.client(okHttp)
			.addConverterFactory(GsonConverterFactory.create(gson))
			.build()

		movies = MoviesService(retrofit.create(IMoviesService::class.java))
		genres = GenresService(retrofit.create(IGenresService::class.java))
	}
}