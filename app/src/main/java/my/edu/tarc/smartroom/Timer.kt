package my.edu.tarc.smartroom

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import my.edu.tarc.smartroom.R

class Timer : AppCompatActivity() {
    //Global variables
    //var START_MILLI_SECONDS = 60000L
    //lateinit var countdown_timer: CountDownTimer
    //var isRunning: Boolean = false;
    //var time_in_milli_seconds = 0L
    var time = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        //Link UI to program
        val UItimer : TextView = findViewById(R.id.UItimer)

        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                UItimer.setText("Time remaining: " + millisUntilFinished / 1000 + "sec")
            }

            override fun onFinish() {
                UItimer.setText("Session End")
            }
        }.start()

    }  //end of onCreate
} //end of class

/*
    //---------------------------------------------------------------
    //passing startTimer through intent
        var timer: String? = intent.getStringExtra("timer")

        if (timer != null) {
            if (timer.equals(1)) {
                object : CountDownTimer(50000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        UItimer.setText(java.lang.String.valueOf(time))
                        time--
                    }

                    override fun onFinish() {
                        UItimer.setText("Session Ends. Do you want to extend?")
                    }
                }.start()

            }
        }//end of if statement
*/