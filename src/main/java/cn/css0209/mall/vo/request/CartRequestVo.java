package cn.css0209.mall.vo.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 购物车
 */
@Data
public class CartRequestVo {
    private String name;
    private Long id;
    private String imgSrc;
    private BigDecimal price;
}
