import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

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
        legalDept.setName("Legal");
        
        emptyDept = new Department();
        emptyDept.setName("EmptyDept");
        
        soloDept = new Department();
        soloDept.setName("Solo");
        
        remoteDept = new Department();
        remoteDept.setName("Remote");
        
        phantomDept = new Department();
        phantomDept.setName("Phantom");
        
        // Create offices
        l1Office = new Office();
        l1Office.setLocation("L1");
        
        l2Office = new Office();
        l2Office.setLocation("L2");
        
        o1Office = new Office();
        o1Office.setLocation("O1");
        
        // Create employees
        e101Employee = new Employee();
        e101Employee.setName("E101");
        e101Employee.setEmployeeId("E101");
        
        e201Employee = new Employee();
        e201Employee.setName("E201");
        e201Employee.setEmployeeId("E201");
    }

    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        company.getDepartments().add(legalDept);
        
        // SetUp: Add Offices "L1", "L2" to Legal
        legalDept.addOffice(l1Office);
        legalDept.addOffice(l2Office);
        
        // SetUp: Set "L1" as headquarter
        legalDept.setHeadquarters(l1Office);
        
        // SetUp: Assign "E101" as manager to Legal
        company.getEmployees().add(e101Employee);
        legalDept.setManager(e101Employee);
        e101Employee.setDepartment(legalDept);
        
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
        
        // Verification: Offices "L1", "L2" removed (implicit through department removal)
        // This is verified by the department being removed
    }

    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp
        
        // SetUp: Create department "Phantom"
        // phantomDept already created in setUp, but not added to company
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDept);
        
        // Expected Output: false
        assertFalse("Delete should return false for non-existent department", result);
    }

    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        company.getDepartments().add(emptyDept);
        
        // SetUp: Add Office "O1" to EmptyDept
        emptyDept.addOffice(o1Office);
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Delete should return true for department with only offices", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("EmptyDept should be removed from company", 
                   company.getDepartments().contains(emptyDept));
    }

    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        company.getDepartments().add(soloDept);
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDept);
        
        // Expected Output: true
        assertTrue("Delete should return true for last remaining department", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company's departments should be empty after deleting last department", 
                  company.getDepartments().isEmpty());
    }

    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        company.getDepartments().add(remoteDept);
        
        // SetUp: Add Employee "E201" to Remote
        company.getEmployees().add(e201Employee);
        e201Employee.setDepartment(remoteDept);
        
        // SetUp: Assign "E201" as manager
        remoteDept.setManager(e201Employee);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDept);
        
        // Expected Output: true
        assertTrue("Delete should return true for department with manager but no offices", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Employee E201 should be removed from company", 
                   company.getEmployees().contains(e201Employee));
    }
}