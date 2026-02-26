import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Setup: Customer "C101" with no IPO requests
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setCanApplyForIPO(true);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 when no applications exist
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_singleApprovedRequest() {
        // Setup: Customer "C102" with one approved application
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setCanApplyForIPO(true);
        
        // Create application with APPROVAL status
        Application application = new Application();
        Company company = new Company();
        company.setName("QuantumTech");
        company.setEmail("quantumtech@gmail.com");
        
        application.setCompany(company);
        application.setShare(50);
        application.setAmountOfMoney(2500.0);
        application.setAllowance(new Document());
        application.setStatus(ApplicationStatus.APPROVAL);
        
        customer.getApplications().add(application);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 for single approved application
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false);
        
        // Create first approved application
        Application app1 = new Application();
        Company company1 = new Company();
        company1.setName("Neuralink");
        app1.setCompany(company1);
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setAllowance(new Document());
        app1.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app1);
        
        // Create second approved application
        Application app2 = new Application();
        Company company2 = new Company();
        company2.setName("SpaceY");
        app2.setCompany(company2);
        app2.setShare(30);
        app2.setAmountOfMoney(15000.0);
        app2.setAllowance(new Document());
        app2.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app2);
        
        // Create rejected application
        Application app3 = new Application();
        Company company3 = new Company();
        company3.setName("BioGen");
        app3.setCompany(company3);
        app3.setShare(20);
        app3.setAmountOfMoney(1000.0);
        app3.setAllowance(new Document());
        app3.setStatus(ApplicationStatus.REJECTED);
        customer.getApplications().add(app3);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals("Customer with 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Customer "C104" with 1 approved, 2 rejected, and 2 pending applications
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setCanApplyForIPO(true);
        
        // Create approved application
        Application app1 = new Application();
        Company company1 = new Company();
        company1.setName("RoboCorp");
        app1.setCompany(company1);
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setAllowance(new Document());
        app1.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app1);
        
        // Create first rejected application
        Application app2 = new Application();
        Company company2 = new Company();
        company2.setName("AI Ventures");
        app2.setCompany(company2);
        app2.setShare(100);
        app2.setAmountOfMoney(10000.0);
        app2.setAllowance(new Document());
        app2.setStatus(ApplicationStatus.REJECTED);
        customer.getApplications().add(app2);
        
        // Create second rejected application
        Application app3 = new Application();
        Company company3 = new Company();
        company3.setName("NanoMed");
        app3.setCompany(company3);
        app3.setShare(100);
        app3.setAmountOfMoney(10000.0);
        app3.setAllowance(new Document());
        app3.setStatus(ApplicationStatus.REJECTED);
        customer.getApplications().add(app3);
        
        // Create first pending application (should NOT be counted)
        Application app4 = new Application();
        Company company4 = new Company();
        company4.setName("GreenEnergy");
        app4.setCompany(company4);
        app4.setShare(100);
        app4.setAmountOfMoney(10000.0);
        app4.setAllowance(new Document());
        app4.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(app4);
        
        // Create second pending application (should NOT be counted)
        Application app5 = new Application();
        Company company5 = new Company();
        company5.setName("CloudScale");
        app5.setCompany(company5);
        app5.setShare(100);
        app5.setAmountOfMoney(10000.0);
        app5.setAllowance(new Document());
        app5.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(app5);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (1 approved + 2 rejected), pending applications excluded
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" creates and cancels an application
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);
        
        // Create a pending application
        Application application = new Application();
        Company company = new Company();
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setAllowance(new Document());
        application.setStatus(ApplicationStatus.PENDING);
        application.setCustomer(customer);
        customer.getApplications().add(application);
        
        // Cancel the application (changes status to REJECTED)
        boolean cancelResult = application.cancel();
        assertTrue("Application cancellation should succeed", cancelResult);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 since canceled application becomes REJECTED
        assertEquals("Customer with canceled application (now REJECTED) should return 1", 1, result);
    }
}