package de.uni.hannover.hci.mi.team6.covidcheckin.contactForm.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.BluetoothActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.model.CustomerPersonalData
import de.uni.hannover.hci.mi.team6.covidcheckin.services.ServicesModule
import kotlinx.android.synthetic.visitor.fragment_contact_form.*


/**
 * This fragment will be added to the activity contactFormActivity. The user will give his data as an input the the data will be
 * saved to CustomerPersonalDataService.
 * @author Anmar
 * */

class ContactFormFragment : Fragment() {
    companion object {
        fun newInstance() = ContactFormFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact_form, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)




        weiter_button.setOnClickListener {


            val firstName = this.editText_Vorname.text.toString().trim()
            val secondName = this.editText_Nachname.text.toString().trim()
            val street = this.editText_strasse.text.toString().trim()
            val streetNumber = this.editText_hausnummer.text.toString().trim()
            val zipCode = this.editText_plz.text.toString().trim()
            val city = this.editText_stadt.text.toString().trim()
            val phoneNumber = this.editText_telefone.text.toString().trim()

            /**
             * Returns if not all Data were given
             */

            if (firstName.isEmpty() or secondName.isEmpty() or street.isEmpty() or streetNumber.isEmpty() or zipCode.isEmpty() or city.isEmpty() or phoneNumber.isEmpty()) {
                Toast.makeText(activity, "Bitte Geben Sie alle Ihre Daten ein.", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            ServicesModule.customerPersonalDataService.save(
                CustomerPersonalData(
                    firstName,
                    secondName,
                    street,
                    streetNumber,
                    zipCode,
                    city,
                    phoneNumber
                )
            )


            /**
             * To go to next Activity, the user must confirm the checkbox.
             */

            if (checkBox.isChecked()) {
                val intent = Intent(activity, BluetoothActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    activity,
                    "Bitte akzeptieren Sie die Datenschutzerklärung.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }


}