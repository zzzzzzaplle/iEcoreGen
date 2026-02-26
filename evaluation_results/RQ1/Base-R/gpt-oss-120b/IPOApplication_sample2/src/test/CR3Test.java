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
        // Setup: Customer "C101" with no IPO requests
        Customer customer = new Customer();
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setRetail(true); // can apply for IPO
        customer.setLocked(false);
        
        // Execute: Get application count summary
        int result = ipoService.getApplicationCountSummary(customer);
        
        // Verify: Should return 0 when no applications exist
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_singlePendingRequest() {
        // Setup: Customer "C102" with one pending application
        Customer customer = new Customer();
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setRetail(true); // can apply for IPO
        customer.setLocked(false);
        
        Company company = new Company();
        company.setName("QuantumTech");
        
        IPOApplication app = new IPOApplication();
        app.setCustomer(customer);
        app.setCompany(company);
        app.setNumberOfShares(50);
        app.setAmount(2500.0);
        app.setDocument(new byte[]{1, 2, 3}); // dummy document
        app.setStatus(ApplicationStatus.PENDING);
        
        customer.addApplication(app);
        
        // Execute: Get application count summary
        int result = ipoService.getApplicationCountSummary(customer);
        
        // Verify: Should return 0 because pending applications are not counted
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        Customer customer = new Customer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setRetail(false); // cannot apply for IPO
        customer.setLocked(false);
        
        // Create approved applications
        Company company1 = new Company();
        company1.setName("Neuralink");
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setNumberOfShares(100);
        app1.setAmount(10000.0);
        app1.setDocument(new byte[]{1});
        app1.setStatus(ApplicationStatus.APPROVED);
        customer.addApplication(app1);
        
        Company company2 = new Company();
        company2.setName("SpaceY");
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setNumberOfShares(30);
        app2.setAmount(15000.0);
        app2.setDocument(new byte[]{2});
        app2.setStatus(ApplicationStatus.APPROVED);
        customer.addApplication(app2);
        
        // Create rejected application
        Company company3 = new Company();
        company3.setName("BioGen");
        IPOApplication app3 = new IPOApplication();
        app3.setCustomer(customer);
        app3.setCompany(company3);
        app3.setNumberOfShares(20);
        app3.setAmount(1000.0);
        app3.setDocument(new byte[]{3});
        app3.setStatus(ApplicationStatus.REJECTED);
        customer.addApplication(app3);
        
        // Execute: Get application count summary
        int result = ipoService.getApplicationCountSummary(customer);
        
        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Customer "C104" with 1 approved, 2 rejected, and 2 pending applications
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setRetail(true); // can apply for IPO
        customer.setLocked(false);
        
        // Approved application
        Company company1 = new Company();
        company1.setName("RoboCorp");
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setNumberOfShares(100);
        app1.setAmount(10000.0);
        app1.setDocument(new byte[]{1});
        app1.setStatus(ApplicationStatus.APPROVED);
        customer.addApplication(app1);
        
        // Rejected applications
        Company company2 = new Company();
        company2.setName("AI Ventures");
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setNumberOfShares(100);
        app2.setAmount(10000.0);
        app2.setDocument(new byte[]{2});
        app2.setStatus(ApplicationStatus.REJECTED);
        customer.addApplication(app2);
        
        Company company3 = new Company();
        company3.setName("NanoMed");
        IPOApplication app3 = new IPOApplication();
        app3.setCustomer(customer);
        app3.setCompany(company3);
        app3.setNumberOfShares(100);
        app3.setAmount(10000.0);
        app3.setDocument(new byte[]{3});
        app3.setStatus(ApplicationStatus.REJECTED);
        customer.addApplication(app3);
        
        // Pending applications (should not be counted)
        Company company4 = new Company();
        company4.setName("GreenEnergy");
        IPOApplication app4 = new IPOApplication();
        app4.setCustomer(customer);
        app4.setCompany(company4);
        app4.setNumberOfShares(100);
        app4.setAmount(10000.0);
        app4.setDocument(new byte[]{4});
        app4.setStatus(ApplicationStatus.PENDING);
        customer.addApplication(app4);
        
        Company company5 = new Company();
        company5.setName("CloudScale");
        IPOApplication app5 = new IPOApplication();
        app5.setCustomer(customer);
        app5.setCompany(company5);
        app5.setNumberOfShares(100);
        app5.setAmount(10000.0);
        app5.setDocument(new byte[]{5});
        app5.setStatus(ApplicationStatus.PENDING);
        customer.addApplication(app5);
        
        // Execute: Get application count summary
        int result = ipoService.getApplicationCountSummary(customer);
        
        // Verify: Should return 3 (1 approved + 2 rejected, excluding 2 pending)
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" with a canceled application
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setRetail(true); // can apply for IPO
        customer.setLocked(false);
        
        // Create and cancel pending application
        Company company = new Company();
        company.setName("Cloud");
        
        IPOApplication app = new IPOApplication();
        app.setCustomer(customer);
        app.setCompany(company);
        app.setNumberOfShares(10);
        app.setAmount(5000.0);
        app.setDocument(new byte[]{1, 0, 1, 0});
        app.setStatus(ApplicationStatus.PENDING);
        customer.addApplication(app);
        
        // Cancel the application (remove it)
        boolean canceled = ipoService.cancelPendingApplication(customer, "Cloud");
        assertTrue(canceled);
        
        // Execute: Get application count summary
        int result = ipoService.getApplicationCountSummary(customer);
        
        // Verify: Should return 0 when all applications are canceled
        assertEquals(0, result);
    }
}