package edu.employee.employee5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.employee.Company;
import edu.employee.Department;
import edu.employee.DepartmentType;
import edu.employee.EmployeeFactory;
import edu.employee.EmployeePackage;
import edu.employee.OffShiftWorker;
import edu.employee.ShiftWorker;
import edu.employee.Worker;

public class CR2Test {
    
    private EmployeeFactory factory;
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize the factory using Ecore factory pattern
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
        
        // Create a worker in delivery department
        OffShiftWorker worker = factory.createOffShiftWorker();
        worker.setWeeklyWorkingHour(40);
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
        
        // Create three workers with same hours
        for (int i = 0; i < 3; i++) {
            OffShiftWorker worker = factory.createOffShiftWorker();
            worker.setWeeklyWorkingHour(35);
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
        OffShiftWorker worker1 = factory.createOffShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        deliveryDept.getEmployees().add(worker1);
        company.getEmployees().add(worker1);
        
        // Create second worker with 30 hours
        OffShiftWorker worker2 = factory.createOffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        deliveryDept.getEmployees().add(worker2);
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
    public void testCase5_mixedDepartmentsWithDeliveryWorkers() {
        // Test Case 5: Delivery department mixed with other departments
        // Input: There are 5 employees in total across all departments. 
        // Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively.
        
        // Create delivery department
        Department deliveryDept = factory.createDepartment();
        deliveryDept.setType(DepartmentType.DELIVERY);
        company.getDepartments().add(deliveryDept);
        
        // Create production department
        Department productionDept = factory.createDepartment();
        productionDept.setType(DepartmentType.PRODUCTION);
        company.getDepartments().add(productionDept);
        
        // Create control department
        Department controlDept = factory.createDepartment();
        controlDept.setType(DepartmentType.CONTROL);
        company.getDepartments().add(controlDept);
        
        // Add 3 workers to delivery department with different hours
        OffShiftWorker worker1 = factory.createOffShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        deliveryDept.getEmployees().add(worker1);
        company.getEmployees().add(worker1);
        
        OffShiftWorker worker2 = factory.createOffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        deliveryDept.getEmployees().add(worker2);
        company.getEmployees().add(worker2);
        
        OffShiftWorker worker3 = factory.createOffShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        deliveryDept.getEmployees().add(worker3);
        company.getEmployees().add(worker3);
        
        // Add 2 workers to other departments (should not affect delivery department calculation)
        OffShiftWorker prodWorker = factory.createOffShiftWorker();
        prodWorker.setWeeklyWorkingHour(40);
        productionDept.getEmployees().add(prodWorker);
        company.getEmployees().add(prodWorker);
        
        OffShiftWorker controlWorker = factory.createOffShiftWorker();
        controlWorker.setWeeklyWorkingHour(45);
        controlDept.getEmployees().add(controlWorker);
        company.getEmployees().add(controlWorker);
        
        // Calculate average working hours for delivery department only
        double result = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours
        assertEquals(28.33, result, 0.01);
    }
}