import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

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
        // Setup: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        deliveryDepartment.addEmployee(worker);
        
        // Execute: Calculate average working hours
        double result = company.calculateAverageDeliveryDepartmentWorkingHours();
        
        // Verify: The average working hours per week is 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() {
        // Setup: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        for (int i = 0; i < 3; i++) {
            OffShiftWorker worker = new OffShiftWorker();
            worker.setWeeklyWorkingHour(35);
            deliveryDepartment.addEmployee(worker);
        }
        
        // Execute: Calculate average working hours
        double result = company.calculateAverageDeliveryDepartmentWorkingHours();
        
        // Verify: The average working hours per week is 35 hours
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() {
        // Setup: Two workers in the Delivery department with 20 and 30 hours
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        deliveryDepartment.addEmployee(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        deliveryDepartment.addEmployee(worker2);
        
        // Execute: Calculate average working hours
        double result = company.calculateAverageDeliveryDepartmentWorkingHours();
        
        // Verify: The average working hours per week is 25 hours
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Setup: Zero workers in the Delivery department (deliveryDepartment is empty)
        
        // Execute: Calculate average working hours
        double result = company.calculateAverageDeliveryDepartmentWorkingHours();
        
        // Verify: The average working hours per week is 0 hours
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Setup: Create multiple departments and add 5 employees total
        Department productionDepartment = new Department();
        productionDepartment.setType(DepartmentType.PRODUCTION);
        company.addDepartment(productionDepartment);
        
        Department controlDepartment = new Department();
        controlDepartment.setType(DepartmentType.CONTROL);
        company.addDepartment(controlDepartment);
        
        // Add 3 workers to delivery department with hours 25, 32, and 28
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        deliveryDepartment.addEmployee(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        deliveryDepartment.addEmployee(worker2);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        deliveryDepartment.addEmployee(worker3);
        
        // Add 2 non-worker employees to other departments (should not affect the calculation)
        Manager manager = new Manager();
        manager.setSalary(5000);
        productionDepartment.addEmployee(manager);
        
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(3000);
        salesPerson.setAmountOfSales(10000);
        salesPerson.setCommissionPercentage(0.1);
        controlDepartment.addEmployee(salesPerson);
        
        // Execute: Calculate average working hours for delivery department only
        double result = company.calculateAverageDeliveryDepartmentWorkingHours();
        
        // Verify: The average working hours per week for workers in the Delivery department is 28.33
        assertEquals(28.33, result, 0.01);
    }
}