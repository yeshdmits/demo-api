package org.exchangeservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Web flux library configuration for WebClient
 *
 * @author yeshenkodmit
 */
@Configuration
@EnableAsync
public class WebClientConfig {

  @Value("${btc-price.url}")
  private String url;

  @Bean
  public WebClient webClient(@Autowired WebClient.Builder webClientBuilder) {
    return webClientBuilder.baseUrl(url)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
  }
}
