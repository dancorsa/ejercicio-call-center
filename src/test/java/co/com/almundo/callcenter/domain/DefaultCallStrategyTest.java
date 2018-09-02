package co.com.almundo.callcenter.domain;

import org.junit.Test;

import co.com.almundo.callcenter.enumerators.EmployeeState;
import co.com.almundo.callcenter.enumerators.EmployeeType;
import co.com.almundo.callcenter.model.Employee;
import co.com.almundo.callcenter.strategy.CallAttendStrategy;
import co.com.almundo.callcenter.strategy.DefaultCallAttendStrategy;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultCallStrategyTest {

	private CallAttendStrategy callAttendStrategy;

	public DefaultCallStrategyTest() {
		this.callAttendStrategy = new DefaultCallAttendStrategy();
	}

	private static Employee mockBusyEmployee(EmployeeType employeeType) {
		Employee employee = mock(Employee.class);
		when(employee.getEmployeeType()).thenReturn(employeeType);
		when(employee.getEmployeeState()).thenReturn(EmployeeState.BUSY);
		return employee;
	}

	@Test
	public void testAssignToNone() {
		Employee operator = mockBusyEmployee(EmployeeType.OPERATOR);
		Employee supervisor = mockBusyEmployee(EmployeeType.SUPERVISOR);
		Employee director = mockBusyEmployee(EmployeeType.DIRECTOR);
		List<Employee> employeeList = Arrays.asList(operator, supervisor, director);

		Employee employee = this.callAttendStrategy.findEmployee(employeeList);

		assertNull(employee);
	}

	@Test
	public void testAssignToOperator() {
		Employee operator = Employee.buildOperator();
		Employee supervisor = Employee.buildSupervisor();
		Employee director = Employee.buildDirector();
		List<Employee> employeeList = Arrays.asList(operator, supervisor, director);

		Employee employee = this.callAttendStrategy.findEmployee(employeeList);

		assertNotNull(employee);
		assertEquals(EmployeeType.OPERATOR, employee.getEmployeeType());
	}

	@Test
	public void testAssignToSupervisor() {
		Employee operator = mock(Employee.class);
		when(operator.getEmployeeState()).thenReturn(EmployeeState.BUSY);
		Employee supervisor = Employee.buildSupervisor();
		Employee director = Employee.buildDirector();
		List<Employee> employeeList = Arrays.asList(operator, supervisor, director);

		Employee employee = this.callAttendStrategy.findEmployee(employeeList);

		assertNotNull(employee);
		assertEquals(EmployeeType.SUPERVISOR, employee.getEmployeeType());
	}

	@Test
	public void testAssignToDirector() {
		Employee operator = mockBusyEmployee(EmployeeType.OPERATOR);
		Employee supervisor = mockBusyEmployee(EmployeeType.SUPERVISOR);
		Employee director = Employee.buildDirector();
		List<Employee> employeeList = Arrays.asList(operator, supervisor, director);

		Employee employee = this.callAttendStrategy.findEmployee(employeeList);

		assertNotNull(employee);
		assertEquals(EmployeeType.DIRECTOR, employee.getEmployeeType());
	}
}
