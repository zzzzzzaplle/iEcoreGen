import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        // Reset customer before each test
        customer = null;
    }
    
    @Test
    public void testCase1_NoApprovedRequests() {
        // Setup: Create customer C201 with pending and rejected applications
        customer = new Customer("Emily", "Chen", "e.chen@example.com", "555-1212");
        customer.setCanApplyForIPO(true);
        
        // Create pending application
        Company techInc = new Company("TechInc", "tech@example.com");
        Document doc1 = new Document();
        Application app1 = new Application(10, 1500.0, customer, techInc, doc1);
        app1.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(app1);
        
        // Create rejected application
        Company bioMed = new Company("BioMed", "biomed@example.com");
        Document doc2 = new Document();
        Application app2 = new Application(10, 2000.0, customer, bioMed, doc2);
        app2.setStatus(ApplicationStatus.REJECTED);
        customer.getApplications().add(app2);
        
        // Execute: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 0 since no approved applications
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SingleApproval() {
        // Setup: Create customer C202 with one approved application
        customer = new Customer("Robert", "Johnson", "r.johnson@example.com", "555-2323");
        customer.setCanApplyForIPO(true);
        
        // Create approved application
        Company solarMax = new Company("SolarMax", "solarmax@gmail.com");
        Document doc = new Document();
        Application app = new Application(84, 4200.0, customer, solarMax, doc);
        app.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app);
        
        // Execute: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 4200.0
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup: Create customer C203 with two approved applications
        customer = new Customer("Sophia", "Williams", "s.williams@example.com", "555-3434");
        customer.setCanApplyForIPO(true);
        
        // First approved application
        Company quantumTech = new Company("QuantumTech", "quantum@example.com");
        Document doc1 = new Document();
        Application app1 = new Application(40, 2000.0, customer, quantumTech, doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app1);
        
        // Second approved application
        Company neuralink = new Company("Neuralink", "neuralink@example.com");
        Document doc2 = new Document();
        Application app2 = new Application(70, 3500.0, customer, neuralink, doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app2);
        
        // Execute: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 5500.0 (2000 + 3500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup: Create customer C204 with five approved applications
        customer = new Customer("James", "Wilson", "j.wilson@vip.example.com", "555-4545");
        customer.setCanApplyForIPO(true);
        
        // Create five approved applications, each with $10,000
        Company[] companies = {
            new Company("TechGiant", "techgiant@example.com"),
            new Company("AutoFuture", "autofuture@example.com"),
            new Company("AeroSpace", "aerospace@example.com"),
            new Company("BioGenius", "biogenius@example.com"),
            new Company("GreenEnergy", "greenenergy@example.com")
        };
        
        int[] shares = {200, 250, 125, 500, 200};
        String[] docIds = {"SM-3006", "SM-3007", "SM-3008", "SM-3009", "SM-3010"};
        
        for (int i = 0; i < companies.length; i++) {
            Document doc = new Document();
            Application app = new Application(shares[i], 10000.0, customer, companies[i], doc);
            app.setStatus(ApplicationStatus.APPROVAL);
            customer.getApplications().add(app);
        }
        
        // Execute: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 50000.0 (5 * 10000)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup: Create customer C205 with approved and pending applications
        customer = new Customer("Olivia", "Brown", "o.brown@example.com", "555-5656");
        customer.setCanApplyForIPO(true);
        
        // Approved applications
        Company cloudServ = new Company("CloudServ", "cloudserv@example.com");
        Document doc1 = new Document();
        Application app1 = new Application(100, 3000.0, customer, cloudServ, doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app1);
        
        Company dataCore = new Company("DataCore", "datacore@example.com");
        Document doc2 = new Document();
        Application app2 = new Application(20, 2750.0, customer, dataCore, doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app2);
        
        Company aiVentures = new Company("AI Ventures", "aiventures@example.com");
        Document doc3 = new Document();
        Application app3 = new Application(30, 3000.0, customer, aiVentures, doc3);
        app3.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app3);
        
        // Pending applications (should not be included in total)
        Company nanoTech = new Company("NanoTech", "nanotech@example.com");
        Document doc4 = new Document();
        Application app4 = new Application(10, 600.0, customer, nanoTech, doc4);
        app4.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(app4);
        
        Company roboWorks = new Company("RoboWorks", "roboworks@example.com");
        Document doc5 = new Document();
        Application app5 = new Application(50, 600.0, customer, roboWorks, doc5);
        app5.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(app5);
        
        // Execute: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 8750.0 (3000 + 2750 + 3000)
        assertEquals(8750.0, result, 0.001);
    }
}