package com.glc.managment.security.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by mberhe on 4/13/19.
 */
public class UIAuthenticationFailureHandler implements AuthenticationEntryPoint {

    private final String loginPage;

    public UIAuthenticationFailureHandler(String loginPage) {
        this.loginPage = loginPage;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if(null == request.getContentType() || !request.getContentType().equalsIgnoreCase("application/json")) {
            response.sendRedirect(request.getContextPath() + loginPage);
        } else {
            new RestAPIAuthenticationFailureHandler().commence(request, response, authException);
        }
    }
}
