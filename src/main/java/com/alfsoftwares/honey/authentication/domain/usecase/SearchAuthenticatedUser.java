package com.alfsoftwares.honey.authentication.domain.usecase;

import static com.alfsoftwares.honey.authentication.domain.model.AuthenticatedUser.ROLE_SEPARATOR;

import com.alfsoftwares.honey.authentication.domain.gateway.AuthenticatedUserGateway;
import com.alfsoftwares.honey.authentication.domain.model.AuthenticatedUser;
import java.util.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SearchAuthenticatedUser implements UserDetailsService {
  private final AuthenticatedUserGateway authenticatedUserGateway;

  public SearchAuthenticatedUser(final AuthenticatedUserGateway authenticatedUserGateway) {
    this.authenticatedUserGateway = authenticatedUserGateway;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AuthenticatedUser user = authenticatedUserGateway.findByUsername(username);

    return new User(user.username(), user.password(), getGrantedAuthorities(user));
  }

  private List<GrantedAuthority> getGrantedAuthorities(AuthenticatedUser user) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    Map<String, Object> attributes = new HashMap<>();

    List<String> roles =
        Arrays.stream(user.roles().split(ROLE_SEPARATOR)).map(role -> "ROLE_" + role).toList();

    roles.forEach(r -> authorities.add(new SimpleGrantedAuthority(r)));

    return authorities;
  }
}
