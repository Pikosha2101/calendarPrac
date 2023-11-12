package com.example.calendar

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private val friendBirthday = Calendar.getInstance()
    private val NORMAL_CHANNEL = "NORMAL_CHANNEL"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //friendBirthday.set(2004, Calendar.MARCH, 4)
        friendBirthday.set(2004, Calendar.JANUARY, 21)
        calendarView = findViewById(R.id.calendarView)

        calendarView.setOnDateChangeListener { _, _, month, dayOfMonth ->
            if (dayOfMonth == friendBirthday.get(Calendar.DATE)
                && month == friendBirthday.get(Calendar.MONTH)){
                createNotification()
                Toast.makeText(this, "TExt", Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun createNotification() {
        val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

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
}