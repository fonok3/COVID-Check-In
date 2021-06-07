package de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.transfer

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.util.Log
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.BluetoothActivity
import java.io.IOException
import java.util.*

/**
 * Restaurant app listens the connection request from other visitor devices and accepts them
 */
class AcceptConnectionThread(activity: BluetoothActivity) : Thread() {

    private var mServerSocket: BluetoothServerSocket? = null
    private var mReceiveThread: ReceiveDataThread? = null
    private val TAG = "AcceptBluetooth"
    private val mActivity: BluetoothActivity = activity

    companion object {
        val MY_UUID = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66")
    }

    init {
        var tmp: BluetoothServerSocket? = null

        try {
            tmp = BluetoothAdapter.getDefaultAdapter()
                .listenUsingInsecureRfcommWithServiceRecord(TAG, MY_UUID)

            Log.d(TAG, "AcceptThread: Setting up Server using: $MY_UUID");
        } catch (e: IOException){
            Log.e(TAG, "AcceptThread: IOException: " + e.message);
        }

        mServerSocket = tmp
    }

    override fun run() {
        Log.d(TAG, "run: AcceptThread Running.")

        var socket: BluetoothSocket? = null
        try {
            Log.d(TAG, "run: RFCOM server socket start.....");

            // This is a blocking call and will only return on a
            // successful connection or an exception
            socket = mServerSocket!!.accept()
            Log.d(TAG, "run: RFCOM server socket accepted connection.");
        } catch (e: IOException) {
            Log.e(TAG, "AcceptThread: IOException: " + e.message );
        }

        socket?.let {
            Log.d(TAG, "connected: Starting.")

            // Start the thread to receive message
            mReceiveThread = ReceiveDataThread(socket, mActivity)
            mReceiveThread!!.start()
        }

    }

    fun cancel() {
        Log.d(TAG, "cancel: Canceling AcceptThread.")
        try {
            mServerSocket!!.close()
        } catch (e : IOException) {
            Log.e(TAG, "cancel: Close of AcceptThread ServerSocket failed. " + e.message)
        }
        interrupt()

        mReceiveThread?.cancel()
    }
}