package com.example.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.local.model.Movies
import com.example.test.R
import com.example.utils.Extensions.diffUtils
import com.example.utils.Extensions.toLongOrNull
import kotlinx.android.synthetic.main.vh_movies.view.*

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.VH>() {

	var data = mutableListOf<Movies>()

	init {
		setHasStableIds(true)
	}

	override fun getItemId(position: Int): Long {
		return data[position].id.toLongOrNull() ?: 0
	}

	fun updateData(newData: List<Movies>) {
		this.diffUtils(data, newData) { o, n -> o.id == n.id && o.results == n.results }
		data.clear()
		data.addAll(newData)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
		return VH(
			LayoutInflater.from(parent.context).inflate(
				R.layout.vh_movies,
				parent,
				false
			)
		)
	}

	override fun onBindViewHolder(holder: VH, position: Int) {
		holder.bind(data[position])
	}

	override fun getItemCount(): Int {
		return data.size
	}

	inner class VH(private val view: View) : RecyclerView.ViewHolder(view) {
		fun bind(item: Movies) {
			with(itemView.innerRv) {
				adapter = InnerMoviesAdapter().also { it.updateData(item.results) }
				layoutManager =
					LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
			}
		}
	}
}