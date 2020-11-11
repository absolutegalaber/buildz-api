package com.absolutegalaber.buildz.config


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class BuildzApiDocsConfig {
    @Bean
    Docket buildzApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(buildzApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage('com.absolutegalaber.buildz.api.v1'))
                .paths(PathSelectors.any())
                .build()
                .ignoredParameterTypes(MetaClass)
    }

    ApiInfo buildzApiInfo() {
        new ApiInfoBuilder()
                .title('Buildz-API')
                .description('Manage you Build Numbers and Artifacts coherently.')
                .license('Apache 2.0')
                .build()
    }


}
