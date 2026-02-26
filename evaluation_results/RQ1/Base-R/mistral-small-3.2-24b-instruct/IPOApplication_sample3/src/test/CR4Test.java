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
        // Setup: Customer "C201" with pending and rejected applications
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        
        // Create pending application
        IpoApplication pendingApp = new IpoApplication();
        pendingApp.setCompanyName("TechInc");
        pendingApp.setShares(10);
        pendingApp.setAmount(1500.0);
        pendingApp.setDocument("QT-3001");
        // Status remains pending (approved=false, rejected=false)
        
        // Create rejected application
        IpoApplication rejectedApp = new IpoApplication();
        rejectedApp.setCompanyName("BioMed");
        rejectedApp.setShares(10);
        rejectedApp.setAmount(2000.0);
        rejectedApp.setDocument("QT-3002");
        rejectedApp.setRejected(true);
        
        // Add applications to customer
        List<IpoApplication> applications = new ArrayList<>();
        applications.add(pendingApp);
        applications.add(rejectedApp);
        customer.setApplications(applications);
        
        // Execute: Query total approved amount
        double result = customer.getTotalApprovedAmount();
        
        // Verify: No approved applications, so result should be 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_singleApproval() {
        // Setup: Customer "C202" with one approved application
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        
        // Create approved application
        IpoApplication approvedApp = new IpoApplication();
        approvedApp.setCompanyName("SolarMax");
        approvedApp.setShares(84);
        approvedApp.setAmount(4200.0);
        approvedApp.setDocument("SM-2024-Q1");
        approvedApp.setApproved(true);
        
        // Add application to customer
        List<IpoApplication> applications = new ArrayList<>();
        applications.add(approvedApp);
        customer.setApplications(applications);
        
        // Execute: Query total approved amount
        double result = customer.getTotalApprovedAmount();
        
        // Verify: Single approved application amount should be 4200
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with two approved applications
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        
        // Create first approved application
        IpoApplication app1 = new IpoApplication();
        app1.setCompanyName("QuantumTech");
        app1.setShares(40);
        app1.setAmount(2000.0);
        app1.setDocument("SM-2024-Q3004");
        app1.setApproved(true);
        
        // Create second approved application
        IpoApplication app2 = new IpoApplication();
        app2.setCompanyName("Neuralink");
        app2.setShares(70);
        app2.setAmount(3500.0);
        app2.setDocument("SM-2024-Q3005");
        app2.setApproved(true);
        
        // Add applications to customer
        List<IpoApplication> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        customer.setApplications(applications);
        
        // Execute: Query total approved amount
        double result = customer.getTotalApprovedAmount();
        
        // Verify: Sum of both approved applications should be 5500
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_largePortfolio() {
        // Setup: Customer "C204" with five approved applications of $10,000 each
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        
        List<IpoApplication> applications = new ArrayList<>();
        
        // Create 5 approved applications, each with $10,000
        String[] companies = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        int[] shares = {200, 250, 125, 500, 200};
        String[] documents = {"SM-3006", "SM-3007", "SM-3008", "SM-3009", "SM-3010"};
        
        for (int i = 0; i < companies.length; i++) {
            IpoApplication app = new IpoApplication();
            app.setCompanyName(companies[i]);
            app.setShares(shares[i]);
            app.setAmount(10000.0);
            app.setDocument(documents[i]);
            app.setApproved(true);
            applications.add(app);
        }
        
        customer.setApplications(applications);
        
        // Execute: Query total approved amount
        double result = customer.getTotalApprovedAmount();
        
        // Verify: 5 applications × $10,000 each = $50,000
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup: Customer "C205" with 3 approved and 2 pending applications
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        
        List<IpoApplication> applications = new ArrayList<>();
        
        // Create approved applications
        IpoApplication app1 = new IpoApplication();
        app1.setCompanyName("CloudServ");
        app1.setShares(100);
        app1.setAmount(3000.0);
        app1.setDocument("SM-3011");
        app1.setApproved(true);
        
        IpoApplication app2 = new IpoApplication();
        app2.setCompanyName("DataCore");
        app2.setShares(20);
        app2.setAmount(2750.0);
        app2.setDocument("SM-3012");
        app2.setApproved(true);
        
        IpoApplication app3 = new IpoApplication();
        app3.setCompanyName("AI Ventures");
        app3.setShares(30);
        app3.setAmount(3000.0);
        app3.setDocument("SM-3013");
        app3.setApproved(true);
        
        // Create pending applications (not approved or rejected)
        IpoApplication pending1 = new IpoApplication();
        pending1.setCompanyName("NanoTech");
        pending1.setShares(10);
        pending1.setAmount(600.0);
        pending1.setDocument("SM-3014");
        
        IpoApplication pending2 = new IpoApplication();
        pending2.setCompanyName("RoboWorks");
        pending2.setShares(50);
        pending2.setAmount(600.0);
        pending2.setDocument("SM-3015");
        
        // Add all applications to customer
        applications.add(app1);
        applications.add(app2);
        applications.add(app3);
        applications.add(pending1);
        applications.add(pending2);
        customer.setApplications(applications);
        
        // Execute: Query total approved amount
        double result = customer.getTotalApprovedAmount();
        
        // Verify: Only approved applications should be counted (3000 + 2750 + 3000 = 8750)
        assertEquals(8750.0, result, 0.001);
    }
}