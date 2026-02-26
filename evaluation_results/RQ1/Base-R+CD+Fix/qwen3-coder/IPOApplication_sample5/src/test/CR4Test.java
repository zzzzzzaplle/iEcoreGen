import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        customer = new Customer();
        company = new Company();
        document = new Document();
    }
    
    @Test
    public void testCase1_noApprovedRequests() {
        // Setup: Customer "C201" with PENDING and REJECTED applications only
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
        
        // Create PENDING application
        Application pendingApp = new Application();
        pendingApp.setShare(10);
        pendingApp.setAmountOfMoney(1500.0);
        pendingApp.setStatus(ApplicationStatus.PENDING);
        pendingApp.setCustomer(customer);
        pendingApp.setCompany(techInc);
        pendingApp.setAllowance(document);
        pendingApp.setEmails(new ArrayList<>());
        
        // Create REJECTED application
        Application rejectedApp = new Application();
        rejectedApp.setShare(10);
        rejectedApp.setAmountOfMoney(2000.0);
        rejectedApp.setStatus(ApplicationStatus.REJECTED);
        rejectedApp.setCustomer(customer);
        rejectedApp.setCompany(bioMed);
        rejectedApp.setAllowance(document);
        rejectedApp.setEmails(new ArrayList<>());
        
        // Add applications to customer
        List<Application> applications = new ArrayList<>();
        applications.add(pendingApp);
        applications.add(rejectedApp);
        customer.setApplications(applications);
        
        // Execute: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: No approved applications, expected output is 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_singleApproval() {
        // Setup: Customer "C202" with one approved application
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setCanApplyForIPO(true);
        
        // Create company
        Company solarMax = new Company();
        solarMax.setName("SolarMax");
        solarMax.setEmail("solarmax@gmail.com");
        
        // Create APPROVED application
        Application approvedApp = new Application();
        approvedApp.setShare(84);
        approvedApp.setAmountOfMoney(4200.0);
        approvedApp.setStatus(ApplicationStatus.APPROVAL);
        approvedApp.setCustomer(customer);
        approvedApp.setCompany(solarMax);
        approvedApp.setAllowance(document);
        approvedApp.setEmails(new ArrayList<>());
        
        // Add application to customer
        List<Application> applications = new ArrayList<>();
        applications.add(approvedApp);
        customer.setApplications(applications);
        
        // Execute: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Single approved application with amount 4200
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with multiple approved applications from different companies
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
        
        // Create first APPROVED application
        Application app1 = new Application();
        app1.setShare(40);
        app1.setAmountOfMoney(2000.0);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCustomer(customer);
        app1.setCompany(quantumTech);
        app1.setAllowance(document);
        app1.setEmails(new ArrayList<>());
        
        // Create second APPROVED application
        Application app2 = new Application();
        app2.setShare(70);
        app2.setAmountOfMoney(3500.0);
        app2.setStatus(ApplicationStatus.APPROVAL);
        app2.setCustomer(customer);
        app2.setCompany(neuralink);
        app2.setAllowance(document);
        app2.setEmails(new ArrayList<>());
        
        // Add applications to customer
        List<Application> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        customer.setApplications(applications);
        
        // Execute: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Sum of two approved applications (2000 + 3500 = 5500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_largePortfolio() {
        // Setup: Customer "C204" with 5 approved applications, each worth $10,000
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
        
        // Create 5 APPROVED applications, each with $10,000
        List<Application> applications = new ArrayList<>();
        
        Application app1 = new Application();
        app1.setShare(200);
        app1.setAmountOfMoney(10000.0);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCustomer(customer);
        app1.setCompany(techGiant);
        app1.setAllowance(document);
        app1.setEmails(new ArrayList<>());
        applications.add(app1);
        
        Application app2 = new Application();
        app2.setShare(250);
        app2.setAmountOfMoney(10000.0);
        app2.setStatus(ApplicationStatus.APPROVAL);
        app2.setCustomer(customer);
        app2.setCompany(autoFuture);
        app2.setAllowance(document);
        app2.setEmails(new ArrayList<>());
        applications.add(app2);
        
        Application app3 = new Application();
        app3.setShare(125);
        app3.setAmountOfMoney(10000.0);
        app3.setStatus(ApplicationStatus.APPROVAL);
        app3.setCustomer(customer);
        app3.setCompany(aeroSpace);
        app3.setAllowance(document);
        app3.setEmails(new ArrayList<>());
        applications.add(app3);
        
        Application app4 = new Application();
        app4.setShare(500);
        app4.setAmountOfMoney(10000.0);
        app4.setStatus(ApplicationStatus.APPROVAL);
        app4.setCustomer(customer);
        app4.setCompany(bioGenius);
        app4.setAllowance(document);
        app4.setEmails(new ArrayList<>());
        applications.add(app4);
        
        Application app5 = new Application();
        app5.setShare(200);
        app5.setAmountOfMoney(10000.0);
        app5.setStatus(ApplicationStatus.APPROVAL);
        app5.setCustomer(customer);
        app5.setCompany(greenEnergy);
        app5.setAllowance(document);
        app5.setEmails(new ArrayList<>());
        applications.add(app5);
        
        customer.setApplications(applications);
        
        // Execute: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Sum of 5 approved applications (5 * 10000 = 50000)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup: Customer "C205" with 3 approved and 2 pending applications
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
        
        // Create APPROVED applications
        List<Application> applications = new ArrayList<>();
        
        Application approvedApp1 = new Application();
        approvedApp1.setShare(100);
        approvedApp1.setAmountOfMoney(3000.0);
        approvedApp1.setStatus(ApplicationStatus.APPROVAL);
        approvedApp1.setCustomer(customer);
        approvedApp1.setCompany(cloudServ);
        approvedApp1.setAllowance(document);
        approvedApp1.setEmails(new ArrayList<>());
        applications.add(approvedApp1);
        
        Application approvedApp2 = new Application();
        approvedApp2.setShare(20);
        approvedApp2.setAmountOfMoney(2750.0);
        approvedApp2.setStatus(ApplicationStatus.APPROVAL);
        approvedApp2.setCustomer(customer);
        approvedApp2.setCompany(dataCore);
        approvedApp2.setAllowance(document);
        approvedApp2.setEmails(new ArrayList<>());
        applications.add(approvedApp2);
        
        Application approvedApp3 = new Application();
        approvedApp3.setShare(30);
        approvedApp3.setAmountOfMoney(3000.0);
        approvedApp3.setStatus(ApplicationStatus.APPROVAL);
        approvedApp3.setCustomer(customer);
        approvedApp3.setCompany(aiVentures);
        approvedApp3.setAllowance(document);
        approvedApp3.setEmails(new ArrayList<>());
        applications.add(approvedApp3);
        
        // Create PENDING applications
        Application pendingApp1 = new Application();
        pendingApp1.setShare(10);
        pendingApp1.setAmountOfMoney(600.0);
        pendingApp1.setStatus(ApplicationStatus.PENDING);
        pendingApp1.setCustomer(customer);
        pendingApp1.setCompany(nanoTech);
        pendingApp1.setAllowance(document);
        pendingApp1.setEmails(new ArrayList<>());
        applications.add(pendingApp1);
        
        Application pendingApp2 = new Application();
        pendingApp2.setShare(50);
        pendingApp2.setAmountOfMoney(600.0);
        pendingApp2.setStatus(ApplicationStatus.PENDING);
        pendingApp2.setCustomer(customer);
        pendingApp2.setCompany(roboWorks);
        pendingApp2.setAllowance(document);
        pendingApp2.setEmails(new ArrayList<>());
        applications.add(pendingApp2);
        
        customer.setApplications(applications);
        
        // Execute: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Sum of only approved applications (3000 + 2750 + 3000 = 8750)
        assertEquals(8750.0, result, 0.001);
    }
}