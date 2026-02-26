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
    public void testCase1_approvePendingRequest() {
        // Setup: Customer "C007" (named "Michael Brown", email "m.brown@example.com", phone "555-1122")
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setCanApplyForIPO(true);
        
        // Setup: Company "SolarMax" (email: solarmax@gmail.com)
        company.setName("SolarMax");
        company.setEmail("solarmax@gmail.com");
        
        // Create application for 10 shares ($200) with document 'S'
        boolean createResult = customer.createApplication(company, 10, 200.0, document);
        assertTrue("Application should be created successfully", createResult);
        
        // Get the created application
        Application app = customer.getApplications().get(0);
        
        // Set application reference for approval
        app.setCustomer(customer);
        app.setCompany(company);
        
        // Test: Bank approves application
        boolean result = app.approve();
        
        // Verify: True (status changes to APPROVAL)
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVAL", ApplicationStatus.APPROVAL, app.getStatus());
    }
    
    @Test
    public void testCase2_rejectPendingRequest() {
        // Setup: Customer "C008" (named "Olivia Lee", email "olivia.l@example.com", phone "555-3344")
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setCanApplyForIPO(true);
        
        // Setup: Company "HealthPlus" (email: healthplus@gmail.com)
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");
        
        // Create application for 10 shares ($5000) with document 'H'
        boolean createResult = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", createResult);
        
        // Get the created application
        Application app = customer.getApplications().get(0);
        
        // Set application reference for rejection
        app.setCustomer(customer);
        app.setCompany(company);
        
        // Test: Bank rejects application
        boolean result = app.reject();
        
        // Verify: True (status changes to REJECTED)
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, app.getStatus());
    }
    
    @Test
    public void testCase3_approveAlreadyApprovedRecord() {
        // Setup: Customer "C009" (named "Daniel Kim", email "d.kim@example.com", phone "555-5566")
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setCanApplyForIPO(true);
        
        // Setup: Company "HealthPlus" (email: healthplus@gmail.com)
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");
        
        // Create application for 10 shares ($5000) with document 'H'
        boolean createResult = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", createResult);
        
        // Get the created application
        Application app = customer.getApplications().get(0);
        
        // Set application reference and approve it first
        app.setCustomer(customer);
        app.setCompany(company);
        
        // First approval
        boolean firstApproveResult = app.approve();
        assertTrue("First approval should succeed", firstApproveResult);
        assertEquals("Application status should be APPROVAL", ApplicationStatus.APPROVAL, app.getStatus());
        
        // Test: Bank attempts to re-approve already approved application
        boolean secondApproveResult = app.approve();
        
        // Verify: False (cannot modify approved applications)
        assertFalse("Should not be able to approve an already approved application", secondApproveResult);
        assertEquals("Application status should remain APPROVAL", ApplicationStatus.APPROVAL, app.getStatus());
    }
    
    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Setup: Customer "C010" (named "Sophie Zhang", email "s.zhang@example.com", phone "555-7788")
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setCanApplyForIPO(true);
        
        // Setup: Company "Health" (email: health@gmail.com)
        company.setName("Health");
        company.setEmail("health@gmail.com");
        
        // Create application for 10 shares ($5000)
        boolean createResult = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", createResult);
        
        // Get the created application
        Application app = customer.getApplications().get(0);
        
        // Set application reference and reject it first
        app.setCustomer(customer);
        app.setCompany(company);
        
        // First rejection
        boolean firstRejectResult = app.reject();
        assertTrue("First rejection should succeed", firstRejectResult);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, app.getStatus());
        
        // Test: Bank tries to reject already rejected application
        boolean secondRejectResult = app.reject();
        
        // Verify: False (cannot modify final decisions)
        assertFalse("Should not be able to reject an already rejected application", secondRejectResult);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, app.getStatus());
    }
    
    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Setup: Customer "C011" (named "William Wang", email "will.w@example.com", phone "555-9900")
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        
        // Customer cannot apply for IPO (eligibility revoked)
        customer.setCanApplyForIPO(false);
        
        // Setup: Company "Cloud" (email: Cloud@gmail.com)
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        // Create pending application for 10 shares ($5000) - customer was eligible when application was created
        boolean createResult = customer.createApplication(company, 10, 5000.0, document);
        
        // Application should not be created since customer is not eligible
        assertFalse("Application should not be created for ineligible customer", createResult);
        
        // For the purpose of this test, we'll manually create an application to simulate the scenario
        Application app = new Application();
        app.setShare(10);
        app.setAmountOfMoney(5000.0);
        app.setStatus(ApplicationStatus.PENDING);
        app.setCustomer(customer);
        app.setCompany(company);
        app.setAllowance(document);
        app.setEmails(new ArrayList<>());
        
        customer.getApplications().add(app);
        
        // Test: Bank processes application for ineligible customer
        boolean result = app.approve();
        
        // Verify: False (must maintain eligibility throughout process)
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, app.getStatus());
    }
}