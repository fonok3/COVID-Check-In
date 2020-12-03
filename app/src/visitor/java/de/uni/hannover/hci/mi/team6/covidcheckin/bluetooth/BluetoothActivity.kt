package de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import de.uni.hannover.hci.mi.team6.covidcheckin.DefaultApplication
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.ui.BluetoothFragment
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.ui.BluetoothListFragment
import de.uni.hannover.hci.mi.team6.covidcheckin.contactForm.ContactFormActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.enterRestaurant.EnterRestaurantActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.permission.PermissionsActivity
import kotlinx.android.synthetic.main.bluetooth_activity.*

class BluetoothActivity : AppCompatActivity() {

    var bluetoothActive: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bluetooth_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(bluetoothLayout.id, BluetoothFragment.newInstance())
                .commitNow()
        }
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bluetooth_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_info -> {
            val intent = Intent(this, PermissionsActivity::class.java)
            intent.putExtra(PermissionsActivity.AUTO_FORWARD, false)
            startActivity(intent)
            true
        }
        R.id.action_edit -> {
            val intent = Intent(this, ContactFormActivity::class.java)
            intent.putExtra(ContactFormActivity.AUTO_FORWARD, false)
            startActivity(intent)
            true
        }
        else -> false
    }

    fun changeToList() {
        supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.right_enter,
            R.anim.left_exit
        )
            .replace(bluetoothLayout.id, BluetoothListFragment.newInstance()).commitNow()
    }


    fun changeToBluetooth() {
        supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.left_enter,
            R.anim.right_exit
        )
            .replace(bluetoothLayout.id, BluetoothFragment.newInstance()).commitNow()
    }

    override fun onStop() {
        super.onStop()
        notificationTest()
    }

    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 101

    /**
     * GEHÖRT HIER NICHT HIN, IST NUR ZUM AUSPROBIEREN BEVOR DIE NOTIFICATION AN DER RICHTIGEN STELLE ERSTELLT WIRD
     */
    private fun notificationTest() {
        createNotificationChannel()

        val intent =
            Intent(
                this,
                EnterRestaurantActivity::class.java
            ).apply { //TODO Link zur richtigen Activity
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(DefaultApplication.context, 0, intent, 0)

        val builder = NotificationCompat.Builder(DefaultApplication.context, CHANNEL_ID)

            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setContentTitle("Restaurant betreten")
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
