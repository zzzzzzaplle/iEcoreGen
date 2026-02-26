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
        // Setup: Customer "C201" with pending and rejected applications
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);
        
        // Create pending application
        Company techInc = new Company();
        techInc.setName("TechInc");
        Application pendingApp = new Application();
        pendingApp.setCompany(techInc);
        pendingApp.setShare(10);
        pendingApp.setAmountOfMoney(1500.0);
        pendingApp.setStatus(ApplicationStatus.PENDING);
        
        // Create rejected application
        Company bioMed = new Company();
        bioMed.setName("BioMed");
        Application rejectedApp = new Application();
        rejectedApp.setCompany(bioMed);
        rejectedApp.setShare(10);
        rejectedApp.setAmountOfMoney(2000.0);
        rejectedApp.setStatus(ApplicationStatus.REJECTED);
        
        // Add applications to customer
        List<Application> applications = new ArrayList<>();
        applications.add(pendingApp);
        applications.add(rejectedApp);
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 0 since no approved applications
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SingleApproval() {
        // Setup: Customer "C202" with one approved application
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        
        // Create approved application
        Company solarMax = new Company();
        solarMax.setName("SolarMax");
        solarMax.setEmail("solarmax@gmail.com");
        Application approvedApp = new Application();
        approvedApp.setCompany(solarMax);
        approvedApp.setShare(84);
        approvedApp.setAmountOfMoney(4200.0);
        approvedApp.setStatus(ApplicationStatus.APPROVAL);
        
        // Add application to customer
        List<Application> applications = new ArrayList<>();
        applications.add(approvedApp);
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 4200.0
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with two approved applications
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        
        // Create first approved application
        Company quantumTech = new Company();
        quantumTech.setName("QuantumTech");
        Application app1 = new Application();
        app1.setCompany(quantumTech);
        app1.setShare(40);
        app1.setAmountOfMoney(2000.0);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        // Create second approved application
        Company neuralink = new Company();
        neuralink.setName("Neuralink");
        Application app2 = new Application();
        app2.setCompany(neuralink);
        app2.setShare(70);
        app2.setAmountOfMoney(3500.0);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        // Add applications to customer
        List<Application> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 5500.0 (2000 + 3500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup: Customer "C204" with five approved applications
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        
        List<Application> applications = new ArrayList<>();
        
        // Create 5 approved applications, each with $10,000
        String[] companyNames = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        int[] shares = {200, 250, 125, 500, 200};
        
        for (int i = 0; i < companyNames.length; i++) {
            Company company = new Company();
            company.setName(companyNames[i]);
            
            Application app = new Application();
            app.setCompany(company);
            app.setShare(shares[i]);
            app.setAmountOfMoney(10000.0);
            app.setStatus(ApplicationStatus.APPROVAL);
            
            applications.add(app);
        }
        
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 50000.0 (5 * 10000)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup: Customer "C205" with 3 approved and 2 pending applications
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        
        List<Application> applications = new ArrayList<>();
        
        // Create approved applications
        String[] approvedCompanies = {"CloudServ", "DataCore", "AI Ventures"};
        int[] approvedShares = {100, 20, 30};
        double[] approvedAmounts = {3000.0, 2750.0, 3000.0};
        
        for (int i = 0; i < approvedCompanies.length; i++) {
            Company company = new Company();
            company.setName(approvedCompanies[i]);
            
            Application app = new Application();
            app.setCompany(company);
            app.setShare(approvedShares[i]);
            app.setAmountOfMoney(approvedAmounts[i]);
            app.setStatus(ApplicationStatus.APPROVAL);
            
            applications.add(app);
        }
        
        // Create pending applications
        String[] pendingCompanies = {"NanoTech", "RoboWorks"};
        int[] pendingShares = {10, 50};
        double[] pendingAmounts = {600.0, 600.0};
        
        for (int i = 0; i < pendingCompanies.length; i++) {
            Company company = new Company();
            company.setName(pendingCompanies[i]);
            
            Application app = new Application();
            app.setCompany(company);
            app.setShare(pendingShares[i]);
            app.setAmountOfMoney(pendingAmounts[i]);
            app.setStatus(ApplicationStatus.PENDING);
            
            applications.add(app);
        }
        
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 8750.0 (3000 + 2750 + 3000) - pending applications should be ignored
        assertEquals(8750.0, result, 0.001);
    }
}