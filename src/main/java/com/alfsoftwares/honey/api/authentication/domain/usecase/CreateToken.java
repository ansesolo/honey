package com.alfsoftwares.honey.api.authentication.domain.usecase;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class CreateToken {

  private final JwtEncoder jwtEncoder;

  public CreateToken(JwtEncoder jwtEncoder) {
    this.jwtEncoder = jwtEncoder;
  }

  public String generate(Authentication authentication) {
    Instant now = Instant.now();

    List<String> roles =
        authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

    JwtClaimsSet claims =
        JwtClaimsSet.builder()
            .issuer("honey-app")
            .issuedAt(now)
            .expiresAt(now.plus(2, ChronoUnit.HOURS))
            .subject(authentication.getName())
            .claim("roles", roles)
            .build();

    JwtEncoderParameters jwtEncoderParameters =
        JwtEncoderParameters.from(JwsHeader.with(SignatureAlgorithm.RS256).build(), claims);

    return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
  }
}
