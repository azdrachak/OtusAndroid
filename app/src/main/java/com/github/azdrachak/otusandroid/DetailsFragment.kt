package com.github.azdrachak.otusandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

class DetailsFragment : Fragment() {

    companion object {
        const val TAG = "DetailsFragment"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
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
        val item: MovieItem = arguments?.getParcelable(Objects.MOVIE_ITEM.name)!!

        view.findViewById<Toolbar>(R.id.pageNameTextView).title = item.title
        view.findViewById<ImageView>(R.id.detailsPoster).setImageResource(item.poster)
        view.findViewById<TextView>(R.id.movieDescription).text = item.description
    }
}
