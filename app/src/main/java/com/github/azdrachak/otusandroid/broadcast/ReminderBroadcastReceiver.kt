package com.github.azdrachak.otusandroid.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.github.azdrachak.otusandroid.App
import com.github.azdrachak.otusandroid.R

class ReminderBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val movieTitle = intent?.getStringExtra("title")
        val text = intent?.getStringExtra("message")
        val movieId: Int = intent?.getStringExtra("movieId")!!.toInt()

        val builder = NotificationCompat.Builder(context!!, App.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_alarm_on_24)
            .setContentTitle(text)
            .setContentText(movieTitle)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(movieId, builder.build())
    }
}
