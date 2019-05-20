package com.foursquare.pilgrimsdk.adobe

import android.util.Log
import com.adobe.marketing.mobile.*
import com.foursquare.pilgrim.LogLevel
import com.foursquare.pilgrim.PilgrimSdk

class PilgrimExtension(extensionApi: ExtensionApi) : Extension(extensionApi) {

    init {
        PilgrimSdk.with(
            PilgrimSdk.Builder(MobileCore.getApplication())
                .logLevel(LogLevel.DEBUG)
                .enableDebugLogs()
                .notificationHandler(PilgrimExtensionNotificationHandler())
        )

        val errorCallback = ExtensionErrorCallback<ExtensionError> { extensionError ->
            Log.e(
                "PilgrimExtension",
                String.format(
                    "An error occurred while registering PilgrimSharedStateListener %d %s",
                    extensionError.errorCode,
                    extensionError.errorName
                )
            )
        }

        api.registerEventListener(
            PilgrimConstants.EVENT_TYPE_ADOBE_HUB,
            PilgrimConstants.EVENT_SOURCE_ADOBE_SHARED_STATE,
            PilgrimSharedStateListener::class.java,
            errorCallback
        )
    }

    override fun getName(): String {
        return NAME
    }

    companion object {
        private const val NAME = "com.foursquare.pilgrim"

        fun registerExtension() {
            val errorCallback = ExtensionErrorCallback<ExtensionError> { extensionError ->
                Log.e(
                    "PilgrimExtension",
                    String.format(
                        "An error occurred while registering the PilgrimExtension %d %s",
                        extensionError.errorCode,
                        extensionError.errorName
                    )
                )
            }

            if (!MobileCore.registerExtension(PilgrimExtension::class.java, errorCallback)) {
                Log.e("PilgrimExtension", "Failed to register PilgrimExtension")
            }
        }

        fun updateKeys(key: String, secret: String){
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