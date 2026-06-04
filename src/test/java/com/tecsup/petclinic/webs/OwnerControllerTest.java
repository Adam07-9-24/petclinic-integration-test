package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.util.TObjectCreator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("h2")
@Slf4j
public class OwnerControllerTest {

	private static final ObjectMapper om = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testUpdateOwner() throws Exception {
		OwnerDTO newOwnerTO = TObjectCreator.newOwner();

		ResultActions mvcActions = mockMvc.perform(post("/owners")
						.content(om.writeValueAsString(newOwnerTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());

		String response = mvcActions.andReturn().getResponse().getContentAsString();
		Long id = JsonPath.parse(response).read("$.id", Long.class);

		OwnerDTO updateOwnerTO = OwnerDTO.builder()
				.id(id)
				.firstName("Alice")
				.lastName("Updated")
				.address("200 Updated Ave.")
				.city("Monona")
				.telephone("6085559999")
				.build();

		mockMvc.perform(put("/owners/" + id)
						.content(om.writeValueAsString(updateOwnerTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(id.intValue())))
				.andExpect(jsonPath("$.lastName", is(updateOwnerTO.getLastName())))
				.andExpect(jsonPath("$.city", is(updateOwnerTO.getCity())));

		mockMvc.perform(get("/owners/" + id))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(id.intValue())))
				.andExpect(jsonPath("$.firstName", is(updateOwnerTO.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(updateOwnerTO.getLastName())))
				.andExpect(jsonPath("$.address", is(updateOwnerTO.getAddress())))
				.andExpect(jsonPath("$.city", is(updateOwnerTO.getCity())))
				.andExpect(jsonPath("$.telephone", is(updateOwnerTO.getTelephone())));
	}

	@Test
	public void testDeleteOwner() throws Exception {
		OwnerDTO newOwnerTO = TObjectCreator.newOwner();

		ResultActions mvcActions = mockMvc.perform(post("/owners")
						.content(om.writeValueAsString(newOwnerTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());

		String response = mvcActions.andReturn().getResponse().getContentAsString();
		Long id = JsonPath.parse(response).read("$.id", Long.class);

		mockMvc.perform(delete("/owners/" + id))
				.andDo(print())
				.andExpect(status().isOk());

		mockMvc.perform(get("/owners/" + id))
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	public void testDeleteOwnerKO() throws Exception {
		mockMvc.perform(delete("/owners/1000"))
				.andDo(print())
				.andExpect(status().isNotFound());
	}
}
