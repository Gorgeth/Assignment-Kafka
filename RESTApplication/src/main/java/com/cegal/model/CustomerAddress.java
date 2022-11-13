package com.cegal.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CustomerAddress.Builder.class)
public class CustomerAddress {

  private String name;
  private String street;
  private String postcode;

  private CustomerAddress(String name, String street, String postcode) {
    this.name = name;
    this.street = street;
    this.postcode = postcode;
  }


  public String getName() {
    return name;
  }

  public String getStreet() {
    return street;
  }

  public String getPostcode() {
    return postcode;
  }


  public static Builder builder() {
    return new Builder();
  }

  @JsonPOJOBuilder(withPrefix = "set")
  public static class Builder {
    private String name;
    private String street;
    private String postcode;

    public CustomerAddress build() {
      return new CustomerAddress(name, street, postcode);
    }


    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setStreet(String street) {
      this.street = street;
      return this;
    }

    public Builder setPostcode(String postcode) {
      this.postcode = postcode;
      return this;
    }
  }
}


