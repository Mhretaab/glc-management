package com.glc.managment.member;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by mberhe on 12/4/18.
 */
@Embeddable
public class MemberChurchId implements Serializable {
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "church_id")
    private Long churchId;

    public MemberChurchId() {

    }

    public MemberChurchId(Long memberId, Long churchId) {
        this.memberId = memberId;
        this.churchId = churchId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberChurchId that = (MemberChurchId) o;

        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) return false;
        return churchId != null ? churchId.equals(that.churchId) : that.churchId == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, churchId);
    }
}
