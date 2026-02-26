import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

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
        // Setup: Customer "C101" with no IPO applications
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setCanApplyForIPO(true);
        
        // Test: Customer requests application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 when no applications exist
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_singleApprovedRequest() {
        // Setup: Customer "C102" with one approved application
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setCanApplyForIPO(true);
        
        // Create company
        company.setName("QuantumTech");
        company.setEmail("quantumtech@gmail.com");
        
        // Create application and set it to approved status
        Application application = new Application();
        application.setShare(50);
        application.setAmountOfMoney(2500);
        application.setCustomer(customer);
        application.setCompany(company);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.APPROVAL);
        
        // Add application to customer
        List<Application> applications = new ArrayList<>();
        applications.add(application);
        customer.setApplications(applications);
        
        // Test: Customer requests application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 for single approved application
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer "C103" with mixed approved and rejected applications
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false); // Customer cannot apply for IPO
        
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
        
        // Create approved application 1
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(10000);
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setAllowance(document);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        // Create approved application 2
        Application app2 = new Application();
        app2.setShare(30);
        app2.setAmountOfMoney(15000);
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setAllowance(document);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        // Create rejected application
        Application app3 = new Application();
        app3.setShare(20);
        app3.setAmountOfMoney(1000);
        app3.setCustomer(customer);
        app3.setCompany(company3);
        app3.setAllowance(document);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Add all applications to customer
        List<Application> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        applications.add(app3);
        customer.setApplications(applications);
        
        // Test: Customer requests application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 for two approved and one rejected applications
        assertEquals("Customer with 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Customer "C104" with mixed status applications
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
        
        // Create approved application
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(10000);
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setAllowance(document);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        // Create rejected applications
        Application app2 = new Application();
        app2.setShare(100);
        app2.setAmountOfMoney(10000);
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setAllowance(document);
        app2.setStatus(ApplicationStatus.REJECTED);
        
        Application app3 = new Application();
        app3.setShare(100);
        app3.setAmountOfMoney(10000);
        app3.setCustomer(customer);
        app3.setCompany(company3);
        app3.setAllowance(document);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Create pending applications (should not be counted)
        Application app4 = new Application();
        app4.setShare(100);
        app4.setAmountOfMoney(10000);
        app4.setCustomer(customer);
        app4.setCompany(company4);
        app4.setAllowance(document);
        app4.setStatus(ApplicationStatus.PENDING);
        
        Application app5 = new Application();
        app5.setShare(100);
        app5.setAmountOfMoney(10000);
        app5.setCustomer(customer);
        app5.setCompany(company5);
        app5.setAllowance(document);
        app5.setStatus(ApplicationStatus.PENDING);
        
        // Add all applications to customer
        List<Application> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        applications.add(app3);
        applications.add(app4);
        applications.add(app5);
        customer.setApplications(applications);
        
        // Test: Customer requests application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (1 approved + 2 rejected), pending applications not counted
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" creates and then cancels an application
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);
        
        // Create company
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        // Create and add a pending application
        Application application = new Application();
        application.setShare(10);
        application.setAmountOfMoney(5000);
        application.setCustomer(customer);
        application.setCompany(company);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        List<Application> applications = new ArrayList<>();
        applications.add(application);
        customer.setApplications(applications);
        
        // Cancel the application
        boolean cancelResult = customer.cancelApplication("Cloud");
        assertTrue("Application cancellation should succeed", cancelResult);
        
        // Test: Customer requests application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 since canceled applications are rejected and not counted
        assertEquals("Customer with canceled application should return 0", 0, result);
    }
}