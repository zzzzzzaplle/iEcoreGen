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
        // Common setup that can be reused across tests
        document = new Document();
    }
    
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Setup: Customer "C101" with no IPO applications
        customer = new Customer();
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
    public void testCase2_singleApprovedRequest() {
        // Setup: Customer "C102" with one approved application
        customer = new Customer();
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setCanApplyForIPO(true);
        
        // Create company
        company = new Company();
        company.setName("QuantumTech");
        company.setEmail("quantumtech@gmail.com");
        
        // Create and add approved application
        Application application = new Application(company, 50, 2500.0, document, customer);
        application.setStatus(ApplicationStatus.APPROVAL);
        List<Application> applications = new ArrayList<>();
        applications.add(application);
        customer.setApplications(applications);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 for single approved application
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        customer = new Customer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false);
        
        // Create companies
        Company company1 = new Company();
        company1.setName("Neuralink");
        company1.setEmail("neuralink@example.com");
        
        Company company2 = new Company();
        company2.setName("SpaceY");
        company2.setEmail("spacey@example.com");
        
        Company company3 = new Company();
        company3.setName("BioGen");
        company3.setEmail("biogen@example.com");
        
        // Create applications
        Application app1 = new Application(company1, 100, 10000.0, document, customer);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2 = new Application(company2, 30, 15000.0, document, customer);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        Application app3 = new Application(company3, 20, 1000.0, document, customer);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Add applications to customer
        List<Application> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        applications.add(app3);
        customer.setApplications(applications);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 for 2 approved + 1 rejected applications
        assertEquals("Customer with 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Customer "C104" with 1 approved, 2 rejected, and 2 pending applications
        customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setCanApplyForIPO(true);
        
        // Create companies
        Company company1 = new Company();
        company1.setName("RoboCorp");
        company1.setEmail("robocorp@example.com");
        
        Company company2 = new Company();
        company2.setName("AI Ventures");
        company2.setEmail("aiventures@example.com");
        
        Company company3 = new Company();
        company3.setName("NanoMed");
        company3.setEmail("nanomed@example.com");
        
        Company company4 = new Company();
        company4.setName("GreenEnergy");
        company4.setEmail("greenenergy@example.com");
        
        Company company5 = new Company();
        company5.setName("CloudScale");
        company5.setEmail("cloudscale@example.com");
        
        // Create applications with different statuses
        Application app1 = new Application(company1, 100, 10000.0, document, customer);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2 = new Application(company2, 100, 10000.0, document, customer);
        app2.setStatus(ApplicationStatus.REJECTED);
        
        Application app3 = new Application(company3, 100, 10000.0, document, customer);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        Application app4 = new Application(company4, 100, 10000.0, document, customer);
        app4.setStatus(ApplicationStatus.PENDING);
        
        Application app5 = new Application(company5, 100, 10000.0, document, customer);
        app5.setStatus(ApplicationStatus.PENDING);
        
        // Add applications to customer
        List<Application> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        applications.add(app3);
        applications.add(app4);
        applications.add(app5);
        customer.setApplications(applications);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 for 1 approved + 2 rejected (pending not counted)
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending should return 3", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" creates and cancels an application
        customer = new Customer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);
        
        // Create company
        company = new Company();
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        // Create a pending application
        Application application = new Application(company, 10, 5000.0, document, customer);
        application.setStatus(ApplicationStatus.PENDING);
        
        // Add application to customer
        List<Application> applications = new ArrayList<>();
        applications.add(application);
        customer.setApplications(applications);
        
        // Cancel the application
        boolean cancelResult = customer.cancelApplication("Cloud");
        assertTrue("Application should be canceled successfully", cancelResult);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 since canceled application becomes rejected
        assertEquals("Customer with canceled application should return 0", 0, result);
    }
}