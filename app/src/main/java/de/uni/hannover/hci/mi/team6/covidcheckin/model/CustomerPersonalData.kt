package de.uni.hannover.hci.mi.team6.covidcheckin.model

import kotlinx.serialization.Serializable

/**
 * Data class used for Personal Data of Customers
 * @author Elias
 */
@Serializable
data class CustomerPersonalData(
    val firstName: String,
    val lastName: String,
    val street: String,
    val streetNumber: String,
    val zipCode: String,
    val city: String,
    val phoneNumber: String
) {
    override fun toString(): String {
        return "$lastName, $firstName\n$street $streetNumber\n$zipCode $city\n$phoneNumber"
    }

    fun toCsvForm(): String {
        return "$lastName,$firstName,$street,$streetNumber,$zipCode,$city,$phoneNumber"
    }
}