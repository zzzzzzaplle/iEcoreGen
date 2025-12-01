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
        salesDept.setName("Sales");
        
        financeDept = new Department();
        financeDept.setName("Finance");
        
        itDept = new Department();
        itDept.setName("IT");
        
        rdDept = new Department();
        rdDept.setName("R&D");
        
        supportDept = new Department();
        supportDept.setName("Support");
        
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
        // SetUp: Create Company with Department "Sales" and Add Employee "E001" to Company
        company.getDepartments().add(salesDept);
        company.getEmployees().add(empE001);
        
        // Action: Assign "E001" as manager to "Sales"
        boolean result = company.assignManager(salesDept, empE001);
        
        // Expected Output: true
        assertTrue("Assigning valid employee to managerless department should return true", result);
        
        // Verification: Sales's manager == E001
        assertEquals("Sales department manager should be E001", empE001, salesDept.getManager());
    }

    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        company.getDepartments().add(financeDept);
        // Note: Employee E999 is created but NOT added to company
        
        // Action: Assign "E999" (non-existent) as manager
        boolean result = company.assignManager(financeDept, empE999);
        
        // Expected Output: false
        assertFalse("Assigning non-existent employee should return false", result);
        
        // Verification: Finance do not have a manager
        assertNull("Finance department should not have a manager", financeDept.getManager());
    }

    @Test
    public void testCase3_ReassignToDepartmentWithExistingManager() {
        // SetUp: Create Company with Department "IT" and Add Employees "E002" and "E003" to Company
        company.getDepartments().add(itDept);
        company.getEmployees().add(empE002);
        company.getEmployees().add(empE003);
        
        // SetUp: Assign "E002" as IT manager
        company.assignManager(itDept, empE002);
        
        // Action: Assign "E003" as new manager
        boolean result = company.assignManager(itDept, empE003);
        
        // Expected Output: false
        assertFalse("Reassigning to department with existing manager should return false", result);
        
        // Verification: IT's manager remains E002
        assertEquals("IT department manager should remain E002", empE002, itDept.getManager());
    }

    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // SetUp: Create Company with Departments "R&D" and "Support"
        company.getDepartments().add(rdDept);
        company.getDepartments().add(supportDept);
        
        // SetUp: Add Employee "E004" to R&D
        empE004.setDepartment(rdDept);
        company.getEmployees().add(empE004);
        
        // Action: Assign "E004" as Support manager
        boolean result = company.assignManager(supportDept, empE004);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue("Assigning employee from different department should return true", result);
        
        // Verification: Support's manager is E004
        assertEquals("Support department manager should be E004", empE004, supportDept.getManager());
    }

    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create empty Company (no departments added)
        // Create department "GhostDept" but don't add to company
        Department ghostDept = new Department();
        ghostDept.setName("GhostDept");
        
        // Create and add employee to company
        Employee tempEmp = new Employee();
        tempEmp.setName("E001");
        company.getEmployees().add(tempEmp);
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = company.assignManager(ghostDept, tempEmp);
        
        // Expected Output: false
        assertFalse("Assigning to non-existent department should return false", result);
    }
}