# react-native-txim-ui 腾讯云即时通信 IM 服务的react-native，使用原生ui版本得sdk
起因，项目中需要用到基础的im功能（聊天和聊天列表），晚上搜了一圈也没有找到，技术栈已经定好，也只能硬着头皮搞了。

直接给大家分享出来，目前功能简单，如果有简单需求的可以直接使用。

当前基于 TIMSDK UI版本 标准版 5.0.6 @2020.09.18

项目地址： [https://github.com/mengyou658/react-native-txim-ui](https://github.com/mengyou658/react-native-txim-ui)

## 支持功能
1. 聊天列表功能
1. 聊天功能

## 待支持的功能
1. 不支持自定义界面（可以yarn install 后，在node_models/react-native-txim-ui/更改里面的代码或者直接clone项目复制先来粘贴过去改吧，虽然不方便，但是也能实现，一个个封装代码都不够项目成本的😂）
1. 离线消息
1. 用户信息编辑
1. 加好友
1. 等等。。。

## 支持版本
react-native 0.60 以上版本
## 如何安装
### 1.安装包

`$ npm install react-native-txim-ui --save`

### 2. link
react-native 0.60以上 使用的autolink
##  接口
```javascript

/**
 * 初始化
 * @param sdkAppId
 */
export function initSdk(sdkAppId);
/**
 * 登录
 * @param userId 用户id
 * @param userSig 用户sig
 * @returns {*|PromiseLike<*>|Promise<*>}
 */
export function login(userId, userSig);

/**
 * 登出
 * @returns {Promise<*>}
 */
export async function logout();

/**
 * 从其他界面跳转到聊天界面
 * @param userId im用户id
 * @param conTitle 聊天标题
 * @param type:
 *  1 = 用户会话
 *  2 = 分组会话
 */
export function startChat(userId, conTitle, type = 1);


```
## 使用示例

先初始化
```javascript
import {ConversationView, initSdk, login, logout, startChat} from 'react-native-txim-ui';
// 先初始化
initSdk(sdkAppId);
```

// 调用登录
```javascript
import {ConversationView, initSdk, login, logout, startChat} from 'react-native-txim-ui';
// 调用登录
login(userId, userSig).then(res=>{
  console.log(res);
}).catch(e => {
});
```

// 从其他界面跳转打开会话
```javascript
import {ConversationView, initSdk, login, logout, startChat} from 'react-native-txim-ui';
// 从其他界面跳转打开会话
startChat(userId, "xxx聊天", 1);

```

展示聊天列表界面
```javascript

// 展示聊天列表界面
import {ConversationView, initSdk, login, logout, startChat} from 'react-native-txim-ui';
import React from "react";

export default class Conversation extends React.Component {

  render() {
    return <SafeAreaView style={{flex:1, paddingTop: (Platform.OS === 'ios' ?  10 : StatusBar.currentHeight)}}>
      <StatusBar backgroundColor={'transparent'} translucent={true} barStyle={"dark-content"} animated={true} />
      <ConversationView style={{ flex: 1 }}  {...this.props} />
    </SafeAreaView>
  }

}


```
