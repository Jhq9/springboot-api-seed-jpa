package may.i.jhq.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import may.i.jhq.core.Result;
import may.i.jhq.core.ResultCodeEnum;
import may.i.jhq.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jinhuaquan
 * @create 2018-01-18 下午3:55
 * @desc The config of web mvc
 **/
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    private final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    private static final String CHARSET_UTF_8 = "UTF-8";

    private static final String HEADER_X_FORWARDED_FOR_= "x-forwarded-for";

    private static final String IP_UNKNOWN = "unknown";

    private static final String HEADER_PROXY_CLIENT_IP = "Proxy-Client-IP";

    private static final String HEADER_WL_PROXY_CLIENT_IP_= "WL-Proxy-Client-IP";

    private static final String HEADER_HTTP_CLIENT_IP = "HTTP_CLIENT_IP";

    private static final String HEADER_HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";

    private static final String HEADER_CONTENT_TYPE = "Content-type";

    /**
     * 使用阿里 FastJson 作为JSON MessageConverter
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        supportedMediaTypes.add(MediaType.IMAGE_GIF);
        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
        supportedMediaTypes.add(MediaType.IMAGE_PNG);
        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.TEXT_XML);

        converter.setSupportedMediaTypes(supportedMediaTypes);
        FastJsonConfig config = new FastJsonConfig();

        config.setSerializerFeatures(
            //保留空的字段
            SerializerFeature.WriteMapNullValue,
            //String null -> ""
            SerializerFeature.WriteNullStringAsEmpty,
            //Number null -> 0
            SerializerFeature.WriteNullNumberAsZero);
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName(CHARSET_UTF_8));
        converters.add(converter);
    }


    /**
     *  统一异常处理
     * @param exceptionResolvers
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add((request, response, handler, e) -> {
            Result result = new Result();
            /**
             * 业务失败的异常，如“账号或密码错误”， 参数校验错误
             */
            if (e instanceof ServiceException || e instanceof IllegalArgumentException) {
                result.setCode(ResultCodeEnum.FAIL).setMessage(e.getMessage());
                logger.info(e.getMessage());
            } else if (e instanceof NoHandlerFoundException) {
                result.setCode(ResultCodeEnum.NOT_FOUND).setMessage("接口 [" + request.getRequestURI() + "] 不存在");
            } else if (e instanceof ServletException) {
                result.setCode(ResultCodeEnum.FAIL).setMessage(e.getMessage());
            } else if (e instanceof AccessDeniedException) {
                result.setCode(ResultCodeEnum.UNAUTHORIZED).setMessage("没有足够的权限访问该接口");
            }
            /**
             * 请求参数校验错误
             */
            else if (e instanceof MethodArgumentNotValidException) {
                result.setCode(ResultCodeEnum.FAIL).setMessage(((MethodArgumentNotValidException) e).getBindingResult().getFieldError().getDefaultMessage());
            }
            else {
                result.setCode(ResultCodeEnum.INTERNAL_SERVER_ERROR).setMessage("接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员");
                String message;
                if (handler instanceof HandlerMethod) {
                    HandlerMethod handlerMethod = (HandlerMethod) handler;
                    message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                        request.getRequestURI(),
                        handlerMethod.getBean().getClass().getName(),
                        handlerMethod.getMethod().getName(),
                        e.getMessage());
                } else {
                    message = e.getMessage();
                }
                logger.error(message, e);
            }
            responseResult(response, result);
            return new ModelAndView();
        });
    }

    /**
     * 解决跨域问题
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
    }


    /**
     * 发现如果继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。
     * 需要重新指定静态资源
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

    /**
     * 配置servlet处理
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) { configurer.enable();
    }

    private void responseResult(HttpServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }


    /**
     * 从Request的Header中解析出ip address
     * @param request
     * @return
     */
    public static String getIpAddress(final HttpServletRequest request) {
        String ip = request.getHeader(HEADER_X_FORWARDED_FOR_);
        if (ip == null || ip.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HEADER_PROXY_CLIENT_IP);
        }
        if (ip == null || ip.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HEADER_WL_PROXY_CLIENT_IP_);
        }
        if (ip == null || ip.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HEADER_HTTP_CLIENT_IP);
        }
        if (ip == null || ip.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HEADER_HTTP_X_FORWARDED_FOR);
        }
        if (ip == null || ip.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果是多级代理，那么取第一个ip为客户端ip
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(0, ip.indexOf(",")).trim();
        }

        return ip;
    }

}
