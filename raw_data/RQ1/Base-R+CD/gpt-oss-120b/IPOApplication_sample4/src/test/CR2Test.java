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
        // Common setup for tests
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
        
        application.setShare(10);
        application.setAmountOfMoney(200.0);
        application.setStatus(ApplicationStatus.PENDING);
        application.setCustomer(customer);
        application.setCompany(company);
        application.setAllowance(document);
        
        // Execute approval
        boolean result = application.approve();
        
        // Verify results
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
        assertEquals("Should have 2 emails sent (customer and company)", 2, application.getEmails().size());
        
        // Verify email content
        Email customerEmail = application.getEmails().get(0);
        assertEquals("Customer email receiver should match", "m.brown@example.com", customerEmail.getReceiver());
        assertTrue("Email content should contain customer name", customerEmail.getContent().contains("Michael Brown"));
        assertTrue("Email content should contain company name", customerEmail.getContent().contains("SolarMax"));
        assertTrue("Email content should contain shares info", customerEmail.getContent().contains("Shares Purchased: 10"));
        assertTrue("Email content should contain amount info", customerEmail.getContent().contains("Amount Paid: $200.00"));
        
        Email companyEmail = application.getEmails().get(1);
        assertEquals("Company email receiver should match", "solarmax@gmail.com", companyEmail.getReceiver());
        assertEquals("Company email content should be same as customer email", customerEmail.getContent(), companyEmail.getContent());
    }
    
    @Test
    public void testCase2_rejectPendingRequest() {
        // Setup for Test Case 2: "Reject pending request"
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setCanApplyForIPO(true);
        int initialFailedAttempts = customer.getFailedAttempts();
        
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");
        
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setStatus(ApplicationStatus.PENDING);
        application.setCustomer(customer);
        application.setCompany(company);
        application.setAllowance(document);
        
        // Execute rejection
        boolean result = application.reject();
        
        // Verify results
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, application.getStatus());
        assertEquals("Failed attempts should be incremented", initialFailedAttempts + 1, customer.getFailedAttempts());
        assertEquals("Should have 1 rejection email sent", 1, application.getEmails().size());
        
        // Verify rejection email
        Email rejectionEmail = application.getEmails().get(0);
        assertEquals("Rejection email receiver should match", "olivia.l@example.com", rejectionEmail.getReceiver());
        assertTrue("Rejection email should contain customer name", rejectionEmail.getContent().contains("Olivia Lee"));
        assertTrue("Rejection email should contain company name", rejectionEmail.getContent().contains("HealthPlus"));
        assertTrue("Rejection email should contain rejection message", rejectionEmail.getContent().contains("has been rejected"));
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
        
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setStatus(ApplicationStatus.APPROVAL); // Already approved
        application.setCustomer(customer);
        application.setCompany(company);
        application.setAllowance(document);
        
        // Execute approval on already approved application
        boolean result = application.approve();
        
        // Verify results
        assertFalse("Should not be able to approve already approved application", result);
        assertEquals("Application status should remain APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
        assertEquals("No new emails should be sent", 0, application.getEmails().size());
    }
    
    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Setup for Test Case 4: "Reject already rejected record"
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setCanApplyForIPO(true);
        int initialFailedAttempts = customer.getFailedAttempts();
        
        company.setName("Health");
        company.setEmail("health@gmail.com");
        
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setStatus(ApplicationStatus.REJECTED); // Already rejected
        application.setCustomer(customer);
        application.setCompany(company);
        application.setAllowance(document);
        
        // Execute rejection on already rejected application
        boolean result = application.reject();
        
        // Verify results
        assertFalse("Should not be able to reject already rejected application", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
        assertEquals("Failed attempts should not be incremented", initialFailedAttempts, customer.getFailedAttempts());
        assertEquals("No new emails should be sent", 0, application.getEmails().size());
    }
    
    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Setup for Test Case 5: "Approve record tied to ineligible customer"
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setCanApplyForIPO(false); // Customer is not eligible
        
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setStatus(ApplicationStatus.PENDING);
        application.setCustomer(customer);
        application.setCompany(company);
        application.setAllowance(document);
        
        // Execute approval for ineligible customer
        boolean result = application.approve();
        
        // Verify results
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
        assertEquals("No emails should be sent", 0, application.getEmails().size());
    }
}