import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    
    private Company company;
    private Department legalDept;
    private Department emptyDept;
    private Department remoteDept;
    private Department soloDept;
    private Department phantomDept;
    private Employee employeeE101;
    private Employee employeeE201;
    private Office officeL1;
    private Office officeL2;
    private Office officeO1;
    
    @Before
    public void setUp() {
        company = new Company();
        
        // Create departments
        legalDept = new Department();
        emptyDept = new Department();
        remoteDept = new Department();
        soloDept = new Department();
        phantomDept = new Department();
        
        // Create employees
        employeeE101 = new Employee();
        employeeE201 = new Employee();
        
        // Create offices
        officeL1 = new Office();
        officeL2 = new Office();
        officeO1 = new Office();
    }
    
    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        company.addDepartment(legalDept);
        
        // SetUp: Add Offices "L1", "L2" to Legal
        legalDept.getOffices().add(officeL1);
        legalDept.getOffices().add(officeL2);
        
        // SetUp: Set "L1" as headquarter
        legalDept.assignOfficeAsHeadquarter(officeL1);
        
        // SetUp: Assign "E101" as manager to Legal
        company.addEmployee(employeeE101);
        legalDept.getEmployees().add(employeeE101);
        legalDept.assignManager(employeeE101);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDept);
        
        // Expected Output: true
        assertTrue("Deleting existing department should return true", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Company should not contain deleted department", 
                   company.getDepartments().contains(legalDept));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Company should not contain employees from deleted department", 
                   company.getEmployees().contains(employeeE101));
        
        // Verification: Offices "L1", "L2" removed (implicit through department removal)
        // Note: Since offices are contained within the department, they are removed when department is deleted
    }
    
    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp()
        
        // SetUp: Create department "Phantom"
        // phantomDept is created in setUp() but not added to company
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDept);
        
        // Expected Output: false
        assertFalse("Deleting non-existent department should return false", result);
    }
    
    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        company.addDepartment(emptyDept);
        
        // SetUp: Add Office "O1" to EmptyDept
        emptyDept.getOffices().add(officeO1);
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Deleting department with only offices should return true", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("Company should not contain deleted department", 
                   company.getDepartments().contains(emptyDept));
    }
    
    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        company.addDepartment(soloDept);
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDept);
        
        // Expected Output: true
        assertTrue("Deleting last remaining department should return true", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company's departments should be empty after deleting last department", 
                  company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        company.addDepartment(remoteDept);
        
        // SetUp: Add Employee "E201" to Remote
        company.addEmployee(employeeE201);
        remoteDept.getEmployees().add(employeeE201);
        
        // SetUp: Assign "E201" as manager
        remoteDept.assignManager(employeeE201);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDept);
        
        // Expected Output: true
        assertTrue("Deleting department with manager but no offices should return true", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Company should not contain manager from deleted department", 
                   company.getEmployees().contains(employeeE201));
    }
}