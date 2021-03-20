package com.yincheng.game.service.login;

import com.yincheng.game.model.enums.LoginMode;
import com.yincheng.game.model.vo.LoginPhoneReq;
import com.yincheng.game.model.vo.LoginReq;
import org.springframework.stereotype.Component;

/**
 * @author qijianguo
 */
@Component
public class LoginOperate {

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
        Login login = LoginOperate.create(LoginMode.PHONE);
        LoginReq phone = new LoginReq();
        phone.setPhone("111111");
        phone.setCode("1111");
        login.execute(phone);
    }
}
