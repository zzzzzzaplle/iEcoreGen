import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private Bank bank;
    private EmailService emailService;
    
    @Before
    public void setUp() {
        bank = new Bank();
        emailService = new EmailService();
    }
    
    @Test
    public void testCase1_approvePendingRequest() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setEligible(true);
        
        Company company = new Company("SolarMax");
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setNumberOfShares(10);
        application.setAmount(200.0);
        application.setDocument("S");
        application.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application);
        
        // Execute
        boolean result = bank.approveApplication(customer, "SolarMax");
        
        // Verify
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVED", 
                     ApplicationStatus.APPROVED, application.getStatus());
    }
    
    @Test
    public void testCase2_rejectPendingRequest() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setEligible(true);
        
        Company company = new Company("HealthPlus");
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument("H");
        application.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application);
        
        // Execute
        boolean result = bank.rejectApplication(customer, "HealthPlus");
        
        // Verify
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", 
                     ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase3_approveAlreadyApprovedRecord() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setEligible(true);
        
        Company company = new Company("HealthPlus");
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument("H");
        application.setStatus(ApplicationStatus.APPROVED);
        
        customer.getApplications().add(application);
        
        // Execute
        boolean result = bank.approveApplication(customer, "HealthPlus");
        
        // Verify
        assertFalse("Should not be able to approve an already approved application", result);
        assertEquals("Application status should remain APPROVED", 
                     ApplicationStatus.APPROVED, application.getStatus());
    }
    
    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setEligible(true);
        
        Company company = new Company("Health");
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument("H");
        application.setStatus(ApplicationStatus.REJECTED);
        
        customer.getApplications().add(application);
        
        // Execute
        boolean result = bank.rejectApplication(customer, "Health");
        
        // Verify
        assertFalse("Should not be able to reject an already rejected application", result);
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Setup
        Customer customer = new Customer();
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setEligible(false); // Customer is ineligible
        
        Company company = new Company("Cloud");
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument("C");
        application.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application);
        
        // Execute
        boolean result = bank.approveApplication(customer, "Cloud");
        
        // Verify
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", 
                     ApplicationStatus.PENDING, application.getStatus());
    }
}