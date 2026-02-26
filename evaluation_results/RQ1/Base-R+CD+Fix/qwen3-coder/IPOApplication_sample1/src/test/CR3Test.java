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
        // Setup: Customer "C101" with no IPO requests
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setCanApplyForIPO(true);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_singlePendingRequest() {
        // Setup: Customer "C102" with one pending application
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setCanApplyForIPO(true);
        
        company.setName("QuantumTech");
        company.setEmail("quantumtech@gmail.com");
        
        // Create pending application
        Application app = new Application();
        app.setShare(50);
        app.setAmountOfMoney(2500.0);
        app.setStatus(ApplicationStatus.PENDING);
        app.setCustomer(customer);
        app.setCompany(company);
        app.setAllowance(document);
        
        List<Application> applications = new ArrayList<>();
        applications.add(app);
        customer.setApplications(applications);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 (pending applications don't count)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false);
        
        // Create companies
        Company company1 = new Company();
        company1.setName("Neuralink");
        
        Company company2 = new Company();
        company2.setName("SpaceY");
        
        Company company3 = new Company();
        company3.setName("BioGen");
        
        // Create applications: 2 approved, 1 rejected
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setStatus(ApplicationStatus.APPROVED);
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setAllowance(document);
        
        Application app2 = new Application();
        app2.setShare(30);
        app2.setAmountOfMoney(15000.0);
        app2.setStatus(ApplicationStatus.APPROVED);
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setAllowance(document);
        
        Application app3 = new Application();
        app3.setShare(20);
        app3.setAmountOfMoney(1000.0);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCustomer(customer);
        app3.setCompany(company3);
        app3.setAllowance(document);
        
        List<Application> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        applications.add(app3);
        customer.setApplications(applications);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (approved + rejected)
        assertEquals(3, result);
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
        Company company1 = new Company();
        company1.setName("RoboCorp");
        
        Company company2 = new Company();
        company2.setName("AI Ventures");
        
        Company company3 = new Company();
        company3.setName("NanoMed");
        
        Company company4 = new Company();
        company4.setName("GreenEnergy");
        
        Company company5 = new Company();
        company5.setName("CloudScale");
        
        // Create applications
        Application app1 = new Application(); // APPROVED
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setStatus(ApplicationStatus.APPROVED);
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setAllowance(document);
        
        Application app2 = new Application(); // REJECTED
        app2.setShare(100);
        app2.setAmountOfMoney(10000.0);
        app2.setStatus(ApplicationStatus.REJECTED);
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setAllowance(document);
        
        Application app3 = new Application(); // REJECTED
        app3.setShare(100);
        app3.setAmountOfMoney(10000.0);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCustomer(customer);
        app3.setCompany(company3);
        app3.setAllowance(document);
        
        Application app4 = new Application(); // PENDING
        app4.setShare(100);
        app4.setAmountOfMoney(10000.0);
        app4.setStatus(ApplicationStatus.PENDING);
        app4.setCustomer(customer);
        app4.setCompany(company4);
        app4.setAllowance(document);
        
        Application app5 = new Application(); // PENDING
        app5.setShare(100);
        app5.setAmountOfMoney(10000.0);
        app5.setStatus(ApplicationStatus.PENDING);
        app5.setCustomer(customer);
        app5.setCompany(company5);
        app5.setAllowance(document);
        
        List<Application> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        applications.add(app3);
        applications.add(app4);
        applications.add(app5);
        customer.setApplications(applications);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (approved + rejected, excluding pending)
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" with one application that gets canceled
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);
        
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        // Create and cancel application
        Application app = new Application();
        app.setShare(10);
        app.setAmountOfMoney(5000.0);
        app.setStatus(ApplicationStatus.PENDING);
        app.setCustomer(customer);
        app.setCompany(company);
        app.setAllowance(document);
        
        List<Application> applications = new ArrayList<>();
        applications.add(app);
        customer.setApplications(applications);
        
        // Cancel the application (changes status to REJECTED)
        app.cancel();
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 (canceled applications are REJECTED)
        assertEquals(1, result);
    }
}