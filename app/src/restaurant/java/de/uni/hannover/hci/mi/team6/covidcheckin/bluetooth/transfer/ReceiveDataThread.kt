package de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.transfer

import android.bluetooth.BluetoothSocket
import android.util.Log
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.BluetoothActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.model.CustomerPersonalData
import de.uni.hannover.hci.mi.team6.covidcheckin.services.ServicesModule
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class ReceiveDataThread(socket: BluetoothSocket, activity: BluetoothActivity) : Thread() {

    private val mSocket: BluetoothSocket = socket
    private val mInStream: InputStream?
    private val mOutStream: OutputStream?
    private val TAG = "ReceiveThread"
    private val mActivity: BluetoothActivity = activity

    init {
        Log.d(TAG, "ReceiveThread: Starting.")

        var tmpIn: InputStream? = null
        var tmpOut: OutputStream? = null

        try {
            tmpIn = mSocket.inputStream
            tmpOut = mSocket.outputStream
        } catch (e: IOException) {
            e.printStackTrace()
        }
        mInStream = tmpIn
        mOutStream = tmpOut
    }

    override fun run() {
        val buffer = ByteArray(1024) // buffer store for the stream
        var bytes: Int // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs
        while (true) {
            // Read from the InputStream
            try {
                bytes = mInStream!!.read(buffer)
                val visitor_info = String(buffer, 0, bytes)
                //TODO save incoming visitor info in database
                Log.d(TAG, "InputStream: $visitor_info")

                mActivity.showSnackbarForReceivedDaten(visitor_info.split(",")[0])


            } catch (e: IOException) {
                Log.e(TAG, "write: Error reading Input Stream. " + e.message)
                break
            }
        }
    }

    // shut down connection and stop thread
    fun cancel() {
        Log.d(TAG, "cancel: Canceling ReceiveThread.")
        try {
            mSocket.close()
        } catch (e: IOException) {
        }

        interrupt()
    }
}