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
        // Common setup for tests
        document = new Document();
    }
    
    @Test
    public void testCase1_ApprovePendingRequest() {
        // Setup: Customer "C007" (Michael Brown) with pending application for SolarMax
        customer = new Customer();
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setCanApplyForIPO(true);
        
        company = new Company("SolarMax", "solarmax@gmail.com");
        
        // Create the application (10 shares, $200)
        application = new Application(10, 200.0, customer, company, document);
        
        // Test: Approve the application
        boolean result = application.approve();
        
        // Verify: Should return true and status should be APPROVAL
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVAL", 
                     ApplicationStatus.APPROVAL, application.getStatus());
        
        // Verify: Emails should be sent to customer and company
        assertEquals("Should have 2 emails (customer and company)", 2, application.getEmails().size());
        assertEquals("First email should be sent to customer", 
                     "m.brown@example.com", application.getEmails().get(0).getReceiver());
        assertEquals("Second email should be sent to company", 
                     "solarmax@gmail.com", application.getEmails().get(1).getReceiver());
    }
    
    @Test
    public void testCase2_RejectPendingRequest() {
        // Setup: Customer "C008" (Olivia Lee) with pending application for HealthPlus
        customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setCanApplyForIPO(true);
        
        company = new Company("HealthPlus", "healthplus@gmail.com");
        
        // Create the application (10 shares, $5000)
        application = new Application(10, 5000.0, customer, company, document);
        
        // Test: Reject the application
        boolean result = application.reject();
        
        // Verify: Should return true and status should be REJECTED
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", 
                     ApplicationStatus.REJECTED, application.getStatus());
        
        // Verify: Rejection email should be sent to customer
        assertEquals("Should have 1 rejection email", 1, application.getEmails().size());
        assertEquals("Rejection email should be sent to customer", 
                     "olivia.l@example.com", application.getEmails().get(0).getReceiver());
        assertTrue("Email content should contain rejection message", 
                   application.getEmails().get(0).getContent().contains("rejected"));
    }
    
    @Test
    public void testCase3_ApproveAlreadyApprovedRecord() {
        // Setup: Customer "C009" (Daniel Kim) with already approved application for HealthPlus
        customer = new Customer();
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setCanApplyForIPO(true);
        
        company = new Company("HealthPlus", "healthplus@gmail.com");
        
        // Create and approve the application (10 shares, $5000)
        application = new Application(10, 5000.0, customer, company, document);
        application.setStatus(ApplicationStatus.APPROVAL); // Set to already approved
        
        // Test: Attempt to re-approve the application
        boolean result = application.approve();
        
        // Verify: Should return false and status should remain APPROVAL
        assertFalse("Cannot re-approve an already approved application", result);
        assertEquals("Application status should remain APPROVAL", 
                     ApplicationStatus.APPROVAL, application.getStatus());
    }
    
    @Test
    public void testCase4_RejectAlreadyRejectedRecord() {
        // Setup: Customer "C010" (Sophie Zhang) with already rejected application
        customer = new Customer();
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setCanApplyForIPO(true);
        
        company = new Company("Health", "health@gmail.com");
        
        // Create and reject the application (10 shares, $5000)
        application = new Application(10, 5000.0, customer, company, document);
        application.setStatus(ApplicationStatus.REJECTED); // Set to already rejected
        
        // Test: Attempt to re-reject the application
        boolean result = application.reject();
        
        // Verify: Should return true (reject() always returns true) but status remains REJECTED
        assertTrue("Reject method always returns true", result);
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase5_ApproveRecordTiedToIneligibleCustomer() {
        // Setup: Customer "C011" (William Wang) who is ineligible for IPO
        customer = new Customer();
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setCanApplyForIPO(false); // Customer is ineligible
        
        company = new Company("Cloud", "Cloud@gmail.com");
        
        // Create the application (10 shares, $5000)
        application = new Application(10, 5000.0, customer, company, document);
        
        // Test: Attempt to approve the application for ineligible customer
        boolean result = application.approve();
        
        // Verify: Should return false and status should remain PENDING
        assertFalse("Cannot approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", 
                     ApplicationStatus.PENDING, application.getStatus());
    }
}