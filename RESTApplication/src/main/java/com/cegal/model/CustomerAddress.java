package com.cegal.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModelProperty;


/**
 * Basic Jackson json for use as input with the POST endpoint, /customerAddress.
 */
@JsonDeserialize(builder = CustomerAddress.Builder.class)
public class CustomerAddress {

    @ApiModelProperty(value = "Customer name", required = true)
    private String name;
    @ApiModelProperty(value = "City for the address", required = true)
    private String city;
    @ApiModelProperty(value = "Area code for the address", required = true)
    private String areaCode;
    @ApiModelProperty(value = "Street name and number", required = true)
    private String street;
    @ApiModelProperty("P.O. Box used by the company")
    private Long poBox;
    @ApiModelProperty("Public contact phone number for the customer")
    private Long phoneNumber;


    public CustomerAddress(String name, String city, String areaCode, String street,
                           Long poBox, Long phoneNumber) {
        this.name = name;
        this.city = city;
        this.areaCode = areaCode;
        this.street = street;
        this.poBox = poBox;
        this.phoneNumber = phoneNumber;
    }


    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public String getStreet() {
        return street;
    }

    public Long getPoBox() {
        return poBox;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }


    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private String name;
        private String city;
        private String areaCode;
        private String street;
        private Long poBox;
        private Long phoneNumber;


        public CustomerAddress build() {
            return new CustomerAddress(name, city, areaCode, street, poBox, phoneNumber);
        }


        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setAreaCode(String areaCode) {
            this.areaCode = areaCode;
            return this;
        }

        public Builder setStreet(String street) {
            this.street = street;
            return this;
        }

        public Builder setPoBox(Long poBox) {
            this.poBox = poBox;
            return this;
        }

        public Builder setPhoneNumber(Long phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }
    }
}


