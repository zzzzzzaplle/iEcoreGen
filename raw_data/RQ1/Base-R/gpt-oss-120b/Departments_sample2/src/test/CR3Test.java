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
    private Office l1Office;
    private Office l2Office;
    private Office o1Office;
    private Employee e101Employee;
    private Employee e201Employee;
    
    @Before
    public void setUp() {
        company = new Company();
        
        // Initialize departments
        legalDept = new Department();
        legalDept.setId("D001");
        legalDept.setName("Legal");
        
        emptyDept = new Department();
        emptyDept.setId("D002");
        emptyDept.setName("EmptyDept");
        
        remoteDept = new Department();
        remoteDept.setId("D003");
        remoteDept.setName("Remote");
        
        soloDept = new Department();
        soloDept.setId("D004");
        soloDept.setName("Solo");
        
        phantomDept = new Department();
        phantomDept.setId("D005");
        phantomDept.setName("Phantom");
        
        // Initialize offices
        l1Office = new Office();
        l1Office.setId("O001");
        l1Office.setName("L1");
        
        l2Office = new Office();
        l2Office.setId("O002");
        l2Office.setName("L2");
        
        o1Office = new Office();
        o1Office.setId("O003");
        o1Office.setName("O1");
        
        // Initialize employees
        e101Employee = new Employee();
        e101Employee.setId("E101");
        e101Employee.setName("John Doe");
        
        e201Employee = new Employee();
        e201Employee.setId("E201");
        e201Employee.setName("Jane Smith");
    }
    
    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        company.addDepartment(legalDept);
        
        // Add Offices "L1", "L2" to Legal
        legalDept.addOffice(l1Office);
        legalDept.addOffice(l2Office);
        
        // Set "L1" as headquarter
        legalDept.setHeadquarters(l1Office);
        
        // Assign "E101" as manager to Legal
        company.addEmployee(e101Employee);
        legalDept.addEmployee(e101Employee);
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
        
        // Note: Offices are automatically removed when department is deleted
        // as they are part of the department object that gets removed
    }
    
    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp()
        
        // SetUp: Create department "Phantom" (but don't add to company)
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDept);
        
        // Expected Output: false
        assertFalse("Deleting non-existent department should return false", result);
    }
    
    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        company.addDepartment(emptyDept);
        
        // Add Office "O1" to EmptyDept
        emptyDept.addOffice(o1Office);
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("Company should not contain EmptyDept department", 
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
        
        // Add Employee "E201" to Remote
        company.addEmployee(e201Employee);
        remoteDept.addEmployee(e201Employee);
        
        // Assign "E201" as manager
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