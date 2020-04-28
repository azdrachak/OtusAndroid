package com.github.azdrachak.otusandroid

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class FavouritesListActivity : AppCompatActivity(), ItemClickListener {
    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites_list)
        initRecycler()
    }

    private fun initRecycler() {
        recycler = findViewById(R.id.recyclerView)
        recycler.adapter =
            MovieListAdapter(LayoutInflater.from(this), Data.favouritesList, this)

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
        val index = Data.favouritesList.indexOf(item)
        Data.favouritesList.remove(item)
        recyclerView.adapter?.notifyItemRemoved(index)
        val toast =
            Toast.makeText(this, resources.getText(R.string.deleteFavourite), Toast.LENGTH_LONG)
        toast.show()
        item.isFavorite = false
        recycler.adapter!!.notifyDataSetChanged()
    }
}
