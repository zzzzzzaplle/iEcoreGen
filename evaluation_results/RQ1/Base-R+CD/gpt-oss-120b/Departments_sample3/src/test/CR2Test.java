import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private Company company;
    private Department sales;
    private Department finance;
    private Department it;
    private Department rnd;
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
        rnd = new Department();
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
        assertTrue("Valid employee should be assigned as manager", result);
        
        // Verification: Sales's manager == E001
        assertEquals("Sales department should have E001 as manager", e001, sales.getManager());
    }
    
    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        company.addDepartment(finance);
        // Note: Employee "E999" is created but NOT added to company
        
        // Action: Assign "E999" (non-existent) as manager
        boolean result = company.assignManager(e999, finance);
        
        // Expected Output: false
        assertFalse("Non-existent employee should not be assigned as manager", result);
        
        // Verification: Finance do not have a manager
        assertNull("Finance department should not have a manager", finance.getManager());
    }
    
    @Test
    public void testCase3_ReassignToDepartmentWithExistingManager() {
        // SetUp: Create Company with Department "IT" and add Employees "E002" and "E003" to Company
        company.addDepartment(it);
        company.addEmployee(e002);
        company.addEmployee(e003);
        
        // SetUp: Assign "E002" as IT manager
        company.assignManager(e002, it);
        
        // Action: Assign "E003" as new manager
        boolean result = company.assignManager(e003, it);
        
        // Expected Output: false
        assertFalse("Should not reassign manager to department with existing manager", result);
        
        // Verification: IT's manager remains E002
        assertEquals("IT department manager should remain E002", e002, it.getManager());
    }
    
    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // SetUp: Create Company with Departments "R&D" and "Support"
        company.addDepartment(rnd);
        company.addDepartment(support);
        
        // SetUp: Add Employee "E004" to R&D (actually to company - employees belong to company, not specific departments)
        company.addEmployee(e004);
        
        // Action: Assign "E004" as Support manager
        boolean result = company.assignManager(e004, support);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue("Employee from different department should be assignable as manager", result);
        
        // Verification: Support's manager is E004
        assertEquals("Support department should have E004 as manager", e004, support.getManager());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create empty Company
        // Note: No departments added to company
        
        // SetUp: Create department "GhostDept" (but not added to company)
        Department ghostDept = new Department();
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = company.assignManager(e001, ghostDept);
        
        // Expected Output: false
        assertFalse("Should not assign manager to non-existent department", result);
    }
}