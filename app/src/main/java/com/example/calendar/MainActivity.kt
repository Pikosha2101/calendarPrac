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
    private val eventDates: List<Calendar> = listOf(
        Calendar.getInstance().apply { set(2004, Calendar.JANUARY, 21) },
        Calendar.getInstance().apply { set(2004, Calendar.MARCH, 4) },
        Calendar.getInstance().apply { set(2003, Calendar.NOVEMBER, 16) },
    )
    private lateinit var notificationManager : NotificationManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        calendarView = findViewById(R.id.calendarView)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            friendBirthday.set(year, month, dayOfMonth)

            if (eventDates.any {it.get(Calendar.MONTH) == month && it.get(Calendar.DATE) == dayOfMonth}){
                createNotification()
            } else {
                deleteNotification()
            }
        }
    }



    private fun createNotification() {
        val channel = NotificationChannel(
            NORMAL_CHANNEL,
            resources.getString(R.string.NOT_IMPORTANT_CHANNEL_NAME),
            NotificationManager.IMPORTANCE_LOW
        )
        channel.enableVibration(false)

        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, NORMAL_CHANNEL)
            .setContentTitle("Поздравьте друга!")
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