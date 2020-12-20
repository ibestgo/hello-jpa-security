package com.bestgo.hellojpasecurity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;

  // user the builder to configure authentication
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // I simply provide my UserDetailsService and spring will use it
    // to determine if the user can be authenticated
    auth.userDetailsService(userDetailsService);
  }

  // setup authorization
  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/admin").hasRole("ADMIN")
        .antMatchers("/user").hasAnyRole("ADMIN", "USER")
        .antMatchers("/").permitAll()
        .and().formLogin();
  }

  // create this as spring xecurity expects for you to create it as a bean
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return NoOpPasswordEncoder.getInstance(); // no encode for testing now
  }
}
