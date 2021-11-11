package com.qijianguo.game.model.vo;

import com.qijianguo.game.model.enums.NotificationType;
import com.qijianguo.game.model.po.Account;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qijianguo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("通知请求参数")
public class NotificationReq {

    @ApiModelProperty(value = "用户ID", dataType = "Integer", required = true)
    private Integer userId;
    @ApiModelProperty(value = "类型：1下注，2提现", dataType = "Integer", required = true)
    private Integer type;
    @ApiModelProperty(value = "积分", dataType = "Integer", required = true)
    private Integer credit;
    @ApiModelProperty(value = "描述", dataType = "Integer", required = true)
    private String description;

    public static NotificationReq create(Account account, NotificationType notificationType) {
        return create("", account, notificationType);
    }

    public static NotificationReq create(String game, Account account, NotificationType notificationType) {
        NotificationReq req = new NotificationReq();
        if (account != null) {
            req.setUserId(account.getUserId());
            req.setCredit(account.getCredit());
            req.setType(notificationType.getType());
            switch (notificationType) {
                case REWARD:
                    req.setDescription("Rp" + account.getCredit() + " in " + game);
                    break;
                case WITHDRAW:
                    req.setDescription("Rp" + account.getCredit());
                    break;
            }
        }
        return req;
    }

}
