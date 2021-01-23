package de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.transfer

import android.bluetooth.BluetoothSocket
import android.util.Log
import android.widget.Toast
import de.uni.hannover.hci.mi.team6.covidcheckin.enterRestaurant.EnterRestaurantActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.services.ServicesModule
import de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData.CustomerPersonalDataService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.restaurantInfo.RestaurantInfoService
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset

/**
 * Send message to the bonded device
 */
class SendDataThread(socket: BluetoothSocket, activity: EnterRestaurantActivity) : Thread() {
    private var mSocket: BluetoothSocket? = null
    private var mInStream: InputStream? = null
    private var mOutStream: OutputStream? = null
    private val TAG = "SendThread"
    private val mActivity = activity

    private val userPersonalDataService: CustomerPersonalDataService by lazy {
        ServicesModule.localCustomerPersonalDataService
    }

    init {
        Log.d(TAG, "SendThread: Starting.")

        mSocket = socket
        var tmpIn: InputStream? = null
        var tmpOut: OutputStream? = null

        try {
            tmpIn = mSocket?.inputStream
            tmpOut = mSocket?.outputStream
        } catch (e: IOException) {
            e.printStackTrace()
        }
        mInStream = tmpIn
        mOutStream = tmpOut
    }

    override fun run() {

        Log.d(TAG, "send: send Called.")

        userPersonalDataService.currentUserPersonalData?.let {
            val bytes: ByteArray =
                Json.encodeToString(userPersonalDataService.currentUserPersonalData!!)
                    .toByteArray(Charset.defaultCharset())

            //while (true) {
            send(bytes)
            //sleep(200)
            //}

            Log.d(TAG, "send finish")
            Log.d(
                TAG,
                "send " + Json.encodeToString(userPersonalDataService.currentUserPersonalData!!)
            )
        }
    }

    // send data to the remote device (restaurant device)
    fun send(bytes: ByteArray?) {
        val text = String(bytes!!, Charset.defaultCharset())
        Log.d(TAG, "send: Writing to outputstream: $text")
        mActivity.runOnUiThread(Runnable {
            Toast.makeText(mActivity, "Info sent: $text", Toast.LENGTH_SHORT).show()

        })
        mActivity.goNextActivity()
        try {
            mOutStream?.write(bytes)
        } catch (e: IOException) {
            Log.e(TAG, "send: Error writing to output stream. " + e.message)
        }
    }

    // shut down connection and stop thread
    fun cancel() {
        try {
            mSocket?.close()
        } catch (e: IOException) {}

        interrupt()
    }
}