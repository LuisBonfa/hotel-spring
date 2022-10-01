package com.alten.hotel.common.configuration.security;

import com.alten.hotel.security.authorization.entry.CustomAuthenticationEntryPoint;
import com.alten.hotel.security.authorization.filter.AuthorizationFilter;
import com.alten.hotel.security.authorization.provider.CustomAuthenticationProvider;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Profile("!test")
@Configuration
@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    AuthorizationFilter authorizationFilter;
    UserDetailsService jwtUserDetailsService;
    CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    public WebSecurityConfig(AuthorizationFilter authorizationFilter, UserDetailsService jwtUserDetailsService, CustomAuthenticationEntryPoint customAuthenticationEntryPoint, CustomAuthenticationProvider customAuthenticationProvider) {
        this.authorizationFilter = authorizationFilter;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Bean
    @Profile("!test")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    @Profile("!test")
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
                        "/configuration/**", "/swagger-ui.html", "/swagger-ui/**", "/webjars/**").permitAll()
                .antMatchers(HttpMethod.POST, "/user").permitAll()
                .antMatchers("/booking-history", "/role/**", "/user-role/**").hasAnyAuthority("admin")
                .anyRequest().authenticated().and().
                exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}