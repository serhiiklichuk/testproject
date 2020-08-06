package com.example.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.local.model.Movies
import com.example.repo.PopularMoviesRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PopularMoviesViewModel(
	private val moviesRepo: PopularMoviesRepository
) : ViewModel() {

	val movies by lazy { MutableLiveData<List<Movies>>() }
	val failureLiveData = moviesRepo.failureLiveData
	val pagination = moviesRepo.pagination

	fun initialize() {
		viewModelScope.launch {

			launch {
				moviesRepo.observeMovies().collect { data ->
					movies.value = data.sortedBy { it.id }
				}
			}

			viewModelScope.launch {
				moviesRepo.getGenres()
			}.join()

			fetchMovies()
		}
	}

	fun fetchMovies(offset: Int = 10) =
		viewModelScope.launch {
			moviesRepo.getMovies(offset)
		}
}