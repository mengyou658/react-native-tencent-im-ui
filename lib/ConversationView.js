import React from "react";
import { requireNativeComponent, StyleSheet, StatusBar, SafeAreaView, Platform } from "react-native";

const ConversationModel = requireNativeComponent("TencentIMConversationModel");
export default class ConversationView extends React.Component {

  render() {
    return <SafeAreaView style={{flex:1, paddingTop: (Platform.OS === 'ios' ?  10 : StatusBar.currentHeight)}}>
      <StatusBar backgroundColor={'transparent'} translucent={true} barStyle={"dark-content"} animated={true} />
      <ConversationModel style={{ flex: 1 }}  {...this.props} />
    </SafeAreaView>
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
  },
});
