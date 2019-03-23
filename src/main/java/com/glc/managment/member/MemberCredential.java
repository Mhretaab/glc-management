package com.glc.managment.member;

import com.glc.managment.common.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by mberhe on 12/5/18.
 */
@Entity
@Table
public class MemberCredential extends AbstractEntity implements Serializable{
    @NotNull(message = "error.validation.user.credential.member.required")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "memberId", nullable = false, columnDefinition = "bigint")
    private Member member;

    @NotNull(message = "error.validation.member.credential.credentials.required")
    @Size(min = 60, max = 60, message = "error.validation.user.credentials.credentials.invalid.length")
    private String credentials;

    @Column(nullable = false, length = 50)
    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "error.validation.member.credential.type.required")
    private CredentialsTypeEnum credentialsType;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public CredentialsTypeEnum getCredentialsType() {
        return credentialsType;
    }

    public void setCredentialsType(CredentialsTypeEnum credentialsType) {
        this.credentialsType = credentialsType;
    }
}
