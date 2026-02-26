import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Test Case 1: "No applications at all"
        // Setup: Customer "C101" with no IPO applications
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setEligibleForIPO(true);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 0 when no applications exist
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_singlePendingRequest() {
        // Test Case 2: "Single pending request"
        // Setup: Customer "C102" with one pending application
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setEligibleForIPO(true);
        
        // Create and add a pending application
        IPOApplication application = new IPOApplication();
        application.setCompanyName("QuantumTech");
        application.setNumberOfShares(50);
        application.setAmount(2500.0);
        
        Document document = new Document();
        document.setDocumentId("QT-2024-FormA");
        application.setDocument(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(application);
        customer.setApplications(applications);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 0 because pending applications are not counted
        assertEquals("Pending application should not be counted", 0, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Test Case 3: "Mix of approved and rejected"
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setEligibleForIPO(false); // Customer cannot apply for IPO
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // Create approved application 1
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("Neuralink");
        app1.setNumberOfShares(100);
        app1.setAmount(10000.0);
        Document doc1 = new Document();
        doc1.setDocumentId("QT-22023-101");
        app1.setDocument(doc1);
        app1.setStatus(ApplicationStatus.APPROVED);
        applications.add(app1);
        
        // Create approved application 2
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("SpaceY");
        app2.setNumberOfShares(30);
        app2.setAmount(15000.0);
        Document doc2 = new Document();
        doc2.setDocumentId("QT-2023-102");
        app2.setDocument(doc2);
        app2.setStatus(ApplicationStatus.APPROVED);
        applications.add(app2);
        
        // Create rejected application
        IPOApplication app3 = new IPOApplication();
        app3.setCompanyName("BioGen");
        app3.setNumberOfShares(20);
        app3.setAmount(1000.0);
        Document doc3 = new Document();
        doc3.setDocumentId("QT-2024-002");
        app3.setDocument(doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        applications.add(app3);
        
        customer.setApplications(applications);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals("Should count 2 approved and 1 rejected applications", 3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Test Case 4: "Five historical requests"
        // Setup: Customer "C104" with 1 approved, 2 rejected, and 2 pending applications
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setEligibleForIPO(true);
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // Create approved application
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("RoboCorp");
        app1.setNumberOfShares(100);
        app1.setAmount(10000.0);
        Document doc1 = new Document();
        doc1.setDocumentId("QT-2023-105");
        app1.setDocument(doc1);
        app1.setStatus(ApplicationStatus.APPROVED);
        applications.add(app1);
        
        // Create rejected application 1
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("AI Ventures");
        app2.setNumberOfShares(100);
        app2.setAmount(10000.0);
        Document doc2 = new Document();
        doc2.setDocumentId("QT-2023-106");
        app2.setDocument(doc2);
        app2.setStatus(ApplicationStatus.REJECTED);
        applications.add(app2);
        
        // Create rejected application 2
        IPOApplication app3 = new IPOApplication();
        app3.setCompanyName("NanoMed");
        app3.setNumberOfShares(100);
        app3.setAmount(10000.0);
        Document doc3 = new Document();
        doc3.setDocumentId("QT-2024-003");
        app3.setDocument(doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        applications.add(app3);
        
        // Create pending application 1 (should not be counted)
        IPOApplication app4 = new IPOApplication();
        app4.setCompanyName("GreenEnergy");
        app4.setNumberOfShares(100);
        app4.setAmount(10000.0);
        Document doc4 = new Document();
        doc4.setDocumentId("QT-2024-004");
        app4.setDocument(doc4);
        app4.setStatus(ApplicationStatus.PENDING);
        applications.add(app4);
        
        // Create pending application 2 (should not be counted)
        IPOApplication app5 = new IPOApplication();
        app5.setCompanyName("CloudScale");
        app5.setNumberOfShares(100);
        app5.setAmount(10000.0);
        Document doc5 = new Document();
        doc5.setDocumentId("QT-2024-005");
        app5.setDocument(doc5);
        app5.setStatus(ApplicationStatus.PENDING);
        applications.add(app5);
        
        customer.setApplications(applications);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 3 (1 approved + 2 rejected, pending applications excluded)
        assertEquals("Should count only approved and rejected applications", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Test Case 5: "All requests canceled"
        // Setup: Customer "C105" with a canceled application
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setEligibleForIPO(true);
        
        // Create a pending application
        IPOApplication application = new IPOApplication();
        application.setCompanyName("Cloud");
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        Document document = new Document();
        document.setDocumentId("QT-1010");
        application.setDocument(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(application);
        customer.setApplications(applications);
        
        // Cancel the application
        customer.cancelPendingApplication("Cloud");
        
        // Execute: Get application count summary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 0 because canceled applications are not counted
        assertEquals("Canceled application should not be counted", 0, result);
    }
}