package com.lush.core.confing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

  /**
   * Define service version.
   */
  @Value("${service.version}")
  private String serviceVersion;

  /**
   * Define service name.
   */
  @Value("${service.name}")
  private String serviceName;

  /**
   * Method name : postsApi. Description : Get swagger2 information of apis.
   *
   * @return springfox.documentation.spring.web.plugins.Docket
   */
  @Bean
  public Docket postsApi() {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage("com.lush" + serviceName))
        .paths(PathSelectors.any()).build().apiInfo(apiInfo());
  }

  /**
   * Method name : apiInfo. Description : Get information of api configuration.
   *
   * @return springfox.documentation.service.ApiInfo.
   */
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .version(serviceVersion)
        .title(serviceName)
        .description(
            "A microservice to provide " + serviceName
                + " for the Lush Service Oriented Architecture.").build();
  }

}
