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
    
    @Before
    public void setUp() {
        // Setup for Test Case 1: No applications at all
        customer101 = new Customer();
        customer101.setName("Thomas");
        customer101.setSurname("Anderson");
        customer101.setEmail("t.anderson@example.com");
        customer101.setTelephone("555-0101");
        customer101.setCanApplyForIPO(true);
        
        // Setup for Test Case 2: Single pending request
        customer102 = new Customer();
        customer102.setName("Lisa");
        customer102.setSurname("Rodriguez");
        customer102.setEmail("l.rodriguez@example.com");
        customer102.setTelephone("555-0202");
        customer102.setCanApplyForIPO(true);
        
        Company quantumTech = new Company();
        quantumTech.setName("QuantumTech");
        quantumTech.setEmail("quantumtech@gmail.com");
        
        Document docQT = new Document();
        Application app2024001 = new Application();
        app2024001.setCustomer(customer102);
        app2024001.setCompany(quantumTech);
        app2024001.setShare(50);
        app2024001.setAmountOfMoney(2500);
        app2024001.setAllowance(docQT);
        app2024001.setStatus(ApplicationStatus.APPROVAL);
        
        List<Application> appsC102 = new ArrayList<>();
        appsC102.add(app2024001);
        customer102.setApplications(appsC102);
        
        // Setup for Test Case 3: Mix of approved and rejected
        customer103 = new Customer();
        customer103.setName("David");
        customer103.setSurname("Kim");
        customer103.setEmail("d.kim@example.com");
        customer103.setTelephone("555-0303");
        customer103.setCanApplyForIPO(false);
        
        Company neuralink = new Company();
        neuralink.setName("Neuralink");
        neuralink.setEmail("neuralink@gmail.com");
        
        Company spaceY = new Company();
        spaceY.setName("SpaceY");
        spaceY.setEmail("spacey@gmail.com");
        
        Company bioGen = new Company();
        bioGen.setName("BioGen");
        bioGen.setEmail("biogen@gmail.com");
        
        Document docQT2023101 = new Document();
        Document docQT2023102 = new Document();
        Document docQT2024002 = new Document();
        
        Application app2023101 = new Application();
        app2023101.setCustomer(customer103);
        app2023101.setCompany(neuralink);
        app2023101.setShare(100);
        app2023101.setAmountOfMoney(10000);
        app2023101.setAllowance(docQT2023101);
        app2023101.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2023102 = new Application();
        app2023102.setCustomer(customer103);
        app2023102.setCompany(spaceY);
        app2023102.setShare(30);
        app2023102.setAmountOfMoney(15000);
        app2023102.setAllowance(docQT2023102);
        app2023102.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2024002 = new Application();
        app2024002.setCustomer(customer103);
        app2024002.setCompany(bioGen);
        app2024002.setShare(20);
        app2024002.setAmountOfMoney(1000);
        app2024002.setAllowance(docQT2024002);
        app2024002.setStatus(ApplicationStatus.REJECTED);
        
        List<Application> appsC103 = new ArrayList<>();
        appsC103.add(app2023101);
        appsC103.add(app2023102);
        appsC103.add(app2024002);
        customer103.setApplications(appsC103);
        
        // Setup for Test Case 4: Five historical requests
        customer104 = new Customer();
        customer104.setName("Emma");
        customer104.setSurname("Wilson");
        customer104.setEmail("e.wilson@example.com");
        customer104.setTelephone("555-0404");
        customer104.setCanApplyForIPO(true);
        
        Company roboCorp = new Company();
        roboCorp.setName("RoboCorp");
        roboCorp.setEmail("robocorp@gmail.com");
        
        Company aiVentures = new Company();
        aiVentures.setName("AI Ventures");
        aiVentures.setEmail("aiventures@gmail.com");
        
        Company nanoMed = new Company();
        nanoMed.setName("NanoMed");
        nanoMed.setEmail("nanomed@gmail.com");
        
        Company greenEnergy = new Company();
        greenEnergy.setName("GreenEnergy");
        greenEnergy.setEmail("greenenergy@gmail.com");
        
        Company cloudScale = new Company();
        cloudScale.setName("CloudScale");
        cloudScale.setEmail("cloudscale@gmail.com");
        
        Document docQT2023105 = new Document();
        Document docQT2023106 = new Document();
        Document docQT2024003 = new Document();
        Document docQT2024004 = new Document();
        Document docQT2024005 = new Document();
        
        Application app2023105 = new Application();
        app2023105.setCustomer(customer104);
        app2023105.setCompany(roboCorp);
        app2023105.setShare(100);
        app2023105.setAmountOfMoney(10000);
        app2023105.setAllowance(docQT2023105);
        app2023105.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2023106 = new Application();
        app2023106.setCustomer(customer104);
        app2023106.setCompany(aiVentures);
        app2023106.setShare(100);
        app2023106.setAmountOfMoney(10000);
        app2023106.setAllowance(docQT2023106);
        app2023106.setStatus(ApplicationStatus.REJECTED);
        
        Application app2024003 = new Application();
        app2024003.setCustomer(customer104);
        app2024003.setCompany(nanoMed);
        app2024003.setShare(100);
        app2024003.setAmountOfMoney(10000);
        app2024003.setAllowance(docQT2024003);
        app2024003.setStatus(ApplicationStatus.REJECTED);
        
        Application app2024004 = new Application();
        app2024004.setCustomer(customer104);
        app2024004.setCompany(greenEnergy);
        app2024004.setShare(100);
        app2024004.setAmountOfMoney(10000);
        app2024004.setAllowance(docQT2024004);
        app2024004.setStatus(ApplicationStatus.PENDING);
        
        Application app2024005 = new Application();
        app2024005.setCustomer(customer104);
        app2024005.setCompany(cloudScale);
        app2024005.setShare(100);
        app2024005.setAmountOfMoney(10000);
        app2024005.setAllowance(docQT2024005);
        app2024005.setStatus(ApplicationStatus.PENDING);
        
        List<Application> appsC104 = new ArrayList<>();
        appsC104.add(app2023105);
        appsC104.add(app2023106);
        appsC104.add(app2024003);
        appsC104.add(app2024004);
        appsC104.add(app2024005);
        customer104.setApplications(appsC104);
        
        // Setup for Test Case 5: All requests canceled
        customer105 = new Customer();
        customer105.setName("James");
        customer105.setSurname("Chen");
        customer105.setEmail("j.chen@example.com");
        customer105.setTelephone("555-0505");
        customer105.setCanApplyForIPO(true);
        
        Company cloud = new Company();
        cloud.setName("Cloud");
        cloud.setEmail("Cloud@gmail.com");
        
        Document docQT1010 = new Document();
        Application app1010 = new Application();
        app1010.setCustomer(customer105);
        app1010.setCompany(cloud);
        app1010.setShare(10);
        app1010.setAmountOfMoney(5000);
        app1010.setAllowance(docQT1010);
        app1010.setStatus(ApplicationStatus.PENDING);
        
        List<Application> appsC105 = new ArrayList<>();
        appsC105.add(app1010);
        customer105.setApplications(appsC105);
        
        // Cancel the application
        customer105.cancelApplication("Cloud");
    }
    
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Input: Customer "C101" requests a count summary
        // Setup: Customer "C101" with no IPO requests filed
        // Expected Output: 0
        
        int result = customer101.getApplicationCount();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_singlePendingRequest() {
        // Input: Customer "C102" asks for the total number of filings
        // Setup: Customer "C102" with one APPROVAL record
        // Expected Output: 1
        
        int result = customer102.getApplicationCount();
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Input: Customer "C103" checks total filings
        // Setup: Customer "C103" with two APPROVAL and one REJECTED record
        // Expected Output: 3
        
        int result = customer103.getApplicationCount();
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Input: Customer "C104" queries the overall count
        // Setup: Customer "C104" with 1 APPROVAL, 2 REJECTED, 2 PENDING records
        // Expected Output: 3 (only APPROVAL and REJECTED count)
        
        int result = customer104.getApplicationCount();
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Input: Customer "C105" asks for the figure
        // Setup: Customer "C105" with one pending application that gets canceled
        // Expected Output: 0
        
        int result = customer105.getApplicationCount();
        assertEquals(0, result);
    }
}