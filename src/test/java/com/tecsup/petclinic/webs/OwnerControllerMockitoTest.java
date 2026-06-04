package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import com.tecsup.petclinic.services.OwnerService;
import com.tecsup.petclinic.util.TObjectCreator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("h2")
@Slf4j
public class OwnerControllerMockitoTest {

	private static final ObjectMapper om = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private OwnerService ownerService;

	@Test
	public void testUpdateOwner() throws Exception {
		OwnerDTO ownerTO = TObjectCreator.getOwnerTO();
		OwnerDTO updateOwnerTO = OwnerDTO.builder()
				.id(ownerTO.getId())
				.firstName(ownerTO.getFirstName())
				.lastName("Updated")
				.address("200 Updated Ave.")
				.city("Monona")
				.telephone("6085559999")
				.build();

		Mockito.when(ownerService.findById(ownerTO.getId()))
				.thenReturn(ownerTO);
		Mockito.when(ownerService.update(any(OwnerDTO.class)))
				.thenReturn(updateOwnerTO);

		mockMvc.perform(put("/owners/" + ownerTO.getId())
						.content(om.writeValueAsString(updateOwnerTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(ownerTO.getId().intValue())))
				.andExpect(jsonPath("$.firstName", is(updateOwnerTO.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(updateOwnerTO.getLastName())))
				.andExpect(jsonPath("$.address", is(updateOwnerTO.getAddress())))
				.andExpect(jsonPath("$.city", is(updateOwnerTO.getCity())))
				.andExpect(jsonPath("$.telephone", is(updateOwnerTO.getTelephone())));
	}

	@Test
	public void testDeleteOwner() throws Exception {
		Long id = TObjectCreator.getOwnerTO().getId();

		Mockito.doNothing().when(ownerService).delete(id);

		mockMvc.perform(delete("/owners/" + id))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void testDeleteOwnerKO() throws Exception {
		Long id = 1000L;

		Mockito.doThrow(new OwnerNotFoundException("Record not found...!"))
				.when(ownerService).delete(id);

		mockMvc.perform(delete("/owners/" + id))
				.andDo(print())
				.andExpect(status().isNotFound());
	}
}
