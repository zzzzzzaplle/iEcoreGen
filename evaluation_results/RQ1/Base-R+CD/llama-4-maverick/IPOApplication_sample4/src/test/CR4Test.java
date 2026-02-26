import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        customer = new Customer();
        company = new Company();
        document = new Document();
    }
    
    @Test
    public void testCase1_NoApprovedRequests() {
        // Setup: Customer "C201" with PENDING and REJECTED applications
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);
        
        // Create PENDING application
        Company techInc = new Company();
        techInc.setName("TechInc");
        Application app3001 = new Application(10, 1500.0, techInc, customer, document);
        app3001.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(app3001);
        
        // Create REJECTED application
        Company bioMed = new Company();
        bioMed.setName("BioMed");
        Application app3002 = new Application(10, 2000.0, bioMed, customer, document);
        app3002.setStatus(ApplicationStatus.REJECTED);
        customer.getApplications().add(app3002);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Expected output is 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SingleApproval() {
        // Setup: Customer "C202" with single APPROVAL application
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setCanApplyForIPO(true);
        
        // Create APPROVAL application
        Company solarMax = new Company();
        solarMax.setName("SolarMax");
        solarMax.setEmail("solarmax@gmail.com");
        Application app3003 = new Application(84, 4200.0, solarMax, customer, document);
        app3003.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app3003);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Expected output is 4200
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with multiple APPROVAL applications
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setCanApplyForIPO(true);
        
        // Create first APPROVAL application
        Company quantumTech = new Company();
        quantumTech.setName("QuantumTech");
        Application app3004 = new Application(40, 2000.0, quantumTech, customer, document);
        app3004.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app3004);
        
        // Create second APPROVAL application
        Company neuralink = new Company();
        neuralink.setName("Neuralink");
        Application app3005 = new Application(70, 3500.0, neuralink, customer, document);
        app3005.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app3005);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Expected output is 5500
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup: Customer "C204" with 5 APPROVAL applications
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setCanApplyForIPO(true);
        
        // Create 5 APPROVAL applications, each with $10,000
        String[] companyNames = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        
        for (int i = 0; i < 5; i++) {
            Company company = new Company();
            company.setName(companyNames[i]);
            Application app = new Application(100, 10000.0, company, customer, document);
            app.setStatus(ApplicationStatus.APPROVAL);
            customer.getApplications().add(app);
        }
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Expected output is 50000
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup: Customer "C205" with mixed APPROVAL and PENDING applications
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setCanApplyForIPO(true);
        
        // Create APPROVAL applications
        Company cloudServ = new Company();
        cloudServ.setName("CloudServ");
        Application app3011 = new Application(100, 3000.0, cloudServ, customer, document);
        app3011.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app3011);
        
        Company dataCore = new Company();
        dataCore.setName("DataCore");
        Application app3012 = new Application(20, 2750.0, dataCore, customer, document);
        app3012.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app3012);
        
        Company aiVentures = new Company();
        aiVentures.setName("AI Ventures");
        Application app3013 = new Application(30, 3000.0, aiVentures, customer, document);
        app3013.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app3013);
        
        // Create PENDING applications (should not be counted in approved total)
        Company nanoTech = new Company();
        nanoTech.setName("NanoTech");
        Application app3014 = new Application(10, 600.0, nanoTech, customer, document);
        app3014.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(app3014);
        
        Company roboWorks = new Company();
        roboWorks.setName("RoboWorks");
        Application app3015 = new Application(50, 600.0, roboWorks, customer, document);
        app3015.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(app3015);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Expected output is 8750 (3000 + 2750 + 3000)
        assertEquals(8750.0, result, 0.001);
    }
}