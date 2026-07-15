package com.website.shared.auth;

import com.website.shared.security.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {
  UserResponse toResponse(UserAccount user);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "enabled", ignore = true)
  @Mapping(target = "authority", ignore = true)
  UserAccount from(SignupRequest request);
}
