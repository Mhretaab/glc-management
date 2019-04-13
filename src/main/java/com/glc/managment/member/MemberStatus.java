package com.glc.managment.member;

import com.glc.managment.common.AbstractEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by mberhe on 4/13/19.
 */
@Entity
@Table(indexes = {
        @Index(name = "member_status_idx_uuid", columnList = "uuid", unique = true),
        @Index(name = "member_status_idx_user", columnList = "memberId")
})
public class MemberStatus extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 8778215014685897711L;

    @NotNull(message = "error.validation.member.required")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "memberId", nullable = false, columnDefinition = "bigint")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Member member;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Date lastLoginByWeb;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Date lastLogin;

    public MemberStatus() {
    }

    public MemberStatus(@NotNull(message = "error.validation.member.required") Member member, Date lastLoginByWeb, Date lastLogin) {
        this.member = member;
        this.lastLoginByWeb = lastLoginByWeb;
        this.lastLogin = lastLogin;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Date getLastLoginByWeb() {
        return lastLoginByWeb;
    }

    public void setLastLoginByWeb(Date lastLoginByWeb) {
        this.lastLoginByWeb = lastLoginByWeb;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public void preInsert() {
        super.preInsert();
        updateLastLoginDate();
    }

    @Override
    public void preUpdate() {
        super.preUpdate();
        updateLastLoginDate();
    }

    private void updateLastLoginDate() {
        List<Date> lastLoginDatesByChannel = new ArrayList<Date>();
        if(null != lastLoginByWeb){
            lastLoginDatesByChannel.add(lastLoginByWeb);
        }

        if(!lastLoginDatesByChannel.isEmpty()){
            lastLogin = Collections.max(lastLoginDatesByChannel);
        }
    }
}
