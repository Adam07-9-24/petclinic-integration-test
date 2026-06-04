package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Owner;
import java.util.List;
import java.util.Optional;

public interface OwnerService {

    Optional<Owner> findById(Long id);
    List<Owner> findByLastName(String lastName);
    List<Owner> findByCity(String city);

    // --- MÉTODOS NUEVOS PARA EL INTEGRANTE A ---
    Owner create(Owner owner);
    Owner update(Owner owner);
    void delete(Long id);
}