package de.uni.hannover.hci.mi.team6.covidcheckin.services.restaurantsInfo

import de.uni.hannover.hci.mi.team6.covidcheckin.model.Beacon
import de.uni.hannover.hci.mi.team6.covidcheckin.model.RestaurantInfo

/**
 *
 */
interface RestaurantsInfoService {
    /**
     *
     */
    fun getInfoForRestaurant(beacon: Beacon, result: (Result<RestaurantInfo>) -> Unit)

    /**
     *
     */
    fun saveRestaurantInfo(restaurantInfo: RestaurantInfo)
}