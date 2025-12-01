package edu.employee.employee3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.employee.Company;
import edu.employee.EmployeeFactory;
import edu.employee.EmployeePackage;
import edu.employee.ShiftWorker;
import edu.employee.OffShiftWorker;
import edu.employee.SalesPeople;
import edu.employee.Manager;
import edu.employee.Department;
import edu.employee.DepartmentType;

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
    public void testCase1_noShiftWorkersInCompany() {
        // Test Case 1: "No shift workers in the company"
        // Input: "A Company object with an empty list of ShiftWorkers"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Create some non-shift worker employees to ensure company has employees
        OffShiftWorker offShiftWorker = factory.createOffShiftWorker();
        offShiftWorker.setWeeklyWorkingHour(40);
        offShiftWorker.setHourlyRates(15.0);
        company.getEmployees().add(offShiftWorker);
        
        SalesPeople salesPerson = factory.createSalesPeople();
        salesPerson.setSalary(3000.0);
        salesPerson.setAmountOfSales(10000.0);
        salesPerson.setCommissionPercentage(0.1);
        company.getEmployees().add(salesPerson);
        
        Manager manager = factory.createManager();
        manager.setSalary(5000.0);
        company.getEmployees().add(manager);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify result is 0 since there are no shift workers
        assertEquals("Total holiday premiums should be 0 when no shift workers exist", 
                     0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Test Case 2: "One shift worker in the company"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 500.00"
        // Expected Output: "The total holiday premiums should be 500.00"
        
        // Create a shift worker with holiday premium
        ShiftWorker shiftWorker = factory.createShiftWorker();
        shiftWorker.setHolidayPremium(500.00);
        company.getEmployees().add(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify result matches the single shift worker's premium
        assertEquals("Total holiday premiums should be 500.00 for one shift worker", 
                     500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: "Multiple shift workers with different premiums"
        // Input: "A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively"
        // Expected Output: "The total holiday premiums should be 900.00"
        
        // Create three shift workers with different holiday premiums
        ShiftWorker shiftWorker1 = factory.createShiftWorker();
        shiftWorker1.setHolidayPremium(200.00);
        company.getEmployees().add(shiftWorker1);
        
        ShiftWorker shiftWorker2 = factory.createShiftWorker();
        shiftWorker2.setHolidayPremium(300.00);
        company.getEmployees().add(shiftWorker2);
        
        ShiftWorker shiftWorker3 = factory.createShiftWorker();
        shiftWorker3.setHolidayPremium(400.00);
        company.getEmployees().add(shiftWorker3);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify result is the sum of all premiums
        assertEquals("Total holiday premiums should be 900.00 for three shift workers", 
                     900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: "Multiple shift workers with some zero premiums"
        // Input: "A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively"
        // Expected Output: "The total holiday premiums should be 400.00"
        
        // Create four shift workers with some zero premiums
        ShiftWorker shiftWorker1 = factory.createShiftWorker();
        shiftWorker1.setHolidayPremium(0.00);
        company.getEmployees().add(shiftWorker1);
        
        ShiftWorker shiftWorker2 = factory.createShiftWorker();
        shiftWorker2.setHolidayPremium(250.00);
        company.getEmployees().add(shiftWorker2);
        
        ShiftWorker shiftWorker3 = factory.createShiftWorker();
        shiftWorker3.setHolidayPremium(0.00);
        company.getEmployees().add(shiftWorker3);
        
        ShiftWorker shiftWorker4 = factory.createShiftWorker();
        shiftWorker4.setHolidayPremium(150.00);
        company.getEmployees().add(shiftWorker4);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify result is the sum of non-zero premiums only
        assertEquals("Total holiday premiums should be 400.00 for shift workers with some zero premiums", 
                     400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: "Single shift worker with zero premium"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 0"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Create a shift worker with zero holiday premium
        ShiftWorker shiftWorker = factory.createShiftWorker();
        shiftWorker.setHolidayPremium(0.00);
        company.getEmployees().add(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify result is 0 for zero premium
        assertEquals("Total holiday premiums should be 0 for shift worker with zero premium", 
                     0.00, result, 0.001);
    }
}