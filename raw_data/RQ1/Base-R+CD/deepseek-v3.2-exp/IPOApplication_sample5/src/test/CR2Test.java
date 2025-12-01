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
        document = new Document();
    }
    
    @Test
    public void testCase1_approvePendingRequest() {
        // Setup
        customer = new Customer();
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setCanApplyForIPO(true);
        
        company = new Company();
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
        assertTrue("Approval should be successful", result);
        assertEquals("Status should be APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
        assertEquals("Should have 2 emails (customer and company)", 2, application.getEmails().size());
        
        // Verify email content
        Email customerEmail = application.getEmails().get(0);
        assertEquals("Customer email recipient should match", "m.brown@example.com", customerEmail.getReceiver());
        assertTrue("Customer email content should contain customer info", 
                  customerEmail.getContent().contains("Michael Brown"));
        
        Email companyEmail = application.getEmails().get(1);
        assertEquals("Company email recipient should match", "solarmax@gmail.com", companyEmail.getReceiver());
        assertTrue("Company email content should contain company info", 
                  companyEmail.getContent().contains("SolarMax"));
    }
    
    @Test
    public void testCase2_rejectPendingRequest() {
        // Setup
        customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setCanApplyForIPO(true);
        
        company = new Company();
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
        assertTrue("Rejection should be successful", result);
        assertEquals("Status should be REJECTED", ApplicationStatus.REJECTED, application.getStatus());
        assertEquals("Should have 1 email (rejection)", 1, application.getEmails().size());
        
        // Verify rejection email
        Email rejectionEmail = application.getEmails().get(0);
        assertEquals("Rejection email recipient should match", "olivia.l@example.com", rejectionEmail.getReceiver());
        assertTrue("Rejection email should contain rejection message", 
                  rejectionEmail.getContent().contains("has been rejected"));
        assertTrue("Rejection email should contain company name", 
                  rejectionEmail.getContent().contains("HealthPlus"));
    }
    
    @Test
    public void testCase3_approveAlreadyApprovedRecord() {
        // Setup
        customer = new Customer();
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setCanApplyForIPO(true);
        
        company = new Company();
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");
        
        application = new Application();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.APPROVAL); // Already approved
        
        // Execute - attempt to approve already approved application
        boolean result = application.approve();
        
        // Verify
        assertFalse("Should not be able to approve already approved application", result);
        assertEquals("Status should remain APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
        // No new emails should be sent for already approved application
        assertEquals("Should have no emails for already approved app", 0, application.getEmails().size());
    }
    
    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Setup
        customer = new Customer();
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setCanApplyForIPO(true);
        
        company = new Company();
        company.setName("Health");
        company.setEmail("health@gmail.com");
        
        application = new Application();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.REJECTED); // Already rejected
        
        // Execute - attempt to reject already rejected application
        boolean result = application.reject();
        
        // Verify
        assertFalse("Should not be able to reject already rejected application", result);
        assertEquals("Status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
        // No new emails should be sent for already rejected application
        assertEquals("Should have no emails for already rejected app", 0, application.getEmails().size());
    }
    
    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Setup
        customer = new Customer();
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setCanApplyForIPO(false); // Ineligible customer
        
        company = new Company();
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        application = new Application();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        // Execute - attempt to approve application for ineligible customer
        boolean result = application.approve();
        
        // Verify
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertEquals("Status should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
        // No emails should be sent for failed approval
        assertEquals("Should have no emails for failed approval", 0, application.getEmails().size());
    }
}