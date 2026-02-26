import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Setup: Create a delivery department with one worker having 40 weekly working hours
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(15.0); // Set hourly rate for base salary calculation
        
        deliveryDept.addEmployee(worker);
        company.addDepartment(deliveryDept);
        
        // Execute: Calculate average working hours for delivery department
        double result = company.findAverageDeliveryDepartmentWorkingHours();
        
        // Verify: Average should be 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHoursInDeliveryDepartment() {
        // Setup: Create a delivery department with three workers, each with 35 weekly working hours
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        // Add three workers with same working hours
        for (int i = 0; i < 3; i++) {
            OffShiftWorker worker = new OffShiftWorker();
            worker.setWeeklyWorkingHour(35);
            worker.setHourlyRates(15.0);
            deliveryDept.addEmployee(worker);
        }
        
        company.addDepartment(deliveryDept);
        
        // Execute: Calculate average working hours for delivery department
        double result = company.findAverageDeliveryDepartmentWorkingHours();
        
        // Verify: Average should be 35 hours
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHoursInDeliveryDepartment() {
        // Setup: Create a delivery department with two workers having different working hours
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        // First worker with 20 hours
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        worker1.setHourlyRates(15.0);
        deliveryDept.addEmployee(worker1);
        
        // Second worker with 30 hours
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        worker2.setHourlyRates(15.0);
        deliveryDept.addEmployee(worker2);
        
        company.addDepartment(deliveryDept);
        
        // Execute: Calculate average working hours for delivery department
        double result = company.findAverageDeliveryDepartmentWorkingHours();
        
        // Verify: Average should be 25 hours ((20 + 30) / 2 = 25)
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Setup: Create a delivery department with no workers
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        company.addDepartment(deliveryDept);
        
        // Execute: Calculate average working hours for delivery department
        double result = company.findAverageDeliveryDepartmentWorkingHours();
        
        // Verify: Average should be 0 hours (no workers in delivery department)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Setup: Create multiple departments including delivery department with mixed workers
        Department productionDept = new Department();
        productionDept.setType(DepartmentType.PRODUCTION);
        
        Department controlDept = new Department();
        controlDept.setType(DepartmentType.CONTROL);
        
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        // Add non-worker employees to other departments (should not affect calculation)
        Manager manager = new Manager();
        manager.setDepartment("Production");
        productionDept.addEmployee(manager);
        
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setDepartment("Control");
        controlDept.addEmployee(salesPerson);
        
        // Add workers to delivery department with different working hours
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        worker1.setHourlyRates(15.0);
        deliveryDept.addEmployee(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        worker2.setHourlyRates(15.0);
        deliveryDept.addEmployee(worker2);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        worker3.setHourlyRates(15.0);
        deliveryDept.addEmployee(worker3);
        
        // Add all departments to company
        company.addDepartment(productionDept);
        company.addDepartment(controlDept);
        company.addDepartment(deliveryDept);
        
        // Execute: Calculate average working hours for delivery department
        double result = company.findAverageDeliveryDepartmentWorkingHours();
        
        // Verify: Average should be 28.33 hours ((25 + 32 + 28) / 3 = 28.333...)
        assertEquals(28.33, result, 0.01); // Using delta of 0.01 for rounding to two decimal places
    }
}