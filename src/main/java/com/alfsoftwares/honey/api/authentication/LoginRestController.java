package com.alfsoftwares.honey.api.authentication;

import com.alfsoftwares.honey.api.authentication.domain.usecase.CreateToken;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// To access to this controller we need a Basic Authentication (user/password)
@SecurityRequirement(name = "Basic Authentication")
public class LoginRestController {
  private final CreateToken createToken;

  public LoginRestController(CreateToken createToken) {
    this.createToken = createToken;
  }

  @PostMapping("/api/login")
  public String getToken(Authentication authentication) {
    return createToken.generate(authentication);
  }
}
