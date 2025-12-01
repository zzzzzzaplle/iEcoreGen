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
        company = new Company();
        
        // Create departments
        sales = new Department();
        finance = new Department();
        it = new Department();
        rd = new Department();
        support = new Department();
        
        // Create employees
        e001 = new Employee("E001");
        e002 = new Employee("E002");
        e003 = new Employee("E003");
        e004 = new Employee("E004");
        e999 = new Employee("E999");
    }
    
    @Test
    public void testCase1_AssignValidEmployeeToManagerlessDepartment() {
        // SetUp: Create Company with Department "Sales" and add Employee "E001" to Company
        company.addDepartment(sales);
        company.addEmployee(e001);
        
        // Action: Assign "E001" as manager to "Sales"
        boolean result = company.assignManager(e001, sales);
        
        // Expected Output: true
        assertTrue("Assignment should succeed", result);
        
        // Verification: Sales's manager == E001
        assertEquals("Sales department should have E001 as manager", e001, sales.getManager());
        assertEquals("E001 should be assigned to Sales department", sales, e001.getDepartment());
    }
    
    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        company.addDepartment(finance);
        // Note: Employee "E999" is created but NOT added to company
        
        // Action: Assign "E999" (non-existent) as manager
        boolean result = company.assignManager(e999, finance);
        
        // Expected Output: false
        assertFalse("Assignment should fail for non-existent employee", result);
        
        // Verification: Finance do not have a manager
        assertNull("Finance department should not have a manager", finance.getManager());
    }
    
    @Test
    public void testCase3_ReassignToDepartmentWithExistingManager() {
        // SetUp: Create Company with Department "IT" and add Employees "E002" and "E003" to Company
        company.addDepartment(it);
        company.addEmployee(e002);
        company.addEmployee(e003);
        
        // Assign "E002" as IT manager
        company.assignManager(e002, it);
        
        // Action: Assign "E003" as new manager
        boolean result = company.assignManager(e003, it);
        
        // Expected Output: false
        assertFalse("Assignment should fail when department already has manager", result);
        
        // Verification: IT's manager remains E002
        assertEquals("IT department manager should remain E002", e002, it.getManager());
        assertNotEquals("IT department manager should not be E003", e003, it.getManager());
    }
    
    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // SetUp: Create Company with Departments "R&D" and "Support" and add Employee "E004" to R&D
        company.addDepartment(rd);
        company.addDepartment(support);
        company.addEmployee(e004);
        
        // Add E004 to R&D department
        e004.setDepartment(rd);
        
        // Action: Assign "E004" as Support manager
        boolean result = company.assignManager(e004, support);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue("Assignment should succeed even if employee is from different department", result);
        
        // Verification: Support's manager is E004
        assertEquals("Support department should have E004 as manager", e004, support.getManager());
        assertEquals("E004 should now be assigned to Support department", support, e004.getDepartment());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create empty Company and create department "GhostDept"
        Department ghostDept = new Department();
        // Note: GhostDept is created but NOT added to company
        company.addEmployee(e001);
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = company.assignManager(e001, ghostDept);
        
        // Expected Output: false
        assertFalse("Assignment should fail for non-existent department", result);
        
        // Verification: GhostDept should not have E001 as manager
        assertNull("Ghost department should not have a manager", ghostDept.getManager());
    }
}