import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
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
        
        // Initialize departments
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
        
        // Initialize offices
        l1Office = new Office();
        l1Office.setLocation("L1");
        
        l2Office = new Office();
        l2Office.setLocation("L2");
        
        o1Office = new Office();
        o1Office.setLocation("O1");
        
        // Initialize employees
        e101Employee = new Employee();
        e101Employee.setName("E101");
        
        e201Employee = new Employee();
        e201Employee.setName("E201");
    }
    
    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        List<Department> departments = new ArrayList<>();
        departments.add(legalDept);
        company.setDepartments(departments);
        
        // SetUp: Add Offices "L1", "L2" to Legal
        legalDept.addOffice(l1Office);
        legalDept.addOffice(l2Office);
        
        // SetUp: Set "L1" as headquarter
        company.assignHeadquarters(legalDept, l1Office);
        
        // SetUp: Assign "E101" as manager to Legal
        List<Employee> employees = new ArrayList<>();
        employees.add(e101Employee);
        company.setEmployees(employees);
        e101Employee.setDepartment(legalDept);
        company.assignManager(legalDept, e101Employee);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Legal department should be removed from company", 
                   company.getDepartments().contains(legalDept));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Employee E101 should be removed from company", 
                   company.getEmployees().contains(e101Employee));
        
        // Note: Offices are managed by Department class, so they are automatically 
        // removed when the department is deleted
    }
    
    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        company.setDepartments(new ArrayList<>());
        company.setEmployees(new ArrayList<>());
        
        // SetUp: Create department "Phantom" (but not added to company)
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDept);
        
        // Expected Output: false
        assertFalse("Deleting non-existent department should return false", result);
    }
    
    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        List<Department> departments = new ArrayList<>();
        departments.add(emptyDept);
        company.setDepartments(departments);
        
        // SetUp: Add Office "O1" to EmptyDept
        emptyDept.addOffice(o1Office);
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("EmptyDept should be removed from company", 
                   company.getDepartments().contains(emptyDept));
    }
    
    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        List<Department> departments = new ArrayList<>();
        departments.add(soloDept);
        company.setDepartments(departments);
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company departments should be empty after deleting last department", 
                  company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        List<Department> departments = new ArrayList<>();
        departments.add(remoteDept);
        company.setDepartments(departments);
        
        // SetUp: Add Employee "E201" to Remote
        List<Employee> employees = new ArrayList<>();
        employees.add(e201Employee);
        company.setEmployees(employees);
        e201Employee.setDepartment(remoteDept);
        
        // SetUp: Assign "E201" as manager
        company.assignManager(remoteDept, e201Employee);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Employee E201 should be removed from company", 
                   company.getEmployees().contains(e201Employee));
    }
}