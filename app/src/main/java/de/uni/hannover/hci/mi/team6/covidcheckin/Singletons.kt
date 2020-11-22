package de.uni.hannover.hci.mi.team6.covidcheckin

/**
 * Singleton that always stores the current personal data. Has to be initialized at app start and written to a file when changed
 * @author Elias
 */
object UserPersonalDataSingleton {
    /**
     * Observers will get called when something is changed
     */
    val observers = mutableListOf<() -> Unit>()

    var firstName: String = "Max"
        private set
    var lastName: String = "Mustermann"
        private set
    var street: String = "Heidestraße"
        private set
    var streetNumber: String = "17"
        private set
    var zipCode: String = "50667"
        private set
    var city: String = "Köln"
        private set
    var phoneNumber: String = "01234567890"
        private set

    /**
     * sets the corresponding fields of the UserPersonalDataSingleton
     */
    fun set(
        firstName: String,
        lastName: String,
        street: String,
        streetNumber: String,
        zipCode: String,
        city: String,
        phoneNumber: String
    ) {
        this.firstName = firstName
        this.lastName = lastName
        this.street = street
        this.streetNumber = streetNumber
        this.city = city
        this.zipCode = zipCode
        this.phoneNumber = phoneNumber

        observers.forEach({ it() })
    }

    override fun toString(): String {
        return "$lastName, $firstName\n$street $streetNumber\n$zipCode $city\n$phoneNumber"
    }
}