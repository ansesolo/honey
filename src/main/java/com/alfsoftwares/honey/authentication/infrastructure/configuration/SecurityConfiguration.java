package com.alfsoftwares.honey.authentication.infrastructure.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  private final UserDetailsService userDetailsService;

  public SecurityConfiguration(final UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(
        jwt -> {
          Collection<String> roles = jwt.getClaimAsStringList("roles");
          return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        });
    return converter;
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(
        List.of("http://localhost:8080", "http://localhost:64232", "http://localhost:64987"));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }

  @Bean
  @Order(1)
  SecurityFilterChain apiLogin(HttpSecurity http) throws Exception {
    System.out.println("My login custom SecurityFilterChain is being applied!");

    return http.csrf(AbstractHttpConfigurer::disable)
        .cors(Customizer.withDefaults())
        .securityMatcher(
            "/api/login/**", "/includes/**", "/js/**", "/images/**", "/index.html", "/")
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth -> {
              auth.requestMatchers("/", "/index.html", "/includes/**", "/js/**", "/images/**")
                  .permitAll();
              auth.requestMatchers("/api/login/**").permitAll();
            })
        .httpBasic(Customizer.withDefaults())
        .build();
  }

  @Bean
  @Order(2)
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    System.out.println("My custom SecurityFilterChain is being applied!");

    return http.csrf(AbstractHttpConfigurer::disable)
        .cors(Customizer.withDefaults())
        .securityMatcher("/**")
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth -> {
              auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
              auth.requestMatchers("/api/admin/**").hasRole("ADMIN");
              auth.requestMatchers("/api/**").hasRole("USER");
              auth.requestMatchers("/", "/index.html", "/*.html").permitAll();
              auth.requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll();
              auth.requestMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll();
              auth.requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll();
              auth.requestMatchers(HttpMethod.GET, "/swagger-resources/*").permitAll();
              auth.requestMatchers(HttpMethod.GET, "/api-docs/**").permitAll();
              auth.requestMatchers(HttpMethod.GET, "/actuator/**").permitAll();
              auth.requestMatchers(HttpMethod.GET, "/actuator/prometheus/**").permitAll();
              auth.anyRequest().authenticated();
            })
        .oauth2ResourceServer(
            oauth2 ->
                oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
        .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder =
        http.getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder);
    return authenticationManagerBuilder.build();
  }

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .components(
            new Components()
                .addSecuritySchemes(
                    "Bearer Authentication",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"))
                .addSecuritySchemes(
                    "Basic Authentication",
                    new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")));
  }
}
