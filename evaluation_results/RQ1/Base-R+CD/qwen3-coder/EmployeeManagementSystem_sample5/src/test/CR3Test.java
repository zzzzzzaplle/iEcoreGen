import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR3Test {
    
    private Company company;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        company = new Company();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_singleSalespersonWithNonZeroSales() throws ParseException {
        // Create a company with one salesperson
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setName("John Doe");
        salesPerson.setDepartment("Sales");
        salesPerson.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        salesPerson.setSocialInsuranceNumber("SIN123456");
        salesPerson.setAmountOfSales(1000.00);
        salesPerson.setCommissionPercentage(0.10);
        salesPerson.setSalary(2000.00);
        
        // Add salesperson to company
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        company.setEmployees(employees);
        
        // Calculate total commission
        double totalCommission = company.calculateTotalSalesPeopleCommission();
        
        // Expected: 1000.00 * 0.10 = 100.00
        assertEquals(100.00, totalCommission, 0.01);
    }
    
    @Test
    public void testCase2_zeroSalespersonsInCompany() throws ParseException {
        // Create a company with no salespeople (only other employee types)
        Manager manager = new Manager();
        manager.setName("Jane Smith");
        manager.setDepartment("Management");
        manager.setBirthDate(dateFormat.parse("1985-05-15 00:00:00"));
        manager.setSocialInsuranceNumber("SIN789012");
        manager.setSalary(5000.00);
        manager.setPosition("Senior Manager");
        
        ShiftWorker worker = new ShiftWorker();
        worker.setName("Bob Johnson");
        worker.setDepartment("Production");
        worker.setBirthDate(dateFormat.parse("1992-03-20 00:00:00"));
        worker.setSocialInsuranceNumber("SIN345678");
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(25.00);
        worker.setHolidayPremium(100.00);
        
        // Add employees to company
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(manager);
        employees.add(worker);
        company.setEmployees(employees);
        
        // Calculate total commission
        double totalCommission = company.calculateTotalSalesPeopleCommission();
        
        // Expected: 0 (no salespeople)
        assertEquals(0.0, totalCommission, 0.01);
    }
    
    @Test
    public void testCase3_multipleSalespersonsWithNonZeroSales() throws ParseException {
        // Create first salesperson
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setName("Alice Brown");
        salesPerson1.setDepartment("Sales");
        salesPerson1.setBirthDate(dateFormat.parse("1988-07-10 00:00:00"));
        salesPerson1.setSocialInsuranceNumber("SIN111111");
        salesPerson1.setAmountOfSales(2000.00);
        salesPerson1.setCommissionPercentage(0.15);
        salesPerson1.setSalary(2500.00);
        
        // Create second salesperson
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setName("Charlie Wilson");
        salesPerson2.setDepartment("Sales");
        salesPerson2.setBirthDate(dateFormat.parse("1991-11-25 00:00:00"));
        salesPerson2.setSocialInsuranceNumber("SIN222222");
        salesPerson2.setAmountOfSales(3000.00);
        salesPerson2.setCommissionPercentage(0.20);
        salesPerson2.setSalary(2800.00);
        
        // Add salespeople to company
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(salesPerson1);
        employees.add(salesPerson2);
        company.setEmployees(employees);
        
        // Calculate total commission
        double totalCommission = company.calculateTotalSalesPeopleCommission();
        
        // Expected: (2000.00 * 0.15) + (3000.00 * 0.20) = 300.00 + 600.00 = 900.00
        assertEquals(900.00, totalCommission, 0.01);
    }
    
    @Test
    public void testCase4_singleSalespersonWithZeroSales() throws ParseException {
        // Create a company with one salesperson with zero sales
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setName("David Lee");
        salesPerson.setDepartment("Sales");
        salesPerson.setBirthDate(dateFormat.parse("1987-09-30 00:00:00"));
        salesPerson.setSocialInsuranceNumber("SIN333333");
        salesPerson.setAmountOfSales(0.0);
        salesPerson.setCommissionPercentage(0.12);
        salesPerson.setSalary(2200.00);
        
        // Add salesperson to company
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        company.setEmployees(employees);
        
        // Calculate total commission
        double totalCommission = company.calculateTotalSalesPeopleCommission();
        
        // Expected: 0 * 0.12 = 0
        assertEquals(0.0, totalCommission, 0.01);
    }
    
    @Test
    public void testCase5_multipleSalespersonsWithMixedSales() throws ParseException {
        // Create first salesperson
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setName("Eva Green");
        salesPerson1.setDepartment("Sales");
        salesPerson1.setBirthDate(dateFormat.parse("1993-02-14 00:00:00"));
        salesPerson1.setSocialInsuranceNumber("SIN444444");
        salesPerson1.setAmountOfSales(1500.00);
        salesPerson1.setCommissionPercentage(0.08);
        salesPerson1.setSalary(2300.00);
        
        // Create second salesperson with zero sales
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setName("Frank Miller");
        salesPerson2.setDepartment("Sales");
        salesPerson2.setBirthDate(dateFormat.parse("1989-12-05 00:00:00"));
        salesPerson2.setSocialInsuranceNumber("SIN555555");
        salesPerson2.setAmountOfSales(0.0);
        salesPerson2.setCommissionPercentage(0.10);
        salesPerson2.setSalary(2100.00);
        
        // Create third salesperson
        SalesPeople salesPerson3 = new SalesPeople();
        salesPerson3.setName("Grace Hall");
        salesPerson3.setDepartment("Sales");
        salesPerson3.setBirthDate(dateFormat.parse("1990-08-18 00:00:00"));
        salesPerson3.setSocialInsuranceNumber("SIN666666");
        salesPerson3.setAmountOfSales(4000.00);
        salesPerson3.setCommissionPercentage(0.25);
        salesPerson3.setSalary(3000.00);
        
        // Add salespeople to company
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(salesPerson1);
        employees.add(salesPerson2);
        employees.add(salesPerson3);
        company.setEmployees(employees);
        
        // Calculate total commission
        double totalCommission = company.calculateTotalSalesPeopleCommission();
        
        // Expected: (1500.00 * 0.08) + (0 * 0.10) + (4000.00 * 0.25) = 120.00 + 0 + 1000.00 = 1120.00
        assertEquals(1120.00, totalCommission, 0.01);
    }
}