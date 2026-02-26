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
    private Employee e001;
    private Employee e002;
    private Employee e003;
    private Employee e004;
    private Employee e999;
    private Department ghostDept;

    @Before
    public void setUp() {
        // Initialize company and common test objects
        company = new Company();
        
        // Create departments
        salesDept = new Department();
        financeDept = new Department();
        itDept = new Department();
        rdDept = new Department();
        supportDept = new Department();
        ghostDept = new Department();
        
        // Create employees
        e001 = new Employee();
        e001.setId(1);
        e001.setName("E001");
        
        e002 = new Employee();
        e002.setId(2);
        e002.setName("E002");
        
        e003 = new Employee();
        e003.setId(3);
        e003.setName("E003");
        
        e004 = new Employee();
        e004.setId(4);
        e004.setName("E004");
        
        e999 = new Employee();
        e999.setId(999);
        e999.setName("E999");
    }

    @Test
    public void testCase1_AssignValidEmployeeToManagerlessDepartment() {
        // SetUp: Create Company with Department "Sales" and add Employee "E001" to Company
        company.addDepartment(salesDept);
        company.addEmployee(e001);
        
        // Action: Assign "E001" as manager to "Sales"
        boolean result = company.assignManager(e001, salesDept);
        
        // Expected Output: true
        assertTrue("Assignment should succeed", result);
        
        // Verification: Sales's manager == E001
        assertEquals("Sales department should have E001 as manager", e001, salesDept.getManager());
    }

    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        company.addDepartment(financeDept);
        // Note: Employee e999 is created but NOT added to company
        
        // Action: Assign "E999" (non-existent) as manager
        boolean result = company.assignManager(e999, financeDept);
        
        // Expected Output: false
        assertFalse("Assignment should fail for non-existent employee", result);
        
        // Verification: Finance do not have a manager
        assertNull("Finance department should not have a manager", financeDept.getManager());
    }

    @Test
    public void testCase3_ReassignToDepartmentWithExistingManager() {
        // SetUp: Create Company with Department "IT", add Employees "E002" and "E003" to Company
        company.addDepartment(itDept);
        company.addEmployee(e002);
        company.addEmployee(e003);
        
        // SetUp: Assign "E002" as IT manager
        company.assignManager(e002, itDept);
        
        // Action: Assign "E003" as new manager
        boolean result = company.assignManager(e003, itDept);
        
        // Expected Output: false
        assertFalse("Assignment should fail for department with existing manager", result);
        
        // Verification: IT's manager remains E002
        assertEquals("IT department manager should remain E002", e002, itDept.getManager());
    }

    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // SetUp: Create Company with Departments "R&D" and "Support"
        company.addDepartment(rdDept);
        company.addDepartment(supportDept);
        
        // SetUp: Add Employee "E004" to R&D
        // Note: Employee belongs to company, not specifically to R&D department
        company.addEmployee(e004);
        
        // Action: Assign "E004" as Support manager
        boolean result = company.assignManager(e004, supportDept);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue("Assignment should succeed for employee from different department", result);
        
        // Verification: Support's manager is E004
        assertEquals("Support department should have E004 as manager", e004, supportDept.getManager());
    }

    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create empty Company
        // Note: No departments added to company
        
        // SetUp: Create department "GhostDept" (but not added to company)
        // Note: ghostDept is created but NOT added to company
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = company.assignManager(e001, ghostDept);
        
        // Expected Output: false
        assertFalse("Assignment should fail for non-existent department", result);
    }
}