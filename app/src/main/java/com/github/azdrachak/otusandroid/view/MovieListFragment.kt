package com.github.azdrachak.otusandroid.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.github.azdrachak.otusandroid.App
import com.github.azdrachak.otusandroid.R
import com.github.azdrachak.otusandroid.ilistener.MovieItemListener
import com.github.azdrachak.otusandroid.model.MovieItem
import com.github.azdrachak.otusandroid.viewmodel.MovieListViewModel

class MovieListFragment : Fragment() {

    companion object {
        const val TAG = "MovieListFragment"
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
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        val adapter =
            MovieListAdapter(LayoutInflater.from(
                activity
            ), viewModel.movies.value as MutableList<MovieItem>,
                clickListener = { listener?.onMovieSelected(it) },
                longClickListener = { listener?.onMovieFavorite(it) })

        recyclerView.adapter = adapter

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )


        viewModel.movies.observe(
            this.viewLifecycleOwner,
            Observer { movieList ->
                adapter.setItems(movieList!!)
                adapter.notifyDataSetChanged()
            }
        )

        viewModel.error.observe(this.viewLifecycleOwner,
            Observer {
                if (App.instance.error && it != null) {
                    Toast.makeText(this.requireContext(), it, Toast.LENGTH_LONG).show()
                    viewModel.onErrorShow()
                }
            })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.moreMovies()
                }
            }
        })
    }
}