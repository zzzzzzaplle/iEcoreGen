import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    private Customer customer;
    private Company company;
    private Application application;
    private Document document;
    
    @Before
    public void setUp() {
        customer = new Customer();
        company = new Company();
        application = new Application();
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
        
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(200.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application);
        
        // Execute
        boolean result = application.approve();
        
        // Verify
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
        
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application);
        
        // Execute
        boolean result = application.reject();
        
        // Verify
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
        
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.APPROVAL);
        
        customer.getApplications().add(application);
        
        // Execute
        boolean result = application.approve();
        
        // Verify
        assertFalse("Cannot approve an already approved application", result);
        assertEquals("Application status should remain APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
        assertEquals("No new emails should be sent", 0, application.getEmails().size());
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
        
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.REJECTED);
        
        customer.getApplications().add(application);
        
        // Execute
        boolean result = application.reject();
        
        // Verify
        assertFalse("Cannot reject an already rejected application", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
        assertEquals("No new emails should be sent", 0, application.getEmails().size());
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
        
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application);
        
        // Execute
        boolean result = application.approve();
        
        // Verify
        assertFalse("Cannot approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
        assertEquals("No emails should be sent", 0, application.getEmails().size());
    }
}