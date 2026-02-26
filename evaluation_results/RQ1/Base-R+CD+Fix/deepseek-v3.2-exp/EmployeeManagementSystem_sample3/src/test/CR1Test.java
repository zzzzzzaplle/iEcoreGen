import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private Company company;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        company = new Company();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CalculateTotalSalaryForCompanyWithSingleWorker() throws Exception {
        // Test Case 1: Calculate Total Salary for Company with Single Worker
        // Input: A Company with 1 Worker. Worker details: weeklyWorkingHour = 40, hourlyRates = 20.00
        // Expected Output: The total salary of all employees in the company is 800.00 (40.00 * 20.00).
        
        // Create and configure worker
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(20.00);
        worker.setName("Worker1");
        worker.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        worker.setSocialInsuranceNumber("123-45-6789");
        worker.setDepartment(DepartmentType.PRODUCTION);
        
        // Add worker to company
        company.addEmployee(worker);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify expected output
        assertEquals(800.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() throws Exception {
        // Test Case 2: Calculate Total Salary for Company with Single SalesPerson
        // Input: A Company with 1 SalesPerson. SalesPerson details: salary = 3000.00, amountOfSales = 2000.00, commissionPercentage = 0.10
        // Expected Output: The total salary of all employees in the company is 3200.00.
        
        // Create and configure salesperson
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        salesPerson.setName("SalesPerson1");
        salesPerson.setBirthDate(dateFormat.parse("1985-05-15 00:00:00"));
        salesPerson.setSocialInsuranceNumber("987-65-4321");
        salesPerson.setDepartment(DepartmentType.CONTROL);
        
        // Add salesperson to company
        company.addEmployee(salesPerson);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify expected output
        assertEquals(3200.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() throws Exception {
        // Test Case 3: Calculate Total Salary for Company with Single Manager
        // Input: A Company with 1 Manager. Manager details: salary = 5000.00
        // Expected Output: The total salary of all employees in the company is 5000.00.
        
        // Create and configure manager
        Manager manager = new Manager();
        manager.setSalary(5000.00);
        manager.setPosition("Senior Manager");
        manager.setName("Manager1");
        manager.setBirthDate(dateFormat.parse("1975-03-20 00:00:00"));
        manager.setSocialInsuranceNumber("555-44-3333");
        manager.setDepartment(DepartmentType.DELIVERY);
        
        // Add manager to company
        company.addEmployee(manager);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify expected output
        assertEquals(5000.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() throws Exception {
        // Test Case 4: Calculate Total Salary for Company with Worker, SalesPerson and Manager
        // Input: A Company with 1 Worker, 1 SalesPerson and 1 Manager. 
        // Worker details: weeklyWorkingHour = 35, hourlyRates = 22.00. 
        // SalesPerson details: salary = 2800.00, amountOfSales = 1500.00, commissionPercentage = 0.15. 
        // Manager details: salary = 4800.00
        // Expected Output: The total salary of all employees in the company is 8595.00.
        
        // Create and configure worker
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(35);
        worker.setHourlyRates(22.00);
        worker.setName("Worker1");
        worker.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        worker.setSocialInsuranceNumber("111-22-3333");
        worker.setDepartment(DepartmentType.PRODUCTION);
        
        // Create and configure salesperson
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        salesPerson.setName("SalesPerson1");
        salesPerson.setBirthDate(dateFormat.parse("1988-07-10 00:00:00"));
        salesPerson.setSocialInsuranceNumber("444-55-6666");
        salesPerson.setDepartment(DepartmentType.CONTROL);
        
        // Create and configure manager
        Manager manager = new Manager();
        manager.setSalary(4800.00);
        manager.setPosition("Team Lead");
        manager.setName("Manager1");
        manager.setBirthDate(dateFormat.parse("1978-11-05 00:00:00"));
        manager.setSocialInsuranceNumber("777-88-9999");
        manager.setDepartment(DepartmentType.DELIVERY);
        
        // Add all employees to company
        company.addEmployee(worker);
        company.addEmployee(salesPerson);
        company.addEmployee(manager);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify expected output
        assertEquals(8595.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() throws Exception {
        // Test Case 5: Calculate Total Salary for Company with Multiple Workers
        // Input: A Company with 2 Workers, 1 shift worker. 
        // First Worker details: weeklyWorkingHour = 45, hourlyRates = 18.00. 
        // Second Worker details: weeklyWorkingHour = 38, hourlyRates = 21.00. 
        // Third shift worker details: weeklyWorkingHour = 30, hourlyRates = 24.00, holiday premiums = 200.00
        // Expected Output: The total salary of all employees in the company is 2528.00.
        
        // Create and configure first worker
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(45);
        worker1.setHourlyRates(18.00);
        worker1.setName("Worker1");
        worker1.setBirthDate(dateFormat.parse("1992-03-15 00:00:00"));
        worker1.setSocialInsuranceNumber("111-11-1111");
        worker1.setDepartment(DepartmentType.PRODUCTION);
        
        // Create and configure second worker
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(38);
        worker2.setHourlyRates(21.00);
        worker2.setName("Worker2");
        worker2.setBirthDate(dateFormat.parse("1993-06-20 00:00:00"));
        worker2.setSocialInsuranceNumber("222-22-2222");
        worker2.setDepartment(DepartmentType.PRODUCTION);
        
        // Create and configure shift worker
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHour(30);
        shiftWorker.setHourlyRates(24.00);
        shiftWorker.setHolidayPremium(200.00);
        shiftWorker.setName("ShiftWorker1");
        shiftWorker.setBirthDate(dateFormat.parse("1991-09-25 00:00:00"));
        shiftWorker.setSocialInsuranceNumber("333-33-3333");
        shiftWorker.setDepartment(DepartmentType.DELIVERY);
        
        // Add all workers to company
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(shiftWorker);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify expected output
        assertEquals(2528.00, totalSalary, 0.001);
    }
}