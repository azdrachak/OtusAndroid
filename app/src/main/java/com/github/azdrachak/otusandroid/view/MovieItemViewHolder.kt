package com.github.azdrachak.otusandroid.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.drawable.Drawable
import android.text.format.DateFormat
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.github.azdrachak.otusandroid.R
import com.github.azdrachak.otusandroid.model.MovieItem
import com.github.azdrachak.otusandroid.model.pojo.MovieAlarmDateTime
import com.github.azdrachak.otusandroid.viewmodel.MovieListViewModel
import java.time.ZonedDateTime
import java.util.*

class MovieItemViewHolder(private val movieView: View, private val viewModel: MovieListViewModel) :
    RecyclerView.ViewHolder(movieView),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    private val poster: ImageView = movieView.findViewById(R.id.poster)
    private val title: TextView = movieView.findViewById(R.id.title)
    private val spinner: ProgressBar = movieView.findViewById(R.id.spinner)
    private val heart = itemView.findViewById<ImageView>(R.id.heart)
    private val alarm = itemView.findViewById<ImageView>(R.id.alarm)

    private val movieAlarm = MovieAlarmDateTime()
    private val alarmDateTime: GregorianCalendar = GregorianCalendar.from(ZonedDateTime.now())
    var day = 0
    var month: Int = 0
    var year: Int = 0
    var hour: Int = 0
    var minute: Int = 0

    fun bind(item: MovieItem) {
        movieAlarm.movieId = item.movieId!!
        movieAlarm.movieTitle = item.title!!
        movieAlarm.movieItem = item

        title.text = item.title
        heart.setImageResource(
            if (item.isFavorite) R.drawable.ic_favorite_paint_24dp
            else R.drawable.ic_favorite_border_24dp
        )

        alarm.setOnClickListener {
            pickDateTime()
        }

        Glide
            .with(movieView.context)
            .load(item.posterPath)
            .error(R.drawable.ic_broken_image_black_24dp)
            .fallback(R.drawable.ic_image_black_24dp)
            .fitCenter()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    spinner.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    spinner.visibility = View.GONE
                    return false
                }
            })
            .into(poster)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        alarmDateTime.set(GregorianCalendar.DAY_OF_MONTH, dayOfMonth)
        alarmDateTime.set(GregorianCalendar.MONTH, month)
        alarmDateTime.set(GregorianCalendar.YEAR, year)

        val calendar = GregorianCalendar.from(ZonedDateTime.now())
        hour = calendar.toZonedDateTime().hour
        minute = calendar.toZonedDateTime().minute
        val timePickerDialog = TimePickerDialog(
            movieView.context,
            this,
            hour,
            minute,
            DateFormat.is24HourFormat(movieView.context)
        )
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        alarmDateTime.set(GregorianCalendar.HOUR_OF_DAY, hourOfDay)
        alarmDateTime.set(GregorianCalendar.MINUTE, minute)
        alarmDateTime.set(GregorianCalendar.SECOND, 0)

        movieAlarm.dateTime = alarmDateTime

        viewModel.onSetAlarm(movieAlarm)
    }

    private fun pickDateTime() {
        val calendar = GregorianCalendar.from(ZonedDateTime.now())
        day = calendar.toZonedDateTime().dayOfMonth
        month = calendar.toZonedDateTime().monthValue - 1 // месяц считается с 0?
        year = calendar.toZonedDateTime().year

        val datePickerDialog =
            DatePickerDialog(movieView.context, this, year, month, day)
        datePickerDialog.show()
    }
}