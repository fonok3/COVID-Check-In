package de.uni.hannover.hci.mi.team6.covidcheckin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import de.uni.hannover.hci.mi.team6.covidcheckin.ui.bluetooth.BluetoothFragment
import de.uni.hannover.hci.mi.team6.covidcheckin.ui.bluetooth.BluetoothListFragment
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
    }

    fun changeToList(){
        supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.right_enter, R.anim.left_exit)
            .replace(bluetoothLayout.id, (BluetoothListFragment).newInstance()).commitNow()
    }

    fun changeToBluetooth(){
        supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.left_enter, R.anim.right_exit)
            .replace(bluetoothLayout.id, (BluetoothFragment).newInstance()).commitNow()
    }
}
