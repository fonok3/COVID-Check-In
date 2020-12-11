package de.uni.hannover.hci.mi.team6.covidcheckin.model

import kotlinx.serialization.Serializable

@Serializable
data class Beacon(val id: String, val major: String, val minor: String)