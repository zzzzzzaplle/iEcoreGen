import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Bank bank;
    
    @Before
    public void setUp() {
        bank = new Bank();
    }
    
    @Test
    public void testCase1_noApprovedRequests() {
        // Setup: Create customer C201 with pending and rejected applications
        Customer customer = new Customer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephoneNumber("555-1212");
        customer.setRestricted(false);
        
        // Create pending application
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompanyName("TechInc");
        app1.setNumberOfShares(10);
        app1.setAmount(1500.0);
        app1.setDocument("QT-3001");
        app1.setStatus(ApplicationStatus.PENDING);
        
        // Create rejected application
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompanyName("BioMed");
        app2.setNumberOfShares(10);
        app2.setAmount(2000.0);
        app2.setDocument("QT-3002");
        app2.setStatus(ApplicationStatus.REJECTED);
        
        // Add applications to customer
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = bank.queryTotalApprovalIPOApplicationsAmount(customer);
        
        // Verify: Should return 0 since no approved applications
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_singleApproval() {
        // Setup: Create customer C202 with one approved application
        Customer customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephoneNumber("555-2323");
        customer.setRestricted(false);
        
        // Create approved application
        IPOApplication app = new IPOApplication();
        app.setCustomer(customer);
        app.setCompanyName("SolarMax");
        app.setNumberOfShares(84);
        app.setAmount(4200.0);
        app.setDocument("SM-2024-Q1");
        app.setStatus(ApplicationStatus.APPROVED);
        
        // Add application to customer
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app);
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = bank.queryTotalApprovalIPOApplicationsAmount(customer);
        
        // Verify: Should return 4200.0
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup: Create customer C203 with multiple approved applications
        Customer customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephoneNumber("555-3434");
        customer.setRestricted(false);
        
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
        
        // Test: Query total approved amount
        double result = bank.queryTotalApprovalIPOApplicationsAmount(customer);
        
        // Verify: Should return 5500.0 (2000 + 3500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_largePortfolio() {
        // Setup: Create customer C204 with 5 approved applications
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephoneNumber("555-4545");
        customer.setRestricted(false);
        
        // Create 5 approved applications, each with $10,000
        List<IPOApplication> applications = new ArrayList<>();
        
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompanyName("TechGiant");
        app1.setNumberOfShares(200);
        app1.setAmount(10000.0);
        app1.setDocument("SM-3006");
        app1.setStatus(ApplicationStatus.APPROVED);
        applications.add(app1);
        
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompanyName("AutoFuture");
        app2.setNumberOfShares(250);
        app2.setAmount(10000.0);
        app2.setDocument("SM-3007");
        app2.setStatus(ApplicationStatus.APPROVED);
        applications.add(app2);
        
        IPOApplication app3 = new IPOApplication();
        app3.setCustomer(customer);
        app3.setCompanyName("AeroSpace");
        app3.setNumberOfShares(125);
        app3.setAmount(10000.0);
        app3.setDocument("SM-3008");
        app3.setStatus(ApplicationStatus.APPROVED);
        applications.add(app3);
        
        IPOApplication app4 = new IPOApplication();
        app4.setCustomer(customer);
        app4.setCompanyName("BioGenius");
        app4.setNumberOfShares(500);
        app4.setAmount(10000.0);
        app4.setDocument("SM-3009");
        app4.setStatus(ApplicationStatus.APPROVED);
        applications.add(app4);
        
        IPOApplication app5 = new IPOApplication();
        app5.setCustomer(customer);
        app5.setCompanyName("GreenEnergy");
        app5.setNumberOfShares(200);
        app5.setAmount(10000.0);
        app5.setDocument("SM-3010");
        app5.setStatus(ApplicationStatus.APPROVED);
        applications.add(app5);
        
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = bank.queryTotalApprovalIPOApplicationsAmount(customer);
        
        // Verify: Should return 50000.0 (5 * 10000)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup: Create customer C205 with approved and pending applications
        Customer customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephoneNumber("555-5656");
        customer.setRestricted(false);
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // Create approved applications
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompanyName("CloudServ");
        app1.setNumberOfShares(100);
        app1.setAmount(3000.0);
        app1.setDocument("SM-3011");
        app1.setStatus(ApplicationStatus.APPROVED);
        applications.add(app1);
        
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompanyName("DataCore");
        app2.setNumberOfShares(20);
        app2.setAmount(2750.0);
        app2.setDocument("SM-3012");
        app2.setStatus(ApplicationStatus.APPROVED);
        applications.add(app2);
        
        IPOApplication app3 = new IPOApplication();
        app3.setCustomer(customer);
        app3.setCompanyName("AI Ventures");
        app3.setNumberOfShares(30);
        app3.setAmount(3000.0);
        app3.setDocument("SM-3013");
        app3.setStatus(ApplicationStatus.APPROVED);
        applications.add(app3);
        
        // Create pending applications (should not be counted)
        IPOApplication app4 = new IPOApplication();
        app4.setCustomer(customer);
        app4.setCompanyName("NanoTech");
        app4.setNumberOfShares(10);
        app4.setAmount(600.0);
        app4.setDocument("SM-3014");
        app4.setStatus(ApplicationStatus.PENDING);
        applications.add(app4);
        
        IPOApplication app5 = new IPOApplication();
        app5.setCustomer(customer);
        app5.setCompanyName("RoboWorks");
        app5.setNumberOfShares(50);
        app5.setAmount(600.0);
        app5.setDocument("SM-3015");
        app5.setStatus(ApplicationStatus.PENDING);
        applications.add(app5);
        
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = bank.queryTotalApprovalIPOApplicationsAmount(customer);
        
        // Verify: Should return 8750.0 (3000 + 2750 + 3000) - pending applications should be ignored
        assertEquals(8750.0, result, 0.001);
    }
}