import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    private Company company;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        company = new Company();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() throws Exception {
        // Test Case 1: Single worker in delivery department
        // Input: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        // Expected Output: The average working hours per week is 40 hours
        
        // Create delivery department
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        // Create shift worker with 40 hours
        ShiftWorker worker = new ShiftWorker();
        worker.setName("Worker1");
        worker.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        worker.setSocialInsuranceNumber("123456789");
        worker.setDepartment("Delivery");
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(20.0);
        worker.setHolidayPremium(100.0);
        
        // Add worker to department
        deliveryDept.addEmployee(worker);
        
        // Add department to company
        company.addDepartment(deliveryDept);
        
        // Calculate average working hours
        double averageHours = company.findAverageDeliveryDepartmentWorkerHours();
        
        // Verify result
        assertEquals(40.0, averageHours, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() throws Exception {
        // Test Case 2: Multiple workers in delivery department with same hours
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        // Expected Output: The average working hours per week is 35 hours
        
        // Create delivery department
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        // Create three workers with 35 hours each
        for (int i = 1; i <= 3; i++) {
            OffShiftWorker worker = new OffShiftWorker();
            worker.setName("Worker" + i);
            worker.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
            worker.setSocialInsuranceNumber("12345678" + i);
            worker.setDepartment("Delivery");
            worker.setWeeklyWorkingHour(35);
            worker.setHourlyRates(18.0);
            
            // Add worker to department
            deliveryDept.addEmployee(worker);
        }
        
        // Add department to company
        company.addDepartment(deliveryDept);
        
        // Calculate average working hours
        double averageHours = company.findAverageDeliveryDepartmentWorkerHours();
        
        // Verify result
        assertEquals(35.0, averageHours, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() throws Exception {
        // Test Case 3: Multiple workers in delivery department with different hours
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        // Expected Output: The average working hours per week is 25 hours
        
        // Create delivery department
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        // Create first worker with 20 hours
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setName("Worker1");
        worker1.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        worker1.setSocialInsuranceNumber("123456781");
        worker1.setDepartment("Delivery");
        worker1.setWeeklyWorkingHour(20);
        worker1.setHourlyRates(15.0);
        worker1.setHolidayPremium(50.0);
        
        // Create second worker with 30 hours
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setName("Worker2");
        worker2.setBirthDate(dateFormat.parse("1991-01-01 00:00:00"));
        worker2.setSocialInsuranceNumber("123456782");
        worker2.setDepartment("Delivery");
        worker2.setWeeklyWorkingHour(30);
        worker2.setHourlyRates(16.0);
        
        // Add workers to department
        deliveryDept.addEmployee(worker1);
        deliveryDept.addEmployee(worker2);
        
        // Add department to company
        company.addDepartment(deliveryDept);
        
        // Calculate average working hours
        double averageHours = company.findAverageDeliveryDepartmentWorkerHours();
        
        // Verify result (20 + 30) / 2 = 25
        assertEquals(25.0, averageHours, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentNoWorkers() throws Exception {
        // Test Case 4: Delivery department with no workers
        // Input: There are zero workers in the Delivery department
        // Expected Output: The average working hours per week is 0 hours (as there are no workers)
        
        // Create delivery department
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        // Add only a manager (not a worker) to the department
        Manager manager = new Manager();
        manager.setName("Manager1");
        manager.setBirthDate(dateFormat.parse("1980-01-01 00:00:00"));
        manager.setSocialInsuranceNumber("123456780");
        manager.setDepartment("Delivery");
        manager.setSalary(50000.0);
        manager.setPosition("Delivery Manager");
        
        deliveryDept.addEmployee(manager);
        
        // Add department to company
        company.addDepartment(deliveryDept);
        
        // Calculate average working hours
        double averageHours = company.findAverageDeliveryDepartmentWorkerHours();
        
        // Verify result should be 0 when no workers
        assertEquals(0.0, averageHours, 0.001);
    }
    
    @Test
    public void testCase5_mixedDepartments() throws Exception {
        // Test Case 5: Delivery department mixed with other departments
        // Input: There are 5 employees in total across all departments. Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours
        
        // Create delivery department
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        // Create three workers for delivery department with different hours
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setName("DeliveryWorker1");
        worker1.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        worker1.setSocialInsuranceNumber("123456781");
        worker1.setDepartment("Delivery");
        worker1.setWeeklyWorkingHour(25);
        worker1.setHourlyRates(17.0);
        worker1.setHolidayPremium(75.0);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setName("DeliveryWorker2");
        worker2.setBirthDate(dateFormat.parse("1991-01-01 00:00:00"));
        worker2.setSocialInsuranceNumber("123456782");
        worker2.setDepartment("Delivery");
        worker2.setWeeklyWorkingHour(32);
        worker2.setHourlyRates(18.0);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setName("DeliveryWorker3");
        worker3.setBirthDate(dateFormat.parse("1992-01-01 00:00:00"));
        worker3.setSocialInsuranceNumber("123456783");
        worker3.setDepartment("Delivery");
        worker3.setWeeklyWorkingHour(28);
        worker3.setHourlyRates(19.0);
        worker3.setHolidayPremium(80.0);
        
        // Add workers to delivery department
        deliveryDept.addEmployee(worker1);
        deliveryDept.addEmployee(worker2);
        deliveryDept.addEmployee(worker3);
        
        // Create production department with workers
        Department productionDept = new Department();
        productionDept.setType(DepartmentType.PRODUCTION);
        
        OffShiftWorker prodWorker1 = new OffShiftWorker();
        prodWorker1.setName("ProductionWorker1");
        prodWorker1.setBirthDate(dateFormat.parse("1993-01-01 00:00:00"));
        prodWorker1.setSocialInsuranceNumber("123456784");
        prodWorker1.setDepartment("Production");
        prodWorker1.setWeeklyWorkingHour(40);
        prodWorker1.setHourlyRates(20.0);
        
        OffShiftWorker prodWorker2 = new OffShiftWorker();
        prodWorker2.setName("ProductionWorker2");
        prodWorker2.setBirthDate(dateFormat.parse("1994-01-01 00:00:00"));
        prodWorker2.setSocialInsuranceNumber("123456785");
        prodWorker2.setDepartment("Production");
        prodWorker2.setWeeklyWorkingHour(38);
        prodWorker2.setHourlyRates(21.0);
        
        // Add workers to production department
        productionDept.addEmployee(prodWorker1);
        productionDept.addEmployee(prodWorker2);
        
        // Add departments to company
        company.addDepartment(deliveryDept);
        company.addDepartment(productionDept);
        
        // Calculate average working hours for delivery department
        double averageHours = company.findAverageDeliveryDepartmentWorkerHours();
        
        // Verify result: (25 + 32 + 28) / 3 = 28.333...
        assertEquals(28.33, averageHours, 0.01);
    }
}