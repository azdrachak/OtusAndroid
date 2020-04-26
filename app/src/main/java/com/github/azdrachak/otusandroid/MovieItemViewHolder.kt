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
        itemView.setOnClickListener { itemClickListener.onItemClick(item) }
        itemView.setOnLongClickListener {
            itemClickListener.onItemLongClick(item)
            true
        }
    }

}