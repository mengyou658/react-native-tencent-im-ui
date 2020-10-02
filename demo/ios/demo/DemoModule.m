//
//  DemoModule.m
//  fangbossApp
//
//  Created by 云超 on 2020/9/26.
//  Copyright © 2020 Facebook. All rights reserved.
//

#import "TUIKit.h"
#import <ImSDK/V2TIMManager.h>
#import <ImSDK/TIMCallback.h>
#import "TUIConversationListController.h"
#import "DemoModule.h"
#import "GenerateTestUserSig.h"
@implementation DemoModule

RCT_EXPORT_MODULE(DemoModule);
RCT_EXPORT_METHOD(getSig:(int)sdkAppId
		secretKey:(NSString *)secretKey
    identifier:(NSString *)identifier
		resolver:(RCTPromiseResolveBlock)resolve
        rejecter:(RCTPromiseRejectBlock)reject		
	)
{
  GenerateTestUserSig *generateTestUserSig = [[GenerateTestUserSig alloc] init];
  NSString *str = [generateTestUserSig genTestUserSig: sdkAppId
                                            secretKey:secretKey
                                           identifier:identifier];
  resolve(str);
}

@end
	
