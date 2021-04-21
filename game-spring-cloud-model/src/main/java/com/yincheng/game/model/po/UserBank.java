package com.yincheng.game.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yincheng.game.model.vo.BankAddReq;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qijianguo
 */
@Data
@TableName("t_user_bank")
@NoArgsConstructor
@AllArgsConstructor
public class UserBank {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String bank;

    private String branch;

    private String cardNo;

    private String realName;

    private Integer userId;

    public static UserBank create(User user, BankAddReq req) {
        UserBank userBank = new UserBank();
        userBank.setUserId(user.getId());
        userBank.setBank(req.getBank());
        userBank.setBranch(req.getBranch());
        userBank.setCardNo(req.getCardNo());
        userBank.setRealName(req.getRealName());
        return userBank;
    }

}
