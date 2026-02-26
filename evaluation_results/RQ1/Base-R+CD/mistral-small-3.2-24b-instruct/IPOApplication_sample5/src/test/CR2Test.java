import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private Customer customer;
    private Company company;
    private Application application;
    
    @Before
    public void setUp() {
        customer = new Customer();
        company = new Company();
        application = new Application();
    }
    
    @Test
    public void testCase1_approvePendingRequest() {
        // Setup
        customer.setName("Michael Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setCanApplyForIPO(true);
        
        company.setName("SolarMax");
        company.setEmail("solarmax@gmail.com");
        
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(200);
        application.setStatus(ApplicationStatus.PENDING);
        application.setAllowance(new Document());
        
        customer.getApplications().add(application);
        
        // Execute
        boolean result = application.approve();
        
        // Verify
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVED", ApplicationStatus.APPROVED, application.getStatus());
        assertEquals("Should have 2 emails sent", 2, application.getEmails().size());
    }
    
    @Test
    public void testCase2_rejectPendingRequest() {
        // Setup
        customer.setName("Olivia Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setCanApplyForIPO(true);
        
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");
        
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(5000);
        application.setStatus(ApplicationStatus.PENDING);
        application.setAllowance(new Document());
        
        customer.getApplications().add(application);
        
        // Execute
        boolean result = application.reject();
        
        // Verify
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, application.getStatus());
        assertEquals("Should have 1 rejection email sent", 1, application.getEmails().size());
    }
    
    @Test
    public void testCase3_approveAlreadyApprovedRecord() {
        // Setup
        customer.setName("Daniel Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setCanApplyForIPO(true);
        
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");
        
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(5000);
        application.setStatus(ApplicationStatus.APPROVED);
        application.setAllowance(new Document());
        
        customer.getApplications().add(application);
        
        // Execute
        boolean result = application.approve();
        
        // Verify
        assertFalse("Cannot approve already approved application", result);
        assertEquals("Application status should remain APPROVED", ApplicationStatus.APPROVED, application.getStatus());
    }
    
    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Setup
        customer.setName("Sophie Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setCanApplyForIPO(true);
        
        company.setName("Health");
        company.setEmail("health@gmail.com");
        
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(5000);
        application.setStatus(ApplicationStatus.REJECTED);
        application.setAllowance(new Document());
        
        customer.getApplications().add(application);
        
        // Execute
        boolean result = application.reject();
        
        // Verify
        assertTrue("Reject should return true even for rejected application", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Setup
        customer.setName("William Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setCanApplyForIPO(false); // Customer is not eligible
        
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(5000);
        application.setStatus(ApplicationStatus.PENDING);
        application.setAllowance(new Document());
        
        customer.getApplications().add(application);
        
        // Execute
        boolean result = application.approve();
        
        // Verify
        assertFalse("Cannot approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
        assertEquals("No emails should be sent", 0, application.getEmails().size());
    }
}