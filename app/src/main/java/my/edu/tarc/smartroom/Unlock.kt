package my.edu.tarc.smartroom

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

        //database1 = common resources firebase
        val database1 = FirebaseDatabase.getInstance("https://bait2123-202010-03.firebaseio.com/")
        val ref1 = database1.getReference("PI_03_CONTROL")

        //accessing the personal database
        val database2 = FirebaseDatabase.getInstance("https://solenoid-lock-f65e8.firebaseio.com/")
        var ref2 = database2.getReference("Room")

        //Initializing variable values
        var code: String = "000000"

        //retrieving the pass code from firebase
        ref2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //Retrieve user room selection from firebase
                var selection = dataSnapshot.child("selection").value.toString();
                //check for retrieving which room
                if (selection == "1") {
                    //storing the correct password into code variable
                    code = dataSnapshot.child("Room1").child("code").value.toString();
                }
                else if (selection == "2") {
                    //storing the correct password into code variable
                    code = dataSnapshot.child("Room2").child("code").value.toString();
                }
                else if (selection == "3") {
                    //storing the correct password into code variable
                    code = dataSnapshot.child("Room3").child("code").value.toString();
                }
                else if (selection == "4") {
                    //storing the correct password into code variable
                    code = dataSnapshot.child("Room4").child("code").value.toString();
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
                    ref1.child("relay1").setValue("1")
                    ref1.child("relay2").setValue("1")
                    Thread.sleep(5000)
                    ref1.child("relay1").setValue("0")
                    ref1.child("relay2").setValue("0")
                }
            }}
    }//end of onCreate
}//end of Class