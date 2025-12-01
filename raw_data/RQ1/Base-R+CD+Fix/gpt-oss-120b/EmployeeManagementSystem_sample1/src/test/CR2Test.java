import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;

public class CR2Test {
    
    private Company company;
    private Department deliveryDepartment;
    private Department productionDepartment;
    
    @Before
    public void setUp() {
        company = new Company();
        deliveryDepartment = new Department();
        deliveryDepartment.setType(DepartmentType.DELIVERY);
        
        productionDepartment = new Department();
        productionDepartment.setType(DepartmentType.PRODUCTION);
        
        company.addDepartment(deliveryDepartment);
        company.addDepartment(productionDepartment);
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Setup: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        ShiftWorker worker = new ShiftWorker();
        worker.setWeeklyWorkingHour(40);
        deliveryDepartment.addEmployee(worker);
        company.addEmployee(worker);
        
        // Execute: Calculate average working hours
        double result = company.calculateAverageDeliveryWorkerHours();
        
        // Verify: The average working hours per week is 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersInDeliveryDepartmentWithSameHours() {
        // Setup: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(35);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setWeeklyWorkingHour(35);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setWeeklyWorkingHour(35);
        
        deliveryDepartment.addEmployee(worker1);
        deliveryDepartment.addEmployee(worker2);
        deliveryDepartment.addEmployee(worker3);
        
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(worker3);
        
        // Execute: Calculate average working hours
        double result = company.calculateAverageDeliveryWorkerHours();
        
        // Verify: The average working hours per week is 35 hours
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersInDeliveryDepartmentWithDifferentHours() {
        // Setup: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        
        deliveryDepartment.addEmployee(worker1);
        deliveryDepartment.addEmployee(worker2);
        
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        
        // Execute: Calculate average working hours
        double result = company.calculateAverageDeliveryWorkerHours();
        
        // Verify: The average working hours per week is 25 hours
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Setup: Zero workers in the Delivery department (only non-worker employees)
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(50000);
        deliveryDepartment.addEmployee(salesPerson);
        company.addEmployee(salesPerson);
        
        // Execute: Calculate average working hours
        double result = company.calculateAverageDeliveryWorkerHours();
        
        // Verify: The average working hours per week is 0 hours (as there are no workers)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Setup: 5 employees in total across all departments. Among them, 3 are in the Delivery 
        // department with weeklyWorkingHours of 25, 32, and 28 hours respectively.
        
        // Delivery department workers
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        
        deliveryDepartment.addEmployee(worker1);
        deliveryDepartment.addEmployee(worker2);
        deliveryDepartment.addEmployee(worker3);
        
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(worker3);
        
        // Other department employees (should not be counted)
        ShiftWorker prodWorker = new ShiftWorker();
        prodWorker.setWeeklyWorkingHour(40);
        productionDepartment.addEmployee(prodWorker);
        company.addEmployee(prodWorker);
        
        Manager manager = new Manager();
        manager.setSalary(80000);
        productionDepartment.addEmployee(manager);
        company.addEmployee(manager);
        
        // Execute: Calculate average working hours
        double result = company.calculateAverageDeliveryWorkerHours();
        
        // Verify: The average working hours per week for workers in the Delivery department is 28.33 hours
        assertEquals(28.33, result, 0.01);
    }
}