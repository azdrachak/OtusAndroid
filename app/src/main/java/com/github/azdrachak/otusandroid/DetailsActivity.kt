package com.github.azdrachak.otusandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        intent?.let {
            pageNameTextView.text = it.getStringExtra("title")!!.toString()
            ocean11Description.text = it.getStringExtra("description")!!.toString()
            ocean11Poster.setImageResource(it.getIntExtra("poster",0))
        }
    }
}
