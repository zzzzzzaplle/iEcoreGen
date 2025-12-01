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
    public void testCase1_NoApplicationsAtAll() {
        // Setup: Customer "C101" with no IPO applications
        Customer customer = new Customer();
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        
        // Test: Retrieve application count summary
        int result = system.getApplicationCountSummary(customer);
        
        // Verify: Should return 0 when no applications exist
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
        
        IPOApplication application = new IPOApplication();
        application.setCompanyName("QuantumTech");
        application.setNumberOfShares(50);
        application.setAmountPaid(2500.0);
        application.setCustomer(customer);
        application.setStatus(ApplicationStatus.PENDING); // Pending status
        
        Document document = new Document();
        document.setFileName("QT-2024-FormA");
        application.setDocument(document);
        
        customer.getApplications().add(application);
        
        // Test: Retrieve application count summary
        int result = system.getApplicationCountSummary(customer);
        
        // Verify: Should return 1 (pending applications are NOT counted)
        assertEquals("Single pending application should return 0 (pending not counted)", 0, result);
    }
    
    @Test
    public void testCase3_MixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        Customer customer = new Customer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setRestricted(true); // Customer cannot apply for IPO
        
        // Approved application 1
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("Neuralink");
        app1.setNumberOfShares(100);
        app1.setAmountPaid(10000.0);
        app1.setCustomer(customer);
        app1.setStatus(ApplicationStatus.APPROVED);
        
        Document doc1 = new Document();
        doc1.setFileName("QT-22023-101");
        app1.setDocument(doc1);
        
        // Approved application 2
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("SpaceY");
        app2.setNumberOfShares(30);
        app2.setAmountPaid(15000.0);
        app2.setCustomer(customer);
        app2.setStatus(ApplicationStatus.APPROVED);
        
        Document doc2 = new Document();
        doc2.setFileName("QT-2023-102");
        app2.setDocument(doc2);
        
        // Rejected application
        IPOApplication app3 = new IPOApplication();
        app3.setCompanyName("BioGen");
        app3.setNumberOfShares(20);
        app3.setAmountPaid(1000.0);
        app3.setCustomer(customer);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        Document doc3 = new Document();
        doc3.setFileName("QT-2024-002");
        app3.setDocument(doc3);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        
        // Test: Retrieve application count summary
        int result = system.getApplicationCountSummary(customer);
        
        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals("Mix of 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_FiveHistoricalRequests() {
        // Setup: Customer "C104" with 1 approved, 2 rejected, and 2 pending applications
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        
        // Approved application
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("RoboCorp");
        app1.setNumberOfShares(100);
        app1.setAmountPaid(10000.0);
        app1.setCustomer(customer);
        app1.setStatus(ApplicationStatus.APPROVED);
        
        Document doc1 = new Document();
        doc1.setFileName("QT-2023-105");
        app1.setDocument(doc1);
        
        // Rejected application 1
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("AI Ventures");
        app2.setNumberOfShares(100);
        app2.setAmountPaid(10000.0);
        app2.setCustomer(customer);
        app2.setStatus(ApplicationStatus.REJECTED);
        
        Document doc2 = new Document();
        doc2.setFileName("QT-2023-106");
        app2.setDocument(doc2);
        
        // Rejected application 2
        IPOApplication app3 = new IPOApplication();
        app3.setCompanyName("NanoMed");
        app3.setNumberOfShares(100);
        app3.setAmountPaid(10000.0);
        app3.setCustomer(customer);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        Document doc3 = new Document();
        doc3.setFileName("QT-2024-003");
        app3.setDocument(doc3);
        
        // Pending application 1
        IPOApplication app4 = new IPOApplication();
        app4.setCompanyName("GreenEnergy");
        app4.setNumberOfShares(100);
        app4.setAmountPaid(10000.0);
        app4.setCustomer(customer);
        app4.setStatus(ApplicationStatus.PENDING);
        
        Document doc4 = new Document();
        doc4.setFileName("QT-2024-004");
        app4.setDocument(doc4);
        
        // Pending application 2
        IPOApplication app5 = new IPOApplication();
        app5.setCompanyName("CloudScale");
        app5.setNumberOfShares(100);
        app5.setAmountPaid(10000.0);
        app5.setCustomer(customer);
        app5.setStatus(ApplicationStatus.PENDING);
        
        Document doc5 = new Document();
        doc5.setFileName("QT-2024-005");
        app5.setDocument(doc5);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Test: Retrieve application count summary
        int result = system.getApplicationCountSummary(customer);
        
        // Verify: Should return 3 (1 approved + 2 rejected, excluding 2 pending)
        assertEquals("1 approved + 2 rejected + 2 pending should return 3", 3, result);
    }
    
    @Test
    public void testCase5_AllRequestsCanceled() {
        // Setup: Customer "C105" with a canceled application
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        
        // Create and cancel pending application
        Document document = new Document();
        document.setFileName("QT-1010");
        
        // Create application
        IPOApplication application = new IPOApplication();
        application.setCompanyName("Cloud");
        application.setNumberOfShares(10);
        application.setAmountPaid(5000.0);
        application.setCustomer(customer);
        application.setStatus(ApplicationStatus.PENDING);
        application.setDocument(document);
        
        // Add to customer's applications
        customer.getApplications().add(application);
        
        // Cancel the application (remove from list)
        boolean cancelResult = system.cancelPendingApplication(customer, "Cloud");
        assertTrue("Application should be canceled successfully", cancelResult);
        
        // Test: Retrieve application count summary after cancellation
        int result = system.getApplicationCountSummary(customer);
        
        // Verify: Should return 0 after cancellation
        assertEquals("After canceling all applications, should return 0", 0, result);
    }
    

}