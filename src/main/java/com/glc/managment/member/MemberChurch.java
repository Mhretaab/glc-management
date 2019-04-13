package com.glc.managment.member;

import com.glc.managment.church.Church;
import com.glc.managment.common.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Created by mberhe on 12/4/18.
 */
@Entity
@Table(name = "member_church")
public class MemberChurch extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = -3715954794577270092L;

    @EmbeddedId
    private MemberChurchId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("churchId")
    private Church church;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Date membershipFromDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Date membershipToDate;

    private boolean anyDisagreement;

    private String disagreementDescription;

    private boolean hasAccompanyingLetter;

    private String reasonForLeaving;

    public MemberChurch() {
    }

    public MemberChurch(Member member, Church church) {
        this.member = member;
        this.church = church;
        this.id = new MemberChurchId(member.getId(), church.getId());
    }

    public void setId(MemberChurchId id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Church getChurch() {
        return church;
    }

    public void setChurch(Church church) {
        this.church = church;
    }

    public Date getMembershipFromDate() {
        return membershipFromDate;
    }

    public void setMembershipFromDate(Date membershipFromDate) {
        this.membershipFromDate = membershipFromDate;
    }

    public Date getMembershipToDate() {
        return membershipToDate;
    }

    public void setMembershipToDate(Date membershipToDate) {
        this.membershipToDate = membershipToDate;
    }

    public boolean isAnyDisagreement() {
        return anyDisagreement;
    }

    public void setAnyDisagreement(boolean anyDisagreement) {
        this.anyDisagreement = anyDisagreement;
    }

    public String getDisagreementDescription() {
        return disagreementDescription;
    }

    public void setDisagreementDescription(String disagreementDescription) {
        this.disagreementDescription = disagreementDescription;
    }

    public boolean isHasAccompanyingLetter() {
        return hasAccompanyingLetter;
    }

    public void setHasAccompanyingLetter(boolean hasAccompanyingLetter) {
        this.hasAccompanyingLetter = hasAccompanyingLetter;
    }

    public String getReasonForLeaving() {
        return reasonForLeaving;
    }

    public void setReasonForLeaving(String reasonForLeaving) {
        this.reasonForLeaving = reasonForLeaving;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MemberChurch that = (MemberChurch) o;

        if (member != null ? !member.equals(that.member) : that.member != null) return false;
        return church != null ? church.equals(that.church) : that.church == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(member, church);
    }
}
