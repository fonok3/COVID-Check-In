package de.uni.hannover.hci.mi.team6.covidcheckin.permission

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.contactForm.ContactFormActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.permission.ui.PermissionsFragment
import de.uni.hannover.hci.mi.team6.covidcheckin.services.ServicesModule
import de.uni.hannover.hci.mi.team6.covidcheckin.services.permissions.PermissionsService


class PermissionsActivity : AppCompatActivity() {
    companion object {
        const val AUTO_FORWARD = "AUTO_FORWARD"
    }

    private val permissionsService: PermissionsService by lazy {
        ServicesModule.permissionsService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.permissions_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PermissionsFragment.newInstance())
                .commitNow()
        }

        if (permissionsService.allPermissionsGranted && intent.extras?.get(
                ContactFormActivity.AUTO_FORWARD
            ) as? Boolean != false) {
            val intent = Intent(this, ContactFormActivity::class.java)
            finish()
            startActivity(intent)
            return
        }
    }
}