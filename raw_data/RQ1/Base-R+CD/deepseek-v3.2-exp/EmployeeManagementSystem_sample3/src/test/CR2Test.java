import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR2Test {
    
    private Company company;
    private Department deliveryDepartment;
    
    @Before
    public void setUp() {
        company = new Company();
        deliveryDepartment = new Department();
        deliveryDepartment.setType(DepartmentType.DELIVERY);
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Setup: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(15.0);
        
        deliveryDepartment.getEmployees().add(worker);
        company.getDepartments().add(deliveryDepartment);
        
        // Execute: Calculate average working hours
        double result = company.calculateAverageWorkerWorkingHoursInDeliveryDepartment();
        
        // Verify: The average working hours per week is 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersInDeliveryDepartmentWithSameHours() {
        // Setup: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(35);
        worker1.setHourlyRates(15.0);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(35);
        worker2.setHourlyRates(15.0);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(35);
        worker3.setHourlyRates(15.0);
        
        deliveryDepartment.getEmployees().add(worker1);
        deliveryDepartment.getEmployees().add(worker2);
        deliveryDepartment.getEmployees().add(worker3);
        company.getDepartments().add(deliveryDepartment);
        
        // Execute: Calculate average working hours
        double result = company.calculateAverageWorkerWorkingHoursInDeliveryDepartment();
        
        // Verify: The average working hours per week is 35 hours
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersInDeliveryDepartmentWithDifferentHours() {
        // Setup: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        worker1.setHourlyRates(15.0);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        worker2.setHourlyRates(15.0);
        
        deliveryDepartment.getEmployees().add(worker1);
        deliveryDepartment.getEmployees().add(worker2);
        company.getDepartments().add(deliveryDepartment);
        
        // Execute: Calculate average working hours
        double result = company.calculateAverageWorkerWorkingHoursInDeliveryDepartment();
        
        // Verify: The average working hours per week is 25 hours
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Setup: There are zero workers in the Delivery department
        // Add some non-worker employees to ensure department exists but has no workers
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(50000);
        salesPerson.setAmountOfSales(100000);
        salesPerson.setCommissionPercentage(0.1);
        
        deliveryDepartment.getEmployees().add(salesPerson);
        company.getDepartments().add(deliveryDepartment);
        
        // Execute: Calculate average working hours
        double result = company.calculateAverageWorkerWorkingHoursInDeliveryDepartment();
        
        // Verify: The average working hours per week is 0 hours (as there are no workers)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Setup: There are 5 employees in total across all departments. 
        // Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively.
        
        // Create Delivery department with 3 workers
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        worker1.setHourlyRates(15.0);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        worker2.setHourlyRates(15.0);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        worker3.setHourlyRates(15.0);
        
        deliveryDepartment.getEmployees().add(worker1);
        deliveryDepartment.getEmployees().add(worker2);
        deliveryDepartment.getEmployees().add(worker3);
        
        // Create other departments with non-worker employees
        Department productionDepartment = new Department();
        productionDepartment.setType(DepartmentType.PRODUCTION);
        
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(50000);
        salesPerson.setAmountOfSales(100000);
        salesPerson.setCommissionPercentage(0.1);
        
        Manager manager = new Manager();
        manager.setSalary(80000);
        
        productionDepartment.getEmployees().add(salesPerson);
        productionDepartment.getEmployees().add(manager);
        
        company.getDepartments().add(deliveryDepartment);
        company.getDepartments().add(productionDepartment);
        
        // Execute: Calculate average working hours
        double result = company.calculateAverageWorkerWorkingHoursInDeliveryDepartment();
        
        // Verify: The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours
        assertEquals(28.33, result, 0.01);
    }
}