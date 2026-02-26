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
        // Common setup that can be reused across tests
        customer = new Customer();
        company = new Company();
        document = new Document();
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
        
        // Create and configure the application
        application = new Application();
        application.setShare(10);
        application.setAmountOfMoney(200.0);
        application.setCompany(company);
        application.setCustomer(customer);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        // Execute the approval
        boolean result = application.approve();
        
        // Verify the result and state changes
        assertTrue("Approval should succeed for pending application", result);
        assertEquals("Status should change to APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
        assertEquals("Two emails should be sent (customer and company)", 2, application.getEmails().size());
        
        // Verify customer email content
        Email custEmail = application.getEmails().get(0);
        assertEquals("Customer email recipient should match", "m.brown@example.com", custEmail.getReceiver());
        assertTrue("Customer email content should contain approval details", 
                   custEmail.getContent().contains("Your IPO application has been processed"));
        
        // Verify company email content  
        Email compEmail = application.getEmails().get(1);
        assertEquals("Company email recipient should match", "solarmax@gmail.com", compEmail.getReceiver());
        assertTrue("Company email content should contain approval details",
                   compEmail.getContent().contains("Your IPO application has been processed"));
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
        
        // Create and configure the application
        application = new Application();
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setCompany(company);
        application.setCustomer(customer);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        // Execute the rejection
        boolean result = application.reject();
        
        // Verify the result and state changes
        assertTrue("Rejection should succeed for pending application", result);
        assertEquals("Status should change to REJECTED", ApplicationStatus.REJECTED, application.getStatus());
        assertEquals("One rejection email should be sent", 1, application.getEmails().size());
        
        // Verify rejection email content
        Email rejectionEmail = application.getEmails().get(0);
        assertEquals("Rejection email recipient should match", "olivia.l@example.com", rejectionEmail.getReceiver());
        assertTrue("Rejection email should contain rejection message", 
                   rejectionEmail.getContent().contains("has been rejected"));
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
        
        // Create and configure an already approved application
        application = new Application();
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setCompany(company);
        application.setCustomer(customer);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.APPROVAL); // Already approved
        
        // Attempt to approve again
        boolean result = application.approve();
        
        // Verify the result and state remains unchanged
        assertFalse("Approval should fail for already approved application", result);
        assertEquals("Status should remain APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
        assertEquals("No new emails should be sent", 0, application.getEmails().size());
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
        
        // Create and configure an already rejected application
        application = new Application();
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setCompany(company);
        application.setCustomer(customer);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.REJECTED); // Already rejected
        
        // Attempt to reject again
        boolean result = application.reject();
        
        // Verify the result and state remains unchanged
        assertFalse("Rejection should fail for already rejected application", result);
        assertEquals("Status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
        assertEquals("No new emails should be sent", 0, application.getEmails().size());
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
        
        // Create and configure the application
        application = new Application();
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setCompany(company);
        application.setCustomer(customer);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        // Execute the approval
        boolean result = application.approve();
        
        // Verify the result - approval should fail due to ineligible customer
        assertFalse("Approval should fail for ineligible customer", result);
        assertEquals("Status should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
        assertEquals("No emails should be sent", 0, application.getEmails().size());
    }
}