package com.alfsoftwares.honey.authentication.domain.gateway;

import com.alfsoftwares.honey.authentication.domain.model.AuthenticatedUser;

public interface AuthenticatedUserGateway {
  AuthenticatedUser findByUsername(String username);
}
