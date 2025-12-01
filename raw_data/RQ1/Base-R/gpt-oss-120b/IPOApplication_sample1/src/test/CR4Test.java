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
        // Setup: Customer "C201" with pending and rejected applications but no approved ones
        Customer customer = new Customer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setEligible(true);
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // PENDING application
        IPOApplication app1 = new IPOApplication();
        app1.setNumberOfShares(10);
        app1.setAmount(1500.0);
        Company company1 = new Company("TechInc");
        app1.setCompany(company1);
        app1.setDocument("QT-3001");
        app1.setStatus(ApplicationStatus.PENDING);
        applications.add(app1);
        
        // REJECTED application
        IPOApplication app2 = new IPOApplication();
        app2.setNumberOfShares(10);
        app2.setAmount(2000.0);
        Company company2 = new Company("BioMed");
        app2.setCompany(company2);
        app2.setDocument("QT-3002");
        app2.setStatus(ApplicationStatus.REJECTED);
        applications.add(app2);
        
        customer.setApplications(applications);
        
        // Execute: Query total approved amount
        double result = bank.getTotalApprovedAmount(customer);
        
        // Verify: Should return 0 since there are no approved applications
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
        customer.setEligible(true);
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // APPROVED application
        IPOApplication app = new IPOApplication();
        app.setNumberOfShares(84);
        app.setAmount(4200.0);
        Company company = new Company("SolarMax");
        app.setCompany(company);
        app.setDocument("SM-2024-Q1");
        app.setStatus(ApplicationStatus.APPROVED);
        applications.add(app);
        
        customer.setApplications(applications);
        
        // Execute: Query total approved amount
        double result = bank.getTotalApprovedAmount(customer);
        
        // Verify: Should return 4200.0 (single approved application amount)
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with two approved applications from different companies
        Customer customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setEligible(true);
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // First APPROVED application
        IPOApplication app1 = new IPOApplication();
        app1.setNumberOfShares(40);
        app1.setAmount(2000.0);
        Company company1 = new Company("QuantumTech");
        app1.setCompany(company1);
        app1.setDocument("SM-2024-Q3004");
        app1.setStatus(ApplicationStatus.APPROVED);
        applications.add(app1);
        
        // Second APPROVED application
        IPOApplication app2 = new IPOApplication();
        app2.setNumberOfShares(70);
        app2.setAmount(3500.0);
        Company company2 = new Company("Neuralink");
        app2.setCompany(company2);
        app2.setDocument("SM-2024-Q3005");
        app2.setStatus(ApplicationStatus.APPROVED);
        applications.add(app2);
        
        customer.setApplications(applications);
        
        // Execute: Query total approved amount
        double result = bank.getTotalApprovedAmount(customer);
        
        // Verify: Should return 5500.0 (2000 + 3500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_largePortfolio() {
        // Setup: Customer "C204" with five approved applications, each worth $10,000
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setEligible(true);
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // Create 5 approved applications with $10,000 each
        String[] companies = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        int[] shares = {200, 250, 125, 500, 200};
        String[] documents = {"SM-3006", "SM-3007", "SM-3008", "SM-3009", "SM-3010"};
        
        for (int i = 0; i < 5; i++) {
            IPOApplication app = new IPOApplication();
            app.setNumberOfShares(shares[i]);
            app.setAmount(10000.0);
            Company company = new Company(companies[i]);
            app.setCompany(company);
            app.setDocument(documents[i]);
            app.setStatus(ApplicationStatus.APPROVED);
            applications.add(app);
        }
        
        customer.setApplications(applications);
        
        // Execute: Query total approved amount
        double result = bank.getTotalApprovedAmount(customer);
        
        // Verify: Should return 50000.0 (5 × 10000)
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
        customer.setEligible(true);
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // Approved applications
        IPOApplication app1 = new IPOApplication();
        app1.setNumberOfShares(100);
        app1.setAmount(3000.0);
        Company company1 = new Company("CloudServ");
        app1.setCompany(company1);
        app1.setDocument("SM-3011");
        app1.setStatus(ApplicationStatus.APPROVED);
        applications.add(app1);
        
        IPOApplication app2 = new IPOApplication();
        app2.setNumberOfShares(20);
        app2.setAmount(2750.0);
        Company company2 = new Company("DataCore");
        app2.setCompany(company2);
        app2.setDocument("SM-3012");
        app2.setStatus(ApplicationStatus.APPROVED);
        applications.add(app2);
        
        IPOApplication app3 = new IPOApplication();
        app3.setNumberOfShares(30);
        app3.setAmount(3000.0);
        Company company3 = new Company("AI Ventures");
        app3.setCompany(company3);
        app3.setDocument("SM-3013");
        app3.setStatus(ApplicationStatus.APPROVED);
        applications.add(app3);
        
        // Pending applications (should not be counted)
        IPOApplication app4 = new IPOApplication();
        app4.setNumberOfShares(10);
        app4.setAmount(600.0);
        Company company4 = new Company("NanoTech");
        app4.setCompany(company4);
        app4.setDocument("SM-3014");
        app4.setStatus(ApplicationStatus.PENDING);
        applications.add(app4);
        
        IPOApplication app5 = new IPOApplication();
        app5.setNumberOfShares(50);
        app5.setAmount(600.0);
        Company company5 = new Company("RoboWorks");
        app5.setCompany(company5);
        app5.setDocument("SM-3015");
        app5.setStatus(ApplicationStatus.PENDING);
        applications.add(app5);
        
        customer.setApplications(applications);
        
        // Execute: Query total approved amount
        double result = bank.getTotalApprovedAmount(customer);
        
        // Verify: Should return 8750.0 (3000 + 2750 + 3000) - pending applications ignored
        assertEquals(8750.0, result, 0.001);
    }
}