package com.github.azdrachak.otusandroid.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.github.azdrachak.otusandroid.R
import com.github.azdrachak.otusandroid.viewmodel.MovieListViewModel

class DetailsFragment : Fragment() {

    companion object {
        const val TAG = "DetailsFragment"
    }

    private val viewModel: MovieListViewModel by lazy {
        ViewModelProvider(requireActivity())
            .get(MovieListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val item: MovieItem = arguments?.getParcelable(
//            Objects.MOVIE_ITEM.name
//        )!!

        viewModel.selectedMovie.observe(this.viewLifecycleOwner, Observer { item ->
            view.findViewById<Toolbar>(R.id.pageNameTextView).title = item.title
            view.findViewById<TextView>(R.id.movieDescription).text = item.description
            Glide
                .with(view)
                .load(item.posterPath)
                .placeholder(item.poster)
                .fitCenter()
                .into(view.findViewById(R.id.detailsPoster))
        })


    }
}
