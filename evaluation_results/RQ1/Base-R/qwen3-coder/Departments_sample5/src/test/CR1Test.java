import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        // Initialize company and common test objects
        company = new Company();
        company.setCompanyId("COMP001");
        company.setName("Test Company");
        
        engineeringDept = new Department();
        engineeringDept.setDepartmentId("DEPT001");
        engineeringDept.setName("Engineering");
        
        marketingDept = new Department();
        marketingDept.setDepartmentId("DEPT002");
        marketingDept.setName("Marketing");
        
        hrDept = new Department();
        hrDept.setDepartmentId("DEPT003");
        hrDept.setName("HR");
        
        officeA = new Office();
        officeA.setOfficeId("OFF001");
        officeA.setAddress("123 Main St");
        
        officeB = new Office();
        officeB.setOfficeId("OFF002");
        officeB.setAddress("456 Oak St");
        
        hrMain = new Office();
        hrMain.setOfficeId("OFF003");
        hrMain.setAddress("789 HR St");
    }
    
    @Test
    public void testCase1_AssignValidHeadquarterToDepartmentWithoutExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering" and add Office "OfficeA" to Engineering
        company.getDepartments().add(engineeringDept);
        engineeringDept.getOffices().add(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = company.assignHeadquarters(engineeringDept, officeA);
        
        // Expected Output: true
        assertTrue("Should return true when assigning valid headquarter", result);
        
        // Verification: OfficeA is the headquarter of Engineering
        assertTrue("OfficeA should be marked as headquarters", officeA.isHeadquarters());
        assertEquals("Engineering's headquarters should be OfficeA", officeA, engineeringDept.getHeadquarters());
    }
    
    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing", add Office "OfficeA" to Marketing
        company.getDepartments().add(engineeringDept);
        company.getDepartments().add(marketingDept);
        marketingDept.getOffices().add(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = company.assignHeadquarters(engineeringDept, officeA);
        
        // Expected Output: false
        assertFalse("Should return false when office doesn't belong to department", result);
        
        // Verification: Engineering do not have headquarter
        assertFalse("Engineering should not have headquarters", engineeringDept.hasHeadquarters());
        assertNull("Engineering's headquarters should be null", engineeringDept.getHeadquarters());
    }
    
    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering", add Offices "OfficeA" and "OfficeB" to Engineering
        // Set "OfficeA" as headquarter
        company.getDepartments().add(engineeringDept);
        engineeringDept.getOffices().add(officeA);
        engineeringDept.getOffices().add(officeB);
        company.assignHeadquarters(engineeringDept, officeA);
        
        // Action: Set "OfficeB" as new headquarter
        boolean result = company.assignHeadquarters(engineeringDept, officeB);
        
        // Expected Output: false
        assertFalse("Should return false when department already has headquarters", result);
        
        // Verification: Engineering's headquarter remains OfficeA
        assertTrue("OfficeA should remain as headquarters", officeA.isHeadquarters());
        assertFalse("OfficeB should not be marked as headquarters", officeB.isHeadquarters());
        assertEquals("Engineering's headquarters should remain OfficeA", officeA, engineeringDept.getHeadquarters());
    }
    
    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR", add only Office "HR-Main" to HR
        company.getDepartments().add(hrDept);
        hrDept.getOffices().add(hrMain);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = company.assignHeadquarters(hrDept, hrMain);
        
        // Expected Output: true
        assertTrue("Should return true when assigning headquarter to department with single office", result);
        
        // Verification: HR's headquarter is HR-Main
        assertTrue("HR-Main should be marked as headquarters", hrMain.isHeadquarters());
        assertEquals("HR's headquarters should be HR-Main", hrMain, hrDept.getHeadquarters());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments, create Department "ghostDept"
        Department ghostDept = new Department();
        ghostDept.setDepartmentId("DEPT999");
        ghostDept.setName("Ghost Department");
        
        Office officeX = new Office();
        officeX.setOfficeId("OFF999");
        officeX.setAddress("Ghost Office");
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        boolean result = company.assignHeadquarters(ghostDept, officeX);
        
        // Expected Output: false (because the department "ghostDept" does not belong to the Company)
        assertFalse("Should return false when department doesn't belong to company", result);
    }
}