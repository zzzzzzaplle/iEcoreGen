import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR1Test {
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_CalculateTotalSalaryForCompanyWithSingleWorker() {
        // Setup: Create a company with 1 Worker
        Worker worker = new Worker();
        worker.setWeeklyWorkingHours(40);
        worker.setHourlyRate(20.00);
        company.addEmployee(worker);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalSalary();
        
        // Verify: Total salary should be 800.00 (40 * 20.00)
        assertEquals(800.00, result, 0.01);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Setup: Create a company with 1 SalesPerson
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setFixedSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        company.addEmployee(salesPerson);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalSalary();
        
        // Verify: Total salary should be 3200.00 (3000 + 2000 * 0.10)
        assertEquals(3200.00, result, 0.01);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Setup: Create a company with 1 Manager
        Manager manager = new Manager();
        manager.setFixedSalary(5000.00);
        company.addEmployee(manager);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalSalary();
        
        // Verify: Total salary should be 5000.00
        assertEquals(5000.00, result, 0.01);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Setup: Create a company with 1 Worker, 1 SalesPerson, and 1 Manager
        Worker worker = new Worker();
        worker.setWeeklyWorkingHours(35);
        worker.setHourlyRate(22.00);
        company.addEmployee(worker);
        
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setFixedSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        company.addEmployee(salesPerson);
        
        Manager manager = new Manager();
        manager.setFixedSalary(4800.00);
        company.addEmployee(manager);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalSalary();
        
        // Verify: Total salary should be 8595.00 (770 + 3025 + 4800)
        assertEquals(8595.00, result, 0.01);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Setup: Create a company with 3 Workers (2 regular, 1 shift worker)
        Worker worker1 = new Worker();
        worker1.setWeeklyWorkingHours(45);
        worker1.setHourlyRate(18.00);
        company.addEmployee(worker1);
        
        Worker worker2 = new Worker();
        worker2.setWeeklyWorkingHours(38);
        worker2.setHourlyRate(21.00);
        company.addEmployee(worker2);
        
        Worker shiftWorker = new Worker();
        shiftWorker.setWeeklyWorkingHours(30);
        shiftWorker.setHourlyRate(24.00);
        shiftWorker.setShiftWorker(true);
        shiftWorker.setHolidayPremium(200.00);
        company.addEmployee(shiftWorker);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalSalary();
        
        // Verify: Total salary should be 2528.00 (810 + 798 + 920)
        assertEquals(2528.00, result, 0.01);
    }
}