import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
        // Setup: Customer "C101" with no applications
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setCanApplyForIPO(true);
        
        // Test: Retrieve application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 when no applications exist
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_SingleApprovedRequest() {
        // Setup: Customer "C102" with one approved application
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setCanApplyForIPO(true);
        
        // Create and add approved application
        Application application = new Application();
        application.setCustomer(customer);
        
        Company quantumTech = new Company();
        quantumTech.setName("QuantumTech");
        quantumTech.setEmail("quantumtech@gmail.com");
        application.setCompany(quantumTech);
        
        application.setShare(50);
        application.setAmountOfMoney(2500.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.APPROVAL);
        
        List<Application> applications = new ArrayList<>();
        applications.add(application);
        customer.setApplications(applications);
        
        // Test: Retrieve application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 for single approved application
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_MixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false);
        
        List<Application> applications = new ArrayList<>();
        
        // Create first approved application (Neuralink)
        Application app1 = new Application();
        app1.setCustomer(customer);
        
        Company neuralink = new Company();
        neuralink.setName("Neuralink");
        neuralink.setEmail("neuralink@example.com");
        app1.setCompany(neuralink);
        
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setAllowance(document);
        app1.setStatus(ApplicationStatus.APPROVAL);
        applications.add(app1);
        
        // Create second approved application (SpaceY)
        Application app2 = new Application();
        app2.setCustomer(customer);
        
        Company spaceY = new Company();
        spaceY.setName("SpaceY");
        spaceY.setEmail("spacey@example.com");
        app2.setCompany(spaceY);
        
        app2.setShare(30);
        app2.setAmountOfMoney(15000.0);
        app2.setAllowance(document);
        app2.setStatus(ApplicationStatus.APPROVAL);
        applications.add(app2);
        
        // Create rejected application (BioGen)
        Application app3 = new Application();
        app3.setCustomer(customer);
        
        Company bioGen = new Company();
        bioGen.setName("BioGen");
        bioGen.setEmail("biogen@example.com");
        app3.setCompany(bioGen);
        
        app3.setShare(20);
        app3.setAmountOfMoney(1000.0);
        app3.setAllowance(document);
        app3.setStatus(ApplicationStatus.REJECTED);
        applications.add(app3);
        
        customer.setApplications(applications);
        
        // Test: Retrieve application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 for 2 approved + 1 rejected applications
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
        
        List<Application> applications = new ArrayList<>();
        
        // Create approved application (RoboCorp)
        Application app1 = new Application();
        app1.setCustomer(customer);
        
        Company roboCorp = new Company();
        roboCorp.setName("RoboCorp");
        roboCorp.setEmail("robocorp@example.com");
        app1.setCompany(roboCorp);
        
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setAllowance(document);
        app1.setStatus(ApplicationStatus.APPROVAL);
        applications.add(app1);
        
        // Create first rejected application (AI Ventures)
        Application app2 = new Application();
        app2.setCustomer(customer);
        
        Company aiVentures = new Company();
        aiVentures.setName("AI Ventures");
        aiVentures.setEmail("aiventures@example.com");
        app2.setCompany(aiVentures);
        
        app2.setShare(100);
        app2.setAmountOfMoney(10000.0);
        app2.setAllowance(document);
        app2.setStatus(ApplicationStatus.REJECTED);
        applications.add(app2);
        
        // Create second rejected application (NanoMed)
        Application app3 = new Application();
        app3.setCustomer(customer);
        
        Company nanoMed = new Company();
        nanoMed.setName("NanoMed");
        nanoMed.setEmail("nanomed@example.com");
        app3.setCompany(nanoMed);
        
        app3.setShare(100);
        app3.setAmountOfMoney(10000.0);
        app3.setAllowance(document);
        app3.setStatus(ApplicationStatus.REJECTED);
        applications.add(app3);
        
        // Create first pending application (GreenEnergy)
        Application app4 = new Application();
        app4.setCustomer(customer);
        
        Company greenEnergy = new Company();
        greenEnergy.setName("GreenEnergy");
        greenEnergy.setEmail("greenenergy@example.com");
        app4.setCompany(greenEnergy);
        
        app4.setShare(100);
        app4.setAmountOfMoney(10000.0);
        app4.setAllowance(document);
        app4.setStatus(ApplicationStatus.PENDING);
        applications.add(app4);
        
        // Create second pending application (CloudScale)
        Application app5 = new Application();
        app5.setCustomer(customer);
        
        Company cloudScale = new Company();
        cloudScale.setName("CloudScale");
        cloudScale.setEmail("cloudscale@example.com");
        app5.setCompany(cloudScale);
        
        app5.setShare(100);
        app5.setAmountOfMoney(10000.0);
        app5.setAllowance(document);
        app5.setStatus(ApplicationStatus.PENDING);
        applications.add(app5);
        
        customer.setApplications(applications);
        
        // Test: Retrieve application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (1 approved + 2 rejected, excluding 2 pending)
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_AllRequestsCanceled() {
        // Setup: Customer "C105" creates and cancels an application
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);
        
        // Create company for the application
        Company cloudCompany = new Company();
        cloudCompany.setName("Cloud");
        cloudCompany.setEmail("Cloud@gmail.com");
        
        // Create application and add to customer
        Application application = new Application();
        application.setCustomer(customer);
        application.setCompany(cloudCompany);
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        List<Application> applications = new ArrayList<>();
        applications.add(application);
        customer.setApplications(applications);
        
        // Cancel the application
        boolean cancelResult = customer.cancelApplication("Cloud");
        assertTrue("Application should be successfully canceled", cancelResult);
        
        // Test: Retrieve application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 since canceled application becomes rejected and pending ones are not counted
        assertEquals("Customer with canceled application should return 0", 0, result);
    }
}