import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Company company;
    private Department deliveryDepartment;
    
    @Before
    public void setUp() {
        company = new Company();
        deliveryDepartment = new Department();
        deliveryDepartment.setType(DepartmentType.DELIVERY);
        company.addDepartment(deliveryDepartment);
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Test Case 1: Single worker in delivery department
        // Input: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        // Expected Output: The average working hours per week is 40 hours
        
        // Create a shift worker with 40 hours per week
        ShiftWorker worker = new ShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(15.0);
        
        // Add worker to delivery department
        deliveryDepartment.addEmployee(worker);
        
        // Calculate average working hours
        double result = company.calculateAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() {
        // Test Case 2: Multiple workers in delivery department with same hours
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        // Expected Output: The average working hours per week is 35 hours
        
        // Create three workers with 35 hours per week each
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(35);
        worker1.setHourlyRates(15.0);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setWeeklyWorkingHour(35);
        worker2.setHourlyRates(16.0);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(35);
        worker3.setHourlyRates(17.0);
        
        // Add workers to delivery department
        deliveryDepartment.addEmployee(worker1);
        deliveryDepartment.addEmployee(worker2);
        deliveryDepartment.addEmployee(worker3);
        
        // Calculate average working hours
        double result = company.calculateAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() {
        // Test Case 3: Multiple workers in delivery department with different hours
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        // Expected Output: The average working hours per week is 25 hours
        
        // Create two workers with different hours
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        worker1.setHourlyRates(15.0);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        worker2.setHourlyRates(16.0);
        
        // Add workers to delivery department
        deliveryDepartment.addEmployee(worker1);
        deliveryDepartment.addEmployee(worker2);
        
        // Calculate average working hours
        double result = company.calculateAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Test Case 4: Delivery department with no workers
        // Input: There are zero workers in the Delivery department
        // Expected Output: The average working hours per week is 0 hours (as there are no workers)
        
        // Add non-worker employees to delivery department
        Manager manager = new Manager();
        manager.setSalary(5000);
        deliveryDepartment.addEmployee(manager);
        
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(3000);
        salesPerson.setAmountOfSales(10000);
        salesPerson.setCommissionPercentage(0.1);
        deliveryDepartment.addEmployee(salesPerson);
        
        // Calculate average working hours
        double result = company.calculateAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result is 0 since there are no workers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Test Case 5: Delivery department mixed with other departments
        // Input: There are 5 employees in total across all departments. Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours
        
        // Create delivery department workers
        ShiftWorker deliveryWorker1 = new ShiftWorker();
        deliveryWorker1.setWeeklyWorkingHour(25);
        deliveryWorker1.setHourlyRates(15.0);
        
        ShiftWorker deliveryWorker2 = new ShiftWorker();
        deliveryWorker2.setWeeklyWorkingHour(32);
        deliveryWorker2.setHourlyRates(16.0);
        
        OffShiftWorker deliveryWorker3 = new OffShiftWorker();
        deliveryWorker3.setWeeklyWorkingHour(28);
        deliveryWorker3.setHourlyRates(17.0);
        
        // Add workers to delivery department
        deliveryDepartment.addEmployee(deliveryWorker1);
        deliveryDepartment.addEmployee(deliveryWorker2);
        deliveryDepartment.addEmployee(deliveryWorker3);
        
        // Create other departments with workers
        Department productionDepartment = new Department();
        productionDepartment.setType(DepartmentType.PRODUCTION);
        
        OffShiftWorker productionWorker = new OffShiftWorker();
        productionWorker.setWeeklyWorkingHour(40);
        productionWorker.setHourlyRates(18.0);
        productionDepartment.addEmployee(productionWorker);
        
        Department controlDepartment = new Department();
        controlDepartment.setType(DepartmentType.CONTROL);
        
        ShiftWorker controlWorker = new ShiftWorker();
        controlWorker.setWeeklyWorkingHour(38);
        controlWorker.setHourlyRates(19.0);
        controlDepartment.addEmployee(controlWorker);
        
        // Add other departments to company
        company.addDepartment(productionDepartment);
        company.addDepartment(controlDepartment);
        
        // Calculate average working hours for delivery department only
        double result = company.calculateAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result (25 + 32 + 28) / 3 = 85 / 3 = 28.333...
        assertEquals(28.33, result, 0.01);
    }
}