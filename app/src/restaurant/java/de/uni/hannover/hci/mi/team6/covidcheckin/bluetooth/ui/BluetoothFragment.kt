package de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.ui

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.beacon.RestaurantBeacon
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.BluetoothActivity
import kotlinx.android.synthetic.main.bluetooth_fragment.*

class BluetoothFragment : Fragment() {
    
    companion object {
        fun newInstance() = BluetoothFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.bluetooth_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val animatorInner: ObjectAnimator = ObjectAnimator.ofFloat(animationInner, "alpha", 1f).apply {
            duration = 1000
            repeatCount = Animation.INFINITE
        }
        val animatorOuter : ObjectAnimator = ObjectAnimator.ofFloat(animationOuter, "alpha", 1f).apply {
            startDelay = 500
            duration = 1000
            repeatCount = Animation.INFINITE
        }
        super.onActivityCreated(savedInstanceState)
        floatingActionButton.setOnClickListener {
            if ((this.activity as BluetoothActivity).bluetoothActive) {
                floatingActionButton.alpha = 0.5f
                animationInner.alpha = 0.0f
                animationOuter.alpha = 0.0f
                animatorInner.cancel()
                animatorOuter.cancel()
                (this.activity as BluetoothActivity).bluetoothActive = false

                RestaurantBeacon.stop()
            } else {
                floatingActionButton.alpha = 1f
                (this.activity as BluetoothActivity).bluetoothActive = true
                animatorInner.start()
                animatorOuter.start()

                RestaurantBeacon.start()
            }
        }
    }
}
