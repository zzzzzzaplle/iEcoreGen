import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        company = new Company();
        
        // Create departments
        salesDept = new Department();
        salesDept.setName("Sales");
        
        financeDept = new Department();
        financeDept.setName("Finance");
        
        itDept = new Department();
        itDept.setName("IT");
        
        rdDept = new Department();
        rdDept.setName("R&D");
        
        supportDept = new Department();
        supportDept.setName("Support");
        
        ghostDept = new Department();
        ghostDept.setName("GhostDept");
        
        // Create employees
        empE001 = new Employee();
        empE001.setEmployeeId("E001");
        empE001.setName("Employee 001");
        
        empE002 = new Employee();
        empE002.setEmployeeId("E002");
        empE002.setName("Employee 002");
        
        empE003 = new Employee();
        empE003.setEmployeeId("E003");
        empE003.setName("Employee 003");
        
        empE004 = new Employee();
        empE004.setEmployeeId("E004");
        empE004.setName("Employee 004");
        
        empE999 = new Employee();
        empE999.setEmployeeId("E999");
        empE999.setName("Employee 999");
    }
    
    @Test
    public void testCase1_AssignValidEmployeeToManagerlessDepartment() {
        // SetUp: Create Company with Department "Sales"
        company.addDepartment(salesDept);
        
        // SetUp: Add Employee "E001" to Company
        company.addEmployee(empE001);
        
        // Action: Assign "E001" as manager to "Sales"
        boolean result = company.assignManager(salesDept, empE001);
        
        // Expected Output: true
        assertTrue("Assignment should succeed", result);
        
        // Verification: Sales's manager == E001
        assertEquals("Sales department should have E001 as manager", empE001, salesDept.getManager());
        assertEquals("Employee E001 should be assigned to Sales department", salesDept, empE001.getDepartment());
    }
    
    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        company.addDepartment(financeDept);
        
        // SetUp: Create Employee "E999" (but do not add to company)
        // Employee E999 exists as object but is not added to company
        
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
        company.addDepartment(itDept);
        
        // SetUp: Add Employees "E002" and "E003" to Company
        company.addEmployee(empE002);
        company.addEmployee(empE003);
        
        // SetUp: Assign "E002" as IT manager
        boolean firstAssignment = company.assignManager(itDept, empE002);
        assertTrue("First assignment should succeed", firstAssignment);
        
        // Action: Assign "E003" as new manager
        boolean result = company.assignManager(itDept, empE003);
        
        // Expected Output: false
        assertFalse("Reassignment should fail when department already has manager", result);
        
        // Verification: IT's manager remains E002
        assertEquals("IT department manager should remain E002", empE002, itDept.getManager());
        assertNotEquals("IT department manager should not be E003", empE003, itDept.getManager());
    }
    
    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // SetUp: Create Company with Departments "R&D" and "Support"
        company.addDepartment(rdDept);
        company.addDepartment(supportDept);
        
        // SetUp: Add Employee "E004" to R&D
        company.addEmployee(empE004);
        empE004.setDepartment(rdDept); // Employee belongs to R&D department
        
        // Action: Assign "E004" as Support manager
        boolean result = company.assignManager(supportDept, empE004);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue("Assignment should succeed even if employee is from different department", result);
        
        // Verification: Support's manager is E004
        assertEquals("Support department should have E004 as manager", empE004, supportDept.getManager());
        assertEquals("Employee E004 should now be assigned to Support department", supportDept, empE004.getDepartment());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is created empty in setUp(), no departments added
        
        // SetUp: Create department "GhostDept" (but do not add to company)
        // GhostDept exists as object but is not added to company
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = company.assignManager(ghostDept, empE001);
        
        // Expected Output: false
        assertFalse("Assignment should fail for non-existent department", result);
        
        // Verification: Employee E001 should not be assigned to any department
        assertNull("Employee E001 should not be assigned to any department", empE001.getDepartment());
    }
}