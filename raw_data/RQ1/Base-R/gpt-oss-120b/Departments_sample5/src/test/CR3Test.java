import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    
    private Company company;
    private Department legalDept;
    private Department phantomDept;
    private Department emptyDept;
    private Department soloDept;
    private Department remoteDept;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        legalDept = new Department();
        legalDept.setName("Legal");
        company.addDepartment(legalDept);
        
        // Add Offices "L1", "L2" to Legal
        Office l1 = new Office();
        l1.setIdentifier("L1");
        Office l2 = new Office();
        l2.setIdentifier("L2");
        legalDept.addOffice(l1);
        legalDept.addOffice(l2);
        
        // Set "L1" as headquarter
        company.assignHeadquarter(legalDept, l1);
        
        // Assign "E101" as manager to Legal
        Employee e101 = new Employee();
        e101.setEmployeeId("E101");
        e101.setName("John Doe");
        company.addEmployee(e101);
        company.assignManager(legalDept, e101);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDept);
        
        // Expected Output: true
        assertTrue("Delete operation should return true for existing department", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Legal department should be removed from company", company.getDepartments().contains(legalDept));
        
        // Verification: Company's employees excludes "E101"
        boolean employeeRemoved = true;
        for (Employee emp : company.getEmployees()) {
            if ("E101".equals(emp.getEmployeeId())) {
                employeeRemoved = false;
                break;
            }
        }
        assertTrue("Employee E101 should be removed from company", employeeRemoved);
        
        // Verification: Offices "L1", "L2" removed (headquarter flag should be reset)
        assertFalse("L1 should no longer be marked as headquarter", l1.isHeadquarter());
        assertEquals("Legal department should have null headquarter", null, legalDept.getHeadquarter());
    }
    
    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Create department "Phantom" but don't add it to company
        phantomDept = new Department();
        phantomDept.setName("Phantom");
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDept);
        
        // Expected Output: false
        assertFalse("Delete operation should return false for non-existent department", result);
    }
    
    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        emptyDept = new Department();
        emptyDept.setName("EmptyDept");
        company.addDepartment(emptyDept);
        
        // Add Office "O1" to EmptyDept
        Office o1 = new Office();
        o1.setIdentifier("O1");
        emptyDept.addOffice(o1);
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Delete operation should return true for department with only offices", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("EmptyDept department should be removed from company", company.getDepartments().contains(emptyDept));
    }
    
    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        soloDept = new Department();
        soloDept.setName("Solo");
        company.addDepartment(soloDept);
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDept);
        
        // Expected Output: true
        assertTrue("Delete operation should return true for last remaining department", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company should have no departments after deleting the only one", company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        remoteDept = new Department();
        remoteDept.setName("Remote");
        company.addDepartment(remoteDept);
        
        // Add Employee "E201" to Remote
        Employee e201 = new Employee();
        e201.setEmployeeId("E201");
        e201.setName("Jane Smith");
        company.addEmployee(e201);
        
        // Assign "E201" as manager
        company.assignManager(remoteDept, e201);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDept);
        
        // Expected Output: true
        assertTrue("Delete operation should return true for department with manager but no offices", result);
        
        // Verification: Company's employees excludes "E201"
        boolean employeeRemoved = true;
        for (Employee emp : company.getEmployees()) {
            if ("E201".equals(emp.getEmployeeId())) {
                employeeRemoved = false;
                break;
            }
        }
        assertTrue("Employee E201 should be removed from company", employeeRemoved);
    }
}