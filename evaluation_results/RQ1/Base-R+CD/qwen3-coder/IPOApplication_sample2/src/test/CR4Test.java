import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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
    private Document doc1;
    private Document doc2;
    private Document doc3;
    private Document doc4;
    private Document doc5;
    private Document doc6;
    private Document doc7;
    private Document doc8;
    private Document doc9;
    private Document doc10;
    private Document doc11;
    private Document doc12;
    private Document doc13;
    private Document doc14;
    private Document doc15;

    @Before
    public void setUp() {
        // Initialize common objects used in tests
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
        bioGenius.setEmail("biogenesis@example.com");
        
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
        
        doc1 = new Document();
        doc2 = new Document();
        doc3 = new Document();
        doc4 = new Document();
        doc5 = new Document();
        doc6 = new Document();
        doc7 = new Document();
        doc8 = new Document();
        doc9 = new Document();
        doc10 = new Document();
        doc11 = new Document();
        doc12 = new Document();
        doc13 = new Document();
        doc14 = new Document();
        doc15 = new Document();
    }

    @Test
    public void testCase1_noApprovedRequests() {
        // Setup: Customer "C201" (named "Emily Chen", email "e.chen@example.com", phone "555-1212")
        customer = new Customer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);
        
        // Setup Application history:
        // - "APP-3001": PENDING (10 shares, $1,500, TechInc, document: "QT-3001")
        customer.createApplication(techInc, 10, 1500.0, doc1);
        
        // - "APP-3002": REJECTED (10 shares, $2,000, BioMed, document: "QT-3002")
        Application app2 = new Application();
        app2.setShare(10);
        app2.setAmountOfMoney(2000.0);
        app2.setStatus(ApplicationStatus.REJECTED);
        app2.setCustomer(customer);
        app2.setCompany(bioMed);
        app2.setAllowance(doc2);
        customer.getApplications().add(app2);
        
        // Verify that the first application is PENDING
        assertEquals(ApplicationStatus.PENDING, customer.getApplications().get(0).getStatus());
        // Verify that the second application is REJECTED
        assertEquals(ApplicationStatus.REJECTED, customer.getApplications().get(1).getStatus());
        
        // Execute: Customer "C201" requests total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.01);
    }

    @Test
    public void testCase2_singleApproval() {
        // Setup: Customer "C202" (named "Robert Johnson", email "r.johnson@example.com", phone "555-2323")
        customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setCanApplyForIPO(true);
        
        // Setup Approved application:
        // ID: "APP-3003"
        // Company: "SolarMax" (solarmax@gmail.com)
        // Amount: $4,200
        // Shares: 84
        // Document: 'SM-2024-Q1'
        customer.createApplication(solarMax, 84, 4200.0, doc3);
        // Approve the application
        customer.getApplications().get(0).approve();
        
        // Verify that the application is APPROVED
        assertEquals(ApplicationStatus.APPROVAL, customer.getApplications().get(0).getStatus());
        
        // Execute: Customer "C202" checks the total approved sum
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 4,200
        assertEquals(4200.0, result, 0.01);
    }

    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" (named "Sophia Williams", email "s.williams@example.com", phone "555-3434")
        customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setCanApplyForIPO(true);
        
        // Setup Approved applications:
        // - "APP-3004": QuantumTech, $2,000, 40 shares, Document: 'SM-2024-Q3004'
        customer.createApplication(quantumTech, 40, 2000.0, doc4);
        customer.getApplications().get(0).approve();
        
        // - "APP-3005": Neuralink, $3,500, 70 shares, Document: 'SM-2024-Q3005'
        customer.createApplication(neuralink, 70, 3500.0, doc5);
        customer.getApplications().get(1).approve();
        
        // Verify that both applications are APPROVED
        assertEquals(ApplicationStatus.APPROVAL, customer.getApplications().get(0).getStatus());
        assertEquals(ApplicationStatus.APPROVAL, customer.getApplications().get(1).getStatus());
        
        // Execute: Customer "C203" asks for the combined figure
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 5,500
        assertEquals(5500.0, result, 0.01);
    }

    @Test
    public void testCase4_largePortfolio() {
        // Setup: Customer "C204" (named "James Wilson", email "j.wilson@vip.example.com", phone "555-4545")
        customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setCanApplyForIPO(true);
        
        // Setup Approved applications (all $10,000 each):
        // "APP-3006": TechGiant (200 shares, Document: 'SM-3006')
        customer.createApplication(techGiant, 200, 10000.0, doc6);
        customer.getApplications().get(0).approve();
        
        // "APP-3007": AutoFuture (250 shares, Document: 'SM-3007')
        customer.createApplication(autoFuture, 250, 10000.0, doc7);
        customer.getApplications().get(1).approve();
        
        // "APP-3008": AeroSpace (125 shares, Document: 'SM-3008')
        customer.createApplication(aeroSpace, 125, 10000.0, doc8);
        customer.getApplications().get(2).approve();
        
        // "APP-3009": BioGenius (500 shares, Document: 'SM-3009')
        customer.createApplication(bioGenius, 500, 10000.0, doc9);
        customer.getApplications().get(3).approve();
        
        // "APP-3010": GreenEnergy (200 shares, Document: 'SM-3010')
        customer.createApplication(greenEnergy, 200, 10000.0, doc10);
        customer.getApplications().get(4).approve();
        
        // Verify that all applications are APPROVED
        for (Application app : customer.getApplications()) {
            assertEquals(ApplicationStatus.APPROVAL, app.getStatus());
        }
        
        // Execute: Customer "C204" requests total amount
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 50,000
        assertEquals(50000.0, result, 0.01);
    }

    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup: Customer "C205" (named "Olivia Brown", email "o.brown@example.com", phone "555-5656")
        customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setCanApplyForIPO(true);
        
        // Setup Approved applications:
        // - "APP-3011": 100 shares, $3,000 (CloudServ), Document: 'SM-3011'
        customer.createApplication(cloudServ, 100, 3000.0, doc11);
        customer.getApplications().get(0).approve();
        
        // - "APP-3012": 20 shares, $2,750 (DataCore), Document: 'SM-3012'
        customer.createApplication(dataCore, 20, 2750.0, doc12);
        customer.getApplications().get(1).approve();
        
        // - "APP-3013": 30 shares, $3,000 (AI Ventures), Document: 'SM-3013'
        customer.createApplication(aiVentures, 30, 3000.0, doc13);
        customer.getApplications().get(2).approve();
        
        // Setup Pending applications:
        // - "APP-3014": 10 shares, $600 (NanoTech), Document: 'SM-3014'
        customer.createApplication(nanoTech, 10, 600.0, doc14);
        // Application remains PENDING by default
        
        // - "APP-3015": 50 shares, $600 (RoboWorks), Document: 'SM-3015'
        customer.createApplication(roboWorks, 50, 600.0, doc15);
        // Application remains PENDING by default
        
        // Verify statuses
        assertEquals(ApplicationStatus.APPROVAL, customer.getApplications().get(0).getStatus());
        assertEquals(ApplicationStatus.APPROVAL, customer.getApplications().get(1).getStatus());
        assertEquals(ApplicationStatus.APPROVAL, customer.getApplications().get(2).getStatus());
        assertEquals(ApplicationStatus.PENDING, customer.getApplications().get(3).getStatus());
        assertEquals(ApplicationStatus.PENDING, customer.getApplications().get(4).getStatus());
        
        // Execute: Customer "C205" asks for aggregate approved spending
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 8,750
        assertEquals(8750.0, result, 0.01);
    }
}