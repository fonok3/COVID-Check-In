package de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.ui.BluetoothFragment
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.ui.BluetoothListFragment
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
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bluetooth_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.edit -> {
            Log.e("TODO","IMPLEMENT_CHANGE_TO_USERDATA")
            true
        }
        else -> {
            true
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
