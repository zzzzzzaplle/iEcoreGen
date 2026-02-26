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
        // Common setup for test cases
        document = new Document();
    }
    
    @Test
    public void testCase1_approvePendingRequest() {
        // Setup: Customer "C007" with pending application for "SolarMax"
        customer = new Customer();
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setCanApplyForIPO(true);
        
        company = new Company("SolarMax", "solarmax@gmail.com");
        
        // Create application with pending status
        application = new Application(10, 200.0, customer, company, document);
        application.setStatus(ApplicationStatus.PENDING);
        
        // Test: Approve the application
        boolean result = application.approve();
        
        // Verify: Should return true and status should be APPROVAL
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
        assertEquals("Should have 2 emails (customer and company)", 2, application.getEmails().size());
    }
    
    @Test
    public void testCase2_rejectPendingRequest() {
        // Setup: Customer "C008" with pending application for "HealthPlus"
        customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setCanApplyForIPO(true);
        
        company = new Company("HealthPlus", "healthplus@gmail.com");
        
        // Create application with pending status
        application = new Application(10, 5000.0, customer, company, document);
        application.setStatus(ApplicationStatus.PENDING);
        
        // Test: Reject the application
        boolean result = application.reject();
        
        // Verify: Should return true and status should be REJECTED
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, application.getStatus());
        assertEquals("Should have 1 email (rejection email)", 1, application.getEmails().size());
    }
    
    @Test
    public void testCase3_approveAlreadyApprovedRecord() {
        // Setup: Customer "C009" with already approved application
        customer = new Customer();
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setCanApplyForIPO(true);
        
        company = new Company("HealthPlus", "healthplus@gmail.com");
        
        // Create application with APPROVAL status
        application = new Application(10, 5000.0, customer, company, document);
        application.setStatus(ApplicationStatus.APPROVAL);
        
        // Test: Attempt to approve already approved application
        boolean result = application.approve();
        
        // Verify: Should return false and status should remain APPROVAL
        assertFalse("Should not be able to approve already approved application", result);
        assertEquals("Application status should remain APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
    }
    
    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Setup: Customer "C010" with already rejected application
        customer = new Customer();
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setCanApplyForIPO(true);
        
        company = new Company("Health", "health@gmail.com");
        
        // Create application with REJECTED status
        application = new Application(10, 5000.0, customer, company, document);
        application.setStatus(ApplicationStatus.REJECTED);
        
        // Test: Attempt to reject already rejected application
        boolean result = application.reject();
        
        // Verify: Should return false and status should remain REJECTED
        assertFalse("Should not be able to reject already rejected application", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Setup: Customer "C011" who is ineligible for IPO
        customer = new Customer();
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setCanApplyForIPO(false); // Customer is ineligible
        
        company = new Company("Cloud", "Cloud@gmail.com");
        
        // Create application with pending status for ineligible customer
        application = new Application(10, 5000.0, customer, company, document);
        application.setStatus(ApplicationStatus.PENDING);
        
        // Test: Attempt to approve application for ineligible customer
        boolean result = application.approve();
        
        // Verify: Should return false and status should remain PENDING
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
    }
}