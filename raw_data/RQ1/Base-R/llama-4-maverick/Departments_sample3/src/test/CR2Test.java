import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR2Test {
    
    private Company company;
    private Department salesDept;
    private Department financeDept;
    private Department itDept;
    private Department rndDept;
    private Department supportDept;
    private Employee emp001;
    private Employee emp002;
    private Employee emp003;
    private Employee emp004;
    private Employee emp999;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        company = new Company();
        salesDept = new Department();
        financeDept = new Department();
        itDept = new Department();
        rndDept = new Department();
        supportDept = new Department();
        emp001 = new Employee();
        emp002 = new Employee();
        emp003 = new Employee();
        emp004 = new Employee();
        emp999 = new Employee();
    }
    
    @Test
    public void testCase1_AssignValidEmployeeToManagerlessDepartment() {
        // SetUp: Create Company with Department "Sales" and Add Employee "E001" to Company
        company.getDepartments().add(salesDept);
        company.getEmployees().add(emp001);
        
        // Action: Assign "E001" as manager to "Sales"
        boolean result = company.assignManager(salesDept, emp001);
        
        // Expected Output: true
        assertTrue("Assignment should be successful", result);
        
        // Verification: Sales's manager == E001
        assertEquals("Sales department manager should be emp001", emp001, salesDept.getManager());
    }
    
    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        company.getDepartments().add(financeDept);
        // Note: emp999 is created but NOT added to company employees list
        
        // Action: Assign "E999" (non-existent) as manager
        boolean result = company.assignManager(financeDept, emp999);
        
        // Expected Output: false
        assertFalse("Assignment should fail for non-existent employee", result);
        
        // Verification: Finance do not have a manager
        assertNull("Finance department should not have a manager", financeDept.getManager());
    }
    
    @Test
    public void testCase3_ReassignToDepartmentWithExistingManager() {
        // SetUp: 
        // 1. Create Company with Department "IT"
        // 2. Add Employees "E002" and "E003" to Company
        // 3. Assign "E002" as IT manager
        company.getDepartments().add(itDept);
        company.getEmployees().add(emp002);
        company.getEmployees().add(emp003);
        company.assignManager(itDept, emp002); // First assignment should succeed
        
        // Action: Assign "E003" as new manager
        boolean result = company.assignManager(itDept, emp003);
        
        // Expected Output: false
        assertFalse("Reassignment should fail when department already has manager", result);
        
        // Verification: IT's manager remains E002
        assertEquals("IT department manager should remain emp002", emp002, itDept.getManager());
    }
    
    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // SetUp:
        // 1. Create Company with Departments "R&D" and "Support"
        // 2. Add Employee "E004" to R&D
        company.getDepartments().add(rndDept);
        company.getDepartments().add(supportDept);
        company.getEmployees().add(emp004);
        
        // Note: Employee is added to company, but not specifically assigned to any department
        // The test spec doesn't require adding employee to department, just to company
        
        // Action: Assign "E004" as Support manager
        boolean result = company.assignManager(supportDept, emp004);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue("Assignment should be successful for employee from different department", result);
        
        // Verification: Support's manager is E004
        assertEquals("Support department manager should be emp004", emp004, supportDept.getManager());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp:
        // 1. Create empty Company
        // 2. Create department "GhostDept" (but not added to company)
        Department ghostDept = new Department();
        company.getEmployees().add(emp001); // Add employee to company
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = company.assignManager(ghostDept, emp001);
        
        // Expected Output: false
        assertFalse("Assignment should fail for non-existent department", result);
        
        // Verification: GhostDept should not have a manager (though not strictly needed per spec)
        assertNull("Ghost department should not have a manager", ghostDept.getManager());
    }
}