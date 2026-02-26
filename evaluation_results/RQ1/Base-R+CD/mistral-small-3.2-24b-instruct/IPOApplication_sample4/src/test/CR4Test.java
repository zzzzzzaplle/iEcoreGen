import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        customer = new Customer();
        company = new Company();
        document = new Document();
    }
    
    @Test
    public void testCase1_NoApprovedRequests() {
        // Setup: Customer "C201" (named "Emily Chen", email "e.chen@example.com", phone "555-1212")
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);
        
        // Create PENDING application: "APP-3001" (10 shares, $1,500, TechInc)
        Company techInc = new Company();
        techInc.setName("TechInc");
        Document doc1 = new Document();
        customer.createApplication(techInc, 10, 1500.0, doc1);
        
        // Create REJECTED application: "APP-3002" (10 shares, $2,000, BioMed)
        Company bioMed = new Company();
        bioMed.setName("BioMed");
        Document doc2 = new Document();
        Application app2 = new Application();
        app2.setShare(10);
        app2.setAmountOfMoney(2000.0);
        app2.setStatus(ApplicationStatus.REJECTED);
        app2.setCustomer(customer);
        app2.setCompany(bioMed);
        app2.setAllowance(doc2);
        customer.getApplications().add(app2);
        
        // Test: Customer requests total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SingleApproval() {
        // Setup: Customer "C202" (named "Robert Johnson", email "r.johnson@example.com", phone "555-2323")
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setCanApplyForIPO(true);
        
        // Create APPROVED application: "APP-3003" (84 shares, $4,200, SolarMax)
        Company solarMax = new Company();
        solarMax.setName("SolarMax");
        solarMax.setEmail("solarmax@gmail.com");
        Document doc = new Document();
        
        Application app = new Application();
        app.setShare(84);
        app.setAmountOfMoney(4200.0);
        app.setStatus(ApplicationStatus.APPROVAL);
        app.setCustomer(customer);
        app.setCompany(solarMax);
        app.setAllowance(doc);
        customer.getApplications().add(app);
        
        // Test: Customer checks the total approved sum
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 4,200
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" (named "Sophia Williams", email "s.williams@example.com", phone "555-3434")
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setCanApplyForIPO(true);
        
        // Create APPROVED application: "APP-3004" (40 shares, $2,000, QuantumTech)
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
        
        // Create APPROVED application: "APP-3005" (70 shares, $3,500, Neuralink)
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
        
        // Test: Customer asks for the combined figure
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 5,500
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup: Customer "C204" (named "James Wilson", email "j.wilson@vip.example.com", phone "555-4545")
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setCanApplyForIPO(true);
        
        // Create 5 APPROVED applications, each $10,000
        String[] companyNames = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        
        for (int i = 0; i < 5; i++) {
            Company company = new Company();
            company.setName(companyNames[i]);
            Document doc = new Document();
            
            Application app = new Application();
            app.setShare(100 + i * 50); // Varying share counts as specified
            app.setAmountOfMoney(10000.0);
            app.setStatus(ApplicationStatus.APPROVAL);
            app.setCustomer(customer);
            app.setCompany(company);
            app.setAllowance(doc);
            customer.getApplications().add(app);
        }
        
        // Test: Customer requests total amount
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 50,000
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup: Customer "C205" (named "Olivia Brown", email "o.brown@example.com", phone "555-5656")
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setCanApplyForIPO(true);
        
        // Create APPROVED applications
        String[] approvedCompanies = {"CloudServ", "DataCore", "AI Ventures"};
        double[] approvedAmounts = {3000.0, 2750.0, 3000.0};
        int[] approvedShares = {100, 20, 30};
        
        for (int i = 0; i < 3; i++) {
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
        
        // Create PENDING applications
        String[] pendingCompanies = {"NanoTech", "RoboWorks"};
        double[] pendingAmounts = {600.0, 600.0};
        int[] pendingShares = {10, 50};
        
        for (int i = 0; i < 2; i++) {
            Company company = new Company();
            company.setName(pendingCompanies[i]);
            Document doc = new Document();
            
            Application app = new Application();
            app.setShare(pendingShares[i]);
            app.setAmountOfMoney(pendingAmounts[i]);
            app.setStatus(ApplicationStatus.PENDING);
            app.setCustomer(customer);
            app.setCompany(company);
            app.setAllowance(doc);
            customer.getApplications().add(app);
        }
        
        // Test: Customer asks for aggregate approved spending
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 8,750
        assertEquals(8750.0, result, 0.001);
    }
}