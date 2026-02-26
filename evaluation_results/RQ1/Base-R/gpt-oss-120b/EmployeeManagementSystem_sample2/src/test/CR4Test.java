import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
        
        // Add some non-shift worker employees to ensure company is not empty
        OffShiftWorker offShiftWorker = new OffShiftWorker();
        offShiftWorker.setDepartment(Department.DELIVERY);
        offShiftWorker.setWeeklyWorkingHours(40.0);
        offShiftWorker.setHourlyRate(25.0);
        company.addEmployee(offShiftWorker);
        
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment(Department.CONTROL);
        salesPerson.setFixedSalary(3000.0);
        salesPerson.setAmountOfSales(10000.0);
        salesPerson.setCommissionPercentage(0.05);
        company.addEmployee(salesPerson);
        
        double result = company.totalHolidayPremiums();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Test Case 2: "One shift worker in the company"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 500.00"
        // Expected Output: "The total holiday premiums should be 500.00"
        
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setDepartment(Department.DELIVERY);
        shiftWorker.setWeeklyWorkingHours(40.0);
        shiftWorker.setHourlyRate(20.0);
        shiftWorker.setHolidayPremium(500.00);
        company.addEmployee(shiftWorker);
        
        double result = company.totalHolidayPremiums();
        assertEquals(500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: "Multiple shift workers with different premiums"
        // Input: "A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively"
        // Expected Output: "The total holiday premiums should be 900.00"
        
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setDepartment(Department.DELIVERY);
        shiftWorker1.setWeeklyWorkingHours(35.0);
        shiftWorker1.setHourlyRate(18.0);
        shiftWorker1.setHolidayPremium(200.00);
        company.addEmployee(shiftWorker1);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setDepartment(Department.DELIVERY);
        shiftWorker2.setWeeklyWorkingHours(40.0);
        shiftWorker2.setHourlyRate(22.0);
        shiftWorker2.setHolidayPremium(300.00);
        company.addEmployee(shiftWorker2);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setDepartment(Department.DELIVERY);
        shiftWorker3.setWeeklyWorkingHours(38.0);
        shiftWorker3.setHourlyRate(25.0);
        shiftWorker3.setHolidayPremium(400.00);
        company.addEmployee(shiftWorker3);
        
        double result = company.totalHolidayPremiums();
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: "Multiple shift workers with some zero premiums"
        // Input: "A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively"
        // Expected Output: "The total holiday premiums should be 400.00"
        
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setDepartment(Department.DELIVERY);
        shiftWorker1.setWeeklyWorkingHours(35.0);
        shiftWorker1.setHourlyRate(18.0);
        shiftWorker1.setHolidayPremium(0.0);
        company.addEmployee(shiftWorker1);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setDepartment(Department.DELIVERY);
        shiftWorker2.setWeeklyWorkingHours(40.0);
        shiftWorker2.setHourlyRate(22.0);
        shiftWorker2.setHolidayPremium(250.00);
        company.addEmployee(shiftWorker2);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setDepartment(Department.DELIVERY);
        shiftWorker3.setWeeklyWorkingHours(38.0);
        shiftWorker3.setHourlyRate(25.0);
        shiftWorker3.setHolidayPremium(0.0);
        company.addEmployee(shiftWorker3);
        
        ShiftWorker shiftWorker4 = new ShiftWorker();
        shiftWorker4.setDepartment(Department.DELIVERY);
        shiftWorker4.setWeeklyWorkingHours(42.0);
        shiftWorker4.setHourlyRate(20.0);
        shiftWorker4.setHolidayPremium(150.00);
        company.addEmployee(shiftWorker4);
        
        double result = company.totalHolidayPremiums();
        assertEquals(400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: "Single shift worker with zero premium"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 0"
        // Expected Output: "The total holiday premiums should be 0"
        
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setDepartment(Department.DELIVERY);
        shiftWorker.setWeeklyWorkingHours(40.0);
        shiftWorker.setHourlyRate(20.0);
        shiftWorker.setHolidayPremium(0.0);
        company.addEmployee(shiftWorker);
        
        double result = company.totalHolidayPremiums();
        assertEquals(0.0, result, 0.001);
    }
}