import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Customer customer;
    private Bank bank;
    
    @Before
    public void setUp() {
        bank = new Bank();
    }
    
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Setup: Customer "C101" with no IPO applications
        customer = new Customer();
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setEligible(true);
        
        // Execute: Get total application count
        int result = customer.getTotalApplicationCount();
        
        // Verify: Should return 0
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_singlePendingRequest() {
        // Setup: Customer "C102" with one pending application
        customer = new Customer();
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setEligible(true);
        
        // Create and add a pending application
        IpoApplication application = new IpoApplication();
        application.setCompany("QuantumTech");
        application.setNumberOfShares(50);
        application.setAmount(2500);
        application.setDocument("QT-2024-FormA");
        application.setApproved(false);
        application.setRejected(false);
        application.setCustomer(customer);
        
        List<IpoApplication> applications = new ArrayList<>();
        applications.add(application);
        customer.setApplications(applications);
        
        // Execute: Get total application count
        int result = customer.getTotalApplicationCount();
        
        // Verify: Should return 0 (pending applications don't count)
        assertEquals("Pending application should not be counted", 0, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        customer = new Customer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setEligible(false);
        
        List<IpoApplication> applications = new ArrayList<>();
        
        // Create approved application 1
        IpoApplication app1 = new IpoApplication();
        app1.setCompany("Neuralink");
        app1.setNumberOfShares(100);
        app1.setAmount(10000);
        app1.setDocument("QT-22023-101");
        app1.setApproved(true);
        app1.setRejected(false);
        app1.setCustomer(customer);
        applications.add(app1);
        
        // Create approved application 2
        IpoApplication app2 = new IpoApplication();
        app2.setCompany("SpaceY");
        app2.setNumberOfShares(30);
        app2.setAmount(15000);
        app2.setDocument("QT-2023-102");
        app2.setApproved(true);
        app2.setRejected(false);
        app2.setCustomer(customer);
        applications.add(app2);
        
        // Create rejected application
        IpoApplication app3 = new IpoApplication();
        app3.setCompany("BioGen");
        app3.setNumberOfShares(20);
        app3.setAmount(1000);
        app3.setDocument("QT-2024-002");
        app3.setApproved(false);
        app3.setRejected(true);
        app3.setCustomer(customer);
        applications.add(app3);
        
        customer.setApplications(applications);
        
        // Execute: Get total application count
        int result = customer.getTotalApplicationCount();
        
        // Verify: Should return 3 (all processed applications count)
        assertEquals("Should count all processed applications", 3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Customer "C104" with 1 approved, 2 rejected, and 2 pending applications
        customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setEligible(true);
        
        List<IpoApplication> applications = new ArrayList<>();
        
        // Create approved application
        IpoApplication app1 = new IpoApplication();
        app1.setCompany("RoboCorp");
        app1.setNumberOfShares(100);
        app1.setAmount(10000);
        app1.setDocument("QT-2023-105");
        app1.setApproved(true);
        app1.setRejected(false);
        app1.setCustomer(customer);
        applications.add(app1);
        
        // Create rejected application 1
        IpoApplication app2 = new IpoApplication();
        app2.setCompany("AI Ventures");
        app2.setNumberOfShares(100);
        app2.setAmount(10000);
        app2.setDocument("QT-2023-106");
        app2.setApproved(false);
        app2.setRejected(true);
        app2.setCustomer(customer);
        applications.add(app2);
        
        // Create rejected application 2
        IpoApplication app3 = new IpoApplication();
        app3.setCompany("NanoMed");
        app3.setNumberOfShares(100);
        app3.setAmount(10000);
        app3.setDocument("QT-2024-003");
        app3.setApproved(false);
        app3.setRejected(true);
        app3.setCustomer(customer);
        applications.add(app3);
        
        // Create pending application 1
        IpoApplication app4 = new IpoApplication();
        app4.setCompany("GreenEnergy");
        app4.setNumberOfShares(100);
        app4.setAmount(10000);
        app4.setDocument("QT-2024-004");
        app4.setApproved(false);
        app4.setRejected(false);
        app4.setCustomer(customer);
        applications.add(app4);
        
        // Create pending application 2
        IpoApplication app5 = new IpoApplication();
        app5.setCompany("CloudScale");
        app5.setNumberOfShares(100);
        app5.setAmount(10000);
        app5.setDocument("QT-2024-005");
        app5.setApproved(false);
        app5.setRejected(false);
        app5.setCustomer(customer);
        applications.add(app5);
        
        customer.setApplications(applications);
        
        // Execute: Get total application count
        int result = customer.getTotalApplicationCount();
        
        // Verify: Should return 3 (only processed applications count)
        assertEquals("Should count only processed applications", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" with canceled application
        customer = new Customer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setEligible(true);
        
        // Create and add a pending application
        IpoApplication application = new IpoApplication();
        application.setCompany("Cloud");
        application.setNumberOfShares(10);
        application.setAmount(5000);
        application.setDocument("QT-1010");
        application.setApproved(false);
        application.setRejected(false);
        application.setCustomer(customer);
        
        List<IpoApplication> applications = new ArrayList<>();
        applications.add(application);
        customer.setApplications(applications);
        
        // Execute: Get total application count
        int result = customer.getTotalApplicationCount();
        
        // Verify: Should return 0 (pending applications don't count)
        assertEquals("Canceled/pending applications should not be counted", 0, result);
    }
}