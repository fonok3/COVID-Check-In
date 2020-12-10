package de.uni.hannover.hci.mi.team6.covidcheckin.services.restaurantInfo

import de.uni.hannover.hci.mi.team6.covidcheckin.model.CustomerPersonalData
import de.uni.hannover.hci.mi.team6.covidcheckin.model.RestaurantInfo

/**
 * Service that provides the current user data.
 * @author Florian Herzog
 */
interface RestaurantInfoService {
    /**
     *  The currently saved user data if available.
     */
    val restaurantInfo: RestaurantInfo?

    /**
     *  Saves new customer personal data.
     */
    fun save(restaurantInfo: RestaurantInfo?)

    /**
     *  Adds a new observer for listening to changed personal data.
     */
    fun addCurrentRestaurantInfoObserver(observer: () -> Unit)
}