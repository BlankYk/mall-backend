package cn.css0209.mall.utils;

import cn.css0209.mall.entity.UserEntity;
import cn.css0209.mall.mapper.UserMapper;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * token工具
 *
 * @author blankyk
 */
@Service
@Slf4j
public class TokenUtils {
    @Resource
    private RedisUtils redis;
    @Autowired
    private UserMapper userMapper;

    /**
     * 生成并保存id
     *
     * @param user 用户信息
     * @return token
     */
    public String generateAndSave(UserEntity user) {
        String token = IdUtil.simpleUUID();
        redis.set(token, user.getUsername());
        log.info("username:{}==>token:{}", user.getUsername(), token);
        return token;
    }

    /**
     * cartToken setter
     * @param username 用户名
     * @return
     */
    public String setCartToken(String username){
        String token = IdUtil.simpleUUID();
        redis.set(token, username);
        return token;
    }

    /**
     * cartToken 查询
     * @param token
     * @return
     */
    public String getCartToken(String token){
        return redis.get(token);
    }

    /**
     * 通过id获取token
     *
     * @param token token
     * @return token对应的用户对象(UserEntity)
     */
    public UserEntity tokenGetUserEntity(String token) {
        String username = redis.get(token);
        return userMapper.findByUsername(username);
    }

    /**
     * 删除token
     *
     * @param token token
     * @return boolean
     */
    public boolean tokenDel(String token) {
        return redis.del(token);
    }
}
