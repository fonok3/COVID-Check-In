package de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.uni.hannover.hci.mi.team6.covidcheckin.R
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
        super.onActivityCreated(savedInstanceState)
        /*toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Change the app background color
            } else {
                // Set the app background color to gray
            }
        }*/
        button.setOnClickListener {
            (context as BluetoothActivity).changeToList()
        }
    }
}
