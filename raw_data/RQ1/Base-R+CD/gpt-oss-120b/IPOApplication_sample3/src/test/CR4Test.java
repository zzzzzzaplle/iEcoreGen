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
    public void testCase1_NoApprovedRequests() {
        // Setup: Customer "C201" with pending and rejected applications
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);
        
        // Create pending application
        Company techInc = new Company();
        techInc.setName("TechInc");
        Document doc1 = new Document();
        Application app1 = new Application();
        app1.setCustomer(customer);
        app1.setCompany(techInc);
        app1.setShare(10);
        app1.setAmountOfMoney(1500.0);
        app1.setAllowance(doc1);
        app1.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(app1);
        
        // Create rejected application
        Company bioMed = new Company();
        bioMed.setName("BioMed");
        Document doc2 = new Document();
        Application app2 = new Application();
        app2.setCustomer(customer);
        app2.setCompany(bioMed);
        app2.setShare(10);
        app2.setAmountOfMoney(2000.0);
        app2.setAllowance(doc2);
        app2.setStatus(ApplicationStatus.REJECTED);
        customer.getApplications().add(app2);
        
        // Test: Customer requests total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: No approved requests should return 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SingleApproval() {
        // Setup: Customer "C202" with one approved application
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setCanApplyForIPO(true);
        
        // Create approved application
        Company solarMax = new Company();
        solarMax.setName("SolarMax");
        solarMax.setEmail("solarmax@gmail.com");
        Document doc = new Document();
        Application app = new Application();
        app.setCustomer(customer);
        app.setCompany(solarMax);
        app.setShare(84);
        app.setAmountOfMoney(4200.0);
        app.setAllowance(doc);
        app.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app);
        
        // Test: Customer checks total approved sum
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Single approval should return 4200
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with multiple approved applications
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setCanApplyForIPO(true);
        
        // Create first approved application
        Company quantumTech = new Company();
        quantumTech.setName("QuantumTech");
        Document doc1 = new Document();
        Application app1 = new Application();
        app1.setCustomer(customer);
        app1.setCompany(quantumTech);
        app1.setShare(40);
        app1.setAmountOfMoney(2000.0);
        app1.setAllowance(doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app1);
        
        // Create second approved application
        Company neuralink = new Company();
        neuralink.setName("Neuralink");
        Document doc2 = new Document();
        Application app2 = new Application();
        app2.setCustomer(customer);
        app2.setCompany(neuralink);
        app2.setShare(70);
        app2.setAmountOfMoney(3500.0);
        app2.setAllowance(doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app2);
        
        // Test: Customer asks for combined figure
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Multiple approvals should return sum (2000 + 3500 = 5500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup: Customer "C204" with large portfolio of approved applications
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setCanApplyForIPO(true);
        
        // Create 5 approved applications, each with $10,000
        String[] companyNames = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        int[] shares = {200, 250, 125, 500, 200};
        
        for (int i = 0; i < companyNames.length; i++) {
            Company company = new Company();
            company.setName(companyNames[i]);
            Document doc = new Document();
            Application app = new Application();
            app.setCustomer(customer);
            app.setCompany(company);
            app.setShare(shares[i]);
            app.setAmountOfMoney(10000.0);
            app.setAllowance(doc);
            app.setStatus(ApplicationStatus.APPROVAL);
            customer.getApplications().add(app);
        }
        
        // Test: Customer requests total amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: 5 approvals of $10,000 each should return 50,000
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup: Customer "C205" with approved and pending applications
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setCanApplyForIPO(true);
        
        // Create approved applications
        String[] approvedCompanies = {"CloudServ", "DataCore", "AI Ventures"};
        int[] approvedShares = {100, 20, 30};
        double[] approvedAmounts = {3000.0, 2750.0, 3000.0};
        
        for (int i = 0; i < approvedCompanies.length; i++) {
            Company company = new Company();
            company.setName(approvedCompanies[i]);
            Document doc = new Document();
            Application app = new Application();
            app.setCustomer(customer);
            app.setCompany(company);
            app.setShare(approvedShares[i]);
            app.setAmountOfMoney(approvedAmounts[i]);
            app.setAllowance(doc);
            app.setStatus(ApplicationStatus.APPROVAL);
            customer.getApplications().add(app);
        }
        
        // Create pending applications
        String[] pendingCompanies = {"NanoTech", "RoboWorks"};
        int[] pendingShares = {10, 50};
        double[] pendingAmounts = {600.0, 600.0};
        
        for (int i = 0; i < pendingCompanies.length; i++) {
            Company company = new Company();
            company.setName(pendingCompanies[i]);
            Document doc = new Document();
            Application app = new Application();
            app.setCustomer(customer);
            app.setCompany(company);
            app.setShare(pendingShares[i]);
            app.setAmountOfMoney(pendingAmounts[i]);
            app.setAllowance(doc);
            app.setStatus(ApplicationStatus.PENDING);
            customer.getApplications().add(app);
        }
        
        // Test: Customer asks for aggregate approved spending
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Only approved amounts should be counted (3000 + 2750 + 3000 = 8750)
        assertEquals(8750.0, result, 0.001);
    }
}