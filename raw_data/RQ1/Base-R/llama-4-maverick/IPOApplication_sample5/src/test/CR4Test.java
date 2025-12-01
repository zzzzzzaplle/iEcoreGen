import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private IPOService ipoService;
    
    @Before
    public void setUp() {
        ipoService = new IPOService();
    }
    
    @Test
    public void testCase1_noApprovedRequests() {
        // Setup: Create customer C201 with pending and rejected applications
        Customer customer = new Customer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephoneNumber("555-1212");
        
        // Create pending application
        IPOApplication pendingApp = new IPOApplication();
        pendingApp.setCompanyName("TechInc");
        pendingApp.setNumberOfShares(10);
        pendingApp.setAmount(1500);
        pendingApp.setDocument("QT-3001");
        pendingApp.setStatus(IPOApplicationStatus.PENDING);
        
        // Create rejected application
        IPOApplication rejectedApp = new IPOApplication();
        rejectedApp.setCompanyName("BioMed");
        rejectedApp.setNumberOfShares(10);
        rejectedApp.setAmount(2000);
        rejectedApp.setDocument("QT-3002");
        rejectedApp.setStatus(IPOApplicationStatus.REJECTED);
        
        customer.getIpoApplications().add(pendingApp);
        customer.getIpoApplications().add(rejectedApp);
        
        // Execute: Get total approved amount
        double result = ipoService.getTotalApprovedAmount(customer);
        
        // Verify: Expected output is 0 since no approved applications
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_singleApproval() {
        // Setup: Create customer C202 with one approved application
        Customer customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephoneNumber("555-2323");
        
        // Create approved application
        IPOApplication approvedApp = new IPOApplication();
        approvedApp.setCompanyName("SolarMax");
        approvedApp.setNumberOfShares(84);
        approvedApp.setAmount(4200);
        approvedApp.setDocument("SM-2024-Q1");
        approvedApp.setStatus(IPOApplicationStatus.APPROVED);
        
        customer.getIpoApplications().add(approvedApp);
        
        // Execute: Get total approved amount
        double result = ipoService.getTotalApprovedAmount(customer);
        
        // Verify: Expected output is 4200
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup: Create customer C203 with two approved applications
        Customer customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephoneNumber("555-3434");
        
        // Create first approved application
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("QuantumTech");
        app1.setNumberOfShares(40);
        app1.setAmount(2000);
        app1.setDocument("SM-2024-Q3004");
        app1.setStatus(IPOApplicationStatus.APPROVED);
        
        // Create second approved application
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("Neuralink");
        app2.setNumberOfShares(70);
        app2.setAmount(3500);
        app2.setDocument("SM-2024-Q3005");
        app2.setStatus(IPOApplicationStatus.APPROVED);
        
        customer.getIpoApplications().add(app1);
        customer.getIpoApplications().add(app2);
        
        // Execute: Get total approved amount
        double result = ipoService.getTotalApprovedAmount(customer);
        
        // Verify: Expected output is 5500 (2000 + 3500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_largePortfolio() {
        // Setup: Create customer C204 with five approved applications
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephoneNumber("555-4545");
        
        // Create 5 approved applications, each with $10,000
        String[] companies = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        String[] documents = {"SM-3006", "SM-3007", "SM-3008", "SM-3009", "SM-3010"};
        int[] shares = {200, 250, 125, 500, 200};
        
        for (int i = 0; i < 5; i++) {
            IPOApplication app = new IPOApplication();
            app.setCompanyName(companies[i]);
            app.setNumberOfShares(shares[i]);
            app.setAmount(10000);
            app.setDocument(documents[i]);
            app.setStatus(IPOApplicationStatus.APPROVED);
            customer.getIpoApplications().add(app);
        }
        
        // Execute: Get total approved amount
        double result = ipoService.getTotalApprovedAmount(customer);
        
        // Verify: Expected output is 50000 (5 * 10000)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup: Create customer C205 with 3 approved and 2 pending applications
        Customer customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephoneNumber("555-5656");
        
        // Create approved applications
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("CloudServ");
        app1.setNumberOfShares(100);
        app1.setAmount(3000);
        app1.setDocument("SM-3011");
        app1.setStatus(IPOApplicationStatus.APPROVED);
        
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("DataCore");
        app2.setNumberOfShares(20);
        app2.setAmount(2750);
        app2.setDocument("SM-3012");
        app2.setStatus(IPOApplicationStatus.APPROVED);
        
        IPOApplication app3 = new IPOApplication();
        app3.setCompanyName("AI Ventures");
        app3.setNumberOfShares(30);
        app3.setAmount(3000);
        app3.setDocument("SM-3013");
        app3.setStatus(IPOApplicationStatus.APPROVED);
        
        // Create pending applications (should not be included in total)
        IPOApplication pendingApp1 = new IPOApplication();
        pendingApp1.setCompanyName("NanoTech");
        pendingApp1.setNumberOfShares(10);
        pendingApp1.setAmount(600);
        pendingApp1.setDocument("SM-3014");
        pendingApp1.setStatus(IPOApplicationStatus.PENDING);
        
        IPOApplication pendingApp2 = new IPOApplication();
        pendingApp2.setCompanyName("RoboWorks");
        pendingApp2.setNumberOfShares(50);
        pendingApp2.setAmount(600);
        pendingApp2.setDocument("SM-3015");
        pendingApp2.setStatus(IPOApplicationStatus.PENDING);
        
        customer.getIpoApplications().add(app1);
        customer.getIpoApplications().add(app2);
        customer.getIpoApplications().add(app3);
        customer.getIpoApplications().add(pendingApp1);
        customer.getIpoApplications().add(pendingApp2);
        
        // Execute: Get total approved amount
        double result = ipoService.getTotalApprovedAmount(customer);
        
        // Verify: Expected output is 8750 (3000 + 2750 + 3000)
        assertEquals(8750.0, result, 0.001);
    }
}