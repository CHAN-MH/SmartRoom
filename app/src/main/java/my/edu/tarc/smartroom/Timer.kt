package my.edu.tarc.smartroom

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class Timer : AppCompatActivity() {

    //Global variable
    var extend: String = "true"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        //Link UI to program
        val UItimer: TextView = findViewById(R.id.UItimer)

        //start of timer
        object : CountDownTimer(30000, 1000) {

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onTick(millisUntilFinished: Long) {
                UItimer.setText("seconds remaining: " + millisUntilFinished / 1000)
                //var timeleft = millisUntilFinished / 1000
                var notiTime = (millisUntilFinished / 1000).toString()
                if (notiTime== "20") {
                    createNotificationChannel()
                }

            }//end of onTick

            override fun onFinish() {
                UItimer.setText("Session Ended!")
            }//end of onFinish
        }.start()//end of timer


    }//end of onCreate

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.mychannel)
            val descriptionText = getString(R.string.channeldesc)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("1234", name, importance).apply {
                description = descriptionText
            }
            //Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        //Send notification to user when session about to end
        val builder = NotificationCompat.Builder(this, "1234")
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Session Status")
            .setContentText("Session about to end")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Session ending in 20 minutes. Kindly evacuate the room on time.")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notificationId = 5678
        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
    }

}//end of class
