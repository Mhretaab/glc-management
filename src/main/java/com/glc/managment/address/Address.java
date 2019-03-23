package com.glc.managment.address;

import com.glc.managment.common.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by mberhe on 12/4/18.
 */
@Entity
@Table(indexes = {
        @Index(name = "address_idx_uuid", columnList = "uuid", unique = true),
        @Index(name = "address_idx_line1", columnList = "line1")
})
public class Address extends AbstractEntity implements Serializable {
    @NotNull(message = "error.validation.country.required")
    @Size(min = 2, max = 5, message = "error.validation.country.invalid.length")
    private String country;
    @Size(max = 50, message = "error.validation.city.invalid.length")
    private String city;
    @Size(max = 255, message = "error.validation.line1.invalid.length")
    private String line1; //woreda
    @Size(max = 255, message = "error.validation.line2.invalid.length")
    private String line2; //kebele
    @Size(max = 50, message = "error.validation.zipcode.invalid.length")
    private String zipcode;
    @Size(max = 50, message = "error.validation.house.number.invalid.length")
    private String houseNumber;
    @Enumerated(value = EnumType.STRING)
    private AddressTypeEnum addressTypeEnum;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public AddressTypeEnum getAddressTypeEnum() {
        return addressTypeEnum;
    }

    public void setAddressTypeEnum(AddressTypeEnum addressTypeEnum) {
        this.addressTypeEnum = addressTypeEnum;
    }
}
