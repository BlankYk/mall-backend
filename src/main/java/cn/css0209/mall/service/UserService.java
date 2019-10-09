package cn.css0209.mall.service;

import cn.css0209.mall.entity.UserEntity;
import cn.css0209.mall.mapper.UserMapper;
import cn.css0209.mall.utils.TokenUtils;
import cn.css0209.mall.vo.JsonResponse;
import cn.css0209.mall.vo.request.LoginRequestVo;
import cn.css0209.mall.vo.response.UserLoginVo;
import cn.css0209.mall.vo.response.UserResponse;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * 用户业务层
 * @author blankyk
 */
@Service
@Slf4j
public class UserService {
    @Autowired
    private UserMapper mapper;
    @Autowired
    private TokenUtils tokenUtils;
    /**
     * 返回信息
     */
    private static String msg;
    /**
     * 返回状态码
     */
    private static int code;

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    public JsonResponse addUser(UserEntity user) {
        user.setPassword(SecureUtil.md5(user.getPassword()));
        UserEntity userSaveObject = mapper.save(user);
        UserResponse userResponse = UserResponse.builder()
                .id(userSaveObject.getId())
                .username(userSaveObject.getUsername())
                .nickname(userSaveObject.getNickname())
                .build();
        return JsonResponse.builder()
                .code(HttpStatus.HTTP_CREATED)
                .msg("添加成功")
                .object(userResponse)
                .build();
    }

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    public JsonResponse delUserById(Long id) {
        try {
            mapper.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.info("<==Error: Id不存在");
            return JsonResponse.builder()
                    .code(HttpStatus.HTTP_NO_CONTENT)
                    .msg("id不存在")
                    .build();
        }
        return JsonResponse.builder()
                .code(HttpStatus.HTTP_NOT_FOUND)
                .msg("删除成功")
                .build();
    }

    /**
     * 登录
     *
     * @param user
     * @return
     */
    public JsonResponse login(LoginRequestVo user, HttpServletRequest request) {
        UserLoginVo loginVo = new UserLoginVo();
        HttpSession session = request.getSession();
        String captchaCode = session.getAttribute("captcha").toString();
        msg = "登录失败";
        code = HttpStatus.HTTP_UNAUTHORIZED;
        if(captchaCode.equals(user.getCaptcha())) {
            Optional.ofNullable(mapper.findByUsernameAndPassword(user.getUsername(), SecureUtil.md5(user.getPassword())))
                    .map(userEntity -> {
                        loginVo.setUsername(userEntity.getUsername());
                        loginVo.setNickname(userEntity.getNickname());
                        loginVo.setToken(tokenUtils.generateAndSave(userEntity));
                        return userEntity;
                    }).ifPresent((e) -> {
                msg = "登录成功";
                code = HttpStatus.HTTP_CREATED;
            });
        }else{
            msg = "验证码错误";
        }
        return JsonResponse.builder()
                .code(code)
                .msg(msg)
                .object(loginVo)
                .build();
    }

    /**
     * 登出
     * @param token token
     */
    public JsonResponse loginOut(String token){
        if(tokenUtils.tokenDel(token)){
            msg = "注销成功";
            code = HttpStatus.HTTP_NO_CONTENT;
        }else{
            msg = "注销失败";
            code = HttpStatus.HTTP_UNAUTHORIZED;
        }
        return JsonResponse.builder()
                .code(code)
                .msg(msg)
                .build();
    }

    /**
     * 登录检查
     */
    public JsonResponse loginStatus(String token){
        UserLoginVo userLoginVo = new UserLoginVo();
        log.info(token);
        try {
            UserEntity user = tokenUtils.tokenGetUserEntity(token);
            userLoginVo.setToken(token);
            userLoginVo.setUsername(user.getUsername());
            userLoginVo.setNickname(user.getNickname());
            msg = "已登录";
            code = HttpStatus.HTTP_OK;
        }catch (NullPointerException e){
            msg = "未登录";
            code = HttpStatus.HTTP_UNAUTHORIZED;
            e.printStackTrace();
        }
        return JsonResponse.builder()
                .code(code)
                .msg(msg)
                .object(userLoginVo)
                .build();
    }
}
