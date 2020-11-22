package my.edu.tarc.smartroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

lateinit var codePIN: String
class SolenoidDoor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solenoid_door)

        //Accessing UI
        val psw: EditText = findViewById(R.id.password)
        val buttonProceed: Button = findViewById(R.id.buttonProceed)
        val textView:TextView = findViewById(R.id.textView)

        //passing the user choice through activity
        var selection: String? = intent.getStringExtra("selection")

        //check user selection and read from database
        //initialize user selection as ntg
        var roomNoSelected:String ?=null //door open
        if (selection == "1") {
            roomNoSelected == "Room1"
        }
        else if (selection == "2") {
            roomNoSelected == "Room2"
        }
        else if (selection == "3") {
            roomNoSelected == "Room3"
        }
        else if (selection == "4") {
            roomNoSelected == "Room4"
        }

        //accessing the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Room").child(roomNoSelected.toString()).child("code")

        //retrieving the pass code from firebase
        myRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                codePIN = dataSnapshot.value.toString();
            }

            override fun onCancelled(error: DatabaseError) {
                textView.text = "Read Failed. Have a nice day"
            }
        })

        //checking psw
        buttonProceed.setOnClickListener {
            //assigning user input to a variable
            val password:String = psw.text.toString().trim();
            if(password.isEmpty()){
                psw.error = "Password is required!";
                psw.requestFocus();
            }

            else if(password.length < 6){
                psw.error = "Password must not less than 6 digit!";
                psw.requestFocus();
            }
            else{
                //do password checking
                if(password == codePIN){
                    openTheDoor()
                }
                else{
                    psw.error = "Password does not match!";
                    psw.requestFocus();
                }

            }}

    }

    private fun openTheDoor() {
        //door open
        Toast.makeText(baseContext, "Door Unlocked!", Toast.LENGTH_SHORT).show()
        Thread.sleep(5_000)
        startActivity(Intent(this, Timer::class.java))
    }


}