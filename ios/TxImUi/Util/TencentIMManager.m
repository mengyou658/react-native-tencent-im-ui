//
//  IMManager.m
//  RCTTxim
//
//  Created by 张建军 on 2019/5/5.
//  Copyright © 2019 feewee. All rights reserved.
//

#import "TencentIMManager.h"
#import "TUIKit.h"
#import <ImSDK/V2TIMManager.h>
#import <ImSDK/TIMCallback.h>
#import "ConversationController.h"

@implementation TencentIMManager {
  /// 是否初始化
  BOOL isInit;
  /// 应用ID
  int sdkAppId;
  /// 会话
  TIMConversation *conversation;
  /// 会话
  NSString *currentReceiver;
  /// 设备token
  NSData *deviceToken;
  /// IM配置
  NSDictionary *configDict;
}


+ (instancetype)getInstance {
  __strong static TencentIMManager *instance;
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    instance = [self new];
  });
  return instance;
}

#pragma clang diagnostic push
#pragma ide diagnostic ignored "ResourceNotFoundInspection"

- (BOOL)initSdk:(TIMSdkConfig *)sdkConfig {
  if (isInit) {
    return YES;
  }
  TIMManager *tm = [TIMManager sharedInstance];
  int sdkAppId = [sdkConfig sdkAppId];
  //新版本不用了
  // NSString *accountType = [configDict valueForKey:@"accountType"];
  // if (!accountType) {
  //   IM_LOG_TAG_ERROR(@"Init", @"未配置accountType");
  //   return NO;
  // }
  // sdkConfig.accountType = accountType;
  // 是否crash上报
//  id disableCrashReportValue = [configDict valueForKey:@"disableCrashReport"];
//  if (disableCrashReportValue) {
//    sdkConfig.disableCrashReportValue = [disableCrashReportValue boolValue];
//  }
  // 是否允许log打印
  id disableLogPrintValue = [configDict valueForKey:@"disableLogPrint"];
  if (disableLogPrintValue) {
    sdkConfig.disableLogPrint = [disableLogPrintValue boolValue];
  }
  // Log输出级别, 默认DEBUG等级
  id logLevelValue = [configDict valueForKey:@"logLevel"];
  if (logLevelValue) {
    sdkConfig.logLevel = (TIMLogLevel) [logLevelValue integerValue];
  }
  [[TUIKit sharedInstance] setupWithAppId:sdkAppId]; // SDKAppID 可以在 即时通信 IM 控制台中获取
  isInit = YES;
  return isInit;
}

#pragma clang diagnostic pop

- (void)loginWithIdentify:(NSString *)identify
                  userSig:(NSString *)userSig
                     succ:(TIMLoginSucc)succ
                     fail:(TIMFail)fail {
  // 登录参数
  TIMLoginParam *loginParam = [TIMLoginParam new];
  loginParam.identifier = identify;
  loginParam.userSig = userSig;
  loginParam.appidAt3rd = [NSString stringWithFormat:@"%d", sdkAppId];
  TIMManager *tm = [TIMManager sharedInstance];
  void (^login)(void) = ^(void) {
    int result = [tm login:loginParam
                      succ:^{
                        [self configAppAPNSDeviceToken];
                        succ();
                      } fail:fail];
    if (result != 0) {
      fail(result, @"调用登录失败");
    }
  };
  // 判断是否已经登录
  if ([tm getLoginStatus] == TIM_STATUS_LOGINED) {
    // 判断是否已经登录了当前账号
    if ([[tm getLoginUser] isEqualToString:identify]) {
      login();
    } else {
      // 登出之前的账号
      int result = [tm logout:^{
        login();
      }                  fail:fail];
      if (result != 0) {
        fail(result, @"切换登录失败");
      }
    }
  } else {
    login();
  }
}

- (void)logoutWithSucc:(TIMLoginSucc)succ fail:(TIMFail)fail {
  TIMManager *tm = [TIMManager sharedInstance];
  if ([tm getLoginStatus] == TIM_STATUS_LOGOUT) {
    succ();
  } else {
    int result = [tm logout:succ fail:fail];
    if (result != 0) {
      fail(result, @"调用登出失败");
    }
  }
}

- (void)startChat: (NSString *)identify
                         title:(NSString *)title
                         type:(int)type {
  TUIConversationCellData *data = [[TUIConversationCellData alloc] init];
  if (type == 2){
   data.groupID = identify;  // 如果是群会话，传入对应的群 ID
 } else {
   data.userID = identify;    // 如果是单聊会话，传入对方用户 ID
 }
  NSLog(@"startChat：%@",identify);

  ConversationController * vc = [ConversationController getInstance];
  [vc pushToChatViewController:data.groupID userID:data.userID];


//  dispatch_async(dispatch_get_main_queue(), ^{
//      AppDelegate *delegate = (AppDelegate *)([UIApplication sharedApplication].delegate);
//      UIViewController *rootVC = delegate.window.rootViewController;
//      ChatViewController *chat = [[ChatViewController alloc] init];
//      chat.conversationData = data;
//      [rootVC presentViewController:chat animated:YES completion:nil];
//  });

}

- (void)configDeviceToken:(NSData *)token {
  deviceToken = token;
}

/**
 * 配置设备token
 */
- (void)configAppAPNSDeviceToken {
  TIMManager *tm = [TIMManager sharedInstance];
  // APNS配置
  TIMAPNSConfig *apnsConfig = [TIMAPNSConfig new];
  [apnsConfig setOpenPush:1];
  [tm setAPNS:apnsConfig succ:^{
//    IM_LOG_TAG_INFO(@"APNS", @"APNS配置成功");
  } fail:^(int code, NSString *msg) {
//    IM_LOG_TAG_WARN(@"APNS", @"APNS配置失败，错误码：%d，原因：%@", code, msg);
  }];
//  NSString *token = [NSString stringWithFormat:@"%@", deviceToken];
//  IM_LOG_TAG_INFO(@"SetToken", @"Token is : %@", token);
  TIMTokenParam *param = [TIMTokenParam new];
#if kAppStoreVersion// AppStore 版本
  #if DEBUG
  param.busiId = (uint32_t) [[configDict valueForKey:@"debugBusiId"] unsignedIntegerValue];
#else
  param.busiId = (uint32_t) [[configDict valueForKey:@"busiId"] unsignedIntegerValue];
#endif
#else// 企业证书 ID
  param.busiId = (uint32_t) [[configDict valueForKey:@"busiId"] unsignedIntegerValue];
#endif
  [param setToken:deviceToken];
  [tm setToken:param
          succ:^{
//            IM_LOG_TAG_INFO(@"SetToken", @"上传token成功");
          }
          fail:^(int code, NSString *msg) {
//            IM_LOG_TAG_WARN(@"SetToken", @"上传token失败，错误码：%d，原因：%@", code, msg);
          }];
}





@end
