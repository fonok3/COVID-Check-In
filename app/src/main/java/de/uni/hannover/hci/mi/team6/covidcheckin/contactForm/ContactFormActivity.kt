package de.uni.hannover.hci.mi.team6.covidcheckin.contactForm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.BluetoothActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.contactForm.ui.ContactFormFragment
import de.uni.hannover.hci.mi.team6.covidcheckin.permission.ui.PermissionsFragment
import de.uni.hannover.hci.mi.team6.covidcheckin.services.ServicesModule
import de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData.CustomerPersonalDataService

class ContactFormActivity : AppCompatActivity() {
    private val customerPersonalDataService: CustomerPersonalDataService by lazy {
        ServicesModule.customerPersonalDataService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ContactFormFragment.newInstance("",""))
                .commitNow()
        }

        if (customerPersonalDataService.currentUserPersonalData != null) {
            val intent = Intent(this, BluetoothActivity::class.java)
            finish()
            startActivity(intent)
            return
        }
    }
}