package com.yincheng.game.model.vo;

import com.yincheng.game.model.po.Account;
import com.yincheng.game.model.po.User;
import lombok.Data;

/**
 * @author qijianguo
 */
@Data
public class NoticeResp {

    private String avatar;

    private Integer reward;

    private String nickName;

    // private String type;

    public static NoticeResp init(User user, Account account) {
        NoticeResp resp = new NoticeResp();
        if (user != null) {
            resp.setAvatar(user.getAvatar());
            resp.setNickName(user.getNickName());
        }
        if (account != null) {
            resp.setReward(account.getReward());
        }
        return resp;
    }

}
