package com.yincheng.game.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yincheng.game.model.po.Account;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

/**
 * @author qijianguo
 */
public interface AccountMapper extends BaseMapper<Account> {

    @Update(value = "update t_account set balance = ifnull(balance,0) + #{credit} where user_id = #{id} and update_time = #{updateTime}")
    int increase(@Param("id") int id, @Param("credit") int credit, @Param("updateTime") Date updateTime);

    @Update(value = "update t_account set balance = ifnull(balance,0) - #{credit} where id = #{id} and update_time = #{updateTime}")
    int decrease(@Param("id") int id, @Param("credit") int credit, @Param("updateTime") Date updateTime);

}
