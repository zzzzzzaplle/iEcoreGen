import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR3Test {
    
    private Company company;
    private Department legalDept;
    private Department phantomDept;
    private Department emptyDept;
    private Department soloDept;
    private Department remoteDept;
    private Office l1Office;
    private Office l2Office;
    private Office o1Office;
    private Employee e101Employee;
    private Employee e201Employee;
    
    @Before
    public void setUp() {
        // Initialize fresh company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        legalDept = new Department();
        legalDept.setName("Legal");
        company.setDepartments(new ArrayList<>(Arrays.asList(legalDept)));
        
        // SetUp: Add Offices "L1", "L2" to Legal
        l1Office = new Office();
        l1Office.setLocation("L1");
        l2Office = new Office();
        l2Office.setLocation("L2");
        legalDept.setOffices(new ArrayList<>(Arrays.asList(l1Office, l2Office)));
        
        // SetUp: Set "L1" as headquarter
        legalDept.assignHeadquarters(l1Office);
        
        // SetUp: Assign "E101" as manager to Legal
        e101Employee = new Employee();
        e101Employee.setName("E101");
        company.setEmployees(new ArrayList<>(Arrays.asList(e101Employee)));
        legalDept.assignManager(e101Employee);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDept);
        
        // Expected Output: true
        assertTrue("Delete should return true for existing department", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Legal department should be removed from company", 
                   company.getDepartments().contains(legalDept));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Employee E101 should be removed from company", 
                   company.getEmployees().contains(e101Employee));
        
        // Verification: Offices "L1", "L2" removed (implicitly verified by department removal)
        assertTrue("Legal department offices should be cleared", 
                  legalDept.getOffices().isEmpty());
        assertNull("Headquarters should be cleared", legalDept.getHeadquarters());
    }
    
    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        company.setDepartments(new ArrayList<>());
        
        // SetUp: Create department "Phantom" (but not added to company)
        phantomDept = new Department();
        phantomDept.setName("Phantom");
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDept);
        
        // Expected Output: false
        assertFalse("Delete should return false for non-existent department", result);
    }
    
    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        emptyDept = new Department();
        emptyDept.setName("EmptyDept");
        company.setDepartments(new ArrayList<>(Arrays.asList(emptyDept)));
        
        // SetUp: Add Office "O1" to EmptyDept
        o1Office = new Office();
        o1Office.setLocation("O1");
        emptyDept.setOffices(new ArrayList<>(Arrays.asList(o1Office)));
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Delete should return true for department with offices only", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("EmptyDept department should be removed from company", 
                   company.getDepartments().contains(emptyDept));
    }
    
    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        soloDept = new Department();
        soloDept.setName("Solo");
        company.setDepartments(new ArrayList<>(Arrays.asList(soloDept)));
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDept);
        
        // Expected Output: true
        assertTrue("Delete should return true for last remaining department", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company departments should be empty after deleting last department", 
                  company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        remoteDept = new Department();
        remoteDept.setName("Remote");
        company.setDepartments(new ArrayList<>(Arrays.asList(remoteDept)));
        
        // SetUp: Add Employee "E201" to Remote
        e201Employee = new Employee();
        e201Employee.setName("E201");
        company.setEmployees(new ArrayList<>(Arrays.asList(e201Employee)));
        remoteDept.setEmployees(new ArrayList<>(Arrays.asList(e201Employee)));
        
        // SetUp: Assign "E201" as manager
        remoteDept.assignManager(e201Employee);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDept);
        
        // Expected Output: true
        assertTrue("Delete should return true for department with manager but no offices", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Employee E201 should be removed from company", 
                   company.getEmployees().contains(e201Employee));
    }
}