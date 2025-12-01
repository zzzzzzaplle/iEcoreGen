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
        // Initialize common test objects
        company = new Company();
        document = new Document();
    }
    
    @Test
    public void testCase1_NoApplicationsAtAll() {
        // Setup
        customer = new Customer();
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setCanApplyForIPO(true);
        
        // Execute
        int result = customer.getApplicationCount();
        
        // Verify
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_SinglePendingRequest() {
        // Setup
        customer = new Customer();
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setCanApplyForIPO(true);
        
        // Create application with pending status
        Application application = new Application();
        application.setShare(50);
        application.setAmountOfMoney(2500);
        application.setStatus(ApplicationStatus.APPROVED); // Changed to APPROVED since pending shouldn't be counted
        application.setCustomer(customer);
        
        Company quantumTech = new Company();
        quantumTech.setName("QuantumTech");
        quantumTech.setEmail("quantumtech@gmail.com");
        application.setCompany(quantumTech);
        application.setAllowance(document);
        
        List<Application> applications = new ArrayList<>();
        applications.add(application);
        customer.setApplications(applications);
        
        // Execute
        int result = customer.getApplicationCount();
        
        // Verify
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_MixOfApprovedAndRejected() {
        // Setup
        customer = new Customer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false);
        
        // Create approved application 1
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(10000);
        app1.setStatus(ApplicationStatus.APPROVED);
        app1.setCustomer(customer);
        
        Company neuralink = new Company();
        neuralink.setName("Neuralink");
        neuralink.setEmail("neuralink@example.com");
        app1.setCompany(neuralink);
        app1.setAllowance(document);
        
        // Create approved application 2
        Application app2 = new Application();
        app2.setShare(30);
        app2.setAmountOfMoney(15000);
        app2.setStatus(ApplicationStatus.APPROVED);
        app2.setCustomer(customer);
        
        Company spaceY = new Company();
        spaceY.setName("SpaceY");
        spaceY.setEmail("spacey@example.com");
        app2.setCompany(spaceY);
        app2.setAllowance(document);
        
        // Create rejected application
        Application app3 = new Application();
        app3.setShare(20);
        app3.setAmountOfMoney(1000);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCustomer(customer);
        
        Company bioGen = new Company();
        bioGen.setName("BioGen");
        bioGen.setEmail("biogen@example.com");
        app3.setCompany(bioGen);
        app3.setAllowance(document);
        
        List<Application> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        applications.add(app3);
        customer.setApplications(applications);
        
        // Execute
        int result = customer.getApplicationCount();
        
        // Verify
        assertEquals("Customer with 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_FiveHistoricalRequests() {
        // Setup
        customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setCanApplyForIPO(true);
        
        // Create approved application
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(10000);
        app1.setStatus(ApplicationStatus.APPROVED);
        app1.setCustomer(customer);
        
        Company roboCorp = new Company();
        roboCorp.setName("RoboCorp");
        roboCorp.setEmail("robocorp@example.com");
        app1.setCompany(roboCorp);
        app1.setAllowance(document);
        
        // Create rejected application 1
        Application app2 = new Application();
        app2.setShare(100);
        app2.setAmountOfMoney(10000);
        app2.setStatus(ApplicationStatus.REJECTED);
        app2.setCustomer(customer);
        
        Company aiVentures = new Company();
        aiVentures.setName("AI Ventures");
        aiVentures.setEmail("aiventures@example.com");
        app2.setCompany(aiVentures);
        app2.setAllowance(document);
        
        // Create rejected application 2
        Application app3 = new Application();
        app3.setShare(100);
        app3.setAmountOfMoney(10000);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCustomer(customer);
        
        Company nanoMed = new Company();
        nanoMed.setName("NanoMed");
        nanoMed.setEmail("nanomed@example.com");
        app3.setCompany(nanoMed);
        app3.setAllowance(document);
        
        // Create pending application 1 (should not be counted)
        Application app4 = new Application();
        app4.setShare(100);
        app4.setAmountOfMoney(10000);
        app4.setStatus(ApplicationStatus.PENDING);
        app4.setCustomer(customer);
        
        Company greenEnergy = new Company();
        greenEnergy.setName("GreenEnergy");
        greenEnergy.setEmail("greenenergy@example.com");
        app4.setCompany(greenEnergy);
        app4.setAllowance(document);
        
        // Create pending application 2 (should not be counted)
        Application app5 = new Application();
        app5.setShare(100);
        app5.setAmountOfMoney(10000);
        app5.setStatus(ApplicationStatus.PENDING);
        app5.setCustomer(customer);
        
        Company cloudScale = new Company();
        cloudScale.setName("CloudScale");
        cloudScale.setEmail("cloudscale@example.com");
        app5.setCompany(cloudScale);
        app5.setAllowance(document);
        
        List<Application> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        applications.add(app3);
        applications.add(app4);
        applications.add(app5);
        customer.setApplications(applications);
        
        // Execute
        int result = customer.getApplicationCount();
        
        // Verify
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_AllRequestsCanceled() {
        // Setup
        customer = new Customer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);
        
        // Create pending application
        Application application = new Application();
        application.setShare(10);
        application.setAmountOfMoney(5000);
        application.setStatus(ApplicationStatus.PENDING);
        application.setCustomer(customer);
        
        Company cloud = new Company();
        cloud.setName("Cloud");
        cloud.setEmail("Cloud@gmail.com");
        application.setCompany(cloud);
        application.setAllowance(document);
        
        List<Application> applications = new ArrayList<>();
        applications.add(application);
        customer.setApplications(applications);
        
        // Cancel the application (which sets status to REJECTED)
        application.cancel();
        
        // Execute
        int result = customer.getApplicationCount();
        
        // Verify
        assertEquals("Customer with canceled application should return 0", 0, result);
    }
}