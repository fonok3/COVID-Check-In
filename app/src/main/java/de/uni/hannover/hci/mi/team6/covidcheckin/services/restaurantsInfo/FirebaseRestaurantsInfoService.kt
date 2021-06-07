package de.uni.hannover.hci.mi.team6.covidcheckin.services.restaurantsInfo

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.uni.hannover.hci.mi.team6.covidcheckin.DefaultApplication
import de.uni.hannover.hci.mi.team6.covidcheckin.model.Address
import de.uni.hannover.hci.mi.team6.covidcheckin.model.Beacon
import de.uni.hannover.hci.mi.team6.covidcheckin.model.Person
import de.uni.hannover.hci.mi.team6.covidcheckin.model.RestaurantInfo
import de.uni.hannover.hci.mi.team6.covidcheckin.services.ServicesModule


class FirebaseRestaurantsInfoService : RestaurantsInfoService {
    companion object {
        const val TAG = "FirebaseRestaurantInfoService"
    }

    private val db = Firebase.firestore

    override fun getInfoForRestaurant(beacon: Beacon, result: (Result<RestaurantInfo>) -> Unit) {
        db.collection("restaurants")
            .whereEqualTo("beacon_id", beacon.id)
            //.whereEqualTo("beacon.major", beacon.major)
            //.whereEqualTo("beacon.minor", beacon.minor)
            .get().addOnSuccessListener { d ->
                // get document with latest timestamp
                val latest = d.documents
                    .sortedByDescending { it.data!!["timestamp"] as Long}.take(1)

                latest.firstOrNull()?.let { restaurant ->
                    result(
                        Result.success(
                            RestaurantInfo(
                                restaurant.data!!["name"] as String,
                                Beacon(
                                    ServicesModule.beaconServiceID,
                                    restaurant.data!!["beacon_major"] as String,
                                    restaurant.data!!["beacon_minor"] as String
                                ),
                                Address(
                                    restaurant.data!!["address_street"] as String,
                                    restaurant.data!!["address_streetNumber"] as String,
                                    restaurant.data!!["address_zipCode"] as Long,
                                    restaurant.data!!["address_city"] as String
                                ),
                                Person(
                                    restaurant.data!!["owner_firstName"] as String,
                                    restaurant.data!!["owner_lastName"] as String
                                )
                            )
                        )
                    )
                }
            }
    }

    @SuppressLint("LongLogTag")
    override fun saveRestaurantInfo(restaurantInfo: RestaurantInfo) {
        val restaurant: Map<String, Any> = hashMapOf(
            "name" to restaurantInfo.name,
            "address_street" to restaurantInfo.address.street,
            "address_streetNumber" to restaurantInfo.address.streetNumber,
            "address_zipCode" to restaurantInfo.address.zipCode,
            "address_city" to restaurantInfo.address.city,
            "beacon_id" to restaurantInfo.beacon.id,
            "beacon_major" to restaurantInfo.beacon.major,
            "beacon_minor" to restaurantInfo.beacon.minor,
            "owner_firstName" to restaurantInfo.owner.firstName,
            "owner_lastName" to restaurantInfo.owner.lastName,
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