package de.uni.hannover.hci.mi.team6.covidcheckin.beacon

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.RemoteException
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import de.uni.hannover.hci.mi.team6.covidcheckin.DefaultApplication
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.enterRestaurant.EnterRestaurantActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.model.RestaurantInfo
import de.uni.hannover.hci.mi.team6.covidcheckin.services.ServicesModule
import org.altbeacon.beacon.BeaconConsumer
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.MonitorNotifier
import org.altbeacon.beacon.Region


class VisitorBeaconService : Service(), BeaconConsumer {

    private lateinit var beaconManager: BeaconManager
    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 101

    override fun onCreate() {
        super.onCreate()
        beaconManager = BeaconManager.getInstanceForApplication(this)
        beaconManager.bind(this)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onBeaconServiceConnect() {
        beaconManager.removeAllMonitorNotifiers()
        beaconManager.addMonitorNotifier(object : MonitorNotifier {
            override fun didEnterRegion(region: Region?) {
                ServicesModule.restaurantsInfoService.getInfoForRestaurant(
                    de.uni.hannover.hci.mi.team6.covidcheckin.model.Beacon(
                        region!!.id1.toString(),
                        region.id2.toString(),
                        region.id3.toString()
                    )
                ) {
                    it.getOrNull()?.let { restaurant ->
                        notificationTest(restaurant)
                        Toast.makeText(applicationContext, "Restaurant betreten", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }

            override fun didExitRegion(region: Region?) {
                Toast.makeText(applicationContext, "Restaurant verlassen", Toast.LENGTH_LONG).show()
            }

            override fun didDetermineStateForRegion(state: Int, region: Region?) {}
        })
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // start finding restaurant beacon.
        try {
            beaconManager.startMonitoringBeaconsInRegion(
                Region("VisitorUniqueID", null, null, null)
            )
        } catch (e: RemoteException) {
        }
        return START_STICKY
    }

    override fun onDestroy() {
        // stop finding restaurant beacon
        try {
            beaconManager.stopMonitoringBeaconsInRegion(
                Region("VisitorUniqueID", null, null, null)
            )
        } catch (e: RemoteException) {
        }
        super.onDestroy()
    }

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

    /**
     * GEHÖRT HIER NICHT HIN, IST NUR ZUM AUSPROBIEREN BEVOR DIE NOTIFICATION AN DER RICHTIGEN STELLE ERSTELLT WIRD
     */
    private fun notificationTest(restaurant: RestaurantInfo) {
        createNotificationChannel()

        val intent =
            Intent(
                this,
                EnterRestaurantActivity::class.java
            ).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(DefaultApplication.context, 0, intent, 0)

        val builder = NotificationCompat.Builder(DefaultApplication.context, CHANNEL_ID)

            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setContentTitle("Restaurant betreten:" + restaurant.name)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Du hast das Restaurant \"Francesca & Fratelli\" betreten. Möchtest du automatisch deine Daten übertragen?")//TODO Dynamisch den Restaurantnamen übernehmen
            )

        with(NotificationManagerCompat.from(DefaultApplication.context)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }

    }
}