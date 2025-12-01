import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
    public void testCase1_noApprovedRequests() {
        // Setup: Customer "C201" (named "Emily Chen", email "e.chen@example.com", phone "555-1212")
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);
        
        // Create PENDING application: "APP-3001": PENDING (10 shares, $1,500, TechInc, document: "QT-3001")
        Company techInc = new Company();
        techInc.setName("TechInc");
        techInc.setEmail("techinc@example.com");
        Document doc1 = new Document();
        
        Application pendingApp = new Application();
        pendingApp.setShare(10);
        pendingApp.setAmountOfMoney(1500.0);
        pendingApp.setStatus(ApplicationStatus.PENDING);
        pendingApp.setCustomer(customer);
        pendingApp.setCompany(techInc);
        pendingApp.setAllowance(doc1);
        
        // Create REJECTED application: "APP-3002": REJECTED (10 shares, $2,000, BioMed, document: "QT-3002")
        Company bioMed = new Company();
        bioMed.setName("BioMed");
        bioMed.setEmail("biomed@example.com");
        Document doc2 = new Document();
        
        Application rejectedApp = new Application();
        rejectedApp.setShare(10);
        rejectedApp.setAmountOfMoney(2000.0);
        rejectedApp.setStatus(ApplicationStatus.REJECTED);
        rejectedApp.setCustomer(customer);
        rejectedApp.setCompany(bioMed);
        rejectedApp.setAllowance(doc2);
        
        // Add applications to customer
        List<Application> applications = new ArrayList<>();
        applications.add(pendingApp);
        applications.add(rejectedApp);
        customer.setApplications(applications);
        
        // Test: Customer "C201" requests total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_singleApproval() {
        // Setup: Customer "C202" (named "Robert Johnson", email "r.johnson@example.com", phone "555-2323")
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setCanApplyForIPO(true);
        
        // Create approved application: "APP-3003": Company: "SolarMax" (solarmax@gmail.com), Amount: $4,200, Shares: 84, Document: 'SM-2024-Q1'
        Company solarMax = new Company();
        solarMax.setName("SolarMax");
        solarMax.setEmail("solarmax@gmail.com");
        Document doc = new Document();
        
        Application approvedApp = new Application();
        approvedApp.setShare(84);
        approvedApp.setAmountOfMoney(4200.0);
        approvedApp.setStatus(ApplicationStatus.APPROVED);
        approvedApp.setCustomer(customer);
        approvedApp.setCompany(solarMax);
        approvedApp.setAllowance(doc);
        
        // Add application to customer
        List<Application> applications = new ArrayList<>();
        applications.add(approvedApp);
        customer.setApplications(applications);
        
        // Test: Customer "C202" checks the total approved sum
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 4,200
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" (named "Sophia Williams", email "s.williams@example.com", phone "555-3434")
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setCanApplyForIPO(true);
        
        // Create first approved application: "APP-3004": Company: "QuantumTech", Amount: $2,000, Shares: 40, Document: 'SM-2024-Q3004'
        Company quantumTech = new Company();
        quantumTech.setName("QuantumTech");
        quantumTech.setEmail("quantumtech@example.com");
        Document doc1 = new Document();
        
        Application app1 = new Application();
        app1.setShare(40);
        app1.setAmountOfMoney(2000.0);
        app1.setStatus(ApplicationStatus.APPROVED);
        app1.setCustomer(customer);
        app1.setCompany(quantumTech);
        app1.setAllowance(doc1);
        
        // Create second approved application: "APP-3005": Company: "Neuralink", Amount: $3,500, Shares: 70, Document: 'SM-2024-Q3005'
        Company neuralink = new Company();
        neuralink.setName("Neuralink");
        neuralink.setEmail("neuralink@example.com");
        Document doc2 = new Document();
        
        Application app2 = new Application();
        app2.setShare(70);
        app2.setAmountOfMoney(3500.0);
        app2.setStatus(ApplicationStatus.APPROVED);
        app2.setCustomer(customer);
        app2.setCompany(neuralink);
        app2.setAllowance(doc2);
        
        // Add applications to customer
        List<Application> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        customer.setApplications(applications);
        
        // Test: Customer "C203" asks for the combined figure
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 5,500
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_largePortfolio() {
        // Setup: Customer "C204" (named "James Wilson", email "j.wilson@vip.example.com", phone "555-4545")
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setCanApplyForIPO(true);
        
        List<Application> applications = new ArrayList<>();
        
        // Create 5 approved applications, all $10,000 each
        String[] companyNames = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        String[] companyEmails = {"techgiant@example.com", "autofuture@example.com", 
                                 "aerospace@example.com", "biogenius@example.com", "greenenergy@example.com"};
        int[] shares = {200, 250, 125, 500, 200};
        String[] documentIds = {"SM-3006", "SM-3007", "SM-3008", "SM-3009", "SM-3010"};
        
        for (int i = 0; i < 5; i++) {
            Company company = new Company();
            company.setName(companyNames[i]);
            company.setEmail(companyEmails[i]);
            
            Document doc = new Document();
            
            Application app = new Application();
            app.setShare(shares[i]);
            app.setAmountOfMoney(10000.0);
            app.setStatus(ApplicationStatus.APPROVED);
            app.setCustomer(customer);
            app.setCompany(company);
            app.setAllowance(doc);
            
            applications.add(app);
        }
        
        customer.setApplications(applications);
        
        // Test: Customer "C204" requests total amount
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 50,000
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup: Customer "C205" (named "Olivia Brown", email "o.brown@example.com", phone "555-5656")
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setCanApplyForIPO(true);
        
        List<Application> applications = new ArrayList<>();
        
        // Approved applications
        String[] approvedCompanies = {"CloudServ", "DataCore", "AI Ventures"};
        double[] approvedAmounts = {3000.0, 2750.0, 3000.0};
        int[] approvedShares = {100, 20, 30};
        String[] approvedDocs = {"SM-3011", "SM-3012", "SM-3013"};
        
        for (int i = 0; i < 3; i++) {
            Company company = new Company();
            company.setName(approvedCompanies[i]);
            company.setEmail(approvedCompanies[i].toLowerCase() + "@example.com");
            
            Document doc = new Document();
            
            Application app = new Application();
            app.setShare(approvedShares[i]);
            app.setAmountOfMoney(approvedAmounts[i]);
            app.setStatus(ApplicationStatus.APPROVED);
            app.setCustomer(customer);
            app.setCompany(company);
            app.setAllowance(doc);
            
            applications.add(app);
        }
        
        // Pending applications
        String[] pendingCompanies = {"NanoTech", "RoboWorks"};
        double[] pendingAmounts = {600.0, 600.0};
        int[] pendingShares = {10, 50};
        String[] pendingDocs = {"SM-3014", "SM-3015"};
        
        for (int i = 0; i < 2; i++) {
            Company company = new Company();
            company.setName(pendingCompanies[i]);
            company.setEmail(pendingCompanies[i].toLowerCase() + "@example.com");
            
            Document doc = new Document();
            
            Application app = new Application();
            app.setShare(pendingShares[i]);
            app.setAmountOfMoney(pendingAmounts[i]);
            app.setStatus(ApplicationStatus.PENDING);
            app.setCustomer(customer);
            app.setCompany(company);
            app.setAllowance(doc);
            
            applications.add(app);
        }
        
        customer.setApplications(applications);
        
        // Test: Customer "C205" asks for aggregate approved spending
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 8,750
        assertEquals(8750.0, result, 0.001);
    }
}