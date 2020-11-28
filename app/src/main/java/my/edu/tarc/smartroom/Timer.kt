package my.edu.tarc.smartroom

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.database.*

class Timer : AppCompatActivity() {

    //Global variable
    private lateinit var selection: String

    //database1 = common resources firebase
    val database1 = FirebaseDatabase.getInstance("https://bait2123-202010-03.firebaseio.com/")

    //database 2 = personal firebase
    val database2: FirebaseDatabase = FirebaseDatabase.getInstance("https://solenoid-lock-f65e8.firebaseio.com/")

    //Write to common resources firebase
    val data1 = database1.getReference("PI_03_CONTROL")

    //Write to personal firebase
    val data2 = database2.getReference("PI_03_CONTROL")
    val timeRef = database2.getReference("Room")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        //Link UI to program
        val UItimer: TextView = findViewById(R.id.UItimer)

        //here is what i chg
        //read from database
        timeRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                selection = dataSnapshot.child("selection").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                //Actions when failed to read data
                UItimer.text = "Read failed."
            }
        })//end of valueEventListener

        //start of timer
        object : CountDownTimer(30000, 1000) {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onTick(millisUntilFinished: Long) {
                UItimer.setText("RemainingTime : " + millisUntilFinished / 1000 + " min")
                var timeLeft = millisUntilFinished / 1000
                //Write Timer value to database
                timeRef.child("Timer").setValue(timeLeft)
                var notiTime = (millisUntilFinished / 1000).toString()
                if (notiTime == "20") {
                    AbtEndNotiChannel()
                }
            }//end of onTick

            override fun onFinish() {
                UItimer.setText("Session Ended!")
                EndNotiChannel()
                when (selection) {
                    "1" -> {
                        timeRef.child("Room1").child("status").setValue("true")
                        timeRef.child("Room1").child("code").setValue("000000")
                    }
                    "2" -> {
                        timeRef.child("Room2").child("status").setValue("true")
                        timeRef.child("Room2").child("code").setValue("000000")
                    }
                    "3" -> {
                        timeRef.child("Room3").child("status").setValue("true")
                        timeRef.child("Room3").child("code").setValue("000000")
                    }
                    "4" -> {
                        timeRef.child("Room4").child("status").setValue("true")
                        timeRef.child("Room4").child("code").setValue("000000")
                    }
                }

                var lcdscr = "1"
                var lcdtxt = "****AVAILABLE***"
                var lcdbkR = "0"
                var lcdbkG = "20"
                var lcdbkB = "0"
                var buzzer = "1"

                //setting the value at common resources
                data1.child("lcdscr").setValue(lcdscr)
                data1.child("lcdtxt").setValue(lcdtxt)
                data1.child("lcdbkR").setValue(lcdbkR)
                data1.child("lcdbkG").setValue(lcdbkG)
                data1.child("lcdbkB").setValue(lcdbkB)
                data1.child("buzzer").setValue(buzzer)

                //setting the value at personal database
                data2.child("lcdscr").setValue(lcdscr)
                data2.child("lcdtxt").setValue(lcdtxt)
                data2.child("lcdbkR").setValue(lcdbkR)
                data2.child("lcdbkG").setValue(lcdbkG)
                data2.child("lcdbkB").setValue(lcdbkB)
                data2.child("buzzer").setValue(buzzer)

                //Let the buzzer sound for 1 second then switch off
                data1.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        var reading = dataSnapshot.child("buzzer").value.toString()
                        if (reading == "1") {
                            val handler = Handler()
                            handler.postDelayed({   //Delay the real-time update
                                data1.child("buzzer").setValue("0")
                            }, 2000)
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.i("Error", "Read failed")
                    }
                })
            }//end of onFinish
        }.start()//end of timer

    }//end of onCreate

    @RequiresApi(Build.VERSION_CODES.O)
    private fun AbtEndNotiChannel() {
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
        val builderAbtEnd = NotificationCompat.Builder(this, "1234")
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Session Status")
            .setContentText("Session ending in 20 minutes")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Session ending in 20 minutes. Kindly evacuate the room on time.")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notificationId = 5678
        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builderAbtEnd.build())
        }
    }//end of AbtEndNotiChannel function

    private fun EndNotiChannel() {
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
        val builderEnded = NotificationCompat.Builder(this, "1234")
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Session Status")
            .setContentText("Session Ended")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Session ending in 20 minutes. Kindly evacuate the room on time.")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notificationId = 8765
        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builderEnded.build())
        }
    }
}//end of class
