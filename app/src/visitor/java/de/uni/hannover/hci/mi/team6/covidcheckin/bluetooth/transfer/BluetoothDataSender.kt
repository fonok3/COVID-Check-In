package de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.transfer

import android.util.Log
import de.uni.hannover.hci.mi.team6.covidcheckin.enterRestaurant.EnterRestaurantActivity

class BluetoothDataSender(activity: EnterRestaurantActivity, deviceName: String) {

    private val discoverThread: DiscoverThread = DiscoverThread(activity, deviceName)

    fun start() {
        Log.d("BLTData", "Init")
        discoverThread.start()
    }

    fun stop() {
        discoverThread.cancel()
    }
}