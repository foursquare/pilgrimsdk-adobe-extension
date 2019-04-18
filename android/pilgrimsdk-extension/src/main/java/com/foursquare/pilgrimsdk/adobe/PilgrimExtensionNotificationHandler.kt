package com.foursquare.pilgrimsdk.adobe

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.adobe.marketing.mobile.Event
import com.adobe.marketing.mobile.ExtensionError
import com.adobe.marketing.mobile.ExtensionErrorCallback
import com.adobe.marketing.mobile.MobileCore
import com.foursquare.api.types.geofence.GeofenceEventType
import com.foursquare.pilgrim.PilgrimNotificationHandler
import com.foursquare.pilgrim.PilgrimSdkBackfillNotification
import com.foursquare.pilgrim.PilgrimSdkGeofenceEventNotification
import com.foursquare.pilgrim.PilgrimSdkVisitNotification

class PilgrimExtensionNotificationHandler : PilgrimNotificationHandler() {

    private val errorCallback = ExtensionErrorCallback<ExtensionError> { extensionError ->
        Log.e("Extensions", String.format("An error occurred while dispatching an even %d %s",
                extensionError.errorCode, extensionError.errorName))
    }

    override fun handleVisit(context: Context, notification: PilgrimSdkVisitNotification) {
        if (notification.visit.hasDeparted()) {
            MobileCore.dispatchEvent(PilgrimExtensionEvents.getDepartureEvent(notification.visit), errorCallback)
        } else {
            MobileCore.dispatchEvent(PilgrimExtensionEvents.getArrivalEvent(notification.visit), errorCallback)
        }
    }

    override fun handleBackfillVisit(context: Context, notification: PilgrimSdkBackfillNotification) {
        MobileCore.dispatchEvent(PilgrimExtensionEvents.getHistoricalEvent(notification.visit), errorCallback)
    }

    override fun handleGeofenceEventNotification(context: Context, notification: PilgrimSdkGeofenceEventNotification) {
        notification.geofenceEvents.forEach { geofence ->
            val event: Event? = when (geofence.geofenceEventType) {
                GeofenceEventType.ENTRANCE -> PilgrimExtensionEvents.getGeofenceEnterEvent(geofence)
                GeofenceEventType.DWELL -> PilgrimExtensionEvents.getGeofenceDwellEvent(geofence)
                GeofenceEventType.VENUE_CONFIRMED -> PilgrimExtensionEvents.getGeofenceConfirmEvent(geofence)
                GeofenceEventType.EXIT -> PilgrimExtensionEvents.getGeofenceExitEvent(geofence)
                else -> null
            }

            if (event != null) {
                MobileCore.dispatchEvent(event, errorCallback)
            }
        }
    }
}