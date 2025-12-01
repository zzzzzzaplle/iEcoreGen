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
    private Employee emp001;
    private Employee emp002;
    private Employee emp003;
    private Employee emp004;
    private Employee emp999;

    @Before
    public void setUp() {
        // Initialize common test objects
        company = new Company();
        
        // Create departments
        salesDept = new Department();
        financeDept = new Department();
        itDept = new Department();
        rdDept = new Department();
        supportDept = new Department();
        Department ghostDept = new Department();
        
        // Create employees
        emp001 = new Employee("E001");
        emp002 = new Employee("E002");
        emp003 = new Employee("E003");
        emp004 = new Employee("E004");
        emp999 = new Employee("E999");
    }

    @Test
    public void testCase1_AssignValidEmployeeToManagerlessDepartment() {
        // SetUp: Create Company with Department "Sales" and Add Employee "E001" to Company
        company.addDepartment(salesDept);
        company.addEmployee(emp001);
        
        // Action: Assign "E001" as manager to "Sales"
        boolean result = company.assignManager(emp001, salesDept);
        
        // Expected Output: true
        assertTrue("Assignment should be successful", result);
        
        // Verification: Sales's manager == E001
        assertEquals("Sales department manager should be E001", emp001, salesDept.getManager());
    }

    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        company.addDepartment(financeDept);
        // Note: Employee "E999" is created but NOT added to company
        
        // Action: Assign "E999" (non-existent) as manager
        boolean result = company.assignManager(emp999, financeDept);
        
        // Expected Output: false
        assertFalse("Assignment should fail for non-existent employee", result);
        
        // Verification: Finance do not have a manager
        assertNull("Finance department should not have a manager", financeDept.getManager());
    }

    @Test
    public void testCase3_ReassignToDepartmentWithExistingManager() {
        // SetUp: Create Company with Department "IT", Add Employees "E002" and "E003" to Company
        company.addDepartment(itDept);
        company.addEmployee(emp002);
        company.addEmployee(emp003);
        
        // SetUp: Assign "E002" as IT manager
        company.assignManager(emp002, itDept);
        
        // Action: Assign "E003" as new manager
        boolean result = company.assignManager(emp003, itDept);
        
        // Expected Output: false
        assertFalse("Reassignment should fail when department already has manager", result);
        
        // Verification: IT's manager remains E002
        assertEquals("IT department manager should remain E002", emp002, itDept.getManager());
    }

    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // SetUp: Create Company with Departments "R&D" and "Support"
        company.addDepartment(rdDept);
        company.addDepartment(supportDept);
        
        // SetUp: Add Employee "E004" to R&D (via company)
        company.addEmployee(emp004);
        
        // Action: Assign "E004" as Support manager
        boolean result = company.assignManager(emp004, supportDept);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue("Assignment should be successful for employee from different department", result);
        
        // Verification: Support's manager is E004
        assertEquals("Support department manager should be E004", emp004, supportDept.getManager());
    }

    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create empty Company and Create department "GhostDept"
        Department ghostDept = new Department();
        // Note: ghostDept is NOT added to company
        company.addEmployee(emp001);
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = company.assignManager(emp001, ghostDept);
        
        // Expected Output: false
        assertFalse("Assignment should fail for non-existent department", result);
    }
}