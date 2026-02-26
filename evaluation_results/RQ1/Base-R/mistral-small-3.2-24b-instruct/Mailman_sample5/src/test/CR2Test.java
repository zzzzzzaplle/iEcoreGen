import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private MailManagementSystem mailSystem;
    
    @Before
    public void setUp() {
        mailSystem = new MailManagementSystem();
    }
    
    @Test
    public void testCase1_RetrieveDeliveriesForBusyArea() {
        // SetUp: Create GeographicalArea "Uptown"
        GeographicalArea uptown = new GeographicalArea();
        uptown.setAreaId("Uptown");
        mailSystem.addGeographicalArea(uptown);
        
        // Add Mailman "James" to Uptown
        Mailman james = new Mailman();
        james.setMailmanId("M001");
        james.setName("James");
        james.setGeographicalArea(uptown);
        uptown.addMailman(james);
        
        // Add Inhabitants "Grace" and "Henry" to Uptown
        Inhabitant grace = new Inhabitant();
        grace.setInhabitantId("I001");
        grace.setName("Grace");
        grace.setGeographicalArea(uptown);
        uptown.addInhabitant(grace);
        
        Inhabitant henry = new Inhabitant();
        henry.setInhabitantId("I002");
        henry.setName("Henry");
        henry.setGeographicalArea(uptown);
        uptown.addInhabitant(henry);
        
        // Create and assign Letter1 for Grace (James)
        Letter letter1 = new Letter();
        letter1.setMailId("L001");
        letter1.setAddressee(grace);
        letter1.setContent("Hello Grace");
        james.assignMail(letter1);
        
        // Create and assign Parcel1 for Henry (James)
        Parcel parcel1 = new Parcel();
        parcel1.setMailId("P001");
        parcel1.setAddressee(henry);
        parcel1.setWeight(2.5);
        parcel1.setDescription("Books");
        james.assignMail(parcel1);
        
        // Action: Retrieve all deliveries for Uptown
        List<RegisteredMail> deliveries = mailSystem.getDeliveriesForArea("Uptown");
        
        // Expected Output: List containing both mail items with James as carrier
        assertEquals(2, deliveries.size());
        assertTrue(deliveries.contains(letter1));
        assertTrue(deliveries.contains(parcel1));
        
        // Verify James is the carrier for both items
        for (RegisteredMail mail : deliveries) {
            assertEquals(james, mail.getMailman());
        }
    }
    
    @Test
    public void testCase2_RetrieveDeliveriesForEmptyArea() {
        // SetUp: Create empty GeographicalArea "Suburb"
        GeographicalArea suburb = new GeographicalArea();
        suburb.setAreaId("Suburb");
        mailSystem.addGeographicalArea(suburb);
        
        // Action: Retrieve deliveries for Suburb
        List<RegisteredMail> deliveries = mailSystem.getDeliveriesForArea("Suburb");
        
        // Expected Output: Empty list
        assertNotNull(deliveries);
        assertTrue(deliveries.isEmpty());
    }
    
    @Test
    public void testCase3_RetrieveDeliveriesForAreaWithUnassignedMail() {
        // SetUp: Create GeographicalArea "Midtown"
        GeographicalArea midtown = new GeographicalArea();
        midtown.setAreaId("Midtown");
        mailSystem.addGeographicalArea(midtown);
        
        // Add Inhabitant "Oscar" to Midtown
        Inhabitant oscar = new Inhabitant();
        oscar.setInhabitantId("I003");
        oscar.setName("Oscar");
        oscar.setGeographicalArea(midtown);
        midtown.addInhabitant(oscar);
        
        // Create RegisteredLetter "Letter4" for Oscar (unassigned)
        Letter letter4 = new Letter();
        letter4.setMailId("L004");
        letter4.setAddressee(oscar);
        letter4.setContent("Unassigned letter");
        // Note: Not assigned to any mailman
        
        // Action: Retrieve deliveries for Midtown
        List<RegisteredMail> deliveries = mailSystem.getDeliveriesForArea("Midtown");
        
        // Expected Output: Empty list (mail not assigned to any mailman)
        assertNotNull(deliveries);
        assertTrue(deliveries.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveDeliveriesForAreaWithMultipleMailmen() {
        // SetUp: Create GeographicalArea "Harbor"
        GeographicalArea harbor = new GeographicalArea();
        harbor.setAreaId("Harbor");
        mailSystem.addGeographicalArea(harbor);
        
        // Add Mailmen "Lucy" and "Rick" to Harbor
        Mailman lucy = new Mailman();
        lucy.setMailmanId("M002");
        lucy.setName("Lucy");
        lucy.setGeographicalArea(harbor);
        harbor.addMailman(lucy);
        
        Mailman rick = new Mailman();
        rick.setMailmanId("M003");
        rick.setName("Rick");
        rick.setGeographicalArea(harbor);
        harbor.addMailman(rick);
        
        // Add Inhabitant "Tina" to Harbor
        Inhabitant tina = new Inhabitant();
        tina.setInhabitantId("I004");
        tina.setName("Tina");
        tina.setGeographicalArea(harbor);
        harbor.addInhabitant(tina);
        
        // Create and assign Letter5 for Tina (Lucy)
        Letter letter5 = new Letter();
        letter5.setMailId("L005");
        letter5.setAddressee(tina);
        letter5.setContent("Letter from Lucy");
        lucy.assignMail(letter5);
        
        // Create and assign Parcel3 for Tina (Rick)
        Parcel parcel3 = new Parcel();
        parcel3.setMailId("P003");
        parcel3.setAddressee(tina);
        parcel3.setWeight(5.0);
        parcel3.setDescription("Electronics");
        rick.assignMail(parcel3);
        
        // Action: Retrieve deliveries for Harbor
        List<RegisteredMail> deliveries = mailSystem.getDeliveriesForArea("Harbor");
        
        // Expected Output: List containing both mail items with respective carriers
        assertEquals(2, deliveries.size());
        assertTrue(deliveries.contains(letter5));
        assertTrue(deliveries.contains(parcel3));
        
        // Verify correct mailman assignments
        for (RegisteredMail mail : deliveries) {
            if (mail.equals(letter5)) {
                assertEquals(lucy, mail.getMailman());
            } else if (mail.equals(parcel3)) {
                assertEquals(rick, mail.getMailman());
            }
        }
    }
    
    @Test
    public void testCase5_RetrieveDeliveriesForNonExistentArea() {
        // Action: Retrieve deliveries for non-existent "MountainView"
        List<RegisteredMail> deliveries = mailSystem.getDeliveriesForArea("MountainView");
        
        // Expected Output: Empty list
        assertNotNull(deliveries);
        assertTrue(deliveries.isEmpty());
    }
}