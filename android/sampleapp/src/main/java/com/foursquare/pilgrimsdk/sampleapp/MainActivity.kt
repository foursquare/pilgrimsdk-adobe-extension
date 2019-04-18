package com.foursquare.pilgrimsdk.sampleapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.adobe.marketing.mobile.Event
import com.adobe.marketing.mobile.MobileCore
import com.foursquare.pilgrimsdk.adobe.PilgrimConstants


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Adobe Extension testing
        val eventData = HashMap<String, Any>()

        eventData["id"] = "test"

        val event = Event.Builder(
            PilgrimConstants.HISTORICAL_EVENT_NAME,
            PilgrimConstants.HISTORICAL_EVENT_TYPE,
            PilgrimConstants.EVENT_SOURCE
        )
            .setEventData(eventData).build()

        MobileCore.dispatchEventWithResponseCallback(
            event,
            { data -> "Adobe Response Callback" + data.name },
            { "Adobe error while dispatching event" })

    }
}
