package de.uni.hannover.hci.mi.team6.covidcheckin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    fun openRestaurantActivity(v: View) {
        startActivity(Intent(this, EnterRestaurantActivity::class.java))
    }

}
