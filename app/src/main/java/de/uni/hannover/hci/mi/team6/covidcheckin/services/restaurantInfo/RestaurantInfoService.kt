package de.uni.hannover.hci.mi.team6.covidcheckin.services.restaurantInfo

import de.uni.hannover.hci.mi.team6.covidcheckin.model.Restaurant
import org.altbeacon.beacon.Beacon

/**
 *
 */
interface RestaurantInfoService {
    /**
     *
     */
    fun getInfoForRestaurant(beacon: Beacon, result: (Result<Restaurant>) -> Void)

    /**
     *
     */
    fun saveRestaurantInfo(restaurantInfo: Restaurant)
}