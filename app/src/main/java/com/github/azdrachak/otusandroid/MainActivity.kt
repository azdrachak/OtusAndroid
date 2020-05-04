package com.github.azdrachak.otusandroid

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.github.azdrachak.otusandroid.click.MovieItemListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_movie_list.*

//TODO Сохранять положение скрола при перевороте
class MainActivity :
    AppCompatActivity(), MovieItemListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, MovieListFragment(), MovieListFragment.TAG)
            .commit()

        findViewById<View>(R.id.inviteFriendButton).setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, InviteFragment(), InviteFragment.TAG)
                .addToBackStack(InviteFragment.TAG)
                .commit()
        }

        findViewById<View>(R.id.favouritesButton).setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, FavoritesFragment(), FavoritesFragment.TAG)
                .addToBackStack(FavoritesFragment.TAG)
                .commit()
        }
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        when (fragment) {
            is MovieListFragment -> {
                fragment.listener = this
            }
            is FavoritesFragment -> {
                fragment.listener = this
            }
        }
    }

    override fun onMovieFavorite(movieItem: MovieItem) {
        if (!movieItem.isFavorite) {
            Data.favouritesList.add(movieItem)
            val toast =
                Toast.makeText(this, resources.getText(R.string.addFavourite), Toast.LENGTH_LONG)
            toast.show()
            movieItem.isFavorite = true

        } else {
            Data.favouritesList.remove(movieItem)
            val toast =
                Toast.makeText(this, resources.getText(R.string.deleteFavourite), Toast.LENGTH_LONG)
            toast.show()
            movieItem.isFavorite = false
        }
        supportFragmentManager.fragments.last().recyclerView.adapter!!.notifyDataSetChanged()
    }

    override fun onMovieSelected(movieItem: MovieItem) {
        val bundle = Bundle()
        bundle.putParcelable(Objects.MOVIE_ITEM.name, movieItem)
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                DetailsFragment.newInstance(bundle),
                DetailsFragment.TAG
            )
            .addToBackStack(DetailsFragment.TAG)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) super.onBackPressed() else {
            val bld = AlertDialog.Builder(this)
            bld.setTitle(R.string.exitTitle)
            bld.setMessage(R.string.exitPrompt)
            bld.setPositiveButton(R.string.exitYes) { _, _ -> super.onBackPressed() }
            bld.setNegativeButton(R.string.exitNo) { dialog, _ -> dialog.cancel() }
            bld.create().show()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }


}
