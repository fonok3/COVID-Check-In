package de.uni.hannover.hci.mi.team6.covidcheckin.model

data class CustomerVisit(
    val dateTime: Long,
    val visitor: CustomerPersonalData
)