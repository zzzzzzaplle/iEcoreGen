import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    private Application application;
    
    @Before
    public void setUp() {
        // Common setup that might be used across multiple tests
        document = new Document();
    }
    
    @Test
    public void testCase1_approvePendingRequest() {
        // Setup
        customer = new Customer("Michael", "Brown", "m.brown@example.com", "555-1122");
        customer.setCanApplyForIPO(true);
        company = new Company("SolarMax", "solarmax@gmail.com");
        
        // Create application
        boolean created = customer.createApplication(company, 10, 200.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Get the created application
        application = customer.getApplications().get(0);
        
        // Test: Approve the application
        boolean result = application.approve();
        
        // Verify
        assertTrue("Application should be approved successfully", result);
        assertEquals("Status should be APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
        assertEquals("Should have 2 emails sent (to customer and company)", 2, application.getEmails().size());
        
        // Verify email content
        Email customerEmail = application.getEmails().get(0);
        assertEquals("Customer email recipient should match", "m.brown@example.com", customerEmail.getReceiver());
        assertTrue("Customer email should contain application details", 
                   customerEmail.getContent().contains("Michael Brown"));
        assertTrue("Customer email should contain company name", 
                   customerEmail.getContent().contains("SolarMax"));
        assertTrue("Customer email should contain shares information", 
                   customerEmail.getContent().contains("10"));
        assertTrue("Customer email should contain amount information", 
                   customerEmail.getContent().contains("$200.00"));
    }
    
    @Test
    public void testCase2_rejectPendingRequest() {
        // Setup
        customer = new Customer("Olivia", "Lee", "olivia.l@example.com", "555-3344");
        customer.setCanApplyForIPO(true);
        company = new Company("HealthPlus", "healthplus@gmail.com");
        
        // Create application
        boolean created = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Get the created application
        application = customer.getApplications().get(0);
        
        // Test: Reject the application
        boolean result = application.reject();
        
        // Verify
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Status should be REJECTED", ApplicationStatus.REJECTED, application.getStatus());
        assertEquals("Should have 1 email sent (rejection notice)", 1, application.getEmails().size());
        
        // Verify rejection email content
        Email rejectionEmail = application.getEmails().get(0);
        assertEquals("Rejection email recipient should match", "olivia.l@example.com", rejectionEmail.getReceiver());
        assertTrue("Rejection email should contain customer name", 
                   rejectionEmail.getContent().contains("Olivia Lee"));
        assertTrue("Rejection email should contain company name", 
                   rejectionEmail.getContent().contains("HealthPlus"));
        assertTrue("Rejection email should indicate rejection", 
                   rejectionEmail.getContent().contains("rejected"));
    }
    
    @Test
    public void testCase3_approveAlreadyApprovedRecord() {
        // Setup
        customer = new Customer("Daniel", "Kim", "d.kim@example.com", "555-5566");
        customer.setCanApplyForIPO(true);
        company = new Company("HealthPlus", "healthplus@gmail.com");
        
        // Create and approve application first
        boolean created = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        application = customer.getApplications().get(0);
        boolean firstApproval = application.approve();
        assertTrue("First approval should succeed", firstApproval);
        
        // Store initial email count
        int initialEmailCount = application.getEmails().size();
        
        // Test: Try to approve already approved application
        boolean result = application.approve();
        
        // Verify
        assertFalse("Should not be able to approve already approved application", result);
        assertEquals("Status should remain APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
        assertEquals("No additional emails should be sent", initialEmailCount, application.getEmails().size());
    }
    
    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Setup
        customer = new Customer("Sophie", "Zhang", "s.zhang@example.com", "555-7788");
        customer.setCanApplyForIPO(true);
        company = new Company("Health", "health@gmail.com");
        
        // Create and reject application first
        boolean created = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        application = customer.getApplications().get(0);
        boolean firstRejection = application.reject();
        assertTrue("First rejection should succeed", firstRejection);
        
        // Store initial email count
        int initialEmailCount = application.getEmails().size();
        
        // Test: Try to reject already rejected application
        boolean result = application.reject();
        
        // Verify
        assertFalse("Should not be able to reject already rejected application", result);
        assertEquals("Status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
        assertEquals("No additional emails should be sent", initialEmailCount, application.getEmails().size());
    }
    
    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Setup
        customer = new Customer("William", "Wang", "will.w@example.com", "555-9900");
        customer.setCanApplyForIPO(false); // Customer is ineligible
        company = new Company("Cloud", "Cloud@gmail.com");
        
        // Create application while customer is eligible
        customer.setCanApplyForIPO(true);
        boolean created = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        application = customer.getApplications().get(0);
        
        // Make customer ineligible before approval
        customer.setCanApplyForIPO(false);
        
        // Test: Try to approve application for ineligible customer
        boolean result = application.approve();
        
        // Verify
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertEquals("Status should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
        assertEquals("No emails should be sent", 0, application.getEmails().size());
    }
}