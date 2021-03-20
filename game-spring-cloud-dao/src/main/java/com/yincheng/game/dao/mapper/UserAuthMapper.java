package com.yincheng.game.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yincheng.game.model.po.UserAuth;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author qijianguo
 */
public interface UserAuthMapper extends BaseMapper<UserAuth> {

    @Select("select * from t_user_auth where user_id = (select user_id from t_user_auth where mode = #{mode} and union_id = #{unionId})")
    List<UserAuth> getUserAuths(@Param("mode") String mode, @Param("unionId")String unionId);

}
