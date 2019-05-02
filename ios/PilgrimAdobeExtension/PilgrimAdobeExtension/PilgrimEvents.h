//  Copyright © 2019 Foursquare. All rights reserved.

#import <Foundation/Foundation.h>
#import <ACPCore/ACPCore.h>
#import <Pilgrim/Pilgrim.h>
#import <ACPCore/ACPExtensionEvent.h>

NS_ASSUME_NONNULL_BEGIN

@interface PilgrimEvents : NSObject

+ (ACPExtensionEvent *)getVisitEventFor:(FSQPVisit *)visit name:(NSString *)name eventType:(NSString *)eventType vistType:(NSString *)visitType;

+ (ACPExtensionEvent *)getGeofenceEventFor:(FSQPGeofenceEvent *)geofence name:(NSString *)name type:(NSString *)type;

@end

NS_ASSUME_NONNULL_END
