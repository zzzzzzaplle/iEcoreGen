import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private Company company;
    private Department salesDept;
    private Department financeDept;
    private Department itDept;
    private Department rndDept;
    private Department supportDept;
    private Employee empE001;
    private Employee empE002;
    private Employee empE003;
    private Employee empE004;
    private Employee empE999;
    
    @Before
    public void setUp() {
        company = new Company();
        
        // Create departments
        salesDept = new Department();
        salesDept.setName("Sales");
        
        financeDept = new Department();
        financeDept.setName("Finance");
        
        itDept = new Department();
        itDept.setName("IT");
        
        rndDept = new Department();
        rndDept.setName("R&D");
        
        supportDept = new Department();
        supportDept.setName("Support");
        
        // Create employees
        empE001 = new Employee();
        empE001.setId("E001");
        empE001.setName("Employee 001");
        
        empE002 = new Employee();
        empE002.setId("E002");
        empE002.setName("Employee 002");
        
        empE003 = new Employee();
        empE003.setId("E003");
        empE003.setName("Employee 003");
        
        empE004 = new Employee();
        empE004.setId("E004");
        empE004.setName("Employee 004");
        
        empE999 = new Employee();
        empE999.setId("E999");
        empE999.setName("Employee 999");
    }
    
    @Test
    public void testCase1_AssignValidEmployeeToManagerlessDepartment() {
        // SetUp: Create Company with Department "Sales" and add Employee "E001" to Company
        company.addDepartment(salesDept);
        company.addEmployee(empE001);
        
        // Action: Assign "E001" as manager to "Sales"
        boolean result = company.assignManager(empE001, salesDept);
        
        // Expected Output: true
        assertTrue("Assignment should succeed", result);
        
        // Verification: Sales's manager == E001
        assertEquals("Sales department should have E001 as manager", empE001, salesDept.getManager());
        assertEquals("Employee E001 should be assigned to Sales department", salesDept, empE001.getDepartment());
    }
    
    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        company.addDepartment(financeDept);
        // Note: Employee E999 is created but NOT added to company
        
        // Action: Assign "E999" (non-existent) as manager
        boolean result = company.assignManager(empE999, financeDept);
        
        // Expected Output: false
        assertFalse("Assignment should fail for non-existent employee", result);
        
        // Verification: Finance do not have a manager
        assertNull("Finance department should not have a manager", financeDept.getManager());
    }
    
    @Test
    public void testCase3_ReassignToDepartmentWithExistingManager() {
        // SetUp: Create Company with Department "IT" and add Employees "E002" and "E003" to Company
        company.addDepartment(itDept);
        company.addEmployee(empE002);
        company.addEmployee(empE003);
        
        // Assign "E002" as IT manager
        company.assignManager(empE002, itDept);
        
        // Action: Assign "E003" as new manager
        boolean result = company.assignManager(empE003, itDept);
        
        // Expected Output: false
        assertFalse("Reassignment should fail when department already has manager", result);
        
        // Verification: IT's manager remains E002
        assertEquals("IT department manager should remain E002", empE002, itDept.getManager());
        assertNotEquals("IT department manager should not be E003", empE003, itDept.getManager());
    }
    
    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // SetUp: Create Company with Departments "R&D" and "Support" and add Employee "E004" to R&D
        company.addDepartment(rndDept);
        company.addDepartment(supportDept);
        company.addEmployee(empE004);
        
        // Assign employee to R&D department
        empE004.setDepartment(rndDept);
        
        // Action: Assign "E004" as Support manager
        boolean result = company.assignManager(empE004, supportDept);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue("Assignment should succeed even if employee is from different department", result);
        
        // Verification: Support's manager is E004
        assertEquals("Support department should have E004 as manager", empE004, supportDept.getManager());
        assertEquals("Employee E004 should now be assigned to Support department", supportDept, empE004.getDepartment());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create empty Company and create department "GhostDept"
        // Note: GhostDept is created but NOT added to company
        Department ghostDept = new Department();
        ghostDept.setName("GhostDept");
        
        // Create and add employee to company
        company.addEmployee(empE001);
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = company.assignManager(empE001, ghostDept);
        
        // Expected Output: false
        assertFalse("Assignment should fail for non-existent department", result);
        
        // Verification: Employee should not be assigned to the ghost department
        assertNotEquals("Employee should not be assigned to non-existent department", ghostDept, empE001.getDepartment());
    }
}