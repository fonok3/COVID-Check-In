package de.uni.hannover.hci.mi.team6.covidcheckin.services

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData.CustomerPersonalDataService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData.FileBasedCustomerPersonalDataService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.permissions.AndroidPermissionsService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.permissions.PermissionsService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.restaurantInfo.FirebaseRestaurantInfoService
import de.uni.hannover.hci.mi.team6.covidcheckin.services.restaurantInfo.RestaurantInfoService


object ServicesModule {
    val permissionsService: PermissionsService by lazy {
        AndroidPermissionsService()
    }

    val customerPersonalDataService: CustomerPersonalDataService by lazy {
        FileBasedCustomerPersonalDataService()
    }

    val restaurantInfoService: RestaurantInfoService by lazy {
        FirebaseRestaurantInfoService()
    }
}