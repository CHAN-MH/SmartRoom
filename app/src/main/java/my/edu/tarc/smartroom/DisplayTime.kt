package my.edu.tarc.smartroom

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DisplayTime: AppCompatActivity() {
    //Global variable
    private lateinit var timeLeft : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_time)

        //database 2 = personal firebase
        val database2: FirebaseDatabase = FirebaseDatabase.getInstance("https://solenoid-lock-f65e8.firebaseio.com/")
        val timeLeftRef = database2.getReference("Room")

        //Link UI to program
        val textViewTimeLeft: TextView = findViewById(R.id.textViewTimeLeft)


        //Retrieve value from database
        timeLeftRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                timeLeft = dataSnapshot.child("Timer").value.toString()
                textViewTimeLeft.text = "Remaining Time : " + timeLeft + " mins"
                if (timeLeft == "0")
                {
                    textViewTimeLeft.text = "Session Ended"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //Actions when failed to read data
                textViewTimeLeft.text = "Read failed."
            }
        })

    }
}