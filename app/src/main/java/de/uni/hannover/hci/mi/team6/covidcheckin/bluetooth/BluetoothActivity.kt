package de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.ui.BluetoothFragment
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.ui.BluetoothListFragment
import de.uni.hannover.hci.mi.team6.covidcheckin.contactForm.ContactFormActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.permission.PermissionsActivity
import kotlinx.android.synthetic.main.bluetooth_activity.*

class BluetoothActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bluetooth_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(bluetoothLayout.id, BluetoothFragment.newInstance())
                .commitNow()
        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_info -> {
                    val intent = Intent(this, PermissionsActivity::class.java)
                    intent.putExtra(PermissionsActivity.AUTO_FORWARD, false)
                    startActivity(intent)
                    true
                }
                R.id.action_edit -> {
                    val intent = Intent(this, ContactFormActivity::class.java)
                    intent.putExtra(PermissionsActivity.AUTO_FORWARD, false)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
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
}
