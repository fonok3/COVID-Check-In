package de.uni.hannover.hci.mi.team6.covidcheckin.services.permissions

import android.app.Activity

interface PermissionsService {
    interface ChangedListener {
        fun permissionDidChange(permission: Permission)
    }

    enum class Permission {
        BLUETOOTH, LOCATION, NOTIFICATIONS
    }

    /**
     *
     */
    val isBluetoothEnabled: Boolean
    val isLocationEnabled: Boolean
    val areNotificationsEnabled: Boolean

    val allPermissionsGranted: Boolean

    fun enableBluetooth(activity: Activity)
    fun enableLocation(activity: Activity)
    fun enableNotifications(activity: Activity)

    fun addPermissionsChangedListener(listener: ChangedListener)
    fun removePermissionsChangedListener(listener: ChangedListener)
}