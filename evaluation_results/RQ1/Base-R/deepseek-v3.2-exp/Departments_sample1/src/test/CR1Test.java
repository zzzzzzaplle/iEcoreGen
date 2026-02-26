import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR1Test {
    
    private Company company;
    private Department engineeringDept;
    private Department marketingDept;
    private Department hrDept;
    private Department ghostDept;
    private Office officeA;
    private Office officeB;
    private Office hrMain;
    private Office officeX;
    
    @Before
    public void setUp() {
        company = new Company();
        engineeringDept = new Department();
        engineeringDept.setName("Engineering");
        
        marketingDept = new Department();
        marketingDept.setName("Marketing");
        
        hrDept = new Department();
        hrDept.setName("HR");
        
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
        // SetUp: Create Company with Department "Engineering"
        company.getDepartments().add(engineeringDept);
        
        // SetUp: Add Office "OfficeA" to Engineering
        officeA.setDepartment(engineeringDept);
        engineeringDept.getOffices().add(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = company.assignHeadquarters(engineeringDept, officeA);
        
        // Expected Output: true
        assertTrue("Should return true when assigning valid headquarter", result);
        
        // Verification: OfficeA is the headquarter of Engineering
        assertEquals("Engineering's headquarter should be OfficeA", officeA, engineeringDept.getHeadquarters());
    }
    
    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing"
        company.getDepartments().add(engineeringDept);
        company.getDepartments().add(marketingDept);
        
        // SetUp: Add Office "OfficeA" to Marketing
        officeA.setDepartment(marketingDept);
        marketingDept.getOffices().add(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = company.assignHeadquarters(engineeringDept, officeA);
        
        // Expected Output: false
        assertFalse("Should return false when office doesn't belong to department", result);
        
        // Verification: Engineering do not have headquarter
        assertNull("Engineering should not have a headquarter", engineeringDept.getHeadquarters());
    }
    
    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering"
        company.getDepartments().add(engineeringDept);
        
        // SetUp: Add Offices "OfficeA" and "OfficeB" to Engineering
        officeA.setDepartment(engineeringDept);
        officeB.setDepartment(engineeringDept);
        engineeringDept.getOffices().add(officeA);
        engineeringDept.getOffices().add(officeB);
        
        // SetUp: Set "OfficeA" as headquarter
        company.assignHeadquarters(engineeringDept, officeA);
        
        // Action: Set "OfficeB" as new headquarter
        boolean result = company.assignHeadquarters(engineeringDept, officeB);
        
        // Expected Output: false
        assertFalse("Should return false when department already has headquarter", result);
        
        // Verification: Engineering's headquarter remains OfficeA
        assertEquals("Engineering's headquarter should remain OfficeA", officeA, engineeringDept.getHeadquarters());
    }
    
    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR"
        company.getDepartments().add(hrDept);
        
        // SetUp: Add only Office "HR-Main" to HR
        hrMain.setDepartment(hrDept);
        hrDept.getOffices().add(hrMain);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = company.assignHeadquarters(hrDept, hrMain);
        
        // Expected Output: true
        assertTrue("Should return true when assigning headquarter to department with single office", result);
        
        // Verification: HR's headquarter is HR-Main
        assertEquals("HR's headquarter should be HR-Main", hrMain, hrDept.getHeadquarters());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments
        // (company starts with empty departments list)
        
        // SetUp: Create Department "ghostDept" (but not added to company)
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        boolean result = company.assignHeadquarters(ghostDept, officeX);
        
        // Expected Output: false (because the department "ghostDept" does not belong to the Company)
        assertFalse("Should return false when department doesn't exist in company", result);
    }
}