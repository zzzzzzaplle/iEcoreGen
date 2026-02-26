import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private BankSystem bankSystem;
    
    @Before
    public void setUp() {
        // Initialize BankSystem before each test
        bankSystem = new BankSystem();
    }
    
    @Test
    public void testCase1_NoApprovedRequests() {
        // Create customer C201 with no approved applications
        Customer customer = new Customer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setEligibleForIPO(true);
        
        // Create pending application
        IPOApplication pendingApp = new IPOApplication();
        pendingApp.setCustomer(customer);
        pendingApp.setCompany("TechInc");
        pendingApp.setNumberOfShares(10);
        pendingApp.setAmount(1500.0);
        pendingApp.setDocument("QT-3001");
        pendingApp.setReviewed(false);
        
        // Create rejected application
        IPOApplication rejectedApp = new IPOApplication();
        rejectedApp.setCustomer(customer);
        rejectedApp.setCompany("BioMed");
        rejectedApp.setNumberOfShares(10);
        rejectedApp.setAmount(2000.0);
        rejectedApp.setDocument("QT-3002");
        rejectedApp.setRejected(true);
        rejectedApp.setReviewed(true);
        
        // Add applications to customer
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(pendingApp);
        applications.add(rejectedApp);
        customer.setApplications(applications);
        
        // Test total approved amount - should be 0
        double result = bankSystem.getTotalApprovedAmount(customer);
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SingleApproval() {
        // Create customer C202 with one approved application
        Customer customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setEligibleForIPO(true);
        
        // Create approved application
        IPOApplication approvedApp = new IPOApplication();
        approvedApp.setCustomer(customer);
        approvedApp.setCompany("SolarMax");
        approvedApp.setNumberOfShares(84);
        approvedApp.setAmount(4200.0);
        approvedApp.setDocument("SM-2024-Q1");
        approvedApp.setApproved(true);
        approvedApp.setReviewed(true);
        
        // Add application to customer
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(approvedApp);
        customer.setApplications(applications);
        
        // Test total approved amount - should be 4200
        double result = bankSystem.getTotalApprovedAmount(customer);
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Create customer C203 with multiple approved applications
        Customer customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setEligibleForIPO(true);
        
        // Create first approved application
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompany("QuantumTech");
        app1.setNumberOfShares(40);
        app1.setAmount(2000.0);
        app1.setDocument("SM-2024-Q3004");
        app1.setApproved(true);
        app1.setReviewed(true);
        
        // Create second approved application
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompany("Neuralink");
        app2.setNumberOfShares(70);
        app2.setAmount(3500.0);
        app2.setDocument("SM-2024-Q3005");
        app2.setApproved(true);
        app2.setReviewed(true);
        
        // Add applications to customer
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        customer.setApplications(applications);
        
        // Test total approved amount - should be 5500 (2000 + 3500)
        double result = bankSystem.getTotalApprovedAmount(customer);
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Create customer C204 with large portfolio of approved applications
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setEligibleForIPO(true);
        
        // Create multiple approved applications, each worth $10,000
        List<IPOApplication> applications = new ArrayList<>();
        
        String[] companies = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        int[] shares = {200, 250, 125, 500, 200};
        String[] documents = {"SM-3006", "SM-3007", "SM-3008", "SM-3009", "SM-3010"};
        
        for (int i = 0; i < companies.length; i++) {
            IPOApplication app = new IPOApplication();
            app.setCustomer(customer);
            app.setCompany(companies[i]);
            app.setNumberOfShares(shares[i]);
            app.setAmount(10000.0);
            app.setDocument(documents[i]);
            app.setApproved(true);
            app.setReviewed(true);
            applications.add(app);
        }
        
        customer.setApplications(applications);
        
        // Test total approved amount - should be 50000 (5 * 10000)
        double result = bankSystem.getTotalApprovedAmount(customer);
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Create customer C205 with approved and pending applications
        Customer customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setEligibleForIPO(true);
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // Create approved applications
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompany("CloudServ");
        app1.setNumberOfShares(100);
        app1.setAmount(3000.0);
        app1.setDocument("SM-3011");
        app1.setApproved(true);
        app1.setReviewed(true);
        
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompany("DataCore");
        app2.setNumberOfShares(20);
        app2.setAmount(2750.0);
        app2.setDocument("SM-3012");
        app2.setApproved(true);
        app2.setReviewed(true);
        
        IPOApplication app3 = new IPOApplication();
        app3.setCustomer(customer);
        app3.setCompany("AI Ventures");
        app3.setNumberOfShares(30);
        app3.setAmount(3000.0);
        app3.setDocument("SM-3013");
        app3.setApproved(true);
        app3.setReviewed(true);
        
        // Create pending applications (should not be counted)
        IPOApplication pending1 = new IPOApplication();
        pending1.setCustomer(customer);
        pending1.setCompany("NanoTech");
        pending1.setNumberOfShares(10);
        pending1.setAmount(600.0);
        pending1.setDocument("SM-3014");
        pending1.setReviewed(false);
        
        IPOApplication pending2 = new IPOApplication();
        pending2.setCustomer(customer);
        pending2.setCompany("RoboWorks");
        pending2.setNumberOfShares(50);
        pending2.setAmount(600.0);
        pending2.setDocument("SM-3015");
        pending2.setReviewed(false);
        
        // Add all applications to customer
        applications.add(app1);
        applications.add(app2);
        applications.add(app3);
        applications.add(pending1);
        applications.add(pending2);
        customer.setApplications(applications);
        
        // Test total approved amount - should be 8750 (3000 + 2750 + 3000)
        double result = bankSystem.getTotalApprovedAmount(customer);
        assertEquals(8750.0, result, 0.001);
    }
}