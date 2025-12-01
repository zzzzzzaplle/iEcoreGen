import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Customer customer101;
    private Customer customer102;
    private Customer customer103;
    private Customer customer104;
    private Customer customer105;
    
    private Company quantumTech;
    private Company neuralink;
    private Company spaceY;
    private Company bioGen;
    private Company roboCorp;
    private Company aiVentures;
    private Company nanoMed;
    private Company greenEnergy;
    private Company cloudScale;
    private Company cloudCompany;
    
    private Document docQT2024FormA;
    private Document docQT22023101;
    private Document docQT2023102;
    private Document docQT2024002;
    private Document docQT2023105;
    private Document docQT2023106;
    private Document docQT2024003;
    private Document docQT2024004;
    private Document docQT2024005;
    private Document docQT1010;
    
    @Before
    public void setUp() {
        // Initialize customers
        customer101 = new Customer();
        customer101.setName("Thomas");
        customer101.setSurname("Anderson");
        customer101.setEmail("t.anderson@example.com");
        customer101.setTelephone("555-0101");
        customer101.setCanApplyForIPO(true);
        
        customer102 = new Customer();
        customer102.setName("Lisa");
        customer102.setSurname("Rodriguez");
        customer102.setEmail("l.rodriguez@example.com");
        customer102.setTelephone("555-0202");
        customer102.setCanApplyForIPO(true);
        
        customer103 = new Customer();
        customer103.setName("David");
        customer103.setSurname("Kim");
        customer103.setEmail("d.kim@example.com");
        customer103.setTelephone("555-0303");
        customer103.setCanApplyForIPO(false);
        
        customer104 = new Customer();
        customer104.setName("Emma");
        customer104.setSurname("Wilson");
        customer104.setEmail("e.wilson@example.com");
        customer104.setTelephone("555-0404");
        customer104.setCanApplyForIPO(true);
        
        customer105 = new Customer();
        customer105.setName("James");
        customer105.setSurname("Chen");
        customer105.setEmail("j.chen@example.com");
        customer105.setTelephone("555-0505");
        customer105.setCanApplyForIPO(true);
        
        // Initialize companies
        quantumTech = new Company();
        quantumTech.setName("QuantumTech");
        quantumTech.setEmail("quantumtech@gmail.com");
        
        neuralink = new Company();
        neuralink.setName("Neuralink");
        neuralink.setEmail("neuralink@gmail.com");
        
        spaceY = new Company();
        spaceY.setName("SpaceY");
        spaceY.setEmail("spacey@gmail.com");
        
        bioGen = new Company();
        bioGen.setName("BioGen");
        bioGen.setEmail("biogen@gmail.com");
        
        roboCorp = new Company();
        roboCorp.setName("RoboCorp");
        roboCorp.setEmail("robocorp@gmail.com");
        
        aiVentures = new Company();
        aiVentures.setName("AI Ventures");
        aiVentures.setEmail("aiventures@gmail.com");
        
        nanoMed = new Company();
        nanoMed.setName("NanoMed");
        nanoMed.setEmail("nanomed@gmail.com");
        
        greenEnergy = new Company();
        greenEnergy.setName("GreenEnergy");
        greenEnergy.setEmail("greenenergy@gmail.com");
        
        cloudScale = new Company();
        cloudScale.setName("CloudScale");
        cloudScale.setEmail("cloudscale@gmail.com");
        
        cloudCompany = new Company();
        cloudCompany.setName("Cloud");
        cloudCompany.setEmail("Cloud@gmail.com");
        
        // Initialize documents
        docQT2024FormA = new Document();
        docQT22023101 = new Document();
        docQT2023102 = new Document();
        docQT2024002 = new Document();
        docQT2023105 = new Document();
        docQT2023106 = new Document();
        docQT2024003 = new Document();
        docQT2024004 = new Document();
        docQT2024005 = new Document();
        docQT1010 = new Document();
    }
    
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Test Case 1: "No applications at all"
        // Input: Customer "C101" requests a count summary.
        // Setup:
        // 1. Customer "C101" (named "Thomas Anderson", email "t.anderson@example.com", phone "555-0101", can apply for IPO) 
        // 2. No IPO requests have ever been filed.
        // Expected Output: 0
        
        int result = customer101.getApplicationCount();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_singleApprovedRequest() {
        // Test Case 2: "Single pending request" 
        // Note: Based on expected output of 1 and the setup mentioning "approval" status, this is actually a single approved request
        // Input: Customer "C102" asks for the total number of filings.
        // Setup:
        // 1. Customer "C102" (named "Lisa Rodriguez", email "l.rodriguez@example.com", phone "555-0202", can apply for IPO)
        // 2. One record exists in pending status:
        //     Application ID: "APP-2024-001"
        //     Company: "QuantumTech" (quantumtech@gmail.com)
        //     Shares: 50 ($2,500)
        //     Document: 'QT-2024-FormA'
        //     Status: approval
        // Expected Output: 1
        
        // Create and add application
        Application app = new Application();
        app.setCustomer(customer102);
        app.setCompany(quantumTech);
        app.setShare(50);
        app.setAmountOfMoney(2500.0);
        app.setAllowance(docQT2024FormA);
        app.setStatus(ApplicationStatus.APPROVAL); // Set to approval as expected output is 1
        customer102.getApplications().add(app);
        
        int result = customer102.getApplicationCount();
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Test Case 3: "Mix of approved and rejected"
        // Input: Customer "C103" checks total filings.
        // Setup:
        // 1. Customer "C103" (named "David Kim", email "d.kim@example.com", phone "555-0303", can not apply for IPO) 
        // 2. Two APPROVAL records and one REJECTED record are stored:
        // - Approved applications:
        //     "APP-2023-101" (Neuralink, 100 shares/$10,000, Document: 'QT-22023-101')
        //     "APP-2023-102" (SpaceY, 30 shares/$15,000, Document: 'QT-2023-102')
        // - Rejected application:
        //     "APP-2024-002" (BioGen, 20 shares/$1,000, Document: 'QT-2024-002')
        // Expected Output: 3
        
        // Create and add first approved application
        Application app1 = new Application();
        app1.setCustomer(customer103);
        app1.setCompany(neuralink);
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setAllowance(docQT22023101);
        app1.setStatus(ApplicationStatus.APPROVAL);
        customer103.getApplications().add(app1);
        
        // Create and add second approved application
        Application app2 = new Application();
        app2.setCustomer(customer103);
        app2.setCompany(spaceY);
        app2.setShare(30);
        app2.setAmountOfMoney(15000.0);
        app2.setAllowance(docQT2023102);
        app2.setStatus(ApplicationStatus.APPROVAL);
        customer103.getApplications().add(app2);
        
        // Create and add rejected application
        Application app3 = new Application();
        app3.setCustomer(customer103);
        app3.setCompany(bioGen);
        app3.setShare(20);
        app3.setAmountOfMoney(1000.0);
        app3.setAllowance(docQT2024002);
        app3.setStatus(ApplicationStatus.REJECTED);
        customer103.getApplications().add(app3);
        
        int result = customer103.getApplicationCount();
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Test Case 4: "Five historical requests"
        // Input: Customer "C104" queries the overall count.
        // Setup:
        // 1. Customer "C104" (named "Emma Wilson", email "e.wilson@example.com", phone "555-0404", can apply for IPO)
        // 2. Five records exist: 1 APPROVAL, 2 REJECTED, 2 pending:
        // - APPROVED: "APP-2023-105" (RoboCorp, 100 shares/$10,000, Document: 'QT-2023-105')
        // - REJECTED:
        //     "APP-2023-106" (AI Ventures, 100 shares/$10,000, Document: 'QT-2023-106')
        //     "APP-2024-003" (NanoMed, 100 shares/$10,000, Document: 'QT-2024-003')
        // - PENDING:
        //     "APP-2024-004" (GreenEnergy, 100 shares/$10,000, Document: 'QT-2024-004')
        //     "APP-2024-005" (CloudScale, 100 shares/$10,000, Document: 'QT-2024-005')
        // Expected Output: 3
        
        // Create and add approved application
        Application app1 = new Application();
        app1.setCustomer(customer104);
        app1.setCompany(roboCorp);
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setAllowance(docQT2023105);
        app1.setStatus(ApplicationStatus.APPROVAL);
        customer104.getApplications().add(app1);
        
        // Create and add first rejected application
        Application app2 = new Application();
        app2.setCustomer(customer104);
        app2.setCompany(aiVentures);
        app2.setShare(100);
        app2.setAmountOfMoney(10000.0);
        app2.setAllowance(docQT2023106);
        app2.setStatus(ApplicationStatus.REJECTED);
        customer104.getApplications().add(app2);
        
        // Create and add second rejected application
        Application app3 = new Application();
        app3.setCustomer(customer104);
        app3.setCompany(nanoMed);
        app3.setShare(100);
        app3.setAmountOfMoney(10000.0);
        app3.setAllowance(docQT2024003);
        app3.setStatus(ApplicationStatus.REJECTED);
        customer104.getApplications().add(app3);
        
        // Create and add first pending application
        Application app4 = new Application();
        app4.setCustomer(customer104);
        app4.setCompany(greenEnergy);
        app4.setShare(100);
        app4.setAmountOfMoney(10000.0);
        app4.setAllowance(docQT2024004);
        app4.setStatus(ApplicationStatus.PENDING);
        customer104.getApplications().add(app4);
        
        // Create and add second pending application
        Application app5 = new Application();
        app5.setCustomer(customer104);
        app5.setCompany(cloudScale);
        app5.setShare(100);
        app5.setAmountOfMoney(10000.0);
        app5.setAllowance(docQT2024005);
        app5.setStatus(ApplicationStatus.PENDING);
        customer104.getApplications().add(app5);
        
        int result = customer104.getApplicationCount();
        assertEquals(3, result); // Only approved and rejected count (1 + 2 = 3)
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Test Case 5: "All requests canceled"
        // Input: Customer "C105" asks for the figure.
        // Setup:
        // 1. Customer "C105" (named "James Chen", email "j.chen@example.com", phone "555-0505", can apply for IPO)
        // 2. Create a pending application "APP-1010" : Applied for 10 shares ($5000) in "Cloud" (email: Cloud@gmail.com), Document: 'QT-1010'
        // 3. Cancel application "APP-1010" to "Cloud"
        // Expected Output: 0
        
        // Create a pending application
        Application app = new Application();
        app.setCustomer(customer105);
        app.setCompany(cloudCompany);
        app.setShare(10);
        app.setAmountOfMoney(5000.0);
        app.setAllowance(docQT1010);
        app.setStatus(ApplicationStatus.PENDING);
        customer105.getApplications().add(app);
        
        // Cancel the application
        boolean canceled = app.cancel();
        assertTrue(canceled);
        assertEquals(ApplicationStatus.REJECTED, app.getStatus()); // Canceled applications are marked as rejected
        
        int result = customer105.getApplicationCount();
        assertEquals(0, result); // Canceled (rejected) applications don't count until they were approved/rejected before cancelation
        // Note: The cancel() method changes status to REJECTED, but since it was pending and then rejected,
        // it should not count towards the application count according to the specification.
    }
}