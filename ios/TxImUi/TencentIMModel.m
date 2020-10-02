//
//  UIViewController+TencentIMModel.m
//  fangbossApp
//
//  Created by 云超 on 2020/9/24.
//  Copyright © 2020 Facebook. All rights reserved.
//

#import "TencentIMModel.h"
#import "TUIKit.h"
#import <ImSDK/V2TIMManager.h>
#import <ImSDK/TIMCallback.h>
#import "TencentIMManager.h"

@implementation TencentIMModel

RCT_EXPORT_MODULE(TencentIMModel);
/**
 * 初始化SDK
 * @param configFilePath 配置文件路径，默认为mainBundle下的txim.plist
 */
RCT_EXPORT_METHOD(initSdk:(int)sdkAppId)
{
  TencentIMManager *tm = [TencentIMManager getInstance];
  TIMSdkConfig *sdkConfig = [TIMSdkConfig new];
  sdkConfig.sdkAppId = sdkAppId;
  [tm initSdk:sdkConfig];
}

/**
 * 用户登录
 * @param identify identify
 * @param userSig userSig
 * @param succ 成功回调
 * @param fail 失败回调
 */
RCT_EXPORT_METHOD(login:(NSString *)identify
                  userSig:(NSString *)userSig
                     resolver:(RCTPromiseResolveBlock)resolve
                     rejecter:(RCTPromiseRejectBlock)reject)
{
  TencentIMManager *tm = [TencentIMManager getInstance];
  [tm loginWithIdentify:identify
                       userSig:userSig
                       succ:^{
                         resolve(@{
                           @"code": @(0),
                           @"msg": @"登录成功!",
                         });
                       }
                       fail:^(int code, NSString *msg) {
                         reject([NSString stringWithFormat:@"%d", code], msg, nil);
                       }];
}

/**
 * 用户登出
 * @param succ 成功回调
 * @param fail 失败回调
 */
RCT_EXPORT_METHOD(logout:(RCTPromiseResolveBlock)resolve
rejecter:(RCTPromiseRejectBlock)reject){
  TencentIMManager *tm = [TencentIMManager getInstance];
  [tm logoutWithSucc:^{
    resolve(@(YES));
  }                  fail:^(int code, NSString *msg) {
    reject([NSString stringWithFormat:@"%@", @(code)], msg, nil);
  }];
}

/**
 * 开启会话
 */
RCT_EXPORT_METHOD(startChatView: (NSString *)identify
                       title:(NSString *)title
                       type:(int)type){
  // NSLog(@"startChat：%@",identify);
  TencentIMManager *tm = [TencentIMManager getInstance];
  [tm startChat:identify
   title:title
   type:type];
}

@end
