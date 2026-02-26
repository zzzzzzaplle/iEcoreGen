import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
        // Initialize company and common test objects
        company = new Company();
        
        // Create departments
        salesDept = new Department();
        salesDept.setName("Sales");
        
        financeDept = new Department();
        financeDept.setName("Finance");
        
        itDept = new Department();
        itDept.setName("IT");
        
        rdDept = new Department();
        rdDept.setName("R&D");
        
        supportDept = new Department();
        supportDept.setName("Support");
        
        ghostDept = new Department();
        ghostDept.setName("GhostDept");
        
        // Create employees
        empE001 = new Employee();
        empE001.setName("Employee 001");
        empE001.setEmployeeId("E001");
        
        empE002 = new Employee();
        empE002.setName("Employee 002");
        empE002.setEmployeeId("E002");
        
        empE003 = new Employee();
        empE003.setName("Employee 003");
        empE003.setEmployeeId("E003");
        
        empE004 = new Employee();
        empE004.setName("Employee 004");
        empE004.setEmployeeId("E004");
        
        empE999 = new Employee();
        empE999.setName("Employee 999");
        empE999.setEmployeeId("E999");
    }

    @Test
    public void testCase1_AssignValidEmployeeToManagerlessDepartment() {
        // SetUp: Create Company with Department "Sales" and Add Employee "E001" to Company
        company.addDepartment(salesDept);
        company.addEmployee(empE001);
        
        // Action: Assign "E001" as manager to "Sales"
        boolean result = salesDept.assignManager(empE001);
        
        // Expected Output: true
        assertTrue("Assignment should succeed for valid employee to managerless department", result);
        
        // Verification: Sales's manager == E001
        assertEquals("Sales department manager should be E001", empE001, salesDept.getManager());
    }

    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        company.addDepartment(financeDept);
        // Note: empE999 is created but NOT added to company
        
        // Action: Assign "E999" (non-existent) as manager
        boolean result = financeDept.assignManager(empE999);
        
        // Expected Output: false
        assertFalse("Assignment should fail for non-existent employee", result);
        
        // Verification: Finance do not have a manager
        assertNull("Finance department should not have a manager", financeDept.getManager());
    }

    @Test
    public void testCase3_ReassignToDepartmentWithExistingManager() {
        // SetUp: Create Company with Department "IT", Add Employees "E002" and "E003" to Company
        company.addDepartment(itDept);
        company.addEmployee(empE002);
        company.addEmployee(empE003);
        
        // SetUp: Assign "E002" as IT manager
        itDept.assignManager(empE002);
        
        // Action: Assign "E003" as new manager
        boolean result = itDept.assignManager(empE003);
        
        // Expected Output: false
        assertFalse("Reassignment should fail when department already has a manager", result);
        
        // Verification: IT's manager remains E002
        assertEquals("IT department manager should remain E002", empE002, itDept.getManager());
    }

    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // SetUp: Create Company with Departments "R&D" and "Support"
        company.addDepartment(rdDept);
        company.addDepartment(supportDept);
        
        // SetUp: Add Employee "E004" to R&D
        company.addEmployee(empE004);
        
        // Action: Assign "E004" as Support manager
        boolean result = supportDept.assignManager(empE004);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue("Assignment should succeed for employee from different department", result);
        
        // Verification: Support's manager is E004
        assertEquals("Support department manager should be E004", empE004, supportDept.getManager());
    }

    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create empty Company and Create department "GhostDept"
        // Note: ghostDept is created but NOT added to company
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = ghostDept.assignManager(empE001);
        
        // Expected Output: false
        assertFalse("Assignment should fail for non-existent department", result);
    }
}