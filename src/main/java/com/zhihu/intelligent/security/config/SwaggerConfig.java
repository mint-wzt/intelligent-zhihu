package com.zhihu.intelligent.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import com.zhihu.intelligent.common.constants.SecurityConstants;


import java.util.ArrayList;
import java.util.List;

/**
 * @author wzt
 * swagger-ui 的配置类，可以进行接口测试
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi(){
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        ticketPar.name(SecurityConstants.TOKEN_HEADER).description("user token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false)
                .build();//header中的ticket参数非必填，传空也可以

        pars.add(ticketPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zhihu.intelligent"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("API with Swagger")
                .build();
    }
}