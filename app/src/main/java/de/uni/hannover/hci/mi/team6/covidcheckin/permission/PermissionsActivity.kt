package de.uni.hannover.hci.mi.team6.covidcheckin.permission

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.uni.hannover.hci.mi.team6.covidcheckin.R
import de.uni.hannover.hci.mi.team6.covidcheckin.permission.ui.PermissionsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PermissionsFragment.newInstance())
                .commitNow()
        }
    }
}