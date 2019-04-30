//
//  FSQPUserState.h
//  Pilgrim
//
//  Created by Carlos Reyes  on 3/5/19.
//  Copyright © 2019 Foursquare. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * User States will be used to keep track of different states of locations that people can be in i.e. travel, sick
 */
NS_SWIFT_NAME(UserState)
@interface FSQPUserState : NSObject <NSSecureCoding>


/**
 * The name for the user state.
 */
@property (nonatomic, nullable, readonly, copy) NSString *name;

/**
 *  A dictionary that contains information about the properties of the user state.
 */
@property (nonatomic, nullable, copy, readonly) NSDictionary<NSString *, NSString *> *properties;

/**
 *  Unavailable.
 */
- (instancetype)init NS_UNAVAILABLE;

/**
 *  Unavailable.
 */
+ (instancetype)new NS_UNAVAILABLE;


@end

NS_ASSUME_NONNULL_END
