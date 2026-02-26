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
        empE001 = new Employee("E001");
        empE002 = new Employee("E002");
        empE003 = new Employee("E003");
        empE004 = new Employee("E004");
        empE999 = new Employee("E999");
    }
    
    @Test
    public void testCase1_AssignValidEmployeeToManagerlessDepartment() {
        // SetUp: Create Company with Department "Sales"
        company.addDepartment(salesDept);
        
        // SetUp: Add Employee "E001" to Company
        company.addEmployee(empE001);
        
        // Action: Assign "E001" as manager to "Sales"
        boolean result = company.assignManager(empE001, salesDept);
        
        // Expected Output: true
        assertTrue("Should successfully assign E001 as manager to Sales department", result);
        
        // Verification: Sales's manager == E001
        assertEquals("Sales department manager should be E001", empE001, salesDept.getManager());
    }
    
    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        company.addDepartment(financeDept);
        
        // SetUp: Create Employee "E999" (but don't add to company)
        // Note: empE999 is created in setUp but not added to company
        
        // Action: Assign "E999" (non-existent) as manager
        boolean result = company.assignManager(empE999, financeDept);
        
        // Expected Output: false
        assertFalse("Should fail to assign non-existent employee as manager", result);
        
        // Verification: Finance do not have a manager
        assertNull("Finance department should not have a manager", financeDept.getManager());
    }
    
    @Test
    public void testCase3_ReassignToDepartmentWithExistingManager() {
        // SetUp: Create Company with Department "IT"
        company.addDepartment(itDept);
        
        // SetUp: Add Employees "E002" and "E003" to Company
        company.addEmployee(empE002);
        company.addEmployee(empE003);
        
        // SetUp: Assign "E002" as IT manager
        company.assignManager(empE002, itDept);
        
        // Action: Assign "E003" as new manager
        boolean result = company.assignManager(empE003, itDept);
        
        // Expected Output: false
        assertFalse("Should fail to reassign manager to department with existing manager", result);
        
        // Verification: IT's manager remains E002
        assertEquals("IT department manager should remain E002", empE002, itDept.getManager());
    }
    
    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // SetUp: Create Company with Departments "R&D" and "Support"
        company.addDepartment(rdDept);
        company.addDepartment(supportDept);
        
        // SetUp: Add Employee "E004" to R&D
        // Note: Employee belongs to company, not specifically to R&D department
        company.addEmployee(empE004);
        
        // Action: Assign "E004" as Support manager
        boolean result = company.assignManager(empE004, supportDept);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue("Should successfully assign E004 as Support manager even though employee is from different department", result);
        
        // Verification: Support's manager is E004
        assertEquals("Support department manager should be E004", empE004, supportDept.getManager());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp
        
        // SetUp: Create department "GhostDept" (but don't add to company)
        Department ghostDept = new Department();
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = company.assignManager(empE001, ghostDept);
        
        // Expected Output: false
        assertFalse("Should fail to assign manager to non-existent department", result);
    }
}