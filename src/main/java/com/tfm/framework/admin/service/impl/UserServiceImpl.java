package com.tfm.framework.admin.service.impl;

import com.tfm.framework.admin.entity.User;
import com.tfm.framework.admin.mapper.UserMapper;
import com.tfm.framework.admin.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Evan Huang
 * @since 2019-01-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
