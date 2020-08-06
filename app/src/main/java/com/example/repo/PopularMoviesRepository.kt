package com.example.repo

import androidx.lifecycle.MutableLiveData
import com.example.cloud.CloudApi
import com.example.cloud.model.GenreModel
import com.example.cloud.model.GenresResponse
import com.example.cloud.model.PopularMoviesResponse
import com.example.local.AppDatabase
import com.example.local.model.Genre
import com.example.local.model.Movie
import com.example.local.model.Movies
import com.example.utils.Extensions.loge
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class PopularMoviesRepository(private val database: AppDatabase, private val cloud: CloudApi) {

	private var id = 0
	private var genresMap = mapOf<Int, String>()

	val failureLiveData = MutableLiveData<Boolean>()
	val pagination = MutableLiveData<Pair<Int, Int>>()

	suspend fun getMovies(offset: Int) {
		val list = mutableListOf<Movies>()
		for (i in 0 until offset) {
			val page = getLatestPage().plus(i + 1)
			val moviesResponse = getMoviesSafely(page)
			pagination.postValue(Pair(moviesResponse.page ?: 0, moviesResponse.totalPages ?: 0))
			list.add(Movies(moviesResponse.page ?: 0, moviesResponse.results?.map {
				Movie(
					id = ++id,
					popularity = it.popularity ?: 0.0,
					originalTitle = it.originalTitle.orEmpty(),
					genres = it.genreIds?.map { id -> genresMap[id].orEmpty() } ?: emptyList(),
					posterPath = it.posterPath.orEmpty())
			} ?: emptyList()))
		}
		insert(list)
	}

	private suspend fun getMoviesSafely(page: Int): PopularMoviesResponse {
		try {
			return cloud.movies.getMovies(page = page)
		} catch (e: Exception) {
			loge(e.message.orEmpty())
			failureLiveData.postValue(true)
		}
		return PopularMoviesResponse()
	}

	private suspend fun getLatestPage(): Int {
		try {
			return database.moviesDao.getLatestPage() ?: 0
		} catch (e: Exception) {
			loge("failed to get")
		}
		return 0
	}

	suspend fun getGenres() {
		val genres: List<GenreModel> = getGenresSafely().genres ?: emptyList()
		val list = genres.map { Genre(it.id, it.name.orEmpty()) }
		genresMap = list.associate { genre -> Pair(genre.id, genre.name) }
		insertGenres(list)
	}

	private suspend fun getGenresSafely(): GenresResponse {
		try {
			return cloud.genres.getGenres()
		} catch (e: Exception) {
			loge(e.message.orEmpty())
			failureLiveData.postValue(true)
		}
		return GenresResponse()
	}

	private suspend fun insertGenres(data: List<Genre>) {
		try {
			database.genresDao.insert(data)
		} catch (e: Exception) {
			loge("failed to insert")
		}
	}

	private suspend fun insert(data: List<Movies>) {
		try {
			database.moviesDao.insert(data)
		} catch (e: Exception) {
			loge("failed to insert")
		}
	}

	fun observeMovies(): Flow<List<Movies>> {
		try {
			return database.moviesDao.observe()
		} catch (e: Exception) {
			loge("failed to observe")
		}
		return emptyFlow()
	}
}