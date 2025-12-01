import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private SoftwareSystem softwareSystem;
    
    @Before
    public void setUp() {
        softwareSystem = new SoftwareSystem();
    }
    
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setEligibleForIPO(true);
        
        // Execute
        int result = softwareSystem.getCustomerApplicationCount(customer);
        
        // Verify
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_singlePendingRequest() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setEligibleForIPO(true);
        
        IPOApplication application = new IPOApplication();
        application.setCompany("QuantumTech");
        application.setNumberOfShares(50);
        application.setAmount(2500.0);
        
        Document document = new Document();
        document.setContent("QT-2024-FormA");
        application.setDocument(document);
        application.setCustomer(customer);
        
        // Add application to customer (pending status by default)
        customer.getApplications().add(application);
        
        // Execute
        int result = softwareSystem.getCustomerApplicationCount(customer);
        
        // Verify
        assertEquals("Single pending application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup
        Customer customer = new Customer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setEligibleForIPO(false);
        
        // Create and add approved applications
        IPOApplication app1 = new IPOApplication();
        app1.setCompany("Neuralink");
        app1.setNumberOfShares(100);
        app1.setAmount(10000.0);
        app1.setApproved(true);
        
        Document doc1 = new Document();
        doc1.setContent("QT-22023-101");
        app1.setDocument(doc1);
        app1.setCustomer(customer);
        
        IPOApplication app2 = new IPOApplication();
        app2.setCompany("SpaceY");
        app2.setNumberOfShares(30);
        app2.setAmount(15000.0);
        app2.setApproved(true);
        
        Document doc2 = new Document();
        doc2.setContent("QT-2023-102");
        app2.setDocument(doc2);
        app2.setCustomer(customer);
        
        // Create and add rejected application
        IPOApplication app3 = new IPOApplication();
        app3.setCompany("BioGen");
        app3.setNumberOfShares(20);
        app3.setAmount(1000.0);
        app3.setRejected(true);
        
        Document doc3 = new Document();
        doc3.setContent("QT-2024-002");
        app3.setDocument(doc3);
        app3.setCustomer(customer);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        
        // Execute
        int result = softwareSystem.getCustomerApplicationCount(customer);
        
        // Verify
        assertEquals("Mix of approved and rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setEligibleForIPO(true);
        
        // Create approved application
        IPOApplication approvedApp = new IPOApplication();
        approvedApp.setCompany("RoboCorp");
        approvedApp.setNumberOfShares(100);
        approvedApp.setAmount(10000.0);
        approvedApp.setApproved(true);
        
        Document doc1 = new Document();
        doc1.setContent("QT-2023-105");
        approvedApp.setDocument(doc1);
        approvedApp.setCustomer(customer);
        
        // Create rejected applications
        IPOApplication rejectedApp1 = new IPOApplication();
        rejectedApp1.setCompany("AI Ventures");
        rejectedApp1.setNumberOfShares(100);
        rejectedApp1.setAmount(10000.0);
        rejectedApp1.setRejected(true);
        
        Document doc2 = new Document();
        doc2.setContent("QT-2023-106");
        rejectedApp1.setDocument(doc2);
        rejectedApp1.setCustomer(customer);
        
        IPOApplication rejectedApp2 = new IPOApplication();
        rejectedApp2.setCompany("NanoMed");
        rejectedApp2.setNumberOfShares(100);
        rejectedApp2.setAmount(10000.0);
        rejectedApp2.setRejected(true);
        
        Document doc3 = new Document();
        doc3.setContent("QT-2024-003");
        rejectedApp2.setDocument(doc3);
        rejectedApp2.setCustomer(customer);
        
        // Create pending applications (not approved or rejected)
        IPOApplication pendingApp1 = new IPOApplication();
        pendingApp1.setCompany("GreenEnergy");
        pendingApp1.setNumberOfShares(100);
        pendingApp1.setAmount(10000.0);
        // Default status (not approved, not rejected)
        
        Document doc4 = new Document();
        doc4.setContent("QT-2024-004");
        pendingApp1.setDocument(doc4);
        pendingApp1.setCustomer(customer);
        
        IPOApplication pendingApp2 = new IPOApplication();
        pendingApp2.setCompany("CloudScale");
        pendingApp2.setNumberOfShares(100);
        pendingApp2.setAmount(10000.0);
        // Default status (not approved, not rejected)
        
        Document doc5 = new Document();
        doc5.setContent("QT-2024-005");
        pendingApp2.setDocument(doc5);
        pendingApp2.setCustomer(customer);
        
        // Add all applications to customer
        customer.getApplications().add(approvedApp);
        customer.getApplications().add(rejectedApp1);
        customer.getApplications().add(rejectedApp2);
        customer.getApplications().add(pendingApp1);
        customer.getApplications().add(pendingApp2);
        
        // Execute
        int result = softwareSystem.getCustomerApplicationCount(customer);
        
        // Verify
        assertEquals("Five applications with 3 reviewed should return 3", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setEligibleForIPO(true);
        
        // Create pending application
        IPOApplication application = new IPOApplication();
        application.setCompany("Cloud");
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        
        Document document = new Document();
        document.setContent("QT-1010");
        application.setDocument(document);
        application.setCustomer(customer);
        
        // Add application to customer
        customer.getApplications().add(application);
        
        // Cancel the application
        softwareSystem.cancelPendingApplication(customer, "Cloud");
        
        // Execute
        int result = softwareSystem.getCustomerApplicationCount(customer);
        
        // Verify
        assertEquals("Canceled application should return 0", 0, result);
    }
}