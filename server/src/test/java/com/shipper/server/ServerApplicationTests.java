package com.shipper.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shipper.server.dto.CustomerRequest;
import com.shipper.server.dto.ProductRequest;
import com.shipper.server.repository.CustomerRepository;
import com.shipper.server.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ServerApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry) {
		dymDynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(productRequestString))
				.andExpect(status().isCreated());
		Assertions.assertEquals(1, productRepository.findAll().size());
	}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("name")
				.description("description")
				.price(BigDecimal.valueOf(1200))
				.build();
	}

	@Test
	void shouldCreateCustomer() throws Exception {
		CustomerRequest customerRequest = getCustomerRequest();
		String customerRequestString = objectMapper.writeValueAsString(customerRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/customer/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(customerRequestString))
				.andExpect(status().isCreated());
		Assertions.assertEquals(1, customerRepository.findAll().size());
	}

	private CustomerRequest getCustomerRequest() {
		return CustomerRequest.builder()
				.name("name")
				.build();
	}

}
