import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR2Test {
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Setup: Create a delivery department with one worker having 40 hours
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        List<Employee> employees = new ArrayList<>();
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        employees.add(worker);
        
        deliveryDept.setEmployees(employees);
        
        // Execute: Calculate average working hours
        double result = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Verify: Expected average is 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHoursInDeliveryDepartment() {
        // Setup: Create a delivery department with three workers, each 35 hours
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            OffShiftWorker worker = new OffShiftWorker();
            worker.setWeeklyWorkingHour(35);
            employees.add(worker);
        }
        
        deliveryDept.setEmployees(employees);
        
        // Execute: Calculate average working hours
        double result = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Verify: Expected average is 35 hours
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHoursInDeliveryDepartment() {
        // Setup: Create a delivery department with two workers: 20 and 30 hours
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        List<Employee> employees = new ArrayList<>();
        
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        employees.add(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        employees.add(worker2);
        
        deliveryDept.setEmployees(employees);
        
        // Execute: Calculate average working hours (20 + 30) / 2 = 25
        double result = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Verify: Expected average is 25 hours
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Setup: Create a delivery department with no workers
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        deliveryDept.setEmployees(new ArrayList<>());
        
        // Execute: Calculate average working hours
        double result = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Verify: Expected average is 0 hours (no workers)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_mixedDepartmentsWithDeliveryWorkers() {
        // Setup: Create a delivery department with 3 workers: 25, 32, 28 hours
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        List<Employee> employees = new ArrayList<>();
        
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        employees.add(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        employees.add(worker2);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        employees.add(worker3);
        
        deliveryDept.setEmployees(employees);
        
        // Execute: Calculate average working hours (25 + 32 + 28) / 3 = 28.33
        double result = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Verify: Expected average is 28.33 hours (rounded to two decimal places)
        assertEquals(28.33, result, 0.01);
    }
}