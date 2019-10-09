package cn.css0209.mall.controller;

import cn.css0209.mall.entity.UserEntity;
import cn.css0209.mall.service.UserService;
import cn.css0209.mall.vo.JsonResponse;
import cn.css0209.mall.vo.request.LoginRequestVo;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用户控制层
 * @author blankyk
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户")
@Slf4j
public class UserController {

    @Autowired
    private UserService service;

    @ApiOperation("添加用户")
    @PostMapping("/add")
    public JsonResponse addUser(@RequestBody UserEntity user){
        return service.addUser(user);
    }

    @ApiOperation("根据id删除用户")
    @DeleteMapping("/del/{id}")
    public JsonResponse delUser(@PathVariable("id") Long id){
        return service.delUserById(id);
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public JsonResponse login(@RequestBody LoginRequestVo user,HttpServletRequest request){
        return service.login(user,request);
    }

    @ApiOperation("登录状态检查")
    @GetMapping("/status")
    public JsonResponse loginStatus(HttpServletRequest request){
        return service.loginStatus(request.getHeader("Token"));
    }

    @ApiOperation("注销")
    @DeleteMapping("/loginOut")
    public JsonResponse loginOut(HttpServletRequest request){
        return service.loginOut(request.getHeader("Token"));
    }

    @ApiOperation("验证码")
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response){
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 40);
        HttpSession session = request.getSession();
        session.setAttribute("captcha", captcha.getCode());
        try {
            captcha.write(response.getOutputStream());
        }catch (IOException e){
            log.info("输出错误");
        }
    }
}
