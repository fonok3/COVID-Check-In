package de.uni.hannover.hci.mi.team6.covidcheckin.services.visitorDatabase

import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import de.uni.hannover.hci.mi.team6.covidcheckin.DefaultApplication
import de.uni.hannover.hci.mi.team6.covidcheckin.model.CustomerPersonalData
import de.uni.hannover.hci.mi.team6.covidcheckin.model.CustomerVisit
import de.uni.hannover.hci.mi.team6.covidcheckin.model.RestaurantData
import de.uni.hannover.hci.mi.team6.covidcheckin.services.ServicesModule
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class JsonBasedVisitorDatabaseService(private val visitorDatabaseFileName: String = "visitorDatabase.json") :
    VisitorDatabaseService {
    private val csvExportFileName: String = "database_export/visitorList.csv"

    private var currentVisitorDatabase: MutableList<CustomerVisit> = ArrayList()

    init {
        readVisitorDatabaseFromFile()
    }

    override fun enterVisitor(customerPersonalData: CustomerPersonalData) {
        currentVisitorDatabase.add(CustomerVisit(System.currentTimeMillis(), customerPersonalData))
        updateFile()
    }

    override fun getCSVFile(): File {
        updateCSV();
        return File(DefaultApplication.context.filesDir, csvExportFileName)
    }

    private fun updateCSV() {
        val file = File(DefaultApplication.context.filesDir, csvExportFileName)
        file.parentFile.mkdirs()
        csvWriter().open(file) {
            ServicesModule.localRestaurantDataService.restaurantInfo?.let { writeRow("Restaurantname: " + it.name) }
            writeRow(
                "Datum",
                "Vorname",
                "Nachname",
                "Straße",
                "Hausnummer",
                "Postleitzahl",
                "Stadt",
                "Telefonnumer"
            )
            for (row in currentVisitorDatabase) {
                writeRow(
                    listOf(
                        row.dateTime,
                        row.visitor.firstName,
                        row.visitor.lastName,
                        row.visitor.street,
                        row.visitor.streetNumber,
                        row.visitor.zipCode,
                        row.visitor.city,
                        row.visitor.phoneNumber
                    )
                )
            }
        }
    }

    private fun updateFile() {
        val file = File(DefaultApplication.context.filesDir, visitorDatabaseFileName)
        file.parentFile.mkdirs()
        file.writeText(Json.encodeToString(currentVisitorDatabase))
    }

    private fun readVisitorDatabaseFromFile(): MutableList<CustomerVisit>? {
        val file = File(DefaultApplication.context.filesDir, visitorDatabaseFileName)
        if (file.exists()) {
            return Json.decodeFromString(file.readText())
        }
        return null
    }
}