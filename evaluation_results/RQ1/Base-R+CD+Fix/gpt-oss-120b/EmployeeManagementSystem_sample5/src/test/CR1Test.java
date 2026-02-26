import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class CR1Test {
    
    private Company company;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        company = new Company();
        company.setEmployees(new ArrayList<>());
        company.setDepartments(new ArrayList<>());
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CalculateTotalSalaryForCompanyWithSingleWorker() throws Exception {
        // Input: A Company with 1 Worker. Worker details: weeklyWorkingHour = 40, hourlyRates = 20.00
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(20.00);
        worker.setName("Worker1");
        worker.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        worker.setSocialInsuranceNumber("123-45-6789");
        worker.setDepartment("PRODUCTION");
        
        company.getEmployees().add(worker);
        
        // Expected Output: The total salary of all employees in the company is 800.00 (40.00 * 20.00)
        double expected = 800.00;
        double actual = company.calculateTotalEmployeeSalary();
        assertEquals(expected, actual, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() throws Exception {
        // Input: A Company with 1 SalesPerson. SalesPerson details: salary = 3000.00, amountOfSales = 2000.00, commissionPercentage = 0.10
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        salesPerson.setName("SalesPerson1");
        salesPerson.setBirthDate(dateFormat.parse("1985-05-15 00:00:00"));
        salesPerson.setSocialInsuranceNumber("987-65-4321");
        salesPerson.setDepartment("CONTROL");
        
        company.getEmployees().add(salesPerson);
        
        // Expected Output: The total salary of all employees in the company is 3200.00
        double expected = 3200.00;
        double actual = company.calculateTotalEmployeeSalary();
        assertEquals(expected, actual, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() throws Exception {
        // Input: A Company with 1 Manager. Manager details: salary = 5000.00
        Manager manager = new Manager();
        manager.setSalary(5000.00);
        manager.setName("Manager1");
        manager.setBirthDate(dateFormat.parse("1975-12-31 00:00:00"));
        manager.setSocialInsuranceNumber("555-44-3333");
        manager.setDepartment("PRODUCTION");
        manager.setPosition("Senior Manager");
        
        company.getEmployees().add(manager);
        
        // Expected Output: The total salary of all employees in the company is 5000.00
        double expected = 5000.00;
        double actual = company.calculateTotalEmployeeSalary();
        assertEquals(expected, actual, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() throws Exception {
        // Input: A Company with 1 Worker, 1 SalesPerson and 1 Manager. 
        // Worker details: weeklyWorkingHour = 35, hourlyRates = 22.00. 
        // SalesPerson details: salary = 2800.00, amountOfSales = 1500.00, commissionPercentage = 0.15. 
        // Manager details: salary = 4800.00
        
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(35);
        worker.setHourlyRates(22.00);
        worker.setName("Worker1");
        worker.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        worker.setSocialInsuranceNumber("123-45-6789");
        worker.setDepartment("PRODUCTION");
        
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        salesPerson.setName("SalesPerson1");
        salesPerson.setBirthDate(dateFormat.parse("1985-05-15 00:00:00"));
        salesPerson.setSocialInsuranceNumber("987-65-4321");
        salesPerson.setDepartment("CONTROL");
        
        Manager manager = new Manager();
        manager.setSalary(4800.00);
        manager.setName("Manager1");
        manager.setBirthDate(dateFormat.parse("1975-12-31 00:00:00"));
        manager.setSocialInsuranceNumber("555-44-3333");
        manager.setDepartment("PRODUCTION");
        manager.setPosition("Senior Manager");
        
        company.getEmployees().add(worker);
        company.getEmployees().add(salesPerson);
        company.getEmployees().add(manager);
        
        // Expected Output: The total salary of all employees in the company is 8595.00
        // Worker: 35 * 22.00 = 770.00
        // SalesPerson: 2800.00 + (1500.00 * 0.15) = 2800.00 + 225.00 = 3025.00
        // Manager: 4800.00
        // Total: 770.00 + 3025.00 + 4800.00 = 8595.00
        double expected = 8595.00;
        double actual = company.calculateTotalEmployeeSalary();
        assertEquals(expected, actual, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() throws Exception {
        // Input: A Company with 2 Workers, 1 shift worker. 
        // First Worker details: weeklyWorkingHour = 45, hourlyRates = 18.00. 
        // Second Worker details: weeklyWorkingHour = 38, hourlyRates = 21.00. 
        // Third shift worker details: weeklyWorkingHour = 30, hourlyRates = 24.00, holiday premiums = 200.00
        
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(45);
        worker1.setHourlyRates(18.00);
        worker1.setName("Worker1");
        worker1.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        worker1.setSocialInsuranceNumber("123-45-6789");
        worker1.setDepartment("PRODUCTION");
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(38);
        worker2.setHourlyRates(21.00);
        worker2.setName("Worker2");
        worker2.setBirthDate(dateFormat.parse("1992-03-15 00:00:00"));
        worker2.setSocialInsuranceNumber("234-56-7890");
        worker2.setDepartment("PRODUCTION");
        
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHour(30);
        shiftWorker.setHourlyRates(24.00);
        shiftWorker.setHolidayPremium(200.00);
        shiftWorker.setName("ShiftWorker1");
        shiftWorker.setBirthDate(dateFormat.parse("1988-07-20 00:00:00"));
        shiftWorker.setSocialInsuranceNumber("345-67-8901");
        shiftWorker.setDepartment("DELIVERY");
        
        company.getEmployees().add(worker1);
        company.getEmployees().add(worker2);
        company.getEmployees().add(shiftWorker);
        
        // Expected Output: The total salary of all employees in the company is 2528.00
        // Worker1: 45 * 18.00 = 810.00
        // Worker2: 38 * 21.00 = 798.00
        // ShiftWorker: (30 * 24.00) + 200.00 = 720.00 + 200.00 = 920.00
        // Total: 810.00 + 798.00 + 920.00 = 2528.00
        double expected = 2528.00;
        double actual = company.calculateTotalEmployeeSalary();
        assertEquals(expected, actual, 0.001);
    }
}