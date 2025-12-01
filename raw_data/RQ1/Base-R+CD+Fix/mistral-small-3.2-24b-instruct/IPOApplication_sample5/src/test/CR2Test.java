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
    public void testCase1_ApprovePendingRequest() {
        // Setup
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setCanApplyForIPO(true);
        
        company.setName("SolarMax");
        company.setEmail("solarmax@gmail.com");
        
        // Create application
        application = new Application(company, 10, 200.0, document, customer);
        customer.getApplications().add(application);
        
        // Test approval
        boolean result = application.approve();
        
        // Verify results
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
        assertEquals("Two emails should be sent (customer and company)", 2, application.getEmails().size());
    }
    
    @Test
    public void testCase2_RejectPendingRequest() {
        // Setup
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setCanApplyForIPO(true);
        
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");
        
        // Create application
        application = new Application(company, 10, 5000.0, document, customer);
        customer.getApplications().add(application);
        
        // Test rejection
        boolean result = application.reject();
        
        // Verify results
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, application.getStatus());
        assertEquals("One rejection email should be sent", 1, application.getEmails().size());
    }
    
    @Test
    public void testCase3_ApproveAlreadyApprovedRecord() {
        // Setup
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setCanApplyForIPO(true);
        
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");
        
        // Create already approved application
        application = new Application(company, 10, 5000.0, document, customer);
        application.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(application);
        
        // Try to approve again
        boolean result = application.approve();
        
        // Verify results
        assertFalse("Should not be able to approve an already approved application", result);
        assertEquals("Application status should remain APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
    }
    
    @Test
    public void testCase4_RejectAlreadyRejectedRecord() {
        // Setup
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setCanApplyForIPO(true);
        
        company.setName("Health");
        company.setEmail("health@gmail.com");
        
        // Create already rejected application
        application = new Application(company, 10, 5000.0, document, customer);
        application.setStatus(ApplicationStatus.REJECTED);
        customer.getApplications().add(application);
        
        // Try to reject again
        boolean result = application.reject();
        
        // Verify results
        assertFalse("Should not be able to reject an already rejected application", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase5_ApproveRecordTiedToIneligibleCustomer() {
        // Setup
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setCanApplyForIPO(false); // Customer is not eligible
        
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        // Create pending application for ineligible customer
        application = new Application(company, 10, 5000.0, document, customer);
        customer.getApplications().add(application);
        
        // Try to approve
        boolean result = application.approve();
        
        // Verify results
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
        assertTrue("No emails should be sent for failed approval", application.getEmails().isEmpty());
    }
}