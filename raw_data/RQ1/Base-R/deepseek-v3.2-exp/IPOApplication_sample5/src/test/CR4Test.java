import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private BankSystem bankSystem;
    
    @Before
    public void setUp() {
        bankSystem = new BankSystem();
    }
    
    @Test
    public void testCase1_noApprovedRequests() {
        // Setup: Customer "C201" with pending and rejected applications
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
        pendingApp.setAmountPaid(1500.0);
        pendingApp.setDocument("QT-3001");
        pendingApp.setStatus(ApplicationStatus.PENDING);
        
        // Create rejected application
        IPOApplication rejectedApp = new IPOApplication();
        rejectedApp.setCustomer(customer);
        rejectedApp.setCompanyName("BioMed");
        rejectedApp.setNumberOfShares(10);
        rejectedApp.setAmountPaid(2000.0);
        rejectedApp.setDocument("QT-3002");
        rejectedApp.setStatus(ApplicationStatus.REJECTED);
        
        // Add applications to customer
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(pendingApp);
        applications.add(rejectedApp);
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = bankSystem.getTotalApprovedAmount(customer);
        
        // Verify: Should return 0 since no approved applications
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_singleApproval() {
        // Setup: Customer "C202" with one approved application
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
        approvedApp.setAmountPaid(4200.0);
        approvedApp.setDocument("SM-2024-Q1");
        approvedApp.setStatus(ApplicationStatus.APPROVED);
        
        // Add application to customer
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(approvedApp);
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = bankSystem.getTotalApprovedAmount(customer);
        
        // Verify: Should return 4200 (single approved amount)
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with multiple approved applications
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
        app1.setAmountPaid(2000.0);
        app1.setDocument("SM-2024-Q3004");
        app1.setStatus(ApplicationStatus.APPROVED);
        
        // Create second approved application
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompanyName("Neuralink");
        app2.setNumberOfShares(70);
        app2.setAmountPaid(3500.0);
        app2.setDocument("SM-2024-Q3005");
        app2.setStatus(ApplicationStatus.APPROVED);
        
        // Add applications to customer
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = bankSystem.getTotalApprovedAmount(customer);
        
        // Verify: Should return 5500 (2000 + 3500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_largePortfolio() {
        // Setup: Customer "C204" with multiple approved applications of $10,000 each
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // Create 5 approved applications, each with $10,000
        String[] companies = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        String[] documents = {"SM-3006", "SM-3007", "SM-3008", "SM-3009", "SM-3010"};
        int[] shares = {200, 250, 125, 500, 200};
        
        for (int i = 0; i < 5; i++) {
            IPOApplication app = new IPOApplication();
            app.setCustomer(customer);
            app.setCompanyName(companies[i]);
            app.setNumberOfShares(shares[i]);
            app.setAmountPaid(10000.0);
            app.setDocument(documents[i]);
            app.setStatus(ApplicationStatus.APPROVED);
            applications.add(app);
        }
        
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = bankSystem.getTotalApprovedAmount(customer);
        
        // Verify: Should return 50000 (5 * 10000)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup: Customer "C205" with approved and pending applications
        Customer customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // Approved applications
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompanyName("CloudServ");
        app1.setNumberOfShares(100);
        app1.setAmountPaid(3000.0);
        app1.setDocument("SM-3011");
        app1.setStatus(ApplicationStatus.APPROVED);
        applications.add(app1);
        
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompanyName("DataCore");
        app2.setNumberOfShares(20);
        app2.setAmountPaid(2750.0);
        app2.setDocument("SM-3012");
        app2.setStatus(ApplicationStatus.APPROVED);
        applications.add(app2);
        
        IPOApplication app3 = new IPOApplication();
        app3.setCustomer(customer);
        app3.setCompanyName("AI Ventures");
        app3.setNumberOfShares(30);
        app3.setAmountPaid(3000.0);
        app3.setDocument("SM-3013");
        app3.setStatus(ApplicationStatus.APPROVED);
        applications.add(app3);
        
        // Pending applications (should not be counted)
        IPOApplication pendingApp1 = new IPOApplication();
        pendingApp1.setCustomer(customer);
        pendingApp1.setCompanyName("NanoTech");
        pendingApp1.setNumberOfShares(10);
        pendingApp1.setAmountPaid(600.0);
        pendingApp1.setDocument("SM-3014");
        pendingApp1.setStatus(ApplicationStatus.PENDING);
        applications.add(pendingApp1);
        
        IPOApplication pendingApp2 = new IPOApplication();
        pendingApp2.setCustomer(customer);
        pendingApp2.setCompanyName("RoboWorks");
        pendingApp2.setNumberOfShares(50);
        pendingApp2.setAmountPaid(600.0);
        pendingApp2.setDocument("SM-3015");
        pendingApp2.setStatus(ApplicationStatus.PENDING);
        applications.add(pendingApp2);
        
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = bankSystem.getTotalApprovedAmount(customer);
        
        // Verify: Should return 8750 (3000 + 2750 + 3000), ignoring pending applications
        assertEquals(8750.0, result, 0.001);
    }
}