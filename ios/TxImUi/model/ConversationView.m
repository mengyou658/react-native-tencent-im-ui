//
//  UIViewController+ConversationView.m
//  fangbossApp
//
//  Created by 云超 on 2020/9/26.
//  Copyright © 2020 Facebook. All rights reserved.
//

#import "ConversationView.h"
#import <React/RCTViewManager.h>
#import "ConversationController.h"

@implementation ConversationView : UIView
- (instancetype)init {
    if (self = [super init]) {
        ConversationController * messageVC = [ConversationController getInstance];
        self.messageVC = messageVC;
        [self addSubview:messageVC.view];
    }
    return self;
}
@end
