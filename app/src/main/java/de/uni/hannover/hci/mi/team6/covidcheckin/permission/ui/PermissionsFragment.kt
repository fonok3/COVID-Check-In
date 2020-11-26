package de.uni.hannover.hci.mi.team6.covidcheckin.permission.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.permission.repository.AndroidPermissionsRepository
import de.uni.hannover.hci.mi.team6.covidcheckin.permission.repository.PermissionsRepository
import kotlinx.android.synthetic.main.permissions_fragment.*


class PermissionsFragment : Fragment(),
    CompoundButton.OnCheckedChangeListener,
    PermissionsRepository.ChangedListener {

    companion object {
        fun newInstance() = PermissionsFragment()
    }

    private lateinit var repository: PermissionsRepository

    private val viewModel: PermissionsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.permissions_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { repository = AndroidPermissionsRepository(it) }

        refreshSwitches()

        notifications_switch.setOnCheckedChangeListener(this)
        location_switch.setOnCheckedChangeListener(this)
        bluetooth_switch.setOnCheckedChangeListener(this)
    }
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (!isChecked) { return }

        if (buttonView == notifications_switch)
            repository.enableNotifications()

        if (buttonView == location_switch)
            repository.enableLocation()

        if (buttonView == bluetooth_switch)
            repository.enableBluetooth()

        refreshSwitches()
    }

    override fun permissionDidChange(permission: PermissionsRepository.Permission) {
        refreshSwitches()
    }

    private fun refreshSwitches() {
        notifications_switch.isChecked = repository.areNotificationsEnabled
        location_switch.isChecked = repository.isLocationEnabled
        bluetooth_switch.isChecked = repository.isBluetoothEnabled

        continue_button.setBackgroundResource(R.color.colorPrimaryDark)
        continue_button.isEnabled = repository.areNotificationsEnabled
                && repository.isLocationEnabled
                && repository.isBluetoothEnabled
    }

}

