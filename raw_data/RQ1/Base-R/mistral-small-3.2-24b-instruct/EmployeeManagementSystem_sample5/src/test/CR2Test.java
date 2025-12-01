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
        // Reset company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Create a single worker in delivery department with 40 hours
        Worker worker = new Worker();
        worker.setWeeklyWorkingHours(40);
        worker.setDepartment(Department.DELIVERY);
        company.addEmployee(worker);
        
        // Calculate average working hours
        double averageHours = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the average is 40 hours
        assertEquals(40.0, averageHours, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersInDeliveryDepartmentWithSameHours() {
        // Create three workers in delivery department, each with 35 hours
        for (int i = 0; i < 3; i++) {
            Worker worker = new Worker();
            worker.setWeeklyWorkingHours(35);
            worker.setDepartment(Department.DELIVERY);
            company.addEmployee(worker);
        }
        
        // Calculate average working hours
        double averageHours = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the average is 35 hours
        assertEquals(35.0, averageHours, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersInDeliveryDepartmentWithDifferentHours() {
        // Create two workers in delivery department with different hours
        Worker worker1 = new Worker();
        worker1.setWeeklyWorkingHours(20);
        worker1.setDepartment(Department.DELIVERY);
        company.addEmployee(worker1);
        
        Worker worker2 = new Worker();
        worker2.setWeeklyWorkingHours(30);
        worker2.setDepartment(Department.DELIVERY);
        company.addEmployee(worker2);
        
        // Calculate average working hours
        double averageHours = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the average is 25 hours (20 + 30) / 2 = 25
        assertEquals(25.0, averageHours, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Add employees that are NOT workers in delivery department
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment(Department.DELIVERY); // SalesPerson in delivery, but not a Worker
        company.addEmployee(salesPerson);
        
        Manager manager = new Manager();
        manager.setDepartment(Department.DELIVERY); // Manager in delivery, but not a Worker
        company.addEmployee(manager);
        
        // Calculate average working hours
        double averageHours = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the average is 0 since there are no workers in delivery
        assertEquals(0.0, averageHours, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Add 5 employees total: 3 in delivery department, 2 in other departments
        
        // Delivery department workers
        Worker worker1 = new Worker();
        worker1.setWeeklyWorkingHours(25);
        worker1.setDepartment(Department.DELIVERY);
        company.addEmployee(worker1);
        
        Worker worker2 = new Worker();
        worker2.setWeeklyWorkingHours(32);
        worker2.setDepartment(Department.DELIVERY);
        company.addEmployee(worker2);
        
        Worker worker3 = new Worker();
        worker3.setWeeklyWorkingHours(28);
        worker3.setDepartment(Department.DELIVERY);
        company.addEmployee(worker3);
        
        // Other department employees (should not be counted)
        Worker productionWorker = new Worker();
        productionWorker.setWeeklyWorkingHours(40);
        productionWorker.setDepartment(Department.PRODUCTION);
        company.addEmployee(productionWorker);
        
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment(Department.CONTROL);
        company.addEmployee(salesPerson);
        
        // Calculate average working hours
        double averageHours = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the average is 28.33 (25 + 32 + 28) / 3 = 85/3 = 28.333...
        assertEquals(28.33, averageHours, 0.01); // Using delta of 0.01 for rounding to 2 decimal places
    }
}