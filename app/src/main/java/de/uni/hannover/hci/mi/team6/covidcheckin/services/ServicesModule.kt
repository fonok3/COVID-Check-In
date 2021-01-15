package de.uni.hannover.hci.mi.team6.covidcheckin.services

import de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData.CustomerPersonalDataService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData.FileBasedCustomerPersonalDataService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.permissions.AndroidPermissionsService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.permissions.PermissionsService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.restaurantInfo.FileBasedRestaurantInfoService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.restaurantInfo.RestaurantInfoService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.restaurantsInfo.FirebaseRestaurantsInfoService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.restaurantsInfo.RestaurantsInfoService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.visitorDatabase.JsonBasedVisitorDatabaseService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.visitorDatabase.VisitorDatabaseService


object ServicesModule {
    const val beaconServiceID = "2f234454-cf6d-4a0f-adf2-f4911ba9ffa6"

    /**
     * HardCoded for now.
     */
    const val beaconMajor: String = "1"

    /**
     * HardCoded for now. 
     */
    const val beaconMinor: String = "2"

    val permissionsService: PermissionsService by lazy {
        AndroidPermissionsService()
    }

    val localCustomerPersonalDataService: CustomerPersonalDataService by lazy {
        FileBasedCustomerPersonalDataService()
    }

    val visitorDatabaseService: VisitorDatabaseService by lazy {
        JsonBasedVisitorDatabaseService()
    }

    val restaurantsInfoService: RestaurantsInfoService by lazy {
        FirebaseRestaurantsInfoService()
    }

    val localRestaurantDataService: RestaurantInfoService by lazy {
        FileBasedRestaurantInfoService()
    }
}