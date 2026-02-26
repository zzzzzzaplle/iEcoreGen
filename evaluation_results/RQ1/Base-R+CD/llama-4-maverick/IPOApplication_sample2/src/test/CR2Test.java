import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer4;
    private Customer customer5;
    private Company solarMax;
    private Company healthPlus;
    private Company health;
    private Company cloud;
    private Document documentS;
    private Document documentH;
    private Application app1001;
    private Application app1002;
    private Application app1003;
    private Application app1004;
    private Application app1005;

    @Before
    public void setUp() {
        // Setup common test data
        solarMax = new Company("SolarMax", "solarmax@gmail.com");
        healthPlus = new Company("HealthPlus", "healthplus@gmail.com");
        health = new Company("Health", "health@gmail.com");
        cloud = new Company("Cloud", "Cloud@gmail.com");
        
        documentS = new Document();
        documentH = new Document();
        
        // Customer 1: Michael Brown - eligible, pending application
        customer1 = new Customer("Michael", "Brown", "m.brown@example.com", "555-1122");
        customer1.setCanApplyForIPO(true);
        app1001 = new Application(10, 200.0, customer1, solarMax, documentS);
        customer1.getApplications().add(app1001);
        
        // Customer 2: Olivia Lee - eligible, pending application
        customer2 = new Customer("Olivia", "Lee", "olivia.l@example.com", "555-3344");
        customer2.setCanApplyForIPO(true);
        app1002 = new Application(10, 5000.0, customer2, healthPlus, documentH);
        customer2.getApplications().add(app1002);
        
        // Customer 3: Daniel Kim - eligible, already approved application
        customer3 = new Customer("Daniel", "Kim", "d.kim@example.com", "555-5566");
        customer3.setCanApplyForIPO(true);
        app1003 = new Application(10, 5000.0, customer3, healthPlus, documentH);
        app1003.setStatus(ApplicationStatus.APPROVAL);
        customer3.getApplications().add(app1003);
        
        // Customer 4: Sophie Zhang - eligible, already rejected application
        customer4 = new Customer("Sophie", "Zhang", "s.zhang@example.com", "555-7788");
        customer4.setCanApplyForIPO(true);
        app1004 = new Application(10, 5000.0, customer4, health, documentH);
        app1004.setStatus(ApplicationStatus.REJECTED);
        customer4.getApplications().add(app1004);
        
        // Customer 5: William Wang - ineligible, pending application
        customer5 = new Customer("William", "Wang", "will.w@example.com", "555-9900");
        customer5.setCanApplyForIPO(false);
        app1005 = new Application(10, 5000.0, customer5, cloud, documentH);
        customer5.getApplications().add(app1005);
    }

    @Test
    public void testCase1_approvePendingRequest() {
        // Test approving a pending application for an eligible customer
        boolean result = app1001.approve();
        
        // Verify approval was successful
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVAL", ApplicationStatus.APPROVAL, app1001.getStatus());
        
        // Verify emails were sent (2 emails for approval)
        assertEquals("Should have 2 emails sent for approval", 2, app1001.getEmails().size());
        
        // Verify email content
        Email customerEmail = app1001.getEmails().get(0);
        assertEquals("Customer email should be sent to customer", customer1.getEmail(), customerEmail.getReceiver());
        assertTrue("Email content should contain customer details", 
                   customerEmail.getContent().contains("Michael Brown"));
        
        Email companyEmail = app1001.getEmails().get(1);
        assertEquals("Company email should be sent to company", solarMax.getEmail(), companyEmail.getReceiver());
    }

    @Test
    public void testCase2_rejectPendingRequest() {
        // Test rejecting a pending application for an eligible customer
        boolean result = app1002.reject();
        
        // Verify rejection was successful
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, app1002.getStatus());
        
        // Verify rejection email was sent
        assertEquals("Should have 1 email sent for rejection", 1, app1002.getEmails().size());
        
        // Verify email content
        Email rejectionEmail = app1002.getEmails().get(0);
        assertEquals("Rejection email should be sent to customer", customer2.getEmail(), rejectionEmail.getReceiver());
        assertTrue("Email content should contain rejection notice", 
                   rejectionEmail.getContent().contains("has been rejected"));
    }

    @Test
    public void testCase3_approveAlreadyApprovedRecord() {
        // Test attempting to approve an already approved application
        boolean result = app1003.approve();
        
        // Verify approval failed
        assertFalse("Should not be able to approve already approved application", result);
        assertEquals("Application status should remain APPROVAL", ApplicationStatus.APPROVAL, app1003.getStatus());
        
        // Verify no new emails were sent
        assertEquals("No new emails should be sent for already approved application", 0, app1003.getEmails().size());
    }

    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Test attempting to reject an already rejected application
        boolean result = app1004.reject();
        
        // Verify rejection failed
        assertFalse("Should not be able to reject already rejected application", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, app1004.getStatus());
        
        // Verify no new emails were sent
        assertEquals("No new emails should be sent for already rejected application", 0, app1004.getEmails().size());
    }

    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Test attempting to approve an application for an ineligible customer
        boolean result = app1005.approve();
        
        // Verify approval failed due to customer ineligibility
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, app1005.getStatus());
        
        // Verify no emails were sent
        assertEquals("No emails should be sent for ineligible customer", 0, app1005.getEmails().size());
    }
}