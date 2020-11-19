package de.uni.hannover.hci.mi.team6.covidcheckin.permission.repository

interface PermissionsRepository {
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

    fun enableBluetooth()
    fun enableLocation()
    fun enableNotifications()

    fun addPermissionsChangedListener(listener: ChangedListener)
    fun removePermissionsChangedListener(listener: ChangedListener)
}