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
        // Setup: Customer "C201" (named "Emily Chen", email "e.chen@example.com", phone "555-1212")
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);
        
        // Create pending application: "APP-3001": PENDING (10 shares, $1,500, TechInc, document: "QT-3001")
        Company techInc = new Company();
        techInc.setName("TechInc");
        Document doc1 = new Document();
        Application app1 = new Application();
        app1.setShare(10);
        app1.setAmountOfMoney(1500.0);
        app1.setCompany(techInc);
        app1.setCustomer(customer);
        app1.setAllowance(doc1);
        app1.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(app1);
        
        // Create rejected application: "APP-3002": REJECTED (10 shares, $2,000, BioMed, document: "QT-3002")
        Company bioMed = new Company();
        bioMed.setName("BioMed");
        Document doc2 = new Document();
        Application app2 = new Application();
        app2.setShare(10);
        app2.setAmountOfMoney(2000.0);
        app2.setCompany(bioMed);
        app2.setCustomer(customer);
        app2.setAllowance(doc2);
        app2.setStatus(ApplicationStatus.REJECTED);
        customer.getApplications().add(app2);
        
        // Test: Customer "C201" requests total approved amount
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
        
        // Create approved application: "APP-3003" - Company: "SolarMax", Amount: $4,200, Shares: 84
        Company solarMax = new Company();
        solarMax.setName("SolarMax");
        solarMax.setEmail("solarmax@gmail.com");
        Document doc = new Document();
        Application app = new Application();
        app.setShare(84);
        app.setAmountOfMoney(4200.0);
        app.setCompany(solarMax);
        app.setCustomer(customer);
        app.setAllowance(doc);
        app.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app);
        
        // Test: Customer "C202" checks the total approved sum
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
        
        // Create first approved application: "APP-3004" - Company: "QuantumTech", Amount: $2,000, Shares: 40
        Company quantumTech = new Company();
        quantumTech.setName("QuantumTech");
        Document doc1 = new Document();
        Application app1 = new Application();
        app1.setShare(40);
        app1.setAmountOfMoney(2000.0);
        app1.setCompany(quantumTech);
        app1.setCustomer(customer);
        app1.setAllowance(doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app1);
        
        // Create second approved application: "APP-3005" - Company: "Neuralink", Amount: $3,500, Shares: 70
        Company neuralink = new Company();
        neuralink.setName("Neuralink");
        Document doc2 = new Document();
        Application app2 = new Application();
        app2.setShare(70);
        app2.setAmountOfMoney(3500.0);
        app2.setCompany(neuralink);
        app2.setCustomer(customer);
        app2.setAllowance(doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app2);
        
        // Test: Customer "C203" asks for the combined figure
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
        
        // Create 5 approved applications, each with $10,000
        String[] companyNames = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        int[] shares = {200, 250, 125, 500, 200};
        
        for (int i = 0; i < companyNames.length; i++) {
            Company company = new Company();
            company.setName(companyNames[i]);
            Document doc = new Document();
            Application app = new Application();
            app.setShare(shares[i]);
            app.setAmountOfMoney(10000.0);
            app.setCompany(company);
            app.setCustomer(customer);
            app.setAllowance(doc);
            app.setStatus(ApplicationStatus.APPROVAL);
            customer.getApplications().add(app);
        }
        
        // Test: Customer "C204" requests total amount
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
        
        // Create approved applications
        String[] approvedCompanies = {"CloudServ", "DataCore", "AI Ventures"};
        int[] approvedShares = {100, 20, 30};
        double[] approvedAmounts = {3000.0, 2750.0, 3000.0};
        
        for (int i = 0; i < approvedCompanies.length; i++) {
            Company company = new Company();
            company.setName(approvedCompanies[i]);
            Document doc = new Document();
            Application app = new Application();
            app.setShare(approvedShares[i]);
            app.setAmountOfMoney(approvedAmounts[i]);
            app.setCompany(company);
            app.setCustomer(customer);
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
            app.setShare(pendingShares[i]);
            app.setAmountOfMoney(pendingAmounts[i]);
            app.setCompany(company);
            app.setCustomer(customer);
            app.setAllowance(doc);
            app.setStatus(ApplicationStatus.PENDING);
            customer.getApplications().add(app);
        }
        
        // Test: Customer "C205" asks for aggregate approved spending
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 8,750
        assertEquals(8750.0, result, 0.001);
    }
}