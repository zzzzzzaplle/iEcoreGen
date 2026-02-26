import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        // Input: A Company object with an empty list of ShiftWorkers
        // Create a company with no shift workers (only other employee types)
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(50000);
        company.getEmployees().add(salesPerson);
        
        Manager manager = new Manager();
        manager.setSalary(80000);
        company.getEmployees().add(manager);
        
        // Expected Output: The total holiday premiums should be 0
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(0.0, result, 0.01);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 500.00
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setHolidayPremium(500.00);
        company.getEmployees().add(shiftWorker);
        
        // Expected Output: The total holiday premiums should be 500.00
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(500.00, result, 0.01);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Input: A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setHolidayPremium(200.00);
        company.getEmployees().add(shiftWorker1);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setHolidayPremium(300.00);
        company.getEmployees().add(shiftWorker2);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setHolidayPremium(400.00);
        company.getEmployees().add(shiftWorker3);
        
        // Expected Output: The total holiday premiums should be 900.00
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(900.00, result, 0.01);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Input: A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setHolidayPremium(0.0);
        company.getEmployees().add(shiftWorker1);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setHolidayPremium(250.00);
        company.getEmployees().add(shiftWorker2);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setHolidayPremium(0.0);
        company.getEmployees().add(shiftWorker3);
        
        ShiftWorker shiftWorker4 = new ShiftWorker();
        shiftWorker4.setHolidayPremium(150.00);
        company.getEmployees().add(shiftWorker4);
        
        // Expected Output: The total holiday premiums should be 400.00
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(400.00, result, 0.01);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 0
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setHolidayPremium(0.0);
        company.getEmployees().add(shiftWorker);
        
        // Expected Output: The total holiday premiums should be 0
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(0.0, result, 0.01);
    }
}