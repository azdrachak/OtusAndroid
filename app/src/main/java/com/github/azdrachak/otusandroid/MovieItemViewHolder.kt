package com.github.azdrachak.otusandroid

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MovieItemViewHolder(movieView: View) : RecyclerView.ViewHolder(movieView) {
    private val poster: ImageView = movieView.findViewById(R.id.poster)
    private val title: TextView = movieView.findViewById(R.id.title)

    fun bind(item: MovieItem, itemClickListener: ItemClickListener) {
        poster.setImageResource(item.poster)
        title.text = item.title
        itemView.findViewById<ImageView>(R.id.heart).setImageResource(
            if (item.isFavorite) R.drawable.ic_favorite_paint_24dp
            else R.drawable.ic_favorite_border_24dp
        )
        itemView.setOnClickListener { itemClickListener.onItemClick(item) }
        itemView.setOnLongClickListener {
            itemClickListener.onItemLongClick(item)
            true
        }
    }

}