import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private SoftwareSystem softwareSystem;
    
    @Before
    public void setUp() {
        softwareSystem = new SoftwareSystem();
    }
    
    @Test
    public void testCase1_noApprovedRequests() {
        // Setup: Customer "C201" with pending and rejected applications, no approved ones
        Customer customer = new Customer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setEligibleForIPO(true);
        
        // Create pending application
        IPOApplication pendingApp = new IPOApplication();
        pendingApp.setCompany("TechInc");
        pendingApp.setNumberOfShares(10);
        pendingApp.setAmount(1500.0);
        Document doc1 = new Document();
        doc1.setContent("QT-3001");
        pendingApp.setDocument(doc1);
        pendingApp.setCustomer(customer);
        pendingApp.setApproved(false);
        pendingApp.setRejected(false);
        
        // Create rejected application
        IPOApplication rejectedApp = new IPOApplication();
        rejectedApp.setCompany("BioMed");
        rejectedApp.setNumberOfShares(10);
        rejectedApp.setAmount(2000.0);
        Document doc2 = new Document();
        doc2.setContent("QT-3002");
        rejectedApp.setDocument(doc2);
        rejectedApp.setCustomer(customer);
        rejectedApp.setApproved(false);
        rejectedApp.setRejected(true);
        
        customer.getApplications().add(pendingApp);
        customer.getApplications().add(rejectedApp);
        
        // Test: Query total approved amount
        double result = softwareSystem.getTotalApprovedAmount(customer);
        
        // Verify: Should be 0 since no approved applications
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
        customer.setEligibleForIPO(true);
        
        // Create approved application
        IPOApplication approvedApp = new IPOApplication();
        approvedApp.setCompany("SolarMax");
        approvedApp.setNumberOfShares(84);
        approvedApp.setAmount(4200.0);
        Document doc = new Document();
        doc.setContent("SM-2024-Q1");
        approvedApp.setDocument(doc);
        approvedApp.setCustomer(customer);
        approvedApp.setApproved(true);
        approvedApp.setRejected(false);
        
        customer.getApplications().add(approvedApp);
        
        // Test: Query total approved amount
        double result = softwareSystem.getTotalApprovedAmount(customer);
        
        // Verify: Should be 4200.0
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with two approved applications for different companies
        Customer customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setEligibleForIPO(true);
        
        // Create first approved application
        IPOApplication app1 = new IPOApplication();
        app1.setCompany("QuantumTech");
        app1.setNumberOfShares(40);
        app1.setAmount(2000.0);
        Document doc1 = new Document();
        doc1.setContent("SM-2024-Q3004");
        app1.setDocument(doc1);
        app1.setCustomer(customer);
        app1.setApproved(true);
        app1.setRejected(false);
        
        // Create second approved application
        IPOApplication app2 = new IPOApplication();
        app2.setCompany("Neuralink");
        app2.setNumberOfShares(70);
        app2.setAmount(3500.0);
        Document doc2 = new Document();
        doc2.setContent("SM-2024-Q3005");
        app2.setDocument(doc2);
        app2.setCustomer(customer);
        app2.setApproved(true);
        app2.setRejected(false);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        
        // Test: Query total approved amount
        double result = softwareSystem.getTotalApprovedAmount(customer);
        
        // Verify: Should be 5500.0 (2000 + 3500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_largePortfolio() {
        // Setup: Customer "C204" with five approved applications, each $10,000
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setEligibleForIPO(true);
        
        String[] companies = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        String[] docIds = {"SM-3006", "SM-3007", "SM-3008", "SM-3009", "SM-3010"};
        int[] shares = {200, 250, 125, 500, 200};
        
        for (int i = 0; i < companies.length; i++) {
            IPOApplication app = new IPOApplication();
            app.setCompany(companies[i]);
            app.setNumberOfShares(shares[i]);
            app.setAmount(10000.0);
            Document doc = new Document();
            doc.setContent(docIds[i]);
            app.setDocument(doc);
            app.setCustomer(customer);
            app.setApproved(true);
            app.setRejected(false);
            customer.getApplications().add(app);
        }
        
        // Test: Query total approved amount
        double result = softwareSystem.getTotalApprovedAmount(customer);
        
        // Verify: Should be 50000.0 (5 * 10000)
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
        customer.setEligibleForIPO(true);
        
        // Create approved applications
        IPOApplication app1 = new IPOApplication();
        app1.setCompany("CloudServ");
        app1.setNumberOfShares(100);
        app1.setAmount(3000.0);
        Document doc1 = new Document();
        doc1.setContent("SM-3011");
        app1.setDocument(doc1);
        app1.setCustomer(customer);
        app1.setApproved(true);
        app1.setRejected(false);
        
        IPOApplication app2 = new IPOApplication();
        app2.setCompany("DataCore");
        app2.setNumberOfShares(20);
        app2.setAmount(2750.0);
        Document doc2 = new Document();
        doc2.setContent("SM-3012");
        app2.setDocument(doc2);
        app2.setCustomer(customer);
        app2.setApproved(true);
        app2.setRejected(false);
        
        IPOApplication app3 = new IPOApplication();
        app3.setCompany("AI Ventures");
        app3.setNumberOfShares(30);
        app3.setAmount(3000.0);
        Document doc3 = new Document();
        doc3.setContent("SM-3013");
        app3.setDocument(doc3);
        app3.setCustomer(customer);
        app3.setApproved(true);
        app3.setRejected(false);
        
        // Create pending applications (should not be counted)
        IPOApplication app4 = new IPOApplication();
        app4.setCompany("NanoTech");
        app4.setNumberOfShares(10);
        app4.setAmount(600.0);
        Document doc4 = new Document();
        doc4.setContent("SM-3014");
        app4.setDocument(doc4);
        app4.setCustomer(customer);
        app4.setApproved(false);
        app4.setRejected(false);
        
        IPOApplication app5 = new IPOApplication();
        app5.setCompany("RoboWorks");
        app5.setNumberOfShares(50);
        app5.setAmount(600.0);
        Document doc5 = new Document();
        doc5.setContent("SM-3015");
        app5.setDocument(doc5);
        app5.setCustomer(customer);
        app5.setApproved(false);
        app5.setRejected(false);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Test: Query total approved amount
        double result = softwareSystem.getTotalApprovedAmount(customer);
        
        // Verify: Should be 8750.0 (3000 + 2750 + 3000)
        assertEquals(8750.0, result, 0.001);
    }
}