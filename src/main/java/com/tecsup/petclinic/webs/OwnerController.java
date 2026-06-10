package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.services.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping(value = "/owners")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<OwnerDTO> create(@RequestBody OwnerDTO ownerTO) {
        Owner createdOwner = ownerService.create(mapToEntity(ownerTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDto(createdOwner));
    }

    @GetMapping(value = "/owners/{id}")
    ResponseEntity<OwnerDTO> findById(@PathVariable Long id) {
        return ownerService.findById(id)
                .map(owner -> ResponseEntity.ok(mapToDto(owner)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/owners/{id}")
    ResponseEntity<OwnerDTO> update(@RequestBody OwnerDTO ownerTO, @PathVariable Long id) {
        return ownerService.findById(id)
                .map(existingOwner -> {
                    existingOwner.setFirstName(ownerTO.getFirstName());
                    existingOwner.setLastName(ownerTO.getLastName());
                    existingOwner.setAddress(ownerTO.getAddress());
                    existingOwner.setCity(ownerTO.getCity());
                    existingOwner.setTelephone(ownerTO.getTelephone());
                    Owner updatedOwner = ownerService.update(existingOwner);
                    return ResponseEntity.ok(mapToDto(updatedOwner));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/owners/{id}")
    ResponseEntity<String> delete(@PathVariable Long id) {
        if (ownerService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ownerService.delete(id);
        return ResponseEntity.ok(" Delete ID :" + id);
    }

    private OwnerDTO mapToDto(Owner owner) {
        return OwnerDTO.builder()
                .id(owner.getId())
                .firstName(owner.getFirstName())
                .lastName(owner.getLastName())
                .address(owner.getAddress())
                .city(owner.getCity())
                .telephone(owner.getTelephone())
                .build();
    }

    private Owner mapToEntity(OwnerDTO ownerTO) {
        Owner owner = new Owner();
        owner.setId(ownerTO.getId());
        owner.setFirstName(ownerTO.getFirstName());
        owner.setLastName(ownerTO.getLastName());
        owner.setAddress(ownerTO.getAddress());
        owner.setCity(ownerTO.getCity());
        owner.setTelephone(ownerTO.getTelephone());
        return owner;
    }
}
