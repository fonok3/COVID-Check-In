package de.uni.hannover.hci.mi.team6.covidcheckin.services

import de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData.CustomerPersonalDataService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData.FileBasedCustomerPersonalDataService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData.RestaurantDataService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData.FileBasedRestaurantDataService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.permissions.AndroidPermissionsService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.permissions.PermissionsService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.visitorDatabase.JsonBasedVisitorDatabaseService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.visitorDatabase.VisitorDatabaseService


object ServicesModule {
    val permissionsService: PermissionsService by lazy {
        AndroidPermissionsService()
    }

    val customerPersonalDataService: CustomerPersonalDataService by lazy {
        FileBasedCustomerPersonalDataService()
    }

    val restaurantDataService: RestaurantDataService by lazy {
        FileBasedRestaurantDataService()
    }

    val visitorDatabaseService: VisitorDatabaseService by lazy {
        JsonBasedVisitorDatabaseService()
    }
}