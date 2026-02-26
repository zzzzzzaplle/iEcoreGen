import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
    public void testCase1_ApprovePendingRequest() {
        // Setup for Test Case 1: "Approve pending request"
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setCanApplyForIPO(true);
        
        company.setName("SolarMax");
        company.setEmail("solarmax@gmail.com");
        
        // Create application through customer
        boolean created = customer.createApplication(company, 10, 200.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Get the created application
        List<Application> applications = customer.getApplications();
        assertEquals("Should have exactly one application", 1, applications.size());
        Application app = applications.get(0);
        
        // Verify initial status is PENDING
        assertEquals("Application should start as PENDING", ApplicationStatus.PENDING, app.getStatus());
        
        // Test approval
        boolean result = app.approve();
        
        // Verify results
        assertTrue("Approval should succeed", result);
        assertEquals("Status should change to APPROVAL", ApplicationStatus.APPROVAL, app.getStatus());
        assertEquals("Should have 2 emails sent (customer and company)", 2, app.getEmails().size());
    }
    
    @Test
    public void testCase2_RejectPendingRequest() {
        // Setup for Test Case 2: "Reject pending request"
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setCanApplyForIPO(true);
        
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");
        
        // Create application through customer
        boolean created = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Get the created application
        List<Application> applications = customer.getApplications();
        assertEquals("Should have exactly one application", 1, applications.size());
        Application app = applications.get(0);
        
        // Verify initial status is PENDING
        assertEquals("Application should start as PENDING", ApplicationStatus.PENDING, app.getStatus());
        
        // Test rejection
        boolean result = app.reject();
        
        // Verify results
        assertTrue("Rejection should succeed", result);
        assertEquals("Status should change to REJECTED", ApplicationStatus.REJECTED, app.getStatus());
        assertEquals("Should have 1 rejection email sent", 1, app.getEmails().size());
    }
    
    @Test
    public void testCase3_ApproveAlreadyApprovedRecord() {
        // Setup for Test Case 3: "Approve already approved record"
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setCanApplyForIPO(true);
        
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");
        
        // Create and approve application first
        boolean created = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        List<Application> applications = customer.getApplications();
        Application app = applications.get(0);
        
        // First approval should succeed
        boolean firstApproval = app.approve();
        assertTrue("First approval should succeed", firstApproval);
        assertEquals("Status should be APPROVAL", ApplicationStatus.APPROVAL, app.getStatus());
        
        // Try to approve again
        boolean secondApproval = app.approve();
        
        // Verify second approval fails
        assertFalse("Second approval should fail", secondApproval);
        assertEquals("Status should remain APPROVAL", ApplicationStatus.APPROVAL, app.getStatus());
    }
    
    @Test
    public void testCase4_RejectAlreadyRejectedRecord() {
        // Setup for Test Case 4: "Reject already rejected record"
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setCanApplyForIPO(true);
        
        company.setName("Health");
        company.setEmail("health@gmail.com");
        
        // Create and reject application first
        boolean created = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        List<Application> applications = customer.getApplications();
        Application app = applications.get(0);
        
        // First rejection should succeed
        boolean firstRejection = app.reject();
        assertTrue("First rejection should succeed", firstRejection);
        assertEquals("Status should be REJECTED", ApplicationStatus.REJECTED, app.getStatus());
        
        // Try to reject again
        boolean secondRejection = app.reject();
        
        // Verify second rejection fails
        assertFalse("Second rejection should fail", secondRejection);
        assertEquals("Status should remain REJECTED", ApplicationStatus.REJECTED, app.getStatus());
    }
    
    @Test
    public void testCase5_ApproveRecordTiedToIneligibleCustomer() {
        // Setup for Test Case 5: "Approve record tied to ineligible customer"
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setCanApplyForIPO(true); // Initially eligible to create application
        
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        // Create application while customer is still eligible
        boolean created = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        List<Application> applications = customer.getApplications();
        Application app = applications.get(0);
        
        // Make customer ineligible (simulating KYC expiration)
        customer.setCanApplyForIPO(false);
        
        // Try to approve with ineligible customer
        boolean approvalResult = app.approve();
        
        // Verify approval fails due to ineligible customer
        assertFalse("Approval should fail for ineligible customer", approvalResult);
        assertEquals("Status should remain PENDING", ApplicationStatus.PENDING, app.getStatus());
        assertEquals("No emails should be sent", 0, app.getEmails().size());
    }
}