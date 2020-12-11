package de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData

import de.uni.hannover.hci.mi.team6.covidcheckin.model.CustomerPersonalData
import de.uni.hannover.hci.mi.team6.covidcheckin.model.RestaurantData

interface RestaurantDataService {
    /**
     *  The currently saved restaurant data if available.
     */
    val currentRestaurantData: RestaurantData?

    /**
     *  Saves new customer personal data.
     */
    fun save(restaurantData: RestaurantData?)

    /**
     *  Adds a new observer for listening to changed personal data.
     */
    fun addCurrentRestaurantDataObserver(observer: () -> Unit)
}
