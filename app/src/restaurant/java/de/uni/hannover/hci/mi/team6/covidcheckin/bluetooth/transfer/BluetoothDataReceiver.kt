package de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.transfer

class BluetoothDataReceiver {

    private val acceptThread: AcceptConnectionThread = AcceptConnectionThread()

    fun start() {
        acceptThread.start()
    }

    fun stop() {
        acceptThread.cancel()
    }
}