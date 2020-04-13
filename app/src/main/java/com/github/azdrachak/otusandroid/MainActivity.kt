package com.github.azdrachak.otusandroid

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

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
            startActivity(Intent(this, Ocean11Activity::class.java))
        }

        detailsButtonOcean12.setOnClickListener {
            ocean12Title.setTextColor(visitedColor)
            ocean12visited = true
            startActivity(Intent(this, Ocean12Activity::class.java))
        }

        inviteFriendButton.setOnClickListener {
            startActivity(Intent(this, InviteActivity::class.java))
        }
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
}
