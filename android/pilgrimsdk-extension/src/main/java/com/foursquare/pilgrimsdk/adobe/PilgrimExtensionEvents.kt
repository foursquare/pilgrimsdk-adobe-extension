package com.foursquare.pilgrimsdk.adobe

import com.adobe.marketing.mobile.Event
import com.foursquare.api.types.geofence.GeofenceEvent
import com.foursquare.api.types.geofence.area.CircularBoundary
import com.foursquare.pilgrim.Visit

object PilgrimExtensionEvents {

    fun getArrivalEvent(visit: Visit): Event {
        val eventData = buildBasicVisitEvent(visit)

        eventData[PilgrimConstants.VISIT_TYPE] = PilgrimConstants.ARRIVAL

        return Event.Builder(
            PilgrimConstants.ARRIVAL_EVENT_NAME,
            PilgrimConstants.ARRIVAL_EVENT_TYPE,
            PilgrimConstants.EVENT_SOURCE
        )
            .setEventData(eventData).build()
    }

    fun getDepartureEvent(visit: Visit): Event {
        val eventData = buildBasicVisitEvent(visit)

        eventData[PilgrimConstants.VISIT_TYPE] = PilgrimConstants.DEPARTURE

        return Event.Builder(
            PilgrimConstants.DEPARTURE_EVENT_NAME,
            PilgrimConstants.DEPARTURE_EVENT_TYPE,
            PilgrimConstants.EVENT_SOURCE
        )
            .setEventData(eventData).build()
    }

    fun getHistoricalEvent(visit: Visit): Event {
        val eventData = buildBasicVisitEvent(visit)

        eventData[PilgrimConstants.VISIT_TYPE] = PilgrimConstants.HISTORICAL

        return Event.Builder(
            PilgrimConstants.HISTORICAL_EVENT_NAME,
            PilgrimConstants.HISTORICAL_EVENT_TYPE,
            PilgrimConstants.EVENT_SOURCE
        )
            .setEventData(eventData).build()
    }

    fun getGeofenceEnterEvent(geofence: GeofenceEvent): Event {
        val eventData = buildBasicGeofenceEvent(geofence)

        return Event.Builder(
            PilgrimConstants.GEOFENCE_ENTER_EVENT_NAME,
            PilgrimConstants.GEOFENCE_ENTER_EVENT_TYPE,
            PilgrimConstants.EVENT_SOURCE
        )
            .setEventData(eventData).build()
    }

    fun getGeofenceDwellEvent(geofence: GeofenceEvent): Event {
        val eventData = buildBasicGeofenceEvent(geofence)

        return Event.Builder(
            PilgrimConstants.GEOFENCE_DWELL_EVENT_NAME,
            PilgrimConstants.GEOFENCE_DWELL_EVENT_TYPE,
            PilgrimConstants.EVENT_SOURCE
        )
            .setEventData(eventData).build()
    }

    fun getGeofenceConfirmEvent(geofence: GeofenceEvent): Event {
        val eventData = buildBasicGeofenceEvent(geofence)

        return Event.Builder(
            PilgrimConstants.GEOFENCE_CONFIRM_EVENT_NAME,
            PilgrimConstants.GEOFENCE_CONFIRM_EVENT_TYPE,
            PilgrimConstants.EVENT_SOURCE
        )
            .setEventData(eventData).build()
    }

    fun getGeofenceExitEvent(geofence: GeofenceEvent): Event {
        val eventData = buildBasicGeofenceEvent(geofence)

        return Event.Builder(
            PilgrimConstants.GEOFENCE_EXIT_EVENT_NAME,
            PilgrimConstants.GEOFENCE_EXIT_EVENT_TYPE,
            PilgrimConstants.EVENT_SOURCE
        )
            .setEventData(eventData).build()
    }

    private fun buildBasicVisitEvent(visit: Visit): HashMap<String, Any> {
        val eventData = HashMap<String, Any>()
        eventData[PilgrimConstants.VISIT_ID] = visit.pilgrimVisitId ?: ""
        eventData[PilgrimConstants.CONFIDENCE] = visit.confidence
        eventData[PilgrimConstants.LAT] = visit.location.lat
        eventData[PilgrimConstants.LNG] = visit.location.lng
        eventData[PilgrimConstants.TIMESTAMP] = visit.location.time
        eventData[PilgrimConstants.LOCATION_TYPE] = visit.type

        visit.venue?.let { venue ->
            eventData[PilgrimConstants.VENUE_ID] = venue.id
            eventData[PilgrimConstants.VENUE_NAME] = venue.name
            eventData[PilgrimConstants.ADDRESS] = venue.location.address ?: ""
            eventData[PilgrimConstants.CROSS_STREET] = venue.location.crossStreet ?: ""
            eventData[PilgrimConstants.CITY] = venue.location.city ?: ""
            eventData[PilgrimConstants.STATE] = venue.location.state ?: ""
            eventData[PilgrimConstants.ZIP_CODE] = venue.location.postalCode ?: ""
            eventData[PilgrimConstants.COUNTRY] = venue.location.country ?: ""
            eventData[PilgrimConstants.PRIMARY_CATEGORY_ID] = venue.primaryCategory?.id ?: ""
            eventData[PilgrimConstants.PRIMARY_CATEGORY_NAME] = venue.primaryCategory?.name ?: ""
            eventData[PilgrimConstants.PROBABILITY] = venue.probability
            eventData[PilgrimConstants.PRIMARY_CHAIN_ID] = venue.venueChains.firstOrNull()?.id
                ?: ""
            eventData[PilgrimConstants.PRIMARY_CHAIN_NAME] = venue.venueChains.firstOrNull()?.name
                ?: ""
            venue.hierarchy.firstOrNull()?.let { superVenue ->
                eventData[PilgrimConstants.SUPERVENUE_ID] = superVenue.id ?: ""
                eventData[PilgrimConstants.SUPERVENUE_NAME] = superVenue.name ?: ""
                eventData[PilgrimConstants.SUPERVENUE_PRIMARY_CATEGORY_ID] = superVenue.categories.firstOrNull()?.id
                    ?: ""
                eventData[PilgrimConstants.SUPERVENUE_PRIMARY_CATEGORY_NAME] = superVenue.categories.firstOrNull()?.name
                    ?: ""
            }
        }

        return eventData
    }

    private fun buildBasicGeofenceEvent(geofence: GeofenceEvent): HashMap<String, Any> {
        val eventData = HashMap<String, Any>()

        eventData[PilgrimConstants.GEOFENCE_EVENT_TYPE] = geofence.geofenceEventType.name
        eventData[PilgrimConstants.EVENT_LAT] = geofence.lat
        eventData[PilgrimConstants.EVENT_LNG] = geofence.lng
        eventData[PilgrimConstants.PARTNER_VENUE_ID] = geofence.partnerVenueId ?: ""
        eventData[PilgrimConstants.VENUE_CHAIN_IDS] = geofence.venue?.venueChains?.joinToString(", ") { it.id }
            ?: ""
        eventData[PilgrimConstants.CATEGORY_IDS] = geofence.venue?.categories
            ?.filter { !it.id.isNullOrEmpty() }
            ?.joinToString(", ") { it.id!! }
            ?: ""

        val boundary = geofence.boundary
        if (boundary is CircularBoundary) {
            eventData[PilgrimConstants.RADIUS] = boundary.radius
            eventData[PilgrimConstants.GEOFENCE_LAT] = boundary.center.latitude
            eventData[PilgrimConstants.GEOFENCE_LNG] = boundary.center.longitude
        }

        return eventData
    }
}