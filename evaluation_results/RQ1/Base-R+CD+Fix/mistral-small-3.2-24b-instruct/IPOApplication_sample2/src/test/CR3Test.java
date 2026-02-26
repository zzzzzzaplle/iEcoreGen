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
        assertEquals("Customer with no applications should return count 0", 0, result);
    }
    
    @Test
    public void testCase2_singleApprovedRequest() {
        // Setup: Customer "C102" with one approved application
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setCanApplyForIPO(true);
        
        company.setName("QuantumTech");
        company.setEmail("quantumtech@gmail.com");
        
        // Create and add approved application
        Application application = new Application();
        application.setShare(50);
        application.setAmountOfMoney(2500);
        application.setStatus(ApplicationStatus.APPROVAL);
        application.setCustomer(customer);
        application.setCompany(company);
        application.setAllowance(document);
        
        List<Application> applications = new ArrayList<>();
        applications.add(application);
        customer.setApplications(applications);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 for single approved application
        assertEquals("Customer with one approved application should return count 1", 1, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false);
        
        List<Application> applications = new ArrayList<>();
        
        // Create first approved application
        Company company1 = new Company();
        company1.setName("Neuralink");
        company1.setEmail("neuralink@example.com");
        
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(10000);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setAllowance(document);
        applications.add(app1);
        
        // Create second approved application
        Company company2 = new Company();
        company2.setName("SpaceY");
        company2.setEmail("spacey@example.com");
        
        Application app2 = new Application();
        app2.setShare(30);
        app2.setAmountOfMoney(15000);
        app2.setStatus(ApplicationStatus.APPROVAL);
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setAllowance(document);
        applications.add(app2);
        
        // Create rejected application
        Company company3 = new Company();
        company3.setName("BioGen");
        company3.setEmail("biogen@example.com");
        
        Application app3 = new Application();
        app3.setShare(20);
        app3.setAmountOfMoney(1000);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCustomer(customer);
        app3.setCompany(company3);
        app3.setAllowance(document);
        applications.add(app3);
        
        customer.setApplications(applications);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 for 2 approved + 1 rejected applications
        assertEquals("Customer with 2 approved and 1 rejected applications should return count 3", 3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Customer "C104" with 1 approved, 2 rejected, and 2 pending applications
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setCanApplyForIPO(true);
        
        List<Application> applications = new ArrayList<>();
        
        // Create approved application
        Company company1 = new Company();
        company1.setName("RoboCorp");
        company1.setEmail("robocorp@example.com");
        
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(10000);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setAllowance(document);
        applications.add(app1);
        
        // Create first rejected application
        Company company2 = new Company();
        company2.setName("AI Ventures");
        company2.setEmail("aiventures@example.com");
        
        Application app2 = new Application();
        app2.setShare(100);
        app2.setAmountOfMoney(10000);
        app2.setStatus(ApplicationStatus.REJECTED);
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setAllowance(document);
        applications.add(app2);
        
        // Create second rejected application
        Company company3 = new Company();
        company3.setName("NanoMed");
        company3.setEmail("nanomed@example.com");
        
        Application app3 = new Application();
        app3.setShare(100);
        app3.setAmountOfMoney(10000);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCustomer(customer);
        app3.setCompany(company3);
        app3.setAllowance(document);
        applications.add(app3);
        
        // Create first pending application (should not be counted)
        Company company4 = new Company();
        company4.setName("GreenEnergy");
        company4.setEmail("greenenergy@example.com");
        
        Application app4 = new Application();
        app4.setShare(100);
        app4.setAmountOfMoney(10000);
        app4.setStatus(ApplicationStatus.PENDING);
        app4.setCustomer(customer);
        app4.setCompany(company4);
        app4.setAllowance(document);
        applications.add(app4);
        
        // Create second pending application (should not be counted)
        Company company5 = new Company();
        company5.setName("CloudScale");
        company5.setEmail("cloudscale@example.com");
        
        Application app5 = new Application();
        app5.setShare(100);
        app5.setAmountOfMoney(10000);
        app5.setStatus(ApplicationStatus.PENDING);
        app5.setCustomer(customer);
        app5.setCompany(company5);
        app5.setAllowance(document);
        applications.add(app5);
        
        customer.setApplications(applications);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (1 approved + 2 rejected), pending applications not counted
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending applications should return count 3", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" creates and then cancels an application
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);
        
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        // Create a pending application
        Application application = new Application();
        application.setShare(10);
        application.setAmountOfMoney(5000);
        application.setStatus(ApplicationStatus.PENDING);
        application.setCustomer(customer);
        application.setCompany(company);
        application.setAllowance(document);
        
        List<Application> applications = new ArrayList<>();
        applications.add(application);
        customer.setApplications(applications);
        
        // Cancel the application (changes status to REJECTED)
        application.cancel();
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 because canceled applications become REJECTED and are counted
        assertEquals("Canceled application becomes REJECTED and should be counted", 1, result);
    }
}