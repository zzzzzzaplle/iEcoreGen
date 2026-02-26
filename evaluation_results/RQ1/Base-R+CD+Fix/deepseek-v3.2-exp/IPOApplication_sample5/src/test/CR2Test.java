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
        
        // Create application through customer's createApplication method
        boolean applicationCreated = customer.createApplication(company, 10, 200.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Get the created application
        Application app = customer.getApplications().get(0);
        app.setCustomer(customer);
        app.setCompany(company);
        
        // Test approval of pending application
        boolean result = app.approve();
        
        // Verify expected output
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVAL", ApplicationStatus.APPROVAL, app.getStatus());
        assertEquals("Should have 2 emails sent (customer and company)", 2, app.getEmails().size());
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
        
        // Create application through customer's createApplication method
        boolean applicationCreated = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Get the created application
        Application app = customer.getApplications().get(0);
        app.setCustomer(customer);
        app.setCompany(company);
        
        // Test rejection of pending application
        boolean result = app.reject();
        
        // Verify expected output
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, app.getStatus());
        assertEquals("Should have 1 rejection email sent", 1, app.getEmails().size());
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
        
        // Create and approve application
        boolean applicationCreated = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        Application app = customer.getApplications().get(0);
        app.setCustomer(customer);
        app.setCompany(company);
        
        // First approval should succeed
        boolean firstApproval = app.approve();
        assertTrue("First approval should succeed", firstApproval);
        
        // Attempt to approve again
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
        
        // Create and reject application
        boolean applicationCreated = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        Application app = customer.getApplications().get(0);
        app.setCustomer(customer);
        app.setCompany(company);
        
        // First rejection should succeed
        boolean firstRejection = app.reject();
        assertTrue("First rejection should succeed", firstRejection);
        
        // Attempt to reject again
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
        customer.setCanApplyForIPO(false); // Customer is NOT eligible for IPO
        
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        // Create application while customer is still eligible (simulating eligibility revocation after application creation)
        customer.setCanApplyForIPO(true);
        boolean applicationCreated = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        Application app = customer.getApplications().get(0);
        app.setCustomer(customer);
        app.setCompany(company);
        
        // Revoke customer eligibility (simulating KYC expiration)
        customer.setCanApplyForIPO(false);
        
        // Attempt to approve application for ineligible customer
        boolean approvalResult = app.approve();
        
        // Verify expected output
        assertFalse("Approval should fail for ineligible customer", approvalResult);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, app.getStatus());
        assertEquals("No emails should be sent", 0, app.getEmails().size());
    }
}