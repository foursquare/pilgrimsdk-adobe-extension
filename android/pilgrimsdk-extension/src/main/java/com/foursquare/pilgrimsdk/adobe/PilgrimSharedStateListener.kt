package com.foursquare.pilgrimsdk.adobe

import android.util.Log
import com.adobe.marketing.mobile.Event
import com.adobe.marketing.mobile.ExtensionApi
import com.adobe.marketing.mobile.ExtensionError
import com.adobe.marketing.mobile.ExtensionErrorCallback
import com.adobe.marketing.mobile.ExtensionListener
import com.adobe.marketing.mobile.MobileCore
import com.foursquare.pilgrim.LogLevel
import com.foursquare.pilgrim.PilgrimSdk

class PilgrimSharedStateListener(val extensionApi: ExtensionApi, type: String, source: String)
    : ExtensionListener(extensionApi, type, source) {

    override fun hear(event: Event?) {
        val errorCallback = ExtensionErrorCallback<ExtensionError> { extensionError -> Log.e("PilgrimSharedState", String.format("An error occurred while retrieving the shared state for configuration %d %s", extensionError.errorCode, extensionError.errorName)) }

        val configurationSharedState = extensionApi.getSharedEventState(PilgrimConstants.CONFIGURATION, event, errorCallback)
        //Use Secret keys from 'configurationSharedState' to start Pilgrim
        val key = configurationSharedState[PilgrimConstants.PILGRIM_CONSUMER_KEY] as String?
        val secret = configurationSharedState[PilgrimConstants.PILGRIM_CONSUMER_SECRET] as String?

        if (!key.isNullOrEmpty() && !secret.isNullOrEmpty()) {
            PilgrimSdk.with(
                    PilgrimSdk.Builder(MobileCore.getApplication())
                            .logLevel(LogLevel.DEBUG)
                            .enableDebugLogs()
                            .consumer(key, secret)
                            .notificationHandler(PilgrimExtensionNotificationHandler())
            )
        }
    }
}