package com.yincheng.game.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务
 * @author qijianguo
 */
@TableName("t_game_task")
@Data
public class Task implements Serializable {

    private static final long serialVersionUID = -1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer gameId;

    /** 游戏期数 */
    private Long period;

    private Integer status;

    private String result;

    private Integer sum;

    private Date startTime;

    private Date endTime;

    private Date createTime;

    private Date updateTime;

    public void setPeriodResult(String result, Integer sum) {
        this.result = result;
        this.sum = sum;
        this.updateTime = new Date();
        this.status = 1;
    }

    public static Task initNext(GameFlow gameFlow, Date nextExecuteTime) {
        Task next = new Task();
        next.setGameId(gameFlow.getId());
        next.setStartTime(new Date());
        next.setEndTime(nextExecuteTime);
        next.setPeriod(gameFlow.getNextPeriod());
        next.setCreateTime(new Date());
        next.setUpdateTime(next.getCreateTime());
        return next;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", gameId=" + gameId +
                ", period=" + period +
                ", status=" + status +
                ", result='" + result + '\'' +
                ", sum=" + sum +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
