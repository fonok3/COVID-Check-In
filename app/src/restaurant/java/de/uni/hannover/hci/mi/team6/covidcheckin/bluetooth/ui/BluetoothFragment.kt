package de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.ui

import android.animation.ObjectAnimator
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.beacon.RestaurantBeacon
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.BluetoothActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.transfer.BluetoothDataReceiver
import de.uni.hannover.hci.mi.team6.covidcheckin.services.ServicesModule
import de.uni.hannover.hci.mi.team6.covidcheckin.services.restaurantInfo.RestaurantInfoService
import de.uni.hannover.hci.mi.team6.covidcheckin.visitorManualContactForm.VisitorManualContactFormActivity
import kotlinx.android.synthetic.main.bluetooth_fragment.animationInner
import kotlinx.android.synthetic.main.bluetooth_fragment.animationOuter
import kotlinx.android.synthetic.main.bluetooth_fragment.floatingActionButton
import kotlinx.android.synthetic.restaurant.bluetooth_fragment.*

class BluetoothFragment : Fragment() {

    private var bltReceiver: BluetoothDataReceiver? = null
    private val restaurantDataService: RestaurantInfoService by lazy {
        ServicesModule.localRestaurantDataService
    }


    companion object {
        fun newInstance() = BluetoothFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.bluetooth_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val animatorInner: ObjectAnimator =
            ObjectAnimator.ofFloat(animationInner, "alpha", 1f).apply {
                duration = 1000
                repeatCount = Animation.INFINITE
            }
        val animatorOuter: ObjectAnimator =
            ObjectAnimator.ofFloat(animationOuter, "alpha", 1f).apply {
                startDelay = 500
                duration = 1000
                repeatCount = Animation.INFINITE
            }
        super.onActivityCreated(savedInstanceState)

        manualInsertButton.setOnClickListener {
            val intent = Intent(activity, VisitorManualContactFormActivity::class.java)
            startActivity(intent)
        }

        floatingActionButton.setOnClickListener {
            if ((this.activity as BluetoothActivity).bluetoothActive) {
                floatingActionButton.alpha = 0.5f
                animationInner.alpha = 0.0f
                animationOuter.alpha = 0.0f
                animatorInner.cancel()
                animatorOuter.cancel()
                tipp.visibility = View.VISIBLE;
                tippArrow.visibility = View.VISIBLE;

                (this.activity as BluetoothActivity).bluetoothActive = false

                RestaurantBeacon.stop()

                bltReceiver!!.stop()    // stop listening to visitor device
            } else {
                floatingActionButton.alpha = 1f
                (this.activity as BluetoothActivity).bluetoothActive = true
                animatorInner.start()
                animatorOuter.start()
                tipp.visibility = View.INVISIBLE;
                tippArrow.visibility = View.INVISIBLE;

                RestaurantBeacon.start()

                restaurantDataService.restaurantInfo?.name.let {
                    // set device name to restaurant name
                    BluetoothAdapter.getDefaultAdapter().name = it
                    Log.d("BLTName", it + "- " + BluetoothAdapter.getDefaultAdapter().name)

                    // start listening to visitor device
                    bltReceiver = BluetoothDataReceiver((this.activity as BluetoothActivity))
                    bltReceiver!!.start()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        bltReceiver!!.stop()
    }
}
