import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private IPOApplicationService service;
    
    @Before
    public void setUp() {
        service = new IPOApplicationService();
    }
    
    @Test
    public void testCase1_NoApprovedRequests() {
        // Setup: Customer "C201" with pending and rejected applications
        Customer customer = new Customer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephoneNumber("555-1212");
        
        // Create pending application
        IPOApplication pendingApp = new IPOApplication();
        pendingApp.setCustomer(customer);
        pendingApp.setCompanyName("TechInc");
        pendingApp.setNumberOfShares(10);
        pendingApp.setAmount(1500.0);
        pendingApp.setDocument("QT-3001");
        pendingApp.setStatus(ApplicationStatus.PENDING);
        
        // Create rejected application
        IPOApplication rejectedApp = new IPOApplication();
        rejectedApp.setCustomer(customer);
        rejectedApp.setCompanyName("BioMed");
        rejectedApp.setNumberOfShares(10);
        rejectedApp.setAmount(2000.0);
        rejectedApp.setDocument("QT-3002");
        rejectedApp.setStatus(ApplicationStatus.REJECTED);
        
        // Add applications to customer
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(pendingApp);
        applications.add(rejectedApp);
        customer.setApplications(applications);
        
        // Test: Query total approval amount
        double result = service.queryTotalApprovalAmount(customer);
        
        // Verify: Should return 0 since no approved applications
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SingleApproval() {
        // Setup: Customer "C202" with one approved application
        Customer customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephoneNumber("555-2323");
        
        // Create approved application
        IPOApplication approvedApp = new IPOApplication();
        approvedApp.setCustomer(customer);
        approvedApp.setCompanyName("SolarMax");
        approvedApp.setNumberOfShares(84);
        approvedApp.setAmount(4200.0);
        approvedApp.setDocument("SM-2024-Q1");
        approvedApp.setStatus(ApplicationStatus.APPROVED);
        
        // Add application to customer
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(approvedApp);
        customer.setApplications(applications);
        
        // Test: Query total approval amount
        double result = service.queryTotalApprovalAmount(customer);
        
        // Verify: Should return 4200.0
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with multiple approved applications
        Customer customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephoneNumber("555-3434");
        
        // Create first approved application
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompanyName("QuantumTech");
        app1.setNumberOfShares(40);
        app1.setAmount(2000.0);
        app1.setDocument("SM-2024-Q3004");
        app1.setStatus(ApplicationStatus.APPROVED);
        
        // Create second approved application
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompanyName("Neuralink");
        app2.setNumberOfShares(70);
        app2.setAmount(3500.0);
        app2.setDocument("SM-2024-Q3005");
        app2.setStatus(ApplicationStatus.APPROVED);
        
        // Add applications to customer
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        customer.setApplications(applications);
        
        // Test: Query total approval amount
        double result = service.queryTotalApprovalAmount(customer);
        
        // Verify: Should return 5500.0 (2000 + 3500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup: Customer "C204" with multiple approved applications, each $10,000
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephoneNumber("555-4545");
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // Create 5 approved applications, each with $10,000 amount
        String[] companies = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        String[] documents = {"SM-3006", "SM-3007", "SM-3008", "SM-3009", "SM-3010"};
        int[] shares = {200, 250, 125, 500, 200};
        
        for (int i = 0; i < companies.length; i++) {
            IPOApplication app = new IPOApplication();
            app.setCustomer(customer);
            app.setCompanyName(companies[i]);
            app.setNumberOfShares(shares[i]);
            app.setAmount(10000.0);
            app.setDocument(documents[i]);
            app.setStatus(ApplicationStatus.APPROVED);
            applications.add(app);
        }
        
        customer.setApplications(applications);
        
        // Test: Query total approval amount
        double result = service.queryTotalApprovalAmount(customer);
        
        // Verify: Should return 50000.0 (5 * 10000)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup: Customer "C205" with approved and pending applications
        Customer customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephoneNumber("555-5656");
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // Create approved applications
        String[] approvedCompanies = {"CloudServ", "DataCore", "AI Ventures"};
        double[] approvedAmounts = {3000.0, 2750.0, 3000.0};
        int[] approvedShares = {100, 20, 30};
        String[] approvedDocs = {"SM-3011", "SM-3012", "SM-3013"};
        
        for (int i = 0; i < approvedCompanies.length; i++) {
            IPOApplication app = new IPOApplication();
            app.setCustomer(customer);
            app.setCompanyName(approvedCompanies[i]);
            app.setNumberOfShares(approvedShares[i]);
            app.setAmount(approvedAmounts[i]);
            app.setDocument(approvedDocs[i]);
            app.setStatus(ApplicationStatus.APPROVED);
            applications.add(app);
        }
        
        // Create pending applications
        String[] pendingCompanies = {"NanoTech", "RoboWorks"};
        double[] pendingAmounts = {600.0, 600.0};
        int[] pendingShares = {10, 50};
        String[] pendingDocs = {"SM-3014", "SM-3015"};
        
        for (int i = 0; i < pendingCompanies.length; i++) {
            IPOApplication app = new IPOApplication();
            app.setCustomer(customer);
            app.setCompanyName(pendingCompanies[i]);
            app.setNumberOfShares(pendingShares[i]);
            app.setAmount(pendingAmounts[i]);
            app.setDocument(pendingDocs[i]);
            app.setStatus(ApplicationStatus.PENDING);
            applications.add(app);
        }
        
        customer.setApplications(applications);
        
        // Test: Query total approval amount
        double result = service.queryTotalApprovalAmount(customer);
        
        // Verify: Should return 8750.0 (3000 + 2750 + 3000)
        assertEquals(8750.0, result, 0.001);
    }
}