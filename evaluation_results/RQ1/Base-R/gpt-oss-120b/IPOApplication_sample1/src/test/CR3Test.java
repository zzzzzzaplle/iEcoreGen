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
        // Setup: Customer "C101" with no IPO requests
        Customer customer = new Customer();
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setEligible(true);
        customer.setApplications(new ArrayList<>());
        
        // Execute: Get application count summary
        int result = bank.getApplicationCountSummary(customer);
        
        // Verify: Should return 0 when no applications exist
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_singlePendingRequest() {
        // Setup: Customer "C102" with one pending application
        Customer customer = new Customer();
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setEligible(true);
        
        List<IPOApplication> applications = new ArrayList<>();
        IPOApplication app = new IPOApplication();
        app.setCustomer(customer);
        
        Company company = new Company("QuantumTech");
        app.setCompany(company);
        app.setNumberOfShares(50);
        app.setAmount(2500.0);
        app.setDocument("QT-2024-FormA");
        app.setStatus(ApplicationStatus.PENDING);
        
        applications.add(app);
        customer.setApplications(applications);
        
        // Execute: Get application count summary
        int result = bank.getApplicationCountSummary(customer);
        
        // Verify: Should return 1 (pending applications are NOT counted)
        assertEquals("Single pending application should not be counted", 0, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        Customer customer = new Customer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setEligible(false); // Customer cannot apply for IPO
        customer.setFailedAttempts(0);
        customer.setRestricted(false);
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // Approved application 1: Neuralink
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompany(new Company("Neuralink"));
        app1.setNumberOfShares(100);
        app1.setAmount(10000.0);
        app1.setDocument("QT-22023-101");
        app1.setStatus(ApplicationStatus.APPROVED);
        applications.add(app1);
        
        // Approved application 2: SpaceY
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompany(new Company("SpaceY"));
        app2.setNumberOfShares(30);
        app2.setAmount(15000.0);
        app2.setDocument("QT-2023-102");
        app2.setStatus(ApplicationStatus.APPROVED);
        applications.add(app2);
        
        // Rejected application: BioGen
        IPOApplication app3 = new IPOApplication();
        app3.setCustomer(customer);
        app3.setCompany(new Company("BioGen"));
        app3.setNumberOfShares(20);
        app3.setAmount(1000.0);
        app3.setDocument("QT-2024-002");
        app3.setStatus(ApplicationStatus.REJECTED);
        applications.add(app3);
        
        customer.setApplications(applications);
        
        // Execute: Get application count summary
        int result = bank.getApplicationCountSummary(customer);
        
        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals("Mix of 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Customer "C104" with 1 approved, 2 rejected, and 2 pending applications
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setEligible(true);
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // Approved application: RoboCorp
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompany(new Company("RoboCorp"));
        app1.setNumberOfShares(100);
        app1.setAmount(10000.0);
        app1.setDocument("QT-2023-105");
        app1.setStatus(ApplicationStatus.APPROVED);
        applications.add(app1);
        
        // Rejected application 1: AI Ventures
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompany(new Company("AI Ventures"));
        app2.setNumberOfShares(100);
        app2.setAmount(10000.0);
        app2.setDocument("QT-2023-106");
        app2.setStatus(ApplicationStatus.REJECTED);
        applications.add(app2);
        
        // Rejected application 2: NanoMed
        IPOApplication app3 = new IPOApplication();
        app3.setCustomer(customer);
        app3.setCompany(new Company("NanoMed"));
        app3.setNumberOfShares(100);
        app3.setAmount(10000.0);
        app3.setDocument("QT-2024-003");
        app3.setStatus(ApplicationStatus.REJECTED);
        applications.add(app3);
        
        // Pending application 1: GreenEnergy
        IPOApplication app4 = new IPOApplication();
        app4.setCustomer(customer);
        app4.setCompany(new Company("GreenEnergy"));
        app4.setNumberOfShares(100);
        app4.setAmount(10000.0);
        app4.setDocument("QT-2024-004");
        app4.setStatus(ApplicationStatus.PENDING);
        applications.add(app4);
        
        // Pending application 2: CloudScale
        IPOApplication app5 = new IPOApplication();
        app5.setCustomer(customer);
        app5.setCompany(new Company("CloudScale"));
        app5.setNumberOfShares(100);
        app5.setAmount(10000.0);
        app5.setDocument("QT-2024-005");
        app5.setStatus(ApplicationStatus.PENDING);
        applications.add(app5);
        
        customer.setApplications(applications);
        
        // Execute: Get application count summary
        int result = bank.getApplicationCountSummary(customer);
        
        // Verify: Should return 3 (1 approved + 2 rejected, excluding 2 pending)
        assertEquals("5 applications with 2 pending should return 3", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" with a pending application that gets canceled
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setEligible(true);
        customer.setApplications(new ArrayList<>());
        
        // Create a pending application
        Company company = new Company("Cloud");
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument("QT-1010");
        application.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application);
        
        // Cancel the pending application
        boolean canceled = bank.cancelPendingApplication(customer, "Cloud");
        assertTrue("Application should be successfully canceled", canceled);
        
        // Execute: Get application count summary
        int result = bank.getApplicationCountSummary(customer);
        
        // Verify: Should return 0 after cancellation
        assertEquals("Canceled pending application should result in 0", 0, result);
    }
}