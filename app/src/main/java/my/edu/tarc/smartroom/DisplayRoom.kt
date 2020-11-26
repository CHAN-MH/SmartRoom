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

    //connect to common resources
    val database1 = FirebaseDatabase.getInstance("https://bait2123-202010-03.firebaseio.com/")
    var comRef = database1.getReference("PI_03_CONTROL")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_room)

        //connect to personal firebase
        val database2: FirebaseDatabase = FirebaseDatabase.getInstance("https://solenoid-lock-f65e8.firebaseio.com/")
        val myref: DatabaseReference = database2.getReference("Room")
        var selectRef = database2.getReference("Room").child("selection")
        var yjSelectRef = database2.getReference("PI_03_CONTROL").child("selection")

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
                yjSelectRef.setValue("1")
                selectRef.setValue("1")
                startActivity(intent)
            }
            else
            {
                displayOccupiedDialog()
                displayToast()
            }
        }
        textViewRoom2.setOnClickListener() {
            //1 = available, 0 = occupied
            val intent = Intent(this, Reservation::class.java)
            if(a2 == 1)
            {
                yjSelectRef.setValue("2")
                selectRef.setValue("2")
                startActivity(intent)

            }
            else
            {
                displayOccupiedDialog()
                displayToast()
            }
        }
        textViewRoom3.setOnClickListener() {
            //1 = available, 0 = occupied
            val intent = Intent(this, Reservation::class.java)
            if(a3 == 1)
            {
                yjSelectRef.setValue("3")
                selectRef.setValue("3")
                startActivity(intent)
            }
            else
            {
                displayOccupiedDialog()
                displayToast()
            }
        }
        textViewRoom4.setOnClickListener() {
            //1 = available, 0 = occupied
            val intent = Intent(this, Reservation::class.java)
            if(a4 == 1)
            {
                yjSelectRef.setValue("4")
                selectRef.setValue("4")
                startActivity(intent)
            }
            else
            {
                displayOccupiedDialog()
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

    private fun displayOccupiedDialog() {
        // create an alert builder
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Room Occupied")
        //setting the value at common resources to display occupied at lcd
        var lcdscr = "1"
        var lcdtxt = "****OCCUPIED****"
        var lcdbkR = "20"
        var lcdbkG = "0"
        var lcdbkB = "0"
        comRef.child("lcdscr").setValue(lcdscr)
        comRef.child("lcdtxt").setValue(lcdtxt)
        comRef.child("lcdbkR").setValue(lcdbkR)
        comRef.child("lcdbkG").setValue(lcdbkG)
        comRef.child("lcdbkB").setValue(lcdbkB)
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
                    setResources()
                }

        // create and show the alert dialog
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }//end of dialog

    private fun setResources() {
        //setting the value at common resources to display available after user close dialog
        var lcdscr = "1"
        var lcdtxt = "****AVAILABLE***"
        var lcdbkR = "0"
        var lcdbkG = "20"
        var lcdbkB = "0"
        comRef.child("lcdscr").setValue(lcdscr)
        comRef.child("lcdtxt").setValue(lcdtxt)
        comRef.child("lcdbkR").setValue(lcdbkR)
        comRef.child("lcdbkG").setValue(lcdbkG)
        comRef.child("lcdbkB").setValue(lcdbkB)
    }
}