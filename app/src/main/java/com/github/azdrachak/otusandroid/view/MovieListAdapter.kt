package com.github.azdrachak.otusandroid.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.azdrachak.otusandroid.R
import com.github.azdrachak.otusandroid.model.MovieItem
import com.github.azdrachak.otusandroid.viewmodel.MovieListViewModel
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieListAdapter(
    private val inflater: LayoutInflater,
    private var listItems: MutableList<MovieItem>,
    private val clickListener: ((movieItem: MovieItem) -> Unit),
    private val longClickListener: ((movieItem: MovieItem) -> Unit),
    private val viewModel: MovieListViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val MOVIE_ITEM_TYPE = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieItemViewHolder(
            inflater.inflate(
                R.layout.movie_item,
                parent,
                false
            ),
            viewModel
        )
    }

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieItemViewHolder) {
            holder.bind(listItems[position])
            holder.itemView.setOnClickListener {
                clickListener(listItems[position])
            }
            holder.itemView.heart.setOnClickListener {
                longClickListener(listItems[position])
            }
        }
    }

    fun setItems(movies: List<MovieItem>) {
        movies.forEach {
            if (!contains(listItems, it)) {
                listItems.add(it)
            }
        }
        notifyDataSetChanged()
    }

    private fun contains(movies: List<MovieItem>, movieItem: MovieItem): Boolean {
        for (item in movies) {
            if (item.movieId == movieItem.movieId) return true
        }
        return false
    }
}
