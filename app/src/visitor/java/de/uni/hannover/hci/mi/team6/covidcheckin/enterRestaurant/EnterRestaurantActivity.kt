package de.uni.hannover.hci.mi.team6.covidcheckin.enterRestaurant

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.BluetoothActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.checkin.CheckinSuccessActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.services.ServicesModule

/**
 * Activity that shows the screen where the User can accept or deny transfer of his UserData to the restaurant
 * @author Elias
 */
class EnterRestaurantActivity : AppCompatActivity() {
    private lateinit var restaurantInfoView: TextView
    private lateinit var userPersonalDataView: TextView
    private lateinit var yesButton: Button
    private lateinit var noButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.enter_restaurant_activity)

        restaurantInfoView = findViewById(R.id.restaurantInfoView)
        userPersonalDataView = findViewById(R.id.userPersonalDataView)
        yesButton = findViewById(R.id.yes_button)
        noButton = findViewById(R.id.no_button)

        val restaurantName = "Francesca & Fratelli" //TODO set correct restaurantName
        restaurantInfoView.text =
            resources.getString(R.string.ask_for_data_transmission, restaurantName)

        yesButton.setOnClickListener {
            val intent = Intent(this, CheckinSuccessActivity::class.java)
            startActivity(intent)
        }
        noButton.setOnClickListener {
            val intent = Intent(this, BluetoothActivity::class.java)
            startActivity(intent)
        }

        updateUserPersonalData()
        ServicesModule.customerPersonalDataService.addCurrentUserPersonalDataObserver { -> updateUserPersonalData() }
    }

    private fun updateUserPersonalData() {
        userPersonalDataView.text =
            ServicesModule.customerPersonalDataService.currentUserPersonalData?.toString()
                ?: "No Saved Data"
        //TODO open contact form if no data saved
    }
}