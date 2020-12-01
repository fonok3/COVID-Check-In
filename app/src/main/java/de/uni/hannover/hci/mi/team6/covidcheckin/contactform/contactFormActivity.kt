package de.uni.hannover.hci.mi.team6.covidcheckin.contactForm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.BluetoothActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.contactForm.ui.ContactFormFragment
import de.uni.hannover.hci.mi.team6.covidcheckin.services.ServicesModule
import de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData.CustomerPersonalDataService
import kotlinx.android.synthetic.main.contact_form_activity.*

class ContactFormActivity : AppCompatActivity() {
    companion object {
        const val AUTO_FORWARD = "AUTO_FORWARD"
    }

    private val customerPersonalDataService: CustomerPersonalDataService by lazy {
        ServicesModule.customerPersonalDataService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.contact_form_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ContactFormFragment.newInstance())
                .commitNow()
        }

        if (customerPersonalDataService.currentUserPersonalData != null && intent.extras?.get(
                AUTO_FORWARD
            ) as? Boolean != false
        ) {
            val intent = Intent(this, BluetoothActivity::class.java)
            finish()
            startActivity(intent)
        }
    }
}
