import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private IPOService ipoService;
    private Customer customer;
    private Company company;
    private IPOApplication application;
    
    @Before
    public void setUp() {
        ipoService = new IPOService();
    }
    
    @Test
    public void testCase1_approvePendingRequest() {
        // Setup
        customer = new Customer();
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setRetail(true);
        customer.setLocked(false);
        
        company = new Company();
        company.setName("SolarMax");
        
        byte[] document = new byte[]{'S'};
        
        // Create pending application
        boolean created = ipoService.createApplication(customer, company, 10, 200.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Test approval
        boolean result = ipoService.approveApplication(customer, company);
        
        // Verify results
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVED", ApplicationStatus.APPROVED, 
                    customer.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase2_rejectPendingRequest() {
        // Setup
        customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setRetail(true);
        customer.setLocked(false);
        
        company = new Company();
        company.setName("HealthPlus");
        
        byte[] document = new byte[]{'H'};
        
        // Create pending application
        boolean created = ipoService.createApplication(customer, company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Test rejection
        boolean result = ipoService.rejectApplication(customer, company);
        
        // Verify results
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, 
                    customer.getApplications().get(0).getStatus());
        assertEquals("Failed attempts should be incremented", 1, customer.getFailedAttempts());
    }
    
    @Test
    public void testCase3_approveAlreadyApprovedRecord() {
        // Setup
        customer = new Customer();
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setRetail(true);
        customer.setLocked(false);
        
        company = new Company();
        company.setName("HealthPlus");
        
        byte[] document = new byte[]{'H'};
        
        // Create and approve application
        boolean created = ipoService.createApplication(customer, company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        boolean firstApproval = ipoService.approveApplication(customer, company);
        assertTrue("First approval should succeed", firstApproval);
        
        // Test re-approval
        boolean result = ipoService.approveApplication(customer, company);
        
        // Verify results
        assertFalse("Should not be able to approve already approved application", result);
    }
    
    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Setup
        customer = new Customer();
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setRetail(true);
        customer.setLocked(false);
        
        company = new Company();
        company.setName("Health");
        
        byte[] document = new byte[]{'H'};
        
        // Create and reject application
        boolean created = ipoService.createApplication(customer, company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        boolean firstRejection = ipoService.rejectApplication(customer, company);
        assertTrue("First rejection should succeed", firstRejection);
        
        // Test re-rejection
        boolean result = ipoService.rejectApplication(customer, company);
        
        // Verify results
        assertFalse("Should not be able to reject already rejected application", result);
    }
    
    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Setup
        customer = new Customer();
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setRetail(true);
        customer.setLocked(false);
        
        company = new Company();
        company.setName("Cloud");
        
        byte[] document = new byte[]{'C'};
        
        // Create pending application
        boolean created = ipoService.createApplication(customer, company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Make customer ineligible by locking them
        customer.setLocked(true);
        
        // Test approval with ineligible customer
        boolean result = ipoService.approveApplication(customer, company);
        
        // Verify results
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, 
                    customer.getApplications().get(0).getStatus());
    }
}