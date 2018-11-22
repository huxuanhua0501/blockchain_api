package com.blockchain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author hu_xuanhua_hua
 * @ClassName: BlockchainController
 * @Description: TODO
 * @date 2018-08-17 14:39
 * @versoin 1.0
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                .select()
                //要扫描的API(Controller)基础包
                .apis(RequestHandlerSelectors.basePackage("com.blockchain.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     *
     * @return
     */
    private ApiInfo buildApiInfo() {

        return new ApiInfoBuilder()
                .title("用户信息API文档")
                .description("生成区块链接口")
                .contact("胡宣化")
                .version("1.0")
                .build();

    }
}