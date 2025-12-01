import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Company company;
    private Department engineeringDept;
    private Department marketingDept;
    private Department hrDept;
    private Office officeA;
    private Office officeB;
    private Office hrMain;
    
    @Before
    public void setUp() {
        company = new Company();
        engineeringDept = new Department();
        marketingDept = new Department();
        hrDept = new Department();
        officeA = new Office();
        officeB = new Office();
        hrMain = new Office();
    }
    
    @Test
    public void testCase1_AssignValidHeadquarterToDepartmentWithoutExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering"
        company.addDepartment(engineeringDept);
        
        // SetUp: Add Office "OfficeA" to Engineering
        List<Office> engineeringOffices = new ArrayList<>();
        engineeringOffices.add(officeA);
        engineeringDept.setOffices(engineeringOffices);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineeringDept.assignOfficeAsHeadquarter(officeA);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: OfficeA is the headquarter of Engineering
        assertEquals(officeA, engineeringDept.getHeadquarter());
    }
    
    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing"
        company.addDepartment(engineeringDept);
        company.addDepartment(marketingDept);
        
        // SetUp: Add Office "OfficeA" to Marketing
        List<Office> marketingOffices = new ArrayList<>();
        marketingOffices.add(officeA);
        marketingDept.setOffices(marketingOffices);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineeringDept.assignOfficeAsHeadquarter(officeA);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verification: Engineering do not have headquarter
        assertNull(engineeringDept.getHeadquarter());
    }
    
    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering"
        company.addDepartment(engineeringDept);
        
        // SetUp: Add Offices "OfficeA" and "OfficeB" to Engineering
        List<Office> engineeringOffices = new ArrayList<>();
        engineeringOffices.add(officeA);
        engineeringOffices.add(officeB);
        engineeringDept.setOffices(engineeringOffices);
        
        // SetUp: Set "OfficeA" as headquarter
        engineeringDept.assignOfficeAsHeadquarter(officeA);
        
        // Action: Set "OfficeB" as new headquarter
        boolean result = engineeringDept.assignOfficeAsHeadquarter(officeB);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verification: Engineering's headquarter remains OfficeA
        assertEquals(officeA, engineeringDept.getHeadquarter());
    }
    
    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR"
        company.addDepartment(hrDept);
        
        // SetUp: Add only Office "HR-Main" to HR
        List<Office> hrOffices = new ArrayList<>();
        hrOffices.add(hrMain);
        hrDept.setOffices(hrOffices);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = hrDept.assignOfficeAsHeadquarter(hrMain);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: HR's headquarter is HR-Main
        assertEquals(hrMain, hrDept.getHeadquarter());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments
        // (company already created in setUp with no departments)
        
        // SetUp: Create Department "ghostDept"
        Department ghostDept = new Department();
        // Note: ghostDept is NOT added to company
        
        Office officeX = new Office();
        List<Office> ghostOffices = new ArrayList<>();
        ghostOffices.add(officeX);
        ghostDept.setOffices(ghostOffices);
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        boolean result = ghostDept.assignOfficeAsHeadquarter(officeX);
        
        // Expected Output: false (because the department "ghostDept" does not belong to the Company)
        // Note: The test specification mentions this, but the actual Department.assignOfficeAsHeadquarter method
        // doesn't check if the department belongs to a company, it only checks if the office belongs to the department.
        // Since we're setting up ghostDept with officeX, this should return true.
        // However, following the test specification strictly, the expected output is false.
        assertFalse(result);
    }
}