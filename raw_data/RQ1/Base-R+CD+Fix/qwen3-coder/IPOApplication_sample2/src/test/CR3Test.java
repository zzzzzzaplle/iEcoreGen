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
    public void testCase1_noApplicationsAtAll() {
        // Setup: Customer "C101" with no applications
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
    public void testCase2_singlePendingRequest() {
        // Setup: Customer "C102" with one pending application
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setCanApplyForIPO(true);
        
        // Create company and application
        company.setName("QuantumTech");
        company.setEmail("quantumtech@gmail.com");
        
        Application app = new Application();
        app.setShare(50);
        app.setAmountOfMoney(2500.0);
        app.setStatus(ApplicationStatus.APPROVED); // According to spec, status is approval
        app.setCustomer(customer);
        app.setCompany(company);
        app.setAllowance(document);
        
        customer.getApplications().add(app);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 for the approved application
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false); // According to spec, cannot apply for IPO
        
        // Create companies
        Company neuralink = new Company();
        neuralink.setName("Neuralink");
        neuralink.setEmail("neuralink@example.com");
        
        Company spaceY = new Company();
        spaceY.setName("SpaceY");
        spaceY.setEmail("spacey@example.com");
        
        Company bioGen = new Company();
        bioGen.setName("BioGen");
        bioGen.setEmail("biogen@example.com");
        
        // Create approved applications
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setStatus(ApplicationStatus.APPROVED);
        app1.setCustomer(customer);
        app1.setCompany(neuralink);
        app1.setAllowance(document);
        
        Application app2 = new Application();
        app2.setShare(30);
        app2.setAmountOfMoney(15000.0);
        app2.setStatus(ApplicationStatus.APPROVED);
        app2.setCustomer(customer);
        app2.setCompany(spaceY);
        app2.setAllowance(document);
        
        // Create rejected application
        Application app3 = new Application();
        app3.setShare(20);
        app3.setAmountOfMoney(1000.0);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCustomer(customer);
        app3.setCompany(bioGen);
        app3.setAllowance(document);
        
        // Add all applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        
        // Execute: Get application count
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
        
        // Create companies
        Company roboCorp = new Company();
        roboCorp.setName("RoboCorp");
        roboCorp.setEmail("robocorp@example.com");
        
        Company aiVentures = new Company();
        aiVentures.setName("AI Ventures");
        aiVentures.setEmail("aiventures@example.com");
        
        Company nanoMed = new Company();
        nanoMed.setName("NanoMed");
        nanoMed.setEmail("nanomed@example.com");
        
        Company greenEnergy = new Company();
        greenEnergy.setName("GreenEnergy");
        greenEnergy.setEmail("greenenergy@example.com");
        
        Company cloudScale = new Company();
        cloudScale.setName("CloudScale");
        cloudScale.setEmail("cloudscale@example.com");
        
        // Create approved application
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setStatus(ApplicationStatus.APPROVED);
        app1.setCustomer(customer);
        app1.setCompany(roboCorp);
        app1.setAllowance(document);
        
        // Create rejected applications
        Application app2 = new Application();
        app2.setShare(100);
        app2.setAmountOfMoney(10000.0);
        app2.setStatus(ApplicationStatus.REJECTED);
        app2.setCustomer(customer);
        app2.setCompany(aiVentures);
        app2.setAllowance(document);
        
        Application app3 = new Application();
        app3.setShare(100);
        app3.setAmountOfMoney(10000.0);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCustomer(customer);
        app3.setCompany(nanoMed);
        app3.setAllowance(document);
        
        // Create pending applications
        Application app4 = new Application();
        app4.setShare(100);
        app4.setAmountOfMoney(10000.0);
        app4.setStatus(ApplicationStatus.PENDING);
        app4.setCustomer(customer);
        app4.setCompany(greenEnergy);
        app4.setAllowance(document);
        
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
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (1 approved + 2 rejected, excluding 2 pending)
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" with one canceled application
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);
        
        // Create company
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        // Create and add pending application
        Application app = new Application();
        app.setShare(10);
        app.setAmountOfMoney(5000.0);
        app.setStatus(ApplicationStatus.PENDING);
        app.setCustomer(customer);
        app.setCompany(company);
        app.setAllowance(document);
        
        customer.getApplications().add(app);
        
        // Cancel the application
        customer.cancelApplication("Cloud");
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 (canceled applications become rejected and are counted)
        // Note: According to cancelApplication logic, pending applications become rejected when canceled
        assertEquals("Customer with canceled application should return 0", 0, result);
    }
}