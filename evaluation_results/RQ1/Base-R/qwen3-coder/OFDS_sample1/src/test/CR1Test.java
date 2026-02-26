import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    @Test
    public void testCase1_CountFullTimeEmployeesWithMixedTypes() {
        // Test Case 1: Count Full-Time Employees with Mixed Types
        // Input: Count full-time employees in a company with a mix of employee types.
        
        // SetUp:
        // 1. Create a Company named "FoodExpress"
        Company company = new Company();
        company.setName("FoodExpress");
        
        // 2. Add 3 employees:
        Employee employee1 = new Employee();
        employee1.setName("Alice");
        employee1.setType("full-time");
        
        Employee employee2 = new Employee();
        employee2.setName("Bob");
        employee2.setType("part-time");
        
        Employee employee3 = new Employee();
        employee3.setName("Charlie");
        employee3.setType("full-time");
        
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addEmployee(employee3);
        
        // 3. Call Company.countFullTimeEmployees() on "FoodExpress"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 2
        assertEquals("Should count 2 full-time employees in mixed employee types", 2, result);
    }
    
    @Test
    public void testCase2_CountFullTimeEmployeesWithNoEmployees() {
        // Test Case 2: Count Full-Time Employees with No Employees
        // Input: Count full-time employees in a company with no employees.
        
        // SetUp:
        // 1. Create a Company named "QuickDeliver" with no employees added
        Company company = new Company();
        company.setName("QuickDeliver");
        
        // Call Company.countFullTimeEmployees() on "QuickDeliver"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Should count 0 full-time employees when no employees exist", 0, result);
    }
    
    @Test
    public void testCase3_CountFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // Test Case 3: Count Full-Time Employees with Only Part-Time Employees
        // Input: Count full-time employees in a company with only part-time employees.
        
        // SetUp:
        // 1. Create a Company named "SnackNation"
        Company company = new Company();
        company.setName("SnackNation");
        
        // 2. Add 4 part-time employees:
        Employee employee1 = new Employee();
        employee1.setName("Dave");
        employee1.setType("part-time");
        
        Employee employee2 = new Employee();
        employee2.setName("Eva");
        employee2.setType("part-time");
        
        Employee employee3 = new Employee();
        employee3.setName("Frank");
        employee3.setType("part-time");
        
        Employee employee4 = new Employee();
        employee4.setName("Grace");
        employee4.setType("part-time");
        
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addEmployee(employee3);
        company.addEmployee(employee4);
        
        // Call Company.countFullTimeEmployees() on "SnackNation"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Should count 0 full-time employees when only part-time employees exist", 0, result);
    }
    
    @Test
    public void testCase4_CountFullTimeEmployeesWithSameType() {
        // Test Case 4: Count Full-Time Employees with Same Type
        // Input: Count full-time employees in a company with all employees being full-time.
        
        // SetUp:
        // 1. Create a Company named "PizzaPro"
        Company company = new Company();
        company.setName("PizzaPro");
        
        // 2. Add 5 full-time employees:
        Employee employee1 = new Employee();
        employee1.setName("Henry");
        employee1.setType("full-time");
        
        Employee employee2 = new Employee();
        employee2.setName("Isla");
        employee2.setType("full-time");
        
        Employee employee3 = new Employee();
        employee3.setName("Jack");
        employee3.setType("full-time");
        
        Employee employee4 = new Employee();
        employee4.setName("Kate");
        employee4.setType("full-time");
        
        Employee employee5 = new Employee();
        employee5.setName("Liam");
        employee5.setType("full-time");
        
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addEmployee(employee3);
        company.addEmployee(employee4);
        company.addEmployee(employee5);
        
        // Call Company.countFullTimeEmployees() on "PizzaPro"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 5
        assertEquals("Should count 5 full-time employees when all employees are full-time", 5, result);
    }
    
    @Test
    public void testCase5_CountFullTimeEmployeesWithOneOfEachType() {
        // Test Case 5: Count Full-Time Employees with One of Each Type
        // Input: Count full-time employees in a company with exactly one of each employee type.
        
        // SetUp:
        // 1. Create a Company named "TastyBites"
        Company company = new Company();
        company.setName("TastyBites");
        
        // 2. Add 2 employees:
        Employee employee1 = new Employee();
        employee1.setName("Mona");
        employee1.setType("full-time");
        
        Employee employee2 = new Employee();
        employee2.setName("Noah");
        employee2.setType("part-time");
        
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        
        // Call Company.countFullTimeEmployees() on "TastyBites"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 1
        assertEquals("Should count 1 full-time employee when one full-time and one part-time employee exist", 1, result);
    }
}