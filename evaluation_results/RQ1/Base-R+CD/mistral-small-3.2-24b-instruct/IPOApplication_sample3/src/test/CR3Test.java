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
        // Setup: Customer "C101" with no IPO applications
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setCanApplyForIPO(true);
        
        // Test: Retrieve application count summary
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
        
        // Create and add pending application
        Application application = new Application();
        application.setShare(50);
        application.setAmountOfMoney(2500.0);
        application.setStatus(ApplicationStatus.PENDING);
        application.setCustomer(customer);
        application.setCompany(company);
        application.setAllowance(document);
        
        customer.getApplications().add(application);
        
        // Test: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 (pending applications are counted)
        assertEquals("Customer with one pending application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_MixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false); // Cannot apply for IPO but existing applications should still be counted
        
        // Create Neuralink company and approved application
        Company neuralink = new Company();
        neuralink.setName("Neuralink");
        neuralink.setEmail("neuralink@example.com");
        
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setStatus(ApplicationStatus.APPROVED);
        app1.setCustomer(customer);
        app1.setCompany(neuralink);
        app1.setAllowance(document);
        
        // Create SpaceY company and approved application
        Company spacey = new Company();
        spacey.setName("SpaceY");
        spacey.setEmail("spacey@example.com");
        
        Application app2 = new Application();
        app2.setShare(30);
        app2.setAmountOfMoney(15000.0);
        app2.setStatus(ApplicationStatus.APPROVED);
        app2.setCustomer(customer);
        app2.setCompany(spacey);
        app2.setAllowance(document);
        
        // Create BioGen company and rejected application
        Company biogen = new Company();
        biogen.setName("BioGen");
        biogen.setEmail("biogen@example.com");
        
        Application app3 = new Application();
        app3.setShare(20);
        app3.setAmountOfMoney(1000.0);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCustomer(customer);
        app3.setCompany(biogen);
        app3.setAllowance(document);
        
        // Add all applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        
        // Test: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals("Customer with 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_FiveHistoricalRequests() {
        // Setup: Customer "C104" with 1 approved, 2 rejected, and 2 pending applications
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setCanApplyForIPO(true);
        
        // Create RoboCorp company and approved application
        Company robocorp = new Company();
        robocorp.setName("RoboCorp");
        robocorp.setEmail("robocorp@example.com");
        
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setStatus(ApplicationStatus.APPROVED);
        app1.setCustomer(customer);
        app1.setCompany(robocorp);
        app1.setAllowance(document);
        
        // Create AI Ventures company and rejected application
        Company aiVentures = new Company();
        aiVentures.setName("AI Ventures");
        aiVentures.setEmail("aiventures@example.com");
        
        Application app2 = new Application();
        app2.setShare(100);
        app2.setAmountOfMoney(10000.0);
        app2.setStatus(ApplicationStatus.REJECTED);
        app2.setCustomer(customer);
        app2.setCompany(aiVentures);
        app2.setAllowance(document);
        
        // Create NanoMed company and rejected application
        Company nanomed = new Company();
        nanomed.setName("NanoMed");
        nanomed.setEmail("nanomed@example.com");
        
        Application app3 = new Application();
        app3.setShare(100);
        app3.setAmountOfMoney(10000.0);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCustomer(customer);
        app3.setCompany(nanomed);
        app3.setAllowance(document);
        
        // Create GreenEnergy company and pending application
        Company greenEnergy = new Company();
        greenEnergy.setName("GreenEnergy");
        greenEnergy.setEmail("greenenergy@example.com");
        
        Application app4 = new Application();
        app4.setShare(100);
        app4.setAmountOfMoney(10000.0);
        app4.setStatus(ApplicationStatus.PENDING);
        app4.setCustomer(customer);
        app4.setCompany(greenEnergy);
        app4.setAllowance(document);
        
        // Create CloudScale company and pending application
        Company cloudScale = new Company();
        cloudScale.setName("CloudScale");
        cloudScale.setEmail("cloudscale@example.com");
        
        Application app5 = new Application();
        app5.setShare(100);
        app5.setAmountOfMoney(10000.0);
        app5.setStatus(ApplicationStatus.PENDING);
        app5.setCustomer(customer);
        app5.setCompany(cloudScale);
        app5.setAllowance(document);
        
        // Add all applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Test: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (1 approved + 2 rejected, pending applications are not counted)
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_AllRequestsCanceled() {
        // Setup: Customer "C105" with a canceled application
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);
        
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        // Create pending application
        Application application = new Application();
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setStatus(ApplicationStatus.PENDING);
        application.setCustomer(customer);
        application.setCompany(company);
        application.setAllowance(document);
        
        customer.getApplications().add(application);
        
        // Cancel the application
        application.cancel();
        
        // Test: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 (canceled application becomes rejected, but method counts non-pending applications)
        // Note: The specification says canceled applications should not be counted
        assertEquals("Customer with canceled application should return 0", 0, result);
    }
}