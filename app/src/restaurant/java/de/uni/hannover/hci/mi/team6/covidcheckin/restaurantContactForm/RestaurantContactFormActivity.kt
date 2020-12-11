package de.uni.hannover.hci.mi.team6.covidcheckin.restaurantContactForm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.BluetoothActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.restaurantContactForm.ui.RestaurantContactFormFragment
import de.uni.hannover.hci.mi.team6.covidcheckin.services.ServicesModule
import de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData.RestaurantDataService

class RestaurantContactFormActivity : AppCompatActivity() {
    companion object {
        const val AUTO_FORWARD = "AUTO_FORWARD"
    }

    private val restaurantDataService: RestaurantDataService by lazy {
        ServicesModule.restaurantDataService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.contact_form_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.contact_form_container, RestaurantContactFormFragment.newInstance())
                .commitNow()
        }

        if (restaurantDataService.currentRestaurantData != null && intent.extras?.get(
                AUTO_FORWARD
            ) as? Boolean != false
        ) {
            val intent = Intent(this, BluetoothActivity::class.java)
            finish()
            startActivity(intent)
        }
    }
}
