package com.foursquare.pilgrimsdk.sampleapp

import android.app.Application
import com.adobe.marketing.mobile.*
import com.foursquare.pilgrimsdk.adobe.PilgrimExtension

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        MobileCore.setApplication(this)
        MobileCore.setLogLevel(LoggingMode.DEBUG)

        try {
            PilgrimExtension.registerExtension()
            UserProfile.registerExtension()
            Identity.registerExtension()
            Lifecycle.registerExtension()
            Signal.registerExtension()
            MobileCore.start { MobileCore.configureWithAppID("launch-EN99764bda0f974903a7d85524510c889b-staging") }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}