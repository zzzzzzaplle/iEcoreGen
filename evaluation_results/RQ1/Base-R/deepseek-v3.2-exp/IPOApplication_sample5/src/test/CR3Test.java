import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private BankSystem bankSystem;
    
    @Before
    public void setUp() {
        bankSystem = new BankSystem();
    }
    
    @Test
    public void testCase1_NoApplicationsAtAll() {
        // Setup: Customer "C101" with no IPO requests
        Customer customer = new Customer();
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        
        // Test: Retrieve application count summary
        int result = bankSystem.getCustomerApplicationCount(customer);
        
        // Verify: Expected output is 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_SinglePendingRequest() {
        // Setup: Customer "C102" with one pending application
        Customer customer = new Customer();
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        
        // Create and add a pending application
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompanyName("QuantumTech");
        application.setNumberOfShares(50);
        application.setAmountPaid(2500.0);
        application.setDocument("QT-2024-FormA");
        application.setStatus(ApplicationStatus.PENDING);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(application);
        customer.setApplications(applications);
        
        // Test: Retrieve application count summary
        int result = bankSystem.getCustomerApplicationCount(customer);
        
        // Verify: Only approved and rejected count, pending should be excluded
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_MixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        Customer customer = new Customer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setRestricted(true); // Cannot apply for IPO
        
        // Create approved applications
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompanyName("Neuralink");
        app1.setNumberOfShares(100);
        app1.setAmountPaid(10000.0);
        app1.setDocument("QT-22023-101");
        app1.setStatus(ApplicationStatus.APPROVED);
        
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompanyName("SpaceY");
        app2.setNumberOfShares(30);
        app2.setAmountPaid(15000.0);
        app2.setDocument("QT-2023-102");
        app2.setStatus(ApplicationStatus.APPROVED);
        
        // Create rejected application
        IPOApplication app3 = new IPOApplication();
        app3.setCustomer(customer);
        app3.setCompanyName("BioGen");
        app3.setNumberOfShares(20);
        app3.setAmountPaid(1000.0);
        app3.setDocument("QT-2024-002");
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Add all applications to customer
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        applications.add(app3);
        customer.setApplications(applications);
        
        // Test: Retrieve application count summary
        int result = bankSystem.getCustomerApplicationCount(customer);
        
        // Verify: Should count 2 approved + 1 rejected = 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_FiveHistoricalRequests() {
        // Setup: Customer "C104" with 1 approved, 2 rejected, and 2 pending applications
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        
        // Create approved application
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompanyName("RoboCorp");
        app1.setNumberOfShares(100);
        app1.setAmountPaid(10000.0);
        app1.setDocument("QT-2023-105");
        app1.setStatus(ApplicationStatus.APPROVED);
        
        // Create rejected applications
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompanyName("AI Ventures");
        app2.setNumberOfShares(100);
        app2.setAmountPaid(10000.0);
        app2.setDocument("QT-2023-106");
        app2.setStatus(ApplicationStatus.REJECTED);
        
        IPOApplication app3 = new IPOApplication();
        app3.setCustomer(customer);
        app3.setCompanyName("NanoMed");
        app3.setNumberOfShares(100);
        app3.setAmountPaid(10000.0);
        app3.setDocument("QT-2024-003");
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Create pending applications (should not be counted)
        IPOApplication app4 = new IPOApplication();
        app4.setCustomer(customer);
        app4.setCompanyName("GreenEnergy");
        app4.setNumberOfShares(100);
        app4.setAmountPaid(10000.0);
        app4.setDocument("QT-2024-004");
        app4.setStatus(ApplicationStatus.PENDING);
        
        IPOApplication app5 = new IPOApplication();
        app5.setCustomer(customer);
        app5.setCompanyName("CloudScale");
        app5.setNumberOfShares(100);
        app5.setAmountPaid(10000.0);
        app5.setDocument("QT-2024-005");
        app5.setStatus(ApplicationStatus.PENDING);
        
        // Add all applications to customer
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        applications.add(app3);
        applications.add(app4);
        applications.add(app5);
        customer.setApplications(applications);
        
        // Test: Retrieve application count summary
        int result = bankSystem.getCustomerApplicationCount(customer);
        
        // Verify: Should count 1 approved + 2 rejected = 3 (pending excluded)
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_AllRequestsCanceled() {
        // Setup: Customer "C105" with a canceled application
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        
        // Create and add a pending application
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompanyName("Cloud");
        application.setNumberOfShares(10);
        application.setAmountPaid(5000.0);
        application.setDocument("QT-1010");
        application.setStatus(ApplicationStatus.PENDING);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(application);
        customer.setApplications(applications);
        
        // Cancel the application (remove from list)
        boolean canceled = bankSystem.cancelPendingApplication(customer, "Cloud");
        assertTrue(canceled);
        
        // Test: Retrieve application count summary
        int result = bankSystem.getCustomerApplicationCount(customer);
        
        // Verify: No applications should remain after cancellation
        assertEquals(0, result);
    }
}