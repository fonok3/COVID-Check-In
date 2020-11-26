package de.uni.hannover.hci.mi.team6.covidcheckin.exportsheet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import kotlinx.android.synthetic.main.export_sheet_layout.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * add to activity
 *
 * val exportSheet = ExportSheetFragment()
 * exportSheet.show(supportFragmentManager, "ExportSheet")
 */
class ExportSheetFragment : BottomSheetDialogFragment() {

    private val restaurantName = "Muster"
    private val healthDeptMail = "gesundheitsamt@email.de"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.export_sheet_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        send_email.setOnClickListener {
            sendEmail()
        }

        save_internal.setOnClickListener {
            saveInternal()
        }
    }

    private fun sendEmail() {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Registerdaten vom $restaurantName Restaurant");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(healthDeptMail))
        var formatter = SimpleDateFormat("dd.MM.yyyy")
        val date = formatter.format(Calendar.getInstance().time)
        emailIntent.putExtra(Intent.EXTRA_TEXT,
            "Sehr geehrte Damen und Herren,\n\nanbei sind die Registerdaten vom $restaurantName Restaurant am $date.\n\nMit freudnlichen Grüßen.");

        startActivity(Intent.createChooser(emailIntent, "E-Mail App wählen"));
    }

    private fun saveInternal() {
        Toast.makeText(context, "save intenal", Toast.LENGTH_LONG).show()
    }
}