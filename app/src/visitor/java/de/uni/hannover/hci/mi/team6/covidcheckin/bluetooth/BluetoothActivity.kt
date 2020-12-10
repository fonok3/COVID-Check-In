package de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.beacon.VisitorBeaconService
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.ui.BluetoothFragment
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.ui.BluetoothListFragment
import de.uni.hannover.hci.mi.team6.covidcheckin.contactForm.ContactFormActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.permission.PermissionsActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.services.ServicesModule
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

        ServicesModule.restaurantsInfoService.getInfoForRestaurant(
            de.uni.hannover.hci.mi.team6.covidcheckin.model.Beacon(
                ServicesModule.beaconServiceID,
                ServicesModule.beaconMajor,
                ServicesModule.beaconMinor
            )
        ) {
            it.getOrNull()?.let { restaurant ->
                Toast.makeText(
                    this,
                    "Restaurant betreten: " + restaurant.name,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
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

    fun changeToList() {
        supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.right_enter,
            R.anim.left_exit
        )
            .replace(bluetoothLayout.id, BluetoothListFragment.newInstance()).commitNow()
    }


    fun changeToBluetooth() {
        supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.left_enter,
            R.anim.right_exit
        )
            .replace(bluetoothLayout.id, BluetoothFragment.newInstance()).commitNow()
    }

    fun startFindingBeacon() {
        val intent = Intent(this@BluetoothActivity, VisitorBeaconService::class.java)
        this.startService(intent);
    }

    fun stopFindingBeacon() {
        val intent = Intent(this@BluetoothActivity, VisitorBeaconService::class.java)
        this.stopService(intent);
    }
}
