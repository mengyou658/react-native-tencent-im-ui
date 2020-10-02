//
//  IMManager.h
//  RCTTxim
//
//  Created by 张建军 on 2019/5/5.
//  Copyright © 2019 feewee. All rights reserved.
//

#import "TUIKit.h"
#import <React/RCTViewManager.h>
#import <ImSDK/V2TIMManager.h>
#import <ImSDK/TIMCallback.h>
#import <React/RCTBridgeModule.h>

NS_ASSUME_NONNULL_BEGIN

@interface TencentIMManager : NSObject

/**
 * 获取实例
 */
+ (instancetype)getInstance;

/**
 * 初始化SDK
 * @param configFilePath 配置文件路径，默认为mainBundle下的txim.plist
 */
- (BOOL)initSdk:(TIMSdkConfig *_Nullable)sdkConfig;

/**
 * 用户登录
 * @param identify identify
 * @param userSig userSig
 * @param succ 成功回调
 * @param fail 失败回调
 */
- (void)loginWithIdentify:(NSString *)identify
                  userSig:(NSString *)userSig
                     succ:(TIMSucc)succ
                     fail:(TIMFail)fail;

/**
 * 用户登出
 * @param succ 成功回调
 * @param fail 失败回调
 */
- (void)logoutWithSucc:(TIMSucc)succ fail:(TIMFail)fail;

/**
 * 开启会话
 */
- (void)startChat: (NSString *)identify
                       title:(NSString *)title
                       type:(int)type;

@end

NS_ASSUME_NONNULL_END
