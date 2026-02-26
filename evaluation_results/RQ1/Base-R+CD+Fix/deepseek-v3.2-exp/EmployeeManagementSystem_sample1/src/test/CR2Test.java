import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    
    private Company company;
    private Department deliveryDepartment;
    private Department otherDepartment;
    
    @Before
    public void setUp() {
        company = new Company();
        deliveryDepartment = new Department();
        deliveryDepartment.setType(DepartmentType.DELIVERY);
        otherDepartment = new Department();
        otherDepartment.setType(DepartmentType.PRODUCTION);
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Input: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        
        deliveryDepartment.addEmployee(worker);
        company.addDepartment(deliveryDepartment);
        
        // Expected Output: The average working hours per week is 40 hours
        double result = company.findAverageWorkingHoursForDeliveryDepartment();
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() {
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(35);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(35);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(35);
        
        deliveryDepartment.addEmployee(worker1);
        deliveryDepartment.addEmployee(worker2);
        deliveryDepartment.addEmployee(worker3);
        company.addDepartment(deliveryDepartment);
        
        // Expected Output: The average working hours per week is 35 hours
        double result = company.findAverageWorkingHoursForDeliveryDepartment();
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() {
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        
        deliveryDepartment.addEmployee(worker1);
        deliveryDepartment.addEmployee(worker2);
        company.addDepartment(deliveryDepartment);
        
        // Expected Output: The average working hours per week is 25 hours
        double result = company.findAverageWorkingHoursForDeliveryDepartment();
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentNoWorkers() {
        // Input: There are zero workers in the Delivery department
        // Add some non-worker employees to delivery department
        Manager manager = new Manager();
        manager.setSalary(5000);
        deliveryDepartment.addEmployee(manager);
        
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(3000);
        deliveryDepartment.addEmployee(salesPerson);
        
        company.addDepartment(deliveryDepartment);
        
        // Expected Output: The average working hours per week is 0 hours
        double result = company.findAverageWorkingHoursForDeliveryDepartment();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Input: There are 5 employees in total across all departments. 
        // Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively
        
        // Create delivery department workers
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        
        deliveryDepartment.addEmployee(worker1);
        deliveryDepartment.addEmployee(worker2);
        deliveryDepartment.addEmployee(worker3);
        
        // Create other department (non-delivery) with mixed employees
        OffShiftWorker otherWorker = new OffShiftWorker();
        otherWorker.setWeeklyWorkingHour(40);
        otherDepartment.addEmployee(otherWorker);
        
        Manager otherManager = new Manager();
        otherManager.setSalary(6000);
        otherDepartment.addEmployee(otherManager);
        
        company.addDepartment(deliveryDepartment);
        company.addDepartment(otherDepartment);
        
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33
        double result = company.findAverageWorkingHoursForDeliveryDepartment();
        assertEquals(28.33, result, 0.01);
    }
}