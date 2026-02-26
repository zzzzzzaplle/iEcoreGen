package edu.employee.employee4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.employee.Company;
import edu.employee.Department;
import edu.employee.DepartmentType;
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
        // Create a new company for each test
        company = factory.createCompany();
    }
    
    @Test
    public void testCase1_NoShiftWorkersInCompany() {
        // Test Case 1: "No shift workers in the company"
        // Input: "A Company object with an empty list of ShiftWorkers"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Setup: Company has no employees (empty by default)
        // No setup needed as company starts with empty employees list
        
        // Execute: Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify: Total holiday premiums should be 0
        assertEquals("Total holiday premiums should be 0 when no shift workers exist", 
                     0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_OneShiftWorkerInCompany() {
        // Test Case 2: "One shift worker in the company"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 500.00"
        // Expected Output: "The total holiday premiums should be 500.00"
        
        // Setup: Create one shift worker with holiday premium of 500.00
        ShiftWorker shiftWorker = factory.createShiftWorker();
        shiftWorker.setHolidayPremium(500.00);
        shiftWorker.setWeeklyWorkingHour(40); // Required for calculation
        shiftWorker.setHourlyRates(25.0); // Required for calculation
        
        // Add shift worker to company
        company.getEmployees().add(shiftWorker);
        
        // Execute: Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify: Total holiday premiums should be 500.00
        assertEquals("Total holiday premiums should be 500.00 for one shift worker", 
                     500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: "Multiple shift workers with different premiums"
        // Input: "A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively"
        // Expected Output: "The total holiday premiums should be 900.00"
        
        // Setup: Create three shift workers with different holiday premiums
        ShiftWorker shiftWorker1 = factory.createShiftWorker();
        shiftWorker1.setHolidayPremium(200.00);
        shiftWorker1.setWeeklyWorkingHour(40);
        shiftWorker1.setHourlyRates(20.0);
        
        ShiftWorker shiftWorker2 = factory.createShiftWorker();
        shiftWorker2.setHolidayPremium(300.00);
        shiftWorker2.setWeeklyWorkingHour(40);
        shiftWorker2.setHourlyRates(25.0);
        
        ShiftWorker shiftWorker3 = factory.createShiftWorker();
        shiftWorker3.setHolidayPremium(400.00);
        shiftWorker3.setWeeklyWorkingHour(40);
        shiftWorker3.setHourlyRates(30.0);
        
        // Add all shift workers to company
        company.getEmployees().add(shiftWorker1);
        company.getEmployees().add(shiftWorker2);
        company.getEmployees().add(shiftWorker3);
        
        // Execute: Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify: Total holiday premiums should be 900.00 (200 + 300 + 400)
        assertEquals("Total holiday premiums should be 900.00 for three shift workers with different premiums", 
                     900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_MultipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: "Multiple shift workers with some zero premiums"
        // Input: "A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively"
        // Expected Output: "The total holiday premiums should be 400.00"
        
        // Setup: Create four shift workers with some zero premiums
        ShiftWorker shiftWorker1 = factory.createShiftWorker();
        shiftWorker1.setHolidayPremium(0.00);
        shiftWorker1.setWeeklyWorkingHour(40);
        shiftWorker1.setHourlyRates(15.0);
        
        ShiftWorker shiftWorker2 = factory.createShiftWorker();
        shiftWorker2.setHolidayPremium(250.00);
        shiftWorker2.setWeeklyWorkingHour(40);
        shiftWorker2.setHourlyRates(20.0);
        
        ShiftWorker shiftWorker3 = factory.createShiftWorker();
        shiftWorker3.setHolidayPremium(0.00);
        shiftWorker3.setWeeklyWorkingHour(40);
        shiftWorker3.setHourlyRates(18.0);
        
        ShiftWorker shiftWorker4 = factory.createShiftWorker();
        shiftWorker4.setHolidayPremium(150.00);
        shiftWorker4.setWeeklyWorkingHour(40);
        shiftWorker4.setHourlyRates(22.0);
        
        // Add all shift workers to company
        company.getEmployees().add(shiftWorker1);
        company.getEmployees().add(shiftWorker2);
        company.getEmployees().add(shiftWorker3);
        company.getEmployees().add(shiftWorker4);
        
        // Execute: Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify: Total holiday premiums should be 400.00 (0 + 250 + 0 + 150)
        assertEquals("Total holiday premiums should be 400.00 for four shift workers with some zero premiums", 
                     400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_SingleShiftWorkerWithZeroPremium() {
        // Test Case 5: "Single shift worker with zero premium"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 0"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Setup: Create one shift worker with zero holiday premium
        ShiftWorker shiftWorker = factory.createShiftWorker();
        shiftWorker.setHolidayPremium(0.00);
        shiftWorker.setWeeklyWorkingHour(40);
        shiftWorker.setHourlyRates(20.0);
        
        // Add shift worker to company
        company.getEmployees().add(shiftWorker);
        
        // Execute: Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify: Total holiday premiums should be 0
        assertEquals("Total holiday premiums should be 0 for single shift worker with zero premium", 
                     0.0, result, 0.001);
    }
}