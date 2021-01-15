package de.uni.hannover.hci.mi.team6.covidcheckin.checkin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import kotlinx.android.synthetic.visitor.checkin_success_layout.*

class CheckinSuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.checkin_success_layout)

        // done button listener
        btn_done.setOnClickListener {
            val toast = Toast.makeText(this, "Fertig button clicked", Toast.LENGTH_SHORT)
            toast.show()
        }

        val name = intent.getStringExtra("DEVICE_NAME")
        Log.d("CheckSuccess", "Check")
        Log.d("CheckSuccess", "" + name)

        tv_restaurant_name.text = name;
    }
}
