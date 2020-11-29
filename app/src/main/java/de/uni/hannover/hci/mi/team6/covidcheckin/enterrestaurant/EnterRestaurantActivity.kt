package de.uni.hannover.hci.mi.team6.covidcheckin.enterrestaurant

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.main.MainActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.model.CustomerPersonalData
import de.uni.hannover.hci.mi.team6.covidcheckin.services.ServicesModule
import de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData.CustomerPersonalDataService

/**
 * Activity that shows the screen where the User can accept or deny transfer of his UserData to the restaurant
 * @author Elias
 */
class EnterRestaurantActivity : AppCompatActivity() {
    private lateinit var restaurantInfoView: TextView
    private lateinit var userPersonalDataView: TextView
    private lateinit var yesButton: Button
    private lateinit var noButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.enter_restaurant_activity)

        restaurantInfoView = findViewById(R.id.restaurantInfoView)
        userPersonalDataView = findViewById(R.id.userPersonalDataView)
        yesButton = findViewById(R.id.yes_button)
        noButton = findViewById(R.id.no_button)

        val restaurantName = "Muster Restaurant" //TODO set correct restaurantName
        restaurantInfoView.text =
            resources.getString(R.string.ask_for_data_transmission, restaurantName)

        yesButton.setOnClickListener { notificationTest() } //TODO Open correct Activity and transmit data
        noButton.setOnClickListener {
            ServicesModule.customerPersonalDataService.save(
                CustomerPersonalData("" + System.currentTimeMillis() % 1000)
            )
        } //TODO Open correct Activity

        updateUserPersonalData()
        ServicesModule.customerPersonalDataService.addCurrentUserPersonalDataObserver { -> updateUserPersonalData() }
    }

    private fun updateUserPersonalData() {
        userPersonalDataView.text =
            ServicesModule.customerPersonalDataService.currentUserPersonalData?.toString()
                ?: "No Saved Data"
        //TODO open contact form if no data saved
    }


    //TODO move all this notification stuff
    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 101

    /**
     * GEHÖRT HIER NICHT HIN, IST NUR ZUM AUSPROBIEREN BEVOR DIE NOTIFICATION AN DER RICHTIGEN STELLE ERSTELLT WIRD
     */
    private fun notificationTest() {
        createNotificationChannel()

        val intent =
            Intent(this, MainActivity::class.java).apply { //TODO Link zur richtigen Activity
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)

            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setContentTitle("Restaurant betreten")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Du hast das Restaurant \"Muster\" betreten. Möchtest du automatisch deine Daten übertragen?")//TODO Dynamisch den Restaurantnamen übernehmen
            )

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }

    }

    /**
     * GEHÖRT HIER NICHT HIN, IST NUR ZUM AUSPROBIEREN BEVOR DIE NOTIFICATION AN DER RICHTIGEN STELLE ERSTELLT WIRD
     */
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}