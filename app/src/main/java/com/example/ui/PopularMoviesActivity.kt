package com.example.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.utils.Extensions.gone
import com.example.utils.Extensions.show
import com.example.utils.Extensions.toast
import kotlinx.android.synthetic.main.a_movies.*
import org.koin.android.viewmodel.ext.android.viewModel


class PopularMoviesActivity : AppCompatActivity() {

	private lateinit var adapter: MoviesAdapter
	private val model: PopularMoviesViewModel by viewModel()
	private var loading = false
	private var totalPages = 0
	private var currentPage = 0


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.a_movies)
		initAdapter()
		initViewModel()
	}

	private fun initViewModel() {
		startLoading()
		model.initialize()
		model.movies.observe(this, Observer { list ->
			finishLoading()
			adapter.updateData(list)
		})

		model.failureLiveData.observe(this, Observer { bool ->
			if (!bool) return@Observer
			finishLoading()
			this.toast(R.string.something_went_wrong)
		})

		model.pagination.observe(this, Observer { pair ->
			totalPages = pair.second
		})
	}

	private fun finishLoading() {
		pb.gone()
		loading = false
	}

	private fun startLoading() {
		pb.show()
		loading = true
	}

	private fun initAdapter() {
		adapter = MoviesAdapter()
		val manager = LinearLayoutManager(this)
		rv.adapter = adapter
		rv.layoutManager = manager

		rv.addOnScrollListener(object : PaginationScrollListener(manager) {
			override val totalPageCount: Int
				get() = totalPages
			override val isLastPage: Boolean
				get() = currentPage == totalPages
			override val isLoading: Boolean
				get() = loading

			override fun loadMoreItems() {
				startLoading()
				model.fetchMovies()
			}
		})
	}
}
