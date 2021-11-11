package com.qijianguo.game.service;

import com.qijianguo.game.model.RedisKeys;
import com.qijianguo.game.model.po.User;
import com.qijianguo.game.model.po.Notification;
import com.qijianguo.game.model.vo.NotificationReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
        User user = userService.getById(req.getUserId());
        Notification notification = Notification.create(user, req);
        save(RedisKeys.noticeReward(), 100, notification);
        return user;
    }

    @Override
    public User withdraw(NotificationReq req) {
        User user = userService.getById(req.getUserId());
        Notification notification = Notification.create(user, req);
        save(RedisKeys.noticeWithdraw(), 100, notification);
        return user;
    }

    @Override
    public List<Notification> getReward(Integer size) {
        List withdraw = redisTemplate.opsForList().range(RedisKeys.noticeReward(), 0,  size == null ? 5 : size);
        return withdraw;
    }

    @Override
    public List<Notification> getWithdraw(Integer size) {
        List withdraw = redisTemplate.opsForList().range(RedisKeys.noticeWithdraw(), 0,  size == null ? 5 : size);
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
