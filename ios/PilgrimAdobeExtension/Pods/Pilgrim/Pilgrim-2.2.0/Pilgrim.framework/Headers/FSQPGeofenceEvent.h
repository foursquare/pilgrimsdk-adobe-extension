//
//  FSQPGeofenceEvent.h
//  Pilgrim
//
//  Copyright © 2018 Foursquare. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreLocation/CoreLocation.h>

@class FSQPVenue;
@class FSQPUserState;

NS_ASSUME_NONNULL_BEGIN

/**
 * An object representing an interaction with one or more registered geofence radii.
 */
NS_SWIFT_NAME(GeofenceEvent)
@interface FSQPGeofenceEvent : NSObject <NSSecureCoding>

/**
 * Defines a list of geofence events.
 */
typedef NS_ENUM(NSInteger, FSQPGeofenceEventType) {
    /** The device has entered a geofence radius */
    FSQPGeofenceEventTypeEntrance,
    /** The device dwelled (remained inside of) a geofence radius */
    FSQPGeofenceEventTypeDwell,
    /** The device has dwelled inside a geofence radius and confirmed a stop at the venue within the radius */
    FSQPGeofenceEventTypeVenueConfirmation,
    /** The device has exited a geofence radius */
    FSQPGeofenceEventTypeExit,
    /** The device is in a geofence radius during a get location request */
    FSQPGeofenceEventTypePresence
} NS_SWIFT_NAME(GeofenceEvent.EventType);

/**
 * The type of event.
 */
@property (nonatomic, readonly) FSQPGeofenceEventType eventType;

/**
 * The geofence identifier string.
 */
@property (nonatomic, copy, readonly) NSString *geofenceID;

/**
 * The geofence name if the venue object is nil, other the venue name
 */
@property (nonatomic, copy, readonly) NSString *name;

/**
 * An array of venue category identifier strings for the venue within this geofence.
 */
@property (nullable, nonatomic, copy, readonly) NSArray<NSString *> *categoryIDs;

/**
 * An array of venue chain identifier strings for the venue within this genfence.
 */
@property (nullable, nonatomic, copy, readonly) NSArray<NSString *> *chainIDs;

/**
 * An optional venue identifier string defined by the pilgrim partner for the given venue.
 */
@property (nullable, nonatomic, copy, readonly) NSString *partnerVenueID;

/**
 * The venue object that the geofence radius contains.
 *
 * @see FSQPVenue
 */
@property (nullable, nonatomic, readonly) FSQPVenue *venue;

/**
 * The CLLocation object containing the location where the geofence event occurred.
 */
@property (nonatomic, readonly) CLLocation *location;

/**
 * The NSDate object containing the time where the geofence event occurred.
 */
@property (nonatomic, readonly) NSDate *timestamp;

/**
 * The current cached user states.
 */
@property (nullable, nonatomic, copy) NSArray<FSQPUserState *> *userStates;

@end

NS_ASSUME_NONNULL_END
