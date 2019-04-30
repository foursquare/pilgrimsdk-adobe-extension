//
//  PilgrimAdobeExtension.h
//  PilgrimAdobeExtension
//
//  Created by Carlos Reyes  on 4/8/19.
//  Copyright © 2019 Foursquare. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <ACPCore/ACPExtension.h>
#import <Pilgrim/Pilgrim.h>

@interface PilgrimAdobeExtension : ACPExtension <FSQPPilgrimManagerDelegate>
- (nullable NSString*) name;

- (nullable NSString*) version;

@property (assign) BOOL didRegisterPilgrim;

- (void) unexpectedError: (nonnull NSError*) error;

+ (void)registerExtension;

- (void)processEventsWith:(ACPExtensionEvent *)event;
@end

//! Project version number for PilgrimAdobeExtension.
FOUNDATION_EXPORT double PilgrimAdobeExtensionVersionNumber;

//! Project version string for PilgrimAdobeExtension.
FOUNDATION_EXPORT const unsigned char PilgrimAdobeExtensionVersionString[];
