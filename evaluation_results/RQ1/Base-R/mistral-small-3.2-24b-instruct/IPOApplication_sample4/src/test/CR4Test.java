import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    @Test
    public void testCase1_NoApprovedRequests() {
        // Setup: Create customer with pending and rejected applications
        Customer customer = new Customer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        
        // Create pending application
        IPOApplication pendingApp = new IPOApplication();
        pendingApp.setCustomer(customer);
        pendingApp.setCompanyName("TechInc");
        pendingApp.setNumberOfShares(10);
        pendingApp.setAmount(1500.0);
        pendingApp.setDocument("QT-3001");
        pendingApp.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(pendingApp);
        
        // Create rejected application
        IPOApplication rejectedApp = new IPOApplication();
        rejectedApp.setCustomer(customer);
        rejectedApp.setCompanyName("BioMed");
        rejectedApp.setNumberOfShares(10);
        rejectedApp.setAmount(2000.0);
        rejectedApp.setDocument("QT-3002");
        rejectedApp.setStatus(ApplicationStatus.REJECTED);
        customer.getApplications().add(rejectedApp);
        
        // Test: Query total approved amount
        double result = IPOApplication.getTotalApprovedAmount(customer);
        
        // Verify: Should return 0 since no approved applications
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SingleApproval() {
        // Setup: Create customer with one approved application
        Customer customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        
        // Create approved application
        IPOApplication approvedApp = new IPOApplication();
        approvedApp.setCustomer(customer);
        approvedApp.setCompanyName("SolarMax");
        approvedApp.setNumberOfShares(84);
        approvedApp.setAmount(4200.0);
        approvedApp.setDocument("SM-2024-Q1");
        approvedApp.setStatus(ApplicationStatus.APPROVED);
        customer.getApplications().add(approvedApp);
        
        // Test: Query total approved amount
        double result = IPOApplication.getTotalApprovedAmount(customer);
        
        // Verify: Should return 4200.0 for the single approved application
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup: Create customer with multiple approved applications
        Customer customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        
        // Create first approved application
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompanyName("QuantumTech");
        app1.setNumberOfShares(40);
        app1.setAmount(2000.0);
        app1.setDocument("SM-2024-Q3004");
        app1.setStatus(ApplicationStatus.APPROVED);
        customer.getApplications().add(app1);
        
        // Create second approved application
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompanyName("Neuralink");
        app2.setNumberOfShares(70);
        app2.setAmount(3500.0);
        app2.setDocument("SM-2024-Q3005");
        app2.setStatus(ApplicationStatus.APPROVED);
        customer.getApplications().add(app2);
        
        // Test: Query total approved amount
        double result = IPOApplication.getTotalApprovedAmount(customer);
        
        // Verify: Should return sum of both approved applications (2000 + 3500 = 5500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup: Create customer with 5 approved applications, each with $10,000
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        
        // Create 5 approved applications, each with $10,000
        String[] companies = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        int[] shares = {200, 250, 125, 500, 200};
        String[] documents = {"SM-3006", "SM-3007", "SM-3008", "SM-3009", "SM-3010"};
        
        for (int i = 0; i < companies.length; i++) {
            IPOApplication app = new IPOApplication();
            app.setCustomer(customer);
            app.setCompanyName(companies[i]);
            app.setNumberOfShares(shares[i]);
            app.setAmount(10000.0);
            app.setDocument(documents[i]);
            app.setStatus(ApplicationStatus.APPROVED);
            customer.getApplications().add(app);
        }
        
        // Test: Query total approved amount
        double result = IPOApplication.getTotalApprovedAmount(customer);
        
        // Verify: Should return 50,000 (5 applications × 10,000 each)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup: Create customer with approved and pending applications
        Customer customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        
        // Create approved applications
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompanyName("CloudServ");
        app1.setNumberOfShares(100);
        app1.setAmount(3000.0);
        app1.setDocument("SM-3011");
        app1.setStatus(ApplicationStatus.APPROVED);
        customer.getApplications().add(app1);
        
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompanyName("DataCore");
        app2.setNumberOfShares(20);
        app2.setAmount(2750.0);
        app2.setDocument("SM-3012");
        app2.setStatus(ApplicationStatus.APPROVED);
        customer.getApplications().add(app2);
        
        IPOApplication app3 = new IPOApplication();
        app3.setCustomer(customer);
        app3.setCompanyName("AI Ventures");
        app3.setNumberOfShares(30);
        app3.setAmount(3000.0);
        app3.setDocument("SM-3013");
        app3.setStatus(ApplicationStatus.APPROVED);
        customer.getApplications().add(app3);
        
        // Create pending applications (should not be counted)
        IPOApplication pending1 = new IPOApplication();
        pending1.setCustomer(customer);
        pending1.setCompanyName("NanoTech");
        pending1.setNumberOfShares(10);
        pending1.setAmount(600.0);
        pending1.setDocument("SM-3014");
        pending1.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(pending1);
        
        IPOApplication pending2 = new IPOApplication();
        pending2.setCustomer(customer);
        pending2.setCompanyName("RoboWorks");
        pending2.setNumberOfShares(50);
        pending2.setAmount(600.0);
        pending2.setDocument("SM-3015");
        pending2.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(pending2);
        
        // Test: Query total approved amount
        double result = IPOApplication.getTotalApprovedAmount(customer);
        
        // Verify: Should return sum of approved applications only (3000 + 2750 + 3000 = 8750)
        assertEquals(8750.0, result, 0.001);
    }
}