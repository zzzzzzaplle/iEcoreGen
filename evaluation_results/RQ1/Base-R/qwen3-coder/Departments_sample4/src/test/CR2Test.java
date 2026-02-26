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
    private Department ghostDept;
    
    @Before
    public void setUp() {
        company = new Company();
        company.setCompanyId("C001");
        company.setName("Test Company");
        
        // Create departments
        salesDept = new Department();
        salesDept.setDepartmentId("D001");
        salesDept.setName("Sales");
        
        financeDept = new Department();
        financeDept.setDepartmentId("D002");
        financeDept.setName("Finance");
        
        itDept = new Department();
        itDept.setDepartmentId("D003");
        itDept.setName("IT");
        
        rdDept = new Department();
        rdDept.setDepartmentId("D004");
        rdDept.setName("R&D");
        
        supportDept = new Department();
        supportDept.setDepartmentId("D005");
        supportDept.setName("Support");
        
        ghostDept = new Department();
        ghostDept.setDepartmentId("D999");
        ghostDept.setName("GhostDept");
        
        // Create employees
        emp001 = new Employee();
        emp001.setId("E001");
        emp001.setName("John Doe");
        
        emp002 = new Employee();
        emp002.setId("E002");
        emp002.setName("Jane Smith");
        
        emp003 = new Employee();
        emp003.setId("E003");
        emp003.setName("Bob Johnson");
        
        emp004 = new Employee();
        emp004.setId("E004");
        emp004.setName("Alice Brown");
        
        emp999 = new Employee();
        emp999.setId("E999");
        emp999.setName("Ghost Employee");
    }
    
    @Test
    public void testCase1_AssignValidEmployeeToManagerlessDepartment() {
        // SetUp: Create Company with Department "Sales" and Add Employee "E001" to Company
        company.getDepartments().add(salesDept);
        company.getEmployees().add(emp001);
        
        // Action: Assign "E001" as manager to "Sales"
        boolean result = company.assignManager(salesDept, emp001);
        
        // Expected Output: true
        assertTrue("Should return true when assigning valid employee to managerless department", result);
        
        // Verification: Sales's manager == E001
        assertEquals("Sales department should have E001 as manager", emp001, salesDept.getManager());
    }
    
    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        company.getDepartments().add(financeDept);
        // Note: Employee E999 is created but NOT added to company
        
        // Action: Assign "E999" (non-existent) as manager
        boolean result = company.assignManager(financeDept, emp999);
        
        // Expected Output: false
        assertFalse("Should return false when assigning non-existent employee", result);
        
        // Verification: Finance do not have a manager
        assertNull("Finance department should not have a manager", financeDept.getManager());
    }
    
    @Test
    public void testCase3_ReassignToDepartmentWithExistingManager() {
        // SetUp: Create Company with Department "IT" and Add Employees "E002" and "E003" to Company
        company.getDepartments().add(itDept);
        company.getEmployees().add(emp002);
        company.getEmployees().add(emp003);
        
        // Assign "E002" as IT manager
        company.assignManager(itDept, emp002);
        
        // Action: Assign "E003" as new manager
        boolean result = company.assignManager(itDept, emp003);
        
        // Expected Output: false
        assertFalse("Should return false when reassigning to department with existing manager", result);
        
        // Verification: IT's manager remains E002
        assertEquals("IT department manager should remain E002", emp002, itDept.getManager());
    }
    
    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // SetUp: Create Company with Departments "R&D" and "Support" and Add Employee "E004" to R&D
        company.getDepartments().add(rdDept);
        company.getDepartments().add(supportDept);
        company.getEmployees().add(emp004);
        
        // Action: Assign "E004" as Support manager
        boolean result = company.assignManager(supportDept, emp004);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue("Should return true when assigning employee from different department", result);
        
        // Verification: Support's manager is E004
        assertEquals("Support department should have E004 as manager", emp004, supportDept.getManager());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create empty Company and Create department "GhostDept"
        // Note: GhostDept is created but NOT added to company
        
        // Create and add employee E001 to company
        company.getEmployees().add(emp001);
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = company.assignManager(ghostDept, emp001);
        
        // Expected Output: false
        assertFalse("Should return false when assigning to non-existent department", result);
    }
}