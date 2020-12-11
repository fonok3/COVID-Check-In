package de.uni.hannover.hci.mi.team6.covidcheckin.services.visitorDatabase

import de.uni.hannover.hci.mi.team6.covidcheckin.model.CustomerPersonalData
import de.uni.hannover.hci.mi.team6.covidcheckin.model.CustomerVisit

interface VisitorDatabaseService {
    fun enterVisitor(customerPersonalData: CustomerPersonalData)
}