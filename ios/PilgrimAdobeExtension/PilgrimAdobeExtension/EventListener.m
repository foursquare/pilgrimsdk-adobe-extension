//
//  EventListener.m
//  PilgrimAdobeExtension
//
//  Created by Carlos Reyes  on 4/25/19.
//  Copyright © 2019 Foursquare. All rights reserved.
//

#import "EventListener.h"
#import "PilgrimAdobeExtension.h"

@implementation EventListener
- (void)hear:(ACPExtensionEvent *)event {
    PilgrimAdobeExtension *parentExtension = [self getParentExtension];
    [parentExtension processEventsWith:event];
    
}

- (PilgrimAdobeExtension *) getParentExtension {
    PilgrimAdobeExtension * parentExtension = nil;
    if ([[self extension] isKindOfClass:PilgrimAdobeExtension.class]) {
        parentExtension = (PilgrimAdobeExtension*) [self extension];
    }

    return parentExtension;
}
@end
