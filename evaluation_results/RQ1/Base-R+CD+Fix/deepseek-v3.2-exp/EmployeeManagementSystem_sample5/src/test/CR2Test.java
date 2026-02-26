import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
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
        // Setup: Create delivery department with one worker having 40 weekly hours
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(15.0); // Hourly rate doesn't affect the test
        deliveryDept.addEmployee(worker);
        
        company.addDepartment(deliveryDept);
        
        // Execute: Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: Average should be exactly 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHoursInDeliveryDepartment() {
        // Setup: Create delivery department with three workers, each with 35 weekly hours
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        // Add three workers with same hours
        for (int i = 0; i < 3; i++) {
            OffShiftWorker worker = new OffShiftWorker();
            worker.setWeeklyWorkingHour(35);
            worker.setHourlyRates(15.0);
            deliveryDept.addEmployee(worker);
        }
        
        company.addDepartment(deliveryDept);
        
        // Execute: Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: Average should be exactly 35 hours (105/3 = 35)
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHoursInDeliveryDepartment() {
        // Setup: Create delivery department with two workers having different hours (20 and 30)
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        worker1.setHourlyRates(15.0);
        deliveryDept.addEmployee(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        worker2.setHourlyRates(15.0);
        deliveryDept.addEmployee(worker2);
        
        company.addDepartment(deliveryDept);
        
        // Execute: Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: Average should be 25 hours ((20+30)/2 = 25)
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Setup: Create delivery department but add no workers
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        // Add a non-worker employee to verify only workers are counted
        Manager manager = new Manager();
        manager.setSalary(50000);
        deliveryDept.addEmployee(manager);
        
        company.addDepartment(deliveryDept);
        
        // Execute: Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: Should return 0 when no workers in delivery department
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Setup: Create multiple departments with mixed employees
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        // Add 3 workers to delivery department with different hours
        int[] deliveryHours = {25, 32, 28};
        for (int hours : deliveryHours) {
            OffShiftWorker worker = new OffShiftWorker();
            worker.setWeeklyWorkingHour(hours);
            worker.setHourlyRates(15.0);
            deliveryDept.addEmployee(worker);
        }
        
        // Add other departments with employees that should not be counted
        Department productionDept = new Department();
        productionDept.setType(DepartmentType.PRODUCTION);
        
        OffShiftWorker productionWorker = new OffShiftWorker();
        productionWorker.setWeeklyWorkingHour(40); // This should not be included
        productionWorker.setHourlyRates(15.0);
        productionDept.addEmployee(productionWorker);
        
        Department controlDept = new Department();
        controlDept.setType(DepartmentType.CONTROL);
        
        Manager controlManager = new Manager(); // Non-worker, should not be included
        controlManager.setSalary(60000);
        controlDept.addEmployee(controlManager);
        
        // Add all departments to company
        company.addDepartment(deliveryDept);
        company.addDepartment(productionDept);
        company.addDepartment(controlDept);
        
        // Execute: Calculate average working hours for delivery department only
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: Average should be 28.33 hours ((25+32+28)/3 = 28.333...)
        assertEquals(28.33, result, 0.01); // Allowing 0.01 tolerance for floating point
    }
}