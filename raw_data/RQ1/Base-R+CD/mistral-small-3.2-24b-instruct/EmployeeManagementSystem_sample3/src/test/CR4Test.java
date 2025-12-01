import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;

public class CR4Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_noShiftWorkersInCompany() {
        // Test Case 1: No shift workers in the company
        // Input: A Company object with an empty list of ShiftWorkers
        // Expected Output: The total holiday premiums should be 0
        
        // Add some non-ShiftWorker employees to ensure they are not counted
        Manager manager = new Manager();
        manager.setSocialInsuranceNumber("12345");
        company.addEmployee(manager);
        
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSocialInsuranceNumber("67890");
        company.addEmployee(salesPerson);
        
        OffShiftWorker offShiftWorker = new OffShiftWorker();
        offShiftWorker.setSocialInsuranceNumber("11111");
        company.addEmployee(offShiftWorker);
        
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals("Total holiday premiums should be 0 when no shift workers exist", 0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerWithPremium() {
        // Test Case 2: One shift worker in the company
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 500.00
        // Expected Output: The total holiday premiums should be 500.00
        
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setSocialInsuranceNumber("12345");
        shiftWorker.setHolidayPremium(500.00);
        company.addEmployee(shiftWorker);
        
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals("Total holiday premiums should be 500.00 for one shift worker with 500 premium", 
                     500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: Multiple shift workers with different premiums
        // Input: A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively
        // Expected Output: The total holiday premiums should be 900.00
        
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setSocialInsuranceNumber("11111");
        shiftWorker1.setHolidayPremium(200.00);
        company.addEmployee(shiftWorker1);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setSocialInsuranceNumber("22222");
        shiftWorker2.setHolidayPremium(300.00);
        company.addEmployee(shiftWorker2);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setSocialInsuranceNumber("33333");
        shiftWorker3.setHolidayPremium(400.00);
        company.addEmployee(shiftWorker3);
        
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals("Total holiday premiums should be 900.00 for three shift workers with premiums 200+300+400", 
                     900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: Multiple shift workers with some zero premiums
        // Input: A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively
        // Expected Output: The total holiday premiums should be 400.00
        
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setSocialInsuranceNumber("11111");
        shiftWorker1.setHolidayPremium(0);
        company.addEmployee(shiftWorker1);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setSocialInsuranceNumber("22222");
        shiftWorker2.setHolidayPremium(250.00);
        company.addEmployee(shiftWorker2);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setSocialInsuranceNumber("33333");
        shiftWorker3.setHolidayPremium(0);
        company.addEmployee(shiftWorker3);
        
        ShiftWorker shiftWorker4 = new ShiftWorker();
        shiftWorker4.setSocialInsuranceNumber("44444");
        shiftWorker4.setHolidayPremium(150.00);
        company.addEmployee(shiftWorker4);
        
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals("Total holiday premiums should be 400.00 for four shift workers with premiums 0+250+0+150", 
                     400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: Single shift worker with zero premium
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 0
        // Expected Output: The total holiday premiums should be 0
        
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setSocialInsuranceNumber("12345");
        shiftWorker.setHolidayPremium(0);
        company.addEmployee(shiftWorker);
        
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals("Total holiday premiums should be 0 for one shift worker with 0 premium", 
                     0.0, result, 0.001);
    }
}