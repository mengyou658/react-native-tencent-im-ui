
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
