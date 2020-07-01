package com.github.azdrachak.otusandroid.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.github.azdrachak.otusandroid.R
import com.github.azdrachak.otusandroid.ilistener.MovieItemListener
import com.github.azdrachak.otusandroid.model.MovieItem
import com.github.azdrachak.otusandroid.viewmodel.MovieListViewModel

class FavoritesFragment : Fragment() {

    companion object {
        const val TAG = "FavoritesFragment"
    }

    private val viewModel: MovieListViewModel by lazy {
        ViewModelProvider(requireActivity())
            .get(MovieListViewModel::class.java)
    }

    var listener: MovieItemListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        val adapter =
            MovieListAdapter(LayoutInflater.from(
                activity
            ),
                viewModel.favoriteMovies.value as MutableList<MovieItem>? ?: mutableListOf(),
                clickListener = { listener?.onMovieSelected(it) },
                longClickListener = { listener?.onMovieFavorite(it) })

        recyclerView.adapter = adapter

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )

        viewModel.favoriteMovies.observe(
            this.viewLifecycleOwner,
            Observer { movieList ->
                adapter.setItems(movieList!!)
            })
    }
}