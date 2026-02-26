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
        
        application = new Application();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(200.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        // Execute
        boolean result = application.approve();
        
        // Verify
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
        assertEquals("Should have 2 emails sent (customer and company)", 2, application.getEmails().size());
        
        // Verify email contents
        Email customerEmail = application.getEmails().get(0);
        assertEquals("Customer email recipient should match", "m.brown@example.com", customerEmail.getReceiver());
        assertTrue("Customer email content should contain customer name", customerEmail.getContent().contains("Michael Brown"));
        assertTrue("Customer email content should contain company name", customerEmail.getContent().contains("SolarMax"));
        
        Email companyEmail = application.getEmails().get(1);
        assertEquals("Company email recipient should match", "solarmax@gmail.com", companyEmail.getReceiver());
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
        
        application = new Application();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        // Execute
        boolean result = application.reject();
        
        // Verify
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, application.getStatus());
        assertEquals("Should have 1 rejection email sent", 1, application.getEmails().size());
        
        Email rejectionEmail = application.getEmails().get(0);
        assertEquals("Rejection email recipient should match", "olivia.l@example.com", rejectionEmail.getReceiver());
        assertTrue("Rejection email should contain company name", rejectionEmail.getContent().contains("HealthPlus"));
        assertTrue("Rejection email should contain customer name", rejectionEmail.getContent().contains("Olivia Lee"));
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
        
        application = new Application();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.APPROVAL); // Already approved
        
        // Execute
        boolean result = application.approve();
        
        // Verify
        assertFalse("Should not be able to approve already approved application", result);
        assertEquals("Application status should remain APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
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
        
        application = new Application();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.REJECTED); // Already rejected
        
        // Execute
        boolean result = application.reject();
        
        // Verify
        assertFalse("Should not be able to reject already rejected application", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase5_ApproveRecordTiedToIneligibleCustomer() {
        // Setup
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setCanApplyForIPO(false); // Ineligible customer
        
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        application = new Application();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        // Execute
        boolean result = application.approve();
        
        // Verify
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
        assertEquals("No emails should be sent for failed approval", 0, application.getEmails().size());
    }
}