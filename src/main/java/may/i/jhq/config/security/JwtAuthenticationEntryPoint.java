package may.i.jhq.config.security;

import com.alibaba.fastjson.JSON;
import may.i.jhq.core.ResultCodeEnum;
import may.i.jhq.core.ResultGenerator;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * @author jinhuaquan
 * @create 2018-01-18 下午5:25
 * @desc The authention entry point
 **/
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable{

    private static final long serialVersionUID = 19950620;

    private static final String AUTHENTICATION_FAIL_MESSAGE = "Authentication failed";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        // This is invoked when user tries to access a secured REST resource without supplying any credentials
        // We should just send a 401 Unauthorized response because there is no 'login page' to redirect to
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();

        out.write(JSON.toJSONString(ResultGenerator.genFailResult(ResultCodeEnum.UNAUTHORIZED, AUTHENTICATION_FAIL_MESSAGE)));
    }

}
