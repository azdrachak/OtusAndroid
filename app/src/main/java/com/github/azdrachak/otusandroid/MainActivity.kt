package com.github.azdrachak.otusandroid

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.azdrachak.otusandroid.click.MovieItemListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MainActivity :
    AppCompatActivity(), MovieItemListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(MovieListFragment.TAG)

        findViewById<BottomNavigationView>(R.id.navigation).setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_favorites -> {
                    loadFragment(FavoritesFragment.TAG)
                    true
                }
                R.id.navigation_invite -> {
                    loadFragment(InviteFragment.TAG)
                    true
                }
                R.id.navigation_home -> {
                    loadFragment(MovieListFragment.TAG)
                    true
                }
                else -> false
            }
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
        val action = if (movieItem.isFavorite) "delete" else "add"

        val add = {
            Data.favouritesList.add(movieItem)
            movieItem.isFavorite = true
        }

        val delete = {
            Data.favouritesList.remove(movieItem)
            movieItem.isFavorite = false
        }

        when (action) {
            "add" -> add.invoke()
            "delete" -> delete.invoke()
        }

        when (action) {
            "add" -> showSnackbar(
                resources.getText(R.string.addFavourite).toString(), movieItem
            )
            "delete" -> showSnackbar(
                resources.getText(R.string.deleteFavourite).toString(), movieItem
            )
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

    private fun loadFragment(fragmentTag: String) {
        var fragment = supportFragmentManager.findFragmentByTag(fragmentTag)
        if (fragment == null) {
            when (fragmentTag) {
                MovieListFragment.TAG -> fragment = MovieListFragment()
                FavoritesFragment.TAG -> fragment = FavoritesFragment()
                InviteFragment.TAG -> fragment = InviteFragment()
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment!!, fragmentTag).commit()
    }

    private fun showSnackbar(text: String, movieItem: MovieItem) {
        val snackbar =
            Snackbar.make(findViewById(R.id.fragmentContainer), text, Snackbar.LENGTH_LONG)
        snackbar.setAction(R.string.undo) { onMovieFavorite(movieItem) }
        snackbar.show()
    }
}
