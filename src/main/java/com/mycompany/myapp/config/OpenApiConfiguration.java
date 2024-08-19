package com.mycompany.myapp.config;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(Constants.SPRING_PROFILE_API_DOCS)
@RequiredArgsConstructor
public class OpenApiConfiguration {

  public static final String API_FIRST_PACKAGE = "com.mycompany.myapp.web.rest";
  private final ApplicationProperties applicationProperties;

  @Bean
  @ConditionalOnMissingBean(name = "apiFirstGroupedOpenAPI")
  public GroupedOpenApi apiFirstGroupedOpenAPI() {
    return GroupedOpenApi.builder()
        .group("openapi")
        .packagesToScan(API_FIRST_PACKAGE)
        .pathsToMatch(applicationProperties.getApiDocs().getDefaultIncludePattern())
        .build();
  }
}
