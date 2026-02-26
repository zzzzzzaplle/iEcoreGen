import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_noApprovedRequests() {
        // Setup: Customer "C201" with pending and rejected applications
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);
        
        // Create pending application for TechInc
        Company techInc = new Company();
        techInc.setName("TechInc");
        Document doc1 = new Document();
        customer.createApplication(techInc, 10, 1500.0, doc1);
        
        // Create rejected application for BioMed
        Company bioMed = new Company();
        bioMed.setName("BioMed");
        Document doc2 = new Document();
        Application rejectedApp = new Application();
        rejectedApp.setShare(10);
        rejectedApp.setAmountOfMoney(2000.0);
        rejectedApp.setStatus(ApplicationStatus.REJECTED);
        rejectedApp.setCustomer(customer);
        rejectedApp.setCompany(bioMed);
        rejectedApp.setAllowance(doc2);
        customer.getApplications().add(rejectedApp);
        
        // Test: Get total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 0 since there are no approved applications
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_singleApproval() {
        // Setup: Customer "C202" with one approved application
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setCanApplyForIPO(true);
        
        // Create approved application for SolarMax
        Company solarMax = new Company();
        solarMax.setName("SolarMax");
        solarMax.setEmail("solarmax@gmail.com");
        Document doc = new Document();
        Application approvedApp = new Application();
        approvedApp.setShare(84);
        approvedApp.setAmountOfMoney(4200.0);
        approvedApp.setStatus(ApplicationStatus.APPROVAL);
        approvedApp.setCustomer(customer);
        approvedApp.setCompany(solarMax);
        approvedApp.setAllowance(doc);
        customer.getApplications().add(approvedApp);
        
        // Test: Get total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 4200.0
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with two approved applications
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setCanApplyForIPO(true);
        
        // Create first approved application for QuantumTech
        Company quantumTech = new Company();
        quantumTech.setName("QuantumTech");
        Document doc1 = new Document();
        Application app1 = new Application();
        app1.setShare(40);
        app1.setAmountOfMoney(2000.0);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCustomer(customer);
        app1.setCompany(quantumTech);
        app1.setAllowance(doc1);
        customer.getApplications().add(app1);
        
        // Create second approved application for Neuralink
        Company neuralink = new Company();
        neuralink.setName("Neuralink");
        Document doc2 = new Document();
        Application app2 = new Application();
        app2.setShare(70);
        app2.setAmountOfMoney(3500.0);
        app2.setStatus(ApplicationStatus.APPROVAL);
        app2.setCustomer(customer);
        app2.setCompany(neuralink);
        app2.setAllowance(doc2);
        customer.getApplications().add(app2);
        
        // Test: Get total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 5500.0 (2000 + 3500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_largePortfolio() {
        // Setup: Customer "C204" with five approved applications
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setCanApplyForIPO(true);
        
        // Create 5 approved applications, each with $10,000
        String[] companyNames = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        int[] shares = {200, 250, 125, 500, 200};
        String[] docIds = {"SM-3006", "SM-3007", "SM-3008", "SM-3009", "SM-3010"};
        
        for (int i = 0; i < companyNames.length; i++) {
            Company company = new Company();
            company.setName(companyNames[i]);
            Document doc = new Document();
            Application app = new Application();
            app.setShare(shares[i]);
            app.setAmountOfMoney(10000.0);
            app.setStatus(ApplicationStatus.APPROVAL);
            app.setCustomer(customer);
            app.setCompany(company);
            app.setAllowance(doc);
            customer.getApplications().add(app);
        }
        
        // Test: Get total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 50000.0 (5 * 10000)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup: Customer "C205" with 3 approved and 2 pending applications
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setCanApplyForIPO(true);
        
        // Create 3 approved applications
        String[] approvedCompanies = {"CloudServ", "DataCore", "AI Ventures"};
        double[] approvedAmounts = {3000.0, 2750.0, 3000.0};
        int[] approvedShares = {100, 20, 30};
        String[] approvedDocs = {"SM-3011", "SM-3012", "SM-3013"};
        
        for (int i = 0; i < approvedCompanies.length; i++) {
            Company company = new Company();
            company.setName(approvedCompanies[i]);
            Document doc = new Document();
            Application app = new Application();
            app.setShare(approvedShares[i]);
            app.setAmountOfMoney(approvedAmounts[i]);
            app.setStatus(ApplicationStatus.APPROVAL);
            app.setCustomer(customer);
            app.setCompany(company);
            app.setAllowance(doc);
            customer.getApplications().add(app);
        }
        
        // Create 2 pending applications (should not be counted in total)
        String[] pendingCompanies = {"NanoTech", "RoboWorks"};
        double[] pendingAmounts = {600.0, 600.0};
        int[] pendingShares = {10, 50};
        String[] pendingDocs = {"SM-3014", "SM-3015"};
        
        for (int i = 0; i < pendingCompanies.length; i++) {
            Company company = new Company();
            company.setName(pendingCompanies[i]);
            Document doc = new Document();
            customer.createApplication(company, pendingShares[i], pendingAmounts[i], doc);
        }
        
        // Test: Get total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 8750.0 (3000 + 2750 + 3000) - pending apps not counted
        assertEquals(8750.0, result, 0.001);
    }
}