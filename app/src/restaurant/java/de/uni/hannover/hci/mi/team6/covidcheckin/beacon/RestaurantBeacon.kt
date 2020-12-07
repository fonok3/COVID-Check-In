package de.uni.hannover.hci.mi.team6.covidcheckin.beacon

import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseSettings
import android.content.Context
import android.util.Log
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.BeaconTransmitter

class RestaurantBeacon {

    companion object {
        const val TAG = "RestaurantBeacon"
        private lateinit var beaconTransmitter: BeaconTransmitter
        private lateinit var beacon: Beacon

        fun build(applicationContext : Context) {
            beacon = Beacon.Builder()
                .setId1("2f234454-cf6d-4a0f-adf2-f4911ba9ffa6")
                .setId2("1")
                .setId3("2")
                .setManufacturer(0x0118) // Radius Networks.  Change this for other beacon layouts
                .setTxPower(-59)
                .setDataFields(LongArray(1){0}.asList()) // Remove this for beacon layouts without d: fields
                .build()

            val beaconParser = BeaconParser().setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25")
            beaconTransmitter = BeaconTransmitter(applicationContext, beaconParser)
        }

        fun start() {
            beaconTransmitter.startAdvertising(beacon, object : AdvertiseCallback() {
                override fun onStartFailure(errorCode: Int) {
                    Log.e(TAG, "Advertisement start failed with code: $errorCode")
                }

                override fun onStartSuccess(settingsInEffect: AdvertiseSettings) {
                    Log.e(TAG, "Advertisement start succeeded.")
                }
            })
        }

        fun stop() {
            beaconTransmitter.stopAdvertising()
            Log.e(TAG, "Advertisement stop succeeded.")
        }
    }
}