package com.glc.managment.security;

import com.glc.managment.member.CredentialsTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Created by mberhe on 4/13/19.
 */
public class AuthenticationProviderImpl implements AuthenticationProvider {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationProviderImpl.class);

    private final AuthenticationService authenticationService;
    private final CredentialsTypeEnum credentialsType;

    public AuthenticationProviderImpl(AuthenticationService authenticationService, CredentialsTypeEnum credentialsType) {
        this.authenticationService = authenticationService;
        this.credentialsType = credentialsType;

        assert this.credentialsType != null;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return authenticationService.loginByEmail((String)authentication.getPrincipal(), (String)authentication.getCredentials(), credentialsType);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
