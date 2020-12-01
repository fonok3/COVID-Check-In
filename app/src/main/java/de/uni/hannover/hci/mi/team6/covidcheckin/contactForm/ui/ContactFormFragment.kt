package de.uni.hannover.hci.mi.team6.covidcheckin.contactForm.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.bluetooth.BluetoothActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.model.CustomerPersonalData
import de.uni.hannover.hci.mi.team6.covidcheckin.permission.ui.PermissionsFragment
import de.uni.hannover.hci.mi.team6.covidcheckin.services.customerPersonalData.CustomerPersonalDataService
import kotlinx.android.synthetic.main.fragment_contact_form.*
import kotlinx.android.synthetic.main.permissions_fragment.*
import kotlinx.android.synthetic.main.permissions_fragment.continue_button


/**
 * This fragment will be added to the activity contactFormActivity. The user will give his data as an input the the data will be
 * saved to CustomerPersonalDataService.
 * @author Anmar
 * */



class ContactFormFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        form_save_button.setOnClickListener{
        val vorname = this.editText_Vorname.toString()
        val nachname = this.editText_Nachname.toString()
        val nutzer_strasse = this.editText_strasse.toString()
        val nutzer_strasse_num = this.editText_hausnummer.toString()
        val nutzer_plz = this.editText_plz.toString()
        val nutzer_stadt = this.editText_stadt.toString()
        val nutzer_telefonnummer = this.editText_telefone.toString()
            CustomerPersonalDataService.currentUserPersonalData= CustomerPersonalData(vorname,nachname,nutzer_strasse,nutzer_strasse_num,nutzer_plz,nutzer_stadt,nutzer_telefonnummer)		            CustomerPersonalDataService.currentUserPersonalData= CustomerPersonalData(vorname,nachname,nutzer_strasse,nutzer_strasse_num,nutzer_plz,nutzer_stadt,nutzer_telefonnummer)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        form_save_button.setOnClickListener {
            val intent = Intent(activity, BluetoothActivity::class.java)
            startActivity(intent)
        }
    }
}