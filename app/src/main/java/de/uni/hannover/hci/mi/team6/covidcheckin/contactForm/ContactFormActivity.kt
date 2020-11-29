package de.uni.hannover.hci.mi.team6.covidcheckin.contactForm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.BluetoothActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.services.ServicesModule
import de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData.CustomerPersonalDataService

class ContactFormActivity : AppCompatActivity() {
    private val customerPersonalDataService: CustomerPersonalDataService by lazy {
        ServicesModule.customerPersonalDataService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (customerPersonalDataService.currentUserPersonalData != null) {
            val intent = Intent(this, BluetoothActivity::class.java)
            finish()
            startActivity(intent)
            return
        }
    }
}