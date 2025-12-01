import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        document = new Document();
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
        Company techInc = new Company();
        techInc.setName("TechInc");
        Application pendingApp = new Application();
        pendingApp.setCustomer(customer);
        pendingApp.setCompany(techInc);
        pendingApp.setShare(10);
        pendingApp.setAmountOfMoney(1500.0);
        pendingApp.setAllowance(document);
        pendingApp.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(pendingApp);
        
        // Create rejected application
        Company bioMed = new Company();
        bioMed.setName("BioMed");
        Application rejectedApp = new Application();
        rejectedApp.setCustomer(customer);
        rejectedApp.setCompany(bioMed);
        rejectedApp.setShare(10);
        rejectedApp.setAmountOfMoney(2000.0);
        rejectedApp.setAllowance(document);
        rejectedApp.setStatus(ApplicationStatus.REJECTED);
        customer.getApplications().add(rejectedApp);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: No approved applications, expected output is 0
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
        
        // Create and add approved application
        Company solarMax = new Company();
        solarMax.setName("SolarMax");
        solarMax.setEmail("solarmax@gmail.com");
        
        Application approvedApp = new Application();
        approvedApp.setCustomer(customer);
        approvedApp.setCompany(solarMax);
        approvedApp.setShare(84);
        approvedApp.setAmountOfMoney(4200.0);
        approvedApp.setAllowance(document);
        approvedApp.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(approvedApp);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Single approved application of $4,200
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with two approved applications from different companies
        customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        
        // Create first approved application
        Company quantumTech = new Company();
        quantumTech.setName("QuantumTech");
        Application app1 = new Application();
        app1.setCustomer(customer);
        app1.setCompany(quantumTech);
        app1.setShare(40);
        app1.setAmountOfMoney(2000.0);
        app1.setAllowance(document);
        app1.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app1);
        
        // Create second approved application
        Company neuralink = new Company();
        neuralink.setName("Neuralink");
        Application app2 = new Application();
        app2.setCustomer(customer);
        app2.setCompany(neuralink);
        app2.setShare(70);
        app2.setAmountOfMoney(3500.0);
        app2.setAllowance(document);
        app2.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app2);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Sum of both approved applications (2000 + 3500 = 5500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_largePortfolio() {
        // Setup: Customer "C204" with five approved applications, each $10,000
        customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        
        // Create five approved applications, each with $10,000
        String[] companyNames = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        int[] shares = {200, 250, 125, 500, 200};
        
        for (int i = 0; i < companyNames.length; i++) {
            Company company = new Company();
            company.setName(companyNames[i]);
            
            Application app = new Application();
            app.setCustomer(customer);
            app.setCompany(company);
            app.setShare(shares[i]);
            app.setAmountOfMoney(10000.0);
            app.setAllowance(document);
            app.setStatus(ApplicationStatus.APPROVAL);
            customer.getApplications().add(app);
        }
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: 5 applications × $10,000 each = $50,000
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup: Customer "C205" with 3 approved and 2 pending applications
        customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        
        // Create approved applications
        String[] approvedCompanies = {"CloudServ", "DataCore", "AI Ventures"};
        int[] approvedShares = {100, 20, 30};
        double[] approvedAmounts = {3000.0, 2750.0, 3000.0};
        
        for (int i = 0; i < approvedCompanies.length; i++) {
            Company company = new Company();
            company.setName(approvedCompanies[i]);
            
            Application app = new Application();
            app.setCustomer(customer);
            app.setCompany(company);
            app.setShare(approvedShares[i]);
            app.setAmountOfMoney(approvedAmounts[i]);
            app.setAllowance(document);
            app.setStatus(ApplicationStatus.APPROVAL);
            customer.getApplications().add(app);
        }
        
        // Create pending applications (should not be counted in total)
        String[] pendingCompanies = {"NanoTech", "RoboWorks"};
        int[] pendingShares = {10, 50};
        double[] pendingAmounts = {600.0, 600.0};
        
        for (int i = 0; i < pendingCompanies.length; i++) {
            Company company = new Company();
            company.setName(pendingCompanies[i]);
            
            Application app = new Application();
            app.setCustomer(customer);
            app.setCompany(company);
            app.setShare(pendingShares[i]);
            app.setAmountOfMoney(pendingAmounts[i]);
            app.setAllowance(document);
            app.setStatus(ApplicationStatus.PENDING);
            customer.getApplications().add(app);
        }
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Only approved applications count (3000 + 2750 + 3000 = 8750)
        assertEquals(8750.0, result, 0.001);
    }
}