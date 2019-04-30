//
//  PilgrimEvents.m
//  PilgrimAdobeExtension
//
//  Created by Carlos Reyes  on 4/15/19.
//  Copyright © 2019 Foursquare. All rights reserved.
//

#import "PilgrimEvents.h"
#import "PilgrimConstants.h"

@implementation PilgrimEvents

+ (ACPExtensionEvent *)getVisitEventFor:(FSQPVisit *)visit name:(NSString *)name eventType:(NSString *)eventType vistType:(NSString *)visitType {
    NSMutableDictionary* eventData = [self buildBasicVisitEventFor:visit];
    eventData[visitType] = visitType;
    return [self buildAdobeEventWith:name type:eventType source:eventSource data:eventData];
}

+ (ACPExtensionEvent *)getGeofenceEventFor:(FSQPGeofenceEvent *)geofence name:(NSString *)name type:(NSString *)type {
    return [self buildAdobeEventWith:name type:type source:eventSource data:[self buildBasicGeofenceEventFor:geofence]];
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

    eventData[visitId] = visit.pilgrimVisitId;
    eventData[confidence] = [NSNumber numberWithInt:visit.confidence];
    eventData[lat] = [NSNumber numberWithDouble:visit.arrivalLocation.coordinate.latitude];
    eventData[lng] = [NSNumber numberWithDouble:visit.arrivalLocation.coordinate.longitude];
    eventData[timestamp] = [dateFormatter stringFromDate:visit.arrivalDate];
    eventData[locationType] = [NSNumber numberWithInt:visit.locationType];

    if (visit.venue != nil) {
        FSQPVenue* venue = visit.venue;
        eventData[venueId] = venue.foursquareID;
        eventData[venueName] = venue.name;
        eventData[address] = venue.locationInformation.address;
        eventData[crossStreet] = venue.locationInformation.crossStreet;
        eventData[city] = venue.locationInformation.city;
        eventData[state] = venue.locationInformation.state;
        eventData[zipCode] = venue.locationInformation.postalCode;
        eventData[country] = venue.locationInformation.country;
        eventData[primaryCategoryId] = venue.primaryCategory.foursquareID;
        eventData[primaryChainName] = venue.primaryCategory.name;
        eventData[probability] = venue.probability;
        eventData[primaryChainId] = venue.chains.firstObject.foursquareID;
        eventData[primaryChainName] = venue.chains.firstObject.name;

        if (venue.hierarchy.firstObject != nil) {
            FSQPVenue* superVenue = venue.hierarchy.firstObject;
            eventData[supervenueId] = superVenue.foursquareID;
            eventData[supervenueName] = superVenue.name;
            eventData[supervenuePrimaryCategoryId] = superVenue.categories.firstObject.foursquareID;
            eventData[supervenuePrimaryCategoryName] = superVenue.categories.firstObject.name;
        }
    }

    return eventData;
}

+ (NSMutableDictionary<NSString*, id> *)buildBasicGeofenceEventFor:(FSQPGeofenceEvent *)geofence {
    NSMutableDictionary* eventData = [[NSMutableDictionary alloc] init];

    eventData[geofenceEventType] = [NSNumber numberWithInt:geofence.eventType];
    eventData[eventLat] = [NSNumber numberWithDouble:geofence.location.coordinate.latitude];
    eventData[eventLng] = [NSNumber numberWithDouble:geofence.location.coordinate.longitude];
    eventData[partnerVenueId] = geofence.partnerVenueID;
    eventData[venueChainIds] = [[geofence.venue.chains valueForKey:@"foursquareID"] componentsJoinedByString:@","];
    eventData[catergoryIds] = [[geofence.venue.categories valueForKey:@"foursquareID"] componentsJoinedByString:@","];

    return eventData;

}

@end
