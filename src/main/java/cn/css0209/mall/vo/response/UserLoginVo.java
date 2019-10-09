package cn.css0209.mall.vo.response;

import lombok.Data;

/**
 * @author blankyk
 */
@Data
public class UserLoginVo {
    private String username;
    private String nickname;
    private String token;
}
