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
        customer = new Customer();
    }
    
    @Test
    public void testCase1_noApprovedRequests() {
        // Setup: Customer "C201" with pending and rejected applications
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setEligible(true);
        
        // Create pending application
        IPOApplication pendingApp = new IPOApplication();
        pendingApp.setCompanyName("TechInc");
        pendingApp.setNumberOfShares(10);
        pendingApp.setAmount(1500.0);
        pendingApp.setDocument("QT-3001");
        pendingApp.setStatus(ApplicationStatus.PENDING);
        
        // Create rejected application
        IPOApplication rejectedApp = new IPOApplication();
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
        
        // Test: Query total approved amount
        double result = customer.getTotalApprovedIPOAmount();
        
        // Verify: Should return 0 since no approved applications
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_singleApproval() {
        // Setup: Customer "C202" with one approved application
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setEligible(true);
        
        // Create approved application
        IPOApplication approvedApp = new IPOApplication();
        approvedApp.setCompanyName("SolarMax");
        approvedApp.setNumberOfShares(84);
        approvedApp.setAmount(4200.0);
        approvedApp.setDocument("SM-2024-Q1");
        approvedApp.setStatus(ApplicationStatus.APPROVED);
        
        // Add application to customer
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(approvedApp);
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = customer.getTotalApprovedIPOAmount();
        
        // Verify: Should return 4200.0
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with multiple approved applications
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setEligible(true);
        
        // Create first approved application
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("QuantumTech");
        app1.setNumberOfShares(40);
        app1.setAmount(2000.0);
        app1.setDocument("SM-2024-Q3004");
        app1.setStatus(ApplicationStatus.APPROVED);
        
        // Create second approved application
        IPOApplication app2 = new IPOApplication();
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
        
        // Test: Query total approved amount
        double result = customer.getTotalApprovedIPOAmount();
        
        // Verify: Should return 5500.0 (2000 + 3500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_largePortfolio() {
        // Setup: Customer "C204" with 5 approved applications, each $10,000
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setEligible(true);
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // Create 5 approved applications, each with $10,000
        String[] companies = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        int[] shares = {200, 250, 125, 500, 200};
        String[] documents = {"SM-3006", "SM-3007", "SM-3008", "SM-3009", "SM-3010"};
        
        for (int i = 0; i < companies.length; i++) {
            IPOApplication app = new IPOApplication();
            app.setCompanyName(companies[i]);
            app.setNumberOfShares(shares[i]);
            app.setAmount(10000.0);
            app.setDocument(documents[i]);
            app.setStatus(ApplicationStatus.APPROVED);
            applications.add(app);
        }
        
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = customer.getTotalApprovedIPOAmount();
        
        // Verify: Should return 50000.0 (5 * 10000)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup: Customer "C205" with approved and pending applications
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setEligible(true);
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // Create approved applications
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("CloudServ");
        app1.setNumberOfShares(100);
        app1.setAmount(3000.0);
        app1.setDocument("SM-3011");
        app1.setStatus(ApplicationStatus.APPROVED);
        
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("DataCore");
        app2.setNumberOfShares(20);
        app2.setAmount(2750.0);
        app2.setDocument("SM-3012");
        app2.setStatus(ApplicationStatus.APPROVED);
        
        IPOApplication app3 = new IPOApplication();
        app3.setCompanyName("AI Ventures");
        app3.setNumberOfShares(30);
        app3.setAmount(3000.0);
        app3.setDocument("SM-3013");
        app3.setStatus(ApplicationStatus.APPROVED);
        
        // Create pending applications (should not be counted)
        IPOApplication pending1 = new IPOApplication();
        pending1.setCompanyName("NanoTech");
        pending1.setNumberOfShares(10);
        pending1.setAmount(600.0);
        pending1.setDocument("SM-3014");
        pending1.setStatus(ApplicationStatus.PENDING);
        
        IPOApplication pending2 = new IPOApplication();
        pending2.setCompanyName("RoboWorks");
        pending2.setNumberOfShares(50);
        pending2.setAmount(600.0);
        pending2.setDocument("SM-3015");
        pending2.setStatus(ApplicationStatus.PENDING);
        
        // Add all applications to customer
        applications.add(app1);
        applications.add(app2);
        applications.add(app3);
        applications.add(pending1);
        applications.add(pending2);
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = customer.getTotalApprovedIPOAmount();
        
        // Verify: Should return 8750.0 (3000 + 2750 + 3000)
        assertEquals(8750.0, result, 0.001);
    }
}