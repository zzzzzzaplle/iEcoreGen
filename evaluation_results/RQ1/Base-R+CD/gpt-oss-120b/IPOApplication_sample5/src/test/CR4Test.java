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
    public void testCase1_NoApprovedRequests() {
        // Setup: Customer "C201" (named "Emily Chen", email "e.chen@example.com", phone "555-1212")
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);
        
        // Create pending application: "APP-3001": PENDING (10 shares, $1,500, TechInc)
        Company techInc = new Company();
        techInc.setName("TechInc");
        Document doc1 = new Document();
        customer.createApplication(techInc, 10, 1500.0, doc1);
        
        // Create rejected application: "APP-3002": REJECTED (10 shares, $2,000, BioMed)
        Company bioMed = new Company();
        bioMed.setName("BioMed");
        Document doc2 = new Document();
        customer.createApplication(bioMed, 10, 2000.0, doc2);
        
        // Manually set the second application to REJECTED status
        List<Application> applications = customer.getApplications();
        if (applications.size() >= 2) {
            applications.get(1).setStatus(ApplicationStatus.REJECTED);
        }
        
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
        
        // Create approved application: "APP-3003" - SolarMax, $4,200, 84 shares
        Company solarMax = new Company();
        solarMax.setName("SolarMax");
        solarMax.setEmail("solarmax@gmail.com");
        Document doc = new Document();
        customer.createApplication(solarMax, 84, 4200.0, doc);
        
        // Manually set the application to APPROVAL status
        List<Application> applications = customer.getApplications();
        if (!applications.isEmpty()) {
            applications.get(0).setStatus(ApplicationStatus.APPROVAL);
        }
        
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
        
        // Create first approved application: "APP-3004" - QuantumTech, $2,000, 40 shares
        Company quantumTech = new Company();
        quantumTech.setName("QuantumTech");
        Document doc1 = new Document();
        customer.createApplication(quantumTech, 40, 2000.0, doc1);
        
        // Create second approved application: "APP-3005" - Neuralink, $3,500, 70 shares
        Company neuralink = new Company();
        neuralink.setName("Neuralink");
        Document doc2 = new Document();
        customer.createApplication(neuralink, 70, 3500.0, doc2);
        
        // Manually set both applications to APPROVAL status
        List<Application> applications = customer.getApplications();
        for (Application app : applications) {
            app.setStatus(ApplicationStatus.APPROVAL);
        }
        
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
        
        // Create 5 approved applications, each $10,000
        String[] companyNames = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        int[] shares = {200, 250, 125, 500, 200};
        
        for (int i = 0; i < companyNames.length; i++) {
            Company company = new Company();
            company.setName(companyNames[i]);
            Document doc = new Document();
            customer.createApplication(company, shares[i], 10000.0, doc);
        }
        
        // Manually set all applications to APPROVAL status
        List<Application> applications = customer.getApplications();
        for (Application app : applications) {
            app.setStatus(ApplicationStatus.APPROVAL);
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
        
        // Create approved applications
        String[] approvedCompanies = {"CloudServ", "DataCore", "AI Ventures"};
        int[] approvedShares = {100, 20, 30};
        double[] approvedAmounts = {3000.0, 2750.0, 3000.0};
        
        for (int i = 0; i < approvedCompanies.length; i++) {
            Company company = new Company();
            company.setName(approvedCompanies[i]);
            Document doc = new Document();
            customer.createApplication(company, approvedShares[i], approvedAmounts[i], doc);
        }
        
        // Create pending applications
        String[] pendingCompanies = {"NanoTech", "RoboWorks"};
        int[] pendingShares = {10, 50};
        double[] pendingAmounts = {600.0, 600.0};
        
        for (int i = 0; i < pendingCompanies.length; i++) {
            Company company = new Company();
            company.setName(pendingCompanies[i]);
            Document doc = new Document();
            customer.createApplication(company, pendingShares[i], pendingAmounts[i], doc);
        }
        
        // Manually set the first 3 applications to APPROVAL status (keep last 2 as PENDING)
        List<Application> applications = customer.getApplications();
        for (int i = 0; i < 3; i++) {
            applications.get(i).setStatus(ApplicationStatus.APPROVAL);
        }
        
        // Test: Customer asks for aggregate approved spending
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 8,750
        assertEquals(8750.0, result, 0.001);
    }
}