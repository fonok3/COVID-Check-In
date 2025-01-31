package de.uni.hannover.hci.mi.team6.covidcheckin.permission.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.restaurantContactForm.RestaurantContactFormActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.services.ServicesModule
import de.uni.hannover.hci.mi.team6.covidcheckin.services.permissions.PermissionsService
import kotlinx.android.synthetic.restaurant.permissions_fragment.*


class PermissionsFragment : Fragment(),
    CompoundButton.OnCheckedChangeListener,
    PermissionsService.ChangedListener {

    companion object {
        fun newInstance() = PermissionsFragment()
    }

    private val service: PermissionsService by lazy {
        ServicesModule.permissionsService
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.permissions_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        refreshSwitches()

        location_switch.setOnCheckedChangeListener(this)
        bluetooth_switch.setOnCheckedChangeListener(this)

        continue_button.setOnClickListener {
            val intent = Intent(activity, RestaurantContactFormActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (!isChecked) { return }

        if (buttonView == location_switch)
            service.enableLocation(requireActivity())

        if (buttonView == bluetooth_switch)
            service.enableBluetooth(requireActivity())

        refreshSwitches()
    }

    override fun permissionDidChange(permission: PermissionsService.Permission) {
        refreshSwitches()
    }

    private fun refreshSwitches() {
        location_switch.isChecked = service.isLocationEnabled
        bluetooth_switch.isChecked = service.isBluetoothEnabled

        continue_button.setBackgroundResource(R.color.colorPrimaryDark)
        continue_button.isEnabled = service.isLocationEnabled
                && service.isBluetoothEnabled
    }

}

