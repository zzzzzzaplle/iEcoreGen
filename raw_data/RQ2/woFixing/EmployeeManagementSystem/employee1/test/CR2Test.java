package edu.employee.employee1.test;

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
        // Initialize the factory and company using Ecore factory pattern
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
        
        // Verify the result
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() {
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
        
        // Verify the result
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() {
        // Create delivery department
        Department deliveryDept = factory.createDepartment();
        deliveryDept.setType(DepartmentType.DELIVERY);
        company.getDepartments().add(deliveryDept);
        
        // Create two workers with different hours
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
        
        // Verify the result (20 + 30) / 2 = 25
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
        
        // Verify the result should be 0 when no workers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Create multiple departments
        Department productionDept = factory.createDepartment();
        productionDept.setType(DepartmentType.PRODUCTION);
        company.getDepartments().add(productionDept);
        
        Department controlDept = factory.createDepartment();
        controlDept.setType(DepartmentType.CONTROL);
        company.getDepartments().add(controlDept);
        
        Department deliveryDept = factory.createDepartment();
        deliveryDept.setType(DepartmentType.DELIVERY);
        company.getDepartments().add(deliveryDept);
        
        // Add workers to production department (should not be counted)
        OffShiftWorker prodWorker1 = factory.createOffShiftWorker();
        prodWorker1.setWeeklyWorkingHour(40);
        productionDept.getEmployees().add(prodWorker1);
        company.getEmployees().add(prodWorker1);
        
        OffShiftWorker prodWorker2 = factory.createOffShiftWorker();
        prodWorker2.setWeeklyWorkingHour(38);
        productionDept.getEmployees().add(prodWorker2);
        company.getEmployees().add(prodWorker2);
        
        // Add workers to delivery department (should be counted)
        ShiftWorker delWorker1 = factory.createShiftWorker();
        delWorker1.setWeeklyWorkingHour(25);
        deliveryDept.getEmployees().add(delWorker1);
        company.getEmployees().add(delWorker1);
        
        ShiftWorker delWorker2 = factory.createShiftWorker();
        delWorker2.setWeeklyWorkingHour(32);
        deliveryDept.getEmployees().add(delWorker2);
        company.getEmployees().add(delWorker2);
        
        OffShiftWorker delWorker3 = factory.createOffShiftWorker();
        delWorker3.setWeeklyWorkingHour(28);
        deliveryDept.getEmployees().add(delWorker3);
        company.getEmployees().add(delWorker3);
        
        // Calculate average working hours for delivery department only
        double result = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Verify the result (25 + 32 + 28) / 3 = 28.33
        assertEquals(28.33, result, 0.01);
    }
}