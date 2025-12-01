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
        empE001.setName("E001");
        
        empE002 = new Employee();
        empE002.setName("E002");
        
        empE003 = new Employee();
        empE003.setName("E003");
        
        empE004 = new Employee();
        empE004.setName("E004");
        
        empE999 = new Employee();
        empE999.setName("E999");
    }
    
    @Test
    public void testCase1_AssignValidEmployeeToManagerlessDepartment() {
        // SetUp: Create Company with Department "Sales" and Add Employee "E001" to Company
        company.addDepartment(salesDept);
        company.addEmployee(empE001);
        
        // Action: Assign "E001" as manager to "Sales"
        boolean result = salesDept.assignManager(empE001);
        
        // Expected Output: true
        assertTrue("Valid employee should be assigned as manager", result);
        
        // Verification: Sales's manager == E001
        assertEquals("Sales department manager should be E001", empE001, salesDept.getManager());
    }
    
    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        company.addDepartment(financeDept);
        // Note: Employee E999 is created but NOT added to company
        
        // Action: Assign "E999" (non-existent) as manager
        boolean result = financeDept.assignManager(empE999);
        
        // Expected Output: false
        assertFalse("Non-existent employee should not be assigned as manager", result);
        
        // Verification: Finance do not have a manager
        assertNull("Finance department should not have a manager", financeDept.getManager());
    }
    
    @Test
    public void testCase3_ReassignToDepartmentWithExistingManager() {
        // SetUp: 
        // 1. Create Company with Department "IT"
        // 2. Add Employees "E002" and "E003" to Company
        // 3. Assign "E002" as IT manager
        company.addDepartment(itDept);
        company.addEmployee(empE002);
        company.addEmployee(empE003);
        itDept.assignManager(empE002);
        
        // Action: Assign "E003" as new manager
        boolean result = itDept.assignManager(empE003);
        
        // Expected Output: false
        assertFalse("Should not reassign manager to department with existing manager", result);
        
        // Verification: IT's manager remains E002
        assertEquals("IT department manager should remain E002", empE002, itDept.getManager());
    }
    
    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // SetUp:
        // 1. Create Company with Departments "R&D" and "Support"
        // 2. Add Employee "E004" to R&D
        company.addDepartment(rdDept);
        company.addDepartment(supportDept);
        company.addEmployee(empE004);
        empE004.setDepartmentName("R&D");
        
        // Action: Assign "E004" as Support manager
        boolean result = supportDept.assignManager(empE004);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue("Employee from different department but same company should be assignable", result);
        
        // Verification: Support's manager is E004
        assertEquals("Support department manager should be E004", empE004, supportDept.getManager());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp:
        // 1. Create empty Company
        // 2. Create department "GhostDept" (but NOT added to company)
        // Note: GhostDept is created but NOT added to company
        
        // Create and add employee to company
        company.addEmployee(empE001);
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = ghostDept.assignManager(empE001);
        
        // Expected Output: false
        // Note: The test specification expects false, but looking at the Department.assignManager method,
        // it only checks if the department already has a manager, not if the department exists in the company.
        // However, the CR states "The employee must belong to the company" and we're testing with a department
        // that doesn't exist in the company, so this should return false.
        assertFalse("Should not assign manager to non-existent department", result);
        
        // Additional verification: GhostDept should not have a manager
        assertNull("Ghost department should not have a manager", ghostDept.getManager());
    }
}