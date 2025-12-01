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
    private Department ghostDept;
    private Employee empE001;
    private Employee empE002;
    private Employee empE003;
    private Employee empE004;
    private Employee empE999;

    @Before
    public void setUp() {
        // Initialize company
        company = new Company();
        company.setName("Test Company");
        
        // Initialize departments
        salesDept = new Department();
        salesDept.setName("Sales");
        salesDept.setDepartmentId(1);
        
        financeDept = new Department();
        financeDept.setName("Finance");
        financeDept.setDepartmentId(2);
        
        itDept = new Department();
        itDept.setName("IT");
        itDept.setDepartmentId(3);
        
        rdDept = new Department();
        rdDept.setName("R&D");
        rdDept.setDepartmentId(4);
        
        supportDept = new Department();
        supportDept.setName("Support");
        supportDept.setDepartmentId(5);
        
        ghostDept = new Department();
        ghostDept.setName("GhostDept");
        ghostDept.setDepartmentId(99);
        
        // Initialize employees
        empE001 = new Employee();
        empE001.setId(1);
        empE001.setName("E001");
        
        empE002 = new Employee();
        empE002.setId(2);
        empE002.setName("E002");
        
        empE003 = new Employee();
        empE003.setId(3);
        empE003.setName("E003");
        
        empE004 = new Employee();
        empE004.setId(4);
        empE004.setName("E004");
        
        empE999 = new Employee();
        empE999.setId(999);
        empE999.setName("E999");
    }

    @Test
    public void testCase1_AssignValidEmployeeToManagerlessDepartment() {
        // SetUp: Create Company with Department "Sales" and add Employee "E001" to Company
        company.addDepartment(salesDept);
        company.addEmployee(empE001);
        
        // Action: Assign "E001" as manager to "Sales"
        boolean result = company.assignManager(salesDept, empE001);
        
        // Expected Output: true
        assertTrue("Assignment should be successful", result);
        
        // Verification: Sales's manager == E001
        assertEquals("Sales department manager should be E001", empE001, salesDept.getManager());
    }

    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        company.addDepartment(financeDept);
        // Note: Employee "E999" is created but NOT added to company
        
        // Action: Assign "E999" (non-existent) as manager
        boolean result = company.assignManager(financeDept, empE999);
        
        // Expected Output: false
        assertFalse("Assignment should fail for non-existent employee", result);
        
        // Verification: Finance do not have a manager
        assertFalse("Finance department should not have a manager", financeDept.hasManager());
        assertNull("Finance department manager should be null", financeDept.getManager());
    }

    @Test
    public void testCase3_ReassignToDepartmentWithExistingManager() {
        // SetUp: Create Company with Department "IT"
        company.addDepartment(itDept);
        
        // Add Employees "E002" and "E003" to Company
        company.addEmployee(empE002);
        company.addEmployee(empE003);
        
        // Assign "E002" as IT manager
        company.assignManager(itDept, empE002);
        
        // Action: Assign "E003" as new manager
        boolean result = company.assignManager(itDept, empE003);
        
        // Expected Output: false
        assertFalse("Reassignment should fail when department already has manager", result);
        
        // Verification: IT's manager remains E002
        assertEquals("IT department manager should remain E002", empE002, itDept.getManager());
    }

    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // SetUp: Create Company with Departments "R&D" and "Support"
        company.addDepartment(rdDept);
        company.addDepartment(supportDept);
        
        // Add Employee "E004" to R&D (by adding to company - employee belongs to company, not department)
        company.addEmployee(empE004);
        
        // Action: Assign "E004" as Support manager
        boolean result = company.assignManager(supportDept, empE004);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue("Assignment should be successful even if employee is from different department", result);
        
        // Verification: Support's manager is E004
        assertEquals("Support department manager should be E004", empE004, supportDept.getManager());
    }

    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create empty Company
        // Note: No departments added to company
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = company.assignManager(ghostDept, empE001);
        
        // Expected Output: false
        assertFalse("Assignment should fail for non-existent department", result);
        
        // Verification: GhostDept should not have a manager (though it's not in company anyway)
        assertFalse("Ghost department should not have a manager", ghostDept.hasManager());
    }
}