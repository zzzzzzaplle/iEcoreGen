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
    public void testCase1_NoApplicationsAtAll() {
        // Setup: Customer "C101" with no IPO requests
        Customer customer = new Customer();
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setEligibleForIPO(true);
        
        // Execute: Retrieve application count
        int result = bankSystem.getCustomerApplicationCount(customer);
        
        // Verify: Expected output is 0
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_SinglePendingRequest() {
        // Setup: Customer "C102" with one pending application
        Customer customer = new Customer();
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setEligibleForIPO(true);
        
        // Create pending application
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany("QuantumTech");
        application.setNumberOfShares(50);
        application.setAmount(2500.0);
        
        Document document = new Document();
        document.setContent("QT-2024-FormA");
        application.setDocument(document);
        
        // Add to customer's applications (but not approved/rejected, so it's pending)
        customer.getApplications().add(application);
        
        // Execute: Retrieve application count
        int result = bankSystem.getCustomerApplicationCount(customer);
        
        // Verify: Pending applications should not be counted, expected output is 0
        assertEquals("Pending applications should not be counted - expected 0", 0, result);
    }
    
    @Test
    public void testCase3_MixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        Customer customer = new Customer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setEligibleForIPO(false); // As specified: can not apply for IPO
        
        // Create and add approved application 1
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompany("Neuralink");
        app1.setNumberOfShares(100);
        app1.setAmount(10000.0);
        
        Document doc1 = new Document();
        doc1.setContent("QT-22023-101");
        app1.setDocument(doc1);
        app1.setApproved(true); // Mark as approved
        
        // Create and add approved application 2
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompany("SpaceY");
        app2.setNumberOfShares(30);
        app2.setAmount(15000.0);
        
        Document doc2 = new Document();
        doc2.setContent("QT-2023-102");
        app2.setDocument(doc2);
        app2.setApproved(true); // Mark as approved
        
        // Create and add rejected application
        IPOApplication app3 = new IPOApplication();
        app3.setCustomer(customer);
        app3.setCompany("BioGen");
        app3.setNumberOfShares(20);
        app3.setAmount(1000.0);
        
        Document doc3 = new Document();
        doc3.setContent("QT-2024-002");
        app3.setDocument(doc3);
        app3.setRejected(true); // Mark as rejected
        
        // Add all applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        
        // Execute: Retrieve application count
        int result = bankSystem.getCustomerApplicationCount(customer);
        
        // Verify: Approved and rejected applications should be counted, expected output is 3
        assertEquals("Approved and rejected applications should be counted - expected 3", 3, result);
    }
    
    @Test
    public void testCase4_FiveHistoricalRequests() {
        // Setup: Customer "C104" with 1 approved, 2 rejected, and 2 pending applications
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setEligibleForIPO(true);
        
        // Create and add approved application
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompany("RoboCorp");
        app1.setNumberOfShares(100);
        app1.setAmount(10000.0);
        
        Document doc1 = new Document();
        doc1.setContent("QT-2023-105");
        app1.setDocument(doc1);
        app1.setApproved(true); // Mark as approved
        
        // Create and add rejected application 1
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompany("AI Ventures");
        app2.setNumberOfShares(100);
        app2.setAmount(10000.0);
        
        Document doc2 = new Document();
        doc2.setContent("QT-2023-106");
        app2.setDocument(doc2);
        app2.setRejected(true); // Mark as rejected
        
        // Create and add rejected application 2
        IPOApplication app3 = new IPOApplication();
        app3.setCustomer(customer);
        app3.setCompany("NanoMed");
        app3.setNumberOfShares(100);
        app3.setAmount(10000.0);
        
        Document doc3 = new Document();
        doc3.setContent("QT-2024-003");
        app3.setDocument(doc3);
        app3.setRejected(true); // Mark as rejected
        
        // Create and add pending application 1 (not approved or rejected)
        IPOApplication app4 = new IPOApplication();
        app4.setCustomer(customer);
        app4.setCompany("GreenEnergy");
        app4.setNumberOfShares(100);
        app4.setAmount(10000.0);
        
        Document doc4 = new Document();
        doc4.setContent("QT-2024-004");
        app4.setDocument(doc4);
        // Leave as pending (no approval or rejection)
        
        // Create and add pending application 2 (not approved or rejected)
        IPOApplication app5 = new IPOApplication();
        app5.setCustomer(customer);
        app5.setCompany("CloudScale");
        app5.setNumberOfShares(100);
        app5.setAmount(10000.0);
        
        Document doc5 = new Document();
        doc5.setContent("QT-2024-005");
        app5.setDocument(doc5);
        // Leave as pending (no approval or rejection)
        
        // Add all applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Execute: Retrieve application count
        int result = bankSystem.getCustomerApplicationCount(customer);
        
        // Verify: Only approved and rejected applications should be counted (3), pending should be excluded
        assertEquals("Only approved and rejected applications should be counted - expected 3", 3, result);
    }
    
    @Test
    public void testCase5_AllRequestsCanceled() {
        // Setup: Customer "C105" with a canceled application
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setEligibleForIPO(true);
        
        // Create and add a pending application
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany("Cloud");
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        
        Document document = new Document();
        document.setContent("QT-1010");
        application.setDocument(document);
        
        // Add to customer's applications
        customer.getApplications().add(application);
        bankSystem.getApplications().add(application);
        
        // Cancel the application (remove from both customer and bank system)
        boolean cancelResult = bankSystem.cancelPendingApplication(customer, "Cloud");
        assertTrue("Application cancellation should succeed", cancelResult);
        
        // Execute: Retrieve application count after cancellation
        int result = bankSystem.getCustomerApplicationCount(customer);
        
        // Verify: Canceled applications should not be counted, expected output is 0
        assertEquals("Canceled applications should not be counted - expected 0", 0, result);
    }
}