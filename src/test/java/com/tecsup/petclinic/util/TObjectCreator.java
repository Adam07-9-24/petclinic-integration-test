package com.tecsup.petclinic.util;

import com.tecsup.petclinic.dtos.OwnerDTO;

public final class TObjectCreator {

    private TObjectCreator() {
    }

    public static OwnerDTO newOwner() {
        return OwnerDTO.builder()
                .firstName("Alice")
                .lastName("Tester")
                .address("100 Test St.")
                .city("Madison")
                .telephone("6085551111")
                .build();
    }

    public static OwnerDTO getOwnerTO() {
        return OwnerDTO.builder()
                .id(1L)
                .firstName("George")
                .lastName("Franklin")
                .address("110 W. Liberty St.")
                .city("Madison")
                .telephone("6085551023")
                .build();
    }
}
