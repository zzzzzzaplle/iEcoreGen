import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private Company company;
    private Department salesDepartment;
    private Department financeDepartment;
    private Department itDepartment;
    private Department rndDepartment;
    private Department supportDepartment;
    private Employee employeeE001;
    private Employee employeeE002;
    private Employee employeeE003;
    private Employee employeeE004;
    private Employee employeeE999;
    private Department ghostDepartment;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        company = new Company();
        
        // Create departments
        salesDepartment = new Department();
        financeDepartment = new Department();
        itDepartment = new Department();
        rndDepartment = new Department();
        supportDepartment = new Department();
        ghostDepartment = new Department();
        
        // Create employees
        employeeE001 = new Employee();
        employeeE002 = new Employee();
        employeeE003 = new Employee();
        employeeE004 = new Employee();
        employeeE999 = new Employee();
    }
    
    @Test
    public void testCase1_AssignValidEmployeeToManagerlessDepartment() {
        // SetUp: Create Company with Department "Sales" and add Employee "E001" to Company
        company.addDepartment(salesDepartment);
        company.addEmployee(employeeE001);
        
        // Action: Assign "E001" as manager to "Sales"
        boolean result = company.assignManager(employeeE001, salesDepartment);
        
        // Expected Output: true
        assertTrue("Should return true when assigning valid employee to managerless department", result);
        
        // Verification: Sales's manager == E001
        assertEquals("Sales department should have E001 as manager", employeeE001, salesDepartment.getManager());
    }
    
    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        company.addDepartment(financeDepartment);
        // Note: employeeE999 is created but NOT added to company
        
        // Action: Assign "E999" (non-existent) as manager
        boolean result = company.assignManager(employeeE999, financeDepartment);
        
        // Expected Output: false
        assertFalse("Should return false when assigning non-existent employee", result);
        
        // Verification: Finance do not have a manager
        assertNull("Finance department should not have a manager", financeDepartment.getManager());
    }
    
    @Test
    public void testCase3_ReassignToDepartmentWithExistingManager() {
        // SetUp: 
        // 1. Create Company with Department "IT"
        company.addDepartment(itDepartment);
        // 2. Add Employees "E002" and "E003" to Company
        company.addEmployee(employeeE002);
        company.addEmployee(employeeE003);
        // 3. Assign "E002" as IT manager
        company.assignManager(employeeE002, itDepartment);
        
        // Action: Assign "E003" as new manager
        boolean result = company.assignManager(employeeE003, itDepartment);
        
        // Expected Output: false
        assertFalse("Should return false when reassigning to department with existing manager", result);
        
        // Verification: IT's manager remains E002
        assertEquals("IT department manager should remain E002", employeeE002, itDepartment.getManager());
    }
    
    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // SetUp:
        // 1. Create Company with Departments "R&D" and "Support"
        company.addDepartment(rndDepartment);
        company.addDepartment(supportDepartment);
        // 2. Add Employee "E004" to R&D
        company.addEmployee(employeeE004);
        
        // Action: Assign "E004" as Support manager
        boolean result = company.assignManager(employeeE004, supportDepartment);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue("Should return true when assigning employee from different department", result);
        
        // Verification: Support's manager is E004
        assertEquals("Support department should have E004 as manager", employeeE004, supportDepartment.getManager());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp:
        // 1. Create empty Company (no departments added)
        // 2. Create department "GhostDept" (created but not added to company)
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = company.assignManager(employeeE001, ghostDepartment);
        
        // Expected Output: false
        assertFalse("Should return false when assigning to non-existent department", result);
    }
}