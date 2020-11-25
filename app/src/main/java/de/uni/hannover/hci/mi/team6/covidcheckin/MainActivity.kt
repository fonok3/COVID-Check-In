package de.uni.hannover.hci.mi.team6.covidcheckin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import de.uni.hannover.hci.mi.team6.covidcheckin.ui.main.ExportSheetFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {

            val exportSheetFragment = ExportSheetFragment();
            exportSheetFragment.show(supportFragmentManager, "ExportSheet")

        }
    }
}
