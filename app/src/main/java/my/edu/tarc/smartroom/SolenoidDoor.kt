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

lateinit var code:String
class SolenoidDoor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solenoid_door)

        //passing the user choice through activity
        var selection: String? = intent.getStringExtra("selection")

        //Accessing UI
        val psw: EditText = findViewById(R.id.password)
        val buttonProceed: Button = findViewById(R.id.buttonProceed)

        //accessing the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Room")

        //retrieving the pass code from firebase
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //check for retrieving which room
                when (selection) {
                    "1" -> {
                        //storing the correct password into code variable
                        code = dataSnapshot.child("Room1").child("code").value.toString();
                    }
                    "2" -> {
                        //storing the correct password into code variable
                        code = dataSnapshot.child("Room2").child("code").value.toString();
                    }
                    "3" -> {
                        //storing the correct password into code variable
                        code = dataSnapshot.child("Room3").child("code").value.toString();
                    }
                    "4" -> {
                        //storing the correct password into code variable
                        code = dataSnapshot.child("Room4").child("code").value.toString();
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                val textView:TextView = findViewById(R.id.textView)
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
                //do password checking
                if(password == code){
                    //door open
                    Toast.makeText(baseContext, "Door Unlocked!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Timer::class.java)
                    startActivity(intent)
                }
                else{
                    psw.error = "Password does not match!";
                    psw.requestFocus();
                }

            }}

    }
}