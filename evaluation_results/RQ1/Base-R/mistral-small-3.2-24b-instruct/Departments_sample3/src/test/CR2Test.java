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
        financeDept = new Department();
        itDept = new Department();
        rdDept = new Department();
        supportDept = new Department();
        
        // Create employees
        empE001 = new Employee();
        empE001.setName("E001");
        
        empE002 = new Employee();
        empE002.setName("E002");
        
        empE003 = new Employee();
        empE003.setName("E003");
        
        empE004 = new Employee();
        empE004.setName("E004");
        
        empE999 = new Employee();
        empE999.setName("E999");
    }

    @Test
    public void testCase1_AssignValidEmployeeToManagerlessDepartment() {
        // SetUp: Create Company with Department "Sales" and add Employee "E001" to Company
        company.addDepartment(salesDept);
        
        // Action: Assign "E001" as manager to "Sales"
        boolean result = salesDept.assignManager(empE001);
        
        // Expected Output: true
        assertTrue("Should successfully assign manager to department without manager", result);
        
        // Verification: Sales's manager == E001
        assertEquals("Sales department manager should be E001", empE001, salesDept.getManager());
    }

    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        company.addDepartment(financeDept);
        
        // Action: Assign "E999" (non-existent) as manager
        boolean result = financeDept.assignManager(empE999);
        
        // Expected Output: false
        assertFalse("Should fail to assign non-existent employee as manager", result);
        
        // Verification: Finance do not have a manager
        assertNull("Finance department should not have a manager", financeDept.getManager());
    }

    @Test
    public void testCase3_ReassignToDepartmentWithExistingManager() {
        // SetUp: Create Company with Department "IT" and add Employees "E002" and "E003" to Company
        company.addDepartment(itDept);
        
        // Assign "E002" as IT manager
        boolean firstAssignment = itDept.assignManager(empE002);
        assertTrue("First manager assignment should succeed", firstAssignment);
        
        // Action: Assign "E003" as new manager
        boolean secondAssignment = itDept.assignManager(empE003);
        
        // Expected Output: false
        assertFalse("Should fail to reassign manager to department with existing manager", secondAssignment);
        
        // Verification: IT's manager remains E002
        assertEquals("IT department manager should remain E002", empE002, itDept.getManager());
    }

    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // SetUp: Create Company with Departments "R&D" and "Support"
        company.addDepartment(rdDept);
        company.addDepartment(supportDept);
        
        // Action: Assign "E004" as Support manager
        boolean result = supportDept.assignManager(empE004);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue("Should successfully assign employee from different department as manager", result);
        
        // Verification: Support's manager is E004
        assertEquals("Support department manager should be E004", empE004, supportDept.getManager());
    }

    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create empty Company and create department "GhostDept"
        Department ghostDept = new Department();
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = ghostDept.assignManager(empE001);
        
        // Expected Output: false
        assertFalse("Should fail to assign manager to non-existent department in company", result);
    }
}