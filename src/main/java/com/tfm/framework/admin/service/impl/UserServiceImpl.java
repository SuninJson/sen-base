package com.tfm.framework.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.tfm.framework.admin.entity.User;
import com.tfm.framework.admin.mapper.UserMapper;
import com.tfm.framework.admin.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Evan Huang
 * @since 2019-01-09
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    private BlockingQueue<User> waitToGetPointsUsers = new ArrayBlockingQueue<User>(10);

    protected volatile boolean isRunning = true;


    public UserServiceImpl() {
        initSendPointsThread();
    }

    /**
     * 初始化赠送积分的线程
     */
    private void initSendPointsThread() {
        singleThreadExecutor.execute(() -> {
            while (isRunning) {
                try {
                    User user = waitToGetPointsUsers.take();
                    sendPoints(user);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void sendPoints(User user) {
        log.info("赠送积分给用户：{}", JSON.toJSONString(user));
    }

    /**
     * 用户注册功能：用户注册后并返回积分
     */
    public void register(User user) {
        addUser(user);
    }

    public void addUser(User user) {
        log.info("添加用户：{}", JSON.toJSONString(user));
        waitToGetPointsUsers.add(user);
    }


}
