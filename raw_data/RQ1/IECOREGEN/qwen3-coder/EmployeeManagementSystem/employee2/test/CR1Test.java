package edu.employee.employee2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.employee.*;
import java.util.Date;

public class CR1Test {
    
    private EmployeeFactory factory;
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize the Ecore factory and create a new company for each test
        factory = EmployeeFactory.eINSTANCE;
        company = factory.createCompany();
    }
    
    @Test
    public void testCase1_CalculateTotalSalaryForCompanyWithSingleWorker() {
        // Test Case 1: "Calculate Total Salary for Company with Single Worker"
        // Input: "A Company with 1 Worker. Worker details: weeklyWorkingHour = 40, hourlyRates = 20.00"
        // Expected Output: "The total salary of all employees in the company is 800.00 (40.00 * 20.00)."
        
        // Create a worker using Ecore factory
        OffShiftWorker worker = factory.createOffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(20.00);
        worker.setName("Worker1");
        worker.setDepartment("Production");
        worker.setBirthDate(new Date());
        worker.setSocialInsuranceNumber("123-45-6789");
        
        // Add worker to company
        company.getEmployees().add(worker);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify expected result
        assertEquals(800.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Test Case 2: "Calculate Total Salary for Company with Single SalesPerson"
        // Input: "A Company with 1 SalesPerson. SalesPerson details: salary = 3000.00, amountOfSales = 2000.00, commissionPercentage = 0.10"
        // Expected Output: "The total salary of all employees in the company is 3200.00."
        
        // Create a sales person using Ecore factory
        SalesPeople salesPerson = factory.createSalesPeople();
        salesPerson.setSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        salesPerson.setName("SalesPerson1");
        salesPerson.setDepartment("Sales");
        salesPerson.setBirthDate(new Date());
        salesPerson.setSocialInsuranceNumber("987-65-4321");
        
        // Add sales person to company
        company.getEmployees().add(salesPerson);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify expected result
        assertEquals(3200.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Test Case 3: "Calculate Total Salary for Company with Single Manager"
        // Input: "A Company with 1 Manager. Manager details: salary = 5000.00"
        // Expected Output: "The total salary of all employees in the company is 5000.00."
        
        // Create a manager using Ecore factory
        Manager manager = factory.createManager();
        manager.setSalary(5000.00);
        manager.setName("Manager1");
        manager.setDepartment("Management");
        manager.setBirthDate(new Date());
        manager.setSocialInsuranceNumber("555-55-5555");
        manager.setPosition("Senior Manager");
        
        // Add manager to company
        company.getEmployees().add(manager);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify expected result
        assertEquals(5000.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Test Case 4: "Calculate Total Salary for Company with Worker, SalesPerson and Manager"
        // Input: "A Company with 1 Worker, 1 SalesPerson and 1 Manager. Worker details: weeklyWorkingHour = 35, hourlyRates = 22.00. SalesPerson details: salary = 2800.00, amountOfSales = 1500.00, commissionPercentage = 0.15. Manager details: salary = 4800.00"
        // Expected Output: "The total salary of all employees in the company is 8595.00."
        
        // Create worker using Ecore factory
        OffShiftWorker worker = factory.createOffShiftWorker();
        worker.setWeeklyWorkingHour(35);
        worker.setHourlyRates(22.00);
        worker.setName("Worker1");
        worker.setDepartment("Production");
        worker.setBirthDate(new Date());
        worker.setSocialInsuranceNumber("111-11-1111");
        
        // Create sales person using Ecore factory
        SalesPeople salesPerson = factory.createSalesPeople();
        salesPerson.setSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        salesPerson.setName("SalesPerson1");
        salesPerson.setDepartment("Sales");
        salesPerson.setBirthDate(new Date());
        salesPerson.setSocialInsuranceNumber("222-22-2222");
        
        // Create manager using Ecore factory
        Manager manager = factory.createManager();
        manager.setSalary(4800.00);
        manager.setName("Manager1");
        manager.setDepartment("Management");
        manager.setBirthDate(new Date());
        manager.setSocialInsuranceNumber("333-33-3333");
        manager.setPosition("Department Manager");
        
        // Add all employees to company
        company.getEmployees().add(worker);
        company.getEmployees().add(salesPerson);
        company.getEmployees().add(manager);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify expected result
        assertEquals(8595.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Test Case 5: "Calculate Total Salary for Company with Multiple Workers"
        // Input: "A Company with 2 Workers, 1 shift worker. First Worker details: weeklyWorkingHour = 45, hourlyRates = 18.00. Second Worker details: weeklyWorkingHour = 38, hourlyRates = 21.00. Third shift worker details: weeklyWorkingHour = 30, hourlyRates = 24.00, holiday premiums = 200.00"
        // Expected Output: "The total salary of all employees in the company is 2528.00."
        
        // Create first worker using Ecore factory
        OffShiftWorker worker1 = factory.createOffShiftWorker();
        worker1.setWeeklyWorkingHour(45);
        worker1.setHourlyRates(18.00);
        worker1.setName("Worker1");
        worker1.setDepartment("Production");
        worker1.setBirthDate(new Date());
        worker1.setSocialInsuranceNumber("444-44-4444");
        
        // Create second worker using Ecore factory
        OffShiftWorker worker2 = factory.createOffShiftWorker();
        worker2.setWeeklyWorkingHour(38);
        worker2.setHourlyRates(21.00);
        worker2.setName("Worker2");
        worker2.setDepartment("Production");
        worker2.setBirthDate(new Date());
        worker2.setSocialInsuranceNumber("555-55-5555");
        
        // Create shift worker using Ecore factory
        ShiftWorker shiftWorker = factory.createShiftWorker();
        shiftWorker.setWeeklyWorkingHour(30);
        shiftWorker.setHourlyRates(24.00);
        shiftWorker.setHolidayPremium(200.00);
        shiftWorker.setName("ShiftWorker1");
        shiftWorker.setDepartment("Delivery");
        shiftWorker.setBirthDate(new Date());
        shiftWorker.setSocialInsuranceNumber("666-66-6666");
        
        // Add all workers to company
        company.getEmployees().add(worker1);
        company.getEmployees().add(worker2);
        company.getEmployees().add(shiftWorker);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify expected result
        assertEquals(2528.00, totalSalary, 0.01);
    }
}