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
        // Setup for Test Case 1: Approve pending request
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setCanApplyForIPO(true);
        
        company.setName("SolarMax");
        company.setEmail("solarmax@gmail.com");
        
        application.setShare(10);
        application.setAmountOfMoney(200.0);
        application.setStatus(ApplicationStatus.PENDING);
        application.setCustomer(customer);
        application.setCompany(company);
        application.setAllowance(document);
        
        customer.getApplications().add(application);
        
        // Execute: Approve the application
        boolean result = application.approve();
        
        // Verify
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVED", ApplicationStatus.APPROVED, application.getStatus());
        assertEquals("Should have 2 emails sent (customer and company)", 2, application.getEmails().size());
    }
    
    @Test
    public void testCase2_rejectPendingRequest() {
        // Setup for Test Case 2: Reject pending request
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setCanApplyForIPO(true);
        
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");
        
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setStatus(ApplicationStatus.PENDING);
        application.setCustomer(customer);
        application.setCompany(company);
        application.setAllowance(document);
        
        customer.getApplications().add(application);
        
        // Execute: Reject the application
        boolean result = application.reject();
        
        // Verify
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, application.getStatus());
        assertEquals("Should have 1 email sent (rejection)", 1, application.getEmails().size());
    }
    
    @Test
    public void testCase3_approveAlreadyApprovedRecord() {
        // Setup for Test Case 3: Approve already approved record
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setCanApplyForIPO(true);
        
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");
        
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setStatus(ApplicationStatus.APPROVED);
        application.setCustomer(customer);
        application.setCompany(company);
        application.setAllowance(document);
        
        customer.getApplications().add(application);
        
        // Execute: Attempt to approve already approved application
        boolean result = application.approve();
        
        // Verify
        assertFalse("Should not be able to approve already approved application", result);
        assertEquals("Application status should remain APPROVED", ApplicationStatus.APPROVED, application.getStatus());
    }
    
    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Setup for Test Case 4: Reject already rejected record
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setCanApplyForIPO(true);
        
        company.setName("Health");
        company.setEmail("health@gmail.com");
        
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setStatus(ApplicationStatus.REJECTED);
        application.setCustomer(customer);
        application.setCompany(company);
        application.setAllowance(document);
        
        customer.getApplications().add(application);
        
        // Execute: Attempt to reject already rejected application
        boolean result = application.reject();
        
        // Verify
        assertFalse("Should not be able to reject already rejected application", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Setup for Test Case 5: Approve record tied to ineligible customer
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setCanApplyForIPO(false); // Customer is ineligible
        
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setStatus(ApplicationStatus.PENDING);
        application.setCustomer(customer);
        application.setCompany(company);
        application.setAllowance(document);
        
        customer.getApplications().add(application);
        
        // Execute: Attempt to approve application for ineligible customer
        boolean result = application.approve();
        
        // Verify
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
        assertTrue("No emails should be sent", application.getEmails().isEmpty());
    }
}