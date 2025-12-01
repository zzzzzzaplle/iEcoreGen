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
        // Initialize common test objects
        company = new Company();
        engineeringDept = new Department();
        marketingDept = new Department();
        hrDept = new Department();
        ghostDept = new Department();
        officeA = new Office();
        officeB = new Office();
        hrMain = new Office();
        officeX = new Office();
        
        // Set addresses for offices
        officeA.setAddress("123 Engineering St");
        officeB.setAddress("456 Engineering Ave");
        hrMain.setAddress("789 HR Blvd");
        officeX.setAddress("999 Ghost St");
    }
    
    @Test
    public void testCase1_AssignValidHeadquarterToDepartmentWithoutExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering"
        company.addDepartment(engineeringDept);
        
        // SetUp: Add Office "OfficeA" to Engineering
        engineeringDept.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineeringDept.assignHeadquarters(officeA);
        
        // Expected Output: true
        assertTrue("Should return true when assigning valid headquarter", result);
        
        // Verification: OfficeA is the headquarter of Engineering
        assertEquals("Engineering's headquarter should be OfficeA", officeA, engineeringDept.getHeadquarters());
    }
    
    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing"
        company.addDepartment(engineeringDept);
        company.addDepartment(marketingDept);
        
        // SetUp: Add Office "OfficeA" to Marketing
        marketingDept.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineeringDept.assignHeadquarters(officeA);
        
        // Expected Output: false
        assertFalse("Should return false when office doesn't belong to department", result);
        
        // Verification: Engineering do not have headquarter
        assertNull("Engineering should not have a headquarter", engineeringDept.getHeadquarters());
    }
    
    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering"
        company.addDepartment(engineeringDept);
        
        // SetUp: Add Offices "OfficeA" and "OfficeB" to Engineering
        engineeringDept.addOffice(officeA);
        engineeringDept.addOffice(officeB);
        
        // SetUp: Set "OfficeA" as headquarter
        engineeringDept.assignHeadquarters(officeA);
        
        // Action: Set "OfficeB" as new headquarter
        boolean result = engineeringDept.assignHeadquarters(officeB);
        
        // Expected Output: false
        assertFalse("Should return false when reassigning existing headquarter", result);
        
        // Verification: Engineering's headquarter remains OfficeA
        assertEquals("Engineering's headquarter should remain OfficeA", officeA, engineeringDept.getHeadquarters());
    }
    
    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR"
        company.addDepartment(hrDept);
        
        // SetUp: Add only Office "HR-Main" to HR
        hrDept.addOffice(hrMain);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = hrDept.assignHeadquarters(hrMain);
        
        // Expected Output: true
        assertTrue("Should return true when assigning headquarter to department with single office", result);
        
        // Verification: HR's headquarter is HR-Main
        assertEquals("HR's headquarter should be HR-Main", hrMain, hrDept.getHeadquarters