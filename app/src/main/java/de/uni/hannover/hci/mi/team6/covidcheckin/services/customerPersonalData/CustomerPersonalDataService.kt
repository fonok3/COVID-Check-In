package de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData

import de.uni.hannover.hci.mi.team6.covidcheckin.DefaultApplication
import de.uni.hannover.hci.mi.team6.covidcheckin.materials.CustomerPersonalData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.properties.Delegates

/**
 * Service that provides the current user data. Saves the data locally and loads it when needed.
 * @author Elias
 */
object CustomerPersonalDataService {
    private val currentUserPersonalDataObservers = mutableListOf<() -> Unit>()

    /**
     * File where UserData is persisted
     */
    private const val userDataFileName = "userData.json"

    /**
     * The current Data object that contains the Customer Data for the current User
     */
    var currentUserPersonalData: CustomerPersonalData by Delegates.observable(CustomerPersonalData()) { _, _, _ ->
        currentUserPersonalDataObservers.forEach {
            it()
        }
    }

    init {
        currentUserPersonalData = readUserdataFromFile()
        currentUserPersonalDataObservers.add {
            val file = File(DefaultApplication.context.filesDir, userDataFileName)
            file.writeText(Json.encodeToString(currentUserPersonalData))
        }
    }

    private fun readUserdataFromFile(): CustomerPersonalData {
        val file = File(DefaultApplication.context.filesDir, userDataFileName)
        if (file.exists()) {
            return Json.decodeFromString(file.readText())
        }
        return CustomerPersonalData()
    }

    /**
     * Observers will be notified when the current user data is changed
     */
    fun addCurrentUserPersonalDataObserver(observer: () -> Unit) {
        currentUserPersonalDataObservers.add(observer)
    }
}
