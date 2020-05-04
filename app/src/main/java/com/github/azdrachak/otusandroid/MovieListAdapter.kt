package com.github.azdrachak.otusandroid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MovieListAdapter(
    private val inflater: LayoutInflater,
    private val listItems: List<MovieItem>,
    private val clickListener: ((movieItem: MovieItem) -> Unit),
    private val longClickListener: ((movieItem: MovieItem) -> Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val MOVIE_ITEM_TYPE = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieItemViewHolder(inflater.inflate(R.layout.movie_item, parent, false))
    }

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieItemViewHolder) {
            holder.bind(listItems[position])
            holder.itemView.setOnClickListener {
                clickListener(listItems[position])
            }
            holder.itemView.setOnLongClickListener {
                longClickListener(listItems[position])
                true
            }
        }
    }
}
