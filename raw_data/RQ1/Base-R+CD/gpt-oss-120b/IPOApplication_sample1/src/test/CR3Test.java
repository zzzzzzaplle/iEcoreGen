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
        // Setup: Customer "C101" with no IPO applications
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setCanApplyForIPO(true);
        customer.setApplications(new ArrayList<Application>());
        
        // Execute: Get application count
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
        application.setShare(50);
        application.setAmountOfMoney(2500.0);
        
        company.setName("QuantumTech");
        company.setEmail("quantumtech@gmail.com");
        application.setCompany(company);
        application.setCustomer(customer);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.APPROVAL);
        
        List<Application> applications = new ArrayList<Application>();
        applications.add(application);
        customer.setApplications(applications);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 for single approved application
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_MixOfApprovedAndRejected() {
        // Setup: Customer "C103" with mixed approved and rejected applications
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false);
        
        List<Application> applications = new ArrayList<Application>();
        
        // Add first approved application
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        company = new Company();
        company.setName("Neuralink");
        app1.setCompany(company);
        app1.setCustomer(customer);
        app1.setAllowance(document);
        app1.setStatus(ApplicationStatus.APPROVAL);
        applications.add(app1);
        
        // Add second approved application
        Application app2 = new Application();
        app2.setShare(30);
        app2.setAmountOfMoney(15000.0);
        Company company2 = new Company();
        company2.setName("SpaceY");
        app2.setCompany(company2);
        app2.setCustomer(customer);
        app2.setAllowance(document);
        app2.setStatus(ApplicationStatus.APPROVAL);
        applications.add(app2);
        
        // Add rejected application
        Application app3 = new Application();
        app3.setShare(20);
        app3.setAmountOfMoney(1000.0);
        Company company3 = new Company();
        company3.setName("BioGen");
        app3.setCompany(company3);
        app3.setCustomer(customer);
        app3.setAllowance(document);
        app3.setStatus(ApplicationStatus.REJECTED);
        applications.add(app3);
        
        customer.setApplications(applications);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 for two approved and one rejected applications
        assertEquals("Customer with 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_FiveHistoricalRequests() {
        // Setup: Customer "C104" with mixed status applications
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setCanApplyForIPO(true);
        
        List<Application> applications = new ArrayList<Application>();
        
        // Add approved application
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        company = new Company();
        company.setName("RoboCorp");
        app1.setCompany(company);
        app1.setCustomer(customer);
        app1.setAllowance(document);
        app1.setStatus(ApplicationStatus.APPROVAL);
        applications.add(app1);
        
        // Add first rejected application
        Application app2 = new Application();
        app2.setShare(100);
        app2.setAmountOfMoney(10000.0);
        Company company2 = new Company();
        company2.setName("AI Ventures");
        app2.setCompany(company2);
        app2.setCustomer(customer);
        app2.setAllowance(document);
        app2.setStatus(ApplicationStatus.REJECTED);
        applications.add(app2);
        
        // Add second rejected application
        Application app3 = new Application();
        app3.setShare(100);
        app3.setAmountOfMoney(10000.0);
        Company company3 = new Company();
        company3.setName("NanoMed");
        app3.setCompany(company3);
        app3.setCustomer(customer);
        app3.setAllowance(document);
        app3.setStatus(ApplicationStatus.REJECTED);
        applications.add(app3);
        
        // Add first pending application (should not be counted)
        Application app4 = new Application();
        app4.setShare(100);
        app4.setAmountOfMoney(10000.0);
        Company company4 = new Company();
        company4.setName("GreenEnergy");
        app4.setCompany(company4);
        app4.setCustomer(customer);
        app4.setAllowance(document);
        app4.setStatus(ApplicationStatus.PENDING);
        applications.add(app4);
        
        // Add second pending application (should not be counted)
        Application app5 = new Application();
        app5.setShare(100);
        app5.setAmountOfMoney(10000.0);
        Company company5 = new Company();
        company5.setName("CloudScale");
        app5.setCompany(company5);
        app5.setCustomer(customer);
        app5.setAllowance(document);
        app5.setStatus(ApplicationStatus.PENDING);
        applications.add(app5);
        
        customer.setApplications(applications);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (1 approved + 2 rejected, excluding 2 pending)
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_AllRequestsCanceled() {
        // Setup: Customer "C105" with canceled application
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);
        
        // Create and add canceled application
        Application application = new Application();
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        application.setCompany(company);
        application.setCustomer(customer);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.REJECTED); // Canceled applications are rejected
        
        List<Application> applications = new ArrayList<Application>();
        applications.add(application);
        customer.setApplications(applications);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 for canceled (rejected) application
        assertEquals("Customer with one canceled (rejected) application should return 0", 0, result);
    }
}