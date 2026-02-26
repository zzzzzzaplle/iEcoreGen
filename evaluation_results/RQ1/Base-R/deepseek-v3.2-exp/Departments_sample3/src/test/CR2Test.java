import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private Company company;
    private Department salesDepartment;
    private Department financeDepartment;
    private Department itDepartment;
    private Department rdDepartment;
    private Department supportDepartment;
    private Employee employeeE001;
    private Employee employeeE002;
    private Employee employeeE003;
    private Employee employeeE004;
    private Employee employeeE999;
    private Department ghostDepartment;
    
    @Before
    public void setUp() {
        // Initialize company and common test objects
        company = new Company();
        
        // Create departments
        salesDepartment = new Department();
        salesDepartment.setName("Sales");
        
        financeDepartment = new Department();
        financeDepartment.setName("Finance");
        
        itDepartment = new Department();
        itDepartment.setName("IT");
        
        rdDepartment = new Department();
        rdDepartment.setName("R&D");
        
        supportDepartment = new Department();
        supportDepartment.setName("Support");
        
        ghostDepartment = new Department();
        ghostDepartment.setName("GhostDept");
        
        // Create employees
        employeeE001 = new Employee();
        employeeE001.setEmployeeId("E001");
        employeeE001.setName("Employee E001");
        
        employeeE002 = new Employee();
        employeeE002.setEmployeeId("E002");
        employeeE002.setName("Employee E002");
        
        employeeE003 = new Employee();
        employeeE003.setEmployeeId("E003");
        employeeE003.setName("Employee E003");
        
        employeeE004 = new Employee();
        employeeE004.setEmployeeId("E004");
        employeeE004.setName("Employee E004");
        
        employeeE999 = new Employee();
        employeeE999.setEmployeeId("E999");
        employeeE999.setName("Employee E999");
    }
    
    @Test
    public void testCase1_AssignValidEmployeeToManagerlessDepartment() {
        // SetUp: Create Company with Department "Sales" and add Employee "E001" to Company
        company.getDepartments().add(salesDepartment);
        company.getEmployees().add(employeeE001);
        
        // Action: Assign "E001" as manager to "Sales"
        boolean result = company.assignManager(salesDepartment, employeeE001);
        
        // Expected Output: true
        assertTrue("Assigning valid employee to managerless department should return true", result);
        
        // Verification: Sales's manager == E001
        assertEquals("Sales department manager should be E001", employeeE001, salesDepartment.getManager());
    }
    
    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        company.getDepartments().add(financeDepartment);
        // Note: employeeE999 is NOT added to company employees list
        
        // Action: Assign "E999" (non-existent) as manager
        boolean result = company.assignManager(financeDepartment, employeeE999);
        
        // Expected Output: false
        assertFalse("Assigning non-existent employee should return false", result);
        
        // Verification: Finance do not have a manager
        assertNull("Finance department should not have a manager", financeDepartment.getManager());
    }
    
    @Test
    public void testCase3_ReassignToDepartmentWithExistingManager() {
        // SetUp: Create Company with Department "IT" and add Employees "E002" and "E003" to Company
        company.getDepartments().add(itDepartment);
        company.getEmployees().add(employeeE002);
        company.getEmployees().add(employeeE003);
        
        // Assign "E002" as IT manager
        company.assignManager(itDepartment, employeeE002);
        
        // Action: Assign "E003" as new manager
        boolean result = company.assignManager(itDepartment, employeeE003);
        
        // Expected Output: false
        assertFalse("Reassigning to department with existing manager should return false", result);
        
        // Verification: IT's manager remains E002
        assertEquals("IT department manager should remain E002", employeeE002, itDepartment.getManager());
    }
    
    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // SetUp: Create Company with Departments "R&D" and "Support"
        company.getDepartments().add(rdDepartment);
        company.getDepartments().add(supportDepartment);
        
        // Add Employee "E004" to R&D
        employeeE004.setDepartment(rdDepartment);
        company.getEmployees().add(employeeE004);
        
        // Action: Assign "E004" as Support manager
        boolean result = company.assignManager(supportDepartment, employeeE004);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue("Assigning employee from different department should return true", result);
        
        // Verification: Support's manager is E004
        assertEquals("Support department manager should be E004", employeeE004, supportDepartment.getManager());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create empty Company
        // Note: No departments added to company
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = company.assignManager(ghostDepartment, employeeE001);
        
        // Expected Output: false
        assertFalse("Assigning to non-existent department should return false", result);
    }
}