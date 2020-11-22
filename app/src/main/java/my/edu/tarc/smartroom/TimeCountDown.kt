package my.edu.tarc.smartroom

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import my.edu.tarc.smartroom.R

class TimeCountDown : AppCompatActivity() {
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
    }  //end of onCreate
} //end of class

/*
fun pauseTimer() {
    countdown_timer.cancel()
    isRunning = false
    buttonExtend.visibility = View.VISIBLE
}

fun startTimer(time_in_seconds: Long) {
    countdown_timer = object : CountDownTimer(time_in_seconds, 1000) {
        override fun onFinish() {
            //extendDialog()
        }
        override fun onTick(p0: Long) {
            time_in_milli_seconds = p0
            updateTextUI()
        }
    }
    countdown_timer.start()

    isRunning = true
    buttonExtend.visibility = View.INVISIBLE
}

fun resetTimer() {
    time_in_milli_seconds = START_MILLI_SECONDS
    updateTextUI()
    buttonExtend.visibility = View.INVISIBLE
}

fun updateTextUI() {
    val minute = (time_in_milli_seconds / 1000) / 60
    val seconds = (time_in_milli_seconds / 1000) % 60

    UItimer.text = "$minute:$seconds"
*/