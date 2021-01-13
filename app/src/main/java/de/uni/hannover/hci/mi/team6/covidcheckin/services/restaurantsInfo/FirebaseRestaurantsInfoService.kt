package de.uni.hannover.hci.mi.team6.covidcheckin.services.restaurantsInfo

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.uni.hannover.hci.mi.team6.covidcheckin.DefaultApplication
import de.uni.hannover.hci.mi.team6.covidcheckin.model.Beacon
import de.uni.hannover.hci.mi.team6.covidcheckin.model.RestaurantInfo


class FirebaseRestaurantsInfoService : RestaurantsInfoService {
    companion object {
        const val TAG = "FirebaseRestaurantInfoService"
    }

    private val db = Firebase.firestore

    override fun getInfoForRestaurant(beacon: Beacon, result: (Result<RestaurantInfo>) -> Unit) {
        /*result(
            Result.success(
                RestaurantInfo(
                    "Restaurant Name",
                    Beacon(ServicesModule.beaconServiceID, ServicesModule.beaconMajor, ServicesModule.beaconMinor),
                    Address("Straße 5", "5b", 30167, "Hannover")
                )
            )
        )*/
        /*db.collection("restaurants")
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
            }*/
    }

    @SuppressLint("LongLogTag")
    override fun saveRestaurantInfo(restaurantInfo: RestaurantInfo) {
        val restaurant: Map<String, Any> = hashMapOf(
            "name" to restaurantInfo.name,
            "address.street" to restaurantInfo.address.street,
            "address.streetNumber" to restaurantInfo.address.streetNumber,
            "address.zipCode" to restaurantInfo.address.zipCode,
            "address.city" to restaurantInfo.address.city,
            "beacon.id" to restaurantInfo.beacon.id,
            "beacon.major" to restaurantInfo.beacon.major,
            "beacon.minor" to restaurantInfo.beacon.minor,
            "owner.firstName" to restaurantInfo.owner.firstName,
            "owner.lastName" to restaurantInfo.owner.lastName,
            "timestamp" to System.currentTimeMillis()
        )

        val prefs: SharedPreferences =
            DefaultApplication.context.getSharedPreferences("RESTAURANT_ID", MODE_PRIVATE)
        val id: String? = prefs.getString("restaurantId", null)

        if (id != null) {
            db.collection("restaurants").document(id).update(restaurant)
        } else {
            db.collection("restaurants")
                .add(restaurant)
                .addOnSuccessListener { documentReference ->
                    val editor: SharedPreferences.Editor =
                        DefaultApplication.context.getSharedPreferences(
                            "RESTAURANT_ID",
                            MODE_PRIVATE
                        )
                            .edit()
                    editor.putString("restaurantId", documentReference.id)
                    editor.apply()
                    Log.d(TAG, prefs.getString("restaurantId", "null2").toString())
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }
    }
}