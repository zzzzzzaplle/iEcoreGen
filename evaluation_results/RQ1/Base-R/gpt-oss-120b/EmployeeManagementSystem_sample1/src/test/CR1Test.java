import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR1Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Reset company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_CalculateTotalSalaryForCompanyWithSingleWorker() {
        // Create a worker with specified details
        Worker worker = new Worker();
        worker.setWeeklyWorkingHours(40.0);
        worker.setHourlyRate(20.0);
        
        // Add worker to company
        company.addEmployee(worker);
        
        // Calculate total salary and verify expected result
        double expectedSalary = 800.00; // 40.00 * 20.00
        double actualSalary = company.calculateTotalSalary();
        
        assertEquals("Total salary for single worker should be 800.00", 
                     expectedSalary, actualSalary, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Create a salesperson with specified details
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setFixedSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add salesperson to company
        company.addEmployee(salesPerson);
        
        // Calculate total salary and verify expected result
        double expectedSalary = 3200.00; // 3000.00 + (2000.00 * 0.10)
        double actualSalary = company.calculateTotalSalary();
        
        assertEquals("Total salary for single salesperson should be 3200.00", 
                     expectedSalary, actualSalary, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Create a manager with specified details
        Manager manager = new Manager();
        manager.setFixedSalary(5000.00);
        
        // Add manager to company
        company.addEmployee(manager);
        
        // Calculate total salary and verify expected result
        double expectedSalary = 5000.00;
        double actualSalary = company.calculateTotalSalary();
        
        assertEquals("Total salary for single manager should be 5000.00", 
                     expectedSalary, actualSalary, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Create worker with specified details
        Worker worker = new Worker();
        worker.setWeeklyWorkingHours(35.0);
        worker.setHourlyRate(22.0);
        
        // Create salesperson with specified details
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setFixedSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        
        // Create manager with specified details
        Manager manager = new Manager();
        manager.setFixedSalary(4800.00);
        
        // Add all employees to company
        company.addEmployee(worker);
        company.addEmployee(salesPerson);
        company.addEmployee(manager);
        
        // Calculate total salary and verify expected result
        double workerSalary = 35.0 * 22.0; // 770.00
        double salesPersonSalary = 2800.00 + (1500.00 * 0.15); // 3025.00
        double managerSalary = 4800.00;
        double expectedSalary = workerSalary + salesPersonSalary + managerSalary; // 8595.00
        
        double actualSalary = company.calculateTotalSalary();
        
        assertEquals("Total salary for worker, salesperson and manager should be 8595.00", 
                     expectedSalary, actualSalary, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Create first worker with specified details
        Worker worker1 = new Worker();
        worker1.setWeeklyWorkingHours(45.0);
        worker1.setHourlyRate(18.0);
        
        // Create second worker with specified details
        Worker worker2 = new Worker();
        worker2.setWeeklyWorkingHours(38.0);
        worker2.setHourlyRate(21.0);
        
        // Create shift worker with specified details
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHours(30.0);
        shiftWorker.setHourlyRate(24.0);
        shiftWorker.setHolidayPremium(200.0);
        
        // Add all workers to company
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(shiftWorker);
        
        // Calculate total salary and verify expected result
        double worker1Salary = 45.0 * 18.0; // 810.00
        double worker2Salary = 38.0 * 21.0; // 798.00
        double shiftWorkerSalary = (30.0 * 24.0) + 200.0; // 920.00
        double expectedSalary = worker1Salary + worker2Salary + shiftWorkerSalary; // 2528.00
        
        double actualSalary = company.calculateTotalSalary();
        
        assertEquals("Total salary for multiple workers including shift worker should be 2528.00", 
                     expectedSalary, actualSalary, 0.001);
    }
}