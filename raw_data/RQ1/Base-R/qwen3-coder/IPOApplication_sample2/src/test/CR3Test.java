import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private IPOApplicationSystem system;
    
    @Before
    public void setUp() {
        system = new IPOApplicationSystem();
    }
    
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Setup: Customer "C101" with no IPO applications
        Customer customer = new Customer();
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmailAddress("t.anderson@example.com");
        customer.setTelephoneNumber("555-0101");
        customer.setEligibleForIPO(true);
        
        // Execute: Get application count
        int result = system.getCustomerApplicationCount(customer);
        
        // Verify: Should return 0 since no applications exist
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
        
        // Create pending application
        IPOApplication app = new IPOApplication();
        app.setCompanyName("QuantumTech");
        app.setNumberOfShares(50);
        app.setAmountOfMoney(2500.0);
        app.setDocument("QT-2024-FormA");
        // Status remains pending (not reviewed)
        
        customer.getApplications().add(app);
        system.getApplications().add(app);
        
        // Execute: Get application count
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
        customer.setEligibleForIPO(false); // As specified in test case
        
        // Create approved applications
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("Neuralink");
        app1.setNumberOfShares(100);
        app1.setAmountOfMoney(10000.0);
        app1.setDocument("QT-22023-101");
        app1.setApproved(true);
        
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("SpaceY");
        app2.setNumberOfShares(30);
        app2.setAmountOfMoney(15000.0);
        app2.setDocument("QT-2023-102");
        app2.setApproved(true);
        
        // Create rejected application
        IPOApplication app3 = new IPOApplication();
        app3.setCompanyName("BioGen");
        app3.setNumberOfShares(20);
        app3.setAmountOfMoney(1000.0);
        app3.setDocument("QT-2024-002");
        app3.setRejected(true);
        
        // Add applications to customer and system
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        system.getApplications().add(app1);
        system.getApplications().add(app2);
        system.getApplications().add(app3);
        
        // Execute: Get application count
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
        
        // Create approved application
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("RoboCorp");
        app1.setNumberOfShares(100);
        app1.setAmountOfMoney(10000.0);
        app1.setDocument("QT-2023-105");
        app1.setApproved(true);
        
        // Create rejected applications
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("AI Ventures");
        app2.setNumberOfShares(100);
        app2.setAmountOfMoney(10000.0);
        app2.setDocument("QT-2023-106");
        app2.setRejected(true);
        
        IPOApplication app3 = new IPOApplication();
        app3.setCompanyName("NanoMed");
        app3.setNumberOfShares(100);
        app3.setAmountOfMoney(10000.0);
        app3.setDocument("QT-2024-003");
        app3.setRejected(true);
        
        // Create pending applications (not reviewed)
        IPOApplication app4 = new IPOApplication();
        app4.setCompanyName("GreenEnergy");
        app4.setNumberOfShares(100);
        app4.setAmountOfMoney(10000.0);
        app4.setDocument("QT-2024-004");
        // Status remains pending (not reviewed)
        
        IPOApplication app5 = new IPOApplication();
        app5.setCompanyName("CloudScale");
        app5.setNumberOfShares(100);
        app5.setAmountOfMoney(10000.0);
        app5.setDocument("QT-2024-005");
        // Status remains pending (not reviewed)
        
        // Add all applications to customer and system
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        system.getApplications().add(app1);
        system.getApplications().add(app2);
        system.getApplications().add(app3);
        system.getApplications().add(app4);
        system.getApplications().add(app5);
        
        // Execute: Get application count
        int result = system.getCustomerApplicationCount(customer);
        
        // Verify: Should return 3 (1 approved + 2 rejected, excluding 2 pending)
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" with one application that gets canceled
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmailAddress("j.chen@example.com");
        customer.setTelephoneNumber("555-0505");
        customer.setEligibleForIPO(true);
        
        // Create pending application
        IPOApplication app = new IPOApplication();
        app.setCompanyName("Cloud");
        app.setNumberOfShares(10);
        app.setAmountOfMoney(5000.0);
        app.setDocument("QT-1010");
        // Status remains pending initially
        
        customer.getApplications().add(app);
        system.getApplications().add(app);
        
        // Cancel the application (marks it as rejected)
        boolean cancellationResult = system.cancelPendingApplication(customer, "Cloud");
        assertTrue(cancellationResult);
        
        // Execute: Get application count
        int result = system.getCustomerApplicationCount(customer);
        
        // Verify: Should return 1 since canceled application is now rejected (reviewed)
        assertEquals(1, result);
    }
}