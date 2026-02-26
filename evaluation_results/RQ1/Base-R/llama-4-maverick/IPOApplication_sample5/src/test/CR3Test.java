import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private IPOService ipoService;
    
    @Before
    public void setUp() {
        ipoService = new IPOService();
    }
    
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Setup: Customer "C101" with no IPO applications
        Customer customer = new Customer();
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephoneNumber("555-0101");
        customer.setFailedAttempts(0); // Can apply for IPO
        
        // Execute: Get application count
        int result = ipoService.getApplicationCount(customer);
        
        // Verify: Should return 0
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_singlePendingRequest() {
        // Setup: Customer "C102" with one pending application
        Customer customer = new Customer();
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephoneNumber("555-0202");
        customer.setFailedAttempts(0); // Can apply for IPO
        
        // Create and add pending application
        IPOApplication application = new IPOApplication();
        application.setCompanyName("QuantumTech");
        application.setNumberOfShares(50);
        application.setAmount(2500);
        application.setDocument("QT-2024-FormA");
        application.setStatus(IPOApplicationStatus.PENDING);
        
        customer.getIpoApplications().add(application);
        
        // Execute: Get application count
        int result = ipoService.getApplicationCount(customer);
        
        // Verify: Should return 1 (pending applications are not included)
        assertEquals("Customer with one pending application should return 0", 0, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        Customer customer = new Customer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephoneNumber("555-0303");
        customer.setFailedAttempts(3); // Cannot apply for IPO
        
        // Create approved application 1
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("Neuralink");
        app1.setNumberOfShares(100);
        app1.setAmount(10000);
        app1.setDocument("QT-22023-101");
        app1.setStatus(IPOApplicationStatus.APPROVED);
        
        // Create approved application 2
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("SpaceY");
        app2.setNumberOfShares(30);
        app2.setAmount(15000);
        app2.setDocument("QT-2023-102");
        app2.setStatus(IPOApplicationStatus.APPROVED);
        
        // Create rejected application
        IPOApplication app3 = new IPOApplication();
        app3.setCompanyName("BioGen");
        app3.setNumberOfShares(20);
        app3.setAmount(1000);
        app3.setDocument("QT-2024-002");
        app3.setStatus(IPOApplicationStatus.REJECTED);
        
        // Add all applications to customer
        customer.getIpoApplications().add(app1);
        customer.getIpoApplications().add(app2);
        customer.getIpoApplications().add(app3);
        
        // Execute: Get application count
        int result = ipoService.getApplicationCount(customer);
        
        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals("Customer with 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Customer "C104" with 1 approved, 2 rejected, and 2 pending applications
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephoneNumber("555-0404");
        customer.setFailedAttempts(0); // Can apply for IPO
        
        // Create approved application
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("RoboCorp");
        app1.setNumberOfShares(100);
        app1.setAmount(10000);
        app1.setDocument("QT-2023-105");
        app1.setStatus(IPOApplicationStatus.APPROVED);
        
        // Create rejected application 1
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("AI Ventures");
        app2.setNumberOfShares(100);
        app2.setAmount(10000);
        app2.setDocument("QT-2023-106");
        app2.setStatus(IPOApplicationStatus.REJECTED);
        
        // Create rejected application 2
        IPOApplication app3 = new IPOApplication();
        app3.setCompanyName("NanoMed");
        app3.setNumberOfShares(100);
        app3.setAmount(10000);
        app3.setDocument("QT-2024-003");
        app3.setStatus(IPOApplicationStatus.REJECTED);
        
        // Create pending application 1
        IPOApplication app4 = new IPOApplication();
        app4.setCompanyName("GreenEnergy");
        app4.setNumberOfShares(100);
        app4.setAmount(10000);
        app4.setDocument("QT-2024-004");
        app4.setStatus(IPOApplicationStatus.PENDING);
        
        // Create pending application 2
        IPOApplication app5 = new IPOApplication();
        app5.setCompanyName("CloudScale");
        app5.setNumberOfShares(100);
        app5.setAmount(10000);
        app5.setDocument("QT-2024-005");
        app5.setStatus(IPOApplicationStatus.PENDING);
        
        // Add all applications to customer
        customer.getIpoApplications().add(app1);
        customer.getIpoApplications().add(app2);
        customer.getIpoApplications().add(app3);
        customer.getIpoApplications().add(app4);
        customer.getIpoApplications().add(app5);
        
        // Execute: Get application count
        int result = ipoService.getApplicationCount(customer);
        
        // Verify: Should return 3 (1 approved + 2 rejected, pending are not included)
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" with a canceled application
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephoneNumber("555-0505");
        customer.setFailedAttempts(0); // Can apply for IPO
        
        // Create and add pending application
        IPOApplication application = new IPOApplication();
        application.setCompanyName("Cloud");
        application.setNumberOfShares(10);
        application.setAmount(5000);
        application.setDocument("QT-1010");
        application.setStatus(IPOApplicationStatus.PENDING);
        
        customer.getIpoApplications().add(application);
        
        // Cancel the application (remove from list)
        ipoService.cancelApplication(customer, "Cloud");
        
        // Execute: Get application count
        int result = ipoService.getApplicationCount(customer);
        
        // Verify: Should return 0 (canceled application is removed)
        assertEquals("Customer with canceled application should return 0", 0, result);
    }
}