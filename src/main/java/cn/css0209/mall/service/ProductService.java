package cn.css0209.mall.service;

import cn.css0209.mall.utils.TokenUtils;
import cn.css0209.mall.vo.JsonResponse;
import cn.css0209.mall.vo.request.CartRequestVo;
import cn.hutool.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * @author blankyk
 */
@Service
@Slf4j
public class ProductService {

    @Autowired
    private TokenUtils tokenUtils;

    /**
     * 购买商品
     * @param request
     * @return
     */
    public JsonResponse buy(CartRequestVo[] requestData, HttpServletRequest request){
        String cartToken = request.getHeader("cartToken");
        String msg = "购买成功";
        int code = HttpStatus.HTTP_OK;
        BigDecimal sum = BigDecimal.valueOf(0.00);
        if(tokenUtils.getCartToken(cartToken)==null){
            code = 429;
            msg = "购买失败,重复提交";
        }else {
            tokenUtils.tokenDel(cartToken);
            for (CartRequestVo product : requestData) {
                sum = sum.add(product.getPrice());
            }
        }
        return JsonResponse.builder()
                .code(code)
                .msg(msg)
                .object(sum)
                .build();
    }
}
