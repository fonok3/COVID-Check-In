package de.uni.hannover.hci.mi.team6.covidcheckin.model

import kotlinx.serialization.Serializable

@Serializable
data class Address(val street: String, val streetNumber: String, val zipCode: Int, val city: String)