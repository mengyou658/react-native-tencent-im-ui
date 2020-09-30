//
//  UIViewController+ConversationView.h
//  fangbossApp
//
//  Created by 云超 on 2020/9/26.
//  Copyright © 2020 Facebook. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "ConversationController.h"

NS_ASSUME_NONNULL_BEGIN

@interface ConversationView : UIView

@property (nonatomic, strong) ConversationController * messageVC;
@end

NS_ASSUME_NONNULL_END
