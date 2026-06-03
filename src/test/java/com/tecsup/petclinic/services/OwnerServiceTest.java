package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Owner;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("h2")
@Slf4j
public class OwnerServiceTest {

	@Autowired
	private OwnerService ownerService;

	@Test
	public void testFindOwnerById() {
		Owner owner = ownerService.findById(1L).orElseThrow();

		assertEquals("George", owner.getFirstName());
		assertEquals("Franklin", owner.getLastName());
		assertEquals("George Franklin", owner.getFirstName() + " " + owner.getLastName());
	}

	@Test
	public void testFindByLastName() {
		List<Owner> owners = ownerService.findByLastName("Davis");

		assertEquals(2, owners.size());

		assertTrue(owners.stream()
				.anyMatch(owner -> "Betty".equals(owner.getFirstName())
						&& "Davis".equals(owner.getLastName())));

		assertTrue(owners.stream()
				.anyMatch(owner -> "Harold".equals(owner.getFirstName())
						&& "Davis".equals(owner.getLastName())));
	}

	@Test
	public void testFindByCity() {
		List<Owner> owners = ownerService.findByCity("Madison");

		assertTrue(!owners.isEmpty());

		assertTrue(owners.stream()
				.allMatch(owner -> "Madison".equals(owner.getCity())));
	}
}