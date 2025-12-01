import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Customer customer;
    private Document document;
    
    @Before
    public void setUp() {
        document = new Document();
    }
    
    @Test
    public void testCase1_noApprovedRequests() {
        // Setup: Customer "C201" with pending and rejected applications
        customer = new Customer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);
        
        // Create companies
        Company techInc = new Company();
        techInc.setName("TechInc");
        techInc.setEmail("techinc@example.com");
        
        Company bioMed = new Company();
        bioMed.setName("BioMed");
        bioMed.setEmail("biomed@example.com");
        
        // Create applications
        Application app1 = new Application(techInc, 10, 1500.0, document, customer);
        app1.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(app1);
        
        Application app2 = new Application(bioMed, 10, 2000.0, document, customer);
        app2.setStatus(ApplicationStatus.REJECTED);
        customer.getApplications().add(app2);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_singleApproval() {
        // Setup: Customer "C202" with one approved application
        customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setCanApplyForIPO(true);
        
        // Create company
        Company solarMax = new Company();
        solarMax.setName("SolarMax");
        solarMax.setEmail("solarmax@gmail.com");
        
        // Create and approve application
        Application app = new Application(solarMax, 84, 4200.0, document, customer);
        app.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with two approved applications
        customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setCanApplyForIPO(true);
        
        // Create companies
        Company quantumTech = new Company();
        quantumTech.setName("QuantumTech");
        quantumTech.setEmail("quantumtech@example.com");
        
        Company neuralink = new Company();
        neuralink.setName("Neuralink");
        neuralink.setEmail("neuralink@example.com");
        
        // Create and approve applications
        Application app1 = new Application(quantumTech, 40, 2000.0, document, customer);
        app1.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app1);
        
        Application app2 = new Application(neuralink, 70, 3500.0, document, customer);
        app2.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app2);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_largePortfolio() {
        // Setup: Customer "C204" with five approved applications
        customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setCanApplyForIPO(true);
        
        // Create companies
        Company techGiant = new Company();
        techGiant.setName("TechGiant");
        techGiant.setEmail("techgiant@example.com");
        
        Company autoFuture = new Company();
        autoFuture.setName("AutoFuture");
        autoFuture.setEmail("autofuture@example.com");
        
        Company aeroSpace = new Company();
        aeroSpace.setName("AeroSpace");
        aeroSpace.setEmail("aerospace@example.com");
        
        Company bioGenius = new Company();
        bioGenius.setName("BioGenius");
        bioGenius.setEmail("biogenius@example.com");
        
        Company greenEnergy = new Company();
        greenEnergy.setName("GreenEnergy");
        greenEnergy.setEmail("greenenergy@example.com");
        
        // Create and approve applications (all $10,000 each)
        Application app1 = new Application(techGiant, 200, 10000.0, document, customer);
        app1.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app1);
        
        Application app2 = new Application(autoFuture, 250, 10000.0, document, customer);
        app2.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app2);
        
        Application app3 = new Application(aeroSpace, 125, 10000.0, document, customer);
        app3.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app3);
        
        Application app4 = new Application(bioGenius, 500, 10000.0, document, customer);
        app4.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app4);
        
        Application app5 = new Application(greenEnergy, 200, 10000.0, document, customer);
        app5.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app5);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup: Customer "C205" with approved and pending applications
        customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setCanApplyForIPO(true);
        
        // Create companies for approved applications
        Company cloudServ = new Company();
        cloudServ.setName("CloudServ");
        cloudServ.setEmail("cloudserv@example.com");
        
        Company dataCore = new Company();
        dataCore.setName("DataCore");
        dataCore.setEmail("datacore@example.com");
        
        Company aiVentures = new Company();
        aiVentures.setName("AI Ventures");
        aiVentures.setEmail("aiventures@example.com");
        
        // Create companies for pending applications
        Company nanoTech = new Company();
        nanoTech.setName("NanoTech");
        nanoTech.setEmail("nanotech@example.com");
        
        Company roboWorks = new Company();
        roboWorks.setName("RoboWorks");
        roboWorks.setEmail("roboworks@example.com");
        
        // Create and approve applications
        Application app1 = new Application(cloudServ, 100, 3000.0, document, customer);
        app1.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app1);
        
        Application app2 = new Application(dataCore, 20, 2750.0, document, customer);
        app2.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app2);
        
        Application app3 = new Application(aiVentures, 30, 3000.0, document, customer);
        app3.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app3);
        
        // Create pending applications
        Application app4 = new Application(nanoTech, 10, 600.0, document, customer);
        app4.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(app4);
        
        Application app5 = new Application(roboWorks, 50, 600.0, document, customer);
        app5.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(app5);
        
        // Test: Query total approved amount (should ignore pending applications)
        double result = customer.getApprovedTotalAmount();
        assertEquals(8750.0, result, 0.001);
    }
}