package de.uni.hannover.hci.mi.team6.covidcheckin.enterRestaurant

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.BluetoothActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.transfer.BluetoothDataSender
import de.uni.hannover.hci.mi.team6.covidcheckin.checkin.CheckinSuccessActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.services.ServicesModule
import kotlinx.android.synthetic.visitor.enter_restaurant_activity.*

/**
 * Activity that shows the screen where the User can accept or deny transfer of his UserData to the restaurant
 * @author Elias
 */
class EnterRestaurantActivity : AppCompatActivity() {
    private lateinit var restaurantInfoView: TextView
    private lateinit var userPersonalDataView: TextView
    private lateinit var yesButton: Button
    private lateinit var noButton: Button
    private lateinit var infoButton: Button
    private lateinit var restaurantName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.enter_restaurant_activity)

        restaurantInfoView = findViewById(R.id.restaurantInfoView)
        userPersonalDataView = findViewById(R.id.userPersonalDataView)
        yesButton = findViewById(R.id.yes_button)
        noButton = findViewById(R.id.no_button)
        infoButton = findViewById(R.id.infoButton)

        infoButton.setOnClickListener {
            val t = Toast.makeText(
                this,
                "Your personal Data will be send directly to the Restaurant over Bluetooth.",
                Toast.LENGTH_LONG
            )
            t.setGravity(
                Gravity.CENTER,
                0,
                -500
            ) //TODO Positionierung nich schön gelöst (unterschiedliche Auflösungen)
            t.show()
        }

        Log.d("Enter Restaurant", "Enter " + intent.getStringExtra("DEVICE_NAME"))
        restaurantName = intent.getStringExtra("DEVICE_NAME").toString()
        restaurantInfoView.text =
            resources.getString(R.string.ask_for_data_transmission, restaurantName)

        yesButton.setOnClickListener {
            progressCircle.visibility = View.VISIBLE
            yes_button.visibility = View.GONE
            no_button.visibility = View.GONE
            restaurantName?.let { it1 -> BluetoothDataSender(this, it1).start() }
        }
        noButton.setOnClickListener {
            val intent = Intent(this, BluetoothActivity::class.java)
            startActivity(intent)
        }

        updateUserPersonalData()
        ServicesModule.localCustomerPersonalDataService.addCurrentUserPersonalDataObserver { -> updateUserPersonalData() }
    }

    private fun updateUserPersonalData() {
        userPersonalDataView.text =
            ServicesModule.localCustomerPersonalDataService.currentUserPersonalData?.toString()
                ?: "No Saved Data"
        //TODO open contact form if no data saved
    }

    fun goNextActivity() {
        val intent = Intent(this, CheckinSuccessActivity::class.java)
        restaurantName = getIntent().getStringExtra("DEVICE_NAME").toString()
        intent.putExtra("DEVICE_NAME", restaurantName)
        Log.d("Enter Restaurant", "Enter $restaurantName")
        startActivity(intent)
    }
}