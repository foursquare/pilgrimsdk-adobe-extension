//  Copyright © 2019 Foursquare. All rights reserved.

@class ACPExtensionEvent;

#import <Foundation/Foundation.h>
#import <ACPCore/ACPExtensionListener.h>

NS_ASSUME_NONNULL_BEGIN
@interface EventListener : ACPExtensionListener

- (void)hear:(ACPExtensionEvent *)event;

@end

NS_ASSUME_NONNULL_END
