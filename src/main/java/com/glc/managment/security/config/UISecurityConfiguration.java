package com.glc.managment.security.config;


import com.glc.managment.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * Created by mberhe on 4/13/19.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Order(5)
public class UISecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String LOGIN_PAGE = "/ui/login";

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/fonts/**", "/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http
                .antMatcher("/**/**")
                .authorizeRequests()
                .antMatchers(
                        LOGIN_PAGE
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(LOGIN_PAGE)
                .loginProcessingUrl("/ui/authenticate")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/ui/logout")
                .permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(uiAuthenticationFailureHandler())
                .and()
                .headers()
                .cacheControl().disable()
                .frameOptions().disable();
    }

    private static AuthenticationEntryPoint uiAuthenticationFailureHandler() {
        return new UIAuthenticationFailureHandler(LOGIN_PAGE);
    }
}

