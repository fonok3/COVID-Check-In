package de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData

import de.uni.hannover.hci.mi.team6.covidcheckin.model.CustomerPersonalData

/**
 * Service that provides the current user data.
 * @author Florian Herzog
 */
interface CustomerPersonalDataService {
    /**
     *  The currently saved user data if available.
     */
    val currentUserPersonalData: CustomerPersonalData?

    /**
     *  Saves new customer personal data.
     */
    fun save(customerPersonalData: CustomerPersonalData?)

    /**
     *  Adds a new observer for listening to changed personal data.
     */
    fun addCurrentUserPersonalDataObserver(observer: () -> Unit)
}
