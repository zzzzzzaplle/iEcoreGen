package edu.employee.employee2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.employee.Company;
import edu.employee.EmployeeFactory;
import edu.employee.EmployeePackage;
import edu.employee.ShiftWorker;

public class CR4Test {
    
    private EmployeeFactory factory;
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize the factory using Ecore factory pattern
        factory = EmployeeFactory.eINSTANCE;
        // Create company instance using factory
        company = factory.createCompany();
    }
    
    @Test
    public void testCase1_noShiftWorkersInCompany() {
        // Test Case 1: "No shift workers in the company"
        // Input: "A Company object with an empty list of ShiftWorkers"
        
        // No shift workers added to company
        // Expected Output: "The total holiday premiums should be 0"
        
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals("Total holiday premiums should be 0 when no shift workers exist", 
                     0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerWithPremium() {
        // Test Case 2: "One shift worker in the company"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 500.00"
        
        // Create shift worker using factory
        ShiftWorker shiftWorker = factory.createShiftWorker();
        shiftWorker.setHolidayPremium(500.00);
        
        // Add shift worker to company
        company.getEmployees().add(shiftWorker);
        
        // Expected Output: "The total holiday premiums should be 500.00"
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals("Total holiday premiums should be 500.00 for one shift worker", 
                     500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersDifferentPremiums() {
        // Test Case 3: "Multiple shift workers with different premiums"
        // Input: "A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively"
        
        // Create first shift worker
        ShiftWorker shiftWorker1 = factory.createShiftWorker();
        shiftWorker1.setHolidayPremium(200.00);
        company.getEmployees().add(shiftWorker1);
        
        // Create second shift worker
        ShiftWorker shiftWorker2 = factory.createShiftWorker();
        shiftWorker2.setHolidayPremium(300.00);
        company.getEmployees().add(shiftWorker2);
        
        // Create third shift worker
        ShiftWorker shiftWorker3 = factory.createShiftWorker();
        shiftWorker3.setHolidayPremium(400.00);
        company.getEmployees().add(shiftWorker3);
        
        // Expected Output: "The total holiday premiums should be 900.00"
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals("Total holiday premiums should be 900.00 for three shift workers with different premiums", 
                     900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersSomeZeroPremiums() {
        // Test Case 4: "Multiple shift workers with some zero premiums"
        // Input: "A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively"
        
        // Create first shift worker with zero premium
        ShiftWorker shiftWorker1 = factory.createShiftWorker();
        shiftWorker1.setHolidayPremium(0.00);
        company.getEmployees().add(shiftWorker1);
        
        // Create second shift worker with premium
        ShiftWorker shiftWorker2 = factory.createShiftWorker();
        shiftWorker2.setHolidayPremium(250.00);
        company.getEmployees().add(shiftWorker2);
        
        // Create third shift worker with zero premium
        ShiftWorker shiftWorker3 = factory.createShiftWorker();
        shiftWorker3.setHolidayPremium(0.00);
        company.getEmployees().add(shiftWorker3);
        
        // Create fourth shift worker with premium
        ShiftWorker shiftWorker4 = factory.createShiftWorker();
        shiftWorker4.setHolidayPremium(150.00);
        company.getEmployees().add(shiftWorker4);
        
        // Expected Output: "The total holiday premiums should be 400.00"
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals("Total holiday premiums should be 400.00 for shift workers with some zero premiums", 
                     400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: "Single shift worker with zero premium"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 0"
        
        // Create shift worker with zero premium
        ShiftWorker shiftWorker = factory.createShiftWorker();
        shiftWorker.setHolidayPremium(0.00);
        
        // Add shift worker to company
        company.getEmployees().add(shiftWorker);
        
        // Expected Output: "The total holiday premiums should be 0"
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals("Total holiday premiums should be 0 for shift worker with zero premium", 
                     0.0, result, 0.001);
    }
}