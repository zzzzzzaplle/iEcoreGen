import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Setup: Customer "C101" with no IPO applications
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        
        // Test: Retrieve application count summary
        int result = IPOApplication.getApplicationCountSummary(customer);
        
        // Verify: Should return 0 when no applications exist
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_singlePendingRequest() {
        // Setup: Customer "C102" with one pending application
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        
        // Create and add a pending application
        IPOApplication app = new IPOApplication();
        app.setCustomer(customer);
        app.setCompanyName("QuantumTech");
        app.setNumberOfShares(50);
        app.setAmount(2500.0);
        app.setDocument("QT-2024-FormA");
        app.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(app);
        
        // Test: Retrieve application count summary
        int result = IPOApplication.getApplicationCountSummary(customer);
        
        // Verify: Should return 0 since pending applications are not counted
        assertEquals("Pending application should not be counted", 0, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setRestricted(true); // Customer cannot apply for IPO
        
        // Create and add approved applications
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompanyName("Neuralink");
        app1.setNumberOfShares(100);
        app1.setAmount(10000.0);
        app1.setDocument("QT-22023-101");
        app1.setStatus(ApplicationStatus.APPROVED);
        
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompanyName("SpaceY");
        app2.setNumberOfShares(30);
        app2.setAmount(15000.0);
        app2.setDocument("QT-2023-102");
        app2.setStatus(ApplicationStatus.APPROVED);
        
        // Create and add rejected application
        IPOApplication app3 = new IPOApplication();
        app3.setCustomer(customer);
        app3.setCompanyName("BioGen");
        app3.setNumberOfShares(20);
        app3.setAmount(1000.0);
        app3.setDocument("QT-2024-002");
        app3.setStatus(ApplicationStatus.REJECTED);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        
        // Test: Retrieve application count summary
        int result = IPOApplication.getApplicationCountSummary(customer);
        
        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals("Should count approved and rejected applications", 3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Customer "C104" with 1 approved, 2 rejected, and 2 pending applications
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        
        // Create and add approved application
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompanyName("RoboCorp");
        app1.setNumberOfShares(100);
        app1.setAmount(10000.0);
        app1.setDocument("QT-2023-105");
        app1.setStatus(ApplicationStatus.APPROVED);
        
        // Create and add rejected applications
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompanyName("AI Ventures");
        app2.setNumberOfShares(100);
        app2.setAmount(10000.0);
        app2.setDocument("QT-2023-106");
        app2.setStatus(ApplicationStatus.REJECTED);
        
        IPOApplication app3 = new IPOApplication();
        app3.setCustomer(customer);
        app3.setCompanyName("NanoMed");
        app3.setNumberOfShares(100);
        app3.setAmount(10000.0);
        app3.setDocument("QT-2024-003");
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Create and add pending applications
        IPOApplication app4 = new IPOApplication();
        app4.setCustomer(customer);
        app4.setCompanyName("GreenEnergy");
        app4.setNumberOfShares(100);
        app4.setAmount(10000.0);
        app4.setDocument("QT-2024-004");
        app4.setStatus(ApplicationStatus.PENDING);
        
        IPOApplication app5 = new IPOApplication();
        app5.setCustomer(customer);
        app5.setCompanyName("CloudScale");
        app5.setNumberOfShares(100);
        app5.setAmount(10000.0);
        app5.setDocument("QT-2024-005");
        app5.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Test: Retrieve application count summary
        int result = IPOApplication.getApplicationCountSummary(customer);
        
        // Verify: Should return 3 (1 approved + 2 rejected, excluding 2 pending)
        assertEquals("Should count only approved and rejected applications", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" with a canceled application
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        
        // Create a pending application
        IPOApplication app = new IPOApplication();
        app.setCustomer(customer);
        app.setCompanyName("Cloud");
        app.setNumberOfShares(10);
        app.setAmount(5000.0);
        app.setDocument("QT-1010");
        app.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(app);
        
        // Cancel the pending application
        boolean canceled = IPOApplication.cancelPendingApplication(customer, "Cloud");
        assertTrue("Application should be canceled successfully", canceled);
        
        // Test: Retrieve application count summary
        int result = IPOApplication.getApplicationCountSummary(customer);
        
        // Verify: Should return 0 since the application was canceled
        assertEquals("Canceled application should not be counted", 0, result);
    }
    
    // Helper method to set restricted status (since it's private in Customer class)
    private void setRestricted(Customer customer, boolean restricted) {
        // Using reflection to set the private field for testing purposes
        try {
            java.lang.reflect.Field field = Customer.class.getDeclaredField("isRestricted");
            field.setAccessible(true);
            field.set(customer, restricted);
        } catch (Exception e) {
            fail("Failed to set restricted status: " + e.getMessage());
        }
    }
}