package de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData

import de.uni.hannover.hci.mi.team6.covidcheckin.DefaultApplication
import de.uni.hannover.hci.mi.team6.covidcheckin.model.RestaurantData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.properties.Delegates

class FileBasedRestaurantDataService(private val restaurantDataFileName: String = "restaurantData.json") :
    RestaurantDataService {
    private val currentRestaurantDataObservers = mutableListOf<() -> Unit>()

    override var currentRestaurantData: RestaurantData? by Delegates.observable(
        null
    ) { _, _, _ ->
        currentRestaurantDataObservers.forEach {
            it()
        }
    }

    init {
        readUserdataFromFile()?.let { currentRestaurantData = it }
        currentRestaurantDataObservers.add {
            val file = File(DefaultApplication.context.filesDir, restaurantDataFileName)
            file.writeText(Json.encodeToString(currentRestaurantData))
        }
    }

    override fun save(restaurantData: RestaurantData?) {
        currentRestaurantData = restaurantData
    }

    override fun addCurrentRestaurantDataObserver(observer: () -> Unit) {
        currentRestaurantDataObservers.add(observer)
    }

    private fun readUserdataFromFile(): RestaurantData? {
        val file = File(DefaultApplication.context.filesDir, restaurantDataFileName)
        if (file.exists()) {
            return Json.decodeFromString(file.readText())
        }
        return null
    }
}