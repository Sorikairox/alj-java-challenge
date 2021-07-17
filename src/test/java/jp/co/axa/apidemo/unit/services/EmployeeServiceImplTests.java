package jp.co.axa.apidemo.unit.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.errors.UnknownEmployee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.services.EmployeeService;
import jp.co.axa.apidemo.services.EmployeeServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class EmployeeServiceImplTests {

	private EmployeeRepository employeeRepository;

	private EmployeeService service;

	@Before
	public void setup() {
		EmployeeRepository mock = mock(EmployeeRepository.class);
		 employeeRepository = spy(mock);
		service = new EmployeeServiceImpl(employeeRepository);
	}

	@Test
	public void retrieveEmployeesReturnsRepositoryFindAll() {
		List<Employee> arrayList = new ArrayList<>();
		Employee employee = new Employee();
		employee.setName("coolName");
		arrayList.add(employee);
		when(employeeRepository.findAll()).thenReturn(arrayList);
		Assert.assertEquals(arrayList, service.getEmployees());
	}

	@Test
	public void getEmployeeByIdReturnsRepositoryFindById() {
		Employee employee = new Employee();
		employee.setName("coolName");
		employee.setId(8978L);
		when(employeeRepository.findById(anyLong())).thenReturn(java.util.Optional.of(employee));
		Assert.assertEquals(employee, service.getEmployeeById(8978L));
	}

	@Test
	public void saveEmployeeReturnsRepositorySave() {
		Employee employee = new Employee();
		employee.setName("coolName");
		employee.setId(8978L);
		when(employeeRepository.save(any())).thenReturn(employee);
		Assert.assertEquals(employee, service.saveEmployee(employee));
	}

	@Test
	public void deleteEmployeeCallsRepositoryDeleteById() {
		Employee employee = new Employee();
		employee.setName("coolName");
		employee.setId(8978L);
		service.deleteEmployeeById(employee.getId());
		verify(employeeRepository, atLeastOnce()).deleteById(employee.getId());
	}

	@Test
	public void updateEmployeeCallsRepositoryUpdateById() {
		Employee employee = new Employee();
		employee.setName("coolName");
		employee.setId(8978L);
		when(employeeRepository.findById(anyLong())).thenReturn(java.util.Optional.of(employee));
		service.updateEmployeeById(employee.getId(), employee);
		verify(employeeRepository, atLeastOnce()).save(employee);
	}

	@Test(expected = UnknownEmployee.class)
	public void updateEmployeeThrowUnknownWhenEmployeeDoesntExist() {
		Employee employee = new Employee();
		employee.setName("coolName");
		employee.setId(8978L);
		when(employeeRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(null));
		service.updateEmployeeById(employee.getId(), employee);
	}
}
