package co.com.almundo.callcenter.domain;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import co.com.almundo.callcenter.enumerators.EmployeeState;
import co.com.almundo.callcenter.model.Call;
import co.com.almundo.callcenter.model.Employee;

public class EmployeeTest {

    @Test
    public void testEmployeeAttendWhileAvailable() throws InterruptedException {
        Employee employee = Employee.buildOperator();
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(employee);
        employee.attend(Call.buildRandomCall(0, 1));

        executorService.awaitTermination(5, TimeUnit.SECONDS);
        assertEquals(1, employee.getAttendedCalls().size());
    }

    @Test
    public void testEmployeeStatesWhileAttend() throws InterruptedException {
        Employee employee = Employee.buildOperator();
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(employee);
        assertEquals(EmployeeState.AVAILABLE, employee.getEmployeeState());
        TimeUnit.SECONDS.sleep(1);
        employee.attend(Call.buildRandomCall(2, 3));
        employee.attend(Call.buildRandomCall(0, 1));
        TimeUnit.SECONDS.sleep(1);
        assertEquals(EmployeeState.BUSY, employee.getEmployeeState());

        executorService.awaitTermination(5, TimeUnit.SECONDS);
        assertEquals(2, employee.getAttendedCalls().size());
    }

}
