import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
        Application pendingApp = new Application(10, 1500.0, ApplicationStatus.PENDING, customer, techInc, document);
        customer.getApplications().add(pendingApp);
        
        // Create REJECTED application
        Company bioMed = new Company();
        bioMed.setName("BioMed");
        Application rejectedApp = new Application(10, 2000.0, ApplicationStatus.REJECTED, customer, bioMed, document);
        customer.getApplications().add(rejectedApp);
        
        // Test: Request total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: No approved applications should return 0
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
        Company solarMax = new Company();
        solarMax.setName("SolarMax");
        solarMax.setEmail("solarmax@gmail.com");
        Application approvedApp = new Application(84, 4200.0, ApplicationStatus.APPROVAL, customer, solarMax, document);
        customer.getApplications().add(approvedApp);
        
        // Test: Request total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Single approved application amount should be returned
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with multiple approved applications
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setCanApplyForIPO(true);
        
        // Create first approved application
        Company quantumTech = new Company();
        quantumTech.setName("QuantumTech");
        Application app1 = new Application(40, 2000.0, ApplicationStatus.APPROVAL, customer, quantumTech, document);
        customer.getApplications().add(app1);
        
        // Create second approved application
        Company neuralink = new Company();
        neuralink.setName("Neuralink");
        Application app2 = new Application(70, 3500.0, ApplicationStatus.APPROVAL, customer, neuralink, document);
        customer.getApplications().add(app2);
        
        // Test: Request total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Sum of all approved application amounts should be returned
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup: Customer "C204" with multiple approved applications of $10,000 each
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setCanApplyForIPO(true);
        
        // Create 5 approved applications, each with $10,000
        String[] companyNames = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        
        for (int i = 0; i < 5; i++) {
            Company comp = new Company();
            comp.setName(companyNames[i]);
            Application app = new Application(100 + i * 50, 10000.0, ApplicationStatus.APPROVAL, customer, comp, document);
            customer.getApplications().add(app);
        }
        
        // Test: Request total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Total should be 5 * $10,000 = $50,000
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
        
        // Create approved applications
        Company cloudServ = new Company();
        cloudServ.setName("CloudServ");
        Application app1 = new Application(100, 3000.0, ApplicationStatus.APPROVAL, customer, cloudServ, document);
        customer.getApplications().add(app1);
        
        Company dataCore = new Company();
        dataCore.setName("DataCore");
        Application app2 = new Application(20, 2750.0, ApplicationStatus.APPROVAL, customer, dataCore, document);
        customer.getApplications().add(app2);
        
        Company aiVentures = new Company();
        aiVentures.setName("AI Ventures");
        Application app3 = new Application(30, 3000.0, ApplicationStatus.APPROVAL, customer, aiVentures, document);
        customer.getApplications().add(app3);
        
        // Create pending applications (should not be counted)
        Company nanoTech = new Company();
        nanoTech.setName("NanoTech");
        Application pending1 = new Application(10, 600.0, ApplicationStatus.PENDING, customer, nanoTech, document);
        customer.getApplications().add(pending1);
        
        Company roboWorks = new Company();
        roboWorks.setName("RoboWorks");
        Application pending2 = new Application(50, 600.0, ApplicationStatus.PENDING, customer, roboWorks, document);
        customer.getApplications().add(pending2);
        
        // Test: Request total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Only approved applications should be counted (3000 + 2750 + 3000 = 8750)
        assertEquals(8750.0, result, 0.001);
    }
}