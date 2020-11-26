package de.uni.hannover.hci.mi.team6.covidcheckin.ui.bluetooth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import kotlinx.android.synthetic.main.bluetooth_list_fragment.*

class BluetoothListFragment : Fragment() {

    companion object {
        fun newInstance() = BluetoothListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.bluetooth_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        backButton.setOnClickListener {
            (context as BluetoothActivity).changeToBluetooth()
        }
    }
}
