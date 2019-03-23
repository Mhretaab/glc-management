package com.glc.managment.member;

import com.glc.managment.address.Address;
import com.glc.managment.church.Church;
import com.glc.managment.common.AbstractEntity;
import com.glc.managment.department.Department;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mberhe on 12/4/18.
 */
@Entity
@Table(indexes = {
        @Index(name = "member_idx_uuid", columnList = "uuid", unique = true),
        @Index(name = "member_idx_given_name", columnList = "givenName"),
        @Index(name = "member_idx_email", columnList = "email"),
        @Index(name = "member_idx_phone_number", columnList = "mobilePhoneNumber")
})
public class Member extends AbstractEntity implements Serializable{

    @NotNull(message = "error.validation.member.given.name.required")
    @Size(max = 50, message = "error.validation.member.firstName.invalid.length")
    private String givenName;

    @Size(max = 50, message = "error.validation.member.firstName.invalid.length")
    private String fatherName;

    @Size(max = 50, message = "error.validation.member.firstName.invalid.length")
    private String grandFatherName;

    @NotNull(message = "error.validation.member.email.required")
    @Pattern(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            flags = {Pattern.Flag.DOTALL, Pattern.Flag.CASE_INSENSITIVE})
    @Size(min = 5, max = 255, message = "error.validation.member.email.invalid.length")
    private String email;

    @Size(max = 13, message = "error.validation.member.phoneNumber.invalid.length")
    private String mobilePhoneNumber;

    @Size(max = 13, message = "error.validation.member.phoneNumber.invalid.length")
    private String primaryTelephone;

    @Size(max = 13, message = "error.validation.member.phoneNumber.invalid.length")
    private String secondaryTelephone;

    @Size(min = 36, max = 36, message = "error.validation.member.phoneNumber.invalid.length")
    private String profilePicture;

    @Column(nullable = false, length = 2)
    @Enumerated(value = EnumType.STRING)
    private GenderEnum genderEnum;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Date dateOfBirth;

    @Column(nullable = true, length = 50)
    @Enumerated(value = EnumType.STRING)
    private EducationalStatusEnum educationalStatusEnum;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "invitedBy", nullable = true, columnDefinition = "bigint")
    private Member invitedBy;

    private String reasonForMembership;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private MartialStatusEnum martialStatusEnum;

    @Column(nullable = false, length = 50)
    @Enumerated(value = EnumType.STRING)
    private ApprovalStatusEnum approvalStatusEnum;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "MemberAddress",
            joinColumns = { @JoinColumn(name = "memberId", columnDefinition = "bigint") },
            inverseJoinColumns = { @JoinColumn(name = "addressId", columnDefinition = "bigint") })
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private List<Address> addresses;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<MemberChurch> previousChurches = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Date bornAgainDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "churchId", nullable = false, columnDefinition = "bigint")
    private Church bornAgainChurch;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "MemberDepartment",
            joinColumns = { @JoinColumn(name = "memberId", columnDefinition = "bigint") },
            inverseJoinColumns = { @JoinColumn(name = "departmentId", columnDefinition = "bigint") })
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private List<Department> departments;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<MemberCredential> credentials;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Role> roles;

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getGrandFatherName() {
        return grandFatherName;
    }

    public void setGrandFatherName(String grandFatherName) {
        this.grandFatherName = grandFatherName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getPrimaryTelephone() {
        return primaryTelephone;
    }

    public void setPrimaryTelephone(String primaryTelephone) {
        this.primaryTelephone = primaryTelephone;
    }

    public String getSecondaryTelephone() {
        return secondaryTelephone;
    }

    public void setSecondaryTelephone(String secondaryTelephone) {
        this.secondaryTelephone = secondaryTelephone;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public GenderEnum getGenderEnum() {
        return genderEnum;
    }

    public void setGenderEnum(GenderEnum genderEnum) {
        this.genderEnum = genderEnum;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public EducationalStatusEnum getEducationalStatusEnum() {
        return educationalStatusEnum;
    }

    public void setEducationalStatusEnum(EducationalStatusEnum educationalStatusEnum) {
        this.educationalStatusEnum = educationalStatusEnum;
    }

    public Member getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(Member invitedBy) {
        this.invitedBy = invitedBy;
    }

    public String getReasonForMembership() {
        return reasonForMembership;
    }

    public void setReasonForMembership(String reasonForMembership) {
        this.reasonForMembership = reasonForMembership;
    }

    public MartialStatusEnum getMartialStatusEnum() {
        return martialStatusEnum;
    }

    public void setMartialStatusEnum(MartialStatusEnum martialStatusEnum) {
        this.martialStatusEnum = martialStatusEnum;
    }

    public ApprovalStatusEnum getApprovalStatusEnum() {
        return approvalStatusEnum;
    }

    public void setApprovalStatusEnum(ApprovalStatusEnum approvalStatusEnum) {
        this.approvalStatusEnum = approvalStatusEnum;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<MemberChurch> getPreviousChurches() {
        return previousChurches;
    }

    public void setPreviousChurches(List<MemberChurch> previousChurches) {
        this.previousChurches = previousChurches;
    }

    public Date getBornAgainDate() {
        return bornAgainDate;
    }

    public void setBornAgainDate(Date bornAgainDate) {
        this.bornAgainDate = bornAgainDate;
    }

    public Church getBornAgainChurch() {
        return bornAgainChurch;
    }

    public void setBornAgainChurch(Church bornAgainChurch) {
        this.bornAgainChurch = bornAgainChurch;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<MemberCredential> getCredentials() {
        return credentials;
    }

    public void setCredentials(List<MemberCredential> credentials) {
        this.credentials = credentials;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
