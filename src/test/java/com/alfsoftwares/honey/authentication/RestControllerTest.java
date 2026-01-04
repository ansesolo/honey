package com.alfsoftwares.honey.authentication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alfsoftwares.honey.authentication.domain.usecase.CreateToken;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class RestControllerTest {
  private final MockMvc mockMvc;
  private final CreateToken createToken;

  @Autowired
  public RestControllerTest(MockMvc mockMvc, CreateToken createToken) {
    this.mockMvc = mockMvc;
    this.createToken = createToken;
  }

  @Test
  public void should_return_ok_when_access_admin_route_with_admin_role() throws Exception {
    String token = getToken("ROLE_ADMIN");
    performOk(token, "/admin");
  }

  @Test
  public void should_return_ko_when_access_admin_route_with_user_role() throws Exception {
    String token = getToken("ROLE_USER");
    performForbidden(token, "/admin");
  }

  @Test
  public void should_return_ok_when_access_user_route_with_user_role() throws Exception {
    String token = getToken("ROLE_USER");
    performOk(token, "/api/products");
  }

  @Test
  public void should_return_ko_when_access_user_route_with_admin_role() throws Exception {
    String token = getToken("ROLE_ADMIN");
    performForbidden(token, "/api/products");
  }

  private void performForbidden(String token, String route) throws Exception {
    mockMvc
        .perform(get(route).header("Authorization", "Bearer " + token))
        .andExpect(status().isForbidden());
  }

  private void performOk(String token, String route) throws Exception {
    mockMvc
        .perform(get(route).header("Authorization", "Bearer " + token))
        .andExpect(status().isOk());
  }

  private String getToken(String role) {
    Map<String, Object> claims = Map.of("sub", "user", "roles", List.of(role));
    Jwt jwt =
        new Jwt(
            "tokenValue",
            Instant.now(),
            Instant.now().plus(5, ChronoUnit.MINUTES),
            Map.of("user", "user"),
            claims);

    OAuth2UserAuthority authorities = new OAuth2UserAuthority(claims);
    OAuth2AuthenticatedPrincipal principal =
        new DefaultOAuth2AuthenticatedPrincipal(claims, List.of(authorities));
    OAuth2AccessToken accessToken =
        new OAuth2AccessToken(
            OAuth2AccessToken.TokenType.BEARER,
            jwt.getTokenValue(),
            jwt.getIssuedAt(),
            jwt.getExpiresAt());

    BearerTokenAuthentication authentication =
        new BearerTokenAuthentication(principal, accessToken, List.of(authorities));

    return createToken.generate(authentication);
  }
}
