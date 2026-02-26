import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        // Reset customer before each test
        customer = null;
    }
    
    @Test
    public void testCase1_noApprovedRequests() {
        // Setup: Customer "C201" (named "Emily Chen", email "e.chen@example.com", phone "555-1212")
        customer = new Customer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);
        
        // Setup: Application history
        List<Application> applications = new ArrayList<>();
        
        // PENDING application
        Application app1 = new Application();
        app1.setShare(10);
        app1.setAmountOfMoney(1500.0);
        app1.setStatus(ApplicationStatus.PENDING);
        Company techInc = new Company("TechInc", "techinc@example.com");
        app1.setCompany(techInc);
        Document doc1 = new Document();
        app1.setAllowance(doc1);
        applications.add(app1);
        
        // REJECTED application
        Application app2 = new Application();
        app2.setShare(10);
        app2.setAmountOfMoney(2000.0);
        app2.setStatus(ApplicationStatus.REJECTED);
        Company bioMed = new Company("BioMed", "biomed@example.com");
        app2.setCompany(bioMed);
        Document doc2 = new Document();
        app2.setAllowance(doc2);
        applications.add(app2);
        
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_singleApproval() {
        // Setup: Customer "C202" (named "Robert Johnson", email "r.johnson@example.com", phone "555-2323")
        customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setCanApplyForIPO(true);
        
        // Setup: Approved application
        List<Application> applications = new ArrayList<>();
        
        Application app = new Application();
        app.setShare(84);
        app.setAmountOfMoney(4200.0);
        app.setStatus(ApplicationStatus.APPROVAL);
        Company solarMax = new Company("SolarMax", "solarmax@gmail.com");
        app.setCompany(solarMax);
        Document doc = new Document();
        app.setAllowance(doc);
        applications.add(app);
        
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 4,200
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" (named "Sophia Williams", email "s.williams@example.com", phone "555-3434")
        customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setCanApplyForIPO(true);
        
        // Setup: Approved applications
        List<Application> applications = new ArrayList<>();
        
        // First approved application
        Application app1 = new Application();
        app1.setShare(40);
        app1.setAmountOfMoney(2000.0);
        app1.setStatus(ApplicationStatus.APPROVAL);
        Company quantumTech = new Company("QuantumTech", "quantumtech@example.com");
        app1.setCompany(quantumTech);
        Document doc1 = new Document();
        app1.setAllowance(doc1);
        applications.add(app1);
        
        // Second approved application
        Application app2 = new Application();
        app2.setShare(70);
        app2.setAmountOfMoney(3500.0);
        app2.setStatus(ApplicationStatus.APPROVAL);
        Company neuralink = new Company("Neuralink", "neuralink@example.com");
        app2.setCompany(neuralink);
        Document doc2 = new Document();
        app2.setAllowance(doc2);
        applications.add(app2);
        
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 5,500
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_largePortfolio() {
        // Setup: Customer "C204" (named "James Wilson", email "j.wilson@vip.example.com", phone "555-4545")
        customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setCanApplyForIPO(true);
        
        // Setup: Approved applications (all $10,000 each)
        List<Application> applications = new ArrayList<>();
        
        String[] companyNames = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        int[] shares = {200, 250, 125, 500, 200};
        String[] docIds = {"SM-3006", "SM-3007", "SM-3008", "SM-3009", "SM-3010"};
        
        for (int i = 0; i < 5; i++) {
            Application app = new Application();
            app.setShare(shares[i]);
            app.setAmountOfMoney(10000.0);
            app.setStatus(ApplicationStatus.APPROVAL);
            Company company = new Company(companyNames[i], companyNames[i].toLowerCase() + "@example.com");
            app.setCompany(company);
            Document doc = new Document();
            app.setAllowance(doc);
            applications.add(app);
        }
        
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 50,000
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup: Customer "C205" (named "Olivia Brown", email "o.brown@example.com", phone "555-5656")
        customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setCanApplyForIPO(true);
        
        // Setup: Applications
        List<Application> applications = new ArrayList<>();
        
        // Approved applications
        String[] approvedCompanies = {"CloudServ", "DataCore", "AI Ventures"};
        int[] approvedShares = {100, 20, 30};
        double[] approvedAmounts = {3000.0, 2750.0, 3000.0};
        String[] approvedDocs = {"SM-3011", "SM-3012", "SM-3013"};
        
        for (int i = 0; i < 3; i++) {
            Application app = new Application();
            app.setShare(approvedShares[i]);
            app.setAmountOfMoney(approvedAmounts[i]);
            app.setStatus(ApplicationStatus.APPROVAL);
            Company company = new Company(approvedCompanies[i], approvedCompanies[i].toLowerCase() + "@example.com");
            app.setCompany(company);
            Document doc = new Document();
            app.setAllowance(doc);
            applications.add(app);
        }
        
        // Pending applications
        String[] pendingCompanies = {"NanoTech", "RoboWorks"};
        int[] pendingShares = {10, 50};
        double[] pendingAmounts = {600.0, 600.0};
        String[] pendingDocs = {"SM-3014", "SM-3015"};
        
        for (int i = 0; i < 2; i++) {
            Application app = new Application();
            app.setShare(pendingShares[i]);
            app.setAmountOfMoney(pendingAmounts[i]);
            app.setStatus(ApplicationStatus.PENDING);
            Company company = new Company(pendingCompanies[i], pendingCompanies[i].toLowerCase() + "@example.com");
            app.setCompany(company);
            Document doc = new Document();
            app.setAllowance(doc);
            applications.add(app);
        }
        
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 8,750
        assertEquals(8750.0, result, 0.001);
    }
}