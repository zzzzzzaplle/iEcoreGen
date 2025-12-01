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
    private Company cloud;
    
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
        
        cloud = new Company();
        cloud.setName("Cloud");
        cloud.setEmail("Cloud@gmail.com");
        
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
        // Test Case 1: No applications at all
        // Customer C101 requests a count summary with no applications
        
        int result = customer101.getApplicationCount();
        
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_singlePendingRequest() {
        // Test Case 2: Single pending request
        // Customer C102 has one APPROVAL application
        
        // Create and add approved application
        Application app = new Application();
        app.setShare(50);
        app.setAmountOfMoney(2500);
        app.setStatus(ApplicationStatus.APPROVAL);
        app.setCustomer(customer102);
        app.setCompany(quantumTech);
        app.setAllowance(docQT2024FormA);
        
        List<Application> apps = new ArrayList<>();
        apps.add(app);
        customer102.setApplications(apps);
        
        int result = customer102.getApplicationCount();
        
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Test Case 3: Mix of approved and rejected
        // Customer C103 has two APPROVAL and one REJECTED applications
        
        // Create approved applications
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(10000);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCustomer(customer103);
        app1.setCompany(neuralink);
        app1.setAllowance(docQT22023101);
        
        Application app2 = new Application();
        app2.setShare(30);
        app2.setAmountOfMoney(15000);
        app2.setStatus(ApplicationStatus.APPROVAL);
        app2.setCustomer(customer103);
        app2.setCompany(spaceY);
        app2.setAllowance(docQT2023102);
        
        // Create rejected application
        Application app3 = new Application();
        app3.setShare(20);
        app3.setAmountOfMoney(1000);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCustomer(customer103);
        app3.setCompany(bioGen);
        app3.setAllowance(docQT2024002);
        
        List<Application> apps = new ArrayList<>();
        apps.add(app1);
        apps.add(app2);
        apps.add(app3);
        customer103.setApplications(apps);
        
        int result = customer103.getApplicationCount();
        
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Test Case 4: Five historical requests
        // Customer C104 has 1 APPROVAL, 2 REJECTED, and 2 PENDING applications
        // Only APPROVAL and REJECTED should be counted (total: 3)
        
        // Create approved application
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(10000);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCustomer(customer104);
        app1.setCompany(roboCorp);
        app1.setAllowance(docQT2023105);
        
        // Create rejected applications
        Application app2 = new Application();
        app2.setShare(100);
        app2.setAmountOfMoney(10000);
        app2.setStatus(ApplicationStatus.REJECTED);
        app2.setCustomer(customer104);
        app2.setCompany(aiVentures);
        app2.setAllowance(docQT2023106);
        
        Application app3 = new Application();
        app3.setShare(100);
        app3.setAmountOfMoney(10000);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCustomer(customer104);
        app3.setCompany(nanoMed);
        app3.setAllowance(docQT2024003);
        
        // Create pending applications
        Application app4 = new Application();
        app4.setShare(100);
        app4.setAmountOfMoney(10000);
        app4.setStatus(ApplicationStatus.PENDING);
        app4.setCustomer(customer104);
        app4.setCompany(greenEnergy);
        app4.setAllowance(docQT2024004);
        
        Application app5 = new Application();
        app5.setShare(100);
        app5.setAmountOfMoney(10000);
        app5.setStatus(ApplicationStatus.PENDING);
        app5.setCustomer(customer104);
        app5.setCompany(cloudScale);
        app5.setAllowance(docQT2024005);
        
        List<Application> apps = new ArrayList<>();
        apps.add(app1);
        apps.add(app2);
        apps.add(app3);
        apps.add(app4);
        apps.add(app5);
        customer104.setApplications(apps);
        
        int result = customer104.getApplicationCount();
        
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Test Case 5: All requests canceled
        // Customer C105 creates a pending application and then cancels it
        // Canceled applications have status REJECTED, so they should not be counted
        
        // Create pending application
        Application app = new Application();
        app.setShare(10);
        app.setAmountOfMoney(5000);
        app.setStatus(ApplicationStatus.PENDING);
        app.setCustomer(customer105);
        app.setCompany(cloud);
        app.setAllowance(docQT1010);
        
        List<Application> apps = new ArrayList<>();
        apps.add(app);
        customer105.setApplications(apps);
        
        // Cancel the application (changes status to REJECTED)
        customer105.cancelApplication("Cloud");
        
        int result = customer105.getApplicationCount();
        
        assertEquals(0, result);
    }
}