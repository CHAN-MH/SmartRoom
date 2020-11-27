package my.edu.tarc.smartroom

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class DisplayUnlock: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_unlock)

        //Link UI to program
        val cardViewDisplayRoom: CardView = findViewById(R.id.cardViewDisplayRoom)
        val cardViewUnlockDoor: CardView = findViewById(R.id.cardViewUnlockDoor)

        //setting onClickListener
        cardViewDisplayRoom.setOnClickListener() {
            startActivity(Intent(this, DisplayRoom::class.java))
        }
        cardViewUnlockDoor.setOnClickListener() {
            startActivity(Intent(this, UnlockWhichDoor::class.java))
        }
    }
}