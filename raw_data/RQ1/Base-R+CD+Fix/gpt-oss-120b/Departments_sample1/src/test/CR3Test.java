import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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
        company = new Company();
        
        // Create departments
        legalDept = new Department();
        phantomDept = new Department();
        emptyDept = new Department();
        soloDept = new Department();
        remoteDept = new Department();
        
        // Create offices
        l1Office = new Office(1, "L1");
        l2Office = new Office(2, "L2");
        o1Office = new Office(3, "O1");
        
        // Create employees
        e101Employee = new Employee(101, "E101");
        e201Employee = new Employee(201, "E201");
    }
    
    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        company.addDepartment(legalDept);
        
        // Add Offices "L1", "L2" to Legal
        legalDept.addOffice(l1Office);
        legalDept.addOffice(l2Office);
        
        // Set "L1" as headquarter
        legalDept.assignOfficeAsHeadquarter(l1Office);
        
        // Assign "E101" as manager to Legal
        company.addEmployee(e101Employee);
        legalDept.setManager(e101Employee);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDept);
        
        // Expected Output: true
        assertTrue("Should return true when deleting existing department", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Department should be removed from company", company.containsDepartment(legalDept));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Manager employee should be removed from company", company.containsEmployee(e101Employee));
        
        // Note: Offices are not directly stored in company, they are only referenced by departments
        // When department is removed, offices become unreachable and eligible for garbage collection
    }
    
    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp
        
        // Create department "Phantom" - but not added to company
        phantomDept = new Department();
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDept);
        
        // Expected Output: false
        assertFalse("Should return false when deleting non-existent department", result);
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
        assertTrue("Should return true when deleting department with only offices", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("Department should be removed from company", company.containsDepartment(emptyDept));
    }
    
    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        company.addDepartment(soloDept);
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDept);
        
        // Expected Output: true
        assertTrue("Should return true when deleting last remaining department", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company departments should be empty", company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        company.addDepartment(remoteDept);
        
        // Add Employee "E201" to Remote
        company.addEmployee(e201Employee);
        
        // Assign "E201" as manager
        remoteDept.setManager(e201Employee);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDept);
        
        // Expected Output: true
        assertTrue("Should return true when deleting department with manager but no offices", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Manager employee should be removed from company", company.containsEmployee(e201Employee));
    }
}