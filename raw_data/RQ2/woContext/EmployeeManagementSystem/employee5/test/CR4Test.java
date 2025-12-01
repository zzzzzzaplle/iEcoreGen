package edu.employee.employee5.test;

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
        // Initialize the factory and create a new company for each test
        factory = EmployeeFactory.eINSTANCE;
        company = factory.createCompany();
    }
    
    @Test
    public void testCase1_NoShiftWorkersInCompany() {
        // Test Case 1: "No shift workers in the company"
        // Input: "A Company object with an empty list of ShiftWorkers"
        // Expected Output: "The total holiday premiums should be 0"
        
        // The company is created with no employees by default
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        assertEquals("Total holiday premiums should be 0 when no shift workers exist", 
                     0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_OneShiftWorkerInCompany() {
        // Test Case 2: "One shift worker in the company"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 500.00"
        // Expected Output: "The total holiday premiums should be 500.00"
        
        // Create a shift worker with holiday premium of 500.00
        ShiftWorker shiftWorker = factory.createShiftWorker();
        shiftWorker.setHolidayPremium(500.00);
        
        // Add the shift worker to the company
        company.getEmployees().add(shiftWorker);
        
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        assertEquals("Total holiday premiums should be 500.00 for one shift worker", 
                     500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: "Multiple shift workers with different premiums"
        // Input: "A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively"
        // Expected Output: "The total holiday premiums should be 900.00"
        
        // Create three shift workers with different holiday premiums
        ShiftWorker shiftWorker1 = factory.createShiftWorker();
        shiftWorker1.setHolidayPremium(200.00);
        
        ShiftWorker shiftWorker2 = factory.createShiftWorker();
        shiftWorker2.setHolidayPremium(300.00);
        
        ShiftWorker shiftWorker3 = factory.createShiftWorker();
        shiftWorker3.setHolidayPremium(400.00);
        
        // Add all shift workers to the company
        company.getEmployees().add(shiftWorker1);
        company.getEmployees().add(shiftWorker2);
        company.getEmployees().add(shiftWorker3);
        
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        assertEquals("Total holiday premiums should be 900.00 for three shift workers with premiums 200, 300, 400", 
                     900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_MultipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: "Multiple shift workers with some zero premiums"
        // Input: "A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively"
        // Expected Output: "The total holiday premiums should be 400.00"
        
        // Create four shift workers with some zero premiums
        ShiftWorker shiftWorker1 = factory.createShiftWorker();
        shiftWorker1.setHolidayPremium(0.00);
        
        ShiftWorker shiftWorker2 = factory.createShiftWorker();
        shiftWorker2.setHolidayPremium(250.00);
        
        ShiftWorker shiftWorker3 = factory.createShiftWorker();
        shiftWorker3.setHolidayPremium(0.00);
        
        ShiftWorker shiftWorker4 = factory.createShiftWorker();
        shiftWorker4.setHolidayPremium(150.00);
        
        // Add all shift workers to the company
        company.getEmployees().add(shiftWorker1);
        company.getEmployees().add(shiftWorker2);
        company.getEmployees().add(shiftWorker3);
        company.getEmployees().add(shiftWorker4);
        
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        assertEquals("Total holiday premiums should be 400.00 for four shift workers with premiums 0, 250, 0, 150", 
                     400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_SingleShiftWorkerWithZeroPremium() {
        // Test Case 5: "Single shift worker with zero premium"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 0"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Create a shift worker with zero holiday premium
        ShiftWorker shiftWorker = factory.createShiftWorker();
        shiftWorker.setHolidayPremium(0.00);
        
        // Add the shift worker to the company
        company.getEmployees().add(shiftWorker);
        
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        assertEquals("Total holiday premiums should be 0 for a shift worker with zero premium", 
                     0.0, result, 0.001);
    }
}