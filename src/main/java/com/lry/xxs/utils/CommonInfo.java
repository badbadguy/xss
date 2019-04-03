package com.lry.xxs.utils;

/**
 * 一些可能会修改的地址  变量
 */
public class CommonInfo {

    //获取用户信息url
    public static String WECHAT_GET_USERINFO = "https://api.weixin.qq.com/sns/userinfo?";

    //获取access_token的url
    public static String WECHAT_GET_TOKEN_BY_CODE = "https://api.weixin.qq.com/sns/oauth2/access_token?";

    //微信小程序的appid
    public static String APPID = "wx53dac20eb7248329";

    //微信小程序的secret
    public static String SECRET = "4f82e5b3b68c4a1d801d6cbf7430b895";

    //百度API获取Access Token
    public static String BAIDUAT = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=78u7sgDGtF2qBHezHaksq54S&client_secret=IxBKkx0CQ0RdgnWLMwv0DDYk9z7dRsHx";

    //百度API Key
    public static String API_Key = "78u7sgDGtF2qBHezHaksq54S";

    //百度AppID
    public static String AppID = "15928753";

    //百度Secret Key
    public static String Secret_Key = "IxBKkx0CQ0RdgnWLMwv0DDYk9z7dRsHx";

    //百度词义相似度url
    public static String BAIDUBCE = "https://aip.baidubce.com/rpc/2.0/nlp/v2/word_emb_sim";
}
