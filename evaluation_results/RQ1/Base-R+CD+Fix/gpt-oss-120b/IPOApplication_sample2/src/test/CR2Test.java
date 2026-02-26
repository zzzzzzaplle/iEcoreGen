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
        document = new Document();
    }
    
    @Test
    public void testCase1_ApprovePendingRequest() {
        // Setup
        customer = new Customer("Michael", "Brown", "m.brown@example.com", "555-1122");
        customer.setCanApplyForIPO(true);
        company = new Company("SolarMax", "solarmax@gmail.com");
        
        // Create application
        boolean applicationCreated = customer.createApplication(company, 10, 200.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Get the created application
        application = customer.getApplications().get(0);
        
        // Test approval
        boolean result = application.approve();
        
        // Verify results
        assertTrue("Approval should succeed", result);
        assertEquals("Status should be APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
        assertEquals("Should have 2 emails sent", 2, application.getEmails().size());
    }
    
    @Test
    public void testCase2_RejectPendingRequest() {
        // Setup
        customer = new Customer("Olivia", "Lee", "olivia.l@example.com", "555-3344");
        customer.setCanApplyForIPO(true);
        company = new Company("HealthPlus", "healthplus@gmail.com");
        
        // Create application
        boolean applicationCreated = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Get the created application
        application = customer.getApplications().get(0);
        
        // Test rejection
        boolean result = application.reject();
        
        // Verify results
        assertTrue("Rejection should succeed", result);
        assertEquals("Status should be REJECTED", ApplicationStatus.REJECTED, application.getStatus());
        assertEquals("Should have 1 email sent", 1, application.getEmails().size());
    }
    
    @Test
    public void testCase3_ApproveAlreadyApprovedRecord() {
        // Setup
        customer = new Customer("Daniel", "Kim", "d.kim@example.com", "555-5566");
        customer.setCanApplyForIPO(true);
        company = new Company("HealthPlus", "healthplus@gmail.com");
        
        // Create and approve application
        boolean applicationCreated = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        application = customer.getApplications().get(0);
        boolean firstApproval = application.approve();
        assertTrue("First approval should succeed", firstApproval);
        
        // Try to approve again
        boolean secondApproval = application.approve();
        
        // Verify results
        assertFalse("Second approval should fail", secondApproval);
        assertEquals("Status should remain APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
    }
    
    @Test
    public void testCase4_RejectAlreadyRejectedRecord() {
        // Setup
        customer = new Customer("Sophie", "Zhang", "s.zhang@example.com", "555-7788");
        customer.setCanApplyForIPO(true);
        company = new Company("Health", "health@gmail.com");
        
        // Create and reject application
        boolean applicationCreated = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        application = customer.getApplications().get(0);
        boolean firstRejection = application.reject();
        assertTrue("First rejection should succeed", firstRejection);
        
        // Try to reject again
        boolean secondRejection = application.reject();
        
        // Verify results
        assertFalse("Second rejection should fail", secondRejection);
        assertEquals("Status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase5_ApproveRecordTiedToIneligibleCustomer() {
        // Setup
        customer = new Customer("William", "Wang", "will.w@example.com", "555-9900");
        customer.setCanApplyForIPO(false); // Customer is ineligible
        company = new Company("Cloud", "Cloud@gmail.com");
        
        // Create application while customer was eligible
        customer.setCanApplyForIPO(true);
        boolean applicationCreated = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Now make customer ineligible
        customer.setCanApplyForIPO(false);
        
        application = customer.getApplications().get(0);
        
        // Test approval with ineligible customer
        boolean result = application.approve();
        
        // Verify results
        assertFalse("Approval should fail for ineligible customer", result);
        assertEquals("Status should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
        assertEquals("No emails should be sent", 0, application.getEmails().size());
    }
}