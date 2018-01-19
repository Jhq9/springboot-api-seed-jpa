package may.i.jhq.config;

import may.i.jhq.constant.ProjectConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author jinhuaquan
 * @create 2018-01-18 下午3:30
 * @desc The configuration of swagger
 **/
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket creatRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                   .apiInfo(apiInfo())
                   .select()
                   //返回ApiSelectotBuilder实例用来控制哪些接口暴露给Swagger来展现
                   .apis(RequestHandlerSelectors.basePackage(ProjectConstant.CONTROLLER_PACKAGE))
                   .paths(PathSelectors.any())
                   .build();

    }


    /**
     * 用来创建Api的基本信息
     * @return
     */
    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                   .title("Spring Boot中使用Swagger2来构建Restful APIs")
                   .description("may i 的Spring Boot配合Swagger2及Easy Mock的测试")
                   .termsOfServiceUrl("http://jinhuaquan.xin/")
                   .version("1.0")
                   .build();
    }

}
