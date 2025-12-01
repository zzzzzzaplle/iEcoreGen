import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Company company;
    private Department engineering;
    private Department marketing;
    private Department hr;
    private Department ghostDept;
    private Office officeA;
    private Office officeB;
    private Office hrMain;
    private Office officeX;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        company = new Company();
        engineering = new Department();
        engineering.setName("Engineering");
        marketing = new Department();
        marketing.setName("Marketing");
        hr = new Department();
        hr.setName("HR");
        ghostDept = new Department();
        ghostDept.setName("ghostDept");
        
        officeA = new Office();
        officeA.setLocation("OfficeA");
        officeB = new Office();
        officeB.setLocation("OfficeB");
        hrMain = new Office();
        hrMain.setLocation("HR-Main");
        officeX = new Office();
        officeX.setLocation("OfficeX");
    }
    
    @Test
    public void testCase1_AssignValidHeadquarterToDepartmentWithoutExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering" and add Office "OfficeA" to Engineering
        company.addDepartment(engineering);
        engineering.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineering.assignHeadquarters(officeA);
        
        // Expected Output: true
        assertTrue("Should return true when assigning valid headquarter", result);
        
        // Verification: OfficeA is the headquarter of Engineering
        assertEquals("Engineering headquarters should be OfficeA", officeA, engineering.getHeadquarters());
    }
    
    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing", add Office "OfficeA" to Marketing
        company.addDepartment(engineering);
        company.addDepartment(marketing);
        marketing.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineering.assignHeadquarters(officeA);
        
        // Expected Output: false
        assertFalse("Should return false when office doesn't belong to department", result);
        
        // Verification: Engineering do not have headquarter
        assertNull("Engineering should not have a headquarter", engineering.getHeadquarters());
    }
    
    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering", add Offices "OfficeA" and "OfficeB" to Engineering
        company.addDepartment(engineering);
        engineering.addOffice(officeA);
        engineering.addOffice(officeB);
        
        // Set "OfficeA" as headquarter
        engineering.assignHeadquarters(officeA);
        
        // Action: Set "OfficeB" as new headquarter
        boolean result = engineering.assignHeadquarters(officeB);
        
        // Expected Output: false
        assertFalse("Should return false when reassigning existing headquarter", result);
        
        // Verification: Engineering's headquarter remains OfficeA
        assertEquals("Engineering headquarters should remain OfficeA", officeA, engineering.getHeadquarters());
    }
    
    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR", add only Office "HR-Main" to HR
        company.addDepartment(hr);
        hr.addOffice(hrMain);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = hr.assignHeadquarters(hrMain);
        
        // Expected Output: true
        assertTrue("Should return true when assigning to department with single office", result);
        
        // Verification: HR's headquarter is HR-Main
        assertEquals("HR headquarters should be HR-Main", hrMain, hr.getHeadquarters());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments, create Department "ghostDept"
        // Note: ghostDept is not added to company
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        boolean result = ghostDept.assignHeadquarters(officeX);
        
        // Expected Output: false
        assertFalse("Should return false when assigning to non-existent department", result);
        
        // Verification: ghostDept should not have headquarters
        assertNull("ghostDept should not have headquarters", ghostDept.getHeadquarters());
    }
}