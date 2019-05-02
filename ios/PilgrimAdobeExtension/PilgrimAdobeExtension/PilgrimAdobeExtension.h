//  Copyright © 2019 Foursquare. All rights reserved.

#import <UIKit/UIKit.h>
#import <ACPCore/ACPExtension.h>
#import <Pilgrim/Pilgrim.h>

@interface PilgrimAdobeExtension : ACPExtension <FSQPPilgrimManagerDelegate>

@property (assign) BOOL didRegisterPilgrim;

- (nullable NSString*)name;

- (nullable NSString*)version;

- (void) unexpectedError:(nonnull NSError*)error;

+ (void)registerExtension;

- (void)processEventsWith:(ACPExtensionEvent *)event;

@end

//! Project version number for PilgrimAdobeExtension.
FOUNDATION_EXPORT double PilgrimAdobeExtensionVersionNumber;

//! Project version string for PilgrimAdobeExtension.
FOUNDATION_EXPORT const unsigned char PilgrimAdobeExtensionVersionString[];
