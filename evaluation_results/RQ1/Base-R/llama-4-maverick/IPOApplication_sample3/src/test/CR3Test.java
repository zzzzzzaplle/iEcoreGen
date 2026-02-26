import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    private Bank bank;
    
    @Before
    public void setUp() {
        bank = new Bank();
    }
    
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Setup: Customer "C101" with no IPO applications
        Customer customer = new Customer();
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephoneNumber("555-0101");
        customer.setRestricted(false);
        
        // Execute: Get application count summary
        int result = bank.getApplicationCountSummary(customer);
        
        // Verify: Should return 0 since no applications exist
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_singleApprovedRequest() {
        // Setup: Customer "C102" with one approved application
        Customer customer = new Customer();
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephoneNumber("555-0202");
        customer.setRestricted(false);
        
        // Create and add approved application
        IPOApplication app = new IPOApplication();
        app.setCustomer(customer);
        app.setCompanyName("QuantumTech");
        app.setNumberOfShares(50);
        app.setAmount(2500.0);
        app.setDocument("QT-2024-FormA");
        app.setStatus(ApplicationStatus.APPROVED);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app);
        customer.setApplications(applications);
        
        // Execute: Get application count summary
        int result = bank.getApplicationCountSummary(customer);
        
        // Verify: Should return 1 since one approved application exists
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer "C103" with two approved and one rejected applications
        Customer customer = new Customer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephoneNumber("555-0303");
        customer.setRestricted(true); // Customer cannot apply but still has historical applications
        
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
        
        // Execute: Get application count summary
        int result = bank.getApplicationCountSummary(customer);
        
        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals("Customer with 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Customer "C104" with 5 applications: 1 approved, 2 rejected, 2 pending
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
        
        // Execute: Get application count summary
        int result = bank.getApplicationCountSummary(customer);
        
        // Verify: Should return 3 (1 approved + 2 rejected, excluding 2 pending)
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" with one canceled application
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephoneNumber("555-0505");
        customer.setRestricted(false);
        
        // Create pending application
        IPOApplication app = new IPOApplication();
        app.setCustomer(customer);
        app.setCompanyName("Cloud");
        app.setNumberOfShares(10);
        app.setAmount(5000.0);
        app.setDocument("QT-1010");
        app.setStatus(ApplicationStatus.PENDING);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app);
        customer.setApplications(applications);
        
        // Cancel the application (changes status to REJECTED)
        bank.cancelPendingApplication(customer, "Cloud");
        
        // Execute: Get application count summary
        int result = bank.getApplicationCountSummary(customer);
        
        // Verify: Should return 0 since canceled applications are rejected and pending applications are not counted
        assertEquals("Customer with canceled application should return 0", 0, result);
    }
}