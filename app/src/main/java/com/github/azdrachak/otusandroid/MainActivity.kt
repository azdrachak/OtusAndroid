package com.github.azdrachak.otusandroid

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.azdrachak.otusandroid.click.MovieItemListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_movie_list.*

//TODO Сохранять положение скрола при перевороте
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
