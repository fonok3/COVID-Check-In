package de.uni.hannover.hci.mi.team6.covidcheckin.services.restaurantInfo

import de.uni.hannover.hci.mi.team6.covidcheckin.DefaultApplication
import de.uni.hannover.hci.mi.team6.covidcheckin.model.CustomerPersonalData
import de.uni.hannover.hci.mi.team6.covidcheckin.model.RestaurantInfo
import de.uni.hannover.hci.mi.team6.covidcheckin.services.ServicesModule
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.properties.Delegates

/**
 * Service that provides the current user data. Saves the data locally and loads it when needed.
 * @author Elias
 */
class FileBasedRestaurantInfoService(private val userDataFileName: String = "restaurantData.json") :
    RestaurantInfoService {
    private val currentRestaurantInfoObservers = mutableListOf<() -> Unit>()

    override fun addCurrentRestaurantInfoObserver(observer: () -> Unit) {
        currentRestaurantInfoObservers.add(observer)
    }

    /**
     * The current Data object that contains the Customer Data for the current User.
     */
    override var restaurantInfo: RestaurantInfo? by Delegates.observable(
        null
    ) { _, _, _ ->
        restaurantInfo?.let { ServicesModule.restaurantsInfoService.saveRestaurantInfo(it) }
        currentRestaurantInfoObservers.forEach {
            it()
        }
    }

    init {
        readUserdataFromFile()?.let { restaurantInfo = it }
        currentRestaurantInfoObservers.add {
            val file = File(DefaultApplication.context.filesDir, userDataFileName)
            file.writeText(Json.encodeToString(restaurantInfo))
        }
    }

    override fun save(restaurantInfo: RestaurantInfo?) {
        this.restaurantInfo = restaurantInfo
    }

    private fun readUserdataFromFile(): RestaurantInfo? {
        val file = File(DefaultApplication.context.filesDir, userDataFileName)
        if (file.exists()) {
            return Json.decodeFromString(file.readText())
        }
        return null
    }
}