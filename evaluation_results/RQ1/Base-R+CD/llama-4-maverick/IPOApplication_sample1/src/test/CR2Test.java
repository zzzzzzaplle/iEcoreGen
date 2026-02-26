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
        // Common setup that can be reused across tests
        document = new Document();
    }
    
    @Test
    public void testCase1_approvePendingRequest() {
        // Setup
        customer = new Customer();
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setCanApplyForIPO(true);
        
        company = new Company("SolarMax", "solarmax@gmail.com");
        
        // Create application with PENDING status
        application = new Application(10, 200.0, customer, company, document);
        
        // Execute: Approve the application
        boolean result = application.approve();
        
        // Verify
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
        assertEquals("Should have 2 emails sent (customer + company)", 2, application.getEmails().size());
    }
    
    @Test
    public void testCase2_rejectPendingRequest() {
        // Setup
        customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setCanApplyForIPO(true);
        
        company = new Company("HealthPlus", "healthplus@gmail.com");
        
        // Create application with PENDING status
        application = new Application(10, 5000.0, customer, company, document);
        
        // Execute: Reject the application
        boolean result = application.reject();
        
        // Verify
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, application.getStatus());
        assertEquals("Should have 1 rejection email sent", 1, application.getEmails().size());
    }
    
    @Test
    public void testCase3_approveAlreadyApprovedRecord() {
        // Setup
        customer = new Customer();
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setCanApplyForIPO(true);
        
        company = new Company("HealthPlus", "healthplus@gmail.com");
        
        // Create application and manually set to APPROVED status
        application = new Application(10, 5000.0, customer, company, document);
        application.setStatus(ApplicationStatus.APPROVAL);
        
        // Execute: Attempt to approve already approved application
        boolean result = application.approve();
        
        // Verify
        assertFalse("Should not be able to approve already approved application", result);
        assertEquals("Status should remain APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
    }
    
    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Setup
        customer = new Customer();
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setCanApplyForIPO(true);
        
        company = new Company("Health", "health@gmail.com");
        
        // Create application and manually set to REJECTED status
        application = new Application(10, 5000.0, customer, company, document);
        application.setStatus(ApplicationStatus.REJECTED);
        
        // Execute: Attempt to reject already rejected application
        boolean result = application.reject();
        
        // Verify
        assertFalse("Should not be able to reject already rejected application", result);
        assertEquals("Status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Setup
        customer = new Customer();
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setCanApplyForIPO(false); // Customer is ineligible
        
        company = new Company("Cloud", "Cloud@gmail.com");
        
        // Create application with PENDING status
        application = new Application(10, 5000.0, customer, company, document);
        
        // Execute: Attempt to approve application for ineligible customer
        boolean result = application.approve();
        
        // Verify
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertEquals("Status should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
        assertEquals("No emails should be sent for failed approval", 0, application.getEmails().size());
    }
}