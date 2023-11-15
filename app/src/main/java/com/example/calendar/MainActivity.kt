package com.example.calendar

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import androidx.core.app.NotificationCompat
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private val friendBirthday = Calendar.getInstance()
    private val NORMAL_CHANNEL = "NORMAL_CHANNEL"
    private val eventDates: ArrayList<BDModel> = arrayListOf (
        BDModel(Calendar.getInstance().apply { set(2004, Calendar.JANUARY, 21) }, "Олег"),
        BDModel(Calendar.getInstance().apply { set(2004, Calendar.MARCH, 4) }, "Вася"),
        BDModel(Calendar.getInstance().apply { set(2003, Calendar.NOVEMBER, 16) }, "Петя"),
        BDModel(Calendar.getInstance().apply { set(2003, Calendar.JANUARY, 21)}, "Вова")
    )

    private lateinit var notificationManager : NotificationManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        calendarView = findViewById(R.id.calendarView)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            friendBirthday.set(year, month, dayOfMonth)

            val matchingEvents = eventDates.filter { it.calendar.get(Calendar.MONTH) == month && it.calendar.get(Calendar.DATE) == dayOfMonth }
            val list : ArrayList<String> = arrayListOf()
            if (matchingEvents.isNotEmpty()) {
                for (event in matchingEvents) {
                    list.add(event.name)
                    createNotification(list)
                }
            } else {
                deleteNotification()
            }
        }
    }



    private fun createNotification(names : ArrayList<String>) {
        val channel = NotificationChannel(
            NORMAL_CHANNEL,
            resources.getString(R.string.NOT_IMPORTANT_CHANNEL_NAME),
            NotificationManager.IMPORTANCE_LOW
        )
        channel.enableVibration(false)

        notificationManager.createNotificationChannel(channel)
        val nameList = names.joinToString(separator = ",")
        val notification = NotificationCompat.Builder(this, NORMAL_CHANNEL)
            .setContentTitle("Поздравьтe $nameList!")
            .setContentText("Сегодня день рождения вашего друга.")
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .build()

        notificationManager.notify(
            R.id.SIMPLE_NOTIFICATION_ID,
            notification
        )
    }



    private fun deleteNotification(){
        notificationManager.cancel(R.id.SIMPLE_NOTIFICATION_ID)
    }
}