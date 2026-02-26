package edu.employee.employee2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.employee.EmployeeFactory;
import edu.employee.EmployeePackage;
import edu.employee.Company;
import edu.employee.Department;
import edu.employee.DepartmentType;
import edu.employee.Worker;
import edu.employee.ShiftWorker;
import edu.employee.OffShiftWorker;

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
        // Create delivery department
        Department deliveryDept = factory.createDepartment();
        deliveryDept.setType(DepartmentType.DELIVERY);
        company.getDepartments().add(deliveryDept);
        
        // Create a single worker in delivery department
        ShiftWorker worker = factory.createShiftWorker();
        worker.setWeeklyWorkingHour(40);
        deliveryDept.getEmployees().add(worker);
        company.getEmployees().add(worker);
        
        // Calculate average working hours
        double result = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Verify result
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() {
        // Create delivery department
        Department deliveryDept = factory.createDepartment();
        deliveryDept.setType(DepartmentType.DELIVERY);
        company.getDepartments().add(deliveryDept);
        
        // Create three workers with same working hours
        for (int i = 0; i < 3; i++) {
            OffShiftWorker worker = factory.createOffShiftWorker();
            worker.setWeeklyWorkingHour(35);
            deliveryDept.getEmployees().add(worker);
            company.getEmployees().add(worker);
        }
        
        // Calculate average working hours
        double result = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Verify result
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() {
        // Create delivery department
        Department deliveryDept = factory.createDepartment();
        deliveryDept.setType(DepartmentType.DELIVERY);
        company.getDepartments().add(deliveryDept);
        
        // Create two workers with different working hours
        ShiftWorker worker1 = factory.createShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        deliveryDept.getEmployees().add(worker1);
        company.getEmployees().add(worker1);
        
        OffShiftWorker worker2 = factory.createOffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        deliveryDept.getEmployees().add(worker2);
        company.getEmployees().add(worker2);
        
        // Calculate average working hours
        double result = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Verify result - average of 20 and 30 is 25
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentNoWorkers() {
        // Create delivery department with no workers
        Department deliveryDept = factory.createDepartment();
        deliveryDept.setType(DepartmentType.DELIVERY);
        company.getDepartments().add(deliveryDept);
        
        // Calculate average working hours
        double result = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Verify result should be 0 when no workers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Create multiple departments
        Department deliveryDept = factory.createDepartment();
        deliveryDept.setType(DepartmentType.DELIVERY);
        company.getDepartments().add(deliveryDept);
        
        Department productionDept = factory.createDepartment();
        productionDept.setType(DepartmentType.PRODUCTION);
        company.getDepartments().add(productionDept);
        
        Department controlDept = factory.createDepartment();
        controlDept.setType(DepartmentType.CONTROL);
        company.getDepartments().add(controlDept);
        
        // Add workers to delivery department with different hours
        int[] deliveryHours = {25, 32, 28};
        for (int hours : deliveryHours) {
            ShiftWorker worker = factory.createShiftWorker();
            worker.setWeeklyWorkingHour(hours);
            deliveryDept.getEmployees().add(worker);
            company.getEmployees().add(worker);
        }
        
        // Add workers to other departments (should not affect delivery department calculation)
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
        
        // Verify result - average of 25, 32, 28 is 28.33 (rounded to two decimal places)
        assertEquals(28.33, result, 0.01);
    }
}