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
        customer.setRestricted(false);
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // PENDING application
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("TechInc");
        app1.setNumberOfShares(10);
        app1.setAmountOfMoney(1500.0);
        app1.setDocument("QT-3001");
        app1.setStatus(ApplicationStatus.PENDING);
        applications.add(app1);
        
        // REJECTED application
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("BioMed");
        app2.setNumberOfShares(10);
        app2.setAmountOfMoney(2000.0);
        app2.setDocument("QT-3002");
        app2.setStatus(ApplicationStatus.REJECTED);
        applications.add(app2);
        
        customer.setIpoApplications(applications);
        
        // Execute: Query total approved amount
        double result = service.queryTotalApprovalAmount(customer);
        
        // Verify: Expected output is 0
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
        customer.setRestricted(false);
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // APPROVED application
        IPOApplication app = new IPOApplication();
        app.setCompanyName("SolarMax");
        app.setNumberOfShares(84);
        app.setAmountOfMoney(4200.0);
        app.setDocument("SM-2024-Q1");
        app.setStatus(ApplicationStatus.APPROVED);
        applications.add(app);
        
        customer.setIpoApplications(applications);
        
        // Execute: Query total approved amount
        double result = service.queryTotalApprovalAmount(customer);
        
        // Verify: Expected output is 4200
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with two approved applications from different companies
        Customer customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephoneNumber("555-3434");
        customer.setRestricted(false);
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // First APPROVED application
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("QuantumTech");
        app1.setNumberOfShares(40);
        app1.setAmountOfMoney(2000.0);
        app1.setDocument("SM-2024-Q3004");
        app1.setStatus(ApplicationStatus.APPROVED);
        applications.add(app1);
        
        // Second APPROVED application
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("Neuralink");
        app2.setNumberOfShares(70);
        app2.setAmountOfMoney(3500.0);
        app2.setDocument("SM-2024-Q3005");
        app2.setStatus(ApplicationStatus.APPROVED);
        applications.add(app2);
        
        customer.setIpoApplications(applications);
        
        // Execute: Query total approved amount
        double result = service.queryTotalApprovalAmount(customer);
        
        // Verify: Expected output is 5500 (2000 + 3500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup: Customer "C204" with five approved applications, each $10,000
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephoneNumber("555-4545");
        customer.setRestricted(false);
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // Create 5 approved applications, each with $10,000
        String[] companies = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        int[] shares = {200, 250, 125, 500, 200};
        String[] documents = {"SM-3006", "SM-3007", "SM-3008", "SM-3009", "SM-3010"};
        
        for (int i = 0; i < 5; i++) {
            IPOApplication app = new IPOApplication();
            app.setCompanyName(companies[i]);
            app.setNumberOfShares(shares[i]);
            app.setAmountOfMoney(10000.0);
            app.setDocument(documents[i]);
            app.setStatus(ApplicationStatus.APPROVED);
            applications.add(app);
        }
        
        customer.setIpoApplications(applications);
        
        // Execute: Query total approved amount
        double result = service.queryTotalApprovalAmount(customer);
        
        // Verify: Expected output is 50000 (5 * 10000)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup: Customer "C205" with 3 approved and 2 pending applications
        Customer customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephoneNumber("555-5656");
        customer.setRestricted(false);
        
        List<IPOApplication> applications = new ArrayList<>();
        
        // Approved applications
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("CloudServ");
        app1.setNumberOfShares(100);
        app1.setAmountOfMoney(3000.0);
        app1.setDocument("SM-3011");
        app1.setStatus(ApplicationStatus.APPROVED);
        applications.add(app1);
        
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("DataCore");
        app2.setNumberOfShares(20);
        app2.setAmountOfMoney(2750.0);
        app2.setDocument("SM-3012");
        app2.setStatus(ApplicationStatus.APPROVED);
        applications.add(app2);
        
        IPOApplication app3 = new IPOApplication();
        app3.setCompanyName("AI Ventures");
        app3.setNumberOfShares(30);
        app3.setAmountOfMoney(3000.0);
        app3.setDocument("SM-3013");
        app3.setStatus(ApplicationStatus.APPROVED);
        applications.add(app3);
        
        // Pending applications (should not be counted)
        IPOApplication app4 = new IPOApplication();
        app4.setCompanyName("NanoTech");
        app4.setNumberOfShares(10);
        app4.setAmountOfMoney(600.0);
        app4.setDocument("SM-3014");
        app4.setStatus(ApplicationStatus.PENDING);
        applications.add(app4);
        
        IPOApplication app5 = new IPOApplication();
        app5.setCompanyName("RoboWorks");
        app5.setNumberOfShares(50);
        app5.setAmountOfMoney(600.0);
        app5.setDocument("SM-3015");
        app5.setStatus(ApplicationStatus.PENDING);
        applications.add(app5);
        
        customer.setIpoApplications(applications);
        
        // Execute: Query total approved amount
        double result = service.queryTotalApprovalAmount(customer);
        
        // Verify: Expected output is 8750 (3000 + 2750 + 3000)
        assertEquals(8750.0, result, 0.001);
    }
}