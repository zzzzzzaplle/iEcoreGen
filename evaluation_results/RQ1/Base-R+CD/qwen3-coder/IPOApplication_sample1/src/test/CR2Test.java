import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private Customer customer1, customer2, customer3, customer4, customer5;
    private Company company1, company2, company3, company4, company5;
    private Document document1, document2, document3, document4, document5;
    private Application application1, application2, application3, application4, application5;
    
    @Before
    public void setUp() {
        // Setup for Test Case 1: Approve pending request
        customer1 = new Customer();
        customer1.setName("Michael Brown");
        customer1.setEmail("m.brown@example.com");
        customer1.setTelephone("555-1122");
        customer1.setCanApplyForIPO(true);
        
        company1 = new Company();
        company1.setName("SolarMax");
        company1.setEmail("solarmax@gmail.com");
        
        document1 = new Document();
        
        application1 = new Application();
        application1.setCustomer(customer1);
        application1.setCompany(company1);
        application1.setShare(10);
        application1.setAmountOfMoney(200.0);
        application1.setAllowance(document1);
        application1.setStatus(ApplicationStatus.PENDING);
        customer1.getApplications().add(application1);
        
        // Setup for Test Case 2: Reject pending request
        customer2 = new Customer();
        customer2.setName("Olivia Lee");
        customer2.setEmail("olivia.l@example.com");
        customer2.setTelephone("555-3344");
        customer2.setCanApplyForIPO(true);
        
        company2 = new Company();
        company2.setName("HealthPlus");
        company2.setEmail("healthplus@gmail.com");
        
        document2 = new Document();
        
        application2 = new Application();
        application2.setCustomer(customer2);
        application2.setCompany(company2);
        application2.setShare(10);
        application2.setAmountOfMoney(5000.0);
        application2.setAllowance(document2);
        application2.setStatus(ApplicationStatus.PENDING);
        customer2.getApplications().add(application2);
        
        // Setup for Test Case 3: Approve already approved record
        customer3 = new Customer();
        customer3.setName("Daniel Kim");
        customer3.setEmail("d.kim@example.com");
        customer3.setTelephone("555-5566");
        customer3.setCanApplyForIPO(true);
        
        company3 = new Company();
        company3.setName("HealthPlus");
        company3.setEmail("healthplus@gmail.com");
        
        document3 = new Document();
        
        application3 = new Application();
        application3.setCustomer(customer3);
        application3.setCompany(company3);
        application3.setShare(10);
        application3.setAmountOfMoney(5000.0);
        application3.setAllowance(document3);
        application3.setStatus(ApplicationStatus.APPROVAL);
        customer3.getApplications().add(application3);
        
        // Setup for Test Case 4: Reject already rejected record
        customer4 = new Customer();
        customer4.setName("Sophie Zhang");
        customer4.setEmail("s.zhang@example.com");
        customer4.setTelephone("555-7788");
        customer4.setCanApplyForIPO(true);
        
        company4 = new Company();
        company4.setName("Health");
        company4.setEmail("health@gmail.com");
        
        document4 = new Document();
        
        application4 = new Application();
        application4.setCustomer(customer4);
        application4.setCompany(company4);
        application4.setShare(10);
        application4.setAmountOfMoney(5000.0);
        application4.setAllowance(document4);
        application4.setStatus(ApplicationStatus.REJECTED);
        customer4.getApplications().add(application4);
        
        // Setup for Test Case 5: Approve record tied to ineligible customer
        customer5 = new Customer();
        customer5.setName("William Wang");
        customer5.setEmail("will.w@example.com");
        customer5.setTelephone("555-9900");
        customer5.setCanApplyForIPO(false); // Ineligible customer
        
        company5 = new Company();
        company5.setName("Cloud");
        company5.setEmail("Cloud@gmail.com");
        
        document5 = new Document();
        
        application5 = new Application();
        application5.setCustomer(customer5);
        application5.setCompany(company5);
        application5.setShare(10);
        application5.setAmountOfMoney(5000.0);
        application5.setAllowance(document5);
        application5.setStatus(ApplicationStatus.PENDING);
        customer5.getApplications().add(application5);
    }
    
    @Test
    public void testCase1_approvePendingRequest() {
        // Input: Bank approves application for company "SolarMax"
        boolean result = application1.approve();
        
        // Expected Output: True (status changes to APPROVAL)
        assertTrue(result);
        assertEquals(ApplicationStatus.APPROVAL, application1.getStatus());
        
        // Verify that emails were sent (2 emails: one to customer, one to company)
        assertEquals(2, application1.getEmails().size());
        
        // Verify email content was created properly
        Email customerEmail = application1.getEmails().get(0);
        Email companyEmail = application1.getEmails().get(1);
        
        assertEquals("m.brown@example.com", customerEmail.getReceiver());
        assertEquals("solarmax@gmail.com", companyEmail.getReceiver());
        
        // Verify content contains customer and company info
        assertTrue(customerEmail.getContent().contains("Michael Brown"));
        assertTrue(customerEmail.getContent().contains("SolarMax"));
        assertTrue(customerEmail.getContent().contains("10"));
        assertTrue(customerEmail.getContent().contains("200.0"));
    }
    
    @Test
    public void testCase2_rejectPendingRequest() {
        // Input: Bank rejects application for "HealthPlus"
        boolean result = application2.reject();
        
        // Expected Output: True (status changes to REJECTED)
        assertTrue(result);
        assertEquals(ApplicationStatus.REJECTED, application2.getStatus());
        
        // Verify that a rejection email was sent to the customer
        assertEquals(1, application2.getEmails().size());
        
        Email rejectionEmail = application2.getEmails().get(0);
        assertEquals("olivia.l@example.com", rejectionEmail.getReceiver());
        assertTrue(rejectionEmail.getContent().contains("rejected"));
        assertTrue(rejectionEmail.getContent().contains("HealthPlus"));
    }
    
    @Test
    public void testCase3_approveAlreadyApprovedRecord() {
        // Input: Bank attempts to re-approve application "APP-1003"
        boolean result = application3.approve();
        
        // Expected Output: False (cannot modify approved applications)
        assertFalse(result);
        assertEquals(ApplicationStatus.APPROVAL, application3.getStatus()); // Status unchanged
    }
    
    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Input: Bank tries to reject application "APP-1004"
        boolean result = application4.reject();
        
        // Expected Output: False (cannot modify final decisions)
        assertFalse(result);
        assertEquals(ApplicationStatus.REJECTED, application4.getStatus()); // Status unchanged
    }
    
    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Input: Bank processes application "APP-1005" for ineligible customer
        boolean result = application5.approve();
        
        // Expected Output: False (must maintain eligibility throughout process)
        assertFalse(result);
        assertEquals(ApplicationStatus.PENDING, application5.getStatus()); // Status unchanged
    }
}