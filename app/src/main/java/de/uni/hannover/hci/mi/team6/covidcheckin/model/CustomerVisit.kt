package de.uni.hannover.hci.mi.team6.covidcheckin.model

import kotlinx.serialization.Serializable

@Serializable
data class CustomerVisit(
    val dateTime: Long,
    val visitor: CustomerPersonalData
)