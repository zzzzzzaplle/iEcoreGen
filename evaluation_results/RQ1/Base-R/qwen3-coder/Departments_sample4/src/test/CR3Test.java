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
        // Initialize company for each test
        company = new Company();
        company.setCompanyId("COMP001");
        company.setName("Test Company");
        
        // Initialize departments
        legalDept = new Department();
        legalDept.setDepartmentId("D001");
        legalDept.setName("Legal");
        
        emptyDept = new Department();
        emptyDept.setDepartmentId("D002");
        emptyDept.setName("EmptyDept");
        
        soloDept = new Department();
        soloDept.setDepartmentId("D003");
        soloDept.setName("Solo");
        
        remoteDept = new Department();
        remoteDept.setDepartmentId("D004");
        remoteDept.setName("Remote");
        
        phantomDept = new Department();
        phantomDept.setDepartmentId("D005");
        phantomDept.setName("Phantom");
        
        // Initialize offices
        l1Office = new Office();
        l1Office.setOfficeId("L1");
        l1Office.setLocation("New York");
        
        l2Office = new Office();
        l2Office.setOfficeId("L2");
        l2Office.setLocation("Boston");
        
        o1Office = new Office();
        o1Office.setOfficeId("O1");
        o1Office.setLocation("Chicago");
        
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
        company.getDepartments().add(legalDept);
        
        // SetUp: Add Offices "L1", "L2" to Legal
        legalDept.getOffices().add(l1Office);
        legalDept.getOffices().add(l2Office);
        
        // SetUp: Set "L1" as headquarter
        l1Office.setHeadquarters(true);
        
        // SetUp: Assign "E101" as manager to Legal
        company.getEmployees().add(e101Employee);
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
        
        // Note: The specification mentions offices should be removed, but the Company class
        // doesn't maintain a separate list of offices - they are contained within departments
        // When department is removed, its offices are effectively removed from the company
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
        company.getDepartments().add(emptyDept);
        
        // SetUp: Add Office "O1" to EmptyDept
        emptyDept.getOffices().add(o1Office);
        
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
        company.getDepartments().add(soloDept);
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company's departments should be empty", 
                  company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        company.getDepartments().add(remoteDept);
        
        // SetUp: Add Employee "E201" to Remote
        company.getEmployees().add(e201Employee);
        
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