import {TIMConversationView, TIMConversationModel} from './lib/ConversationView';
import {initSdk, login, logout, startChat} from './lib/TencentIMModel';

export const TIMInitSdk = initSdk;
export const TIMLogin = login;
export const TIMLogout = logout;
export const TIMStartChat = startChat;

export default {
  TIMConversationView,
  TIMConversationModel,
  TIMInitSdk: TIMInitSdk,
  TIMLogin: TIMLogin,
  TIMLogout: TIMLogout,
  TIMStartChat: TIMStartChat
};
