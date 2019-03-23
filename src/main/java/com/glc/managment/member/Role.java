package com.glc.managment.member;

import com.glc.managment.common.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by mberhe on 12/5/18.
 */
@Entity
@Table(indexes = {
        @Index(name = "role_idx_uuid", columnList = "uuid", unique = true),
        @Index(name = "role_idx_member_name", columnList = "memberId, name", unique = true)
})
public class Role extends AbstractEntity implements Serializable {

    @NotNull(message = "error.validation.role.member.required")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "memberId", nullable = false, columnDefinition = "bigint")
    private Member member;

    @NotNull(message = "error.validation.role.name.required")
    @Column(nullable = false, length = 50)
    @Enumerated(value = EnumType.STRING)
    private RoleEnum name;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public RoleEnum getName() {
        return name;
    }

    public void setName(RoleEnum name) {
        this.name = name;
    }
}
