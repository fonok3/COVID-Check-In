package de.uni.hannover.hci.mi.team6.covidcheckin.model

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantInfo(val name: String, val beacon: Beacon, val address: Address, val owner: Person)
