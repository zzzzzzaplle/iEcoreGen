import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        customer = new Customer();
        company = new Company();
        document = new Document();
    }
    
    @Test
    public void testCase1_NoApplicationsAtAll() {
        // Setup: Customer "C101" with no IPO requests
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setCanApplyForIPO(true);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 when no applications exist
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_SinglePendingRequest() {
        // Setup: Customer "C102" with one pending application
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setCanApplyForIPO(true);
        
        company.setName("QuantumTech");
        company.setEmail("quantumtech@gmail.com");
        
        // Create pending application
        Application application = new Application(company, 50, 2500.0, document, customer);
        // Status should be PENDING by default, no need to change
        
        // Add application to customer's list
        customer.getApplications().add(application);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 (pending applications are NOT included, but test spec says expected output is 1)
        // Note: There's a discrepancy between the method logic and test spec
        // Method excludes PENDING, but test spec expects 1 for pending-only scenario
        assertEquals("Single pending application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_MixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false);
        
        // Create first approved application
        Company company1 = new Company();
        company1.setName("Neuralink");
        company1.setEmail("neuralink@example.com");
        Application app1 = new Application(company1, 100, 10000.0, document, customer);
        app1.setStatus(ApplicationStatus.APPROVED);
        
        // Create second approved application
        Company company2 = new Company();
        company2.setName("SpaceY");
        company2.setEmail("spacey@example.com");
        Application app2 = new Application(company2, 30, 15000.0, document, customer);
        app2.setStatus(ApplicationStatus.APPROVED);
        
        // Create rejected application
        Company company3 = new Company();
        company3.setName("BioGen");
        company3.setEmail("biogen@example.com");
        Application app3 = new Application(company3, 20, 1000.0, document, customer);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Add all applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals("Mix of 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_FiveHistoricalRequests() {
        // Setup: Customer "C104" with 1 approved, 2 rejected, and 2 pending applications
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setCanApplyForIPO(true);
        
        // Create approved application
        Company company1 = new Company();
        company1.setName("RoboCorp");
        company1.setEmail("robocorp@example.com");
        Application app1 = new Application(company1, 100, 10000.0, document, customer);
        app1.setStatus(ApplicationStatus.APPROVED);
        
        // Create first rejected application
        Company company2 = new Company();
        company2.setName("AI Ventures");
        company2.setEmail("aiventures@example.com");
        Application app2 = new Application(company2, 100, 10000.0, document, customer);
        app2.setStatus(ApplicationStatus.REJECTED);
        
        // Create second rejected application
        Company company3 = new Company();
        company3.setName("NanoMed");
        company3.setEmail("nanomed@example.com");
        Application app3 = new Application(company3, 100, 10000.0, document, customer);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Create first pending application
        Company company4 = new Company();
        company4.setName("GreenEnergy");
        company4.setEmail("greenenergy@example.com");
        Application app4 = new Application(company4, 100, 10000.0, document, customer);
        // Status remains PENDING by default
        
        // Create second pending application
        Company company5 = new Company();
        company5.setName("CloudScale");
        company5.setEmail("cloudscale@example.com");
        Application app5 = new Application(company5, 100, 10000.0, document, customer);
        // Status remains PENDING by default
        
        // Add all applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (1 approved + 2 rejected; pending applications are excluded)
        assertEquals("1 approved, 2 rejected, and 2 pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_AllRequestsCanceled() {
        // Setup: Customer "C105" with a canceled application
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);
        
        // Create company
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        // Create and cancel application
        Application application = new Application(company, 10, 5000.0, document, customer);
        application.cancel(); // This changes status to REJECTED
        
        // Add application to customer
        customer.getApplications().add(application);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 (canceled applications become REJECTED, which should be counted)
        // Note: There's a discrepancy - canceled applications become REJECTED, so should be counted
        // But test spec expects 0
        assertEquals("Canceled application should return 0", 0, result);
    }
}