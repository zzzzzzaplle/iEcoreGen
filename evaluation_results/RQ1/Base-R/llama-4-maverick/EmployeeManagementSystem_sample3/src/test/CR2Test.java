import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
        ShiftWorker worker = new ShiftWorker();
        worker.setDepartment("delivery");
        worker.setWeeklyWorkingHours(40.0);
        company.addEmployee(worker);
        
        double result = company.calculateAverageWorkingHoursInDeliveryDepartment();
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersInDeliveryDepartmentSameHours() {
        // Test Case 2: Multiple workers in delivery department with same hours
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        for (int i = 0; i < 3; i++) {
            ShiftWorker worker = new ShiftWorker();
            worker.setDepartment("delivery");
            worker.setWeeklyWorkingHours(35.0);
            company.addEmployee(worker);
        }
        
        double result = company.calculateAverageWorkingHoursInDeliveryDepartment();
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersInDeliveryDepartmentDifferentHours() {
        // Test Case 3: Multiple workers in delivery department with different hours
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setDepartment("delivery");
        worker1.setWeeklyWorkingHours(20.0);
        company.addEmployee(worker1);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setDepartment("delivery");
        worker2.setWeeklyWorkingHours(30.0);
        company.addEmployee(worker2);
        
        double result = company.calculateAverageWorkingHoursInDeliveryDepartment();
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Test Case 4: Delivery department with no workers
        // Input: There are zero workers in the Delivery department
        // No workers added to company
        
        double result = company.calculateAverageWorkingHoursInDeliveryDepartment();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Test Case 5: Delivery department mixed with other departments
        // Input: There are 5 employees in total across all departments. Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively
        
        // Add 3 delivery department workers
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setDepartment("delivery");
        worker1.setWeeklyWorkingHours(25.0);
        company.addEmployee(worker1);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setDepartment("delivery");
        worker2.setWeeklyWorkingHours(32.0);
        company.addEmployee(worker2);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setDepartment("delivery");
        worker3.setWeeklyWorkingHours(28.0);
        company.addEmployee(worker3);
        
        // Add 2 non-delivery department employees
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment("sales");
        company.addEmployee(salesPerson);
        
        Manager manager = new Manager();
        manager.setDepartment("management");
        company.addEmployee(manager);
        
        double result = company.calculateAverageWorkingHoursInDeliveryDepartment();
        // Expected: (25 + 32 + 28) / 3 = 85 / 3 = 28.333...
        assertEquals(28.33, result, 0.01);
    }
}