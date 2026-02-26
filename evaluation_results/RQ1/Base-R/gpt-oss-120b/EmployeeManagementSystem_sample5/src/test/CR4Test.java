import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_noShiftWorkersInCompany() {
        // Test Case 1: "No shift workers in the company"
        // Input: "A Company object with an empty list of ShiftWorkers"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Add some non-shift workers to ensure company is not empty
        NonShiftWorker worker1 = new NonShiftWorker();
        worker1.setDepartment(Department.DELIVERY);
        worker1.setWeeklyWorkingHours(40.0);
        worker1.setHourlyRate(20.0);
        worker1.setWeekendPermit(true);
        
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment(Department.CONTROL);
        salesPerson.setSalaryBase(3000.0);
        salesPerson.setAmountOfSales(10000.0);
        salesPerson.setCommissionPercentage(0.05);
        
        company.addEmployee(worker1);
        company.addEmployee(salesPerson);
        
        // Calculate total holiday premiums
        double result = company.totalHolidayPremiums();
        
        // Verify result is 0 since there are no shift workers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Test Case 2: "One shift worker in the company"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 500.00"
        // Expected Output: "The total holiday premiums should be 500.00"
        
        // Create a shift worker with holiday premium of 500.00
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setDepartment(Department.DELIVERY);
        shiftWorker.setWeeklyWorkingHours(40.0);
        shiftWorker.setHourlyRate(25.0);
        shiftWorker.setHolidayPremium(500.00);
        
        company.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.totalHolidayPremiums();
        
        // Verify result is exactly 500.00
        assertEquals(500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: "Multiple shift workers with different premiums"
        // Input: "A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively"
        // Expected Output: "The total holiday premiums should be 900.00"
        
        // Create three shift workers with different holiday premiums
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setDepartment(Department.DELIVERY);
        worker1.setWeeklyWorkingHours(35.0);
        worker1.setHourlyRate(22.0);
        worker1.setHolidayPremium(200.00);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setDepartment(Department.DELIVERY);
        worker2.setWeeklyWorkingHours(40.0);
        worker2.setHourlyRate(24.0);
        worker2.setHolidayPremium(300.00);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setDepartment(Department.DELIVERY);
        worker3.setWeeklyWorkingHours(38.0);
        worker3.setHourlyRate(26.0);
        worker3.setHolidayPremium(400.00);
        
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(worker3);
        
        // Calculate total holiday premiums
        double result = company.totalHolidayPremiums();
        
        // Verify result is the sum of all premiums: 200 + 300 + 400 = 900
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: "Multiple shift workers with some zero premiums"
        // Input: "A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively"
        // Expected Output: "The total holiday premiums should be 400.00"
        
        // Create four shift workers with some zero premiums
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setDepartment(Department.DELIVERY);
        worker1.setWeeklyWorkingHours(35.0);
        worker1.setHourlyRate(22.0);
        worker1.setHolidayPremium(0.00);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setDepartment(Department.DELIVERY);
        worker2.setWeeklyWorkingHours(40.0);
        worker2.setHourlyRate(24.0);
        worker2.setHolidayPremium(250.00);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setDepartment(Department.DELIVERY);
        worker3.setWeeklyWorkingHours(38.0);
        worker3.setHourlyRate(26.0);
        worker3.setHolidayPremium(0.00);
        
        ShiftWorker worker4 = new ShiftWorker();
        worker4.setDepartment(Department.DELIVERY);
        worker4.setWeeklyWorkingHours(42.0);
        worker4.setHourlyRate(28.0);
        worker4.setHolidayPremium(150.00);
        
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(worker3);
        company.addEmployee(worker4);
        
        // Calculate total holiday premiums
        double result = company.totalHolidayPremiums();
        
        // Verify result is sum of non-zero premiums: 0 + 250 + 0 + 150 = 400
        assertEquals(400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: "Single shift worker with zero premium"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 0"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Create a shift worker with zero holiday premium
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setDepartment(Department.DELIVERY);
        shiftWorker.setWeeklyWorkingHours(40.0);
        shiftWorker.setHourlyRate(25.0);
        shiftWorker.setHolidayPremium(0.00);
        
        company.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.totalHolidayPremiums();
        
        // Verify result is 0 since the premium is zero
        assertEquals(0.0, result, 0.001);
    }
}