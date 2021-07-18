package jp.co.axa.apidemo.unit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import jp.co.axa.apidemo.controllers.EmployeeController;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.errors.UnknownEmployee;
import jp.co.axa.apidemo.services.EmployeeService;
import jp.co.axa.apidemo.services.HardCodedUserService;
import jp.co.axa.apidemo.services.JwtService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class, secure = false)
@AutoConfigureMockMvc(addFilters = false)
public class EmployeeControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	JwtService jwtService;

	@MockBean
	HardCodedUserService hardCodedUserService;

	@MockBean
	private EmployeeService employeeService;

	@Test
	public void getEmployeesReturnsListFromService() throws Exception {
		List<Employee> arrayList = new ArrayList<>();
		Employee employee = new Employee();
		employee.setName("coolName");
		arrayList.add(employee);
		when(employeeService.getEmployees()).thenReturn(arrayList);
		this.mockMvc.perform(get("/api/v1/employees")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", is("coolName")));
	}

	@Test
	public void getEmployeeWithIdReturnsEmployeeFromService() throws Exception {
		Employee employee = new Employee();
		employee.setName("lookedForEmployeeName");
		employee.setId(987987L);
		when(employeeService.getEmployeeById(employee.getId())).thenReturn(employee);
		this.mockMvc.perform(get("/api/v1/employees/" + employee.getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("lookedForEmployeeName")))
				.andExpect(jsonPath("$.id", is(987987)));
	}

	@Test
	public void saveEmployeeShouldReturnEmployeeFromService() throws Exception {
		Employee employee = new Employee();
		employee.setName("createdEmployee");
		employee.setId(987987L);
		when(employeeService.saveEmployee(ArgumentMatchers.any())).thenReturn(employee);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(employee);

		this.mockMvc.perform(post("/api/v1/employees").contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson)).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("createdEmployee")))
				.andExpect(jsonPath("$.id", is(987987)));
	}

	@Test
	public void deleteEmployeeRespondOk() throws Exception {
		Employee employee = new Employee();
		employee.setName("createdEmployee");
		employee.setId(987987L);
		this.mockMvc.perform(delete("/api/v1/employees/" + employee.getId())).andExpect(status().isOk());
	}

	@Test
	public void updateEmployeeReturnsEmployeeFromService() throws Exception {
		Employee employee = new Employee();
		employee.setName("updatedEmployee");
		employee.setId(987987L);
		when(employeeService.updateEmployeeById(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(employee);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(employee);

		this.mockMvc.perform(put("/api/v1/employees/" + employee.getId()).contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson)).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("updatedEmployee")))
				.andExpect(jsonPath("$.id", is(987987)));
	}

	@Test
	public void updateEmployeeThrowsBadRequestWhenEmployeeDoesntExist() throws Exception {
		Employee employee = new Employee();
		employee.setName("updatedEmployee");
		employee.setId(987987L);
		when(employeeService.updateEmployeeById(ArgumentMatchers.any(), ArgumentMatchers.any())).thenThrow(new UnknownEmployee());

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(employee);

		this.mockMvc.perform(put("/api/v1/employees/" + employee.getId()).contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson)).andExpect(status().isBadRequest());
	}
}
