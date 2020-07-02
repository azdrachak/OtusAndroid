package com.github.azdrachak.otusandroid.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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

    private val viewModel: MovieListViewModel by lazy {
        ViewModelProvider(this)
            .get(MovieListViewModel::class.java)
    }

    private lateinit var sharedPreferences: SharedPreferences
    private val dataRequestInterval: Long = 20 * 60 * 1000 //20 minutes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("movies_prefs", Context.MODE_PRIVATE)

        if (App.instance.appFirstRun) {

            loadFragment(SplashFragment.TAG)
            App.instance.appFirstRun = false

            // Запрос данных из АПИ, если в БД ничего не сохранялось или прошло > 20 минут с последнего запроса
            if (!sharedPreferences.getBoolean("savedToDb", false)
                || isDataRequestTime(
                    System.currentTimeMillis()
                    , sharedPreferences.getLong("apiAccessTime", System.currentTimeMillis())
                )
            ) {
                viewModel.moreMovies()
            }

            Handler().postDelayed(
                {
                    loadFragment(MovieListFragment.TAG)
                }, 2000
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

        viewModel.apiRequestTimeLiveData.observe(this, Observer {
            it?.let {
                val editor = sharedPreferences.edit()
                editor.putLong("apiAccessTime", it)
                editor.apply()
            }
        })

        viewModel.savedToDbLiveData.observe(this, Observer {
            it?.let {
                val editor = sharedPreferences.edit()
                editor.putBoolean("savedToDb", it)
                editor.apply()
            }
        })
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

        when (action) {
            "add" -> movieItem.isFavorite = true
            "delete" -> movieItem.isFavorite = false
        }

        when (action) {
            "add" -> showSnackbar(
                resources.getText(R.string.addFavourite).toString(), movieItem
            )
            "delete" -> showSnackbar(
                resources.getText(R.string.deleteFavourite).toString(), movieItem
            )
        }

        viewModel.onMovieFavorite(movieItem)
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

    private fun isDataRequestTime(currentTime: Long, lastTime: Long): Boolean =
        currentTime - lastTime >= dataRequestInterval
}
