package de.uni.hannover.hci.mi.team6.covidcheckin.model

import kotlinx.serialization.Serializable

/**
 * Data class used for Personal Data of Customers
 * @author Elias
 */
@Serializable
data class RestaurantData(
    val restaurantName: String,
    val ownerFirstName: String,
    val ownerlastName: String,
    val street: String,
    val streetNumber: String,
    val zipCode: String,
    val city: String,
    val phoneNumber: String
)