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
    public void testCase1_NoApprovedRequests() {
        // Setup: Create customer C201 with pending and rejected applications
        Customer customer = new Customer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setRetail(true);
        
        // Create pending application
        IPOApplication pendingApp = new IPOApplication();
        pendingApp.setNumberOfShares(10);
        pendingApp.setAmount(1500.0);
        pendingApp.setStatus(ApplicationStatus.PENDING);
        
        Company techInc = new Company();
        techInc.setName("TechInc");
        pendingApp.setCompany(techInc);
        pendingApp.setDocument("QT-3001".getBytes());
        customer.addApplication(pendingApp);
        
        // Create rejected application
        IPOApplication rejectedApp = new IPOApplication();
        rejectedApp.setNumberOfShares(10);
        rejectedApp.setAmount(2000.0);
        rejectedApp.setStatus(ApplicationStatus.REJECTED);
        
        Company bioMed = new Company();
        bioMed.setName("BioMed");
        rejectedApp.setCompany(bioMed);
        rejectedApp.setDocument("QT-3002".getBytes());
        customer.addApplication(rejectedApp);
        
        // Test: Query total approved amount
        double result = ipoService.getTotalApprovedAmount(customer);
        
        // Verify: Should return 0 since no approved applications
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SingleApproval() {
        // Setup: Create customer C202 with one approved application
        Customer customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setRetail(true);
        
        // Create approved application
        IPOApplication approvedApp = new IPOApplication();
        approvedApp.setNumberOfShares(84);
        approvedApp.setAmount(4200.0);
        approvedApp.setStatus(ApplicationStatus.APPROVED);
        
        Company solarMax = new Company();
        solarMax.setName("SolarMax");
        approvedApp.setCompany(solarMax);
        approvedApp.setDocument("SM-2024-Q1".getBytes());
        customer.addApplication(approvedApp);
        
        // Test: Query total approved amount
        double result = ipoService.getTotalApprovedAmount(customer);
        
        // Verify: Should return 4200.0 for the single approved application
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup: Create customer C203 with two approved applications
        Customer customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setRetail(true);
        
        // Create first approved application
        IPOApplication app1 = new IPOApplication();
        app1.setNumberOfShares(40);
        app1.setAmount(2000.0);
        app1.setStatus(ApplicationStatus.APPROVED);
        
        Company quantumTech = new Company();
        quantumTech.setName("QuantumTech");
        app1.setCompany(quantumTech);
        app1.setDocument("SM-2024-Q3004".getBytes());
        customer.addApplication(app1);
        
        // Create second approved application
        IPOApplication app2 = new IPOApplication();
        app2.setNumberOfShares(70);
        app2.setAmount(3500.0);
        app2.setStatus(ApplicationStatus.APPROVED);
        
        Company neuralink = new Company();
        neuralink.setName("Neuralink");
        app2.setCompany(neuralink);
        app2.setDocument("SM-2024-Q3005".getBytes());
        customer.addApplication(app2);
        
        // Test: Query total approved amount
        double result = ipoService.getTotalApprovedAmount(customer);
        
        // Verify: Should return 5500.0 (2000 + 3500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup: Create customer C204 with five approved applications
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setRetail(true);
        
        // Create five approved applications, each with $10,000
        String[] companyNames = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        int[] shares = {200, 250, 125, 500, 200};
        String[] documentIds = {"SM-3006", "SM-3007", "SM-3008", "SM-3009", "SM-3010"};
        
        for (int i = 0; i < companyNames.length; i++) {
            IPOApplication app = new IPOApplication();
            app.setNumberOfShares(shares[i]);
            app.setAmount(10000.0);
            app.setStatus(ApplicationStatus.APPROVED);
            
            Company company = new Company();
            company.setName(companyNames[i]);
            app.setCompany(company);
            app.setDocument(documentIds[i].getBytes());
            customer.addApplication(app);
        }
        
        // Test: Query total approved amount
        double result = ipoService.getTotalApprovedAmount(customer);
        
        // Verify: Should return 50000.0 (5 * 10000)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup: Create customer C205 with approved and pending applications
        Customer customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setRetail(true);
        
        // Create three approved applications
        Object[][] approvedAppsData = {
            {"CloudServ", 100, 3000.0, "SM-3011"},
            {"DataCore", 20, 2750.0, "SM-3012"}, 
            {"AI Ventures", 30, 3000.0, "SM-3013"}
        };
        
        for (Object[] appData : approvedAppsData) {
            IPOApplication app = new IPOApplication();
            app.setNumberOfShares((Integer) appData[1]);
            app.setAmount((Double) appData[2]);
            app.setStatus(ApplicationStatus.APPROVED);
            
            Company company = new Company();
            company.setName((String) appData[0]);
            app.setCompany(company);
            app.setDocument(((String) appData[3]).getBytes());
            customer.addApplication(app);
        }
        
        // Create two pending applications (should not be counted)
        Object[][] pendingAppsData = {
            {"NanoTech", 10, 600.0, "SM-3014"},
            {"RoboWorks", 50, 600.0, "SM-3015"}
        };
        
        for (Object[] appData : pendingAppsData) {
            IPOApplication app = new IPOApplication();
            app.setNumberOfShares((Integer) appData[1]);
            app.setAmount((Double) appData[2]);
            app.setStatus(ApplicationStatus.PENDING);
            
            Company company = new Company();
            company.setName((String) appData[0]);
            app.setCompany(company);
            app.setDocument(((String) appData[3]).getBytes());
            customer.addApplication(app);
        }
        
        // Test: Query total approved amount
        double result = ipoService.getTotalApprovedAmount(customer);
        
        // Verify: Should return 8750.0 (3000 + 2750 + 3000) - pending applications ignored
        assertEquals(8750.0, result, 0.001);
    }
}