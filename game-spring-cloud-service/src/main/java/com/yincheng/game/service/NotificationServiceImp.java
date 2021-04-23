package com.yincheng.game.service;

import com.yincheng.game.common.util.RegexUtils;
import com.yincheng.game.model.RedisKeys;
import com.yincheng.game.model.po.Notification;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.vo.NotificationReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author qijianguo
 */
@Service
public class NotificationServiceImp implements NotificationService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;

    @Override
    public User reward(NotificationReq req) {
        Notification notification = new Notification();
        User user = null;
        if (req.getUserId() != null) {
            user = userService.getById(req.getUserId());
            notification.setCover(user.getAvatar());
            notification.setTitle(RegexUtils.replaceWithStar(user.getNickName()));
            notification.setDescription(req.getDescription());
            notification.setCredit(req.getCredit());
            notification.setTime(new Date());
        }
        String key = RedisKeys.notice("reward");
        save(key, 100, notification);
        return user;
    }

    @Override
    public User withdraw(NotificationReq req) {
        Notification notification = new Notification();
        User user = null;
        if (req.getUserId() != null) {
            user = userService.getById(req.getUserId());
            notification.setCover(user.getAvatar());
            notification.setTitle(RegexUtils.replaceWithStar(user.getNickName()));
            notification.setDescription(req.getDescription());
            notification.setCredit(req.getCredit());
            notification.setTime(new Date());
        }
        String key = RedisKeys.notice("withdraw");
        save(key, 100, notification);
        return user;
    }

    @Override
    public List<Notification> getReward(Integer size) {
        List withdraw = redisTemplate.opsForList().range(RedisKeys.notice("reward"), 0,  size == null ? 5 : size);
        return withdraw;
    }

    @Override
    public List<Notification> getWithdraw(Integer size) {
        List withdraw = redisTemplate.opsForList().range(RedisKeys.notice("withdraw"), 0,  size == null ? 5 : size);
        return withdraw;
    }

    private void save(String key, Integer maxSize, Notification notification) {
        Long size = redisTemplate.opsForList().size(key);
        if (size >= maxSize) {
            redisTemplate.opsForList().leftPush(key, notification);
            redisTemplate.opsForList().trim(key, 0, maxSize);
        } else {
            redisTemplate.opsForList().leftPush(key, notification);
        }
    }



}
