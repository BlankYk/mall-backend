package cn.css0209.mall.config;

import cn.css0209.mall.vo.JsonResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 拦截器
 * @author blankyk
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("==>"+request.getRequestURI());
        String tokenStr = "Token";
        log.info(request.getHeader(tokenStr));
        if (request.getHeader(tokenStr) == null || "".equals(request.getHeader(tokenStr)))  {
            log.info("<==用户未登录");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            JSONObject result = new JSONObject(JsonResponse.builder()
                    .code(HttpStatus.HTTP_UNAUTHORIZED)
                    .msg("用户未登录")
            );
            writer.write(result.toStringPretty());
            return false;
        }
        return true;
    }
}
