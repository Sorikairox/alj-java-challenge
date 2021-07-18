package jp.co.axa.apidemo.e2e.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import jp.co.axa.apidemo.entities.Employee;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void stage_01_addEmployee() throws Exception {
		Employee employee = new Employee();
		employee.setName("niceName");
		employee.setDepartment("coolDepartment");

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(employee);

		mockMvc.perform(post("/api/v1/employees").contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("niceName")))
				.andExpect(jsonPath("$.department", is("coolDepartment")));
	}

	@Test
	public void stage_02_getEmployees() throws Exception {
		mockMvc.perform(get("/api/v1/employees"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", is("niceName")));
	}

	@Test
	public void stage_03_getOneEmployee() throws Exception {
		mockMvc.perform(get("/api/v1/employees/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("niceName")));
	}

	@Test
	public void stage_04_putEmployee() throws Exception {
		Employee employee = new Employee();
		employee.setName("updatedName");
		employee.setDepartment("coolDepartment");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(employee);
		mockMvc.perform(put("/api/v1/employees/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("updatedName")));
	}

	@Test
	public void stage_05_deleteEmployeeWhenEmployeeExist() throws Exception {
		mockMvc.perform(delete("/api/v1/employees/1"))
				.andExpect(status().isOk());
	}

	@Test
	public void stage_06_deleteEmployeeRespondBadRequestWhenEmployeeDoNotExist() throws Exception {
		mockMvc.perform(delete("/api/v1/employees/1"))
				.andExpect(status().isBadRequest());
	}

}
