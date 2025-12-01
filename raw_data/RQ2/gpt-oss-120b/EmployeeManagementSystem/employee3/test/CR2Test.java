package edu.employee.employee3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.employee.*;
import edu.employee.EmployeeFactory;
import edu.employee.EmployeePackage;
import org.eclipse.emf.common.util.EList;

public class CR2Test {
    
    private EmployeeFactory factory;
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize the factory and create a company instance
        factory = EmployeeFactory.eINSTANCE;
        company = factory.createCompany();
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Test Case 1: Single worker in delivery department
        // Input: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        
        // Create delivery department
        Department deliveryDept = factory.createDepartment();
        deliveryDept.setType(DepartmentType.DELIVERY);
        company.getDepartments().add(deliveryDept);
        
        // Create a shift worker (delivery department only allows shift workers)
        ShiftWorker worker = factory.createShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(15.0);
        worker.setHolidayPremium(50.0);
        
        // Add worker to department and company
        deliveryDept.getEmployees().add(worker);
        company.getEmployees().add(worker);
        
        // Calculate average working hours
        double result = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Expected Output: The average working hours per week is 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() {
        // Test Case 2: Multiple workers in delivery department with same hours
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        
        // Create delivery department
        Department deliveryDept = factory.createDepartment();
        deliveryDept.setType(DepartmentType.DELIVERY);
        company.getDepartments().add(deliveryDept);
        
        // Create three shift workers with same working hours
        for (int i = 0; i < 3; i++) {
            ShiftWorker worker = factory.createShiftWorker();
            worker.setWeeklyWorkingHour(35);
            worker.setHourlyRates(15.0);
            worker.setHolidayPremium(50.0);
            
            deliveryDept.getEmployees().add(worker);
            company.getEmployees().add(worker);
        }
        
        // Calculate average working hours
        double result = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Expected Output: The average working hours per week is 35 hours
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() {
        // Test Case 3: Multiple workers in delivery department with different hours
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        
        // Create delivery department
        Department deliveryDept = factory.createDepartment();
        deliveryDept.setType(DepartmentType.DELIVERY);
        company.getDepartments().add(deliveryDept);
        
        // Create first worker with 20 hours
        ShiftWorker worker1 = factory.createShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        worker1.setHourlyRates(15.0);
        worker1.setHolidayPremium(50.0);
        
        // Create second worker with 30 hours
        ShiftWorker worker2 = factory.createShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        worker2.setHourlyRates(15.0);
        worker2.setHolidayPremium(50.0);
        
        // Add workers to department and company
        deliveryDept.getEmployees().add(worker1);
        deliveryDept.getEmployees().add(worker2);
        company.getEmployees().add(worker1);
        company.getEmployees().add(worker2);
        
        // Calculate average working hours
        double result = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Expected Output: The average working hours per week is 25 hours
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentNoWorkers() {
        // Test Case 4: Delivery department with no workers
        // Input: There are zero workers in the Delivery department
        
        // Create delivery department with no workers
        Department deliveryDept = factory.createDepartment();
        deliveryDept.setType(DepartmentType.DELIVERY);
        company.getDepartments().add(deliveryDept);
        
        // Calculate average working hours
        double result = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Expected Output: The average working hours per week is 0 hours (as there are no workers)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Test Case 5: Delivery department mixed with other departments
        // Input: There are 5 employees in total across all departments. Among them, 3 are in the Delivery department 
        // with weeklyWorkingHours of 25, 32, and 28 hours respectively.
        
        // Create delivery department
        Department deliveryDept = factory.createDepartment();
        deliveryDept.setType(DepartmentType.DELIVERY);
        company.getDepartments().add(deliveryDept);
        
        // Create three shift workers for delivery department with different hours
        ShiftWorker worker1 = factory.createShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        worker1.setHourlyRates(15.0);
        worker1.setHolidayPremium(50.0);
        
        ShiftWorker worker2 = factory.createShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        worker2.setHourlyRates(15.0);
        worker2.setHolidayPremium(50.0);
        
        ShiftWorker worker3 = factory.createShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        worker3.setHourlyRates(15.0);
        worker3.setHolidayPremium(50.0);
        
        // Add delivery department workers
        deliveryDept.getEmployees().add(worker1);
        deliveryDept.getEmployees().add(worker2);
        deliveryDept.getEmployees().add(worker3);
        company.getEmployees().add(worker1);
        company.getEmployees().add(worker2);
        company.getEmployees().add(worker3);
        
        // Create other departments with non-worker employees to mix things up
        Department productionDept = factory.createDepartment();
        productionDept.setType(DepartmentType.PRODUCTION);
        company.getDepartments().add(productionDept);
        
        // Add a manager to production department (not a worker, shouldn't affect calculation)
        Manager manager = factory.createManager();
        manager.setSalary(5000.0);
        productionDept.getEmployees().add(manager);
        company.getEmployees().add(manager);
        
        // Add a sales person to production department (not a worker, shouldn't affect calculation)
        SalesPeople salesPerson = factory.createSalesPeople();
        salesPerson.setSalary(3000.0);
        salesPerson.setAmountOfSales(10000.0);
        salesPerson.setCommissionPercentage(0.1);
        productionDept.getEmployees().add(salesPerson);
        company.getEmployees().add(salesPerson);
        
        // Calculate average working hours for delivery department only
        double result = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours
        // Calculation: (25 + 32 + 28) / 3 = 85 / 3 = 28.333...
        assertEquals(28.33, result, 0.01);
    }
}