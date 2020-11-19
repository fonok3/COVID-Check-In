package de.uni.hannover.hci.mi.team6.covidcheckin.permission.repository

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import androidx.core.app.ActivityCompat

class AndroidPermissionsRepository(private val activity: Activity): PermissionsRepository {
    companion object {
        const val TAG: String = "AndroidPermissionsRepository"
        const val REQUEST_ENABLE_PERMISSIONS: Int = 1
    }

    private var listeners = HashSet<PermissionsRepository.ChangedListener>()
    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    override val isBluetoothEnabled: Boolean
        get() = bluetoothAdapter?.isEnabled == true
    override val isLocationEnabled: Boolean
        get() = TODO("not implemented")
    override val areNotificationsEnabled: Boolean
        get() = TODO("not implemented")

    override fun enableBluetooth() {
        if (!isBluetoothEnabled) {
            return
        }
        ActivityCompat.requestPermissions(activity,
            arrayOf(Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN),
            AndroidPermissionsRepository.REQUEST_ENABLE_PERMISSIONS)
    }

    override fun enableLocation() {
        if (!isLocationEnabled) {
            return
        }
        ActivityCompat.requestPermissions(activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            AndroidPermissionsRepository.REQUEST_ENABLE_PERMISSIONS)
    }

    override fun enableNotifications() {
        if (!areNotificationsEnabled) {
            return
        }
        ActivityCompat.requestPermissions(activity,
            arrayOf(Manifest.permission.ACCESS_NOTIFICATION_POLICY),
            AndroidPermissionsRepository.REQUEST_ENABLE_PERMISSIONS)
    }

    override fun addPermissionsChangedListener(listener: PermissionsRepository.ChangedListener) {
        listeners.add(listener)
    }

    override fun removePermissionsChangedListener(listener: PermissionsRepository.ChangedListener) {
        listeners.remove(listener)
    }
}