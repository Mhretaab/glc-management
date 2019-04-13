package com.glc.managment.church;

import com.glc.managment.address.Address;
import com.glc.managment.common.AbstractEntity;
import com.glc.managment.member.MemberChurch;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mberhe on 12/4/18.
 */
@Entity
@Table(indexes = {
        @Index(name = "church_idx_uuid", columnList = "uuid", unique = true),
        @Index(name = "church_idx_primary_phone", columnList = "primaryTelephone"),
        @Index(name = "address_idx_email", columnList = "email")
})
public class Church extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = -6321339311569884536L;

    @NotNull(message = "error.validation.church.given.name.required")
    @Size(max = 50, message = "error.validation.church.firstName.invalid.length")
    private String name;

    @Pattern(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            flags = {Pattern.Flag.DOTALL, Pattern.Flag.CASE_INSENSITIVE})
    @Size(min = 5, max = 255, message = "error.validation.church.email.invalid.length")
    private String email;

    @URL
    private String webAddress;

    @Size(max = 13, message = "error.validation.church.phoneNumber.invalid.length")
    private String mobilePhoneNumber;

    @Size(max = 13, message = "error.validation.church.phoneNumber.invalid.length")
    private String primaryTelephone;

    @Size(max = 13, message = "error.validation.church.phoneNumber.invalid.length")
    private String secondaryTelephone;

    @NotNull(message = "error.validation.church.given.name.required")
    @Size(max = 50, message = "error.validation.church.firstName.invalid.length")
    @Size(max = 50, message = "error.validation.church.firstName.invalid.length")
    private String contactPersonName;
    @Enumerated(value = EnumType.STRING)
    private ContactPersonTitleEnum contactPersonTitleEnum;

    @NotNull(message = "error.validation.address.required")
    @JoinColumn(name = "addressId")
    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "church", orphanRemoval = true)
    private List<MemberChurch> previousMembers = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
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

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public ContactPersonTitleEnum getContactPersonTitleEnum() {
        return contactPersonTitleEnum;
    }

    public void setContactPersonTitleEnum(ContactPersonTitleEnum contactPersonTitleEnum) {
        this.contactPersonTitleEnum = contactPersonTitleEnum;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<MemberChurch> getPreviousMembers() {
        return previousMembers;
    }

    public void setPreviousMembers(List<MemberChurch> previousMembers) {
        this.previousMembers = previousMembers;
    }
}
