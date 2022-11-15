package com.cegal.avro;

import com.cegal.avro.model.CustomerAddress;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerAddressDeSerializerTest {

    private final CustomerAddressDeSerializer serializer = new CustomerAddressDeSerializer();

    @Test
    void serializerCustomerAddress() {
        CustomerAddress address = CustomerAddress.newBuilder()
                .setName("Name")
                .setCity("City")
                .setAreaCode("AreaCode")
                .setStreet("Street")
                .setPoBox(5555555L)
                .setPhoneNumber(98765432L)
                .build();

        byte[] serializedAddress = serializer.serialize("NotUsed", address);
        CustomerAddress deserializedAddress = serializer.deserialize("NotUsed", serializedAddress);

        assertAll(
                () -> assertEquals(address.getName().toString(), deserializedAddress.getName().toString()),
                () -> assertEquals(address.getCity().toString(), deserializedAddress.getCity().toString()),
                () -> assertEquals(address.getAreaCode().toString(), deserializedAddress.getAreaCode().toString()),
                () -> assertEquals(address.getStreet().toString(), deserializedAddress.getStreet().toString()),
                () -> assertEquals(address.getPoBox(), deserializedAddress.getPoBox()),
                () -> assertEquals(address.getPhoneNumber(), deserializedAddress.getPhoneNumber())
        );
    }

}