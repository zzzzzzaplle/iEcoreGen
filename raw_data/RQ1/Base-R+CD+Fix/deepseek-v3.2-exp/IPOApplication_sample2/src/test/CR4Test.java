import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Customer customer;
    private Document testDocument;
    
    @Before
    public void setUp() {
        // Common setup for test cases
        testDocument = new Document();
    }
    
    @Test
    public void testCase1_NoApprovedRequests() {
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
        Application app1 = new Application();
        app1.setCustomer(customer);
        app1.setCompany(techInc);
        app1.setShare(10);
        app1.setAmountOfMoney(1500.0);
        app1.setAllowance(testDocument);
        app1.setStatus(ApplicationStatus.PENDING);
        
        Application app2 = new Application();
        app2.setCustomer(customer);
        app2.setCompany(bioMed);
        app2.setShare(10);
        app2.setAmountOfMoney(2000.0);
        app2.setAllowance(testDocument);
        app2.setStatus(ApplicationStatus.REJECTED);
        
        // Add applications to customer
        List<Application> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 0 since no approved applications
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SingleApproval() {
        // Setup: Customer "C202" with one approved application
        customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        
        // Create company
        Company solarMax = new Company();
        solarMax.setName("SolarMax");
        solarMax.setEmail("solarmax@gmail.com");
        
        // Create approved application
        Application app = new Application();
        app.setCustomer(customer);
        app.setCompany(solarMax);
        app.setShare(84);
        app.setAmountOfMoney(4200.0);
        app.setAllowance(testDocument);
        app.setStatus(ApplicationStatus.APPROVAL);
        
        // Add application to customer
        List<Application> applications = new ArrayList<>();
        applications.add(app);
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 4200.0
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with multiple approved applications
        customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        
        // Create companies
        Company quantumTech = new Company();
        quantumTech.setName("QuantumTech");
        quantumTech.setEmail("quantumtech@example.com");
        
        Company neuralink = new Company();
        neuralink.setName("Neuralink");
        neuralink.setEmail("neuralink@example.com");
        
        // Create approved applications
        Application app1 = new Application();
        app1.setCustomer(customer);
        app1.setCompany(quantumTech);
        app1.setShare(40);
        app1.setAmountOfMoney(2000.0);
        app1.setAllowance(testDocument);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2 = new Application();
        app2.setCustomer(customer);
        app2.setCompany(neuralink);
        app2.setShare(70);
        app2.setAmountOfMoney(3500.0);
        app2.setAllowance(testDocument);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        // Add applications to customer
        List<Application> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 5500.0 (2000 + 3500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup: Customer "C204" with multiple approved applications ($10,000 each)
        customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        
        // Create companies
        Company techGiant = new Company();
        techGiant.setName("TechGiant");
        techGiant.setEmail("techgiant@example.com");
        
        Company autoFuture = new Company();
        autoFuture.setName("AutoFuture");
        autoFuture.setEmail("autofuture@example.com");
        
        Company aerospace = new Company();
        aerospace.setName("AeroSpace");
        aerospace.setEmail("aerospace@example.com");
        
        Company bioGenius = new Company();
        bioGenius.setName("BioGenius");
        bioGenius.setEmail("biogenius@example.com");
        
        Company greenEnergy = new Company();
        greenEnergy.setName("GreenEnergy");
        greenEnergy.setEmail("greenenergy@example.com");
        
        // Create approved applications (all $10,000 each)
        Application app1 = new Application();
        app1.setCustomer(customer);
        app1.setCompany(techGiant);
        app1.setShare(200);
        app1.setAmountOfMoney(10000.0);
        app1.setAllowance(testDocument);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2 = new Application();
        app2.setCustomer(customer);
        app2.setCompany(autoFuture);
        app2.setShare(250);
        app2.setAmountOfMoney(10000.0);
        app2.setAllowance(testDocument);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        Application app3 = new Application();
        app3.setCustomer(customer);
        app3.setCompany(aerospace);
        app3.setShare(125);
        app3.setAmountOfMoney(10000.0);
        app3.setAllowance(testDocument);
        app3.setStatus(ApplicationStatus.APPROVAL);
        
        Application app4 = new Application();
        app4.setCustomer(customer);
        app4.setCompany(bioGenius);
        app4.setShare(500);
        app4.setAmountOfMoney(10000.0);
        app4.setAllowance(testDocument);
        app4.setStatus(ApplicationStatus.APPROVAL);
        
        Application app5 = new Application();
        app5.setCustomer(customer);
        app5.setCompany(greenEnergy);
        app5.setShare(200);
        app5.setAmountOfMoney(10000.0);
        app5.setAllowance(testDocument);
        app5.setStatus(ApplicationStatus.APPROVAL);
        
        // Add applications to customer
        List<Application> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        applications.add(app3);
        applications.add(app4);
        applications.add(app5);
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 50000.0 (5 * 10000)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup: Customer "C205" with approved and pending applications
        customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        
        // Create companies
        Company cloudServ = new Company();
        cloudServ.setName("CloudServ");
        cloudServ.setEmail("cloudserv@example.com");
        
        Company dataCore = new Company();
        dataCore.setName("DataCore");
        dataCore.setEmail("datacore@example.com");
        
        Company aiVentures = new Company();
        aiVentures.setName("AI Ventures");
        aiVentures.setEmail("aiventures@example.com");
        
        Company nanoTech = new Company();
        nanoTech.setName("NanoTech");
        nanoTech.setEmail("nanotech@example.com");
        
        Company roboWorks = new Company();
        roboWorks.setName("RoboWorks");
        roboWorks.setEmail("roboworks@example.com");
        
        // Create approved applications
        Application app1 = new Application();
        app1.setCustomer(customer);
        app1.setCompany(cloudServ);
        app1.setShare(100);
        app1.setAmountOfMoney(3000.0);
        app1.setAllowance(testDocument);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2 = new Application();
        app2.setCustomer(customer);
        app2.setCompany(dataCore);
        app2.setShare(20);
        app2.setAmountOfMoney(2750.0);
        app2.setAllowance(testDocument);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        Application app3 = new Application();
        app3.setCustomer(customer);
        app3.setCompany(aiVentures);
        app3.setShare(30);
        app3.setAmountOfMoney(3000.0);
        app3.setAllowance(testDocument);
        app3.setStatus(ApplicationStatus.APPROVAL);
        
        // Create pending applications
        Application app4 = new Application();
        app4.setCustomer(customer);
        app4.setCompany(nanoTech);
        app4.setShare(10);
        app4.setAmountOfMoney(600.0);
        app4.setAllowance(testDocument);
        app4.setStatus(ApplicationStatus.PENDING);
        
        Application app5 = new Application();
        app5.setCustomer(customer);
        app5.setCompany(roboWorks);
        app5.setShare(50);
        app5.setAmountOfMoney(600.0);
        app5.setAllowance(testDocument);
        app5.setStatus(ApplicationStatus.PENDING);
        
        // Add applications to customer
        List<Application> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        applications.add(app3);
        applications.add(app4);
        applications.add(app5);
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 8750.0 (3000 + 2750 + 3000) - pending applications should be ignored
        assertEquals(8750.0, result, 0.001);
    }
}