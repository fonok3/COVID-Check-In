package de.uni.hannover.hci.mi.team6.covidcheckin.services.restaurantsInfo

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.uni.hannover.hci.mi.team6.covidcheckin.model.Address
import de.uni.hannover.hci.mi.team6.covidcheckin.model.Beacon
import de.uni.hannover.hci.mi.team6.covidcheckin.model.RestaurantInfo

class FirebaseRestaurantsInfoService : RestaurantsInfoService {
    companion object {
        const val TAG = "FirebaseRestaurantInfoService"
    }

    private val db = Firebase.firestore

    override fun getInfoForRestaurant(beacon: Beacon, result: (Result<RestaurantInfo>) -> Unit) {
        db.collection("restaurants")
            .whereEqualTo("beacon.major", beacon.major)
            .whereEqualTo("beacon.minor", beacon.minor)
            .get().addOnSuccessListener { it ->
                it.documents.firstOrNull()?.let { restaurant ->
                    result(
                        Result.success(
                            RestaurantInfo(
                                restaurant.data!!["name"] as String,
                                Beacon("", "", ""),
                                Address("", 0, 0, "")
                            )
                        )
                    )
                }
            }
    }

    @SuppressLint("LongLogTag")
    override fun saveRestaurantInfo(restaurantInfo: RestaurantInfo) {
        val restaurant = hashMapOf(
            "name" to restaurantInfo.name,
            "address.street" to restaurantInfo.address.street,
            "address.streetNumber" to restaurantInfo.address.streetNumber,
            "address.zipCode" to restaurantInfo.address.zipCode,
            "address.city" to restaurantInfo.address.city,
            "beacon.id" to restaurantInfo.beacon.id,
            "beacon.major" to restaurantInfo.beacon.major,
            "beacon.minor" to restaurantInfo.beacon.minor,
            "timestamp" to System.currentTimeMillis()
        )

        // Add a new document with a generated ID
        db.collection("restaurants")
            .add(restaurant)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}