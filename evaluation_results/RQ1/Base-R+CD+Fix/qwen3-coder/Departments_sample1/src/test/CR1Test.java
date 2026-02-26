import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    private Company company;
    private Department engineering;
    private Department marketing;
    private Department hr;
    private Office officeA;
    private Office officeB;
    private Office hrMain;
    private Office officeX;
    
    @Before
    public void setUp() {
        company = new Company();
        engineering = new Department();
        marketing = new Department();
        hr = new Department();
        officeA = new Office();
        officeB = new Office();
        hrMain = new Office();
        officeX = new Office();
    }
    
    @Test
    public void testCase1_AssignValidHeadquarterToDepartmentWithoutExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering", Add Office "OfficeA" to Engineering
        company.addDepartment(engineering);
        engineering.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineering.assignOfficeAsHeadquarter(officeA);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: OfficeA is the headquarter of Engineering
        assertEquals(officeA, engineering.getHeadquarter());
    }
    
    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing", Add Office "OfficeA" to Marketing
        company.addDepartment(engineering);
        company.addDepartment(marketing);
        marketing.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineering.assignOfficeAsHeadquarter(officeA);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verification: Engineering do not have headquarter
        assertNull(engineering.getHeadquarter());
    }
    
    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering", Add Offices "OfficeA" and "OfficeB" to Engineering, Set "OfficeA" as headquarter
        company.addDepartment(engineering);
        engineering.addOffice(officeA);
        engineering.addOffice(officeB);
        engineering.assignOfficeAsHeadquarter(officeA);
        
        // Action: Set "OfficeB" as new headquarter
        boolean result = engineering.assignOfficeAsHeadquarter(officeB);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verification: Engineering's headquarter remains OfficeA
        assertEquals(officeA, engineering.getHeadquarter());
    }
    
    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR", Add only Office "HR-Main" to HR
        company.addDepartment(hr);
        hr.addOffice(hrMain);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = hr.assignOfficeAsHeadquarter(hrMain);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: HR's headquarter is HR-Main
        assertEquals(hrMain, hr.getHeadquarter());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments, Create Department "ghostDept"
        Department ghostDept = new Department();
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        boolean result = ghostDept.assignOfficeAsHeadquarter(officeX);
        
        // Expected Output: false (because the department "ghostDept" does not belong to the Company)
        // Note: The test specification mentions department not belonging to company, but the actual method
        // only checks if office exists in department and if headquarter already exists
        assertFalse(result);
    }
}