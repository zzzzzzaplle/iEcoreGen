import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private Bank bank;
    
    @Before
    public void setUp() {
        bank = new Bank();
    }
    
    @Test
    public void testCase1_NoApprovedRequests() {
        // Setup: Create customer C201
        Customer customer = new Customer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setEligible(true);
        
        // Setup: Add pending application
        IpoApplication pendingApp = new IpoApplication();
        pendingApp.setCompany("TechInc");
        pendingApp.setNumberOfShares(10);
        pendingApp.setAmount(1500.0);
        pendingApp.setDocument("QT-3001");
        pendingApp.setApproved(false);
        pendingApp.setRejected(false);
        pendingApp.setCustomer(customer);
        customer.getApplications().add(pendingApp);
        
        // Setup: Add rejected application
        IpoApplication rejectedApp = new IpoApplication();
        rejectedApp.setCompany("BioMed");
        rejectedApp.setNumberOfShares(10);
        rejectedApp.setAmount(2000.0);
        rejectedApp.setDocument("QT-3002");
        rejectedApp.setApproved(false);
        rejectedApp.setRejected(true);
        rejectedApp.setCustomer(customer);
        customer.getApplications().add(rejectedApp);
        
        // Test: Get total approved amount
        double result = customer.getTotalApprovedAmount();
        
        // Verify: Should return 0 since no approved applications
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SingleApproval() {
        // Setup: Create customer C202
        Customer customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        
        // Setup: Add approved application
        IpoApplication approvedApp = new IpoApplication();
        approvedApp.setCompany("SolarMax");
        approvedApp.setNumberOfShares(84);
        approvedApp.setAmount(4200.0);
        approvedApp.setDocument("SM-2024-Q1");
        approvedApp.setApproved(true);
        approvedApp.setRejected(false);
        approvedApp.setCustomer(customer);
        customer.getApplications().add(approvedApp);
        
        // Test: Get total approved amount
        double result = customer.getTotalApprovedAmount();
        
        // Verify: Should return 4200.0
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup: Create customer C203
        Customer customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        
        // Setup: Add first approved application
        IpoApplication app1 = new IpoApplication();
        app1.setCompany("QuantumTech");
        app1.setNumberOfShares(40);
        app1.setAmount(2000.0);
        app1.setDocument("SM-2024-Q3004");
        app1.setApproved(true);
        app1.setRejected(false);
        app1.setCustomer(customer);
        customer.getApplications().add(app1);
        
        // Setup: Add second approved application
        IpoApplication app2 = new IpoApplication();
        app2.setCompany("Neuralink");
        app2.setNumberOfShares(70);
        app2.setAmount(3500.0);
        app2.setDocument("SM-2024-Q3005");
        app2.setApproved(true);
        app2.setRejected(false);
        app2.setCustomer(customer);
        customer.getApplications().add(app2);
        
        // Test: Get total approved amount
        double result = customer.getTotalApprovedAmount();
        
        // Verify: Should return 5500.0 (2000 + 3500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup: Create customer C204
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        
        // Setup: Add 5 approved applications, each with $10,000
        String[] companies = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        int[] shares = {200, 250, 125, 500, 200};
        String[] documents = {"SM-3006", "SM-3007", "SM-3008", "SM-3009", "SM-3010"};
        
        for (int i = 0; i < companies.length; i++) {
            IpoApplication app = new IpoApplication();
            app.setCompany(companies[i]);
            app.setNumberOfShares(shares[i]);
            app.setAmount(10000.0);
            app.setDocument(documents[i]);
            app.setApproved(true);
            app.setRejected(false);
            app.setCustomer(customer);
            customer.getApplications().add(app);
        }
        
        // Test: Get total approved amount
        double result = customer.getTotalApprovedAmount();
        
        // Verify: Should return 50000.0 (5 * 10000)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup: Create customer C205
        Customer customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        
        // Setup: Add approved applications
        IpoApplication app1 = new IpoApplication();
        app1.setCompany("CloudServ");
        app1.setNumberOfShares(100);
        app1.setAmount(3000.0);
        app1.setDocument("SM-3011");
        app1.setApproved(true);
        app1.setRejected(false);
        app1.setCustomer(customer);
        customer.getApplications().add(app1);
        
        IpoApplication app2 = new IpoApplication();
        app2.setCompany("DataCore");
        app2.setNumberOfShares(20);
        app2.setAmount(2750.0);
        app2.setDocument("SM-3012");
        app2.setApproved(true);
        app2.setRejected(false);
        app2.setCustomer(customer);
        customer.getApplications().add(app2);
        
        IpoApplication app3 = new IpoApplication();
        app3.setCompany("AI Ventures");
        app3.setNumberOfShares(30);
        app3.setAmount(3000.0);
        app3.setDocument("SM-3013");
        app3.setApproved(true);
        app3.setRejected(false);
        app3.setCustomer(customer);
        customer.getApplications().add(app3);
        
        // Setup: Add pending applications (should not be counted)
        IpoApplication pending1 = new IpoApplication();
        pending1.setCompany("NanoTech");
        pending1.setNumberOfShares(10);
        pending1.setAmount(600.0);
        pending1.setDocument("SM-3014");
        pending1.setApproved(false);
        pending1.setRejected(false);
        pending1.setCustomer(customer);
        customer.getApplications().add(pending1);
        
        IpoApplication pending2 = new IpoApplication();
        pending2.setCompany("RoboWorks");
        pending2.setNumberOfShares(50);
        pending2.setAmount(600.0);
        pending2.setDocument("SM-3015");
        pending2.setApproved(false);
        pending2.setRejected(false);
        pending2.setCustomer(customer);
        customer.getApplications().add(pending2);
        
        // Test: Get total approved amount
        double result = customer.getTotalApprovedAmount();
        
        // Verify: Should return 8750.0 (3000 + 2750 + 3000)
        assertEquals(8750.0, result, 0.001);
    }
}