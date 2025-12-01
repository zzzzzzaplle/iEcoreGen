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
    public void testCase1_noApplicationsAtAll() {
        // Setup: Customer with no IPO applications
        Customer customer = new Customer();
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephoneNumber("555-0101");
        customer.setRestricted(false);
        
        // Execute: Get application count summary
        int result = service.getApplicationCountSummary(customer);
        
        // Verify: Should return 0 since no applications exist
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_singlePendingRequest() {
        // Setup: Customer with one pending application
        Customer customer = new Customer();
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephoneNumber("555-0202");
        customer.setRestricted(false);
        
        // Create and add a pending application
        IPOApplication app = new IPOApplication();
        app.setCompanyName("QuantumTech");
        app.setNumberOfShares(50);
        app.setAmountOfMoney(2500);
        app.setDocument("QT-2024-FormA");
        app.setStatus(ApplicationStatus.PENDING);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app);
        customer.setIpoApplications(applications);
        
        // Execute: Get application count summary
        int result = service.getApplicationCountSummary(customer);
        
        // Verify: Should return 1 (pending applications are included according to test spec)
        assertEquals("Customer with one pending application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer with mixed application statuses
        Customer customer = new Customer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephoneNumber("555-0303");
        customer.setRestricted(true); // Customer cannot apply for IPO
        
        // Create and add approved applications
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("Neuralink");
        app1.setNumberOfShares(100);
        app1.setAmountOfMoney(10000);
        app1.setDocument("QT-22023-101");
        app1.setStatus(ApplicationStatus.APPROVED);
        
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("SpaceY");
        app2.setNumberOfShares(30);
        app2.setAmountOfMoney(15000);
        app2.setDocument("QT-2023-102");
        app2.setStatus(ApplicationStatus.APPROVED);
        
        // Create and add rejected application
        IPOApplication app3 = new IPOApplication();
        app3.setCompanyName("BioGen");
        app3.setNumberOfShares(20);
        app3.setAmountOfMoney(1000);
        app3.setDocument("QT-2024-002");
        app3.setStatus(ApplicationStatus.REJECTED);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        applications.add(app3);
        customer.setIpoApplications(applications);
        
        // Execute: Get application count summary
        int result = service.getApplicationCountSummary(customer);
        
        // Verify: Should return 3 (all non-pending applications)
        assertEquals("Customer with 3 non-pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Customer with 5 applications of mixed statuses
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephoneNumber("555-0404");
        customer.setRestricted(false);
        
        // Create and add approved application
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("RoboCorp");
        app1.setNumberOfShares(100);
        app1.setAmountOfMoney(10000);
        app1.setDocument("QT-2023-105");
        app1.setStatus(ApplicationStatus.APPROVED);
        
        // Create and add rejected applications
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("AI Ventures");
        app2.setNumberOfShares(100);
        app2.setAmountOfMoney(10000);
        app2.setDocument("QT-2023-106");
        app2.setStatus(ApplicationStatus.REJECTED);
        
        IPOApplication app3 = new IPOApplication();
        app3.setCompanyName("NanoMed");
        app3.setNumberOfShares(100);
        app3.setAmountOfMoney(10000);
        app3.setDocument("QT-2024-003");
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Create and add pending applications
        IPOApplication app4 = new IPOApplication();
        app4.setCompanyName("GreenEnergy");
        app4.setNumberOfShares(100);
        app4.setAmountOfMoney(10000);
        app4.setDocument("QT-2024-004");
        app4.setStatus(ApplicationStatus.PENDING);
        
        IPOApplication app5 = new IPOApplication();
        app5.setCompanyName("CloudScale");
        app5.setNumberOfShares(100);
        app5.setAmountOfMoney(10000);
        app5.setDocument("QT-2024-005");
        app5.setStatus(ApplicationStatus.PENDING);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        applications.add(app3);
        applications.add(app4);
        applications.add(app5);
        customer.setIpoApplications(applications);
        
        // Execute: Get application count summary
        int result = service.getApplicationCountSummary(customer);
        
        // Verify: Should return 3 (only non-pending applications)
        assertEquals("Customer with 3 non-pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer with no applications after cancellation
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephoneNumber("555-0505");
        customer.setRestricted(false);
        
        // Create a pending application
        IPOApplication app = new IPOApplication();
        app.setCompanyName("Cloud");
        app.setNumberOfShares(10);
        app.setAmountOfMoney(5000);
        app.setDocument("QT-1010");
        app.setStatus(ApplicationStatus.PENDING);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app);
        customer.setIpoApplications(applications);
        
        // Cancel the application (remove from list)
        customer.getIpoApplications().clear();
        
        // Execute: Get application count summary
        int result = service.getApplicationCountSummary(customer);
        
        // Verify: Should return 0 since application was canceled/removed
        assertEquals("Customer with canceled application should return 0", 0, result);
    }
}