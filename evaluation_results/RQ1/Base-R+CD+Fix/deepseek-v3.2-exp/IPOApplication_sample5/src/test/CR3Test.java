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
        
        // Test: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Expected output is 0
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
        
        // Create company and application
        company.setName("QuantumTech");
        company.setEmail("quantumtech@gmail.com");
        
        Application application = new Application();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(50);
        application.setAmountOfMoney(2500.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.APPROVAL);
        
        // Add application to customer's list
        List<Application> applications = new ArrayList<>();
        applications.add(application);
        customer.setApplications(applications);
        
        // Test: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Expected output is 1
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
        Application app1 = new Application();
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setAllowance(document);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2 = new Application();
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setShare(30);
        app2.setAmountOfMoney(15000.0);
        app2.setAllowance(document);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        Application app3 = new Application();
        app3.setCustomer(customer);
        app3.setCompany(company3);
        app3.setShare(20);
        app3.setAmountOfMoney(1000.0);
        app3.setAllowance(document);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Add applications to customer's list
        List<Application> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        applications.add(app3);
        customer.setApplications(applications);
        
        // Test: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Expected output is 3
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
        
        // Create applications
        Application app1 = new Application(); // APPROVED
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setAllowance(document);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2 = new Application(); // REJECTED
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setShare(100);
        app2.setAmountOfMoney(10000.0);
        app2.setAllowance(document);
        app2.setStatus(ApplicationStatus.REJECTED);
        
        Application app3 = new Application(); // REJECTED
        app3.setCustomer(customer);
        app3.setCompany(company3);
        app3.setShare(100);
        app3.setAmountOfMoney(10000.0);
        app3.setAllowance(document);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        Application app4 = new Application(); // PENDING
        app4.setCustomer(customer);
        app4.setCompany(company4);
        app4.setShare(100);
        app4.setAmountOfMoney(10000.0);
        app4.setAllowance(document);
        app4.setStatus(ApplicationStatus.PENDING);
        
        Application app5 = new Application(); // PENDING
        app5.setCustomer(customer);
        app5.setCompany(company5);
        app5.setShare(100);
        app5.setAmountOfMoney(10000.0);
        app5.setAllowance(document);
        app5.setStatus(ApplicationStatus.PENDING);
        
        // Add applications to customer's list
        List<Application> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        applications.add(app3);
        applications.add(app4);
        applications.add(app5);
        customer.setApplications(applications);
        
        // Test: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Expected output is 3 (only approved and rejected count)
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
        
        // Create company
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        // Create and cancel application
        Application application = new Application();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        // Add application to customer's list
        List<Application> applications = new ArrayList<>();
        applications.add(application);
        customer.setApplications(applications);
        
        // Cancel the application (changes status to REJECTED)
        application.cancel();
        
        // Test: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Expected output is 1 (canceled applications become REJECTED)
        assertEquals("Customer with one canceled application should return 1", 1, result);
    }
}