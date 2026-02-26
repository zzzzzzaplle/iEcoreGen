import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR2Test {
    private Company company;
    private Department deliveryDepartment;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        company = new Company();
        deliveryDepartment = new Department();
        deliveryDepartment.setType(DepartmentType.DELIVERY);
        company.addDepartment(deliveryDepartment);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() throws Exception {
        // Test Case 1: "Single worker in delivery department"
        // Input: "One worker in the Delivery department with a weeklyWorkingHour of 40 hours."
        // Expected Output: "The average working hours per week is 40 hours."
        
        // Create a shift worker with 40 hours
        ShiftWorker worker = new ShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(15.0);
        worker.setHolidayPremium(50.0);
        worker.setName("John Doe");
        worker.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        worker.setSocialInsuranceNumber("123-45-6789");
        worker.setDepartment(DepartmentType.DELIVERY);
        
        // Add worker to delivery department and company
        deliveryDepartment.addEmployee(worker);
        company.addEmployee(worker);
        
        // Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersInDeliveryDepartmentWithSameHours() throws Exception {
        // Test Case 2: "Multiple workers in delivery department with same hours"
        // Input: "Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours."
        // Expected Output: "The average working hours per week is 35 hours."
        
        // Create three workers with 35 hours each
        for (int i = 0; i < 3; i++) {
            OffShiftWorker worker = new OffShiftWorker();
            worker.setWeeklyWorkingHour(35);
            worker.setHourlyRates(12.0);
            worker.setName("Worker " + (i + 1));
            worker.setBirthDate(dateFormat.parse("1985-05-15 00:00:00"));
            worker.setSocialInsuranceNumber("987-65-432" + i);
            worker.setDepartment(DepartmentType.DELIVERY);
            
            // Add workers to delivery department and company
            deliveryDepartment.addEmployee(worker);
            company.addEmployee(worker);
        }
        
        // Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersInDeliveryDepartmentWithDifferentHours() throws Exception {
        // Test Case 3: "Multiple workers in delivery department with different hours"
        // Input: "Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours."
        // Expected Output: "The average working hours per week is 25 hours."
        
        // Create first worker with 20 hours
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        worker1.setHourlyRates(18.0);
        worker1.setHolidayPremium(25.0);
        worker1.setName("Alice Smith");
        worker1.setBirthDate(dateFormat.parse("1992-03-10 00:00:00"));
        worker1.setSocialInsuranceNumber("111-22-3333");
        worker1.setDepartment(DepartmentType.DELIVERY);
        
        // Create second worker with 30 hours
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        worker2.setHourlyRates(16.0);
        worker2.setName("Bob Johnson");
        worker2.setBirthDate(dateFormat.parse("1988-07-20 00:00:00"));
        worker2.setSocialInsuranceNumber("444-55-6666");
        worker2.setDepartment(DepartmentType.DELIVERY);
        
        // Add workers to delivery department and company
        deliveryDepartment.addEmployee(worker1);
        deliveryDepartment.addEmployee(worker2);
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        
        // Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result: (20 + 30) / 2 = 25
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Test Case 4: "Delivery department with no workers"
        // Input: "There are zero workers in the Delivery department."
        // Expected Output: "The average working hours per week is 0 hours (as there are no workers)."
        
        // Delivery department has no workers (only setup)
        
        // Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result should be 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() throws Exception {
        // Test Case 5: "Delivery department mixed with other departments"
        // Input: "There are 5 employees in total across all departments. Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively."
        // Expected Output: "The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours."
        
        // Create other departments
        Department productionDepartment = new Department();
        productionDepartment.setType(DepartmentType.PRODUCTION);
        company.addDepartment(productionDepartment);
        
        Department controlDepartment = new Department();
        controlDepartment.setType(DepartmentType.CONTROL);
        company.addDepartment(controlDepartment);
        
        // Create delivery department workers with different hours: 25, 32, 28
        int[] deliveryHours = {25, 32, 28};
        for (int i = 0; i < deliveryHours.length; i++) {
            ShiftWorker worker = new ShiftWorker();
            worker.setWeeklyWorkingHour(deliveryHours[i]);
            worker.setHourlyRates(14.0 + i);
            worker.setHolidayPremium(30.0 + i * 5);
            worker.setName("Delivery Worker " + (i + 1));
            worker.setBirthDate(dateFormat.parse("1990-01-0" + (i + 1) + " 00:00:00"));
            worker.setSocialInsuranceNumber("555-66-777" + i);
            worker.setDepartment(DepartmentType.DELIVERY);
            
            deliveryDepartment.addEmployee(worker);
            company.addEmployee(worker);
        }
        
        // Create employees for other departments (non-workers or workers in other departments)
        Manager productionManager = new Manager();
        productionManager.setName("Production Manager");
        productionManager.setDepartment(DepartmentType.PRODUCTION);
        productionDepartment.addEmployee(productionManager);
        company.addEmployee(productionManager);
        
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setName("Sales Person");
        salesPerson.setDepartment(DepartmentType.CONTROL);
        controlDepartment.addEmployee(salesPerson);
        company.addEmployee(salesPerson);
        
        // Calculate average working hours: (25 + 32 + 28) / 3 = 85 / 3 = 28.333...
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result with 0.01 precision for rounding
        assertEquals(28.33, result, 0.01);
    }
}