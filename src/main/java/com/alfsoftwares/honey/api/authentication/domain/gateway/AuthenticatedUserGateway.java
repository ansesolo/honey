package com.alfsoftwares.honey.api.authentication.domain.gateway;

import com.alfsoftwares.honey.api.authentication.domain.model.AuthenticatedUser;

public interface AuthenticatedUserGateway {
  AuthenticatedUser findByUsername(String username);
}
