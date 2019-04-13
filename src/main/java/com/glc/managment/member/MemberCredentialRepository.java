package com.glc.managment.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by mberhe on 4/13/19.
 */
@Repository
public interface MemberCredentialRepository extends JpaRepository<MemberCredential, Long> {
    MemberCredential findByMemberAndAndCredentialsType(Member member, CredentialsTypeEnum credentialsTypeEnum);
    MemberCredential findByCredentialsResetCode(String credentialsResetCode);
}
