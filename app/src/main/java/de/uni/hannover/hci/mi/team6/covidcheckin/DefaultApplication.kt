package de.uni.hannover.hci.mi.team6.covidcheckin

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp

/**
 * Application used to provide the application context in a static way
 * @author Elias
 */
class DefaultApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        FirebaseApp.initializeApp(context)
    }

    companion object {
        lateinit var context: Context
            private set
    }
}