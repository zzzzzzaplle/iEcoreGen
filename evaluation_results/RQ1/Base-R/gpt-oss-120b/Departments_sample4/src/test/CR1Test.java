import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
        engineeringDept.setName("Engineering");
        
        marketingDept = new Department();
        marketingDept.setName("Marketing");
        
        hrDept = new Department();
        hrDept.setName("HR");
        
        officeA = new Office();
        officeA.setLocation("OfficeA");
        
        officeB = new Office();
        officeB.setLocation("OfficeB");
        
        hrMain = new Office();
        hrMain.setLocation("HR-Main");
    }
    
    @Test
    public void testCase1_AssignValidHeadquarterToDepartmentWithoutExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering" and add Office "OfficeA" to Engineering
        company.addDepartment(engineeringDept);
        engineeringDept.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = company.assignHeadquarters(engineeringDept, officeA);
        
        // Expected Output: true
        assertTrue("Should return true when assigning valid headquarter", result);
        
        // Verification: OfficeA is the headquarter of Engineering
        assertEquals("Engineering's headquarter should be OfficeA", officeA, engineeringDept.getHeadquarters());
    }
    
    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing", add Office "OfficeA" to Marketing
        company.addDepartment(engineeringDept);
        company.addDepartment(marketingDept);
        marketingDept.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = company.assignHeadquarters(engineeringDept, officeA);
        
        // Expected Output: false
        assertFalse("Should return false when office doesn't belong to department", result);
        
        // Verification: Engineering do not have headquarter
        assertNull("Engineering should not have a headquarter", engineeringDept.getHeadquarters());
    }
    
    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering", add Offices "OfficeA" and "OfficeB" to Engineering
        company.addDepartment(engineeringDept);
        engineeringDept.addOffice(officeA);
        engineeringDept.addOffice(officeB);
        
        // Set "OfficeA" as headquarter
        company.assignHeadquarters(engineeringDept, officeA);
        
        // Action: Set "OfficeB" as new headquarter
        boolean result = company.assignHeadquarters(engineeringDept, officeB);
        
        // Expected Output: false
        assertFalse("Should return false when department already has headquarters", result);
        
        // Verification: Engineering's headquarter remains OfficeA
        assertEquals("Engineering's headquarter should remain OfficeA", officeA, engineeringDept.getHeadquarters());
    }
    
    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR", add only Office "HR-Main" to HR
        company.addDepartment(hrDept);
        hrDept.addOffice(hrMain);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = company.assignHeadquarters(hrDept, hrMain);
        
        // Expected Output: true
        assertTrue("Should return true when assigning headquarter to department with single office", result);
        
        // Verification: HR's headquarter is HR-Main
        assertEquals("HR's headquarter should be HR-Main", hrMain, hrDept.getHeadquarters());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments, create Department "ghostDept"
        Department ghostDept = new Department();
        ghostDept.setName("ghostDept");
        
        Office officeX = new Office();
        officeX.setLocation("OfficeX");
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        boolean result = company.assignHeadquarters(ghostDept, officeX);
        
        // Expected Output: false (because the department "ghostDept" does not belong to the Company)
        assertFalse("Should return false when department doesn't belong to company", result);
    }
}