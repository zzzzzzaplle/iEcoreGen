import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    private Company company;
    private Department salesDepartment;
    private Department financeDepartment;
    private Department itDepartment;
    private Department rdDepartment;
    private Department supportDepartment;
    private Department ghostDepartment;
    private Employee employeeE001;
    private Employee employeeE002;
    private Employee employeeE003;
    private Employee employeeE004;
    private Employee employeeE999;

    @Before
    public void setUp() {
        company = new Company();
        
        // Create departments
        salesDepartment = new Department();
        financeDepartment = new Department();
        itDepartment = new Department();
        rdDepartment = new Department();
        supportDepartment = new Department();
        ghostDepartment = new Department();
        
        // Create employees
        employeeE001 = new Employee();
        employeeE002 = new Employee();
        employeeE003 = new Employee();
        employeeE004 = new Employee();
        employeeE999 = new Employee();
    }

    @Test
    public void testCase1_AssignValidEmployeeToManagerlessDepartment() {
        // SetUp: Create Company with Department "Sales" and Add Employee "E001" to Company
        company.addDepartment(salesDepartment);
        company.addEmployee(employeeE001);
        
        // Action: Assign "E001" as manager to "Sales"
        boolean result = company.assignManager(employeeE001, salesDepartment);
        
        // Expected Output: true
        assertTrue("Manager assignment should be successful", result);
        
        // Verification: Sales's manager == E001
        assertEquals("Sales department manager should be E001", employeeE001, salesDepartment.getManager());
    }

    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        company.addDepartment(financeDepartment);
        // Note: Employee E999 is created but NOT added to company
        
        // Action: Assign "E999" (non-existent) as manager
        boolean result = company.assignManager(employeeE999, financeDepartment);
        
        // Expected Output: false
        assertFalse("Manager assignment should fail for non-existent employee", result);
        
        // Verification: Finance do not have a manager
        assertNull("Finance department should not have a manager", financeDepartment.getManager());
    }

    @Test
    public void testCase3_ReassignToDepartmentWithExistingManager() {
        // SetUp: Create Company with Department "IT", Add Employees "E002" and "E003" to Company
        company.addDepartment(itDepartment);
        company.addEmployee(employeeE002);
        company.addEmployee(employeeE003);
        
        // Assign "E002" as IT manager
        company.assignManager(employeeE002, itDepartment);
        
        // Action: Assign "E003" as new manager
        boolean result = company.assignManager(employeeE003, itDepartment);
        
        // Expected Output: false
        assertFalse("Manager reassignment should fail when department already has manager", result);
        
        // Verification: IT's manager remains E002
        assertEquals("IT department manager should remain E002", employeeE002, itDepartment.getManager());
    }

    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // SetUp: Create Company with Departments "R&D" and "Support", Add Employee "E004" to R&D
        company.addDepartment(rdDepartment);
        company.addDepartment(supportDepartment);
        company.addEmployee(employeeE004);
        
        // Add employee to R&D department
        employeeE004.setDepartment(rdDepartment);
        
        // Action: Assign "E004" as Support manager
        boolean result = company.assignManager(employeeE004, supportDepartment);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue("Manager assignment should be successful even if employee is from different department", result);
        
        // Verification: Support's manager is E004
        assertEquals("Support department manager should be E004", employeeE004, supportDepartment.getManager());
    }

    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create empty Company, Create department "GhostDept"
        // Note: Ghost department is created but NOT added to company
        company.addEmployee(employeeE001);
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = company.assignManager(employeeE001, ghostDepartment);
        
        // Expected Output: false
        assertFalse("Manager assignment should fail for non-existent department", result);
    }
}