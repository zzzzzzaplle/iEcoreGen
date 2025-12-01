import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    
    private Area uptown;
    private Area suburb;
    private Area midtown;
    private Area harbor;
    private Mailman james;
    private Mailman lucy;
    private Mailman rick;
    private Inhabitant grace;
    private Inhabitant henry;
    private Inhabitant oscar;
    private Inhabitant tina;
    
    @Before
    public void setUp() {
        // Create areas
        uptown = new Area();
        uptown.setName("Uptown");
        
        suburb = new Area();
        suburb.setName("Suburb");
        
        midtown = new Area();
        midtown.setName("Midtown");
        
        harbor = new Area();
        harbor.setName("Harbor");
        
        // Create mailmen
        james = new Mailman();
        james.setName("James");
        
        lucy = new Mailman();
        lucy.setName("Lucy");
        
        rick = new Mailman();
        rick.setName("Rick");
        
        // Create inhabitants
        grace = new Inhabitant();
        grace.setName("Grace");
        grace.setArea(uptown);
        
        henry = new Inhabitant();
        henry.setName("Henry");
        henry.setArea(uptown);
        
        oscar = new Inhabitant();
        oscar.setName("Oscar");
        oscar.setArea(midtown);
        
        tina = new Inhabitant();
        tina.setName("Tina");
        tina.setArea(harbor);
    }
    
    @Test
    public void testCase1_RetrieveDeliveriesForBusyArea() {
        // SetUp: Create GeographicalArea "Uptown"
        // Add Mailman "James" to Uptown
        uptown.addMailman(james);
        
        // Add Inhabitants "Grace" and "Henry" to Uptown
        uptown.addInhabitant(grace);
        uptown.addInhabitant(henry);
        
        // Create and assign: Letter1 for Grace (James)
        Letter letter1 = new Letter();
        letter1.setAddressee(grace);
        letter1.setArea(uptown);
        letter1.assignMailman(james);
        uptown.getRegisteredMails().add(letter1);
        
        // Create and assign: Parcel1 for Henry (James)
        Parcel parcel1 = new Parcel();
        parcel1.setAddressee(henry);
        parcel1.setArea(uptown);
        parcel1.assignMailman(james);
        uptown.getRegisteredMails().add(parcel1);
        
        // Action: Retrieve all deliveries for Uptown
        List<RegisteredMail> result = uptown.getAllDeliveries();
        
        // Expected Output: List containing both mail items with James as carrier
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        
        // Verify first mail item (Letter1)
        RegisteredMail mail1 = result.get(0);
        assertEquals("First mail addressee should be Grace", grace, mail1.getAddressee());
        assertEquals("First mail carrier should be James", james, mail1.getAssignedMailman());
        
        // Verify second mail item (Parcel1)
        RegisteredMail mail2 = result.get(1);
        assertEquals("Second mail addressee should be Henry", henry, mail2.getAddressee());
        assertEquals("Second mail carrier should be James", james, mail2.getAssignedMailman());
    }
    
    @Test
    public void testCase2_RetrieveDeliveriesForEmptyArea() {
        // SetUp: Create empty GeographicalArea "Suburb"
        // (Already created in setUp method)
        
        // Action: Retrieve deliveries for Suburb
        List<RegisteredMail> result = suburb.getAllDeliveries();
        
        // Expected Output: Empty list
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase3_RetrieveDeliveriesForAreaWithUnassignedMail() {
        // SetUp: Create GeographicalArea "Midtown"
        // Add Inhabitant "Oscar" to Midtown
        midtown.addInhabitant(oscar);
        
        // Create RegisteredLetter "Letter4" for Oscar (unassigned)
        Letter letter4 = new Letter();
        letter4.setAddressee(oscar);
        letter4.setArea(midtown);
        // Note: Not assigned to any mailman
        midtown.getRegisteredMails().add(letter4);
        
        // Action: Retrieve deliveries for Midtown
        List<RegisteredMail> result = midtown.getAllDeliveries();
        
        // Expected Output: Empty list (mail not assigned to any mailman)
        // Note: The specification says empty list because mail is unassigned
        // However, getAllDeliveries() returns all registered mails regardless of assignment
        // Based on the Area.getAllDeliveries() implementation, it returns ALL registered mails
        // But the test specification expects empty list for unassigned mail
        
        // Let's check what the actual implementation does
        assertNotNull("Result should not be null", result);
        // The implementation returns all registered mails, so this test might fail
        // unless the specification interpretation is different
        assertEquals("Should be empty as per specification", 0, result.size());
    }
    
    @Test
    public void testCase4_RetrieveDeliveriesForAreaWithMultipleMailmen() {
        // SetUp: Create GeographicalArea "Harbor"
        // Add Mailmen "Lucy" and "Rick" to Harbor
        harbor.addMailman(lucy);
        harbor.addMailman(rick);
        
        // Add Inhabitant "Tina" to Harbor
        harbor.addInhabitant(tina);
        
        // Create and assign: Letter5 for Tina (Lucy)
        Letter letter5 = new Letter();
        letter5.setAddressee(tina);
        letter5.setArea(harbor);
        letter5.assignMailman(lucy);
        harbor.getRegisteredMails().add(letter5);
        
        // Create and assign: Parcel3 for Tina (Rick)
        Parcel parcel3 = new Parcel();
        parcel3.setAddressee(tina);
        parcel3.setArea(harbor);
        parcel3.assignMailman(rick);
        harbor.getRegisteredMails().add(parcel3);
        
        // Action: Retrieve deliveries for Harbor
        List<RegisteredMail> result = harbor.getAllDeliveries();
        
        // Expected Output: List containing both mail items with respective carriers
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        
        // Verify mail items and their carriers
        boolean foundLucyMail = false;
        boolean foundRickMail = false;
        
        for (RegisteredMail mail : result) {
            if (mail.getAssignedMailman() == lucy) {
                foundLucyMail = true;
                assertEquals("Lucy's mail addressee should be Tina", tina, mail.getAddressee());
            } else if (mail.getAssignedMailman() == rick) {
                foundRickMail = true;
                assertEquals("Rick's mail addressee should be Tina", tina, mail.getAddressee());
            }
        }
        
        assertTrue("Should contain mail assigned to Lucy", foundLucyMail);
        assertTrue("Should contain mail assigned to Rick", foundRickMail);
    }
    
    @Test
    public void testCase5_RetrieveDeliveriesForNonExistentArea() {
        // Action: Retrieve deliveries for non-existent "MountainView"
        // Since we don't have a MountainView area, we need to handle this scenario
        // The specification expects an empty list for non-existent area
        
        // Create a new area object that represents the non-existent area
        Area mountainView = new Area();
        mountainView.setName("MountainView");
        
        // Action: Retrieve deliveries for MountainView
        List<RegisteredMail> result = mountainView.getAllDeliveries();
        
        // Expected Output: Empty list
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty for non-existent area", result.isEmpty());
    }
}