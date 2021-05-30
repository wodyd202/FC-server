package com.fc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private ApiInfo apiInfo(String title) {
        return new ApiInfoBuilder()
                .title(title)
                .description("FC API DOCS")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .build();
    }

	@Bean
	@SuppressWarnings("unchecked")
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("FC")
                .apiInfo(this.apiInfo("FC API"))
                .select()
                .apis(
                		Predicates.or(
        				RequestHandlerSelectors.basePackage("com.fc.command.member.api"),
        				RequestHandlerSelectors.basePackage("com.fc.query.member.api"),
        				RequestHandlerSelectors.basePackage("com.fc.command.store.api"),
        				RequestHandlerSelectors.basePackage("com.fc.query.store.api"),
        				RequestHandlerSelectors.basePackage("com.fc.command.product.api"),
        				RequestHandlerSelectors.basePackage("com.fc.query.product.api"),
        				RequestHandlerSelectors.basePackage("com.fc.config.security.jwt.api")
					)
        		)
                .paths(PathSelectors.ant("/**"))
                .build();
    }
}
