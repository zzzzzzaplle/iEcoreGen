import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR3Test {
    private Company company;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        company = new Company();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_singleSalespersonWithNonZeroSales() throws Exception {
        // Setup: A Company object with one SalesPerson where amountOfSales = 1000.00 and commissionPercentage = 0.10
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(1000.00);
        salesPerson.setCommissionPercentage(0.10);
        salesPerson.setName("John Doe");
        salesPerson.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        salesPerson.setSocialInsuranceNumber("123-45-6789");
        salesPerson.setDepartment("Sales");
        salesPerson.setSalary(50000.00);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        company.setEmployees(employees);
        
        // Execute: Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify: The total commission amount is 100.00
        assertEquals(100.00, result, 0.001);
    }
    
    @Test
    public void testCase2_zeroSalespersonsInCompany() {
        // Setup: A Company object with no SalesPerson objects
        List<Employee> employees = new ArrayList<>();
        company.setEmployees(employees);
        
        // Execute: Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify: The total commission amount is 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleSalespersonsWithNonZeroSales() throws Exception {
        // Setup: A Company object with two SalesPerson objects
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setAmountOfSales(2000.00);
        salesPerson1.setCommissionPercentage(0.15);
        salesPerson1.setName("Alice Smith");
        salesPerson1.setBirthDate(dateFormat.parse("1985-05-15 00:00:00"));
        salesPerson1.setSocialInsuranceNumber("987-65-4321");
        salesPerson1.setDepartment("Sales");
        salesPerson1.setSalary(60000.00);
        
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setAmountOfSales(3000.00);
        salesPerson2.setCommissionPercentage(0.20);
        salesPerson2.setName("Bob Johnson");
        salesPerson2.setBirthDate(dateFormat.parse("1992-08-20 00:00:00"));
        salesPerson2.setSocialInsuranceNumber("456-78-9012");
        salesPerson2.setDepartment("Sales");
        salesPerson2.setSalary(55000.00);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson1);
        employees.add(salesPerson2);
        company.setEmployees(employees);
        
        // Execute: Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify: The total commission amount is 900.00
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_singleSalespersonWithZeroSales() throws Exception {
        // Setup: A Company object with one SalesPerson where amountOfSales = 0 and commissionPercentage = 0.12
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(0.0);
        salesPerson.setCommissionPercentage(0.12);
        salesPerson.setName("Jane Wilson");
        salesPerson.setBirthDate(dateFormat.parse("1988-03-10 00:00:00"));
        salesPerson.setSocialInsuranceNumber("234-56-7890");
        salesPerson.setDepartment("Sales");
        salesPerson.setSalary(48000.00);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        company.setEmployees(employees);
        
        // Execute: Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify: The total commission amount is 0 * 0.12 = 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_multipleSalespersonsWithMixedSales() throws Exception {
        // Setup: A Company object with three SalesPerson objects
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setAmountOfSales(1500.00);
        salesPerson1.setCommissionPercentage(0.08);
        salesPerson1.setName("Mike Brown");
        salesPerson1.setBirthDate(dateFormat.parse("1991-07-22 00:00:00"));
        salesPerson1.setSocialInsuranceNumber("345-67-8901");
        salesPerson1.setDepartment("Sales");
        salesPerson1.setSalary(52000.00);
        
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setAmountOfSales(0.0);
        salesPerson2.setCommissionPercentage(0.10);
        salesPerson2.setName("Sarah Davis");
        salesPerson2.setBirthDate(dateFormat.parse("1987-11-05 00:00:00"));
        salesPerson2.setSocialInsuranceNumber("567-89-0123");
        salesPerson2.setDepartment("Sales");
        salesPerson2.setSalary(49000.00);
        
        SalesPeople salesPerson3 = new SalesPeople();
        salesPerson3.setAmountOfSales(4000.00);
        salesPerson3.setCommissionPercentage(0.25);
        salesPerson3.setName("Tom Wilson");
        salesPerson3.setBirthDate(dateFormat.parse("1993-02-18 00:00:00"));
        salesPerson3.setSocialInsuranceNumber("678-90-1234");
        salesPerson3.setDepartment("Sales");
        salesPerson3.setSalary(58000.00);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson1);
        employees.add(salesPerson2);
        employees.add(salesPerson3);
        company.setEmployees(employees);
        
        // Execute: Calculate total commission
        double result = company.calculateTotalSalesPeopleCommission();
        
        // Verify: The total commission amount is 1120.00
        assertEquals(1120.00, result, 0.001);
    }
}