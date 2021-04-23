package com.yincheng.game.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yincheng.game.model.po.SinopayDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

/**
 * @author qijianguo
 */
public interface SinopayDetailMapper extends BaseMapper<SinopayDetail> {

    /**
     * 根据订单号查询
     * @param outOrderId 订单号
     * @return
     */
    @Select("select * from t_sinopay_detail where out_order_id = #{outOrderId}")
    SinopayDetail selectByOutOrderId(@Param("outOrderId") String outOrderId);

    @Select("select * from t_sinopay_detail where order_id = #{orderId}")
    SinopayDetail selectByOrderId(@Param("orderId") String orderId);

    @Update("update t_sinopay_detail set account_detail_id = #{accountDetailId}, order_id = #{orderId}, received = #{received}, status = #{status} where id = #{id}")
    void update(@Param("accountDetailId") Integer accountDetailId,
                @Param("orderId") String orderId,
                @Param("received") BigDecimal received,
                @Param("status") String status,
                @Param("id") Integer id);
}
