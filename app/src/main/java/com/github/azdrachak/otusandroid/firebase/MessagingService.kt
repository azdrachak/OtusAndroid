package com.github.azdrachak.otusandroid.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.github.azdrachak.otusandroid.App
import com.github.azdrachak.otusandroid.R
import com.github.azdrachak.otusandroid.view.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService : FirebaseMessagingService() {

    private var messageTitle: String = ""
    private var messageText: String = ""

    private var movieId = "FCM"
    private var title = ""
    private var description = ""
    private var posterPath = ""

    companion object {
        const val TAG = "MessagingService"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        messageTitle = remoteMessage.data["messageTitle"]!!
        messageText = remoteMessage.data["messageText"]!!
        title = remoteMessage.data["title"]!!
        description = remoteMessage.data["description"]!!
        posterPath = remoteMessage.data["posterPath"]!!

        sendNotification(remoteMessage)
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {

        val clickActionIntent = Intent(applicationContext, MainActivity::class.java).let {
            it.putExtra("movieId", movieId)
            it.putExtra("title", title)
            it.putExtra("description", description)
            it.putExtra("posterPath", posterPath)
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            clickActionIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationChannelId = App.CHANNEL_ID

        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (nm.getNotificationChannel(notificationChannelId) == null) {
                NotificationChannel(
                    notificationChannelId,
                    getString(R.string.channel_name),
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = getString(R.string.channel_desc)
                    enableLights(true)
                    enableVibration(true)
                    setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
                    nm.createNotificationChannel(this)
                }
            }
        }

        val notificationBuilder = NotificationCompat.Builder(this, notificationChannelId)
            .setSmallIcon(R.drawable.ic_email_black_24dp)
            .setContentTitle(messageTitle)
            .setContentText(messageText)
            .setAutoCancel(true)
            .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
            .setContentIntent(pendingIntent)

        val notificationId = System.currentTimeMillis().toInt()
        nm.notify(notificationId, notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        Log.d(TAG, token)
    }
}