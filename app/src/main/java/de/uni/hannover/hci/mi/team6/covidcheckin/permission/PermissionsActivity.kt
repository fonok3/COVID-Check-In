package de.uni.hannover.hci.mi.team6.covidcheckin.permission

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.BluetoothActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.permission.ui.PermissionsFragment
import de.uni.hannover.hci.mi.team6.covidcheckin.services.ServicesModule
import de.uni.hannover.hci.mi.team6.covidcheckin.services.permissions.PermissionsService


class PermissionsActivity : AppCompatActivity() {

    private val service: PermissionsService by lazy {
        ServicesModule.permissionsService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PermissionsFragment.newInstance())
                .commitNow()
        }

        if (service.allPermissionsGranted) {
            val intent = Intent(this, BluetoothActivity::class.java)
            finish()
            startActivity(intent)
            return
        }
    }
}