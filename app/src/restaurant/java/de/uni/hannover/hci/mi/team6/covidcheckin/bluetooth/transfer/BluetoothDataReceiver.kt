package de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.transfer

import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.BluetoothActivity

class BluetoothDataReceiver(activity: BluetoothActivity) {

    private val acceptThread: AcceptConnectionThread = AcceptConnectionThread(activity)

    fun start() {
        acceptThread.start()
    }

    fun stop() {
        acceptThread.cancel()
    }
}