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
    
    /**
     * Test Case 1: No shift workers in the company
     * Input: A Company object with an empty list of ShiftWorkers
     * Expected Output: The total holiday premiums should be 0
     */
    @Test
    public void testCase1_noShiftWorkers() {
        // Create company with no shift workers (only other employee types)
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setFixedSalary(3000.00);
        salesPerson.setAmountOfSales(10000.00);
        salesPerson.setCommissionPercentage(0.05);
        company.addEmployee(salesPerson);
        
        OffShiftWorker offShiftWorker = new OffShiftWorker();
        offShiftWorker.setWeeklyWorkingHours(40.0);
        offShiftWorker.setHourlyRate(15.0);
        company.addEmployee(offShiftWorker);
        
        // Verify total holiday premiums is 0 when no shift workers exist
        double result = company.totalHolidayPremiums();
        assertEquals(0.0, result, 0.001);
    }
    
    /**
     * Test Case 2: One shift worker in the company
     * Input: A Company object with a single ShiftWorker having a holidayPremium of 500.00
     * Expected Output: The total holiday premiums should be 500.00
     */
    @Test
    public void testCase2_singleShiftWorker() {
        // Create and configure a single shift worker
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setHolidayPremium(500.00);
        shiftWorker.setWeeklyWorkingHours(40.0);
        shiftWorker.setHourlyRate(20.0);
        shiftWorker.setDepartment(Department.DELIVERY);
        
        company.addEmployee(shiftWorker);
        
        // Verify total holiday premiums equals the single worker's premium
        double result = company.totalHolidayPremiums();
        assertEquals(500.00, result, 0.001);
    }
    
    /**
     * Test Case 3: Multiple shift workers with different premiums
     * Input: A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively
     * Expected Output: The total holiday premiums should be 900.00
     */
    @Test
    public void testCase3_multipleShiftWorkersDifferentPremiums() {
        // Create and configure three shift workers with different premiums
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setHolidayPremium(200.00);
        worker1.setWeeklyWorkingHours(35.0);
        worker1.setHourlyRate(18.0);
        worker1.setDepartment(Department.DELIVERY);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setHolidayPremium(300.00);
        worker2.setWeeklyWorkingHours(40.0);
        worker2.setHourlyRate(20.0);
        worker2.setDepartment(Department.DELIVERY);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setHolidayPremium(400.00);
        worker3.setWeeklyWorkingHours(38.0);
        worker3.setHourlyRate(19.0);
        worker3.setDepartment(Department.DELIVERY);
        
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(worker3);
        
        // Verify total holiday premiums equals sum of all premiums
        double result = company.totalHolidayPremiums();
        assertEquals(900.00, result, 0.001);
    }
    
    /**
     * Test Case 4: Multiple shift workers with some zero premiums
     * Input: A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively
     * Expected Output: The total holiday premiums should be 400.00
     */
    @Test
    public void testCase4_multipleShiftWorkersWithZeroPremiums() {
        // Create and configure four shift workers with some zero premiums
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setHolidayPremium(0.00);
        worker1.setWeeklyWorkingHours(35.0);
        worker1.setHourlyRate(18.0);
        worker1.setDepartment(Department.DELIVERY);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setHolidayPremium(250.00);
        worker2.setWeeklyWorkingHours(40.0);
        worker2.setHourlyRate(20.0);
        worker2.setDepartment(Department.DELIVERY);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setHolidayPremium(0.00);
        worker3.setWeeklyWorkingHours(38.0);
        worker3.setHourlyRate(19.0);
        worker3.setDepartment(Department.DELIVERY);
        
        ShiftWorker worker4 = new ShiftWorker();
        worker4.setHolidayPremium(150.00);
        worker4.setWeeklyWorkingHours(42.0);
        worker4.setHourlyRate(21.0);
        worker4.setDepartment(Department.DELIVERY);
        
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(worker3);
        company.addEmployee(worker4);
        
        // Verify total holiday premiums equals sum of non-zero premiums
        double result = company.totalHolidayPremiums();
        assertEquals(400.00, result, 0.001);
    }
    
    /**
     * Test Case 5: Single shift worker with zero premium
     * Input: A Company object with a single ShiftWorker having a holidayPremium of 0
     * Expected Output: The total holiday premiums should be 0
     */
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Create and configure a single shift worker with zero premium
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setHolidayPremium(0.00);
        shiftWorker.setWeeklyWorkingHours(40.0);
        shiftWorker.setHourlyRate(20.0);
        shiftWorker.setDepartment(Department.DELIVERY);
        
        company.addEmployee(shiftWorker);
        
        // Verify total holiday premiums is 0 when the only shift worker has zero premium
        double result = company.totalHolidayPremiums();
        assertEquals(0.0, result, 0.001);
    }
}