package cn.css0209.mall.mapper;

import cn.css0209.mall.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户Jpa映射(持久层)
 * @author blankyk
 */
public interface UserMapper extends JpaRepository<UserEntity,Long> {
    /**
     * 登录查询
     * @param username username
     * @param password password
     * @return UserEntity
     */
    UserEntity findByUsernameAndPassword(String username,String password);

    /**
     * 根据username查询UserEntity
     * @param username username
     * @return UserEntity
     */
    UserEntity findByUsername(String username);
}
