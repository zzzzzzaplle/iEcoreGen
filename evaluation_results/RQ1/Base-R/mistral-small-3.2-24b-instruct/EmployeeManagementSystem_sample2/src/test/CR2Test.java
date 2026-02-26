import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        // Expected Output: The average working hours per week is 40 hours
        
        // Create a shift worker for delivery department
        ShiftWorker worker = new ShiftWorker();
        worker.setName("John Doe");
        worker.setDepartment("Delivery");
        worker.setWeeklyWorkingHours(40);
        worker.setHourlyRate(25.0);
        worker.setHolidayPremium(100.0);
        
        company.addEmployee(worker);
        
        // Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHoursInDeliveryDepartment() {
        // Test Case 2: Multiple workers in delivery department with same hours
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        // Expected Output: The average working hours per week is 35 hours
        
        // Create three workers for delivery department with same hours
        for (int i = 0; i < 3; i++) {
            ShiftWorker worker = new ShiftWorker();
            worker.setName("Worker " + (i + 1));
            worker.setDepartment("Delivery");
            worker.setWeeklyWorkingHours(35);
            worker.setHourlyRate(20.0);
            worker.setHolidayPremium(80.0);
            company.addEmployee(worker);
        }
        
        // Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHoursInDeliveryDepartment() {
        // Test Case 3: Multiple workers in delivery department with different hours
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        // Expected Output: The average working hours per week is 25 hours
        
        // Create first worker with 20 hours
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setName("Worker 1");
        worker1.setDepartment("Delivery");
        worker1.setWeeklyWorkingHours(20);
        worker1.setHourlyRate(18.0);
        worker1.setHolidayPremium(60.0);
        company.addEmployee(worker1);
        
        // Create second worker with 30 hours
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setName("Worker 2");
        worker2.setDepartment("Delivery");
        worker2.setWeeklyWorkingHours(30);
        worker2.setHourlyRate(22.0);
        worker2.setHolidayPremium(90.0);
        company.addEmployee(worker2);
        
        // Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result (average of 20 and 30 is 25)
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Test Case 4: Delivery department with no workers
        // Input: There are zero workers in the Delivery department
        // Expected Output: The average working hours per week is 0 hours (as there are no workers)
        
        // Add employees from other departments (non-delivery)
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setName("Sales Person");
        salesPerson.setDepartment("Sales");
        salesPerson.setFixedSalary(3000.0);
        salesPerson.setAmountOfSales(10000.0);
        salesPerson.setCommissionPercentage(0.1);
        company.addEmployee(salesPerson);
        
        Manager manager = new Manager();
        manager.setName("Manager");
        manager.setDepartment("Management");
        manager.setPosition("Senior Manager");
        company.addEmployee(manager);
        
        // Calculate average working hours - should return 0 since no delivery workers
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result is 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Test Case 5: Delivery department mixed with other departments
        // Input: There are 5 employees in total across all departments. Among them, 3 are in the Delivery department 
        // with weeklyWorkingHours of 25, 32, and 28 hours respectively
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours
        
        // Create 3 delivery workers with different hours
        ShiftWorker deliveryWorker1 = new ShiftWorker();
        deliveryWorker1.setName("Delivery Worker 1");
        deliveryWorker1.setDepartment("Delivery");
        deliveryWorker1.setWeeklyWorkingHours(25);
        deliveryWorker1.setHourlyRate(20.0);
        deliveryWorker1.setHolidayPremium(75.0);
        company.addEmployee(deliveryWorker1);
        
        ShiftWorker deliveryWorker2 = new ShiftWorker();
        deliveryWorker2.setName("Delivery Worker 2");
        deliveryWorker2.setDepartment("Delivery");
        deliveryWorker2.setWeeklyWorkingHours(32);
        deliveryWorker2.setHourlyRate(22.0);
        deliveryWorker2.setHolidayPremium(85.0);
        company.addEmployee(deliveryWorker2);
        
        ShiftWorker deliveryWorker3 = new ShiftWorker();
        deliveryWorker3.setName("Delivery Worker 3");
        deliveryWorker3.setDepartment("Delivery");
        deliveryWorker3.setWeeklyWorkingHours(28);
        deliveryWorker3.setHourlyRate(21.0);
        deliveryWorker3.setHolidayPremium(80.0);
        company.addEmployee(deliveryWorker3);
        
        // Add 2 employees from other departments
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setName("Sales Person");
        salesPerson.setDepartment("Sales");
        salesPerson.setFixedSalary(3500.0);
        salesPerson.setAmountOfSales(15000.0);
        salesPerson.setCommissionPercentage(0.08);
        company.addEmployee(salesPerson);
        
        Manager manager = new Manager();
        manager.setName("Department Manager");
        manager.setDepartment("Management");
        manager.setPosition("Manager");
        company.addEmployee(manager);
        
        // Calculate average working hours for delivery department
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result (average of 25, 32, and 28 is 28.33 when rounded to two decimal places)
        assertEquals(28.33, result, 0.01);
    }
}