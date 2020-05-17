package com.github.azdrachak.otusandroid

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MovieItemViewHolder(private val movieView: View) : RecyclerView.ViewHolder(movieView) {
    private val poster: ImageView = movieView.findViewById(R.id.poster)
    private val title: TextView = movieView.findViewById(R.id.title)
    private val spinner: ProgressBar = movieView.findViewById(R.id.spinner)

    fun bind(item: MovieItem) {
        title.text = item.title
        itemView.findViewById<ImageView>(R.id.heart).setImageResource(
            if (item.isFavorite) R.drawable.ic_favorite_paint_24dp
            else R.drawable.ic_favorite_border_24dp
        )

        Glide
            .with(movieView.context)
            .load(item.posterPath)
            .error(R.drawable.ic_broken_image_black_24dp)
            .fallback(R.drawable.ic_image_black_24dp)
            .fitCenter()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    spinner.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    spinner.visibility = View.GONE
                    return false
                }
            })
            .into(poster)
    }
}