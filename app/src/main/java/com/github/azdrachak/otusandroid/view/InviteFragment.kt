package com.github.azdrachak.otusandroid.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.azdrachak.otusandroid.R
import kotlinx.android.synthetic.main.fragment_invite.*

class InviteFragment : Fragment() {

    companion object {
        const val TAG = "InviteFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_invite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sendButton.setOnClickListener {
            sendEmail(
                arrayOf(emailEditText.text.toString()),
                subjectEditText.text.toString(),
                contentsEditText.text.toString()
            )
        }
    }

    private fun sendEmail(addresses: Array<String>, subject: String, text: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, text)
        }
        startActivity(intent)
    }
}