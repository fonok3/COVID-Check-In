package de.uni.hannover.hci.mi.team6.covidcheckin.services

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.ContextWrapper
import de.uni.hannover.hci.mi.team6.covidcheckin.DefaultApplication
import de.uni.hannover.hci.mi.team6.covidcheckin.services.permissions.AndroidPermissionsService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.permissions.PermissionsService


object ServicesModule {
    val permissionsService: PermissionsService by lazy {
        AndroidPermissionsService()
    }
}