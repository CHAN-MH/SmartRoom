package my.edu.tarc.smartroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Door : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solenoid_door)

        var code: String = "000000"

        //Accessing UI
        val psw: EditText = findViewById(R.id.password)
        val buttonProceed: Button = findViewById(R.id.buttonProceed)
        val textView:TextView = findViewById(R.id.textView)

        //database1 = common resources firebase
        val database1 = FirebaseDatabase.getInstance("https://bait2123-202010-03.firebaseio.com/")
        val Ref = database1.getReference("PI_03_CONTROL")


        //accessing the personal database
        val database2 = FirebaseDatabase.getInstance("https://solenoid-lock-f65e8.firebaseio.com/")
        var myRef = database2.getReference("Room")

        val doorfirebase = FirebaseDatabase.getInstance("https://bait2123-202010-05.firebaseio.com/")
        val relay1 = doorfirebase.getReference("PI_001").child("buzz");
        val relay2 = doorfirebase.getReference("PI_001").child("lock");

        //retrieving the pass code from firebase
        myRef.addValueEventListener(object : ValueEventListener {
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
                textView.text = "Sign In Function Failed"
            }
        })

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
                if(password != code){
                    psw.error = "Password does not match!";
                    psw.requestFocus();
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
                    val intent = Intent(this, Timer::class.java)
                    startActivity(intent)
                }
            }}//end of proceed button
    }//end of OnCreate
}//end of Class