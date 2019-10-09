package cn.css0209.mall.vo.request;

import lombok.Data;

/**
 * @author blankyk
 */
@Data
public class LoginRequestVo {
    private String username;
    private String password;
    private String captcha;
}
