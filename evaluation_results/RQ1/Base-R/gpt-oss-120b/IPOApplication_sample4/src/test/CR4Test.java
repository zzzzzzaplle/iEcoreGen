import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private IPOSystem ipoSystem;
    
    @Before
    public void setUp() {
        ipoSystem = new IPOSystem();
    }
    
    @Test
    public void testCase1_noApprovedRequests() {
        // Setup: Customer "C201" with pending and rejected applications
        Customer customer = new Customer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setEligible(true);
        
        Company techInc = new Company();
        techInc.setName("TechInc");
        
        Company bioMed = new Company();
        bioMed.setName("BioMed");
        
        Document doc1 = new Document();
        doc1.setFileName("QT-3001");
        
        Document doc2 = new Document();
        doc2.setFileName("QT-3002");
        
        // Create pending application
        IPOApplication pendingApp = new IPOApplication();
        pendingApp.setCustomer(customer);
        pendingApp.setCompany(techInc);
        pendingApp.setShares(10);
        pendingApp.setAmount(1500.0);
        pendingApp.setDocument(doc1);
        pendingApp.setStatus(ApplicationStatus.PENDING);
        
        // Create rejected application
        IPOApplication rejectedApp = new IPOApplication();
        rejectedApp.setCustomer(customer);
        rejectedApp.setCompany(bioMed);
        rejectedApp.setShares(10);
        rejectedApp.setAmount(2000.0);
        rejectedApp.setDocument(doc2);
        rejectedApp.setStatus(ApplicationStatus.REJECTED);
        
        // Add applications to customer
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(pendingApp);
        applications.add(rejectedApp);
        customer.setApplications(applications);
        
        // Execute: Get total approved amount
        double result = ipoSystem.getTotalApprovedAmount(customer);
        
        // Verify: Should be 0 since no approved applications
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_singleApproval() {
        // Setup: Customer "C202" with one approved application
        Customer customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setEligible(true);
        
        Company solarMax = new Company();
        solarMax.setName("SolarMax");
        
        Document document = new Document();
        document.setFileName("SM-2024-Q1");
        
        // Create approved application
        IPOApplication approvedApp = new IPOApplication();
        approvedApp.setCustomer(customer);
        approvedApp.setCompany(solarMax);
        approvedApp.setShares(84);
        approvedApp.setAmount(4200.0);
        approvedApp.setDocument(document);
        approvedApp.setStatus(ApplicationStatus.APPROVED);
        
        // Add application to customer
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(approvedApp);
        customer.setApplications(applications);
        
        // Execute: Get total approved amount
        double result = ipoSystem.getTotalApprovedAmount(customer);
        
        // Verify: Should be 4200.0
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with two approved applications from different companies
        Customer customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setEligible(true);
        
        Company quantumTech = new Company();
        quantumTech.setName("QuantumTech");
        
        Company neuralink = new Company();
        neuralink.setName("Neuralink");
        
        Document doc1 = new Document();
        doc1.setFileName("SM-2024-Q3004");
        
        Document doc2 = new Document();
        doc2.setFileName("SM-2024-Q3005");
        
        // Create first approved application
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompany(quantumTech);
        app1.setShares(40);
        app1.setAmount(2000.0);
        app1.setDocument(doc1);
        app1.setStatus(ApplicationStatus.APPROVED);
        
        // Create second approved application
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompany(neuralink);
        app2.setShares(70);
        app2.setAmount(3500.0);
        app2.setDocument(doc2);
        app2.setStatus(ApplicationStatus.APPROVED);
        
        // Add applications to customer
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        customer.setApplications(applications);
        
        // Execute: Get total approved amount
        double result = ipoSystem.getTotalApprovedAmount(customer);
        
        // Verify: Should be 5500.0 (2000 + 3500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_largePortfolio() {
        // Setup: Customer "C204" with five approved applications, each $10,000
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setEligible(true);
        
        Company techGiant = new Company();
        techGiant.setName("TechGiant");
        
        Company autoFuture = new Company();
        autoFuture.setName("AutoFuture");
        
        Company aeroSpace = new Company();
        aeroSpace.setName("AeroSpace");
        
        Company bioGenius = new Company();
        bioGenius.setName("BioGenius");
        
        Company greenEnergy = new Company();
        greenEnergy.setName("GreenEnergy");
        
        Document doc1 = new Document();
        doc1.setFileName("SM-3006");
        
        Document doc2 = new Document();
        doc2.setFileName("SM-3007");
        
        Document doc3 = new Document();
        doc3.setFileName("SM-3008");
        
        Document doc4 = new Document();
        doc4.setFileName("SM-3009");
        
        Document doc5 = new Document();
        doc5.setFileName("SM-3010");
        
        // Create all approved applications
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompany(techGiant);
        app1.setShares(200);
        app1.setAmount(10000.0);
        app1.setDocument(doc1);
        app1.setStatus(ApplicationStatus.APPROVED);
        
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompany(autoFuture);
        app2.setShares(250);
        app2.setAmount(10000.0);
        app2.setDocument(doc2);
        app2.setStatus(ApplicationStatus.APPROVED);
        
        IPOApplication app3 = new IPOApplication();
        app3.setCustomer(customer);
        app3.setCompany(aeroSpace);
        app3.setShares(125);
        app3.setAmount(10000.0);
        app3.setDocument(doc3);
        app3.setStatus(ApplicationStatus.APPROVED);
        
        IPOApplication app4 = new IPOApplication();
        app4.setCustomer(customer);
        app4.setCompany(bioGenius);
        app4.setShares(500);
        app4.setAmount(10000.0);
        app4.setDocument(doc4);
        app4.setStatus(ApplicationStatus.APPROVED);
        
        IPOApplication app5 = new IPOApplication();
        app5.setCustomer(customer);
        app5.setCompany(greenEnergy);
        app5.setShares(200);
        app5.setAmount(10000.0);
        app5.setDocument(doc5);
        app5.setStatus(ApplicationStatus.APPROVED);
        
        // Add all applications to customer
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        applications.add(app3);
        applications.add(app4);
        applications.add(app5);
        customer.setApplications(applications);
        
        // Execute: Get total approved amount
        double result = ipoSystem.getTotalApprovedAmount(customer);
        
        // Verify: Should be 50000.0 (5 * 10000)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup: Customer "C205" with approved and pending applications
        Customer customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setEligible(true);
        
        Company cloudServ = new Company();
        cloudServ.setName("CloudServ");
        
        Company dataCore = new Company();
        dataCore.setName("DataCore");
        
        Company aiVentures = new Company();
        aiVentures.setName("AI Ventures");
        
        Company nanoTech = new Company();
        nanoTech.setName("NanoTech");
        
        Company roboWorks = new Company();
        roboWorks.setName("RoboWorks");
        
        Document doc1 = new Document();
        doc1.setFileName("SM-3011");
        
        Document doc2 = new Document();
        doc2.setFileName("SM-3012");
        
        Document doc3 = new Document();
        doc3.setFileName("SM-3013");
        
        Document doc4 = new Document();
        doc4.setFileName("SM-3014");
        
        Document doc5 = new Document();
        doc5.setFileName("SM-3015");
        
        // Create approved applications
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompany(cloudServ);
        app1.setShares(100);
        app1.setAmount(3000.0);
        app1.setDocument(doc1);
        app1.setStatus(ApplicationStatus.APPROVED);
        
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompany(dataCore);
        app2.setShares(20);
        app2.setAmount(2750.0);
        app2.setDocument(doc2);
        app2.setStatus(ApplicationStatus.APPROVED);
        
        IPOApplication app3 = new IPOApplication();
        app3.setCustomer(customer);
        app3.setCompany(aiVentures);
        app3.setShares(30);
        app3.setAmount(3000.0);
        app3.setDocument(doc3);
        app3.setStatus(ApplicationStatus.APPROVED);
        
        // Create pending applications
        IPOApplication app4 = new IPOApplication();
        app4.setCustomer(customer);
        app4.setCompany(nanoTech);
        app4.setShares(10);
        app4.setAmount(600.0);
        app4.setDocument(doc4);
        app4.setStatus(ApplicationStatus.PENDING);
        
        IPOApplication app5 = new IPOApplication();
        app5.setCustomer(customer);
        app5.setCompany(roboWorks);
        app5.setShares(50);
        app5.setAmount(600.0);
        app5.setDocument(doc5);
        app5.setStatus(ApplicationStatus.PENDING);
        
        // Add all applications to customer
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        applications.add(app3);
        applications.add(app4);
        applications.add(app5);
        customer.setApplications(applications);
        
        // Execute: Get total approved amount
        double result = ipoSystem.getTotalApprovedAmount(customer);
        
        // Verify: Should be 8750.0 (3000 + 2750 + 3000)
        assertEquals(8750.0, result, 0.001);
    }
}