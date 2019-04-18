//  Copyright © 2019 Foursquare. All rights reserved.

#import "PilgrimEvents.h"
#import "PilgrimConstants.h"

@implementation PilgrimEvents

+ (ACPExtensionEvent *)getVisitEventFor:(FSQPVisit *)visit name:(NSString *)name eventType:(NSString *)eventType vistType:(NSString *)visitType {
    NSMutableDictionary* eventData = [self buildBasicVisitEventFor:visit];
    eventData[VISIT_TYPE] = visitType;
    return [self buildAdobeEventWith:name type:eventType source:EVENT_SOURCE data:eventData];
}

+ (ACPExtensionEvent *)getGeofenceEventFor:(FSQPGeofenceEvent *)geofence name:(NSString *)name type:(NSString *)type {
    return [self buildAdobeEventWith:name type:type source:EVENT_SOURCE data:[self buildBasicGeofenceEventFor:geofence]];
}

+ (ACPExtensionEvent *)buildAdobeEventWith:(NSString *)name type:(NSString *)type source:(NSString *)source data:(nullable NSDictionary *)data {
    NSError* error = nil;
    ACPExtensionEvent* event = [ACPExtensionEvent extensionEventWithName:name type:type source:source data:data error:&error];

    if (error) {
        NSLog(@"Error creating %@ event %@:%ld", name, [error domain], [error code]);
    }

    return event;
}

+ (NSMutableDictionary<NSString*, id> *)buildBasicVisitEventFor:(FSQPVisit *)visit {
    NSMutableDictionary* eventData = [[NSMutableDictionary alloc] init];
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"MM-dd-YYYY HH:mm:ss zzz"];

    eventData[VISIT_ID] = visit.pilgrimVisitId;
    eventData[CONFIDENCE] = [NSNumber numberWithInt:visit.confidence];
    eventData[LAT] = [NSNumber numberWithDouble:visit.arrivalLocation.coordinate.latitude];
    eventData[LNG] = [NSNumber numberWithDouble:visit.arrivalLocation.coordinate.longitude];
    eventData[TIMESTAMP] = [dateFormatter stringFromDate:visit.arrivalDate];
    eventData[LOCATION_TYPE] = [NSNumber numberWithInt:visit.locationType];

    if (visit.venue != nil) {
        FSQPVenue* venue = visit.venue;
        eventData[VENUE_ID] = venue.foursquareID;
        eventData[VENUE_NAME] = venue.name;
        eventData[ADDRESS] = venue.locationInformation.address;
        eventData[CROSSSTREET] = venue.locationInformation.crossStreet;
        eventData[CITY] = venue.locationInformation.city;
        eventData[STATE] = venue.locationInformation.state;
        eventData[ZIP_CODE] = venue.locationInformation.postalCode;
        eventData[COUNTRY] = venue.locationInformation.country;
        eventData[PRIMARY_CATEGORY_ID] = venue.primaryCategory.foursquareID;
        eventData[PRIMARY_CATEGORY_NAME] = venue.primaryCategory.name;
        eventData[PROBABILITY] = venue.probability;
        eventData[PRIMARY_CHAIN_ID] = venue.chains.firstObject.foursquareID;
        eventData[PRIMARY_CHAIN_NAME] = venue.chains.firstObject.name;

        if (venue.hierarchy.firstObject != nil) {
            FSQPVenue* superVenue = venue.hierarchy.firstObject;
            eventData[SUPERVENUE_ID] = superVenue.foursquareID;
            eventData[SUPERVENUE_NAME] = superVenue.name;
            eventData[SUPERVENUE_PRIMARY_CATEGORY_ID] = superVenue.categories.firstObject.foursquareID;
            eventData[SUPERVENUE_PRIMARY_CATEGORY_NAME] = superVenue.categories.firstObject.name;
        }
    }

    return eventData;
}

+ (NSMutableDictionary<NSString*, id> *)buildBasicGeofenceEventFor:(FSQPGeofenceEvent *)geofence {
    NSMutableDictionary* eventData = [[NSMutableDictionary alloc] init];

    eventData[GEOFENCE_EVENT_TYPE] = [NSNumber numberWithInt:geofence.eventType];
    eventData[EVENT_LAT] = [NSNumber numberWithDouble:geofence.location.coordinate.latitude];
    eventData[EVENT_LNG] = [NSNumber numberWithDouble:geofence.location.coordinate.longitude];
    eventData[PARTNER_VENUE_ID] = geofence.partnerVenueID;
    eventData[VENUE_CHAIN_IDS] = [[geofence.venue.chains valueForKey:@"foursquareID"] componentsJoinedByString:@","];
    eventData[CATERGORY_IDS] = [[geofence.venue.categories valueForKey:@"foursquareID"] componentsJoinedByString:@","];

    return eventData;

}

@end
