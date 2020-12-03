package de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.ui.BluetoothFragment
import de.uni.hannover.hci.mi.team6.covidcheckin.contactForm.ContactFormActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.permission.PermissionsActivity
import kotlinx.android.synthetic.main.bluetooth_activity.*

class BluetoothActivity : AppCompatActivity() {

    var bluetoothActive: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bluetooth_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(bluetoothLayout.id, BluetoothFragment.newInstance())
                .commitNow()
        }
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bluetooth_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_info -> {
            val intent = Intent(this, PermissionsActivity::class.java)
            intent.putExtra(PermissionsActivity.AUTO_FORWARD, false)
            startActivity(intent)
            true
        }
        R.id.action_edit -> {
            val intent = Intent(this, ContactFormActivity::class.java)
            intent.putExtra(ContactFormActivity.AUTO_FORWARD, false)
            startActivity(intent)
            true
        }
        else -> false
    }
}