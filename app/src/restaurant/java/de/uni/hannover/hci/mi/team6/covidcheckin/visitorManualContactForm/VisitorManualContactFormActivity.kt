package de.uni.hannover.hci.mi.team6.covidcheckin.visitorManualContactForm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.BluetoothActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.restaurantContactForm.ui.RestaurantContactFormFragment
import de.uni.hannover.hci.mi.team6.covidcheckin.services.ServicesModule
import de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData.CustomerPersonalDataService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData.RestaurantDataService
import de.uni.hannover.hci.mi.team6.covidcheckin.visitorManualContactForm.ui.VisitorManualContactFormFragment

class VisitorManualContactFormActivity : AppCompatActivity() {
    private val customerPersonalDataService: CustomerPersonalDataService by lazy {
        ServicesModule.customerPersonalDataService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.contact_form_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.contact_form_container,
                    VisitorManualContactFormFragment.newInstance()
                )
                .commitNow()
        }
    }
}
