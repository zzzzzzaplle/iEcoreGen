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
        // Arrange: Create a company with no shift workers
        // Using other employee types to ensure the list is not empty but contains no ShiftWorkers
        List<Employee> employees = new ArrayList<>();
        
        // Add a manager
        Manager manager = new Manager();
        manager.setSalary(50000);
        employees.add(manager);
        
        // Add a sales person
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(40000);
        employees.add(salesPerson);
        
        // Add an off-shift worker
        OffShiftWorker offShiftWorker = new OffShiftWorker();
        offShiftWorker.setWeeklyWorkingHour(40);
        offShiftWorker.setHourlyRates(20);
        employees.add(offShiftWorker);
        
        company.setEmployees(employees);
        
        // Act: Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Assert: The total should be 0 since there are no shift workers
        assertEquals(0.0, result, 0.01);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Arrange: Create a company with a single shift worker having holidayPremium of 500.00
        List<Employee> employees = new ArrayList<>();
        
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setHolidayPremium(500.00);
        employees.add(shiftWorker);
        
        company.setEmployees(employees);
        
        // Act: Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Assert: The total should be 500.00
        assertEquals(500.00, result, 0.01);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Arrange: Create a company with three shift workers having premiums of 200.00, 300.00, and 400.00
        List<Employee> employees = new ArrayList<>();
        
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setHolidayPremium(200.00);
        employees.add(shiftWorker1);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setHolidayPremium(300.00);
        employees.add(shiftWorker2);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setHolidayPremium(400.00);
        employees.add(shiftWorker3);
        
        company.setEmployees(employees);
        
        // Act: Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Assert: The total should be 900.00 (200 + 300 + 400)
        assertEquals(900.00, result, 0.01);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Arrange: Create a company with four shift workers having premiums of 0, 250.00, 0, and 150.00
        List<Employee> employees = new ArrayList<>();
        
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setHolidayPremium(0.00);
        employees.add(shiftWorker1);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setHolidayPremium(250.00);
        employees.add(shiftWorker2);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setHolidayPremium(0.00);
        employees.add(shiftWorker3);
        
        ShiftWorker shiftWorker4 = new ShiftWorker();
        shiftWorker4.setHolidayPremium(150.00);
        employees.add(shiftWorker4);
        
        company.setEmployees(employees);
        
        // Act: Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Assert: The total should be 400.00 (0 + 250 + 0 + 150)
        assertEquals(400.00, result, 0.01);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Arrange: Create a company with a single shift worker having holidayPremium of 0
        List<Employee> employees = new ArrayList<>();
        
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setHolidayPremium(0.00);
        employees.add(shiftWorker);
        
        company.setEmployees(employees);
        
        // Act: Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Assert: The total should be 0
        assertEquals(0.0, result, 0.01);
    }
}