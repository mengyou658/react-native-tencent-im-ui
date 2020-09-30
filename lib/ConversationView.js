import React from "react";
import { requireNativeComponent } from "react-native";

const ConversationModel = requireNativeComponent("TencentIMConversationModel");

class ConversationView extends React.Component {

  render() {
    return <ConversationModel style={{ flex: 1 }}  {...this.props} />
  }

}


export default {
  ConversationNativeModel: ConversationModel,
  ConversationView
}
