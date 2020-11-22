package my.edu.tarc.smartroom

import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class Timer : AppCompatActivity() {
    //Global variable
    //var time = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        //Link UI to program
        val UItimer : TextView = findViewById(R.id.UItimer)

        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                UItimer.setText("seconds remaining: " + millisUntilFinished / 1000)
                var timeleft = (millisUntilFinished / 1000).toString()
                if (timeleft == "20")
                {
                    displayExtensionDialog()
                }
            }
            override fun onFinish() {
                UItimer.setText("Session Ended!")
            }
        }.start()

    }//end of onCreate

    private fun displayExtensionDialog() {
        // create an alert builder
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Extend Session")

        // set the custom layout
        val customLayout: View = layoutInflater.inflate(R.layout.dialog_extension, null);
        builder.setView(customLayout);
        builder.setMessage("Session about to end. Do you want to extend your session?")

        builder.apply {
            setPositiveButton(R.string.extend, DialogInterface.OnClickListener { dialog, _ ->
                //When user click extend
                //textViewExtension.text = "Succesfully extend session for another 30 minute"
                dialog.cancel()
            })
            setNegativeButton(R.string.nothx, DialogInterface.OnClickListener { dialog, _->
                //When user click No, thanks
                //.text = "Please evacuate the room before your session ends."
                dialog.cancel()
            })
        }

        // create and show the alert dialog
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }//end of dialog

}//end of class

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