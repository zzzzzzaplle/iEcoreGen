import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR1Test {
    
    private Company company;
    private Department engineering;
    private Department marketing;
    private Department hr;
    private Office officeA;
    private Office officeB;
    private Office hrMain;
    
    @Before
    public void setUp() {
        company = new Company();
        engineering = new Department();
        engineering.setName("Engineering");
        marketing = new Department();
        marketing.setName("Marketing");
        hr = new Department();
        hr.setName("HR");
        
        officeA = new Office();
        officeA.setLocation("OfficeA");
        officeB = new Office();
        officeB.setLocation("OfficeB");
        hrMain = new Office();
        hrMain.setLocation("HR-Main");
    }
    
    @Test
    public void testCase1_AssignValidHeadquarterToDepartmentWithoutExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering" and Add Office "OfficeA" to Engineering
        company.getDepartments().add(engineering);
        engineering.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = company.assignHeadquarters(engineering, officeA);
        
        // Expected Output: true
        assertTrue("Should return true when assigning valid headquarter", result);
        
        // Verification: OfficeA is the headquarter of Engineering
        assertEquals("Engineering's headquarter should be OfficeA", officeA, engineering.getHeadquarters());
    }
    
    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing", Add Office "OfficeA" to Marketing
        company.getDepartments().add(engineering);
        company.getDepartments().add(marketing);
        marketing.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = company.assignHeadquarters(engineering, officeA);
        
        // Expected Output: false
        assertFalse("Should return false when office doesn't belong to department", result);
        
        // Verification: Engineering do not have headquarter
        assertNull("Engineering should not have a headquarter", engineering.getHeadquarters());
    }
    
    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering", Add Offices "OfficeA" and "OfficeB" to Engineering, Set "OfficeA" as headquarter
        company.getDepartments().add(engineering);
        engineering.addOffice(officeA);
        engineering.addOffice(officeB);
        company.assignHeadquarters(engineering, officeA);
        
        // Action: Set "OfficeB" as new headquarter
        boolean result = company.assignHeadquarters(engineering, officeB);
        
        // Expected Output: false
        assertFalse("Should return false when reassigning existing headquarter", result);
        
        // Verification: Engineering's headquarter remains OfficeA
        assertEquals("Engineering's headquarter should remain OfficeA", officeA, engineering.getHeadquarters());
    }
    
    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR", Add only Office "HR-Main" to HR
        company.getDepartments().add(hr);
        hr.addOffice(hrMain);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = company.assignHeadquarters(hr, hrMain);
        
        // Expected Output: true
        assertTrue("Should return true when assigning headquarter to department with single office", result);
        
        // Verification: HR's headquarter is HR-Main
        assertEquals("HR's headquarter should be HR-Main", hrMain, hr.getHeadquarters());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments, Create Department "ghostDept"
        Department ghostDept = new Department();
        ghostDept.setName("ghostDept");
        Office officeX = new Office();
        officeX.setLocation("OfficeX");
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        boolean result = company.assignHeadquarters(ghostDept, officeX);
        
        // Expected Output: false (because the department "ghostDept" does not belong to the Company)
        assertFalse("Should return false when department doesn't exist in company", result);
    }
}