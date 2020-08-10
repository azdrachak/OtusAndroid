package com.github.azdrachak.otusandroid.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.github.azdrachak.otusandroid.R
import com.github.azdrachak.otusandroid.model.pojo.MovieAlarmDateTime
import com.github.azdrachak.otusandroid.viewmodel.MovieListViewModel
import java.util.*

class DetailsFragment : Fragment(),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private val movieAlarm = MovieAlarmDateTime()
    var day = 0
    var month: Int = 0
    var year: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var text: String = ""

    var movieId: Int = 0

    companion object {
        const val TAG = "DetailsFragment"
    }

    private val viewModel: MovieListViewModel by lazy {
        ViewModelProvider(requireActivity())
            .get(MovieListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.details_menu, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectedMovie.observe(this.viewLifecycleOwner, Observer { item ->
            movieAlarm.movieId = item.movieId!!
            movieAlarm.movieTitle = item.title!!
            movieAlarm.movieItem = item
            view.findViewById<Toolbar>(R.id.pageNameTextView).title = item.title
            view.findViewById<TextView>(R.id.movieDescription).text = item.description
            Glide
                .with(view)
                .load(item.posterPath)
                .placeholder(item.poster)
                .fitCenter()
                .into(view.findViewById(R.id.detailsPoster))
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_add) {
            pickDateTime()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        movieAlarm.day = dayOfMonth
        movieAlarm.month = month
        movieAlarm.year = year

        val calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(
            this.context,
            this,
            hour,
            minute,
            DateFormat.is24HourFormat(this.context)
        )
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        movieAlarm.hour = hourOfDay
        movieAlarm.minute = minute
        viewModel.onSetAlarm(movieAlarm)
    }

    private fun pickDateTime() {
        val calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)

        val datePickerDialog =
            DatePickerDialog(this.requireContext(), this, year, month, day)
        datePickerDialog.show()
    }
}
