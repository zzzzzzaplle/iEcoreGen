import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Customer customer;
    private Company techInc;
    private Company bioMed;
    private Company solarMax;
    private Company quantumTech;
    private Company neuralink;
    private Company techGiant;
    private Company autoFuture;
    private Company aeroSpace;
    private Company bioGenius;
    private Company greenEnergy;
    private Company cloudServ;
    private Company dataCore;
    private Company aiVentures;
    private Company nanoTech;
    private Company roboWorks;
    
    @Before
    public void setUp() {
        // Initialize test companies
        techInc = new Company();
        techInc.setName("TechInc");
        techInc.setEmail("techinc@example.com");
        
        bioMed = new Company();
        bioMed.setName("BioMed");
        bioMed.setEmail("biomed@example.com");
        
        solarMax = new Company();
        solarMax.setName("SolarMax");
        solarMax.setEmail("solarmax@gmail.com");
        
        quantumTech = new Company();
        quantumTech.setName("QuantumTech");
        quantumTech.setEmail("quantumtech@example.com");
        
        neuralink = new Company();
        neuralink.setName("Neuralink");
        neuralink.setEmail("neuralink@example.com");
        
        techGiant = new Company();
        techGiant.setName("TechGiant");
        techGiant.setEmail("techgiant@example.com");
        
        autoFuture = new Company();
        autoFuture.setName("AutoFuture");
        autoFuture.setEmail("autofuture@example.com");
        
        aeroSpace = new Company();
        aeroSpace.setName("AeroSpace");
        aeroSpace.setEmail("aerospace@example.com");
        
        bioGenius = new Company();
        bioGenius.setName("BioGenius");
        bioGenius.setEmail("biogenius@example.com");
        
        greenEnergy = new Company();
        greenEnergy.setName("GreenEnergy");
        greenEnergy.setEmail("greenenergy@example.com");
        
        cloudServ = new Company();
        cloudServ.setName("CloudServ");
        cloudServ.setEmail("cloudserv@example.com");
        
        dataCore = new Company();
        dataCore.setName("DataCore");
        dataCore.setEmail("datacore@example.com");
        
        aiVentures = new Company();
        aiVentures.setName("AI Ventures");
        aiVentures.setEmail("aiventures@example.com");
        
        nanoTech = new Company();
        nanoTech.setName("NanoTech");
        nanoTech.setEmail("nanotech@example.com");
        
        roboWorks = new Company();
        roboWorks.setName("RoboWorks");
        roboWorks.setEmail("roboworks@example.com");
    }
    
    @Test
    public void testCase1_NoApprovedRequests() {
        // Setup customer C201
        customer = new Customer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);
        
        // Create pending application for TechInc
        Document doc1 = new Document();
        customer.createApplication(techInc, 10, 1500.0, doc1);
        
        // Create rejected application for BioMed
        Document doc2 = new Document();
        Application rejectedApp = new Application(bioMed, 10, 2000.0, doc2, customer);
        rejectedApp.reject();
        customer.getApplications().add(rejectedApp);
        
        // Test total approved amount
        double result = customer.getApprovedTotalAmount();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SingleApproval() {
        // Setup customer C202
        customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setCanApplyForIPO(true);
        
        // Create and approve application for SolarMax
        Document doc = new Document();
        customer.createApplication(solarMax, 84, 4200.0, doc);
        
        // Get the application and approve it
        Application app = customer.getApplications().get(0);
        app.approve();
        
        // Test total approved amount
        double result = customer.getApprovedTotalAmount();
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup customer C203
        customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setCanApplyForIPO(true);
        
        // Create and approve application for QuantumTech
        Document doc1 = new Document();
        customer.createApplication(quantumTech, 40, 2000.0, doc1);
        Application app1 = customer.getApplications().get(0);
        app1.approve();
        
        // Create and approve application for Neuralink
        Document doc2 = new Document();
        customer.createApplication(neuralink, 70, 3500.0, doc2);
        Application app2 = customer.getApplications().get(1);
        app2.approve();
        
        // Test total approved amount
        double result = customer.getApprovedTotalAmount();
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup customer C204
        customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setCanApplyForIPO(true);
        
        // Create and approve 5 applications, each with $10,000
        Document doc1 = new Document();
        customer.createApplication(techGiant, 200, 10000.0, doc1);
        Application app1 = customer.getApplications().get(0);
        app1.approve();
        
        Document doc2 = new Document();
        customer.createApplication(autoFuture, 250, 10000.0, doc2);
        Application app2 = customer.getApplications().get(1);
        app2.approve();
        
        Document doc3 = new Document();
        customer.createApplication(aeroSpace, 125, 10000.0, doc3);
        Application app3 = customer.getApplications().get(2);
        app3.approve();
        
        Document doc4 = new Document();
        customer.createApplication(bioGenius, 500, 10000.0, doc4);
        Application app4 = customer.getApplications().get(3);
        app4.approve();
        
        Document doc5 = new Document();
        customer.createApplication(greenEnergy, 200, 10000.0, doc5);
        Application app5 = customer.getApplications().get(4);
        app5.approve();
        
        // Test total approved amount
        double result = customer.getApprovedTotalAmount();
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup customer C205
        customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setCanApplyForIPO(true);
        
        // Create and approve 3 applications
        Document doc1 = new Document();
        customer.createApplication(cloudServ, 100, 3000.0, doc1);
        Application app1 = customer.getApplications().get(0);
        app1.approve();
        
        Document doc2 = new Document();
        customer.createApplication(dataCore, 20, 2750.0, doc2);
        Application app2 = customer.getApplications().get(1);
        app2.approve();
        
        Document doc3 = new Document();
        customer.createApplication(aiVentures, 30, 3000.0, doc3);
        Application app3 = customer.getApplications().get(2);
        app3.approve();
        
        // Create 2 pending applications (should not be counted)
        Document doc4 = new Document();
        customer.createApplication(nanoTech, 10, 600.0, doc4);
        
        Document doc5 = new Document();
        customer.createApplication(roboWorks, 50, 600.0, doc5);
        
        // Test total approved amount
        double result = customer.getApprovedTotalAmount();
        assertEquals(8750.0, result, 0.001);
    }
}