package my.edu.tarc.smartroom

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.database.*

class DisplayRoom : AppCompatActivity() {
    var roomNo : String = ""
    var status : Boolean = true
    lateinit var dialog : Dialog
    lateinit var dataSnapshot : DataSnapshot
    var a1 : Int = 1
    var a2 : Int = 1
    var a3 : Int = 1
    var a4 : Int = 1
    //internal val intent = Intent(this, Reservation::class.java)
    var selection : String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_room)


        //secondary firebase : sir firebase
        val database1 = FirebaseDatabase.getInstance("https://bait2123-202010-03.firebaseio.com/")

        //primary firebase : our firebase
        val database2: FirebaseDatabase = FirebaseDatabase.getInstance("https://solenoid-lock-f65e8.firebaseio.com/")
        val myref: DatabaseReference = database2.getReference("Room")

        //Link UI to program
        val textViewRoom1: TextView = findViewById(R.id.textViewRoom1)
        val textViewRoom2: TextView = findViewById(R.id.textViewRoom2)
        val textViewRoom3: TextView = findViewById(R.id.textViewRoom3)
        val textViewRoom4: TextView = findViewById(R.id.textViewRoom4)


        //displaying the rooms status(available or occupied)
        myref.child("Room1").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                roomNo = dataSnapshot.child("roomNo").value.toString()
                textViewRoom1.text = roomNo
                status = dataSnapshot.child("status").value.toString().toBoolean()
                if (status) {
                    textViewRoom1.setBackgroundResource(R.color.available_colour)
                    a1 = 1
                } else {
                    textViewRoom1.setBackgroundResource(R.color.occupied_colour)
                    a1 = 0
                }

            }

            override fun onCancelled(error: DatabaseError) {
                // Actions when failed to read value
                textViewRoom1.text = "Read Failed"
            }
        })

        myref.child("Room2").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                roomNo = dataSnapshot.child("roomNo").value.toString()
                textViewRoom2.text = roomNo
                status = dataSnapshot.child("status").value.toString().toBoolean()
                if (status) {
                    textViewRoom2.setBackgroundResource(R.color.available_colour)
                    a2 = 1
                } else {
                    textViewRoom2.setBackgroundResource(R.color.occupied_colour)
                    a2 = 0
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Actions when failed to read value
                textViewRoom2.text = "Read Failed"
            }
        })

        myref.child("Room3").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                roomNo = dataSnapshot.child("roomNo").value.toString()
                textViewRoom3.text = roomNo
                status = dataSnapshot.child("status").value.toString().toBoolean()
                if (status) {
                    textViewRoom3.setBackgroundResource(R.color.available_colour)
                    a3 = 1
                } else {
                    textViewRoom3.setBackgroundResource(R.color.occupied_colour)
                    a3 = 0
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Actions when failed to read value
                textViewRoom3.text = "Read Failed"
            }
        })

        myref.child("Room4").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                roomNo = dataSnapshot.child("roomNo").value.toString()
                textViewRoom4.text = roomNo
                status = dataSnapshot.child("status").value.toString().toBoolean()
                if (status) {
                    textViewRoom4.setBackgroundResource(R.color.available_colour)
                    a4 = 1
                } else {
                    textViewRoom4.setBackgroundResource(R.color.occupied_colour)
                    a4 = 0
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Actions when failed to read value
                textViewRoom4.text = "Read Failed"
            }
        })

        //setting onCLickListener to direct user to another page if clicked
        textViewRoom1.setOnClickListener() {
            //1 = available, 0 = occupied
            val intent = Intent(this, Reservation::class.java)
            if(a1 == 1)
            {
                selection = "1"
                intent.putExtra("selection", selection)
                startActivity(intent)
            }
            else
            {
                //displayOccupiedDialog()
                displayToast()
            }
        }
        textViewRoom2.setOnClickListener() {
            //1 = available, 0 = occupied
            val intent = Intent(this, Reservation::class.java)
            if(a2 == 1)
            {
                selection = "2"
                intent.putExtra("selection", selection)
                startActivity(intent)

            }
            else
            {
                //displayOccupiedDialog()
                displayToast()
            }
        }
        textViewRoom3.setOnClickListener() {
            //1 = available, 0 = occupied
            val intent = Intent(this, Reservation::class.java)
            if(a3 == 1)
            {
                selection = "3"
                intent.putExtra("selection", selection)
                startActivity(intent)
            }
            else
            {
                //displayOccupiedDialog()
                displayToast()
            }
        }
        textViewRoom4.setOnClickListener() {
            //1 = available, 0 = occupied
            val intent = Intent(this, Reservation::class.java)
            if(a4 == 1)
            {
                selection = "4"
                intent.putExtra("selection", selection)
                startActivity(intent)
            }
            else
            {
                //displayOccupiedDialog()
                displayToast()
            }
        }
    }//end of onCreate

    private fun displayToast() {
        val text = "Room Occupied!"
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()
    }

    /*
    private fun displayOccupiedDialog() {
        // create an alert builder
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Room Occupied")

        // set the custom layout
        val customLayout: View = layoutInflater.inflate(R.layout.dialog_occupied, null);
        builder.setView(customLayout);
        builder.setMessage("Please proceed to other rooms available")

        builder
                .setPositiveButton(
                        "Noted"
                ) { dialog, _ -> // When the user click yes button
                    // then app will close
                    dialog.cancel()
                }

        // create and show the alert dialog
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    */
}