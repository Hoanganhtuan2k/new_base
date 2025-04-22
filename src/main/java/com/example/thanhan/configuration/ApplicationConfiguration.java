package com.example.thanhan.configuration;

import com.example.thanhan.exception.BusinessException;
import com.example.thanhan.model.entity.user.User;
import com.example.thanhan.model.enumeration.BusinessExceptionStatus;
import com.example.thanhan.repository.UserRepository;
import com.example.thanhan.repository.UserRoleJDBCRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

  private final UserRepository userRepository;
  private final UserRoleJDBCRepo userRoleJDBCRepo;

  @Bean
  public UserDetailsService userDetailsService () {
    return username -> {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new BusinessException(BusinessExceptionStatus.INVALID_CREDENTIALS));
            user.setRoles(userRoleJDBCRepo.getRoleNamesByUserId(user.getId()));
            return user;
    };
  }

  @Bean
  public AuthenticationProvider authenticationProvider () {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager ( AuthenticationConfiguration authenticationConfiguration ) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder () {
    return new BCryptPasswordEncoder();
  }

}
