package my.edu.tarc.smartroom

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UnlockWhichDoor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unlock_which_door)

        //connect to personal firebase
        val database2: FirebaseDatabase = FirebaseDatabase.getInstance("https://solenoid-lock-f65e8.firebaseio.com/")
        val myref: DatabaseReference = database2.getReference("Room")
        var selectRef = database2.getReference("Room").child("selection")
        var yjSelectRef = database2.getReference("PI_03_CONTROL").child("selection")

        //Link UI to program
        val textViewRoom1: TextView = findViewById(R.id.door1)
        val textViewRoom2: TextView = findViewById(R.id.door2)
        val textViewRoom3: TextView = findViewById(R.id.door3)
        val textViewRoom4: TextView = findViewById(R.id.door4)

        //starting the new intent
        val i = Intent(applicationContext, Unlock::class.java)

        //pass the room selection
        textViewRoom1.setOnClickListener() {
            i.putExtra("roomChoosen", "1")
            startActivity(i)
        }
        textViewRoom2.setOnClickListener() {
            i.putExtra("roomChoosen", "2")
            startActivity(i)
        }
        textViewRoom3.setOnClickListener() {
            i.putExtra("roomChoosen", "3")
            startActivity(i)
        }
        textViewRoom4.setOnClickListener() {
            i.putExtra("roomChoosen", "4")
            startActivity(i)
        }

    }
}