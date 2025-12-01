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
    public void testCase1_noApprovedRequests() {
        // Setup: Customer "C201" with PENDING and REJECTED applications only
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephoneNumber("555-1212");
        
        // Create PENDING application
        IPOApplication pendingApp = new IPOApplication();
        pendingApp.setCompanyName("TechInc");
        pendingApp.setNumberOfShares(10);
        pendingApp.setAmount(1500.0);
        pendingApp.setDocument("QT-3001");
        pendingApp.setStatus(IPOApplication.Status.PENDING);
        
        // Create REJECTED application
        IPOApplication rejectedApp = new IPOApplication();
        rejectedApp.setCompanyName("BioMed");
        rejectedApp.setNumberOfShares(10);
        rejectedApp.setAmount(2000.0);
        rejectedApp.setDocument("QT-3002");
        rejectedApp.setStatus(IPOApplication.Status.REJECTED);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(pendingApp);
        applications.add(rejectedApp);
        customer.setApplications(applications);
        
        // Test: Query total approval amount
        double result = customer.queryTotalApprovalAmount();
        
        // Verify: No approved requests should return 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_singleApproval() {
        // Setup: Customer "C202" with one APPROVED application
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephoneNumber("555-2323");
        
        // Create APPROVED application
        IPOApplication approvedApp = new IPOApplication();
        approvedApp.setCompanyName("SolarMax");
        approvedApp.setNumberOfShares(84);
        approvedApp.setAmount(4200.0);
        approvedApp.setDocument("SM-2024-Q1");
        approvedApp.setStatus(IPOApplication.Status.APPROVED);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(approvedApp);
        customer.setApplications(applications);
        
        // Test: Query total approval amount
        double result = customer.queryTotalApprovalAmount();
        
        // Verify: Single approval should return 4200
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with multiple APPROVED applications
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephoneNumber("555-3434");
        
        // Create first APPROVED application
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("QuantumTech");
        app1.setNumberOfShares(40);
        app1.setAmount(2000.0);
        app1.setDocument("SM-2024-Q3004");
        app1.setStatus(IPOApplication.Status.APPROVED);
        
        // Create second APPROVED application
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("Neuralink");
        app2.setNumberOfShares(70);
        app2.setAmount(3500.0);
        app2.setDocument("SM-2024-Q3005");
        app2.setStatus(IPOApplication.Status.APPROVED);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        customer.setApplications(applications);
        
        // Test: Query total approval amount
        double result = customer.queryTotalApprovalAmount();
        
        // Verify: Multiple approvals should return sum (5500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_largePortfolio() {
        // Setup: Customer "C204" with large portfolio of APPROVED applications
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephoneNumber("555-4545");
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // Create multiple APPROVED applications, each with $10,000
        String[] companies = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        String[] documents = {"SM-3006", "SM-3007", "SM-3008", "SM-3009", "SM-3010"};
        int[] shares = {200, 250, 125, 500, 200};
        
        for (int i = 0; i < companies.length; i++) {
            IPOApplication app = new IPOApplication();
            app.setCompanyName(companies[i]);
            app.setNumberOfShares(shares[i]);
            app.setAmount(10000.0);
            app.setDocument(documents[i]);
            app.setStatus(IPOApplication.Status.APPROVED);
            applications.add(app);
        }
        
        customer.setApplications(applications);
        
        // Test: Query total approval amount
        double result = customer.queryTotalApprovalAmount();
        
        // Verify: Large portfolio should return 50000 (5 * 10000)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup: Customer "C205" with mix of APPROVED and PENDING applications
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephoneNumber("555-5656");
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // Create APPROVED applications
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("CloudServ");
        app1.setNumberOfShares(100);
        app1.setAmount(3000.0);
        app1.setDocument("SM-3011");
        app1.setStatus(IPOApplication.Status.APPROVED);
        
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("DataCore");
        app2.setNumberOfShares(20);
        app2.setAmount(2750.0);
        app2.setDocument("SM-3012");
        app2.setStatus(IPOApplication.Status.APPROVED);
        
        IPOApplication app3 = new IPOApplication();
        app3.setCompanyName("AI Ventures");
        app3.setNumberOfShares(30);
        app3.setAmount(3000.0);
        app3.setDocument("SM-3013");
        app3.setStatus(IPOApplication.Status.APPROVED);
        
        // Create PENDING applications
        IPOApplication app4 = new IPOApplication();
        app4.setCompanyName("NanoTech");
        app4.setNumberOfShares(10);
        app4.setAmount(600.0);
        app4.setDocument("SM-3014");
        app4.setStatus(IPOApplication.Status.PENDING);
        
        IPOApplication app5 = new IPOApplication();
        app5.setCompanyName("RoboWorks");
        app5.setNumberOfShares(50);
        app5.setAmount(600.0);
        app5.setDocument("SM-3015");
        app5.setStatus(IPOApplication.Status.PENDING);
        
        applications.add(app1);
        applications.add(app2);
        applications.add(app3);
        applications.add(app4);
        applications.add(app5);
        customer.setApplications(applications);
        
        // Test: Query total approval amount
        double result = customer.queryTotalApprovalAmount();
        
        // Verify: Should return sum of only APPROVED applications (8750)
        assertEquals(8750.0, result, 0.001);
    }
}