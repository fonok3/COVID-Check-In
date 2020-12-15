package de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.transfer

// TODO use this in the sending data activity
class BluetoothDataSender(targetMacAddress: String) {

    private val deviceMacAddress: String = targetMacAddress
    private val connectThread: ConnectThread = ConnectThread(deviceMacAddress)

    fun start() {
        connectThread.start()
    }

    fun stop() {
        connectThread.cancel()
    }
}