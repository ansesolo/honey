package com.alfsoftwares.honey.core.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HalConfiguration {

  @Bean
  public org.springframework.hateoas.mediatype.hal.HalConfiguration globalPolicy() {
    return new org.springframework.hateoas.mediatype.hal.HalConfiguration()
        .withRenderSingleLinks(
            org.springframework.hateoas.mediatype.hal.HalConfiguration.RenderSingleLinks.AS_ARRAY);
  }
}
