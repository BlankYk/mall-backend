package cn.css0209.mall.vo.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author blankyk
 */
@Data
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String nickname;
}
