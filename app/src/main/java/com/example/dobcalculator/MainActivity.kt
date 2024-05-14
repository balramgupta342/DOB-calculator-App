package com.example.dobcalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate : TextView?= null
    private var tvAgeInMinutes : TextView?= null
    private var tvHours : TextView?= null
    private var tvDays :TextView?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById(R.id.btndatepicker)
        tvSelectedDate = findViewById(R.id.tvselecteddate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)
        tvHours = findViewById(R.id.tvHours)
        tvDays = findViewById(R.id.tvDays)
        btnDatePicker.setOnClickListener{
            clickdatepicker()
        }
    }

    private fun clickdatepicker(){
        val mycalendar = Calendar.getInstance()
        val year = mycalendar.get(Calendar.YEAR)
        val month = mycalendar.get(Calendar.MONTH)
        val day = mycalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this,
            { _, selectedyear, selectedmonth, selecteddayOfMonth ->
                Toast.makeText(this,
                    "Year was $selectedyear , Month was ${selectedmonth+1} , Day of month was $selecteddayOfMonth",
                    Toast.LENGTH_LONG).show()

                val selectedDate = "$selecteddayOfMonth/${selectedmonth+1}/$selectedyear"
                tvSelectedDate?.text=selectedDate

                val sdf= SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val thedate = sdf.parse(selectedDate)
                thedate.let {
                    val selectedDateInMinutes = thedate.time/60000
                    val selectedDateInHours = selectedDateInMinutes/60
                    val selectedDateInDays = selectedDateInHours/24

                    val currentdate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentdate.let {
                        val currentDateInMinutes = currentdate.time/60000
                        val currentDateInHours = currentDateInMinutes/60
                        val currentDateInDays = currentDateInHours/24

                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        val differenceInHours = currentDateInHours - selectedDateInHours
                        val differenceInDays = currentDateInDays - selectedDateInDays

                        tvAgeInMinutes?.text = differenceInMinutes.toString()
                        tvHours?.text = differenceInHours.toString()
                        tvDays?.text = differenceInDays.toString()
                    }

                }

            },
            year,
            month,
            day
        )

        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}