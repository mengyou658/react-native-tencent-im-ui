import { NativeModules } from "react-native";

export const TencentIMModule = NativeModules.TencentIMModel;

let initFlag = false;
let loginFlag = false;

export function initSdk(sdkAppId) {
  TencentIMModule.initSdk(sdkAppId);
  initFlag = true;
}
export function login(userId, userSig) {
  return TencentIMModule.login(userId, userSig).then(res=> {
    loginFlag = true;
    return res;
  });
}

export async function logout() {
  return TencentIMModule.logout().then(res=>{
    loginFlag = false;
    return res;
  });
}

/**
 *
 * @param userId im用户id
 * @param conTitle 聊天标题
 * @param type:
 *  1 = 用户会话
 *  2 = 分组会话
 */
export function startChat(userId, conTitle, type = 1) {
  if (!loginFlag) {
    throw new Error("请先调用登录接口：login(userId, userSig)");
  }
  TencentIMModule.startChat(userId, conTitle, type);
}
