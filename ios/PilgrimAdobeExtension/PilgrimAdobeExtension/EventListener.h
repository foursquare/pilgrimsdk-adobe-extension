//
//  EventListener.h
//  PilgrimAdobeExtension
//
//  Created by Carlos Reyes  on 4/25/19.
//  Copyright © 2019 Foursquare. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <ACPCore/ACPExtensionListener.h>
#import <ACPCore/ACPExtensionEvent.h>

NS_ASSUME_NONNULL_BEGIN
@interface EventListener : ACPExtensionListener

- (void) hear:(ACPExtensionEvent *)event;

@end

NS_ASSUME_NONNULL_END
