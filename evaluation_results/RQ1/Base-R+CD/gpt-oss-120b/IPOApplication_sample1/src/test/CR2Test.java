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
        application = new Application();
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
        
        // Create application through customer's createApplication method
        boolean applicationCreated = customer.createApplication(company, 10, 200.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Get the created application
        Application app = customer.getApplications().get(0);
        app.setStatus(ApplicationStatus.PENDING);
        
        // Test: Approve the pending application
        boolean result = app.approve();
        
        // Verify
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVAL", ApplicationStatus.APPROVAL, app.getStatus());
        assertEquals("Should have 2 emails sent (customer and company)", 2, app.getEmails().size());
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
        
        // Create application through customer's createApplication method
        boolean applicationCreated = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Get the created application
        Application app = customer.getApplications().get(0);
        app.setStatus(ApplicationStatus.PENDING);
        
        // Test: Reject the pending application
        boolean result = app.reject();
        
        // Verify
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, app.getStatus());
        assertEquals("Should have 1 rejection email sent", 1, app.getEmails().size());
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
        
        // Create application and set it to APPROVED status
        Application app = new Application();
        app.setCustomer(customer);
        app.setCompany(company);
        app.setShare(10);
        app.setAmountOfMoney(5000.0);
        app.setAllowance(document);
        app.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app);
        
        // Test: Try to approve an already approved application
        boolean result = app.approve();
        
        // Verify
        assertFalse("Should not be able to approve an already approved application", result);
        assertEquals("Application status should remain APPROVAL", ApplicationStatus.APPROVAL, app.getStatus());
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
        
        // Create application and set it to REJECTED status
        Application app = new Application();
        app.setCustomer(customer);
        app.setCompany(company);
        app.setShare(10);
        app.setAmountOfMoney(5000.0);
        app.setAllowance(document);
        app.setStatus(ApplicationStatus.REJECTED);
        customer.getApplications().add(app);
        
        // Test: Try to reject an already rejected application
        boolean result = app.reject();
        
        // Verify
        assertFalse("Should not be able to reject an already rejected application", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, app.getStatus());
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
        
        // Create application through customer's createApplication method
        // This should fail since customer is ineligible
        boolean applicationCreated = customer.createApplication(company, 10, 5000.0, document);
        assertFalse("Application should not be created for ineligible customer", applicationCreated);
        
        // Manually create application with PENDING status for testing
        Application app = new Application();
        app.setCustomer(customer);
        app.setCompany(company);
        app.setShare(10);
        app.setAmountOfMoney(5000.0);
        app.setAllowance(document);
        app.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(app);
        
        // Test: Try to approve application for ineligible customer
        boolean result = app.approve();
        
        // Verify
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, app.getStatus());
    }
}