import { NativeModules } from "react-native";

const DemoModule = NativeModules.DemoModule;

/**
 * 腾讯云 SDKAppId，需要替换为您自己账号下的 SDKAppId。
 *
 * 进入腾讯云实时音视频[控制台](https://console.cloud.tencent.com/rav ) 创建应用，即可看到 SDKAppId，
 * 它是腾讯云用于区分客户的唯一标识。
 */
export const SDKAPPID = 1400431123;

/**
 * 计算签名用的加密密钥，获取步骤如下：
 *
 * step1. 进入腾讯云实时音视频[控制台](https://console.cloud.tencent.com/rav )，如果还没有应用就创建一个，
 * step2. 单击“应用配置”进入基础配置页面，并进一步找到“帐号体系集成”部分。
 * step3. 点击“查看密钥”按钮，就可以看到计算 UserSig 使用的加密的密钥了，请将其拷贝并复制到如下的变量中
 *
 * 注意：该方案仅适用于调试Demo，正式上线前请将 UserSig 计算代码和密钥迁移到您的后台服务器上，以避免加密密钥泄露导致的流量盗用。
 * 文档：https://cloud.tencent.com/document/product/647/17275#Server
 */
export const SECRETKEY = 'de936233134cc03b9837bf1178a5306100728bfd31dfe25c71f5c945f18c7812';

/**
 * 这里测试使用，请勿模仿，正常需要从服务器端生成
 * @param userID
 * @returns {{userSig: *, SDKAppID: number}}
 */
export async function genTestUserSig(userID) {
  return await DemoModule.getSig(SDKAPPID, SECRETKEY, userID);
}
