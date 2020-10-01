import ConversationModel from './lib/ConversationView';
import {initSdk, login, logout, startChat} from './lib/TencentIMModel';

export const TIMConversationModel = ConversationModel;
export const TIMInitSdk = initSdk;
export const TIMLogin = login;
export const TIMLogout = logout;
export const TIMStartChat = startChat;

export default {
  TIMConversationModel,
  TIMInitSdk: TIMInitSdk,
  TIMLogin: TIMLogin,
  TIMLogout: TIMLogout,
  TIMStartChat: TIMStartChat
};
