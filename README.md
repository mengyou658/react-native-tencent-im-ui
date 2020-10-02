# react-native-tencent-im-ui 腾讯云即时通信 IM 服务的react-native，使用原生ui版本得sdk

起因，项目中需要用到基础的im功能（聊天和聊天列表），晚上搜了一圈也没有找到，技术栈已经定好，也只能硬着头皮搞了。

直接给大家分享出来，目前功能简单，如果有简单需求的可以直接使用。

当前基于 TIMSDK UI版本 标准版 5.0.6 @2020.09.18

项目地址： [https://github.com/mengyou658/react-native-tencent-im-ui](https://github.com/mengyou658/react-native-tencent-im-ui)

## 支持功能
1. 聊天列表功能
1. 聊天功能

## 待支持的功能
1. 不支持自定义界面（可以yarn install 后，在node_models/react-native-tencent-im-ui/更改里面的代码或者直接clone项目复制先来粘贴过去改吧，虽然不方便，但是也能实现，一个个封装代码都不够项目成本的😂）
1. 离线消息
1. 用户信息编辑
1. 加好友
1. 等等。。。

## 支持版本
react-native 0.60 以上版本
## 如何安装
### 1.安装包

注意需要 --save 参数，react-native会自动link

`$ npm install react-native-tencent-im-ui --save`
`$ yarn add react-native-tencent-im-ui `

### 2. link

react-native 0.60以上 使用的autolink，注意需要 --save 参数，react-native会自动link

#### android 特别处理
1. **需要在AndroidManifest.xml增加 activity**
`<activity android:name="com.yunchao.tencentim.activity.ChatActivity" />`
1. **在你自己的项目中的，android/app/src/main/java/<你的包名>/MainApplication.java 中onCreate()方法中增加如下**
```java
 @Override
  public void onCreate() {
    super.onCreate();
    SoLoader.init(this, /* native exopackage */ false);
   ...

    // 然后添加这一行，这里很重要，initSdk中使用到这个，初始化必须在主Application中初始化，否则会出现线程错误问题
    IMApplication.setContext(this,  MainActivity.class); 
   ...
  }
```
1. demo 中增加的有华为push的示例（使用的低版本的push，高版本的总是提示安装hms-core，有点烦），完整的请参考腾讯im-sdk内的demo
## 示例 请参考 demo 文件夹

android demo 截图

![微信图片_20201002151638](README.assets/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20201002151638.jpg)![微信图片_20201002151644](README.assets/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20201002151644-1601623124924.jpg)

ios demo 截图

#### ios 特别处理
1. **在你自己的项目中的，AppDelegate.m**

   ```object
   ...
   // 引入头文件
   #import "ConversationController.h"
   ...
   // 方法 didFinishLaunchingWithOptions 中修改
   - (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
   ...
   
   // 下面一行更改为
   // self.window.rootViewController = rootViewController;
    UINavigationController *nv = [[UINavigationController alloc] initWithRootViewController:rootViewController];
    nv.navigationBar.hidden = YES;
    nv.delegate = self;
   
    self.window.rootViewController = nv;
   
    ConversationController *c = [ConversationController getInstance];
    [c initNc: nv];
     
   ...
   
   ```

   

##  接口
```javascript

/**
 * 初始化
 * @param sdkAppId
 */
export function TIMInitSdk(sdkAppId);
/**
 * 登录
 * @param userId 用户id
 * @param userSig 用户sig
 * @returns {*|PromiseLike<*>|Promise<*>}
 */
export function TIMLogin(userId, userSig);

/**
 * 登出
 * @returns {Promise<*>}
 */
export async function TIMLogout();

/**
 * 从其他界面跳转到聊天界面
 * @param userId im用户id
 * @param conTitle 聊天标题
 * @param type:
 *  1 = 用户会话
 *  2 = 分组会话
 */
export function TIMStartChat(userId, conTitle, type = 1);


```
## 使用示例

先初始化
```javascript
import {TIMConversationModel, TIMInitSdk, TIMLogin, TIMLogout, TIMStartChat} from 'react-native-tencent-im-ui';
// 先初始化
TIMInitSdk(sdkAppId);
```

// 调用登录
```javascript
import {TIMConversationModel, TIMInitSdk, TIMLogin, TIMLogout, TIMStartChat} from 'react-native-tencent-im-ui';
// 调用登录
TIMLogin(userId, userSig).then(res=>{
  console.log(res);
}).catch(e => {
});
```

// 从其他界面跳转打开会话
```javascript
import {TIMConversationModel, TIMInitSdk, TIMLogin, TIMLogout, TIMStartChat} from 'react-native-tencent-im-ui';
// 从其他界面跳转打开会话
TIMStartChat(userId, "xxx聊天", 1);

```

展示聊天列表界面
```javascript

// 展示聊天列表界面
import {TIMConversationModel, TIMInitSdk, TIMLogin, TIMLogout, TIMStartChat} from 'react-native-tencent-im-ui';
import React from "react";
import {
  SafeAreaView,
  StatusBar,
} from 'react-native';

export default class Conversation extends React.Component {

  render() {
    return <SafeAreaView style={{flex:1, paddingTop: (Platform.OS === 'ios' ?  10 : StatusBar.currentHeight)}}>
      <StatusBar backgroundColor={'transparent'} translucent={true} barStyle={"dark-content"} animated={true} />
      <TIMConversationModel style={{ flex: 1 }}  {...this.props} />
    </SafeAreaView>
  }

}


```

### 参考鸣谢项目
1. [https://github.com/yz1311/react-native-txim/](https://github.com/yz1311/react-native-txim/)
1. [https://github.com/kurisu994/react-native-txim](https://github.com/kurisu994/react-native-txim)
