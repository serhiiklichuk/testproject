package com.example.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.local.model.Movie
import com.example.test.R
import com.example.utils.Constants
import com.example.utils.Extensions.diffUtils
import com.example.utils.Extensions.loadWithGlide
import com.example.utils.Extensions.toLongOrNull
import kotlinx.android.synthetic.main.vh_movies_card.view.*

class InnerMoviesAdapter : RecyclerView.Adapter<InnerMoviesAdapter.VH>() {

	var data = mutableListOf<Movie>()

	init {
		setHasStableIds(true)
	}

	override fun getItemId(position: Int): Long {
		return data[position].id.toLongOrNull() ?: 0
	}

	fun updateData(newData: List<Movie>) {
		this.diffUtils(data, newData) { o, n ->
			o.title == n.title &&
					o.genres == n.genres &&
					o.posterPath == n.posterPath &&
					o.originalTitle == n.originalTitle
		}
		data.clear()
		data.addAll(newData)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
		return VH(
			LayoutInflater.from(parent.context).inflate(
				R.layout.vh_movies_card,
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

	inner class VH(view: View) : RecyclerView.ViewHolder(view) {
		fun bind(item: Movie) {
			val genres = item.genres.joinToString(", ")
			itemView.img.loadWithGlide("${Constants.BASE_IMAGE_URL}${item.posterPath}")
			itemView.title.text = item.originalTitle
			itemView.tvGenres.text = genres
		}
	}
}