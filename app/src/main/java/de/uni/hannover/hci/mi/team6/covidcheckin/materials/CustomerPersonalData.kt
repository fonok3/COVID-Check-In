package de.uni.hannover.hci.mi.team6.covidcheckin.materials

import kotlinx.serialization.Serializable

/**
 * Data class used for Personal Data of Customers
 * @author Elias
 */
@Serializable
data class CustomerPersonalData(
    val firstName: String = "Max",
    val lastName: String = "Mustermann",
    val street: String = "Heidestraße",
    val streetNumber: String = "17",
    val zipCode: String = "50667",
    val city: String = "Köln",
    val phoneNumber: String = "01234 567890"
) {
    override fun toString(): String {
        return "$lastName, $firstName\n$street $streetNumber\n$zipCode $city\n$phoneNumber"
    }
}