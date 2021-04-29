package de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.transfer

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.widget.Toast
import de.uni.hannover.hci.mi.team6.covidcheckin.enterRestaurant.EnterRestaurantActivity

class DiscoverThread(activity: EnterRestaurantActivity, deviceName: String) : Thread() {
    private val mActivity = activity
    private val mDeviceName = deviceName
    private var mConnectThread: ConnectThread? = null
    val TAG = "DiscoverThread"

    init {
        var mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        Log.d(TAG, "Init")

        mActivity.runOnUiThread(Runnable {
            Toast.makeText(mActivity, "Info sent", Toast.LENGTH_SHORT).show()

        })
        mActivity.goNextActivity()

        if (mBluetoothAdapter.isDiscovering) {
            mBluetoothAdapter.cancelDiscovery()
            mBluetoothAdapter.startDiscovery()
        } else {
            mBluetoothAdapter.startDiscovery()
        }

        /**
         * Broadcast Receiver for listing devices that are not yet paired
         */
        val mBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val action = intent.action
                if (action == BluetoothDevice.ACTION_FOUND) {
                    val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    if (device != null) {
                        Log.d(TAG, "" + device.name + " - " + device.address)
                        if(device.name == mDeviceName) {
                            val mac = device.address
                            Log.d(TAG, "FOUND " + device.name + " - " + mac)
                            mConnectThread = ConnectThread(mac, mActivity)
                            mConnectThread!!.start()

                        }
                    }
                }
            }
        }

        val discoverDevicesIntent = IntentFilter(BluetoothDevice.ACTION_FOUND)
        mActivity.registerReceiver(mBroadcastReceiver, discoverDevicesIntent)
        Log.d(TAG, "Register")
    }

    fun cancel() {
        interrupt()
        mConnectThread?.cancel()
    }

}