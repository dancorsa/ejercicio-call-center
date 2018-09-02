package co.com.almundo.callcenter.domain;

import org.junit.Test;

import co.com.almundo.callcenter.domain.Dispatcher;
import co.com.almundo.callcenter.model.Call;
import co.com.almundo.callcenter.model.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DispatcherTest {
	private static final int MAX_CALL_TIME = 10;
    
	private static final int MIN_CALL_TIME = 5;
	
	private static final int CALL_AMOUNT = 10;

    

    

    @Test(expected = NullPointerException.class)
    public void testDispatcherExecuteWithNullEmployees() {
        new Dispatcher(null);
    }

    @Test(expected = NullPointerException.class)
    public void testDispatcherExecuteWithNullStrategy() {
        new Dispatcher(new ArrayList<>(), null);
    }

    @Test
    public void testDispatchCallsToEmployees() throws InterruptedException {
        List<Employee> employeeList = buildEmployeeList();
        Dispatcher dispatcher = new Dispatcher(employeeList);
        dispatcher.start();
        TimeUnit.SECONDS.sleep(1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(dispatcher);
        TimeUnit.SECONDS.sleep(1);

        loadCallList().stream().forEach(call -> {
            dispatcher.dispatch(call);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                fail();
            }
        });

        executorService.awaitTermination(MAX_CALL_TIME * 2, TimeUnit.SECONDS);
        assertEquals(CALL_AMOUNT, employeeList.stream().mapToInt(employee -> employee.getAttendedCalls().size()).sum());
    }

    private static List<Employee> buildEmployeeList() {
        Employee operator1 = Employee.buildOperator();
        Employee operator2 = Employee.buildOperator();
        Employee operator3 = Employee.buildOperator();
        Employee operator4 = Employee.buildOperator();
        Employee operator5 = Employee.buildOperator();
        Employee supervisor1 = Employee.buildSupervisor();
        Employee supervisor2 = Employee.buildSupervisor();
        Employee supervisor3 = Employee.buildSupervisor();
        Employee supervisor4 = Employee.buildSupervisor();
        Employee director = Employee.buildDirector();
        return Arrays.asList(operator1, operator2, operator3, operator4, operator5,
                supervisor1, supervisor2, supervisor3, supervisor4, director);
    }

    private static List<Call> loadCallList() {
        return Call.buildListOfRandomCalls(CALL_AMOUNT, MIN_CALL_TIME, MAX_CALL_TIME);
    }

}
