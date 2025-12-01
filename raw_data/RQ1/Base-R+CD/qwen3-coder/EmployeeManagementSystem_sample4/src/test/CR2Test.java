import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Department deliveryDepartment;
    private Department productionDepartment;
    private Company company;
    
    @Before
    public void setUp() {
        // Set up a delivery department for testing
        deliveryDepartment = new Department();
        deliveryDepartment.setType(DepartmentType.DELIVERY);
        
        // Set up a production department for testing mixed departments scenario
        productionDepartment = new Department();
        productionDepartment.setType(DepartmentType.PRODUCTION);
        
        // Set up company
        company = new Company();
    }
    
    @Test
    public void testCase1_SingleWorkerInDeliveryDepartment() {
        // Input: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        ShiftWorker worker = new ShiftWorker();
        worker.setWeeklyWorkingHour(40);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(worker);
        deliveryDepartment.setEmployees(employees);
        
        // Expected Output: The average working hours per week is 40 hours
        double expectedAverage = 40.0;
        double actualAverage = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        assertEquals("Average working hours should be 40 for single worker with 40 hours", 
                     expectedAverage, actualAverage, 0.01);
    }
    
    @Test
    public void testCase2_MultipleWorkersInDeliveryDepartmentWithSameHours() {
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(35);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(35);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setWeeklyWorkingHour(35);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(worker1);
        employees.add(worker2);
        employees.add(worker3);
        deliveryDepartment.setEmployees(employees);
        
        // Expected Output: The average working hours per week is 35 hours
        double expectedAverage = 35.0;
        double actualAverage = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        assertEquals("Average working hours should be 35 for three workers each with 35 hours", 
                     expectedAverage, actualAverage, 0.01);
    }
    
    @Test
    public void testCase3_MultipleWorkersInDeliveryDepartmentWithDifferentHours() {
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(worker1);
        employees.add(worker2);
        deliveryDepartment.setEmployees(employees);
        
        // Expected Output: The average working hours per week is 25 hours
        double expectedAverage = 25.0;
        double actualAverage = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        assertEquals("Average working hours should be 25 for workers with 20 and 30 hours", 
                     expectedAverage, actualAverage, 0.01);
    }
    
    @Test
    public void testCase4_DeliveryDepartmentWithNoWorkers() {
        // Input: There are zero workers in the Delivery department
        List<Employee> employees = new ArrayList<>();
        deliveryDepartment.setEmployees(employees);
        
        // Expected Output: The average working hours per week is 0 hours (as there are no workers)
        double expectedAverage = 0.0;
        double actualAverage = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        assertEquals("Average working hours should be 0 when there are no workers", 
                     expectedAverage, actualAverage, 0.01);
    }
    
    @Test
    public void testCase5_DeliveryDepartmentMixedWithOtherDepartments() {
        // Input: There are 5 employees in total across all departments. Among them, 3 are in the Delivery department 
        // with weeklyWorkingHours of 25, 32, and 28 hours respectively
        
        // Create workers for delivery department
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        
        // Add workers to delivery department
        List<Employee> deliveryEmployees = new ArrayList<>();
        deliveryEmployees.add(worker1);
        deliveryEmployees.add(worker2);
        deliveryEmployees.add(worker3);
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 hours
        double expectedAverage = 28.33;
        double actualAverage = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        assertEquals("Average working hours should be 28.33 for workers with 25, 32, and 28 hours", 
                     expectedAverage, actualAverage, 0.01);
    }
}