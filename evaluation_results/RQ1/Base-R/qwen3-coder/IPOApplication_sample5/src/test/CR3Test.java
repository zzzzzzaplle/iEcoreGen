import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private IPOApplicationSystem system;
    
    @Before
    public void setUp() {
        system = new IPOApplicationSystem();
    }
    
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Setup: Customer "C101" with no IPO requests
        Customer customer = new Customer();
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmailAddress("t.anderson@example.com");
        customer.setTelephoneNumber("555-0101");
        customer.setEligibleForIPO(true);
        
        // Execute: Get customer application count
        int result = system.getCustomerApplicationCount(customer);
        
        // Verify: Should return 0 for no applications
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_singlePendingRequest() {
        // Setup: Customer "C102" with one pending application
        Customer customer = new Customer();
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmailAddress("l.rodriguez@example.com");
        customer.setTelephoneNumber("555-0202");
        customer.setEligibleForIPO(true);
        
        Company company = new Company();
        company.setName("QuantumTech");
        company.setEmailAddress("quantumtech@gmail.com");
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setNumberOfShares(50);
        application.setAmount(2500.0);
        application.setDocument("QT-2024-FormA");
        // Application is pending (not approved/rejected, not reviewed)
        
        customer.getApplications().add(application);
        
        // Execute: Get customer application count
        int result = system.getCustomerApplicationCount(customer);
        
        // Verify: Should return 0 since pending applications are not counted
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        Customer customer = new Customer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmailAddress("d.kim@example.com");
        customer.setTelephoneNumber("555-0303");
        customer.setEligibleForIPO(false);
        
        // Create approved application 1
        Company company1 = new Company();
        company1.setName("Neuralink");
        
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setNumberOfShares(100);
        app1.setAmount(10000.0);
        app1.setDocument("QT-22023-101");
        app1.setApproved(true); // This sets reviewed to true
        
        // Create approved application 2
        Company company2 = new Company();
        company2.setName("SpaceY");
        
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setNumberOfShares(30);
        app2.setAmount(15000.0);
        app2.setDocument("QT-2023-102");
        app2.setApproved(true); // This sets reviewed to true
        
        // Create rejected application
        Company company3 = new Company();
        company3.setName("BioGen");
        
        IPOApplication app3 = new IPOApplication();
        app3.setCustomer(customer);
        app3.setCompany(company3);
        app3.setNumberOfShares(20);
        app3.setAmount(1000.0);
        app3.setDocument("QT-2024-002");
        app3.setRejected(true); // This sets reviewed to true
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        
        // Execute: Get customer application count
        int result = system.getCustomerApplicationCount(customer);
        
        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Customer "C104" with 1 approved, 2 rejected, and 2 pending applications
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmailAddress("e.wilson@example.com");
        customer.setTelephoneNumber("555-0404");
        customer.setEligibleForIPO(true);
        
        // Approved application
        Company company1 = new Company();
        company1.setName("RoboCorp");
        
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setNumberOfShares(100);
        app1.setAmount(10000.0);
        app1.setDocument("QT-2023-105");
        app1.setApproved(true); // This sets reviewed to true
        
        // Rejected application 1
        Company company2 = new Company();
        company2.setName("AI Ventures");
        
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setNumberOfShares(100);
        app2.setAmount(10000.0);
        app2.setDocument("QT-2023-106");
        app2.setRejected(true); // This sets reviewed to true
        
        // Rejected application 2
        Company company3 = new Company();
        company3.setName("NanoMed");
        
        IPOApplication app3 = new IPOApplication();
        app3.setCustomer(customer);
        app3.setCompany(company3);
        app3.setNumberOfShares(100);
        app3.setAmount(10000.0);
        app3.setDocument("QT-2024-003");
        app3.setRejected(true); // This sets reviewed to true
        
        // Pending application 1 (not reviewed)
        Company company4 = new Company();
        company4.setName("GreenEnergy");
        
        IPOApplication app4 = new IPOApplication();
        app4.setCustomer(customer);
        app4.setCompany(company4);
        app4.setNumberOfShares(100);
        app4.setAmount(10000.0);
        app4.setDocument("QT-2024-004");
        // Not approved/rejected, so not reviewed
        
        // Pending application 2 (not reviewed)
        Company company5 = new Company();
        company5.setName("CloudScale");
        
        IPOApplication app5 = new IPOApplication();
        app5.setCustomer(customer);
        app5.setCompany(company5);
        app5.setNumberOfShares(100);
        app5.setAmount(10000.0);
        app5.setDocument("QT-2024-005");
        // Not approved/rejected, so not reviewed
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Execute: Get customer application count
        int result = system.getCustomerApplicationCount(customer);
        
        // Verify: Should return 3 (1 approved + 2 rejected, pending are not counted)
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" with a canceled application
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmailAddress("j.chen@example.com");
        customer.setTelephoneNumber("555-0505");
        customer.setEligibleForIPO(true);
        
        // Create a pending application
        Company company = new Company();
        company.setName("Cloud");
        company.setEmailAddress("Cloud@gmail.com");
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument("QT-1010");
        // Application is pending (not approved/rejected)
        
        customer.getApplications().add(application);
        
        // Cancel the application (remove it from customer's applications)
        boolean canceled = system.cancelPendingApplication(customer, "Cloud");
        assertTrue(canceled);
        
        // Execute: Get customer application count
        int result = system.getCustomerApplicationCount(customer);
        
        // Verify: Should return 0 since the only application was canceled
        assertEquals(0, result);
    }
}