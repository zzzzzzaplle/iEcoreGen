import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    private Customer eligibleCustomer;
    private Customer ineligibleCustomer;
    private Company solarMaxCompany;
    private Company healthPlusCompany;
    private Company cloudCompany;
    private Document documentS;
    private Document documentH;
    private Document documentCloud;
    private Application pendingApplication1;
    private Application pendingApplication2;
    private Application approvedApplication;
    private Application rejectedApplication;
    private Application ineligiblePendingApplication;

    @Before
    public void setUp() {
        // Setup for Test Case 1
        eligibleCustomer = new Customer("Michael", "Brown", "m.brown@example.com", "555-1122", true);
        solarMaxCompany = new Company("SolarMax", "solarmax@gmail.com");
        documentS = new Document();
        pendingApplication1 = new Application(10, 200.0, eligibleCustomer, solarMaxCompany, documentS);
        
        // Setup for Test Case 2
        healthPlusCompany = new Company("HealthPlus", "healthplus@gmail.com");
        documentH = new Document();
        pendingApplication2 = new Application(10, 5000.0, eligibleCustomer, healthPlusCompany, documentH);
        
        // Setup for Test Case 3
        approvedApplication = new Application(10, 5000.0, eligibleCustomer, healthPlusCompany, documentH);
        approvedApplication.setStatus(ApplicationStatus.APPROVAL);
        
        // Setup for Test Case 4
        rejectedApplication = new Application(10, 5000.0, eligibleCustomer, healthPlusCompany, documentH);
        rejectedApplication.setStatus(ApplicationStatus.REJECTED);
        
        // Setup for Test Case 5
        ineligibleCustomer = new Customer("William", "Wang", "will.w@example.com", "555-9900", false);
        cloudCompany = new Company("Cloud", "Cloud@gmail.com");
        documentCloud = new Document();
        ineligiblePendingApplication = new Application(10, 5000.0, ineligibleCustomer, cloudCompany, documentCloud);
    }

    @Test
    public void testCase1_ApprovePendingRequest() {
        // Test approving a pending application for eligible customer
        boolean result = pendingApplication1.approve();
        
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVAL", ApplicationStatus.APPROVAL, pendingApplication1.getStatus());
        assertEquals("Should have 2 emails (customer and company)", 2, pendingApplication1.getEmails().size());
    }

    @Test
    public void testCase2_RejectPendingRequest() {
        // Test rejecting a pending application
        boolean result = pendingApplication2.reject();
        
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, pendingApplication2.getStatus());
        assertEquals("Should have 1 rejection email", 1, pendingApplication2.getEmails().size());
    }

    @Test
    public void testCase3_ApproveAlreadyApprovedRecord() {
        // Test attempting to approve an already approved application
        boolean result = approvedApplication.approve();
        
        assertFalse("Should not be able to approve already approved application", result);
        assertEquals("Application status should remain APPROVAL", ApplicationStatus.APPROVAL, approvedApplication.getStatus());
    }

    @Test
    public void testCase4_RejectAlreadyRejectedRecord() {
        // Test attempting to reject an already rejected application
        boolean result = rejectedApplication.reject();
        
        assertFalse("Should not be able to reject already rejected application", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, rejectedApplication.getStatus());
    }

    @Test
    public void testCase5_ApproveRecordTiedToIneligibleCustomer() {
        // Test attempting to approve application for ineligible customer
        boolean result = ineligiblePendingApplication.approve();
        
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, ineligiblePendingApplication.getStatus());
    }
}