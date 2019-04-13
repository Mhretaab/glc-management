package com.glc.managment.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glc.managment.common.error.ErrorCodes;
import com.glc.managment.common.error.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by mberhe on 4/13/19.
 */
public class RestAPIAuthenticationFailureHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpStatus.FORBIDDEN.value());

        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, new ErrorDTO(
                ErrorCodes.ACCESS_DENIED_ERROR, authException.getMessage()
        ));
        out.flush();
    }
}