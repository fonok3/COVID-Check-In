package de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.transfer

import android.app.Activity
import android.util.Log

class BluetoothDataSender(activity: Activity, deviceName: String) {

    private val discoverThread: DiscoverThread = DiscoverThread(activity, deviceName)

    fun start() {
        Log.d("BLTData", "Init")
        discoverThread.start()
    }

    fun stop() {
        discoverThread.cancel()
    }
}