package com.github.azdrachak.otusandroid

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private var ocean11visited = false
    private var ocean12visited = false
    private val visitedColor = Color.BLUE
    private val regularColor = Color.DKGRAY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState?.apply {
            ocean11visited = savedInstanceState.getBoolean("ocean11")
            ocean12visited = savedInstanceState.getBoolean("ocean12")
        }

        setColor(ocean11Title, ocean11visited)
        setColor(ocean12Title, ocean12visited)

        detailsButtonOcean11.setOnClickListener {
            ocean11Title.setTextColor(visitedColor)
            ocean11visited = true
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("title", getStringFromRes(R.string.ocean11ru))
            intent.putExtra("poster", R.drawable.ocean11)
            intent.putExtra("description", getStringFromRes(R.string.ocean11_descr))
            startActivity(intent)
        }

        detailsButtonOcean12.setOnClickListener {
            ocean12Title.setTextColor(visitedColor)
            ocean12visited = true
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("title", getStringFromRes(R.string.ocean12ru))
            intent.putExtra("poster", R.drawable.ocean12)
            intent.putExtra("description", getStringFromRes(R.string.ocean12_descr))
            startActivity(intent)
        }

        inviteFriendButton.setOnClickListener {
            startActivity(Intent(this, InviteActivity::class.java))
        }
    }

    override fun onBackPressed() {
        val bld = AlertDialog.Builder(this)
        bld.setTitle(R.string.exitTitle)
        bld.setMessage(R.string.exitPrompt)
        bld.setPositiveButton(R.string.exitYes) { _, _ -> super.onBackPressed() }
        bld.setNegativeButton(R.string.exitNo) { dialog, _ -> dialog.cancel() }
        bld.create().show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("ocean11", ocean11visited)
        outState.putBoolean("ocean12", ocean12visited)
    }

    private fun setColor(text: TextView, isVisited: Boolean) =
        when {
            isVisited -> text.setTextColor(visitedColor)
            else -> text.setTextColor(regularColor)
        }

    private fun getStringFromRes(id: Int): String = resources.getString(id)
}
