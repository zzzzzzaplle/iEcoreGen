import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private IPOApplicationSystem system;
    
    @Before
    public void setUp() {
        system = new IPOApplicationSystem();
    }
    
    @Test
    public void testCase1_NoApprovedRequests() {
        // Setup: Customer "C201" with pending and rejected applications
        Customer customer = new Customer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmailAddress("e.chen@example.com");
        customer.setTelephoneNumber("555-1212");
        customer.setEligibleForIPO(true);
        
        Company techInc = new Company();
        techInc.setName("TechInc");
        
        Company bioMed = new Company();
        bioMed.setName("BioMed");
        
        // Create pending application
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompany(techInc);
        app1.setNumberOfShares(10);
        app1.setAmount(1500.0);
        app1.setDocument("QT-3001");
        app1.setApproved(false);
        app1.setRejected(false);
        app1.setReviewed(false);
        
        // Create rejected application
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompany(bioMed);
        app2.setNumberOfShares(10);
        app2.setAmount(2000.0);
        app2.setDocument("QT-3002");
        app2.setApproved(false);
        app2.setRejected(true);
        app2.setReviewed(true);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        
        // Test: Query total approved amount
        double result = system.getTotalApprovedAmount(customer);
        
        // Verify: Should return 0 since no approved applications
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SingleApproval() {
        // Setup: Customer "C202" with one approved application
        Customer customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmailAddress("r.johnson@example.com");
        customer.setTelephoneNumber("555-2323");
        customer.setEligibleForIPO(true);
        
        Company solarMax = new Company();
        solarMax.setName("SolarMax");
        solarMax.setEmailAddress("solarmax@gmail.com");
        
        // Create approved application
        IPOApplication app = new IPOApplication();
        app.setCustomer(customer);
        app.setCompany(solarMax);
        app.setNumberOfShares(84);
        app.setAmount(4200.0);
        app.setDocument("SM-2024-Q1");
        app.setApproved(true);
        app.setRejected(false);
        app.setReviewed(true);
        
        customer.getApplications().add(app);
        
        // Test: Query total approved amount
        double result = system.getTotalApprovedAmount(customer);
        
        // Verify: Should return 4200.0
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with two approved applications from different companies
        Customer customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmailAddress("s.williams@example.com");
        customer.setTelephoneNumber("555-3434");
        customer.setEligibleForIPO(true);
        
        Company quantumTech = new Company();
        quantumTech.setName("QuantumTech");
        
        Company neuralink = new Company();
        neuralink.setName("Neuralink");
        
        // Create first approved application
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompany(quantumTech);
        app1.setNumberOfShares(40);
        app1.setAmount(2000.0);
        app1.setDocument("SM-2024-Q3004");
        app1.setApproved(true);
        app1.setRejected(false);
        app1.setReviewed(true);
        
        // Create second approved application
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompany(neuralink);
        app2.setNumberOfShares(70);
        app2.setAmount(3500.0);
        app2.setDocument("SM-2024-Q3005");
        app2.setApproved(true);
        app2.setRejected(false);
        app2.setReviewed(true);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        
        // Test: Query total approved amount
        double result = system.getTotalApprovedAmount(customer);
        
        // Verify: Should return 5500.0 (2000 + 3500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup: Customer "C204" with five approved applications, each $10,000
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmailAddress("j.wilson@vip.example.com");
        customer.setTelephoneNumber("555-4545");
        customer.setEligibleForIPO(true);
        
        String[] companyNames = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        String[] documentIds = {"SM-3006", "SM-3007", "SM-3008", "SM-3009", "SM-3010"};
        int[] shares = {200, 250, 125, 500, 200};
        
        for (int i = 0; i < companyNames.length; i++) {
            Company company = new Company();
            company.setName(companyNames[i]);
            
            IPOApplication app = new IPOApplication();
            app.setCustomer(customer);
            app.setCompany(company);
            app.setNumberOfShares(shares[i]);
            app.setAmount(10000.0);
            app.setDocument(documentIds[i]);
            app.setApproved(true);
            app.setRejected(false);
            app.setReviewed(true);
            
            customer.getApplications().add(app);
        }
        
        // Test: Query total approved amount
        double result = system.getTotalApprovedAmount(customer);
        
        // Verify: Should return 50000.0 (5 * 10000)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup: Customer "C205" with three approved and two pending applications
        Customer customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmailAddress("o.brown@example.com");
        customer.setTelephoneNumber("555-5656");
        customer.setEligibleForIPO(true);
        
        // Approved applications
        String[] approvedCompanies = {"CloudServ", "DataCore", "AI Ventures"};
        int[] approvedShares = {100, 20, 30};
        double[] approvedAmounts = {3000.0, 2750.0, 3000.0};
        String[] approvedDocs = {"SM-3011", "SM-3012", "SM-3013"};
        
        for (int i = 0; i < approvedCompanies.length; i++) {
            Company company = new Company();
            company.setName(approvedCompanies[i]);
            
            IPOApplication app = new IPOApplication();
            app.setCustomer(customer);
            app.setCompany(company);
            app.setNumberOfShares(approvedShares[i]);
            app.setAmount(approvedAmounts[i]);
            app.setDocument(approvedDocs[i]);
            app.setApproved(true);
            app.setRejected(false);
            app.setReviewed(true);
            
            customer.getApplications().add(app);
        }
        
        // Pending applications (should not be counted)
        String[] pendingCompanies = {"NanoTech", "RoboWorks"};
        int[] pendingShares = {10, 50};
        double[] pendingAmounts = {600.0, 600.0};
        String[] pendingDocs = {"SM-3014", "SM-3015"};
        
        for (int i = 0; i < pendingCompanies.length; i++) {
            Company company = new Company();
            company.setName(pendingCompanies[i]);
            
            IPOApplication app = new IPOApplication();
            app.setCustomer(customer);
            app.setCompany(company);
            app.setNumberOfShares(pendingShares[i]);
            app.setAmount(pendingAmounts[i]);
            app.setDocument(pendingDocs[i]);
            app.setApproved(false);
            app.setRejected(false);
            app.setReviewed(false);
            
            customer.getApplications().add(app);
        }
        
        // Test: Query total approved amount
        double result = system.getTotalApprovedAmount(customer);
        
        // Verify: Should return 8750.0 (3000 + 2750 + 3000)
        assertEquals(8750.0, result, 0.001);
    }
}