import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR2Test {
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Setup: Create one worker in Delivery department with 40 hours
        Worker worker = new OffShiftWorker();
        worker.setDepartment(Department.DELIVERY);
        worker.setWeeklyWorkingHours(40.0);
        company.addEmployee(worker);
        
        // Execute: Calculate average working hours
        double result = company.averageWorkingHoursForDeliveryWorkers();
        
        // Verify: Average should be 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersInDeliveryDepartmentWithSameHours() {
        // Setup: Create three workers in Delivery department, each with 35 hours
        for (int i = 0; i < 3; i++) {
            Worker worker = new OffShiftWorker();
            worker.setDepartment(Department.DELIVERY);
            worker.setWeeklyWorkingHours(35.0);
            company.addEmployee(worker);
        }
        
        // Execute: Calculate average working hours
        double result = company.averageWorkingHoursForDeliveryWorkers();
        
        // Verify: Average should be 35 hours
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersInDeliveryDepartmentWithDifferentHours() {
        // Setup: Create two workers in Delivery department with different hours
        Worker worker1 = new OffShiftWorker();
        worker1.setDepartment(Department.DELIVERY);
        worker1.setWeeklyWorkingHours(20.0);
        company.addEmployee(worker1);
        
        Worker worker2 = new OffShiftWorker();
        worker2.setDepartment(Department.DELIVERY);
        worker2.setWeeklyWorkingHours(30.0);
        company.addEmployee(worker2);
        
        // Execute: Calculate average working hours
        double result = company.averageWorkingHoursForDeliveryWorkers();
        
        // Verify: Average should be 25 hours (20 + 30) / 2
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Setup: Company has no employees in Delivery department
        // (company is empty after setup)
        
        // Execute: Calculate average working hours
        double result = company.averageWorkingHoursForDeliveryWorkers();
        
        // Verify: Should return 0 when no workers in delivery department
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Setup: Create 5 employees across all departments
        // 3 in Delivery department and 2 in other departments
        
        // Delivery department workers
        Worker deliveryWorker1 = new OffShiftWorker();
        deliveryWorker1.setDepartment(Department.DELIVERY);
        deliveryWorker1.setWeeklyWorkingHours(25.0);
        company.addEmployee(deliveryWorker1);
        
        Worker deliveryWorker2 = new OffShiftWorker();
        deliveryWorker2.setDepartment(Department.DELIVERY);
        deliveryWorker2.setWeeklyWorkingHours(32.0);
        company.addEmployee(deliveryWorker2);
        
        Worker deliveryWorker3 = new OffShiftWorker();
        deliveryWorker3.setDepartment(Department.DELIVERY);
        deliveryWorker3.setWeeklyWorkingHours(28.0);
        company.addEmployee(deliveryWorker3);
        
        // Other department workers (should not be included in calculation)
        Worker productionWorker = new OffShiftWorker();
        productionWorker.setDepartment(Department.PRODUCTION);
        productionWorker.setWeeklyWorkingHours(40.0);
        company.addEmployee(productionWorker);
        
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment(Department.CONTROL);
        company.addEmployee(salesPerson);
        
        // Execute: Calculate average working hours
        double result = company.averageWorkingHoursForDeliveryWorkers();
        
        // Verify: Average should be 28.33 (25 + 32 + 28) / 3 = 28.333...
        assertEquals(28.33, result, 0.01); // Using delta of 0.01 for rounding to two decimals
    }
}