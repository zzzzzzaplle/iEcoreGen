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
        // Setup: Customer "C007" with pending application for "SolarMax"
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setCanApplyForIPO(true);
        
        company.setName("SolarMax");
        company.setEmail("solarmax@gmail.com");
        
        // Create application and set to PENDING
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(200.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application);
        
        // Execute: Approve the application
        boolean result = application.approve();
        
        // Verify: Should return true and status should be APPROVAL
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
        assertEquals("Should have 2 emails (customer and company)", 2, application.getEmails().size());
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
        
        // Create application and set to PENDING
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application);
        
        // Execute: Reject the application
        boolean result = application.reject();
        
        // Verify: Should return true and status should be REJECTED
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, application.getStatus());
        assertEquals("Should have 1 email (rejection notice)", 1, application.getEmails().size());
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
        
        // Create application and set to APPROVAL (already approved)
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.APPROVAL);
        
        customer.getApplications().add(application);
        
        // Execute: Attempt to approve already approved application
        boolean result = application.approve();
        
        // Verify: Should return false and status should remain APPROVAL
        assertFalse("Should not be able to approve already approved application", result);
        assertEquals("Application status should remain APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
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
        
        // Create application and set to REJECTED (already rejected)
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.REJECTED);
        
        customer.getApplications().add(application);
        
        // Execute: Attempt to reject already rejected application
        boolean result = application.reject();
        
        // Verify: Should return false and status should remain REJECTED
        assertFalse("Should not be able to reject already rejected application", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Setup: Customer "C011" who is not eligible for IPO
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setCanApplyForIPO(false); // Customer is not eligible
        
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        // Create application and set to PENDING
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application);
        
        // Execute: Attempt to approve application for ineligible customer
        boolean result = application.approve();
        
        // Verify: Should return false and status should remain PENDING
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
    }
}