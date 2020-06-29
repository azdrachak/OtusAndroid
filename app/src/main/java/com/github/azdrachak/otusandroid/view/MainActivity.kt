package com.github.azdrachak.otusandroid.view

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.azdrachak.otusandroid.App
import com.github.azdrachak.otusandroid.R
import com.github.azdrachak.otusandroid.ilistener.MovieItemListener
import com.github.azdrachak.otusandroid.model.MovieItem
import com.github.azdrachak.otusandroid.viewmodel.MovieListViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity :
    AppCompatActivity(), MovieItemListener {

    lateinit var viewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)

        if (App.instance.appFirstRun) {
            viewModel.moreMovies()

            loadFragment(SplashFragment.TAG)
            App.instance.appFirstRun = false

            Handler().postDelayed(
                {
                    loadFragment(MovieListFragment.TAG)
                }, 1000
            )
        }

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
            App.instance.favouritesList.add(movieItem)
            movieItem.isFavorite = true
        }

        val delete = {
            App.instance.favouritesList.remove(movieItem)
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

        viewModel.onMovieFavorite()
    }

    override fun onMovieSelected(movieItem: MovieItem) {

        viewModel.onMovieSelect(movieItem)

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                DetailsFragment(),
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
                SplashFragment.TAG -> fragment =
                    SplashFragment()
                MovieListFragment.TAG -> fragment =
                    MovieListFragment()
                FavoritesFragment.TAG -> fragment =
                    FavoritesFragment()
                InviteFragment.TAG -> fragment =
                    InviteFragment()
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
