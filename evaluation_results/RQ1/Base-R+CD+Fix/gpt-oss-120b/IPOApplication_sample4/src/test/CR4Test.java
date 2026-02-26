import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Customer customer;

    @Before
    public void setUp() {
        // Reset customer before each test
        customer = new Customer();
    }

    @Test
    public void testCase1_NoApprovedRequests() {
        // Setup: Customer "C201" with pending and rejected applications
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);

        // Create pending application
        Application app1 = new Application();
        app1.setShare(10);
        app1.setAmountOfMoney(1500.0);
        Company techInc = new Company();
        techInc.setName("TechInc");
        app1.setCompany(techInc);
        app1.setCustomer(customer);
        app1.setStatus(ApplicationStatus.PENDING);
        Document doc1 = new Document();
        app1.setAllowance(doc1);
        customer.getApplications().add(app1);

        // Create rejected application
        Application app2 = new Application();
        app2.setShare(10);
        app2.setAmountOfMoney(2000.0);
        Company bioMed = new Company();
        bioMed.setName("BioMed");
        app2.setCompany(bioMed);
        app2.setCustomer(customer);
        app2.setStatus(ApplicationStatus.REJECTED);
        Document doc2 = new Document();
        app2.setAllowance(doc2);
        customer.getApplications().add(app2);

        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should be 0 since no approved applications
        assertEquals(0.0, result, 0.001);
    }

    @Test
    public void testCase2_SingleApproval() {
        // Setup: Customer "C202" with one approved application
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setCanApplyForIPO(true);

        // Create approved application
        Application app = new Application();
        app.setShare(84);
        app.setAmountOfMoney(4200.0);
        Company solarMax = new Company();
        solarMax.setName("SolarMax");
        solarMax.setEmail("solarmax@gmail.com");
        app.setCompany(solarMax);
        app.setCustomer(customer);
        app.setStatus(ApplicationStatus.APPROVAL);
        Document doc = new Document();
        app.setAllowance(doc);
        customer.getApplications().add(app);

        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should be 4200
        assertEquals(4200.0, result, 0.001);
    }

    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with two approved applications
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setCanApplyForIPO(true);

        // First approved application
        Application app1 = new Application();
        app1.setShare(40);
        app1.setAmountOfMoney(2000.0);
        Company quantumTech = new Company();
        quantumTech.setName("QuantumTech");
        app1.setCompany(quantumTech);
        app1.setCustomer(customer);
        app1.setStatus(ApplicationStatus.APPROVAL);
        Document doc1 = new Document();
        app1.setAllowance(doc1);
        customer.getApplications().add(app1);

        // Second approved application
        Application app2 = new Application();
        app2.setShare(70);
        app2.setAmountOfMoney(3500.0);
        Company neuralink = new Company();
        neuralink.setName("Neuralink");
        app2.setCompany(neuralink);
        app2.setCustomer(customer);
        app2.setStatus(ApplicationStatus.APPROVAL);
        Document doc2 = new Document();
        app2.setAllowance(doc2);
        customer.getApplications().add(app2);

        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should be 5500 (2000 + 3500)
        assertEquals(5500.0, result, 0.001);
    }

    @Test
    public void testCase4_LargePortfolio() {
        // Setup: Customer "C204" with five approved applications
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setCanApplyForIPO(true);

        // Create 5 approved applications, each with $10,000
        String[] companyNames = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        int[] shares = {200, 250, 125, 500, 200};
        
        for (int i = 0; i < 5; i++) {
            Application app = new Application();
            app.setShare(shares[i]);
            app.setAmountOfMoney(10000.0);
            Company company = new Company();
            company.setName(companyNames[i]);
            app.setCompany(company);
            app.setCustomer(customer);
            app.setStatus(ApplicationStatus.APPROVAL);
            Document doc = new Document();
            app.setAllowance(doc);
            customer.getApplications().add(app);
        }

        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should be 50000 (5 * 10000)
        assertEquals(50000.0, result, 0.001);
    }

    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup: Customer "C205" with approved and pending applications
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setCanApplyForIPO(true);

        // Approved applications
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(3000.0);
        Company cloudServ = new Company();
        cloudServ.setName("CloudServ");
        app1.setCompany(cloudServ);
        app1.setCustomer(customer);
        app1.setStatus(ApplicationStatus.APPROVAL);
        Document doc1 = new Document();
        app1.setAllowance(doc1);
        customer.getApplications().add(app1);

        Application app2 = new Application();
        app2.setShare(20);
        app2.setAmountOfMoney(2750.0);
        Company dataCore = new Company();
        dataCore.setName("DataCore");
        app2.setCompany(dataCore);
        app2.setCustomer(customer);
        app2.setStatus(ApplicationStatus.APPROVAL);
        Document doc2 = new Document();
        app2.setAllowance(doc2);
        customer.getApplications().add(app2);

        Application app3 = new Application();
        app3.setShare(30);
        app3.setAmountOfMoney(3000.0);
        Company aiVentures = new Company();
        aiVentures.setName("AI Ventures");
        app3.setCompany(aiVentures);
        app3.setCustomer(customer);
        app3.setStatus(ApplicationStatus.APPROVAL);
        Document doc3 = new Document();
        app3.setAllowance(doc3);
        customer.getApplications().add(app3);

        // Pending applications (should not be counted)
        Application app4 = new Application();
        app4.setShare(10);
        app4.setAmountOfMoney(600.0);
        Company nanoTech = new Company();
        nanoTech.setName("NanoTech");
        app4.setCompany(nanoTech);
        app4.setCustomer(customer);
        app4.setStatus(ApplicationStatus.PENDING);
        Document doc4 = new Document();
        app4.setAllowance(doc4);
        customer.getApplications().add(app4);

        Application app5 = new Application();
        app5.setShare(50);
        app5.setAmountOfMoney(600.0);
        Company roboWorks = new Company();
        roboWorks.setName("RoboWorks");
        app5.setCompany(roboWorks);
        app5.setCustomer(customer);
        app5.setStatus(ApplicationStatus.PENDING);
        Document doc5 = new Document();
        app5.setAllowance(doc5);
        customer.getApplications().add(app5);

        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should be 8750 (3000 + 2750 + 3000)
        assertEquals(8750.0, result, 0.001);
    }
}