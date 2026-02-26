import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private IPOApplicationSystem system;
    
    @Before
    public void setUp() {
        system = new IPOApplicationSystem();
    }
    
    @Test
    public void testCase1_noApprovedRequests() {
        // Setup: Customer "C201" with pending and rejected applications
        Customer customer = new Customer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        
        // Create pending application
        IPOApplication pendingApp = new IPOApplication();
        pendingApp.setCompanyName("TechInc");
        pendingApp.setNumberOfShares(10);
        pendingApp.setAmountPaid(1500.0);
        pendingApp.setStatus(ApplicationStatus.PENDING);
        
        Document doc1 = new Document();
        doc1.setFileName("QT-3001");
        pendingApp.setDocument(doc1);
        pendingApp.setCustomer(customer);
        
        // Create rejected application
        IPOApplication rejectedApp = new IPOApplication();
        rejectedApp.setCompanyName("BioMed");
        rejectedApp.setNumberOfShares(10);
        rejectedApp.setAmountPaid(2000.0);
        rejectedApp.setStatus(ApplicationStatus.REJECTED);
        
        Document doc2 = new Document();
        doc2.setFileName("QT-3002");
        rejectedApp.setDocument(doc2);
        rejectedApp.setCustomer(customer);
        
        // Add applications to customer
        customer.getApplications().add(pendingApp);
        customer.getApplications().add(rejectedApp);
        
        // Test: Query total approved amount
        double result = system.getTotalApprovedAmount(customer);
        
        // Verify: Should return 0 since no approved applications
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_singleApproval() {
        // Setup: Customer "C202" with one approved application
        Customer customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        
        // Create approved application
        IPOApplication approvedApp = new IPOApplication();
        approvedApp.setCompanyName("SolarMax");
        approvedApp.setNumberOfShares(84);
        approvedApp.setAmountPaid(4200.0);
        approvedApp.setStatus(ApplicationStatus.APPROVED);
        
        Document document = new Document();
        document.setFileName("SM-2024-Q1");
        approvedApp.setDocument(document);
        approvedApp.setCustomer(customer);
        
        customer.getApplications().add(approvedApp);
        
        // Test: Query total approved amount
        double result = system.getTotalApprovedAmount(customer);
        
        // Verify: Should return 4200.0 for single approved application
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with multiple approved applications from different companies
        Customer customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        
        // Create first approved application
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("QuantumTech");
        app1.setNumberOfShares(40);
        app1.setAmountPaid(2000.0);
        app1.setStatus(ApplicationStatus.APPROVED);
        
        Document doc1 = new Document();
        doc1.setFileName("SM-2024-Q3004");
        app1.setDocument(doc1);
        app1.setCustomer(customer);
        
        // Create second approved application
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("Neuralink");
        app2.setNumberOfShares(70);
        app2.setAmountPaid(3500.0);
        app2.setStatus(ApplicationStatus.APPROVED);
        
        Document doc2 = new Document();
        doc2.setFileName("SM-2024-Q3005");
        app2.setDocument(doc2);
        app2.setCustomer(customer);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        
        // Test: Query total approved amount
        double result = system.getTotalApprovedAmount(customer);
        
        // Verify: Should return 5500.0 (2000 + 3500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_largePortfolio() {
        // Setup: Customer "C204" with multiple approved applications, each $10,000
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        
        // Create 5 approved applications, each with $10,000 amount
        String[] companies = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        int[] shares = {200, 250, 125, 500, 200};
        String[] documents = {"SM-3006", "SM-3007", "SM-3008", "SM-3009", "SM-3010"};
        
        for (int i = 0; i < 5; i++) {
            IPOApplication app = new IPOApplication();
            app.setCompanyName(companies[i]);
            app.setNumberOfShares(shares[i]);
            app.setAmountPaid(10000.0);
            app.setStatus(ApplicationStatus.APPROVED);
            
            Document doc = new Document();
            doc.setFileName(documents[i]);
            app.setDocument(doc);
            app.setCustomer(customer);
            
            customer.getApplications().add(app);
        }
        
        // Test: Query total approved amount
        double result = system.getTotalApprovedAmount(customer);
        
        // Verify: Should return 50000.0 (5 * 10000)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup: Customer "C205" with approved and pending applications
        Customer customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        
        // Create approved applications
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("CloudServ");
        app1.setNumberOfShares(100);
        app1.setAmountPaid(3000.0);
        app1.setStatus(ApplicationStatus.APPROVED);
        
        Document doc1 = new Document();
        doc1.setFileName("SM-3011");
        app1.setDocument(doc1);
        app1.setCustomer(customer);
        
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("DataCore");
        app2.setNumberOfShares(20);
        app2.setAmountPaid(2750.0);
        app2.setStatus(ApplicationStatus.APPROVED);
        
        Document doc2 = new Document();
        doc2.setFileName("SM-3012");
        app2.setDocument(doc2);
        app2.setCustomer(customer);
        
        IPOApplication app3 = new IPOApplication();
        app3.setCompanyName("AI Ventures");
        app3.setNumberOfShares(30);
        app3.setAmountPaid(3000.0);
        app3.setStatus(ApplicationStatus.APPROVED);
        
        Document doc3 = new Document();
        doc3.setFileName("SM-3013");
        app3.setDocument(doc3);
        app3.setCustomer(customer);
        
        // Create pending applications (should not be counted)
        IPOApplication pending1 = new IPOApplication();
        pending1.setCompanyName("NanoTech");
        pending1.setNumberOfShares(10);
        pending1.setAmountPaid(600.0);
        pending1.setStatus(ApplicationStatus.PENDING);
        
        Document doc4 = new Document();
        doc4.setFileName("SM-3014");
        pending1.setDocument(doc4);
        pending1.setCustomer(customer);
        
        IPOApplication pending2 = new IPOApplication();
        pending2.setCompanyName("RoboWorks");
        pending2.setNumberOfShares(50);
        pending2.setAmountPaid(600.0);
        pending2.setStatus(ApplicationStatus.PENDING);
        
        Document doc5 = new Document();
        doc5.setFileName("SM-3015");
        pending2.setDocument(doc5);
        pending2.setCustomer(customer);
        
        // Add all applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(pending1);
        customer.getApplications().add(pending2);
        
        // Test: Query total approved amount
        double result = system.getTotalApprovedAmount(customer);
        
        // Verify: Should return 8750.0 (3000 + 2750 + 3000) - pending applications ignored
        assertEquals(8750.0, result, 0.001);
    }
}