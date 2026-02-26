import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
        assertTrue(result);
        
        // Verification: Sales's manager == E001
        assertEquals(e001, sales.getManager());
    }
    
    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        company.addDepartment(finance);
        // Note: Employee "E999" is created but NOT added to company
        
        // Action: Assign "E999" (non-existent) as manager
        boolean result = company.assignManager(e999, finance);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verification: Finance do not have a manager
        assertNull(finance.getManager());
    }
    
    @Test
    public void testCase3_ReassignToDepartmentWithExistingManager() {
        // SetUp: Create Company with Department "IT" and Add Employees "E002" and "E003" to Company
        company.addDepartment(it);
        company.addEmployee(e002);
        company.addEmployee(e003);
        
        // Assign "E002" as IT manager
        company.assignManager(e002, it);
        
        // Action: Assign "E003" as new manager
        boolean result = company.assignManager(e003, it);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verification: IT's manager remains E002
        assertEquals(e002, it.getManager());
    }
    
    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // SetUp: Create Company with Departments "R&D" and "Support"
        company.addDepartment(rnd);
        company.addDepartment(support);
        
        // Add Employee "E004" to R&D (note: employee belongs to company, not specifically to R&D department)
        company.addEmployee(e004);
        
        // Action: Assign "E004" as Support manager
        boolean result = company.assignManager(e004, support);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue(result);
        
        // Verification: Support's manager is E004
        assertEquals(e004, support.getManager());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create empty Company
        // Create department "GhostDept" (but don't add to company)
        Department ghostDept = new Department();
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = company.assignManager(e001, ghostDept);
        
        // Expected Output: false
        assertFalse(result);
    }
}