package de.uni.hannover.hci.mi.team6.covidcheckin.contactForm.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.BluetoothActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.model.Address
import de.uni.hannover.hci.mi.team6.covidcheckin.model.Beacon
import de.uni.hannover.hci.mi.team6.covidcheckin.model.CustomerPersonalData
import de.uni.hannover.hci.mi.team6.covidcheckin.model.RestaurantInfo
import de.uni.hannover.hci.mi.team6.covidcheckin.services.ServicesModule
import kotlinx.android.synthetic.restaurant.fragment_contact_form.*


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
            val resturantName = this.editText_resturantname.text.toString().trim()
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
            if (resturantName.isEmpty()){

                var bac: GradientDrawable = editText_resturantname.background as GradientDrawable
                bac.setStroke(2, Color.RED)
                bac.setColor(getResources().getColor(R.color.editTextWarning))
            }
            else {
                var bac: GradientDrawable = editText_resturantname.background as GradientDrawable
                bac.setStroke(2, Color.BLACK)
                bac.setColor(getResources().getColor(R.color.editTextNormal))
            }

            if (firstName.isEmpty()){

                var bac: GradientDrawable = editText_Vorname.background as GradientDrawable
                bac.setStroke(2, Color.RED)
                bac.setColor(getResources().getColor(R.color.editTextWarning))
            }
            else {
                var bac: GradientDrawable = editText_Vorname.background as GradientDrawable
                bac.setStroke(2, Color.BLACK)
                bac.setColor(getResources().getColor(R.color.editTextNormal))
            }

            if (secondName.isEmpty()){


                var bac: GradientDrawable = editText_Nachname.background as GradientDrawable
                bac.setStroke(2, Color.RED)
                bac.setColor(getResources().getColor(R.color.editTextWarning))
            }
            else {
                var bac: GradientDrawable = editText_Nachname.background as GradientDrawable
                bac.setStroke(2, Color.BLACK)
                bac.setColor(getResources().getColor(R.color.editTextNormal))
            }

            if(street.isEmpty()){

                var bac: GradientDrawable = editText_strasse.background as GradientDrawable
                bac.setStroke(2, Color.RED)
                bac.setColor(getResources().getColor(R.color.editTextWarning))
            }
            else {
                var bac: GradientDrawable = editText_strasse.background as GradientDrawable
                bac.setStroke(2, Color.BLACK)
                bac.setColor(getResources().getColor(R.color.editTextNormal))
            }

            if(streetNumber.isEmpty()){

                var bac: GradientDrawable = editText_hausnummer.background as GradientDrawable
                bac.setStroke(2, Color.RED)
                bac.setColor(getResources().getColor(R.color.editTextWarning))
            }
            else {
                var bac: GradientDrawable = editText_hausnummer.background as GradientDrawable
                bac.setStroke(2, Color.BLACK)
                bac.setColor(getResources().getColor(R.color.editTextNormal))
            }

            if(zipCode.isEmpty()){

                var bac: GradientDrawable = editText_plz.background as GradientDrawable
                bac.setStroke(2, Color.RED)
                bac.setColor(getResources().getColor(R.color.editTextWarning))
            }
            else {
                var bac: GradientDrawable = editText_plz.background as GradientDrawable
                bac.setStroke(2, Color.BLACK)
                bac.setColor(getResources().getColor(R.color.editTextNormal))
            }

            if(city.isEmpty()){

                var bac: GradientDrawable = editText_stadt.background as GradientDrawable
                bac.setStroke(2, Color.RED)
                bac.setColor(getResources().getColor(R.color.editTextWarning))
            }
            else {
                var bac: GradientDrawable = editText_stadt.background as GradientDrawable
                bac.setStroke(2, Color.BLACK)
                bac.setColor(getResources().getColor(R.color.editTextNormal))
            }

            if(phoneNumber.isEmpty()){

                var bac: GradientDrawable = editText_telefone.background as GradientDrawable
                bac.setStroke(2, Color.RED)
                bac.setColor(getResources().getColor(R.color.editTextWarning))
            }
            else {
                var bac: GradientDrawable = editText_telefone.background as GradientDrawable
                bac.setStroke(2, Color.BLACK)
                bac.setColor(getResources().getColor(R.color.editTextNormal))
            }

            if (resturantName.isEmpty() or firstName.isEmpty() or secondName.isEmpty() or street.isEmpty() or streetNumber.isEmpty() or zipCode.isEmpty() or city.isEmpty() or phoneNumber.isEmpty()) {
                Toast.makeText(activity, "Bitte Geben Sie alle Ihre Daten ein.", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            ServicesModule.localCustomerPersonalDataService.save(
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

            ServicesModule.localRestaurantDataService.save(
                RestaurantInfo(
                    firstName,
                    Beacon(
                        ServicesModule.beaconServiceID,
                        ServicesModule.beaconMajor,
                        ServicesModule.beaconMajor
                    ),
                    Address(street, streetNumber, zipCode.toInt(), city)
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