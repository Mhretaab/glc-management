package com.glc.managment.security;

import com.glc.managment.member.CredentialsTypeEnum;
import com.glc.managment.member.Member;

public interface AuthenticationService {

    public static final int MAX_NUMBER_OF_FAILED_AUTHENTICATION_ATTEMPTS_BEFORE_LOCKING = 5;

    Member authenticateByEmail(String email, String credentials, CredentialsTypeEnum credentialsType);
    AuthenticationToken loginByEmail(String email, String credentials, CredentialsTypeEnum credentialsType);
    String encodePassword(String password);
    Member getCurrentMember();
    void logout();
    boolean canCurrentMemberEditMember(Member member);
    boolean canCurrentMemberCrudMembers();
    boolean canCurrentMemberAssignRoles();
    void setLoginStatusForCurrentMember();
    String createCredentialsResetCode(String email);
    boolean isCredentialsResetCodeValid(String credentialsResetCode);
    void cancelCredentialsResetCodeIfValid(String credentialsResetCode);
}