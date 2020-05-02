package com.github.azdrachak.otusandroid

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), ItemClickListener {

    lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecycler()

        findViewById<View>(R.id.inviteFriendButton).setOnClickListener {
            startActivity(Intent(this, InviteActivity::class.java))
        }

        findViewById<View>(R.id.favouritesButton).setOnClickListener {
            startActivity(Intent(this, FavouritesListActivity::class.java))
        }
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler = findViewById(R.id.recyclerView)
        recycler.layoutManager = layoutManager
        recycler.adapter = MovieListAdapter(LayoutInflater.from(this), Data.items, this)

        val itemDecorator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(getDrawable(R.drawable.divider)!!)
        recycler.addItemDecoration(itemDecorator)
    }

    override fun onItemClick(item: MovieItem) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("title", item.title)
        intent.putExtra("poster", item.poster)
        intent.putExtra("description", item.description)
        startActivity(intent)
    }

    override fun onItemLongClick(item: MovieItem) {
        if (!item.isFavorite) {
            Data.favouritesList.add(item)
            val toast =
                Toast.makeText(this, resources.getText(R.string.addFavourite), Toast.LENGTH_LONG)
            toast.show()
            item.isFavorite = true

        } else {
            Data.favouritesList.remove(item)
            val toast =
                Toast.makeText(this, resources.getText(R.string.deleteFavourite), Toast.LENGTH_LONG)
            toast.show()
            item.isFavorite = false
        }
        recycler.adapter!!.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        val bld = AlertDialog.Builder(this)
        bld.setTitle(R.string.exitTitle)
        bld.setMessage(R.string.exitPrompt)
        bld.setPositiveButton(R.string.exitYes) { _, _ -> super.onBackPressed() }
        bld.setNegativeButton(R.string.exitNo) { dialog, _ -> dialog.cancel() }
        bld.create().show()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        recycler.adapter!!.notifyDataSetChanged()
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onResume() {
        recycler.adapter!!.notifyDataSetChanged()
        super.onResume()
    }
}
