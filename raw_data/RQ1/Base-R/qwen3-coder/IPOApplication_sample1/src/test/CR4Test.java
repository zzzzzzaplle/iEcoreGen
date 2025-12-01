import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private BankSystem bankSystem;
    
    @Before
    public void setUp() {
        bankSystem = new BankSystem();
    }
    
    @Test
    public void testCase1_NoApprovedRequests() {
        // Setup Customer C201
        Customer customer = new Customer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setEligibleForIPO(true);
        
        // Setup PENDING application APP-3001
        IPOApplication pendingApp = new IPOApplication();
        pendingApp.setCustomer(customer);
        pendingApp.setCompany("TechInc");
        pendingApp.setNumberOfShares(10);
        pendingApp.setAmount(1500.0);
        Document doc1 = new Document();
        doc1.setContent("QT-3001");
        pendingApp.setDocument(doc1);
        pendingApp.setApproved(false);
        pendingApp.setRejected(false);
        
        // Setup REJECTED application APP-3002
        IPOApplication rejectedApp = new IPOApplication();
        rejectedApp.setCustomer(customer);
        rejectedApp.setCompany("BioMed");
        rejectedApp.setNumberOfShares(10);
        rejectedApp.setAmount(2000.0);
        Document doc2 = new Document();
        doc2.setContent("QT-3002");
        rejectedApp.setDocument(doc2);
        rejectedApp.setApproved(false);
        rejectedApp.setRejected(true);
        
        // Add applications to customer
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(pendingApp);
        applications.add(rejectedApp);
        customer.setApplications(applications);
        
        // Test total approved amount
        double result = bankSystem.getTotalApprovedIPOAmount(customer);
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SingleApproval() {
        // Setup Customer C202
        Customer customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        
        // Setup approved application APP-3003
        IPOApplication approvedApp = new IPOApplication();
        approvedApp.setCustomer(customer);
        approvedApp.setCompany("SolarMax");
        approvedApp.setNumberOfShares(84);
        approvedApp.setAmount(4200.0);
        Document doc = new Document();
        doc.setContent("SM-2024-Q1");
        approvedApp.setDocument(doc);
        approvedApp.setApproved(true);
        approvedApp.setRejected(false);
        
        // Add application to customer
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(approvedApp);
        customer.setApplications(applications);
        
        // Test total approved amount
        double result = bankSystem.getTotalApprovedIPOAmount(customer);
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup Customer C203
        Customer customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        
        // Setup approved application APP-3004
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompany("QuantumTech");
        app1.setNumberOfShares(40);
        app1.setAmount(2000.0);
        Document doc1 = new Document();
        doc1.setContent("SM-2024-Q3004");
        app1.setDocument(doc1);
        app1.setApproved(true);
        app1.setRejected(false);
        
        // Setup approved application APP-3005
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompany("Neuralink");
        app2.setNumberOfShares(70);
        app2.setAmount(3500.0);
        Document doc2 = new Document();
        doc2.setContent("SM-2024-Q3005");
        app2.setDocument(doc2);
        app2.setApproved(true);
        app2.setRejected(false);
        
        // Add applications to customer
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        customer.setApplications(applications);
        
        // Test total approved amount
        double result = bankSystem.getTotalApprovedIPOAmount(customer);
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup Customer C204
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        
        // Create 5 approved applications, each with $10,000 amount
        List<IPOApplication> applications = new ArrayList<>();
        String[] companies = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        String[] docIds = {"SM-3006", "SM-3007", "SM-3008", "SM-3009", "SM-3010"};
        int[] shares = {200, 250, 125, 500, 200};
        
        for (int i = 0; i < 5; i++) {
            IPOApplication app = new IPOApplication();
            app.setCustomer(customer);
            app.setCompany(companies[i]);
            app.setNumberOfShares(shares[i]);
            app.setAmount(10000.0);
            Document doc = new Document();
            doc.setContent(docIds[i]);
            app.setDocument(doc);
            app.setApproved(true);
            app.setRejected(false);
            applications.add(app);
        }
        
        customer.setApplications(applications);
        
        // Test total approved amount
        double result = bankSystem.getTotalApprovedIPOAmount(customer);
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup Customer C205
        Customer customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // Setup approved applications
        // APP-3011: CloudServ
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompany("CloudServ");
        app1.setNumberOfShares(100);
        app1.setAmount(3000.0);
        Document doc1 = new Document();
        doc1.setContent("SM-3011");
        app1.setDocument(doc1);
        app1.setApproved(true);
        app1.setRejected(false);
        applications.add(app1);
        
        // APP-3012: DataCore
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompany("DataCore");
        app2.setNumberOfShares(20);
        app2.setAmount(2750.0);
        Document doc2 = new Document();
        doc2.setContent("SM-3012");
        app2.setDocument(doc2);
        app2.setApproved(true);
        app2.setRejected(false);
        applications.add(app2);
        
        // APP-3013: AI Ventures
        IPOApplication app3 = new IPOApplication();
        app3.setCustomer(customer);
        app3.setCompany("AI Ventures");
        app3.setNumberOfShares(30);
        app3.setAmount(3000.0);
        Document doc3 = new Document();
        doc3.setContent("SM-3013");
        app3.setDocument(doc3);
        app3.setApproved(true);
        app3.setRejected(false);
        applications.add(app3);
        
        // Setup pending applications (should not be counted in total approved amount)
        // APP-3014: NanoTech
        IPOApplication pendingApp1 = new IPOApplication();
        pendingApp1.setCustomer(customer);
        pendingApp1.setCompany("NanoTech");
        pendingApp1.setNumberOfShares(10);
        pendingApp1.setAmount(600.0);
        Document doc4 = new Document();
        doc4.setContent("SM-3014");
        pendingApp1.setDocument(doc4);
        pendingApp1.setApproved(false);
        pendingApp1.setRejected(false);
        applications.add(pendingApp1);
        
        // APP-3015: RoboWorks
        IPOApplication pendingApp2 = new IPOApplication();
        pendingApp2.setCustomer(customer);
        pendingApp2.setCompany("RoboWorks");
        pendingApp2.setNumberOfShares(50);
        pendingApp2.setAmount(600.0);
        Document doc5 = new Document();
        doc5.setContent("SM-3015");
        pendingApp2.setDocument(doc5);
        pendingApp2.setApproved(false);
        pendingApp2.setRejected(false);
        applications.add(pendingApp2);
        
        customer.setApplications(applications);
        
        // Test total approved amount (only approved applications should be counted)
        double result = bankSystem.getTotalApprovedIPOAmount(customer);
        assertEquals(8750.0, result, 0.001);
    }
}