package de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.transfer

import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset

/**
 * Send message to the bonded device
 */
class SendDataThread(socket: BluetoothSocket) : Thread() {
    private var mSocket: BluetoothSocket? = null
    private var mInStream: InputStream? = null
    private var mOutStream: OutputStream? = null
    private val TAG = "SendThread"

    //TODO bind visitor data
    private val testInfo: String = "{'name':'max','salary':56000,'email':'max@max.de'}"

    init {
        Log.d(TAG, "ConnectedThread: Starting.")

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

        val bytes: ByteArray = testInfo.toByteArray(Charset.defaultCharset())

        //while (true) {
            send(bytes)
            //sleep(200)
        //}
        Log.d(TAG, "send finish")
    }

    // send data to the remote device (restaurant device)
    fun send(bytes: ByteArray?) {
        val text = String(bytes!!, Charset.defaultCharset())
        Log.d(TAG, "send: Writing to outputstream: $text")
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