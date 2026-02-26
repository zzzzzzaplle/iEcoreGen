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
    private Office officeA;
    private Office officeB;
    private Office officeC;
    private Office hrMain;
    
    @Before
    public void setUp() {
        company = new Company();
        company.setName("Test Company");
        
        engineering = new Department();
        engineering.setName("Engineering");
        engineering.setDepartmentId(1);
        
        marketing = new Department();
        marketing.setName("Marketing");
        marketing.setDepartmentId(2);
        
        hr = new Department();
        hr.setName("HR");
        hr.setDepartmentId(3);
        
        officeA = new Office();
        officeA.setLocation("OfficeA");
        officeA.setOfficeId(101);
        
        officeB = new Office();
        officeB.setLocation("OfficeB");
        officeB.setOfficeId(102);
        
        officeC = new Office();
        officeC.setLocation("OfficeC");
        officeC.setOfficeId(103);
        
        hrMain = new Office();
        hrMain.setLocation("HR-Main");
        hrMain.setOfficeId(104);
    }
    
    @Test
    public void testCase1_AssignValidHeadquarterToDepartmentWithoutExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering" and add Office "OfficeA" to Engineering
        company.addDepartment(engineering);
        engineering.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = company.assignHeadquarters(engineering, officeA);
        
        // Expected Output: true
        assertTrue("Should successfully assign headquarter", result);
        
        // Verification: OfficeA is the headquarter of Engineering
        assertTrue("OfficeA should be headquarters", officeA.isHeadquarters());
        assertEquals("Engineering should have OfficeA as headquarters", officeA, engineering.getHeadquarters());
    }
    
    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing", add Office "OfficeA" to Marketing
        company.addDepartment(engineering);
        company.addDepartment(marketing);
        marketing.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = company.assignHeadquarters(engineering, officeA);
        
        // Expected Output: false
        assertFalse("Should fail to assign headquarter - office doesn't belong to department", result);
        
        // Verification: Engineering do not have headquarter
        assertFalse("Engineering should not have headquarters", engineering.hasHeadquarters());
        assertNull("Engineering should not have headquarters", engineering.getHeadquarters());
    }
    
    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering", add Offices "OfficeA" and "OfficeB" to Engineering, set "OfficeA" as headquarter
        company.addDepartment(engineering);
        engineering.addOffice(officeA);
        engineering.addOffice(officeB);
        company.assignHeadquarters(engineering, officeA);
        
        // Action: Set "OfficeB" as new headquarter
        boolean result = company.assignHeadquarters(engineering, officeB);
        
        // Expected Output: false
        assertFalse("Should fail to reassign headquarter - department already has headquarters", result);
        
        // Verification: Engineering's headquarter remains OfficeA
        assertTrue("OfficeA should remain headquarters", officeA.isHeadquarters());
        assertFalse("OfficeB should not be headquarters", officeB.isHeadquarters());
        assertEquals("Engineering should still have OfficeA as headquarters", officeA, engineering.getHeadquarters());
    }
    
    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR", add only Office "HR-Main" to HR
        company.addDepartment(hr);
        hr.addOffice(hrMain);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = company.assignHeadquarters(hr, hrMain);
        
        // Expected Output: true
        assertTrue("Should successfully assign headquarter to department with single office", result);
        
        // Verification: HR's headquarter is HR-Main
        assertTrue("HR-Main should be headquarters", hrMain.isHeadquarters());
        assertEquals("HR should have HR-Main as headquarters", hrMain, hr.getHeadquarters());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments, create Department "ghostDept"
        Department ghostDept = new Department();
        ghostDept.setName("ghostDept");
        ghostDept.setDepartmentId(999);
        
        Office officeX = new Office();
        officeX.setLocation("OfficeX");
        officeX.setOfficeId(999);
        ghostDept.addOffice(officeX);
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        boolean result = company.assignHeadquarters(ghostDept, officeX);
        
        // Expected Output: false (because the department "ghostDept" does not belong to the Company)
        assertFalse("Should fail to assign headquarter - department not in company", result);
        
        // OfficeX should not be headquarters since assignment failed
        assertFalse("OfficeX should not be headquarters", officeX.isHeadquarters());
    }
}