import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_AssignValidEmployeeToManagerlessDepartment() {
        // SetUp: Create Company with Department "Sales"
        Department salesDept = new Department();
        company.addDepartment(salesDept);
        
        // SetUp: Add Employee "E001" to Company
        Employee employeeE001 = new Employee("E001");
        company.addEmployee(employeeE001);
        
        // Action: Assign "E001" as manager to "Sales"
        boolean result = company.assignManager(employeeE001, salesDept);
        
        // Expected Output: true
        assertTrue("Assignment should be successful", result);
        
        // Verification: Sales's manager == E001
        assertEquals("Sales department manager should be E001", employeeE001, salesDept.getManager());
    }
    
    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        Department financeDept = new Department();
        company.addDepartment(financeDept);
        
        // SetUp: Create Employee "E999" (but not added to company)
        Employee employeeE999 = new Employee("E999");
        
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
        Department itDept = new Department();
        company.addDepartment(itDept);
        
        // SetUp: Add Employees "E002" and "E003" to Company
        Employee employeeE002 = new Employee("E002");
        Employee employeeE003 = new Employee("E003");
        company.addEmployee(employeeE002);
        company.addEmployee(employeeE003);
        
        // SetUp: Assign "E002" as IT manager
        company.assignManager(employeeE002, itDept);
        
        // Action: Assign "E003" as new manager
        boolean result = company.assignManager(employeeE003, itDept);
        
        // Expected Output: false
        assertFalse("Reassignment should fail for department with existing manager", result);
        
        // Verification: IT's manager remains E002
        assertEquals("IT department manager should remain E002", employeeE002, itDept.getManager());
    }
    
    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // SetUp: Create Company with Departments "R&D" and "Support"
        Department rdDept = new Department();
        Department supportDept = new Department();
        company.addDepartment(rdDept);
        company.addDepartment(supportDept);
        
        // SetUp: Add Employee "E004" to R&D
        Employee employeeE004 = new Employee("E004");
        company.addEmployee(employeeE004);
        
        // Action: Assign "E004" as Support manager
        boolean result = company.assignManager(employeeE004, supportDept);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue("Assignment should be successful for employee from different department", result);
        
        // Verification: Support's manager is E004
        assertEquals("Support department manager should be E004", employeeE004, supportDept.getManager());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create empty Company
        // (company is already empty from @Before setup)
        
        // SetUp: Create department "GhostDept" (but not added to company)
        Department ghostDept = new Department();
        
        // Action: Assign Employee "E001" to "GhostDept"
        Employee employeeE001 = new Employee("E001");
        company.addEmployee(employeeE001);
        
        boolean result = company.assignManager(employeeE001, ghostDept);
        
        // Expected Output: false
        assertFalse("Assignment should fail for non-existent department", result);
    }
}