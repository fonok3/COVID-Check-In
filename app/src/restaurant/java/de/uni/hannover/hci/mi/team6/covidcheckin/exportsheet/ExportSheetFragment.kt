package de.uni.hannover.hci.mi.team6.covidcheckin.exportsheet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import de.uni.hannover.hci.mi.team6.covidcheckin.DefaultApplication
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.services.ServicesModule
import kotlinx.android.synthetic.restaurant.export_sheet_layout.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * add to activity
 *
 * val exportSheet = ExportSheetFragment()
 * exportSheet.show(supportFragmentManager, "ExportSheet")
 */
class ExportSheetFragment : BottomSheetDialogFragment() {

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
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        val intent = Intent(Intent.ACTION_SEND)
        val devInfoFile = ServicesModule.visitorDatabaseService.getCSVFile()
        var devInfoUri: Uri? = null
        try {
            devInfoUri = FileProvider.getUriForFile(
                DefaultApplication.context,
                "de.uni.hannover.hci.mi.team6.covidcheckin.fileprovider",
                devInfoFile
            )
        } catch (e: java.lang.Exception) {
        }
        intent.putExtra(Intent.EXTRA_STREAM, devInfoUri)
        emailIntent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(healthDeptMail))
        intent.putExtra(
            Intent.EXTRA_SUBJECT,
            "Registerdaten vom ${ServicesModule.restaurantDataService.currentRestaurantData?.restaurantName} Restaurant"
        )
        var formatter = SimpleDateFormat("dd.MM.yyyy")
        val date = formatter.format(Calendar.getInstance().time)
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "Sehr geehrte Damen und Herren,\n\nanbei sind die Registerdaten vom ${ServicesModule.restaurantDataService.currentRestaurantData?.restaurantName} Restaurant am $date.\n\nMit freundlichen Grüßen."
        );
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.selector = emailIntent
        startActivity(Intent.createChooser(intent, "E-Mail App wählen"))
    }

    private fun saveInternal() {
        Toast.makeText(context, "save intenal", Toast.LENGTH_LONG).show()
    }
}