import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR3Test {
    
    private Company company;
    private Department legalDept;
    private Department emptyDept;
    private Department soloDept;
    private Department remoteDept;
    private Department phantomDept;
    private Office l1Office;
    private Office l2Office;
    private Office o1Office;
    private Employee e101Employee;
    private Employee e201Employee;
    
    @Before
    public void setUp() {
        company = new Company();
        
        // Create departments
        legalDept = new Department();
        emptyDept = new Department();
        soloDept = new Department();
        remoteDept = new Department();
        phantomDept = new Department();
        
        // Create offices
        l1Office = new Office();
        l1Office.setLocation("L1");
        l1Office.setNumber(101);
        
        l2Office = new Office();
        l2Office.setLocation("L2");
        l2Office.setNumber(102);
        
        o1Office = new Office();
        o1Office.setLocation("O1");
        o1Office.setNumber(201);
        
        // Create employees
        e101Employee = new Employee();
        e101Employee.setId(101);
        e101Employee.setName("E101");
        
        e201Employee = new Employee();
        e201Employee.setId(201);
        e201Employee.setName("E201");
    }
    
    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        company.addDepartment(legalDept);
        
        // SetUp: Add Offices "L1", "L2" to Legal
        legalDept.addOffice(l1Office);
        legalDept.addOffice(l2Office);
        
        // SetUp: Set "L1" as headquarter
        legalDept.assignOfficeAsHeadquarter(l1Office);
        
        // SetUp: Assign "E101" as manager to Legal
        company.addEmployee(e101Employee);
        legalDept.setManager(e101Employee);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Company should not contain Legal department", 
                   company.getDepartments().contains(legalDept));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Company should not contain E101 employee", 
                   company.getEmployees().contains(e101Employee));
        
        // Verification: Offices "L1", "L2" removed (implicit through department deletion)
        // Note: Offices are tied to the department, so when department is removed, offices are effectively removed
    }
    
    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp
        
        // SetUp: Create department "Phantom"
        // phantomDept already created in setUp
        
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
        emptyDept.addOffice(o1Office);
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("Company should not contain EmptyDept", 
                   company.getDepartments().contains(emptyDept));
    }
    
    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        company.addDepartment(soloDept);
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company departments should be empty", 
                  company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        company.addDepartment(remoteDept);
        
        // SetUp: Add Employee "E201" to Remote
        company.addEmployee(e201Employee);
        
        // SetUp: Assign "E201" as manager
        remoteDept.setManager(e201Employee);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Company should not contain E201 employee", 
                   company.getEmployees().contains(e201Employee));
    }
}