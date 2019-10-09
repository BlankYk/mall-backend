package cn.css0209.mall.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author blankyk
 */
@Data
@Builder
public class JsonResponse {
    private int code;
    private String msg;
    private Object object;
}
