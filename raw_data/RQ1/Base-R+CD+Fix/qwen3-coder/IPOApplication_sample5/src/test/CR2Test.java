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
        // Setup
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
        app.setCustomer(customer);
        app.setCompany(company);
        
        // Test approval
        boolean result = app.approve();
        
        // Verify results
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVAL", ApplicationStatus.APPROVAL, app.getStatus());
        assertEquals("Should have 2 emails sent", 2, app.getEmails().size());
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
        boolean applicationCreated = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Get the created application
        Application app = customer.getApplications().get(0);
        app.setCustomer(customer);
        app.setCompany(company);
        
        // Test rejection
        boolean result = app.reject();
        
        // Verify results
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, app.getStatus());
        assertEquals("Should have 1 rejection email sent", 1, app.getEmails().size());
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
        
        // Create and manually set application as approved
        Application app = new Application();
        app.setShare(10);
        app.setAmountOfMoney(5000.0);
        app.setStatus(ApplicationStatus.APPROVAL);
        app.setCustomer(customer);
        app.setCompany(company);
        app.setAllowance(document);
        app.setEmails(new ArrayList<>());
        
        customer.getApplications().add(app);
        
        // Test approval on already approved application
        boolean result = app.approve();
        
        // Verify results
        assertFalse("Should not be able to approve already approved application", result);
        assertEquals("Application status should remain APPROVAL", ApplicationStatus.APPROVAL, app.getStatus());
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
        
        // Create and manually set application as rejected
        Application app = new Application();
        app.setShare(10);
        app.setAmountOfMoney(5000.0);
        app.setStatus(ApplicationStatus.REJECTED);
        app.setCustomer(customer);
        app.setCompany(company);
        app.setAllowance(document);
        app.setEmails(new ArrayList<>());
        
        customer.getApplications().add(app);
        
        // Test rejection on already rejected application
        boolean result = app.reject();
        
        // Verify results
        assertFalse("Should not be able to reject already rejected application", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, app.getStatus());
    }
    
    @Test
    public void testCase5_ApproveRecordTiedToIneligibleCustomer() {
        // Setup
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setCanApplyForIPO(false); // Customer is ineligible
        
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        // Create application while customer was eligible (simulating eligibility revoked later)
        customer.setCanApplyForIPO(true);
        boolean applicationCreated = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Get the created application
        Application app = customer.getApplications().get(0);
        app.setCustomer(customer);
        app.setCompany(company);
        
        // Make customer ineligible (simulating KYC expiration)
        customer.setCanApplyForIPO(false);
        
        // Test approval with ineligible customer
        boolean result = app.approve();
        
        // Verify results
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, app.getStatus());
    }
}