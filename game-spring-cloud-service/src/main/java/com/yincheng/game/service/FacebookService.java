package com.yincheng.game.service;

import com.alibaba.fastjson.JSON;
import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.common.util.HttpUtils;
import com.yincheng.game.model.vo.FacebookDebugTokenResp;
import com.yincheng.game.model.vo.FacebookUserResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author qijianguo
 */
@Component
public class FacebookService {

    private static final Logger logger = LoggerFactory.getLogger(FacebookService.class);

    /** 临时口令 */
    public static final String GET_CODE_URL = "https://www.facebook.com/v10.0/dialog/oauth";
    /** 访问口令 */
    public static final String GET_ACCESS_TOKEN_URL = "https://graph.facebook.com/v10.0/oauth/access_token";
    /** 用户信息 */
    public static final String GET_USER_INFO = "https://graph.facebook.com/me";
    /** 验证口令 */
    public static final String DEBUG_TOKEN = "https://graph.facebook.com/debug_token";
    /** App Id */
    public static final String APP_ID = "1165322047236078";
    /** App Secret */
    public static final String APP_SECRET = "4e6bf0c54658a3cefffa92e8f1751502";
    /** 用户信息字段 */
    //public static final String USER_INFO_FIELDS = "id,cover,email,gender,name,languages,timezone,third_party_id,updated_time,user_about_me,read_stream";
    public static final String USER_INFO_FIELDS = "id,name,birthday,gender,hometown,email,devices";

    public static String REDIRECT_URL = "http://gntina.iok.la/doLogin";

    public String redirectUrl() {
        return String.format("%s?client_id=%s&redirect_uri=%s", GET_CODE_URL, APP_ID, REDIRECT_URL);
    }

    public String getAccessToken(String code) {
//        if (StringUtils.isEmpty(code)) {
//            throw new BusinessException(EmBusinessError.INVALID_FACEBOOK_CODE);
//        }
//        Map<String, String> params = new HashMap<>(4);
//        params.put("client_id", APP_ID);
//        params.put("client_secret", APP_SECRET);
//        params.put("redirect_uri", REDIRECT_URL);
//        params.put("code", code);
//        String result;
//        try {
//            result = HttpUtils.getInstance().executeGetWithSSL(GET_ACCESS_TOKEN_URL, params);
//            logger.info("getUserInfo:{}", result);
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            throw new BusinessException(EmBusinessError.INVALID_FACEBOOK_CODE);
//        }
//        if (result != null) {
//        }
        return "";
    }

    public FacebookUserResp getUserInfo(String accessToken) {
        String url = String.format("%s?access_token=%s&fields=%s",
                GET_USER_INFO, accessToken, USER_INFO_FIELDS);
        FacebookUserResp resp = null;
        try {
            String result = HttpUtils.getInstance().executeGetWithSSL(url);
            logger.info("getUserInfo:{}", result);
            resp = JSON.parseObject(result, FacebookUserResp.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(EmBusinessError.INVALID_FACEBOOK_ACC_TOKEN);
        }
        return resp;
    }

    /**
     * 调用图谱API，验证用户是否是来自我的应用
     * @param accessToken
     */
    public boolean verifyAccessToken(String accessToken, String unionId) {
        String appToken = getAccessToken();
        // https://graph.facebook.com/debug_token?access_token=
        // https://graph.facebook.com/debug_token?access_token={Your AppId}%7C{Your AppSecret}&input_token=XXX
        String url = String.format("%s?access_token=%s&input_token=%s",
                DEBUG_TOKEN, APP_ID + "%7C" + APP_SECRET, accessToken);
        try {
            String result = HttpUtils.getInstance().executeGetWithSSL(url);
            logger.info("verifyAccessToken:{}", result);
            FacebookDebugTokenResp resp = JSON.parseObject(result, FacebookDebugTokenResp.class);
            FacebookDebugTokenResp.DataBean data = resp.getData();
            if (data.is_valid() && Objects.equals(unionId, data.getUser_id())) {
                return true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        throw new BusinessException(EmBusinessError.INVALID_FACEBOOK_ACC_TOKEN);
    }

    /**
     * 调用图谱API，验证用户是否是来自我的应用
     * @param accessToken
     */
    public FacebookDebugTokenResp verifyAccessToken(String accessToken) {
        String appToken = getAccessToken();
        // https://graph.facebook.com/debug_token?access_token=
        // https://graph.facebook.com/debug_token?access_token={Your AppId}%7C{Your AppSecret}&input_token=XXX
        String url = String.format("%s?access_token=%s&input_token=%s",
                DEBUG_TOKEN, APP_ID + "%7C" + APP_SECRET, accessToken);
        try {
            String result = HttpUtils.getInstance().executeGetWithSSL(url);
            logger.info("verifyAccessToken:{}", result);
            FacebookDebugTokenResp resp = JSON.parseObject(result, FacebookDebugTokenResp.class);
            return resp;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        throw new BusinessException(EmBusinessError.INVALID_FACEBOOK_ACC_TOKEN);
    }

    /**
     * 获取应用口令（用来验证口令是否来自我的应用）
     * @return
     */
    public String getAccessToken(){
        HashMap<String, String> params = new HashMap<>();
        params.put("client_id", APP_ID);
        params.put("client_secret", APP_SECRET);
        params.put("grant_type", "client_credentials");
        String appToken=null;
        String result;
        String url = String.format("%s?client_id=%s&client_secret=%s",
                GET_ACCESS_TOKEN_URL, APP_ID, APP_SECRET);
        try {
            result = HttpUtils.getInstance().executeGetWithSSL(url);
            logger.info("getAppToken:{}", result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        // FIXME获取token


        return appToken;
    }

}
