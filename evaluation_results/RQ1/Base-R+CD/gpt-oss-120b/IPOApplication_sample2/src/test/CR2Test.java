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
        // Setup for Test Case 1: "Approve pending request"
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setCanApplyForIPO(true);
        
        company.setName("SolarMax");
        company.setEmail("solarmax@gmail.com");
        
        // Create application
        boolean applicationCreated = customer.createApplication(company, 10, 200.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Get the created application
        Application app = customer.getApplications().get(0);
        
        // Test approval
        boolean result = app.approve();
        
        // Verify expected output
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVAL", ApplicationStatus.APPROVAL, app.getStatus());
    }
    
    @Test
    public void testCase2_rejectPendingRequest() {
        // Setup for Test Case 2: "Reject pending request"
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setCanApplyForIPO(true);
        
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");
        
        // Create application
        boolean applicationCreated = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Get the created application
        Application app = customer.getApplications().get(0);
        
        // Test rejection
        boolean result = app.reject();
        
        // Verify expected output
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, app.getStatus());
    }
    
    @Test
    public void testCase3_approveAlreadyApprovedRecord() {
        // Setup for Test Case 3: "Approve already approved record"
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setCanApplyForIPO(true);
        
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");
        
        // Create and approve application first
        boolean applicationCreated = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        Application app = customer.getApplications().get(0);
        boolean firstApproval = app.approve();
        assertTrue("First approval should succeed", firstApproval);
        
        // Try to approve again
        boolean secondApproval = app.approve();
        
        // Verify expected output
        assertFalse("Second approval should fail for already approved application", secondApproval);
        assertEquals("Application status should remain APPROVAL", ApplicationStatus.APPROVAL, app.getStatus());
    }
    
    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Setup for Test Case 4: "Reject already rejected record"
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setCanApplyForIPO(true);
        
        company.setName("Health");
        company.setEmail("health@gmail.com");
        
        // Create and reject application first
        boolean applicationCreated = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        Application app = customer.getApplications().get(0);
        boolean firstRejection = app.reject();
        assertTrue("First rejection should succeed", firstRejection);
        
        // Try to reject again
        boolean secondRejection = app.reject();
        
        // Verify expected output
        assertFalse("Second rejection should fail for already rejected application", secondRejection);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, app.getStatus());
    }
    
    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Setup for Test Case 5: "Approve record tied to ineligible customer"
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setCanApplyForIPO(false); // Customer is ineligible
        
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        // Create application (this should succeed even if customer is ineligible, as per the source code)
        boolean applicationCreated = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Get the created application
        Application app = customer.getApplications().get(0);
        
        // Test approval with ineligible customer
        boolean result = app.approve();
        
        // Verify expected output
        assertFalse("Approval should fail for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, app.getStatus());
    }
}