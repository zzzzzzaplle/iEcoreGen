package edu.ipo.ipo5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.ipo.Application;
import edu.ipo.ApplicationStatus;
import edu.ipo.Company;
import edu.ipo.Customer;
import edu.ipo.Document;
import edu.ipo.IpoFactory;

public class CR4Test {
    
    private IpoFactory factory;
    
    @Before
    public void setUp() {
        factory = IpoFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_NoApprovedRequests() {
        // Setup: Create customer C201
        Customer customer = factory.createCustomer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);
        
        // Create companies
        Company techInc = factory.createCompany();
        techInc.setName("TechInc");
        techInc.setEmail("techinc@example.com");
        
        Company bioMed = factory.createCompany();
        bioMed.setName("BioMed");
        bioMed.setEmail("biomed@example.com");
        
        // Create documents
        Document doc1 = factory.createDocument();
        Document doc2 = factory.createDocument();
        
        // Create PENDING application
        Application app1 = factory.createApplication();
        app1.setShare(10);
        app1.setAmountOfMoney(1500.0);
        app1.setStatus(ApplicationStatus.PENDING);
        app1.setCompany(techInc);
        app1.setAllowance(doc1);
        app1.setCustomer(customer);
        customer.getApplications().add(app1);
        
        // Create REJECTED application
        Application app2 = factory.createApplication();
        app2.setShare(10);
        app2.setAmountOfMoney(2000.0);
        app2.setStatus(ApplicationStatus.REJECTED);
        app2.setCompany(bioMed);
        app2.setAllowance(doc2);
        app2.setCustomer(customer);
        customer.getApplications().add(app2);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should be 0 since no approved applications
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SingleApproval() {
        // Setup: Create customer C202
        Customer customer = factory.createCustomer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setCanApplyForIPO(true);
        
        // Create company
        Company solarMax = factory.createCompany();
        solarMax.setName("SolarMax");
        solarMax.setEmail("solarmax@gmail.com");
        
        // Create document
        Document doc = factory.createDocument();
        
        // Create APPROVED application
        Application app = factory.createApplication();
        app.setShare(84);
        app.setAmountOfMoney(4200.0);
        app.setStatus(ApplicationStatus.APPROVAL);
        app.setCompany(solarMax);
        app.setAllowance(doc);
        app.setCustomer(customer);
        customer.getApplications().add(app);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should be 4200.0
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup: Create customer C203
        Customer customer = factory.createCustomer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setCanApplyForIPO(true);
        
        // Create companies
        Company quantumTech = factory.createCompany();
        quantumTech.setName("QuantumTech");
        quantumTech.setEmail("quantumtech@example.com");
        
        Company neuralink = factory.createCompany();
        neuralink.setName("Neuralink");
        neuralink.setEmail("neuralink@example.com");
        
        // Create documents
        Document doc1 = factory.createDocument();
        Document doc2 = factory.createDocument();
        
        // Create first APPROVED application
        Application app1 = factory.createApplication();
        app1.setShare(40);
        app1.setAmountOfMoney(2000.0);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCompany(quantumTech);
        app1.setAllowance(doc1);
        app1.setCustomer(customer);
        customer.getApplications().add(app1);
        
        // Create second APPROVED application
        Application app2 = factory.createApplication();
        app2.setShare(70);
        app2.setAmountOfMoney(3500.0);
        app2.setStatus(ApplicationStatus.APPROVAL);
        app2.setCompany(neuralink);
        app2.setAllowance(doc2);
        app2.setCustomer(customer);
        customer.getApplications().add(app2);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should be 5500.0 (2000 + 3500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup: Create customer C204
        Customer customer = factory.createCustomer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setCanApplyForIPO(true);
        
        // Create companies
        Company techGiant = factory.createCompany();
        techGiant.setName("TechGiant");
        techGiant.setEmail("techgiant@example.com");
        
        Company autoFuture = factory.createCompany();
        autoFuture.setName("AutoFuture");
        autoFuture.setEmail("autofuture@example.com");
        
        Company aeroSpace = factory.createCompany();
        aeroSpace.setName("AeroSpace");
        aeroSpace.setEmail("aerospace@example.com");
        
        Company bioGenius = factory.createCompany();
        bioGenius.setName("BioGenius");
        bioGenius.setEmail("biogenius@example.com");
        
        Company greenEnergy = factory.createCompany();
        greenEnergy.setName("GreenEnergy");
        greenEnergy.setEmail("greenenergy@example.com");
        
        // Create documents
        Document doc1 = factory.createDocument();
        Document doc2 = factory.createDocument();
        Document doc3 = factory.createDocument();
        Document doc4 = factory.createDocument();
        Document doc5 = factory.createDocument();
        
        // Create 5 APPROVED applications, each with $10,000
        Application app1 = factory.createApplication();
        app1.setShare(200);
        app1.setAmountOfMoney(10000.0);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCompany(techGiant);
        app1.setAllowance(doc1);
        app1.setCustomer(customer);
        customer.getApplications().add(app1);
        
        Application app2 = factory.createApplication();
        app2.setShare(250);
        app2.setAmountOfMoney(10000.0);
        app2.setStatus(ApplicationStatus.APPROVAL);
        app2.setCompany(autoFuture);
        app2.setAllowance(doc2);
        app2.setCustomer(customer);
        customer.getApplications().add(app2);
        
        Application app3 = factory.createApplication();
        app3.setShare(125);
        app3.setAmountOfMoney(10000.0);
        app3.setStatus(ApplicationStatus.APPROVAL);
        app3.setCompany(aeroSpace);
        app3.setAllowance(doc3);
        app3.setCustomer(customer);
        customer.getApplications().add(app3);
        
        Application app4 = factory.createApplication();
        app4.setShare(500);
        app4.setAmountOfMoney(10000.0);
        app4.setStatus(ApplicationStatus.APPROVAL);
        app4.setCompany(bioGenius);
        app4.setAllowance(doc4);
        app4.setCustomer(customer);
        customer.getApplications().add(app4);
        
        Application app5 = factory.createApplication();
        app5.setShare(200);
        app5.setAmountOfMoney(10000.0);
        app5.setStatus(ApplicationStatus.APPROVAL);
        app5.setCompany(greenEnergy);
        app5.setAllowance(doc5);
        app5.setCustomer(customer);
        customer.getApplications().add(app5);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should be 50000.0 (5 * 10000)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup: Create customer C205
        Customer customer = factory.createCustomer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setCanApplyForIPO(true);
        
        // Create companies for approved applications
        Company cloudServ = factory.createCompany();
        cloudServ.setName("CloudServ");
        cloudServ.setEmail("cloudserv@example.com");
        
        Company dataCore = factory.createCompany();
        dataCore.setName("DataCore");
        dataCore.setEmail("datacore@example.com");
        
        Company aiVentures = factory.createCompany();
        aiVentures.setName("AI Ventures");
        aiVentures.setEmail("aiventures@example.com");
        
        // Create companies for pending applications
        Company nanoTech = factory.createCompany();
        nanoTech.setName("NanoTech");
        nanoTech.setEmail("nanotech@example.com");
        
        Company roboWorks = factory.createCompany();
        roboWorks.setName("RoboWorks");
        roboWorks.setEmail("roboworks@example.com");
        
        // Create documents
        Document doc1 = factory.createDocument();
        Document doc2 = factory.createDocument();
        Document doc3 = factory.createDocument();
        Document doc4 = factory.createDocument();
        Document doc5 = factory.createDocument();
        
        // Create APPROVED applications
        Application app1 = factory.createApplication();
        app1.setShare(100);
        app1.setAmountOfMoney(3000.0);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCompany(cloudServ);
        app1.setAllowance(doc1);
        app1.setCustomer(customer);
        customer.getApplications().add(app1);
        
        Application app2 = factory.createApplication();
        app2.setShare(20);
        app2.setAmountOfMoney(2750.0);
        app2.setStatus(ApplicationStatus.APPROVAL);
        app2.setCompany(dataCore);
        app2.setAllowance(doc2);
        app2.setCustomer(customer);
        customer.getApplications().add(app2);
        
        Application app3 = factory.createApplication();
        app3.setShare(30);
        app3.setAmountOfMoney(3000.0);
        app3.setStatus(ApplicationStatus.APPROVAL);
        app3.setCompany(aiVentures);
        app3.setAllowance(doc3);
        app3.setCustomer(customer);
        customer.getApplications().add(app3);
        
        // Create PENDING applications (should not be counted)
        Application app4 = factory.createApplication();
        app4.setShare(10);
        app4.setAmountOfMoney(600.0);
        app4.setStatus(ApplicationStatus.PENDING);
        app4.setCompany(nanoTech);
        app4.setAllowance(doc4);
        app4.setCustomer(customer);
        customer.getApplications().add(app4);
        
        Application app5 = factory.createApplication();
        app5.setShare(50);
        app5.setAmountOfMoney(600.0);
        app5.setStatus(ApplicationStatus.PENDING);
        app5.setCompany(roboWorks);
        app5.setAllowance(doc5);
        app5.setCustomer(customer);
        customer.getApplications().add(app5);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should be 8750.0 (3000 + 2750 + 3000)
        assertEquals(8750.0, result, 0.001);
    }
}