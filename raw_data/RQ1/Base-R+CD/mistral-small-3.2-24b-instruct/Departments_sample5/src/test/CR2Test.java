import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    private Company company;
    private Department sales;
    private Department finance;
    private Department it;
    private Department rd;
    private Department support;
    private Employee e001;
    private Employee e002;
    private Employee e003;
    private Employee e004;
    private Employee e999;

    @Before
    public void setUp() {
        // Initialize company and objects that might be used in multiple tests
        company = new Company();
        
        // Create departments
        sales = new Department();
        finance = new Department();
        it = new Department();
        rd = new Department();
        support = new Department();
        
        // Create employees
        e001 = new Employee();
        e002 = new Employee();
        e003 = new Employee();
        e004 = new Employee();
        e999 = new Employee();
    }

    @Test
    public void testCase1_AssignValidEmployeeToManagerlessDepartment() {
        // SetUp: Create Company with Department "Sales" and Add Employee "E001" to Company
        company.addDepartment(sales);
        company.addEmployee(e001);
        
        // Action: Assign "E001" as manager to "Sales"
        boolean result = company.assignManager(e001, sales);
        
        // Expected Output: true
        assertTrue("Valid employee should be assigned as manager", result);
        
        // Verification: Sales's manager == E001
        assertEquals("Manager should be E001", e001, sales.getManager());
    }

    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        company.addDepartment(finance);
        // Note: Employee e999 is not added to company (non-existent)
        
        // Action: Assign "E999" (non-existent) as manager
        boolean result = company.assignManager(e999, finance);
        
        // Expected Output: false
        assertFalse("Non-existent employee should not be assigned as manager", result);
        
        // Verification: Finance do not have a manager
        assertNull("Finance department should not have a manager", finance.getManager());
    }

    @Test
    public void testCase3_ReassignToDepartmentWithExistingManager() {
        // SetUp: 
        // 1. Create Company with Department "IT"
        company.addDepartment(it);
        // 2. Add Employees "E002" and "E003" to Company
        company.addEmployee(e002);
        company.addEmployee(e003);
        // 3. Assign "E002" as IT manager
        company.assignManager(e002, it);
        
        // Action: Assign "E003" as new manager
        boolean result = company.assignManager(e003, it);
        
        // Expected Output: false
        assertFalse("Should not reassign manager to department with existing manager", result);
        
        // Verification: IT's manager remains E002
        assertEquals("Manager should remain E002", e002, it.getManager());
    }

    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // SetUp:
        // 1. Create Company with Departments "R&D" and "Support"
        company.addDepartment(rd);
        company.addDepartment(support);
        // 2. Add Employee "E004" to R&D
        company.addEmployee(e004);
        
        // Action: Assign "E004" as Support manager
        boolean result = company.assignManager(e004, support);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue("Employee from different department should be assignable as manager", result);
        
        // Verification: Support's manager is E004
        assertEquals("Support manager should be E004", e004, support.getManager());
    }

    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create empty Company
        // Note: GhostDept department is not added to company
        
        // Create a department that won't be added to company
        Department ghostDept = new Department();
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = company.assignManager(e001, ghostDept);
        
        // Expected Output: false
        assertFalse("Should not assign manager to non-existent department", result);
    }
}