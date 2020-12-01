package de.uni.hannover.hci.mi.team6.covidcheckin.contactForm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.ui.BluetoothFragment
import de.uni.hannover.hci.mi.team6.covidcheckin.model.CustomerPersonalData
import de.uni.hannover.hci.mi.team6.covidcheckin.services.ServicesModule
import kotlinx.android.synthetic.main.fragment_contact_form.*


/**
 * This fragment will be added to the activity contactFormActivity. The user will give his data as an input the the data will be
 * saved to CustomerPersonalDataService.
 * @author Anmar
 * */

class ContactFormFragment : Fragment() {
    companion object {
        fun newInstance() = BluetoothFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact_form, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        form_save_button.setOnClickListener {

            val firstName = this.editText_Vorname.toString()
            val sectionName = this.editText_Nachname.toString()
            val street = this.editText_strasse.toString()
            val streetNumber = this.editText_hausnummer.toString()
            val zipCode = this.editText_plz.toString()
            val city = this.editText_stadt.toString()
            val phoneNumber = this.editText_telefone.toString()

            ServicesModule.customerPersonalDataService.save(
                CustomerPersonalData(
                    firstName,
                    sectionName,
                    street,
                    streetNumber,
                    zipCode,
                    city,
                    phoneNumber
                )
            )

        }
    }


}