package de.uni.hannover.hci.mi.team6.covidcheckin.services.visitorDatabase

import de.uni.hannover.hci.mi.team6.covidcheckin.DefaultApplication
import de.uni.hannover.hci.mi.team6.covidcheckin.model.CustomerPersonalData
import de.uni.hannover.hci.mi.team6.covidcheckin.model.CustomerVisit
import de.uni.hannover.hci.mi.team6.covidcheckin.model.RestaurantData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class JsonBasedVisitorDatabaseService(private val visitorDatabaseFileName: String = "visitorDatabase.json") :
    VisitorDatabaseService {

    private var currentVisitorDatabase: MutableList<CustomerVisit> = ArrayList()

    init {
        readVisitorDatabaseFromFile()
    }

    override fun enterVisitor(customerPersonalData: CustomerPersonalData) {
        currentVisitorDatabase.add(CustomerVisit(System.currentTimeMillis(), customerPersonalData))
        updateFile()
    }

    private fun updateFile() {
        val file = File(DefaultApplication.context.filesDir, visitorDatabaseFileName)
        file.writeText(Json.encodeToString(currentVisitorDatabase))
    }

    private fun readVisitorDatabaseFromFile(): MutableList<CustomerPersonalData>? {
        val file = File(DefaultApplication.context.filesDir, visitorDatabaseFileName)
        if (file.exists()) {
            return Json.decodeFromString(file.readText())
        }
        return null
    }
}