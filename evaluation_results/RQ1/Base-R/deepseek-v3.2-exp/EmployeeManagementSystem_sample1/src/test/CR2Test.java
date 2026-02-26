import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR2Test {
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Test Case 1: Single worker in delivery department
        // Input: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        Worker worker = new Worker();
        worker.setDepartment("Delivery");
        worker.setWeeklyWorkingHours(40.0);
        company.addEmployee(worker);
        
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersInDeliveryDepartmentSameHours() {
        // Test Case 2: Multiple workers in delivery department with same hours
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        Worker worker1 = new Worker();
        worker1.setDepartment("Delivery");
        worker1.setWeeklyWorkingHours(35.0);
        company.addEmployee(worker1);
        
        Worker worker2 = new Worker();
        worker2.setDepartment("Delivery");
        worker2.setWeeklyWorkingHours(35.0);
        company.addEmployee(worker2);
        
        Worker worker3 = new Worker();
        worker3.setDepartment("Delivery");
        worker3.setWeeklyWorkingHours(35.0);
        company.addEmployee(worker3);
        
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersInDeliveryDepartmentDifferentHours() {
        // Test Case 3: Multiple workers in delivery department with different hours
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        Worker worker1 = new Worker();
        worker1.setDepartment("Delivery");
        worker1.setWeeklyWorkingHours(20.0);
        company.addEmployee(worker1);
        
        Worker worker2 = new Worker();
        worker2.setDepartment("Delivery");
        worker2.setWeeklyWorkingHours(30.0);
        company.addEmployee(worker2);
        
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Test Case 4: Delivery department with no workers
        // Input: There are zero workers in the Delivery department
        // Add employees from other departments to ensure they are ignored
        Manager manager = new Manager();
        manager.setDepartment("HR");
        manager.setFixedSalary(50000.0);
        company.addEmployee(manager);
        
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment("Sales");
        salesPerson.setFixedSalary(30000.0);
        salesPerson.setAmountOfSales(100000.0);
        salesPerson.setCommissionPercentage(0.1);
        company.addEmployee(salesPerson);
        
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Test Case 5: Delivery department mixed with other departments
        // Input: There are 5 employees in total across all departments. 
        // Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively
        
        // Add delivery department workers
        Worker worker1 = new Worker();
        worker1.setDepartment("Delivery");
        worker1.setWeeklyWorkingHours(25.0);
        company.addEmployee(worker1);
        
        Worker worker2 = new Worker();
        worker2.setDepartment("Delivery");
        worker2.setWeeklyWorkingHours(32.0);
        company.addEmployee(worker2);
        
        Worker worker3 = new Worker();
        worker3.setDepartment("Delivery");
        worker3.setWeeklyWorkingHours(28.0);
        company.addEmployee(worker3);
        
        // Add employees from other departments (should be ignored)
        Manager manager = new Manager();
        manager.setDepartment("HR");
        manager.setFixedSalary(50000.0);
        company.addEmployee(manager);
        
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment("Sales");
        salesPerson.setFixedSalary(30000.0);
        salesPerson.setAmountOfSales(100000.0);
        salesPerson.setCommissionPercentage(0.1);
        company.addEmployee(salesPerson);
        
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(28.33, result, 0.01); // Allow small delta for floating point precision
    }
}