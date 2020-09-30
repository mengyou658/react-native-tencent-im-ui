/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React, { Component } from "react";
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
  Button
} from 'react-native';

import {
  Header,
  LearnMoreLinks,
  Colors,
  DebugInstructions,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

import {Toast, TabView} from 'teaset';
import {TIMInitSdk, TIMLogin, TIMLogout, TIMStartChat} from 'react-native-tencent-im-ui';

import {SDKAPPID, SECRETKEY, genTestUserSig} from './src/genSig';
import Conversation from './src/conView';
let userId = '云超';
let userIdTo = '梦游';

class App extends Component {

  async componentDidMount() {
    // 先初始化
    try {
      TIMInitSdk(SDKAPPID);
      Toast.success("初始化成功");
      TIMLogin(userId, await genTestUserSig(userId)).then(res=>{
        Toast.success(userId + "登录成功");
      }).catch(e => {
        Toast.fail(userId + "登录失败");
      });
    } catch (e) {
      console.log('fail', e)
      Toast.fail("初始化失败，请检查SDKAPPID");
    }
  }

  openChat() {
    try {
      TIMStartChat(userIdTo, userId + "和" + userIdTo, 1);
    } catch (e) {
      console.error(e);
    }
  }

  render() {
    return (
      <>
        <StatusBar barStyle="dark-content"/>
          <TabView style={{flex: 1}} type='projector'>
            <TabView.Sheet
              title='主页'
            >
              <View style={styles.body}>
                <View style={styles.sectionContainer}>
                  <Button title="点击这里打开新会话" onPress={this.openChat}>点击这里打开新会话</Button>
                  <Text style={styles.sectionDescription}>
                    当前登录用户：{userId}，聊天用户：{userIdTo}
                  </Text>
                </View>
              </View>
            </TabView.Sheet>
            <TabView.Sheet
              title='会话列表'
            >
              <Conversation/>
            </TabView.Sheet>
          </TabView>
      </>
    );
  }
}

const styles = StyleSheet.create({
  scrollView: {
    backgroundColor: Colors.lighter,
  },
  engine: {
    position: 'absolute',
    right: 0,
  },
  body: {
    backgroundColor: Colors.white,
  },
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
    color: Colors.black,
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
    color: Colors.dark,
  },
  highlight: {
    fontWeight: '700',
  },
  footer: {
    color: Colors.dark,
    fontSize: 12,
    fontWeight: '600',
    padding: 4,
    paddingRight: 12,
    textAlign: 'right',
  },
});

export default App;
