package com.yincheng.game.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yincheng.game.common.util.TimeUtils;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
 * 游戏持久化实体类
 * @author qijianguo
 */
@TableName("t_game")
@Data
public class GameFlow {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 类型 */
    private String type;

    /** 名称 */
    private String name;

    /** 展示图片 */
    private String cover;

    /** 描述 */
    private String description;

    /** 跳转地址 */
    private String link;

    /** 开启状态 */
    private Integer enabled;

    /** 执行周期表达式 */
    private String cron;

    // FIXME 更改字段名
    /** 上一期 */
    private Long period;// prev
    /** 当前期 */
    private Long nextPeriod;    // curr
    /** 下一期 */
    @TableField(exist = false)
    private Long tempPeriod;    // next

    private String result;



    private Date updateTime;

    /** 游戏创建时间 */
    private Date createTime;

    public void initPeriod() {
        Date date = TimeUtils.convertLocalDate2Date(LocalDate.now());
        long todayStartPeriod = Long.parseLong(TimeUtils.convertDate2String(date, TimeUtils.YYYYHHMM) + "0001");
        if (nextPeriod != null && nextPeriod != -1) {
            // 昨天的
            if (todayStartPeriod > nextPeriod) {
                tempPeriod = todayStartPeriod;
            } else {
                // 今天
                tempPeriod = nextPeriod + 1;
            }
        } else {
            nextPeriod = -1L;
            tempPeriod = todayStartPeriod;
        }

    }

}
