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
    private Employee empE001;
    private Employee empE002;
    private Employee empE003;
    private Employee empE004;
    private Employee empE999;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        company = new Company();
        salesDept = new Department("Sales");
        financeDept = new Department("Finance");
        itDept = new Department("IT");
        rdDept = new Department("R&D");
        supportDept = new Department("Support");
        ghostDept = new Department("GhostDept");
        
        empE001 = new Employee("E001");
        empE002 = new Employee("E002");
        empE003 = new Employee("E003");
        empE004 = new Employee("E004");
        empE999 = new Employee("E999");
    }
    
    @Test
    public void testCase1_AssignValidEmployeeToManagerlessDepartment() {
        // SetUp: Create Company with Department "Sales"
        company.getDepartments().add(salesDept);
        // SetUp: Add Employee "E001" to Company
        company.getEmployees().add(empE001);
        
        // Action: Assign "E001" as manager to "Sales"
        boolean result = company.assignManager(salesDept, empE001);
        
        // Expected Output: true
        assertTrue("Assignment should be successful", result);
        // Verification: Sales's manager == E001
        assertEquals("Sales department should have E001 as manager", empE001, salesDept.getManager());
    }
    
    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        company.getDepartments().add(financeDept);
        // SetUp: Create Employee "E999" (but not added to company)
        // Employee E999 is created in setUp but not added to company employees list
        
        // Action: Assign "E999" (non-existent) as manager
        boolean result = company.assignManager(financeDept, empE999);
        
        // Expected Output: false
        assertFalse("Assignment should fail for non-existent employee", result);
        // Verification: Finance do not have a manager
        assertNull("Finance department should not have a manager", financeDept.getManager());
    }
    
    @Test
    public void testCase3_ReassignToDepartmentWithExistingManager() {
        // SetUp: Create Company with Department "IT"
        company.getDepartments().add(itDept);
        // SetUp: Add Employees "E002" and "E003" to Company
        company.getEmployees().add(empE002);
        company.getEmployees().add(empE003);
        // SetUp: Assign "E002" as IT manager
        company.assignManager(itDept, empE002);
        
        // Action: Assign "E003" as new manager
        boolean result = company.assignManager(itDept, empE003);
        
        // Expected Output: false
        assertFalse("Reassignment should fail when department already has manager", result);
        // Verification: IT's manager remains E002
        assertEquals("IT department manager should remain E002", empE002, itDept.getManager());
    }
    
    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // Note: The test specification mentions "Add Employee E004 to R&D" but employees are 
        // added to company, not directly to departments. Departments don't have employee lists.
        
        // SetUp: Create Company with Departments "R&D" and "Support"
        company.getDepartments().add(rdDept);
        company.getDepartments().add(supportDept);
        // SetUp: Add Employee "E004" to Company (not to specific department since departments don't track employees)
        company.getEmployees().add(empE004);
        
        // Action: Assign "E004" as Support manager
        boolean result = company.assignManager(supportDept, empE004);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue("Assignment should be successful for employee belonging to company", result);
        // Verification: Support's manager is E004
        assertEquals("Support department should have E004 as manager", empE004, supportDept.getManager());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp, no departments added
        // SetUp: Create department "GhostDept" (created in setUp but not added to company)
        
        // Create an employee to assign
        company.getEmployees().add(empE001);
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = company.assignManager(ghostDept, empE001);
        
        // Expected Output: false
        assertFalse("Assignment should fail for non-existent department", result);
    }
}