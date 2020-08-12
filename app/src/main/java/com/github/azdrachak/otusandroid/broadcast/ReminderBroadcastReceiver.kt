package com.github.azdrachak.otusandroid.broadcast

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.github.azdrachak.otusandroid.App
import com.github.azdrachak.otusandroid.R
import com.github.azdrachak.otusandroid.view.MainActivity

class ReminderBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val movieTitle = intent?.getStringExtra("title")
        val text = intent?.getStringExtra("message")
        val movieId: Int = intent?.getStringExtra("movieId")!!.toInt()

        val clickActionIntent = Intent(context, MainActivity::class.java)
        clickActionIntent.putExtra("movieId", movieId.toString())
        val clickActionPendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            clickActionIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context!!, App.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_alarm_on_24)
            .setContentTitle(text)
            .setContentText(movieTitle)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(clickActionPendingIntent)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(movieId, builder.build())
    }
}
