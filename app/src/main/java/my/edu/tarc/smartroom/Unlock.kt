package my.edu.tarc.smartroom

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Unlock: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unlock)

        //Link UI to program
        val doorPin : EditText = findViewById(R.id.editTextPIN)
        val buttonUnlock : Button = findViewById(R.id.buttonUnlock)
        val textViewDisplayRoomNum : TextView = findViewById(R.id.textViewDisplayRoomNum)

        //database1 = common resources firebase
        val database1 = FirebaseDatabase.getInstance("https://bait2123-202010-03.firebaseio.com/")
        val ref1 = database1.getReference("PI_03_CONTROL")

        //accessing the personal database
        val database2 = FirebaseDatabase.getInstance("https://solenoid-lock-f65e8.firebaseio.com/")
        var ref2 = database2.getReference("Room")

        //Initializing variable values
        var code: String = "000000"

        //Retrieving value from intent
        val roomChoosen = intent.getStringExtra("roomChoosen")

        val secondary = FirebaseDatabase.getInstance("https://bait2123-202010-05.firebaseio.com/")
        val relay1 = secondary.getReference("PI_001").child("buzz");
        val relay2 = secondary.getReference("PI_001").child("lock");

        //retrieving the pass code from firebase
        ref2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //Retrieve user room selection from firebase
                //var selection = dataSnapshot.child("selection").value.toString();
                //check for retrieving which room
                if (roomChoosen == "1") {
                    //storing the correct password into code variable
                    code = dataSnapshot.child("Room1").child("code").value.toString()
                    textViewDisplayRoomNum.text = "Room No : R01"
                }
                else if (roomChoosen == "2") {
                    //storing the correct password into code variable
                    code = dataSnapshot.child("Room2").child("code").value.toString()
                    textViewDisplayRoomNum.text = "Room No : R02"
                }
                else if (roomChoosen == "3") {
                    //storing the correct password into code variable
                    code = dataSnapshot.child("Room3").child("code").value.toString()
                    textViewDisplayRoomNum.text = "Room No : R03"
                }
                else if (roomChoosen == "4") {
                    //storing the correct password into code variable
                    code = dataSnapshot.child("Room4").child("code").value.toString()
                    textViewDisplayRoomNum.text = "Room No : R04"
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext,"Loading Failed. PLease try again.", Toast.LENGTH_SHORT).show()
            }
        })

        buttonUnlock.setOnClickListener {
            //assigning user input to a variable
            val pin = doorPin.text.toString().trim()
            if(pin.isEmpty()){
                doorPin.error = "Password is required!";
                doorPin.requestFocus();
            }
            else if(pin.length < 6){
                doorPin.error = "Password must not less than 6 digit!";
                doorPin.requestFocus();
            }
            else{
                if(pin != code){
                    doorPin.error = "Password does not match!";
                    doorPin.requestFocus();
                }
                else{
                    Toast.makeText(baseContext, "Door Unlocked!!YAY", Toast.LENGTH_SHORT).show()
                    // reaction when door is unlocked
                    object : CountDownTimer(10000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            relay1.setValue("1")
                            relay2.setValue("1")
                        }

                        override fun onFinish() {
                            relay1.setValue("0")
                            relay2.setValue("0")
                        }
                    }.start()
                }
            }}
    }//end of onCreate
}//end of Class