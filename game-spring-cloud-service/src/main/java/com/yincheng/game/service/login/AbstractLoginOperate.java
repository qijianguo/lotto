package com.yincheng.game.service.login;

import com.yincheng.game.model.enums.LoginMode;
import com.yincheng.game.model.vo.LoginPhoneReq;

/**
 * @author qijianguo
 */
public class AbstractLoginOperate {

    public static Login create(LoginMode loginMode) {
        switch (loginMode) {
            case PHONE:
                return new PhoneLogin();
            case FACEBOOK:
                return new FacebookLogin();
            default:
        }
        return null;
    }

    public static void main(String[] args) {
        Login login = AbstractLoginOperate.create(LoginMode.PHONE);
        LoginPhoneReq phone = new LoginPhoneReq();
        phone.setPhone("111111");
        phone.setCode("1111");
        login.execute(phone);
    }
}
