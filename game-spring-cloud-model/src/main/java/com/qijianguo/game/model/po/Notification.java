package com.qijianguo.game.model.po;

import com.qijianguo.game.common.util.RegexUtils;
import com.qijianguo.game.model.vo.NotificationReq;
import lombok.Data;

import java.util.Date;

/**
 * @author qijianguo
 */
@Data
public class Notification {

    private Integer type;

    private String cover;

    private String title;

    private Integer credit;

    private String description;

    private Date time;

    public static Notification create(User user, NotificationReq req) {
        Notification notification = new Notification();
        notification.setCover(user.getAvatar());
        notification.setTitle(RegexUtils.replaceWithStar(user.getNickName()));
        notification.setDescription(req.getDescription());
        notification.setCredit(req.getCredit());
        notification.setTime(new Date());
        return notification;
    }

}
