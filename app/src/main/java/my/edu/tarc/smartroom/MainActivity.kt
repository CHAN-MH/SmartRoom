package my.edu.tarc.smartroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //connect to common resources firebase
        //val database1 = FirebaseDatabase.getInstance("https://bait2123-202010-03.firebaseio.com/")

        //connect to personal firebase
        //val database2 = FirebaseDatabase.getInstance("https://solenoid-lock-f65e8.firebaseio.com/")

        //connect to personal database for authentication
        //var auth: FirebaseAuth = FirebaseAuth.getInstance()

        val buttonLogin: Button = findViewById(R.id.buttonLogin)
        var email1: TextView = findViewById(R.id.editTextEmail)
        var password1: TextView = findViewById(R.id.editTextPassword)
        var defaultemail : String = "imposter@gmail.com"
        var defaultpsw : String = "123456"

        //start of login button
        buttonLogin.setOnClickListener() {
            val email = email1.text.toString()
            val password = password1.text.toString()
            var loginStatus = "true"
/*
            //login validation
            if (email1.text.toString().isEmpty()) {
                email1.error = "Please enter your email"
                loginStatus = "false"
            }
            if (password1.text.toString().isEmpty()) {
                password1.error = "Password is required"
                loginStatus = "false"
            }
 */
            if (loginStatus == "true") {
                if (email == defaultemail && password == defaultpsw){
                //auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                    //if (task.isSuccessful) {
                        // Sign in success direct user to reservation page
                        //Log.d("success","success")
                        startActivity(Intent(this, DisplayRoom::class.java))}
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Login failed. Please try again", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }//end of login button
    //}
//}