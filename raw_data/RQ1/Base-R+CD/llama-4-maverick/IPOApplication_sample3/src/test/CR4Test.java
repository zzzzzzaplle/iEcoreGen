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
    public void testCase1_noApprovedRequests() {
        // Setup: Customer "C201" with pending and rejected applications
        customer = new Customer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
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
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 0 since no approved applications
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_singleApproval() {
        // Setup: Customer "C202" with one approved application
        customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setCanApplyForIPO(true);
        
        // Create approved application
        Company solarMax = new Company("SolarMax", "solarmax@gmail.com");
        Document doc = new Document();
        Application app = new Application(84, 4200.0, customer, solarMax, doc);
        app.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 4200.0
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with multiple approved applications
        customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setCanApplyForIPO(true);
        
        // Create first approved application
        Company quantumTech = new Company("QuantumTech", "quantum@example.com");
        Document doc1 = new Document();
        Application app1 = new Application(40, 2000.0, customer, quantumTech, doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app1);
        
        // Create second approved application
        Company neuralink = new Company("Neuralink", "neuralink@example.com");
        Document doc2 = new Document();
        Application app2 = new Application(70, 3500.0, customer, neuralink, doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app2);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 5500.0 (2000 + 3500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_largePortfolio() {
        // Setup: Customer "C204" with multiple approved applications of $10,000 each
        customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setCanApplyForIPO(true);
        
        // Create 5 approved applications, each with $10,000
        String[] companyNames = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        
        for (int i = 0; i < 5; i++) {
            Company company = new Company(companyNames[i], companyNames[i].toLowerCase() + "@example.com");
            Document doc = new Document();
            Application app = new Application(100 + i * 50, 10000.0, customer, company, doc);
            app.setStatus(ApplicationStatus.APPROVAL);
            customer.getApplications().add(app);
        }
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 50000.0 (5 * 10000)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup: Customer "C205" with approved and pending applications
        customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setCanApplyForIPO(true);
        
        // Create approved applications
        String[] approvedCompanies = {"CloudServ", "DataCore", "AI Ventures"};
        int[] approvedShares = {100, 20, 30};
        double[] approvedAmounts = {3000.0, 2750.0, 3000.0};
        
        for (int i = 0; i < 3; i++) {
            Company company = new Company(approvedCompanies[i], approvedCompanies[i].toLowerCase() + "@example.com");
            Document doc = new Document();
            Application app = new Application(approvedShares[i], approvedAmounts[i], customer, company, doc);
            app.setStatus(ApplicationStatus.APPROVAL);
            customer.getApplications().add(app);
        }
        
        // Create pending applications (should not be counted in approved total)
        String[] pendingCompanies = {"NanoTech", "RoboWorks"};
        int[] pendingShares = {10, 50};
        double[] pendingAmounts = {600.0, 600.0};
        
        for (int i = 0; i < 2; i++) {
            Company company = new Company(pendingCompanies[i], pendingCompanies[i].toLowerCase() + "@example.com");
            Document doc = new Document();
            Application app = new Application(pendingShares[i], pendingAmounts[i], customer, company, doc);
            app.setStatus(ApplicationStatus.PENDING);
            customer.getApplications().add(app);
        }
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 8750.0 (3000 + 2750 + 3000) - pending applications not counted
        assertEquals(8750.0, result, 0.001);
    }
}