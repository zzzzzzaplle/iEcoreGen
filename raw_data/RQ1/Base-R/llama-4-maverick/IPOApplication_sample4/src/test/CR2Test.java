import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    private IPOApplicationService service;
    private Customer customer;
    
    @Before
    public void setUp() {
        service = new IPOApplicationService();
        customer = new Customer();
    }
    
    @Test
    public void testCase1_ApprovePendingRequest() {
        // Setup
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephoneNumber("555-1122");
        customer.setRestricted(false);
        
        IPOApplication application = new IPOApplication();
        application.setCompanyName("SolarMax");
        application.setNumberOfShares(10);
        application.setAmountOfMoney(200);
        application.setDocument("S");
        application.setStatus(ApplicationStatus.PENDING);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(application);
        customer.setIpoApplications(applications);
        
        // Execute
        boolean result = service.approveOrRejectApplication(customer, "SolarMax", true);
        
        // Verify
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVED", ApplicationStatus.APPROVED, application.getStatus());
    }
    
    @Test
    public void testCase2_RejectPendingRequest() {
        // Setup
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephoneNumber("555-3344");
        customer.setRestricted(false);
        
        IPOApplication application = new IPOApplication();
        application.setCompanyName("HealthPlus");
        application.setNumberOfShares(10);
        application.setAmountOfMoney(5000);
        application.setDocument("H");
        application.setStatus(ApplicationStatus.PENDING);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(application);
        customer.setIpoApplications(applications);
        
        // Execute
        boolean result = service.approveOrRejectApplication(customer, "HealthPlus", false);
        
        // Verify
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase3_ApproveAlreadyApprovedRecord() {
        // Setup
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephoneNumber("555-5566");
        customer.setRestricted(false);
        
        IPOApplication application = new IPOApplication();
        application.setCompanyName("HealthPlus");
        application.setNumberOfShares(10);
        application.setAmountOfMoney(5000);
        application.setDocument("H");
        application.setStatus(ApplicationStatus.APPROVED);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(application);
        customer.setIpoApplications(applications);
        
        // Execute
        boolean result = service.approveOrRejectApplication(customer, "HealthPlus", true);
        
        // Verify
        assertFalse("Should not be able to modify approved applications", result);
        assertEquals("Application status should remain APPROVED", ApplicationStatus.APPROVED, application.getStatus());
    }
    
    @Test
    public void testCase4_RejectAlreadyRejectedRecord() {
        // Setup
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephoneNumber("555-7788");
        customer.setRestricted(false);
        
        IPOApplication application = new IPOApplication();
        application.setCompanyName("Health");
        application.setNumberOfShares(10);
        application.setAmountOfMoney(5000);
        application.setDocument("H");
        application.setStatus(ApplicationStatus.REJECTED);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(application);
        customer.setIpoApplications(applications);
        
        // Execute
        boolean result = service.approveOrRejectApplication(customer, "Health", false);
        
        // Verify
        assertFalse("Should not be able to modify rejected applications", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase5_ApproveRecordTiedToIneligibleCustomer() {
        // Setup
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephoneNumber("555-9900");
        customer.setRestricted(true); // Customer is restricted/ineligible
        
        IPOApplication application = new IPOApplication();
        application.setCompanyName("Cloud");
        application.setNumberOfShares(10);
        application.setAmountOfMoney(5000);
        application.setDocument("H");
        application.setStatus(ApplicationStatus.PENDING);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(application);
        customer.setIpoApplications(applications);
        
        // Execute
        boolean result = service.approveOrRejectApplication(customer, "Cloud", true);
        
        // Verify
        assertFalse("Should not be able to process application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
    }
}