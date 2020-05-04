package com.github.azdrachak.otusandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.github.azdrachak.otusandroid.click.MovieItemListener

class MovieListFragment : Fragment() {

    companion object {
        const val TAG = "MovieListFragment"
    }

    var listener: MovieItemListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.adapter =
            MovieListAdapter(LayoutInflater.from(activity), Data.items,
                clickListener = { listener?.onMovieSelected(it) },
                longClickListener = { listener?.onMovieFavorite(it) })

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
    }
}