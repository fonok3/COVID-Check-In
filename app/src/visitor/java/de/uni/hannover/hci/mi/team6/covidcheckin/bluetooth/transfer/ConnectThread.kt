package de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.transfer

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import de.uni.hannover.hci.mi.team6.covidcheckin.enterRestaurant.EnterRestaurantActivity
import java.io.IOException
import java.util.*

/**
 * Make a bluetooth connection to the given device
 * When connected, call the SendThread to send message
 */
class ConnectThread (macAddress: String, activity: EnterRestaurantActivity) : Thread() {

    private var mSocket: BluetoothSocket? = null
    private val TAG = "ConnectThread"
    private val mDevice: BluetoothDevice?
    private val MY_UUID = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66")
    private val deviceMacAdress = macAddress
    private var mSendThread: SendDataThread? = null
    private val mActivity = activity

    init {
        Log.d(TAG, "ConnectThread: started.")

        mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(deviceMacAdress)
        Log.d(TAG, "ConnectThread: " + mDevice.address)
    }

    override fun run() {
        var tmp: BluetoothSocket? = null
        Log.i(TAG, "RUN mConnectThread ")

        // Get a BluetoothSocket for a connection with the given BluetoothDevice
        try {
            Log.d(TAG, "ConnectThread: Trying to create InsecureRfcommSocket using UUID: $MY_UUID")
            tmp = mDevice?.createRfcommSocketToServiceRecord(MY_UUID)
        } catch (e: IOException) {
            Log.e(TAG, "ConnectThread: Could not create InsecureRfcommSocket " + e.message)
        }
        mSocket = tmp

        // Make a connection to the BluetoothSocket
        try {
            // This is a blocking call and will only return on a
            // successful connection or an exception
            mSocket?.connect()
            Log.d(TAG, "run: ConnectThread connected.")
        } catch (e: IOException) {
            // Close the socket
            try {
                mSocket?.close()
                Log.d(TAG, "run: Closed Socket.")
            } catch (e1: IOException) {
                Log.e(TAG, "mConnectThread: run: Unable to close connection in socket "
                        + e1.message)
            }
            Log.d(TAG, "run: ConnectThread: Could not connect to UUID: $MY_UUID")
        }

        mSocket?.let {
            Log.d(TAG, "connected: Starting.")

            // Start the thread to send data
            mSendThread = SendDataThread(mSocket!!, mActivity)
            mSendThread?.start()
        }
    }

    fun cancel() {
        try {
            Log.d(TAG, "cancel: Closing Client Socket.")
            mSocket?.close()
        } catch (e: IOException) {
            Log.e(TAG, "cancel: close() of mmSocket in Connectthread failed. " + e.message)
        }
        interrupt()

        mSendThread?.cancel()
    }
}