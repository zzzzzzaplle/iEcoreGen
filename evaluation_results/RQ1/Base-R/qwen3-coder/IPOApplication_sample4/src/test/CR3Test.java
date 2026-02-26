import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    private BankSystem bankSystem;
    
    @Before
    public void setUp() {
        bankSystem = new BankSystem();
    }
    
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Setup: Customer "C101" with no IPO requests
        Customer customer = new Customer();
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setEligibleForIPO(true);
        
        // Execute: Get customer application count
        int result = bankSystem.getCustomerApplicationCount(customer);
        
        // Verify: Should return 0 when no applications exist
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_singlePendingRequest() {
        // Setup: Customer "C102" with one pending application
        Customer customer = new Customer();
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setEligibleForIPO(true);
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany("QuantumTech");
        application.setNumberOfShares(50);
        application.setAmount(2500.0);
        application.setDocument("QT-2024-FormA");
        // Status remains pending (not reviewed)
        
        customer.getApplications().add(application);
        bankSystem.getApplications().add(application);
        
        // Execute: Get customer application count
        int result = bankSystem.getCustomerApplicationCount(customer);
        
        // Verify: Should return 0 since pending applications are not counted
        assertEquals("Pending application should not be counted", 0, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        Customer customer = new Customer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setEligibleForIPO(false);
        
        // Approved application 1
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompany("Neuralink");
        app1.setNumberOfShares(100);
        app1.setAmount(10000.0);
        app1.setDocument("QT-22023-101");
        app1.setApproved(true);
        
        // Approved application 2
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompany("SpaceY");
        app2.setNumberOfShares(30);
        app2.setAmount(15000.0);
        app2.setDocument("QT-2023-102");
        app2.setApproved(true);
        
        // Rejected application
        IPOApplication app3 = new IPOApplication();
        app3.setCustomer(customer);
        app3.setCompany("BioGen");
        app3.setNumberOfShares(20);
        app3.setAmount(1000.0);
        app3.setDocument("QT-2024-002");
        app3.setRejected(true);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        
        bankSystem.getApplications().add(app1);
        bankSystem.getApplications().add(app2);
        bankSystem.getApplications().add(app3);
        
        // Execute: Get customer application count
        int result = bankSystem.getCustomerApplicationCount(customer);
        
        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals("Should count 3 reviewed applications", 3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Customer "C104" with 1 approved, 2 rejected, and 2 pending applications
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setEligibleForIPO(true);
        
        // Approved application
        IPOApplication approvedApp = new IPOApplication();
        approvedApp.setCustomer(customer);
        approvedApp.setCompany("RoboCorp");
        approvedApp.setNumberOfShares(100);
        approvedApp.setAmount(10000.0);
        approvedApp.setDocument("QT-2023-105");
        approvedApp.setApproved(true);
        
        // Rejected application 1
        IPOApplication rejectedApp1 = new IPOApplication();
        rejectedApp1.setCustomer(customer);
        rejectedApp1.setCompany("AI Ventures");
        rejectedApp1.setNumberOfShares(100);
        rejectedApp1.setAmount(10000.0);
        rejectedApp1.setDocument("QT-2023-106");
        rejectedApp1.setRejected(true);
        
        // Rejected application 2
        IPOApplication rejectedApp2 = new IPOApplication();
        rejectedApp2.setCustomer(customer);
        rejectedApp2.setCompany("NanoMed");
        rejectedApp2.setNumberOfShares(100);
        rejectedApp2.setAmount(10000.0);
        rejectedApp2.setDocument("QT-2024-003");
        rejectedApp2.setRejected(true);
        
        // Pending application 1
        IPOApplication pendingApp1 = new IPOApplication();
        pendingApp1.setCustomer(customer);
        pendingApp1.setCompany("GreenEnergy");
        pendingApp1.setNumberOfShares(100);
        pendingApp1.setAmount(10000.0);
        pendingApp1.setDocument("QT-2024-004");
        // Status remains pending (not reviewed)
        
        // Pending application 2
        IPOApplication pendingApp2 = new IPOApplication();
        pendingApp2.setCustomer(customer);
        pendingApp2.setCompany("CloudScale");
        pendingApp2.setNumberOfShares(100);
        pendingApp2.setAmount(10000.0);
        pendingApp2.setDocument("QT-2024-005");
        // Status remains pending (not reviewed)
        
        // Add all applications to customer
        customer.getApplications().add(approvedApp);
        customer.getApplications().add(rejectedApp1);
        customer.getApplications().add(rejectedApp2);
        customer.getApplications().add(pendingApp1);
        customer.getApplications().add(pendingApp2);
        
        // Add all applications to bank system
        bankSystem.getApplications().add(approvedApp);
        bankSystem.getApplications().add(rejectedApp1);
        bankSystem.getApplications().add(rejectedApp2);
        bankSystem.getApplications().add(pendingApp1);
        bankSystem.getApplications().add(pendingApp2);
        
        // Execute: Get customer application count
        int result = bankSystem.getCustomerApplicationCount(customer);
        
        // Verify: Should return 3 (1 approved + 2 rejected, pending ones not counted)
        assertEquals("Should count only 3 reviewed applications", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" with one pending application that gets canceled
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setEligibleForIPO(true);
        
        // Create pending application
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany("Cloud");
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument("QT-1010");
        // Status remains pending (not reviewed)
        
        customer.getApplications().add(application);
        bankSystem.getApplications().add(application);
        
        // Cancel the application
        boolean cancelResult = bankSystem.cancelPendingApplication(customer, "Cloud");
        assertTrue("Cancellation should succeed", cancelResult);
        
        // Execute: Get customer application count
        int result = bankSystem.getCustomerApplicationCount(customer);
        
        // Verify: Should return 0 since canceled applications are marked as rejected but removed from system
        assertEquals("Canceled application should not be counted", 0, result);
    }
}