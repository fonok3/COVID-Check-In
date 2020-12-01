package de.uni.hannover.hci.mi.team6.covidcheckin.services

import de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData.CustomerPersonalDataService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData.FileBasedCustomerPersonalDataService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.permissions.AndroidPermissionsService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.permissions.PermissionsService


object ServicesModule {
    val permissionsService: PermissionsService by lazy {
        AndroidPermissionsService()
    }

    val customerPersonalDataService: CustomerPersonalDataService by lazy {
        FileBasedCustomerPersonalDataService()
    }
}