package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OwnerServiceTest {

    @Autowired
    private OwnerService ownerService;

    @Test
    public void testCreateOwner() {
        // 1. Crear dueño
        Owner owner = new Owner();
        owner.setFirstName("Neyra");
        owner.setLastName("Prueba");

        // Usamos create() en vez de save()
        Owner created = ownerService.create(owner);

        // Verificar que se asigna id
        assertNotNull(created.getId(), "Se debe asignar un ID al crear");
    }

    @Test
    public void testUpdateOwner() {
        // 1. Crear
        Owner owner = new Owner();
        owner.setFirstName("Carlos");
        owner.setLastName("Original");
        Owner created = ownerService.create(owner);

        // 2. Actualizar
        created.setLastName("Actualizado");
        ownerService.update(created); // Usamos update() en vez de save()

        // 3. Re-leer desde H2 para confirmar el cambio
        Owner readFromH2 = ownerService.findById(created.getId()).get();
        assertEquals("Actualizado", readFromH2.getLastName(), "El apellido debió cambiar en H2");
    }

    @Test
    public void testDeleteOwner() {
        // 1. Crear
        Owner owner = new Owner();
        owner.setFirstName("Para");
        owner.setLastName("Borrar");
        Owner created = ownerService.create(owner);
        Long idToDelete = created.getId();

        // 2. Borrar (pasando el ID en lugar del objeto completo)
        ownerService.delete(idToDelete);

        // 3. Verificar que lanza OwnerNotFoundException al buscarlo de nuevo
        assertThrows(OwnerNotFoundException.class, () -> {
            ownerService.findById(idToDelete).orElseThrow(() -> new OwnerNotFoundException("No encontrado"));
        });
    }
}