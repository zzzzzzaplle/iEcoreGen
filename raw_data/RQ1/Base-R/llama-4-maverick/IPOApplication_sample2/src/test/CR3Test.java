import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private IPOApplicationService service;
    
    @Before
    public void setUp() {
        service = new IPOApplicationService();
    }
    
    @Test
    public void testCase1_NoApplicationsAtAll() {
        // Setup: Customer "C101" with no IPO applications
        Customer customer = new Customer();
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephoneNumber("555-0101");
        customer.setRestricted(false);
        customer.setApplications(new ArrayList<>()); // No applications
        
        // Execute: Retrieve application count summary
        int result = service.getApplicationCountSummary(customer);
        
        // Verify: Expected output is 0
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_SinglePendingRequest() {
        // Setup: Customer "C102" with one pending application
        Customer customer = new Customer();
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephoneNumber("555-0202");
        customer.setRestricted(false);
        
        // Create and add pending application
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompanyName("QuantumTech");
        application.setNumberOfShares(50);
        application.setAmount(2500.0);
        application.setDocument("QT-2024-FormA");
        application.setStatus(ApplicationStatus.APPROVED); // Note: Spec says "Status: approval"
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(application);
        customer.setApplications(applications);
        
        // Execute: Retrieve application count summary
        int result = service.getApplicationCountSummary(customer);
        
        // Verify: Expected output is 1 (only approved/rejected applications count)
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_MixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        Customer customer = new Customer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephoneNumber("555-0303");
        customer.setRestricted(true); // Note: Spec says "can not apply for IPO"
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // Add first approved application
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompanyName("Neuralink");
        app1.setNumberOfShares(100);
        app1.setAmount(10000.0);
        app1.setDocument("QT-22023-101");
        app1.setStatus(ApplicationStatus.APPROVED);
        applications.add(app1);
        
        // Add second approved application
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompanyName("SpaceY");
        app2.setNumberOfShares(30);
        app2.setAmount(15000.0);
        app2.setDocument("QT-2023-102");
        app2.setStatus(ApplicationStatus.APPROVED);
        applications.add(app2);
        
        // Add rejected application
        IPOApplication app3 = new IPOApplication();
        app3.setCustomer(customer);
        app3.setCompanyName("BioGen");
        app3.setNumberOfShares(20);
        app3.setAmount(1000.0);
        app3.setDocument("QT-2024-002");
        app3.setStatus(ApplicationStatus.REJECTED);
        applications.add(app3);
        
        customer.setApplications(applications);
        
        // Execute: Retrieve application count summary
        int result = service.getApplicationCountSummary(customer);
        
        // Verify: Expected output is 3 (2 approved + 1 rejected)
        assertEquals("Customer with 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_FiveHistoricalRequests() {
        // Setup: Customer "C104" with 1 approved, 2 rejected, and 2 pending applications
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephoneNumber("555-0404");
        customer.setRestricted(false);
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // Add approved application
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompanyName("RoboCorp");
        app1.setNumberOfShares(100);
        app1.setAmount(10000.0);
        app1.setDocument("QT-2023-105");
        app1.setStatus(ApplicationStatus.APPROVED);
        applications.add(app1);
        
        // Add first rejected application
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompanyName("AI Ventures");
        app2.setNumberOfShares(100);
        app2.setAmount(10000.0);
        app2.setDocument("QT-2023-106");
        app2.setStatus(ApplicationStatus.REJECTED);
        applications.add(app2);
        
        // Add second rejected application
        IPOApplication app3 = new IPOApplication();
        app3.setCustomer(customer);
        app3.setCompanyName("NanoMed");
        app3.setNumberOfShares(100);
        app3.setAmount(10000.0);
        app3.setDocument("QT-2024-003");
        app3.setStatus(ApplicationStatus.REJECTED);
        applications.add(app3);
        
        // Add first pending application (should not be counted)
        IPOApplication app4 = new IPOApplication();
        app4.setCustomer(customer);
        app4.setCompanyName("GreenEnergy");
        app4.setNumberOfShares(100);
        app4.setAmount(10000.0);
        app4.setDocument("QT-2024-004");
        app4.setStatus(ApplicationStatus.PENDING);
        applications.add(app4);
        
        // Add second pending application (should not be counted)
        IPOApplication app5 = new IPOApplication();
        app5.setCustomer(customer);
        app5.setCompanyName("CloudScale");
        app5.setNumberOfShares(100);
        app5.setAmount(10000.0);
        app5.setDocument("QT-2024-005");
        app5.setStatus(ApplicationStatus.PENDING);
        applications.add(app5);
        
        customer.setApplications(applications);
        
        // Execute: Retrieve application count summary
        int result = service.getApplicationCountSummary(customer);
        
        // Verify: Expected output is 3 (1 approved + 2 rejected, pending applications excluded)
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_AllRequestsCanceled() {
        // Setup: Customer "C105" with one application that gets canceled
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephoneNumber("555-0505");
        customer.setRestricted(false);
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // Create pending application
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompanyName("Cloud");
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument("QT-1010");
        application.setStatus(ApplicationStatus.PENDING);
        applications.add(application);
        
        customer.setApplications(applications);
        
        // Cancel the application (changes status to REJECTED)
        service.cancelPendingApplication(customer, "Cloud");
        
        // Execute: Retrieve application count summary
        int result = service.getApplicationCountSummary(customer);
        
        // Verify: Expected output is 0 (canceled application becomes rejected, but spec expects 0 for this case)
        // Note: The spec shows "Expected Output: 0" despite the cancellation
        assertEquals("Canceled application should result in 0 count", 0, result);
    }
}