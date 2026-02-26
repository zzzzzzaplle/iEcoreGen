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
        // Common setup that runs before each test
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
        
        // Input: Request application count summary
        int result = customer.getApplicationCount();
        
        // Expected Output: 0
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
        
        // Create company and application
        company = new Company();
        company.setName("QuantumTech");
        company.setEmail("quantumtech@gmail.com");
        
        Application application = new Application();
        application.setShare(50);
        application.setAmountOfMoney(2500);
        application.setStatus(ApplicationStatus.APPROVAL);
        application.setCustomer(customer);
        application.setCompany(company);
        application.setAllowance(document);
        
        // Add application to customer's list
        List<Application> applications = new ArrayList<>();
        applications.add(application);
        customer.setApplications(applications);
        
        // Input: Request total number of filings
        int result = customer.getApplicationCount();
        
        // Expected Output: 1
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
        
        List<Application> applications = new ArrayList<>();
        
        // First approved application
        Company company1 = new Company();
        company1.setName("Neuralink");
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(10000);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setAllowance(document);
        applications.add(app1);
        
        // Second approved application
        Company company2 = new Company();
        company2.setName("SpaceY");
        Application app2 = new Application();
        app2.setShare(30);
        app2.setAmountOfMoney(15000);
        app2.setStatus(ApplicationStatus.APPROVAL);
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setAllowance(document);
        applications.add(app2);
        
        // Rejected application
        Company company3 = new Company();
        company3.setName("BioGen");
        Application app3 = new Application();
        app3.setShare(20);
        app3.setAmountOfMoney(1000);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCustomer(customer);
        app3.setCompany(company3);
        app3.setAllowance(document);
        applications.add(app3);
        
        customer.setApplications(applications);
        
        // Input: Check total filings
        int result = customer.getApplicationCount();
        
        // Expected Output: 3
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
        
        List<Application> applications = new ArrayList<>();
        
        // Approved application
        Company company1 = new Company();
        company1.setName("RoboCorp");
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(10000);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setAllowance(document);
        applications.add(app1);
        
        // First rejected application
        Company company2 = new Company();
        company2.setName("AI Ventures");
        Application app2 = new Application();
        app2.setShare(100);
        app2.setAmountOfMoney(10000);
        app2.setStatus(ApplicationStatus.REJECTED);
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setAllowance(document);
        applications.add(app2);
        
        // Second rejected application
        Company company3 = new Company();
        company3.setName("NanoMed");
        Application app3 = new Application();
        app3.setShare(100);
        app3.setAmountOfMoney(10000);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCustomer(customer);
        app3.setCompany(company3);
        app3.setAllowance(document);
        applications.add(app3);
        
        // First pending application (should not be counted)
        Company company4 = new Company();
        company4.setName("GreenEnergy");
        Application app4 = new Application();
        app4.setShare(100);
        app4.setAmountOfMoney(10000);
        app4.setStatus(ApplicationStatus.PENDING);
        app4.setCustomer(customer);
        app4.setCompany(company4);
        app4.setAllowance(document);
        applications.add(app4);
        
        // Second pending application (should not be counted)
        Company company5 = new Company();
        company5.setName("CloudScale");
        Application app5 = new Application();
        app5.setShare(100);
        app5.setAmountOfMoney(10000);
        app5.setStatus(ApplicationStatus.PENDING);
        app5.setCustomer(customer);
        app5.setCompany(company5);
        app5.setAllowance(document);
        applications.add(app5);
        
        customer.setApplications(applications);
        
        // Input: Query overall count
        int result = customer.getApplicationCount();
        
        // Expected Output: 3 (only approved and rejected, not pending)
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" with a canceled application
        customer = new Customer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);
        
        // Create and add a pending application
        company = new Company();
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
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
        
        // Cancel the application
        boolean cancelResult = customer.cancelApplication("Cloud");
        assertTrue("Application cancellation should succeed", cancelResult);
        
        // Input: Request the count figure
        int result = customer.getApplicationCount();
        
        // Expected Output: 0 (canceled application becomes rejected, but originally was pending)
        assertEquals("Customer with canceled application should return 0", 0, result);
    }
}