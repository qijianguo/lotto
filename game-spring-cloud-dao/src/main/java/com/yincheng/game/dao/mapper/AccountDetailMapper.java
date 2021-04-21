package com.yincheng.game.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yincheng.game.model.po.AccountDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author qijianguo
 */
public interface AccountDetailMapper extends BaseMapper<AccountDetail> {

    @Select("select exists(select id from t_account_detail where user_id = #{userId} and type = #{type})")
    Integer exists(@Param("userId") Integer userId, @Param("type") Integer type);
}
