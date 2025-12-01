import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Company company;
    private Department legalDept;
    private Department emptyDept;
    private Department remoteDept;
    private Department soloDept;
    private Department phantomDept;
    private Office l1;
    private Office l2;
    private Office o1;
    private Employee e101;
    private Employee e201;
    
    @Before
    public void setUp() {
        company = new Company();
        
        // Initialize departments
        legalDept = new Department();
        legalDept.setName("Legal");
        
        emptyDept = new Department();
        emptyDept.setName("EmptyDept");
        
        remoteDept = new Department();
        remoteDept.setName("Remote");
        
        soloDept = new Department();
        soloDept.setName("Solo");
        
        phantomDept = new Department();
        phantomDept.setName("Phantom");
        
        // Initialize offices
        l1 = new Office();
        l1.setLocation("L1");
        l1.setAddress("123 Legal St");
        
        l2 = new Office();
        l2.setLocation("L2");
        l2.setAddress("456 Legal Ave");
        
        o1 = new Office();
        o1.setLocation("O1");
        o1.setAddress("789 Empty Blvd");
        
        // Initialize employees
        e101 = new Employee();
        e101.setName("John Doe");
        e101.setEmployeeId("E101");
        e101.setPosition("Legal Manager");
        
        e201 = new Employee();
        e201.setName("Jane Smith");
        e201.setEmployeeId("E201");
        e201.setPosition("Remote Manager");
    }
    
    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        company.getDepartments().add(legalDept);
        
        // Add Offices "L1", "L2" to Legal
        legalDept.addOffice(l1);
        legalDept.addOffice(l2);
        
        // Set "L1" as headquarter
        legalDept.setHeadquarters(l1);
        
        // Assign "E101" as manager to Legal
        company.getEmployees().add(e101);
        legalDept.setManager(e101);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Legal department should be removed from company", 
                    company.getDepartments().contains(legalDept));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Employee E101 should be removed from company", 
                    company.getEmployees().contains(e101));
        
        // Verification: Offices "L1", "L2" removed
        assertTrue("Legal department offices should be empty", 
                   legalDept.getOffices().isEmpty());
    }
    
    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp()
        
        // Action: Delete "Phantom" department (not in company)
        boolean result = company.deleteDepartment(phantomDept);
        
        // Expected Output: false
        assertFalse("Non-existent department deletion should return false", result);
    }
    
    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        company.getDepartments().add(emptyDept);
        
        // Add Office "O1" to EmptyDept
        emptyDept.addOffice(o1);
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Department with only offices deletion should return true", result);
        
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
        assertTrue("Last department deletion should return true", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company departments should be empty after deleting last department", 
                   company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        company.getDepartments().add(remoteDept);
        
        // Add Employee "E201" to Remote (add to company employees)
        company.getEmployees().add(e201);
        
        // Assign "E201" as manager
        remoteDept.setManager(e201);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDept);
        
        // Expected Output: true
        assertTrue("Department with manager but no offices deletion should return true", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Employee E201 should be removed from company", 
                    company.getEmployees().contains(e201));
    }
}