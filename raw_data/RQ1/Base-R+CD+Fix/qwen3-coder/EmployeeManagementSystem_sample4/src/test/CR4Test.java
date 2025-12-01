import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR4Test {
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_NoShiftWorkersInCompany() {
        // Test Case 1: "No shift workers in the company"
        // Input: "A Company object with an empty list of ShiftWorkers"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Setup - company has no employees (no shift workers)
        company.setEmployees(new ArrayList<Employee>());
        
        // Execute
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_OneShiftWorkerInCompany() {
        // Test Case 2: "One shift worker in the company"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 500.00"
        // Expected Output: "The total holiday premiums should be 500.00"
        
        // Setup - create one shift worker with holiday premium of 500.00
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setHolidayPremium(500.00);
        
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(shiftWorker);
        company.setEmployees(employees);
        
        // Execute
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify
        assertEquals(500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: "Multiple shift workers with different premiums"
        // Input: "A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively"
        // Expected Output: "The total holiday premiums should be 900.00"
        
        // Setup - create three shift workers with different holiday premiums
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setHolidayPremium(200.00);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setHolidayPremium(300.00);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setHolidayPremium(400.00);
        
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(worker1);
        employees.add(worker2);
        employees.add(worker3);
        company.setEmployees(employees);
        
        // Execute
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_MultipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: "Multiple shift workers with some zero premiums"
        // Input: "A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively"
        // Expected Output: "The total holiday premiums should be 400.00"
        
        // Setup - create four shift workers with some zero premiums
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setHolidayPremium(0.00);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setHolidayPremium(250.00);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setHolidayPremium(0.00);
        
        ShiftWorker worker4 = new ShiftWorker();
        worker4.setHolidayPremium(150.00);
        
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(worker1);
        employees.add(worker2);
        employees.add(worker3);
        employees.add(worker4);
        company.setEmployees(employees);
        
        // Execute
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify
        assertEquals(400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_SingleShiftWorkerWithZeroPremium() {
        // Test Case 5: "Single shift worker with zero premium"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 0"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Setup - create one shift worker with zero holiday premium
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setHolidayPremium(0.00);
        
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(shiftWorker);
        company.setEmployees(employees);
        
        // Execute
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify
        assertEquals(0.00, result, 0.001);
    }
}