import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    private Bank bank;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer4;
    private Customer customer5;
    private IPOApplication app1;
    private IPOApplication app2;
    private IPOApplication app3;
    private IPOApplication app4;
    private IPOApplication app5;

    @Before
    public void setUp() {
        bank = new Bank();
        
        // Setup for Test Case 1: Approve pending request
        customer1 = new Customer();
        customer1.setName("Michael");
        customer1.setSurname("Brown");
        customer1.setEmail("m.brown@example.com");
        customer1.setTelephoneNumber("555-1122");
        customer1.setRestricted(false);
        
        app1 = new IPOApplication();
        app1.setCustomer(customer1);
        app1.setCompanyName("SolarMax");
        app1.setNumberOfShares(10);
        app1.setAmount(200.0);
        app1.setDocument("S");
        app1.setStatus(ApplicationStatus.PENDING);
        customer1.getApplications().add(app1);
        
        // Setup for Test Case 2: Reject pending request
        customer2 = new Customer();
        customer2.setName("Olivia");
        customer2.setSurname("Lee");
        customer2.setEmail("olivia.l@example.com");
        customer2.setTelephoneNumber("555-3344");
        customer2.setRestricted(false);
        
        app2 = new IPOApplication();
        app2.setCustomer(customer2);
        app2.setCompanyName("HealthPlus");
        app2.setNumberOfShares(10);
        app2.setAmount(5000.0);
        app2.setDocument("H");
        app2.setStatus(ApplicationStatus.PENDING);
        customer2.getApplications().add(app2);
        
        // Setup for Test Case 3: Approve already approved record
        customer3 = new Customer();
        customer3.setName("Daniel");
        customer3.setSurname("Kim");
        customer3.setEmail("d.kim@example.com");
        customer3.setTelephoneNumber("555-5566");
        customer3.setRestricted(false);
        
        app3 = new IPOApplication();
        app3.setCustomer(customer3);
        app3.setCompanyName("HealthPlus");
        app3.setNumberOfShares(10);
        app3.setAmount(5000.0);
        app3.setDocument("H");
        app3.setStatus(ApplicationStatus.APPROVED);
        customer3.getApplications().add(app3);
        
        // Setup for Test Case 4: Reject already rejected record
        customer4 = new Customer();
        customer4.setName("Sophie");
        customer4.setSurname("Zhang");
        customer4.setEmail("s.zhang@example.com");
        customer4.setTelephoneNumber("555-7788");
        customer4.setRestricted(false);
        
        app4 = new IPOApplication();
        app4.setCustomer(customer4);
        app4.setCompanyName("Health");
        app4.setNumberOfShares(10);
        app4.setAmount(5000.0);
        app4.setDocument("H");
        app4.setStatus(ApplicationStatus.REJECTED);
        customer4.getApplications().add(app4);
        
        // Setup for Test Case 5: Approve record tied to ineligible customer
        customer5 = new Customer();
        customer5.setName("William");
        customer5.setSurname("Wang");
        customer5.setEmail("will.w@example.com");
        customer5.setTelephoneNumber("555-9900");
        customer5.setRestricted(true);
        
        app5 = new IPOApplication();
        app5.setCustomer(customer5);
        app5.setCompanyName("Cloud");
        app5.setNumberOfShares(10);
        app5.setAmount(5000.0);
        app5.setDocument("C");
        app5.setStatus(ApplicationStatus.PENDING);
        customer5.getApplications().add(app5);
    }

    @Test
    public void testCase1_approvePendingRequest() {
        // Test approving a pending application
        boolean result = bank.approveOrRejectApplication(app1, true);
        
        // Verify the operation returns true
        assertTrue("Approving pending application should return true", result);
        
        // Verify the application status changed to APPROVED
        assertEquals("Application status should be APPROVED", 
                     ApplicationStatus.APPROVED, app1.getStatus());
    }

    @Test
    public void testCase2_rejectPendingRequest() {
        // Test rejecting a pending application
        boolean result = bank.approveOrRejectApplication(app2, false);
        
        // Verify the operation returns true
        assertTrue("Rejecting pending application should return true", result);
        
        // Verify the application status changed to REJECTED
        assertEquals("Application status should be REJECTED", 
                     ApplicationStatus.REJECTED, app2.getStatus());
    }

    @Test
    public void testCase3_approveAlreadyApprovedRecord() {
        // Test attempting to approve an already approved application
        boolean result = bank.approveOrRejectApplication(app3, true);
        
        // Verify the operation returns false
        assertFalse("Approving already approved application should return false", result);
        
        // Verify the application status remains APPROVED
        assertEquals("Application status should remain APPROVED", 
                     ApplicationStatus.APPROVED, app3.getStatus());
    }

    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Test attempting to reject an already rejected application
        boolean result = bank.approveOrRejectApplication(app4, false);
        
        // Verify the operation returns false
        assertFalse("Rejecting already rejected application should return false", result);
        
        // Verify the application status remains REJECTED
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, app4.getStatus());
    }

    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Test attempting to approve an application for an ineligible customer
        boolean result = bank.approveOrRejectApplication(app5, true);
        
        // Verify the operation returns false (customer is restricted)
        assertFalse("Approving application for restricted customer should return false", result);
        
        // Verify the application status remains PENDING
        assertEquals("Application status should remain PENDING", 
                     ApplicationStatus.PENDING, app5.getStatus());
    }
}