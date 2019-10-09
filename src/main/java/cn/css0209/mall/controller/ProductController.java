package cn.css0209.mall.controller;

import cn.css0209.mall.service.ProductService;
import cn.css0209.mall.utils.TokenUtils;
import cn.css0209.mall.vo.JsonResponse;
import cn.css0209.mall.vo.request.CartRequestVo;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
 * @author blankyk
 */
@Api(tags = "商品")
@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService service;
    @Autowired
    private TokenUtils tokenUtils;

    @ApiOperation("返回商品数据")
    @GetMapping
    public JsonResponse getAllProducts(){
        JSONArray result = JSONUtil.parseArray("[" +
                "{\"id\": \"1\",\"name\": \"MacBook Pro 13.3\",\"price\": \"10199.00\",\"imgSrc\": \"https://img.css0209.cn/school/mall/macbookpro.jpg\"}," +
                "{\"id\": \"2\",\"name\": \"华为mate30\",\"price\": \"4299.00\",\"imgSrc\": \"https://img.css0209.cn/school/mall/huaweimate30.png\"}," +
                "{\"id\": \"3\",\"name\": \"AOC 27英寸微框 IPS技术屏\",\"price\": \"849.00\",\"imgSrc\": \"https://img.css0209.cn/school/mall/AOC.jpg\"}," +
                "{\"id\": \"4\",\"name\": \"希捷1T台式机械硬盘\",\"price\": \"269.00\",\"imgSrc\": \"https://img.css0209.cn/school/mall/xijie1T.jpg\"}," +
                "{\"id\": \"5\",\"name\": \"索尼(SONY)PS4 Pro\",\"price\": \"2699.00\",\"imgSrc\": \"https://img.css0209.cn/school/mall/SONYPS4.jpg\"}," +
                "{\"id\": \"6\",\"name\": \"华硕 RTX2080Ti\",\"price\": \"12999.00\",\"imgSrc\": \"https://img.css0209.cn/school/mall/RTX2080Ti.jpg\"}," +
                "{\"id\": \"7\",\"name\": \"SK-II神仙水\",\"price\": \"1540.00\",\"imgSrc\": \"https://img.css0209.cn/school/mall/SK2.jpg\"}," +
                "{\"id\": \"8\",\"name\": \"小米MixAlpha\",\"price\": \"19999.00\",\"imgSrc\": \"https://img.css0209.cn/school/mall/MixAlpha.jpg\"}]"
        );
        return JsonResponse.builder()
                .code(HttpStatus.HTTP_OK)
                .msg("")
                .object(result)
                .build();
    }

    @ApiOperation("防重复提交token")
    @PostMapping("/cartToken")
    public JsonResponse cartToken(HttpServletRequest request){
        String username = request.getHeader("username");
        tokenUtils.setCartToken(username);
        return JsonResponse.builder()
                .code(HttpStatus.HTTP_OK)
                .msg("cartToken")
                .object(tokenUtils.setCartToken(username))
                .build();
    }

    @ApiOperation("购买商品")
    @PostMapping("/buy")
    public JsonResponse buy(@RequestBody CartRequestVo[] requestData,HttpServletRequest request){
        return service.buy(requestData,request);
    }

}
