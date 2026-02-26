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
        customer = new Customer();
        company = new Company();
        document = new Document();
    }
    
    @Test
    public void testCase1_approvePendingRequest() {
        // Setup: Customer "C007" with pending application for "SolarMax"
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setCanApplyForIPO(true);
        
        company.setName("SolarMax");
        company.setEmail("solarmax@gmail.com");
        
        // Create pending application
        boolean created = customer.createApplication(company, 10, 200.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Get the created application
        application = customer.getApplications().get(0);
        
        // Test: Approve the pending application
        boolean result = application.approve();
        
        // Verify: Approval should succeed and status should be APPROVED
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVED", 
                     ApplicationStatus.APPROVED, application.getStatus());
        
        // Verify emails were sent
        assertEquals("Should have 2 emails sent", 2, application.getEmails().size());
        assertEquals("First email should be to customer", 
                     "m.brown@example.com", application.getEmails().get(0).getReceiver());
        assertEquals("Second email should be to company", 
                     "solarmax@gmail.com", application.getEmails().get(1).getReceiver());
    }
    
    @Test
    public void testCase2_rejectPendingRequest() {
        // Setup: Customer "C008" with pending application for "HealthPlus"
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setCanApplyForIPO(true);
        
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");
        
        // Create pending application
        boolean created = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Get the created application
        application = customer.getApplications().get(0);
        
        // Test: Reject the pending application
        boolean result = application.reject();
        
        // Verify: Rejection should succeed and status should be REJECTED
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", 
                     ApplicationStatus.REJECTED, application.getStatus());
        
        // Verify rejection email was sent
        assertEquals("Should have 1 rejection email", 1, application.getEmails().size());
        assertEquals("Email should be to customer", 
                     "olivia.l@example.com", application.getEmails().get(0).getReceiver());
        assertEquals("Email content should be rejection notice", 
                     "Your application for IPO has been rejected.", 
                     application.getEmails().get(0).getContent());
    }
    
    @Test
    public void testCase3_approveAlreadyApprovedRecord() {
        // Setup: Customer "C009" with already approved application
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setCanApplyForIPO(true);
        
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");
        
        // Create and approve application
        boolean created = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        application = customer.getApplications().get(0);
        boolean approved = application.approve();
        assertTrue("Application should be approved initially", approved);
        
        // Test: Try to approve again
        boolean result = application.approve();
        
        // Verify: Should return false since already approved
        assertFalse("Should not be able to approve already approved application", result);
        assertEquals("Status should remain APPROVED", 
                     ApplicationStatus.APPROVED, application.getStatus());
    }
    
    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Setup: Customer "C010" with already rejected application
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setCanApplyForIPO(true);
        
        company.setName("Health");
        company.setEmail("health@gmail.com");
        
        // Create and reject application
        boolean created = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        application = customer.getApplications().get(0);
        boolean rejected = application.reject();
        assertTrue("Application should be rejected initially", rejected);
        
        // Test: Try to reject again
        boolean result = application.reject();
        
        // Verify: Should return false since already rejected
        assertFalse("Should not be able to reject already rejected application", result);
        assertEquals("Status should remain REJECTED", 
                     ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Setup: Customer "C011" who is ineligible for IPO
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setCanApplyForIPO(false); // Ineligible customer
        
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        // Create pending application (note: customer was eligible when creating)
        customer.setCanApplyForIPO(true); // Temporarily eligible for creation
        boolean created = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Make customer ineligible
        customer.setCanApplyForIPO(false);
        
        // Get the created application
        application = customer.getApplications().get(0);
        
        // Test: Try to approve application for ineligible customer
        boolean result = application.approve();
        
        // Verify: Should return false due to customer ineligibility
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertEquals("Status should remain PENDING", 
                     ApplicationStatus.PENDING, application.getStatus());
        assertEquals("No emails should be sent for failed approval", 
                     0, application.getEmails().size());
    }
}