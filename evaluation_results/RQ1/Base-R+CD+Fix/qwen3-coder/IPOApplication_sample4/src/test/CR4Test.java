import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
        app1.setShare(10);
        app1.setAmountOfMoney(1500.0);
        app1.setCustomer(customer);
        app1.setCompany(techInc);
        app1.setAllowance(doc1);
        app1.setStatus(ApplicationStatus.PENDING);
        
        // Create rejected application
        Company bioMed = new Company();
        bioMed.setName("BioMed");
        Document doc2 = new Document();
        Application app2 = new Application();
        app2.setShare(10);
        app2.setAmountOfMoney(2000.0);
        app2.setCustomer(customer);
        app2.setCompany(bioMed);
        app2.setAllowance(doc2);
        app2.setStatus(ApplicationStatus.REJECTED);
        
        List<Application> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        customer.setApplications(applications);
        
        // Execute: Query total approved amount
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
        customer.setCanApplyForIPO(true);
        
        // Create approved application
        Company solarMax = new Company();
        solarMax.setName("SolarMax");
        solarMax.setEmail("solarmax@gmail.com");
        Document doc = new Document();
        Application app = new Application();
        app.setShare(84);
        app.setAmountOfMoney(4200.0);
        app.setCustomer(customer);
        app.setCompany(solarMax);
        app.setAllowance(doc);
        app.setStatus(ApplicationStatus.APPROVAL);
        
        List<Application> applications = new ArrayList<>();
        applications.add(app);
        customer.setApplications(applications);
        
        // Execute: Query total approved amount
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
        customer.setCanApplyForIPO(true);
        
        // Create first approved application
        Company quantumTech = new Company();
        quantumTech.setName("QuantumTech");
        Document doc1 = new Document();
        Application app1 = new Application();
        app1.setShare(40);
        app1.setAmountOfMoney(2000.0);
        app1.setCustomer(customer);
        app1.setCompany(quantumTech);
        app1.setAllowance(doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        // Create second approved application
        Company neuralink = new Company();
        neuralink.setName("Neuralink");
        Document doc2 = new Document();
        Application app2 = new Application();
        app2.setShare(70);
        app2.setAmountOfMoney(3500.0);
        app2.setCustomer(customer);
        app2.setCompany(neuralink);
        app2.setAllowance(doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        List<Application> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        customer.setApplications(applications);
        
        // Execute: Query total approved amount
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
        customer.setCanApplyForIPO(true);
        
        List<Application> applications = new ArrayList<>();
        
        // Create five approved applications, each with $10,000
        String[] companyNames = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        
        for (String companyName : companyNames) {
            Company company = new Company();
            company.setName(companyName);
            Document doc = new Document();
            Application app = new Application();
            app.setShare(100); // Share count doesn't affect the amount calculation
            app.setAmountOfMoney(10000.0);
            app.setCustomer(customer);
            app.setCompany(company);
            app.setAllowance(doc);
            app.setStatus(ApplicationStatus.APPROVAL);
            applications.add(app);
        }
        
        customer.setApplications(applications);
        
        // Execute: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 50000.0 (5 * 10000)
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
        
        List<Application> applications = new ArrayList<>();
        
        // Create approved applications
        String[] approvedCompanies = {"CloudServ", "DataCore", "AI Ventures"};
        double[] approvedAmounts = {3000.0, 2750.0, 3000.0};
        int[] approvedShares = {100, 20, 30};
        
        for (int i = 0; i < approvedCompanies.length; i++) {
            Company company = new Company();
            company.setName(approvedCompanies[i]);
            Document doc = new Document();
            Application app = new Application();
            app.setShare(approvedShares[i]);
            app.setAmountOfMoney(approvedAmounts[i]);
            app.setCustomer(customer);
            app.setCompany(company);
            app.setAllowance(doc);
            app.setStatus(ApplicationStatus.APPROVAL);
            applications.add(app);
        }
        
        // Create pending applications
        String[] pendingCompanies = {"NanoTech", "RoboWorks"};
        double[] pendingAmounts = {600.0, 600.0};
        int[] pendingShares = {10, 50};
        
        for (int i = 0; i < pendingCompanies.length; i++) {
            Company company = new Company();
            company.setName(pendingCompanies[i]);
            Document doc = new Document();
            Application app = new Application();
            app.setShare(pendingShares[i]);
            app.setAmountOfMoney(pendingAmounts[i]);
            app.setCustomer(customer);
            app.setCompany(company);
            app.setAllowance(doc);
            app.setStatus(ApplicationStatus.PENDING);
            applications.add(app);
        }
        
        customer.setApplications(applications);
        
        // Execute: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 8750.0 (3000 + 2750 + 3000)
        assertEquals(8750.0, result, 0.001);
    }
}