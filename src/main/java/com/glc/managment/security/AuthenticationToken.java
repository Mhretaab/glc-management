package com.glc.managment.security;

import com.glc.managment.member.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthenticationToken extends UsernamePasswordAuthenticationToken {

    private Member member;

    AuthenticationToken(Member member, Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    public void updateCachedMember(Member member) {
        if (null == member) {
            throw new NullPointerException("member cant be null");
        }
        if (!member.equals(this.member)) {
            throw new IllegalArgumentException("Provided member instance does not match with the cached member");
        }
        this.member = member;
    }
}