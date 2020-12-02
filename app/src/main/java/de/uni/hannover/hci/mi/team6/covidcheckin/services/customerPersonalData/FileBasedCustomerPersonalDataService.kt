package de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData

import de.uni.hannover.hci.mi.team6.covidcheckin.DefaultApplication
import de.uni.hannover.hci.mi.team6.covidcheckin.model.CustomerPersonalData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.properties.Delegates

/**
 * Service that provides the current user data. Saves the data locally and loads it when needed.
 * @author Elias
 */
class FileBasedCustomerPersonalDataService(private val userDataFileName: String = "userData.json") :
    CustomerPersonalDataService {
    private val currentUserPersonalDataObservers = mutableListOf<() -> Unit>()

    /**
     * The current Data object that contains the Customer Data for the current User.
     */
    override var currentUserPersonalData: CustomerPersonalData? by Delegates.observable(
        CustomerPersonalData()
    ) { _, _, _ ->
        currentUserPersonalDataObservers.forEach {
            it()
        }
    }

    init {
        readUserdataFromFile()?.let { currentUserPersonalData }
        currentUserPersonalDataObservers.add {
            val file = File(DefaultApplication.context.filesDir, userDataFileName)
            file.writeText(Json.encodeToString(currentUserPersonalData))
        }
    }

    override fun save(customerPersonalData: CustomerPersonalData?) {
        currentUserPersonalData = customerPersonalData
    }

    /**
     * Observers will be notified when the current user data is changed
     */
    override fun addCurrentUserPersonalDataObserver(observer: () -> Unit) {
        currentUserPersonalDataObservers.add(observer)
    }

    private fun readUserdataFromFile(): CustomerPersonalData? {
        val file = File(DefaultApplication.context.filesDir, userDataFileName)
        if (file.exists()) {
            return Json.decodeFromString(file.readText())
        }
        return null
    }
}