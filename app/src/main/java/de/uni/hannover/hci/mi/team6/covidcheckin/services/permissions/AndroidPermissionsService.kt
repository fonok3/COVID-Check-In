package de.uni.hannover.hci.mi.team6.covidcheckin.services.permissions

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import de.uni.hannover.hci.mi.team6.covidcheckin.DefaultApplication

class AndroidPermissionsService() : PermissionsService {
    companion object {
        const val TAG: String = "AndroidPermissionsRepository"
        const val REQUEST_ENABLE_PERMISSIONS: Int = 1
    }

    private var listeners = HashSet<PermissionsService.ChangedListener>()
    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    override val isBluetoothEnabled: Boolean
        get() = bluetoothAdapter?.isEnabled == true
    override val isLocationEnabled: Boolean
        get() = ContextCompat.checkSelfPermission(
            DefaultApplication.context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    override val areNotificationsEnabled: Boolean
        get() = NotificationManagerCompat.from(DefaultApplication.context).areNotificationsEnabled()
    override val allPermissionsGranted: Boolean
        get() = isBluetoothEnabled && isLocationEnabled && areNotificationsEnabled


    override fun enableBluetooth(activity: Activity) {
        if (isBluetoothEnabled) {
            return
        }
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN),
            REQUEST_ENABLE_PERMISSIONS
        )
    }

    override fun enableLocation(activity: Activity) {
        if (isLocationEnabled) {
            return
        }
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_ENABLE_PERMISSIONS
        )
    }

    override fun enableNotifications(activity: Activity) {
        if (areNotificationsEnabled) {
            return
        }
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_NOTIFICATION_POLICY),
            REQUEST_ENABLE_PERMISSIONS
        )
    }

    override fun addPermissionsChangedListener(listener: PermissionsService.ChangedListener) {
        listeners.add(listener)
    }

    override fun removePermissionsChangedListener(listener: PermissionsService.ChangedListener) {
        listeners.remove(listener)
    }
}