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
import com.google.firebase.database.*

class Timer : AppCompatActivity() {

    //Global variable
    private lateinit var selection : String
    //database1 = common resources firebase
    val database1 = FirebaseDatabase.getInstance("https://bait2123-202010-03.firebaseio.com/")
    //primary firebase : our firebase
    val database2: FirebaseDatabase = FirebaseDatabase.getInstance("https://solenoid-lock-f65e8.firebaseio.com/")
    val timeRef = database2.getReference("Room")
    //Write to common resources firebase
    val data1 = database1.getReference("PI_03_CONTROL")
    //Write to personal firebase
    val data2 = database2.getReference("PI_03_CONTROL")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        //Link UI to program
        val UItimer: TextView = findViewById(R.id.UItimer)

        //start of timer
        object : CountDownTimer(20000, 1000) {

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onTick(millisUntilFinished: Long) {
                UItimer.setText("RemainingTime : " + millisUntilFinished / 1000)
                //var timeleft = millisUntilFinished / 1000
                var notiTime = (millisUntilFinished / 1000).toString()
                if (notiTime== "10") {
                    createNotificationChannel()
                }
            }//end of onTick

            override fun onFinish() {
                UItimer.setText("Session Ended!")
                timeRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        selection = dataSnapshot.child("selection").value.toString()
                        when (selection) {
                            "1" -> {
                                timeRef.child("Room1").child("status").setValue("true")
                            }
                            "2" -> {
                                timeRef.child("Room2").child("status").setValue("true")
                            }
                            "3" -> {
                                timeRef.child("Room3").child("status").setValue("true")
                            }
                            "4" -> {
                                timeRef.child("Room4").child("status").setValue("true")
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        //Actions when failed to read data
                        UItimer.text = "Read failed."
                    }
                })//end of valueEventListener

                var lcdscr = "1"
                var lcdtxt = "****AVAILABLE****"
                var lcdbkR = "0"
                var lcdbkG = "20"
                var lcdbkB = "0"

                //setting the value at common resources
                data1.child("lcdscr").setValue(lcdscr)
                data1.child("lcdtxt").setValue(lcdtxt)
                data1.child("lcdbkR").setValue(lcdbkR)
                data1.child("lcdbkG").setValue(lcdbkG)
                data1.child("lcdbkB").setValue(lcdbkB)

                //for testing purpose
                //setting the value at personal database
                data2.child("lcdscr").setValue(lcdscr)
                data2.child("lcdtxt").setValue(lcdtxt)
                data2.child("lcdbkR").setValue(lcdbkR)
                data2.child("lcdbkG").setValue(lcdbkG)
                data2.child("lcdbkB").setValue(lcdbkB)
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
