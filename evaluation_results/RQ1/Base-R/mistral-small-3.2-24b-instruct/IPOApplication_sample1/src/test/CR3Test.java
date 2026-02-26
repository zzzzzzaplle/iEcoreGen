import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Customer customer;
    private IPOApplication application;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Setup: Customer "C101" with no IPO applications
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        
        // Execute: Call getApplicationCountSummary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 0 when no applications exist
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_singlePendingRequest() {
        // Setup: Customer "C102" with one pending application
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        
        // Create and add pending application
        application = new IPOApplication();
        application.setCompanyName("QuantumTech");
        application.setShares(50);
        application.setAmount(2500);
        application.setDocument("QT-2024-FormA");
        application.setStatus(IPOApplication.Status.PENDING);
        application.setCustomer(customer);
        
        customer.getApplications().add(application);
        
        // Execute: Call getApplicationCountSummary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 1 (pending applications are not counted)
        assertEquals("Customer with one pending application should return 0", 0, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer "C103" with approved and rejected applications
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setRestricted(true); // Cannot apply for IPO
        
        // Create approved applications
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("Neuralink");
        app1.setShares(100);
        app1.setAmount(10000);
        app1.setDocument("QT-22023-101");
        app1.setStatus(IPOApplication.Status.APPROVED);
        app1.setCustomer(customer);
        
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("SpaceY");
        app2.setShares(30);
        app2.setAmount(15000);
        app2.setDocument("QT-2023-102");
        app2.setStatus(IPOApplication.Status.APPROVED);
        app2.setCustomer(customer);
        
        // Create rejected application
        IPOApplication app3 = new IPOApplication();
        app3.setCompanyName("BioGen");
        app3.setShares(20);
        app3.setAmount(1000);
        app3.setDocument("QT-2024-002");
        app3.setStatus(IPOApplication.Status.REJECTED);
        app3.setCustomer(customer);
        
        // Add all applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        
        // Execute: Call getApplicationCountSummary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals("Customer with 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Customer "C104" with mixed status applications
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        
        // Create approved application
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("RoboCorp");
        app1.setShares(100);
        app1.setAmount(10000);
        app1.setDocument("QT-2023-105");
        app1.setStatus(IPOApplication.Status.APPROVED);
        app1.setCustomer(customer);
        
        // Create rejected applications
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("AI Ventures");
        app2.setShares(100);
        app2.setAmount(10000);
        app2.setDocument("QT-2023-106");
        app2.setStatus(IPOApplication.Status.REJECTED);
        app2.setCustomer(customer);
        
        IPOApplication app3 = new IPOApplication();
        app3.setCompanyName("NanoMed");
        app3.setShares(100);
        app3.setAmount(10000);
        app3.setDocument("QT-2024-003");
        app3.setStatus(IPOApplication.Status.REJECTED);
        app3.setCustomer(customer);
        
        // Create pending applications (should not be counted)
        IPOApplication app4 = new IPOApplication();
        app4.setCompanyName("GreenEnergy");
        app4.setShares(100);
        app4.setAmount(10000);
        app4.setDocument("QT-2024-004");
        app4.setStatus(IPOApplication.Status.PENDING);
        app4.setCustomer(customer);
        
        IPOApplication app5 = new IPOApplication();
        app5.setCompanyName("CloudScale");
        app5.setShares(100);
        app5.setAmount(10000);
        app5.setDocument("QT-2024-005");
        app5.setStatus(IPOApplication.Status.PENDING);
        app5.setCustomer(customer);
        
        // Add all applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Execute: Call getApplicationCountSummary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 3 (1 approved + 2 rejected, pending not counted)
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" with canceled application
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        
        // Create pending application
        application = new IPOApplication();
        application.setCompanyName("Cloud");
        application.setShares(10);
        application.setAmount(5000);
        application.setDocument("QT-1010");
        application.setStatus(IPOApplication.Status.PENDING);
        application.setCustomer(customer);
        
        customer.getApplications().add(application);
        
        // Cancel the application (changes status to REJECTED)
        application.cancel();
        
        // Execute: Call getApplicationCountSummary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 0 (canceled applications are rejected and not counted)
        assertEquals("Customer with canceled application should return 0", 0, result);
    }
}