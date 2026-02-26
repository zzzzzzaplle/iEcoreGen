import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Company company;
    private Department salesDept;
    private Department financeDept;
    private Department itDept;
    private Department rdDept;
    private Department supportDept;
    private Department ghostDept;
    private Employee employeeE001;
    private Employee employeeE002;
    private Employee employeeE003;
    private Employee employeeE004;
    private Employee employeeE999;
    
    @Before
    public void setUp() {
        // Initialize company and common test objects
        company = new Company();
        
        // Create departments
        salesDept = new Department();
        financeDept = new Department();
        itDept = new Department();
        rdDept = new Department();
        supportDept = new Department();
        ghostDept = new Department();
        
        // Create employees
        employeeE001 = new Employee();
        employeeE002 = new Employee();
        employeeE003 = new Employee();
        employeeE004 = new Employee();
        employeeE999 = new Employee();
    }
    
    @Test
    public void testCase1_AssignValidEmployeeToManagerlessDepartment() {
        // SetUp: Create Company with Department "Sales"
        company.addDepartment(salesDept);
        
        // SetUp: Add Employee "E001" to Company
        company.addEmployee(employeeE001);
        
        // Action: Assign "E001" as manager to "Sales"
        boolean result = company.assignManager(employeeE001, salesDept);
        
        // Expected Output: true
        assertTrue("Assignment should succeed for valid employee to managerless department", result);
        
        // Verification: Sales's manager == E001
        assertEquals("Sales department should have E001 as manager", employeeE001, salesDept.getManager());
    }
    
    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        company.addDepartment(financeDept);
        
        // SetUp: Create Employee "E999" (but NOT added to company)
        // Employee E999 is created in setUp but not added to company
        
        // Action: Assign "E999" (non-existent) as manager
        boolean result = company.assignManager(employeeE999, financeDept);
        
        // Expected Output: false
        assertFalse("Assignment should fail for non-existent employee", result);
        
        // Verification: Finance do not have a manager
        assertNull("Finance department should not have a manager", financeDept.getManager());
    }
    
    @Test
    public void testCase3_ReassignToDepartmentWithExistingManager() {
        // SetUp: Create Company with Department "IT"
        company.addDepartment(itDept);
        
        // SetUp: Add Employees "E002" and "E003" to Company
        company.addEmployee(employeeE002);
        company.addEmployee(employeeE003);
        
        // SetUp: Assign "E002" as IT manager
        company.assignManager(employeeE002, itDept);
        
        // Action: Assign "E003" as new manager
        boolean result = company.assignManager(employeeE003, itDept);
        
        // Expected Output: false
        assertFalse("Reassignment should fail when department already has a manager", result);
        
        // Verification: IT's manager remains E002
        assertEquals("IT department manager should remain E002", employeeE002, itDept.getManager());
    }
    
    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // SetUp: Create Company with Departments "R&D" and "Support"
        company.addDepartment(rdDept);
        company.addDepartment(supportDept);
        
        // SetUp: Add Employee "E004" to R&D (by adding to company - employee belongs to company)
        company.addEmployee(employeeE004);
        
        // Action: Assign "E004" as Support manager
        boolean result = company.assignManager(employeeE004, supportDept);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue("Assignment should succeed for employee from different department", result);
        
        // Verification: Support's manager is E004
        assertEquals("Support department should have E004 as manager", employeeE004, supportDept.getManager());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp
        
        // SetUp: Create department "GhostDept" (but NOT added to company)
        // GhostDept is created in setUp but not added to company
        
        // Create and add employee to company
        company.addEmployee(employeeE001);
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = company.assignManager(employeeE001, ghostDept);
        
        // Expected Output: false
        assertFalse("Assignment should fail for non-existent department", result);
    }
}