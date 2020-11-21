package my.edu.tarc.smartroom

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class Reservation : AppCompatActivity() {

    //database1 = common resources firebase
    val database1 = FirebaseDatabase.getInstance("https://bait2123-202010-03.firebaseio.com/")
    //database2 = personal firebase
    val database2: FirebaseDatabase = FirebaseDatabase.getInstance("https://solenoid-lock-f65e8.firebaseio.com/")

    //Write to common resources firebase
    val data1 = database1.getReference("PI_03_CONTROL")
    //Write to personal firebase
    val data2 = database2.getReference("bait2123-202010-03").child("PI_03_CONTROL")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        val roomRef: DatabaseReference = database2.getReference("Room")

        //accessingUI element
        val reserveButton: Button = findViewById(R.id.buttonReserve)
        val codeOTP: TextView = findViewById(R.id.code)
        val textViewRoomNo : TextView = findViewById(R.id.textViewRoomNo)
        val textViewNoOfPax : TextView = findViewById(R.id.textViewNoOfPax)
        //initialize code variable
        var code:Int = 0 ;
        var pcode:String ="";


        //passing user's room selection through intent
        var selection: String? = intent.getStringExtra("selection")

        roomRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (selection == "1")
                {
                    var roomNo:String = dataSnapshot.child("Room1").child("roomNo").getValue().toString()
                    var noOfPax:String = dataSnapshot.child("Room1").child("noOfPax").getValue().toString()
                    textViewRoomNo.text = roomNo
                    textViewNoOfPax.text = noOfPax
                }
                else if(selection == "2")
                {
                    var roomNo:String = dataSnapshot.child("Room2").child("roomNo").getValue().toString()
                    var noOfPax:String = dataSnapshot.child("Room2").child("noOfPax").getValue().toString()
                    textViewRoomNo.text = roomNo
                    textViewNoOfPax.text = noOfPax
                }
                else if(selection == "3")
                {
                    var roomNo:String = dataSnapshot.child("Room3").child("roomNo").getValue().toString()
                    var noOfPax:String = dataSnapshot.child("Room3").child("noOfPax").getValue().toString()
                    textViewRoomNo.text = roomNo
                    textViewNoOfPax.text = noOfPax
                }
                else if(selection == "4")
                {
                    var roomNo:String = dataSnapshot.child("Room4").child("roomNo").getValue().toString()
                    var noOfPax:String = dataSnapshot.child("Room4").child("noOfPax").getValue().toString()
                    textViewRoomNo.text = roomNo
                    textViewNoOfPax.text = noOfPax
                }
            }
            override fun onCancelled(error: DatabaseError) {
                //Actions when failed to read data
                textViewRoomNo.text = "Read Failed"
                textViewNoOfPax.text = "Read Failed"
            }
        })


        reserveButton.setOnClickListener {
            var lcdscr = "1"
            var lcdtxt = "**!!OCCUPIED!!**"
            var lcdbkR = "200"
            var lcdbkG = "0"
            var lcdbkB = "0"

            //setting the value at common resources
            data1.child("lcdscr").setValue(lcdscr)
            data1.child("lcdtxt").setValue(lcdtxt)
            data1.child("lcdbkR").setValue(lcdbkR)
            data1.child("lcdbkG").setValue(lcdbkG)
            data1.child("lcdbkB").setValue(lcdbkB)

            //for testing purpose
            //setting the value at personal database
            data2.child("lcdscr").setValue(lcdscr)
            data2.child("lcdtxt").setValue(lcdtxt)
            data2.child("lcdbkR").setValue(lcdbkR)
            data2.child("lcdbkG").setValue(lcdbkG)
            data2.child("lcdbkB").setValue(lcdbkB)
            //allow user to press once only
            reserveButton.isEnabled = false;

            //random function fo 6 digit pin
            code = (Math.random()*1000000).toInt()
            pcode = String.format("%06d", code);
            codeOTP.text = "Your room pin is $pcode"

            //check for room selection
            if(selection == "1"){
                //room1 is selected, write to room 1 firebase code
                val codePin = database2.getReference("Room").child("Room1").child("code")
                codePin.setValue(pcode)
            }
            else if(selection == "2"){
                //room1 is selected, write to room 1 firebase code
                val codePin = database2.getReference("Room").child("Room2").child("code")
                codePin.setValue(pcode)
            }
            else if(selection == "3"){
                //room1 is selected, write to room 1 firebase code
                val codePin = database2.getReference("Room").child("Room3").child("code")
                codePin.setValue(pcode)
            }
            else{
                //room1 is selected, write to room 1 firebase code
                val codePin = database2.getReference("Room").child("Room4").child("code")
                codePin.setValue(pcode)
            }

            //changing message in the button
            reserveButton.text = "SUCCESSFULLY BOOKED"



            //changing the room status to occupied in firebase
            if (selection == "1") {
                roomRef.child("Room1").child("status").setValue("false")

            } else if (selection == "2") {
                roomRef.child("Room2").child("status").setValue("false")

            } else if (selection == "3") {
                roomRef.child("Room3").child("status").setValue("false")

            } else if (selection == "4") {
                roomRef.child("Room4").child("status").setValue("false")

            }
        }

        //open the door action
        val door: TextView = findViewById(R.id.openthedoor)
        door.setOnClickListener {
            startActivity(Intent(this, SolenoidDoor::class.java))
        }
    }
}