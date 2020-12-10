package de.uni.hannover.hci.mi.team6.covidcheckin.services.restaurantInfo

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.uni.hannover.hci.mi.team6.covidcheckin.model.Restaurant
import org.altbeacon.beacon.Beacon
import java.time.Instant

class FirebaseRestaurantInfoService: RestaurantInfoService {
    companion object {
        const val TAG = "FirebaseRestaurantInfoService"
    }

    private val db = Firebase.firestore

    override fun getInfoForRestaurant(beacon: Beacon, result: (Result<Restaurant>) -> Void) {

    }

    @SuppressLint("LongLogTag")
    override fun saveRestaurantInfo(restaurantInfo: Restaurant) {
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