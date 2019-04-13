package com.glc.managment.security;

import com.glc.managment.common.error.InvalidCredentialsException;
import com.glc.managment.common.error.ItemNotFoundException;
import com.glc.managment.common.error.MemberNotFoundException;
import com.glc.managment.member.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by mberhe on 4/13/19.
 */
@Service("authenticationService")
@Transactional(dontRollbackOn = AuthenticationException.class)
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final MessageSource messageSource;
    private final MemberRepository memberRepository;
    private final MemberStatusRepository memberStatusRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationContext applicationContext;
    private final MemberCredentialRepository memberCredentialRepository;

    @Autowired
    public AuthenticationServiceImpl(MessageSource messageSource, MemberRepository memberRepository, MemberStatusRepository memberStatusRepository,
                                     PasswordEncoder passwordEncoder, ApplicationContext applicationContext, MemberCredentialRepository memberCredentialRepository) {
        this.messageSource = messageSource;
        this.memberRepository = memberRepository;
        this.memberStatusRepository = memberStatusRepository;
        this.passwordEncoder = passwordEncoder;
        this.applicationContext = applicationContext;
        this.memberCredentialRepository = memberCredentialRepository;
    }

    @Override
    public Member authenticateByEmail(String email, String credentials, CredentialsTypeEnum credentialsType) {
        if (log.isDebugEnabled())
            log.debug(String.format("Authenticating %s Using %s", email, credentialsType.name()));

        Member member = memberRepository.findByEmailIgnoreCase(email);

        if (null == member) {
            if (log.isInfoEnabled())
                log.info(String.format("Member not found matching email: %s", email));
            throw new MemberNotFoundException(email);
        }

        if (null == credentialsType)
            credentialsType = CredentialsTypeEnum.PASSWORD;

        MemberCredential memberCredential = memberCredentialRepository.findByMemberAndAndCredentialsType(member, credentialsType);

        if (null == memberCredential) {
            if (log.isInfoEnabled())
                log.info(String.format("Credentials of type: %s not found for: %s", credentialsType.name(), email));
            throw new InvalidCredentialsException(email);
        }

        boolean isAuthenticated = passwordEncoder.matches(credentials, memberCredential.getCredentials());

        if (isAuthenticated) {
            return member;
        } else {
            if (log.isInfoEnabled())
                log.info(String.format("Credentials do not match for email: %s Using Credentials Type %s", email, credentialsType.name()));

            throw new InvalidCredentialsException(email);

        }
    }

    @Override
    public AuthenticationToken loginByEmail(String email, String credentials, CredentialsTypeEnum credentialsType) {
        Member authenticatedMember = authenticateByEmail(email, credentials, credentialsType);

        AuthenticationToken authenticationToken = new AuthenticationToken(authenticatedMember, email, credentials, getGrantedAuthorities(authenticatedMember));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        setLoginStatusForCurrentMember();

        return authenticationToken;
    }

    private List<GrantedAuthority> getGrantedAuthorities(Member member) {
        if (null == member || null == member.getRoles())
            return null;

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        for (Role r : member.getRoles()) {
            grantedAuthorities.add(r);
        }

        return grantedAuthorities;
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public Member getCurrentMember() {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AuthenticationToken) {

            Member member = ((AuthenticationToken) SecurityContextHolder.getContext().getAuthentication()).getMember();

            if (null != member)
                member = memberRepository.findByUuid(member.getUuid()); //Load user from the database so that db session is enabled for lazy loaded items to load

            return member;

        } else {
            return null;
        }
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }

    @Override
    public boolean canCurrentMemberEditMember(Member member) {
        Member currentMember = getCurrentMember();
        return null != currentMember && (currentMember.hasRole(RoleEnum.ROLE_ADMIN.name()) ||
                currentMember.hasRole(RoleEnum.ROLE_PASTOR.name())
        );
    }

    @Override
    public boolean canCurrentMemberCrudMembers() {
        Member currentMember = getCurrentMember();
        return null != currentMember && (currentMember.hasRole(RoleEnum.ROLE_ADMIN.name()) ||
                currentMember.hasRole(RoleEnum.ROLE_PASTOR.name())
        );
    }

    @Override
    public boolean canCurrentMemberAssignRoles() {
        Member currentMember = getCurrentMember();
        return null != currentMember && (currentMember.hasRole(RoleEnum.ROLE_ADMIN.name()) ||
                currentMember.hasRole(RoleEnum.ROLE_PASTOR.name())
        );
    }

    @Override
    public void setLoginStatusForCurrentMember() {
        Member member = getCurrentMember();
        if (member == null) return;

        MemberStatus memberStatus = memberStatusRepository.findByMember(member);
        if (memberStatus == null) {
            memberStatus = new MemberStatus();
            memberStatus.setUuid(new StringBuilder().append(member.getUuid()).reverse().toString());
            memberStatus.setMember(member);
        }

        memberStatus.setUuid(new StringBuilder().append(member.getUuid()).reverse().toString());

        memberStatus.setLastLoginByWeb(new Date());

        try {
            memberStatusRepository.save(memberStatus);
        } catch (Exception ex) {
            log.warn(String.format("Unable to save member status. Error: %s", ex.getMessage()));
        }
    }

    @Override
    public String createCredentialsResetCode(String email) {
        if (null == email || email.trim().isEmpty())
            throw new IllegalArgumentException("email cannot be null or empty");

        Member member = memberRepository.findByEmailIgnoreCase(email);

        if (null == member)
            throw new ItemNotFoundException(email);

        MemberCredential memberCredential = null;

        for (MemberCredential mc : member.getCredentials()) {
            if (mc.getCredentialsType() == CredentialsTypeEnum.PASSWORD) {
                memberCredential = mc;
            }
        }

        if (null == memberCredential) {
            memberCredential = new MemberCredential();
            memberCredential.setMember(member);
            memberCredential.setCredentialsType(CredentialsTypeEnum.PASSWORD);
            memberCredential.setCredentials(UUID.randomUUID().toString());
        }

        memberCredential.setCredentialsResetCode(UUID.randomUUID().toString());
        memberCredential.setCredentialsResetCodeCreatedDate(new Date());

        memberCredential = memberCredentialRepository.save(memberCredential);

        return memberCredential.getCredentialsResetCode();
    }

    @Override
    public boolean isCredentialsResetCodeValid(String credentialsResetCode) {
        if (null == credentialsResetCode || credentialsResetCode.trim().isEmpty())
            return false;

        MemberCredential memberCredential = memberCredentialRepository.findByCredentialsResetCode(credentialsResetCode);

        return null != memberCredential;
    }

    @Override
    public void cancelCredentialsResetCodeIfValid(String credentialsResetCode) {
        if (null == credentialsResetCode || credentialsResetCode.trim().isEmpty())
            return;

        MemberCredential memberCredential = memberCredentialRepository.findByCredentialsResetCode(credentialsResetCode);

        if (null != memberCredential) {

            memberCredential.setCredentialsResetCode(null);
            memberCredential.setCredentialsResetCodeCreatedDate(null);
            memberCredentialRepository.save(memberCredential);
        }
    }
}
