package my.edu.tarc.smartroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //connect to personal firebase
        val database2 = FirebaseDatabase.getInstance("https://solenoid-lock-f65e8.firebaseio.com/")
        val loginRef: DatabaseReference = database2.getReference("UserProfile")

        val buttonLogin: Button = findViewById(R.id.buttonLogin)
        var email1: TextView = findViewById(R.id.editTextEmail)
        var password1: TextView = findViewById(R.id.editTextPassword)

        //start of login button
        buttonLogin.setOnClickListener() {
            val email = email1.text.toString()
            val password = password1.text.toString()
            var loginStatus = "true"

            //login validation
            if (email1.text.toString().isEmpty()) {
                email1.error = "Please enter your email"
                loginStatus = "false"
            }
            if (password1.text.toString().isEmpty()) {
                password1.error = "Password is required"
                loginStatus = "false"
            }
            loginRef.child("User1").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot)
                {
                    var defaultemail = dataSnapshot.child("email").value
                    var defaultpassword = dataSnapshot.child("password").value
                    if(email == defaultemail && password == defaultpassword)
                    {
                        loginStatus = "true"
                    }
                }
                override fun onCancelled(error: DatabaseError)
                {
                    // Actions when failed to read value
                    Toast.makeText(baseContext, "Load Failed. Please try again", Toast.LENGTH_SHORT).show()
                }
            })
            if (loginStatus == "true")
            {
                //Direct user to DisplayRoom page ig login is successful
                startActivity(Intent(this, DisplayUnlock::class.java))
            }
            else
            {
                // If sign in fails, display a message to the user.
                Toast.makeText(baseContext, "Login failed. Please try again", Toast.LENGTH_SHORT).show()
            }
        }//end of login button
    }//end of onCreate
}//end of class
