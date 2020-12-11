package de.uni.hannover.hci.mi.team6.covidcheckin.visitorManualContactForm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.services.ServicesModule
import de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData.CustomerPersonalDataService
import de.uni.hannover.hci.mi.team6.covidcheckin.visitorManualContactForm.ui.VisitorManualContactFormFragment

class VisitorManualContactFormActivity : AppCompatActivity() {
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
