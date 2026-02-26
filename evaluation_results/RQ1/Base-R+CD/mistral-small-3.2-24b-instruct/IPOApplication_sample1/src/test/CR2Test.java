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
        // Setup
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setCanApplyForIPO(true);
        
        company.setName("SolarMax");
        company.setEmail("solarmax@gmail.com");
        
        // Create application
        boolean created = customer.createApplication(company, 10, 200.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Get the created application
        application = customer.getApplications().get(0);
        
        // Test: Approve the pending application
        boolean result = application.approve();
        
        // Verify
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVED", ApplicationStatus.APPROVED, application.getStatus());
        assertEquals("Should have 2 emails sent (customer and company)", 2, application.getEmails().size());
    }
    
    @Test
    public void testCase2_rejectPendingRequest() {
        // Setup
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setCanApplyForIPO(true);
        
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");
        
        // Create application
        boolean created = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Get the created application
        application = customer.getApplications().get(0);
        
        // Test: Reject the pending application
        boolean result = application.reject();
        
        // Verify
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, application.getStatus());
        assertEquals("Should have 1 rejection email sent", 1, application.getEmails().size());
    }
    
    @Test
    public void testCase3_approveAlreadyApprovedRecord() {
        // Setup
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setCanApplyForIPO(true);
        
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");
        
        // Create application
        boolean created = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Get the created application
        application = customer.getApplications().get(0);
        
        // First approval
        boolean firstApproval = application.approve();
        assertTrue("First approval should succeed", firstApproval);
        
        // Test: Attempt to approve already approved application
        boolean result = application.approve();
        
        // Verify
        assertFalse("Should not be able to approve an already approved application", result);
        assertEquals("Application status should remain APPROVED", ApplicationStatus.APPROVED, application.getStatus());
    }
    
    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Setup
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setCanApplyForIPO(true);
        
        company.setName("Health");
        company.setEmail("health@gmail.com");
        
        // Create application
        boolean created = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Get the created application
        application = customer.getApplications().get(0);
        
        // First rejection
        boolean firstRejection = application.reject();
        assertTrue("First rejection should succeed", firstRejection);
        
        // Test: Attempt to reject already rejected application
        boolean result = application.reject();
        
        // Verify
        assertFalse("Should not be able to reject an already rejected application", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Setup
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setCanApplyForIPO(false); // Customer is ineligible
        
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        // Create application (customer was eligible when creating application)
        customer.setCanApplyForIPO(true); // Temporarily set to eligible for creation
        boolean created = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Get the created application
        application = customer.getApplications().get(0);
        
        // Make customer ineligible for approval
        customer.setCanApplyForIPO(false);
        
        // Test: Attempt to approve application for ineligible customer
        boolean result = application.approve();
        
        // Verify
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
        assertTrue("No emails should be sent for failed approval", application.getEmails().isEmpty());
    }
}