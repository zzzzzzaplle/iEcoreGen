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
        // Initialize companies
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
        
        // Initialize documents
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
        // Setup customer C201: Emily Chen
        customer = new Customer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);
        
        // Create PENDING application APP-3001
        customer.createApplication(techInc, 10, 1500.0, doc1);
        
        // Create REJECTED application APP-3002
        Application app2 = new Application();
        app2.setCustomer(customer);
        app2.setCompany(bioMed);
        app2.setShare(10);
        app2.setAmountOfMoney(2000.0);
        app2.setAllowance(doc2);
        app2.setStatus(ApplicationStatus.REJECTED);
        customer.getApplications().add(app2);
        
        // Verify that there are no approved applications
        assertEquals(0.0, customer.getApprovedTotalAmount(), 0.01);
    }

    @Test
    public void testCase2_singleApproval() {
        // Setup customer C202: Robert Johnson
        customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setCanApplyForIPO(true);
        
        // Create approved application APP-3003
        Application app = new Application();
        app.setCustomer(customer);
        app.setCompany(solarMax);
        app.setShare(84);
        app.setAmountOfMoney(4200.0);
        app.setAllowance(doc3);
        app.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app);
        
        // Verify the total approved amount is 4200
        assertEquals(4200.0, customer.getApprovedTotalAmount(), 0.01);
    }

    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup customer C203: Sophia Williams
        customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setCanApplyForIPO(true);
        
        // Create first approved application APP-3004
        Application app1 = new Application();
        app1.setCustomer(customer);
        app1.setCompany(quantumTech);
        app1.setShare(40);
        app1.setAmountOfMoney(2000.0);
        app1.setAllowance(doc4);
        app1.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app1);
        
        // Create second approved application APP-3005
        Application app2 = new Application();
        app2.setCustomer(customer);
        app2.setCompany(neuralink);
        app2.setShare(70);
        app2.setAmountOfMoney(3500.0);
        app2.setAllowance(doc5);
        app2.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app2);
        
        // Verify the total approved amount is 5500 (2000 + 3500)
        assertEquals(5500.0, customer.getApprovedTotalAmount(), 0.01);
    }

    @Test
    public void testCase4_largePortfolio() {
        // Setup customer C204: James Wilson
        customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setCanApplyForIPO(true);
        
        // Create 5 approved applications, each with $10,000
        Application app1 = new Application();
        app1.setCustomer(customer);
        app1.setCompany(techGiant);
        app1.setShare(200);
        app1.setAmountOfMoney(10000.0);
        app1.setAllowance(doc6);
        app1.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app1);
        
        Application app2 = new Application();
        app2.setCustomer(customer);
        app2.setCompany(autoFuture);
        app2.setShare(250);
        app2.setAmountOfMoney(10000.0);
        app2.setAllowance(doc7);
        app2.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app2);
        
        Application app3 = new Application();
        app3.setCustomer(customer);
        app3.setCompany(aeroSpace);
        app3.setShare(125);
        app3.setAmountOfMoney(10000.0);
        app3.setAllowance(doc8);
        app3.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app3);
        
        Application app4 = new Application();
        app4.setCustomer(customer);
        app4.setCompany(bioGenius);
        app4.setShare(500);
        app4.setAmountOfMoney(10000.0);
        app4.setAllowance(doc9);
        app4.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app4);
        
        Application app5 = new Application();
        app5.setCustomer(customer);
        app5.setCompany(greenEnergy);
        app5.setShare(200);
        app5.setAmountOfMoney(10000.0);
        app5.setAllowance(doc10);
        app5.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app5);
        
        // Verify the total approved amount is 50000 (5 * 10000)
        assertEquals(50000.0, customer.getApprovedTotalAmount(), 0.01);
    }

    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup customer C205: Olivia Brown
        customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setCanApplyForIPO(true);
        
        // Create approved applications
        Application app1 = new Application();
        app1.setCustomer(customer);
        app1.setCompany(cloudServ);
        app1.setShare(100);
        app1.setAmountOfMoney(3000.0);
        app1.setAllowance(doc11);
        app1.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app1);
        
        Application app2 = new Application();
        app2.setCustomer(customer);
        app2.setCompany(dataCore);
        app2.setShare(20);
        app2.setAmountOfMoney(2750.0);
        app2.setAllowance(doc12);
        app2.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app2);
        
        Application app3 = new Application();
        app3.setCustomer(customer);
        app3.setCompany(aiVentures);
        app3.setShare(30);
        app3.setAmountOfMoney(3000.0);
        app3.setAllowance(doc13);
        app3.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app3);
        
        // Create pending applications
        Application app4 = new Application();
        app4.setCustomer(customer);
        app4.setCompany(nanoTech);
        app4.setShare(10);
        app4.setAmountOfMoney(600.0);
        app4.setAllowance(doc14);
        app4.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(app4);
        
        Application app5 = new Application();
        app5.setCustomer(customer);
        app5.setCompany(roboWorks);
        app5.setShare(50);
        app5.setAmountOfMoney(600.0);
        app5.setAllowance(doc15);
        app5.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(app5);
        
        // Verify the total approved amount is 8750 (3000 + 2750 + 3000)
        assertEquals(8750.0, customer.getApprovedTotalAmount(), 0.01);
    }
}