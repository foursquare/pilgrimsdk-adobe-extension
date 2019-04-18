//  Copyright © 2019 Foursquare. All rights reserved.

#import <Foundation/Foundation.h>
#import "PilgrimAdobeExtension.h"
#import <ACPCore/ACPCore.h>
#import "PilgrimEvents.h"
#import "PilgrimConstants.h"
#import "EventListener.h"

@implementation PilgrimAdobeExtension

- (nullable NSString*) name {
    return [[NSBundle mainBundle] bundleIdentifier];
}

- (NSString *)version {
    return [FSQPPilgrimManager pilgrimSDKVersionString];
}

- (void)unexpectedError:(NSError *)error {
    NSLog(@"Unexpected error %@", error.localizedDescription);
}

- (instancetype)init {
    if (self = [super init]) {
        self.didRegisterPilgrim = false;

        NSError *error = nil;

        // register a listener for configuration events
        if ([self.api registerListener:[EventListener class]
                             eventType:@"com.adobe.eventType.hub"
                           eventSource:@"com.adobe.eventSource.sharedState"
                                 error:&error]) {
            NSLog(@"MyExtensionListener successfully registered for Event Hub Shared State events");
        } else if (error) {
            NSLog(@"An error occured while registering MyExtensionListener, error code: %ld", [error code]);
        }
    }
    return self;
}

+ (void)registerExtension {
    NSError *error = nil;
    [ACPCore registerExtension:[PilgrimAdobeExtension class] error:&error];

    if (error) {
        NSLog(@"Error Registering Pilgrim Extension %@:%ld", [error domain], [error code]);
    }
}

- (void)pilgrimManager:(nonnull FSQPPilgrimManager *)pilgrimManager handleVisit:(nonnull FSQPVisit *)visit {
    visit.hasDeparted ? [self sendAdobeEventWith:[PilgrimEvents getVisitEventFor:visit name:DEPARTURE_EVENT_NAME eventType:DEPARTURE_EVENT_TYPE vistType:DEPARTURE] eventType:@"visit departure"] : [self sendAdobeEventWith:[PilgrimEvents getVisitEventFor:visit name:ARRIVAL_EVENT_NAME eventType:ARRIVAL_EVENT_TYPE vistType:ARRIVAL] eventType:@"visit arrival"];
}

- (void)pilgrimManager:(FSQPPilgrimManager *)pilgrimManager handleBackfillVisit:(FSQPVisit *)visit {
    [self sendAdobeEventWith:[PilgrimEvents getVisitEventFor:visit name:HISTORICAL_EVENT_NAME eventType:HISTORICAL_EVENT_TYPE vistType:HISTORICAL] eventType:@"historical"];
}

- (void)pilgrimManager:(FSQPPilgrimManager *)pilgrimManager handleGeofenceEvents:(NSArray<FSQPGeofenceEvent *> *)geofenceEvents {
    for (FSQPGeofenceEvent* geofence in geofenceEvents) {
        switch (geofence.eventType) {
            case FSQPGeofenceEventTypeEntrance:
                [self sendAdobeEventWith:[PilgrimEvents getGeofenceEventFor:geofence name:GEOFENCE_ENTER_EVENT_NAME type:GEOFENCE_ENTER_EVENT_TYPE] eventType:@"geofence entrance"];
            case FSQPGeofenceEventTypeDwell:
                [self sendAdobeEventWith:[PilgrimEvents getGeofenceEventFor:geofence name:GEOFENCE_DWELL_EVENT_NAME type:GEOFENCE_DWELL_EVENT_TYPE] eventType:@"geofence dwell"];
            case FSQPGeofenceEventTypeVenueConfirmation:
                [self sendAdobeEventWith:[PilgrimEvents getGeofenceEventFor:geofence name:GEOFENCE_CONFIRM_EVENT_NAME type:GEOFENCE_CONFIRM_EVENT_TYPE] eventType:@"geofence confirm"];
            case FSQPGeofenceEventTypeExit:
                [self sendAdobeEventWith:[PilgrimEvents getGeofenceEventFor:geofence name:GEOFENCE_EXIT_EVENT_NAME type:GEOFENCE_EXIT_EVENT_TYPE] eventType:@"geofence exit"];
            default:
                break;
        }
    }
}

- (void)sendAdobeEventWith:(ACPExtensionEvent *)event eventType:(NSString *)eventType {
    NSError* error = nil;
    [ACPCore dispatchEvent:event error:&error];

    if (error) {
        NSLog(@"Error dispatching %@ event %@:%ld", eventType, [error localizedDescription], [error code]);
    }
}

- (void)processEventsWith: (ACPExtensionEvent *)event {
    NSError *error = nil;
    NSDictionary *configSharedState = [self.api getSharedEventState:@"com.adobe.module.configuration" event:event error:&error];

    if([configSharedState objectForKey:@"pilgrimConsumerKey"] && !self.didRegisterPilgrim) {
        FSQPPilgrimManager* pilgrimManager = [FSQPPilgrimManager sharedManager];
        [pilgrimManager configureWithConsumerKey:configSharedState[@"pilgrimConsumerKey"] secret:configSharedState[@"pilgrimConsumerSecret"] delegate:self completion:nil];
        pilgrimManager.debugLogsEnabled = YES;
        [pilgrimManager start];
        self.didRegisterPilgrim = true;
    }
}

@end
