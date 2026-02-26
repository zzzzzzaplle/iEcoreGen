import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        // Common setup for test cases
        customer = new Customer();
        company = new Company();
        document = new Document();
    }
    
    @Test
    public void testCase1_ApprovePendingRequest() {
        // Setup for Test Case 1: Approve pending request
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setCanApplyForIPO(true);
        
        company.setName("SolarMax");
        company.setEmail("solarmax@gmail.com");
        
        // Create a pending application
        Application application = new Application(10, 200.0, company, customer, document);
        customer.getApplications().add(application);
        
        // Test approval of pending application
        boolean result = application.approve();
        
        // Verify the result and status change
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
        assertEquals("Two emails should be sent (customer and company)", 2, application.getEmails().size());
        
        // Verify email content
        Email customerEmail = application.getEmails().get(0);
        assertEquals("Customer email recipient should match", "m.brown@example.com", customerEmail.getReceiver());
        assertTrue("Customer email content should contain customer details", 
                  customerEmail.getContent().contains("Michael Brown"));
        
        Email companyEmail = application.getEmails().get(1);
        assertEquals("Company email recipient should match", "solarmax@gmail.com", companyEmail.getReceiver());
        assertTrue("Company email content should contain company details", 
                  companyEmail.getContent().contains("SolarMax"));
    }
    
    @Test
    public void testCase2_RejectPendingRequest() {
        // Setup for Test Case 2: Reject pending request
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setCanApplyForIPO(true);
        
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");
        
        // Create a pending application
        Application application = new Application(10, 5000.0, company, customer, document);
        customer.getApplications().add(application);
        
        // Test rejection of pending application
        boolean result = application.reject();
        
        // Verify the result and status change
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, application.getStatus());
        assertEquals("One rejection email should be sent", 1, application.getEmails().size());
        
        // Verify rejection email
        Email rejectionEmail = application.getEmails().get(0);
        assertEquals("Rejection email recipient should match", "olivia.l@example.com", rejectionEmail.getReceiver());
        assertTrue("Rejection email should contain rejection message", 
                  rejectionEmail.getContent().contains("has been rejected"));
    }
    
    @Test
    public void testCase3_ApproveAlreadyApprovedRecord() {
        // Setup for Test Case 3: Approve already approved record
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setCanApplyForIPO(true);
        
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");
        
        // Create an already approved application
        Application application = new Application(10, 5000.0, company, customer, document);
        application.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(application);
        
        // Attempt to approve an already approved application
        boolean result = application.approve();
        
        // Verify that approval fails for already approved application
        assertFalse("Should not be able to approve an already approved application", result);
        assertEquals("Application status should remain APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
        assertEquals("No new emails should be sent", 0, application.getEmails().size());
    }
    
    @Test
    public void testCase4_RejectAlreadyRejectedRecord() {
        // Setup for Test Case 4: Reject already rejected record
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setCanApplyForIPO(true);
        
        company.setName("Health");
        company.setEmail("health@gmail.com");
        
        // Create an already rejected application
        Application application = new Application(10, 5000.0, company, customer, document);
        application.setStatus(ApplicationStatus.REJECTED);
        customer.getApplications().add(application);
        
        // Attempt to reject an already rejected application
        boolean result = application.reject();
        
        // Verify that rejection fails for already rejected application
        assertFalse("Should not be able to reject an already rejected application", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
        assertEquals("No new emails should be sent", 0, application.getEmails().size());
    }
    
    @Test
    public void testCase5_ApproveRecordTiedToIneligibleCustomer() {
        // Setup for Test Case 5: Approve record tied to ineligible customer
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setCanApplyForIPO(false); // Customer is ineligible
        
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        // Create a pending application for ineligible customer
        Application application = new Application(10, 5000.0, company, customer, document);
        customer.getApplications().add(application);
        
        // Attempt to approve application for ineligible customer
        boolean result = application.approve();
        
        // Verify that approval fails for ineligible customer
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
        assertEquals("No emails should be sent", 0, application.getEmails().size());
    }
}