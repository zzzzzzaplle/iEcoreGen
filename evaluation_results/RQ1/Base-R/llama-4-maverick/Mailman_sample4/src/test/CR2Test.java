import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    
    private MailDeliverySystem mailDeliverySystem;
    
    @Before
    public void setUp() {
        mailDeliverySystem = new MailDeliverySystem();
    }
    
    @Test
    public void testCase1_RetrieveDeliveriesForBusyArea() {
        // SetUp: Create GeographicalArea "Uptown"
        GeographicalArea uptown = new GeographicalArea();
        uptown.setName("Uptown");
        mailDeliverySystem.addGeographicalArea(uptown);
        
        // Add Mailman "James" to Uptown
        Mailman james = new Mailman();
        james.setName("James");
        mailDeliverySystem.addMailman(uptown, james);
        
        // Add Inhabitants "Grace" and "Henry" to Uptown
        Inhabitant grace = new Inhabitant();
        grace.setName("Grace");
        mailDeliverySystem.addInhabitant(uptown, grace);
        
        Inhabitant henry = new Inhabitant();
        henry.setName("Henry");
        mailDeliverySystem.addInhabitant(uptown, henry);
        
        // Create and assign Letter1 for Grace (James)
        Letter letter1 = new Letter();
        letter1.setAddressee(grace);
        letter1.setMailman(james);
        uptown.addRegisteredMail(letter1);
        
        // Create and assign Parcel1 for Henry (James)
        Parcel parcel1 = new Parcel();
        parcel1.setAddressee(henry);
        parcel1.setMailman(james);
        uptown.addRegisteredMail(parcel1);
        
        // Action: Retrieve all deliveries for Uptown
        List<RegisteredMail> deliveries = mailDeliverySystem.getRegisteredMailDeliveries(uptown);
        
        // Expected Output: List containing both mail items with James as carrier
        assertNotNull("Deliveries list should not be null", deliveries);
        assertEquals("Should contain 2 mail items", 2, deliveries.size());
        assertTrue("Should contain letter1", deliveries.contains(letter1));
        assertTrue("Should contain parcel1", deliveries.contains(parcel1));
        assertEquals("Letter1 should have James as mailman", james, letter1.getMailman());
        assertEquals("Parcel1 should have James as mailman", james, parcel1.getMailman());
    }
    
    @Test
    public void testCase2_RetrieveDeliveriesForEmptyArea() {
        // SetUp: Create empty GeographicalArea "Suburb"
        GeographicalArea suburb = new GeographicalArea();
        suburb.setName("Suburb");
        mailDeliverySystem.addGeographicalArea(suburb);
        
        // Action: Retrieve deliveries for Suburb
        List<RegisteredMail> deliveries = mailDeliverySystem.getRegisteredMailDeliveries(suburb);
        
        // Expected Output: Empty list
        assertNotNull("Deliveries list should not be null", deliveries);
        assertTrue("Deliveries list should be empty", deliveries.isEmpty());
    }
    
    @Test
    public void testCase3_RetrieveDeliveriesForAreaWithUnassignedMail() {
        // SetUp: Create GeographicalArea "Midtown"
        GeographicalArea midtown = new GeographicalArea();
        midtown.setName("Midtown");
        mailDeliverySystem.addGeographicalArea(midtown);
        
        // Add Inhabitant "Oscar" to Midtown
        Inhabitant oscar = new Inhabitant();
        oscar.setName("Oscar");
        mailDeliverySystem.addInhabitant(midtown, oscar);
        
        // Create RegisteredLetter "Letter4" for Oscar (unassigned)
        Letter letter4 = new Letter();
        letter4.setAddressee(oscar);
        // mailman not assigned - left as null
        midtown.addRegisteredMail(letter4);
        
        // Action: Retrieve deliveries for Midtown
        List<RegisteredMail> deliveries = mailDeliverySystem.getRegisteredMailDeliveries(midtown);
        
        // Expected Output: Empty list (mail not assigned to any mailman)
        assertNotNull("Deliveries list should not be null", deliveries);
        assertTrue("Deliveries list should be empty when mail is unassigned", deliveries.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveDeliveriesForAreaWithMultipleMailmen() {
        // SetUp: Create GeographicalArea "Harbor"
        GeographicalArea harbor = new GeographicalArea();
        harbor.setName("Harbor");
        mailDeliverySystem.addGeographicalArea(harbor);
        
        // Add Mailmen "Lucy" and "Rick" to Harbor
        Mailman lucy = new Mailman();
        lucy.setName("Lucy");
        mailDeliverySystem.addMailman(harbor, lucy);
        
        Mailman rick = new Mailman();
        rick.setName("Rick");
        mailDeliverySystem.addMailman(harbor, rick);
        
        // Add Inhabitant "Tina" to Harbor
        Inhabitant tina = new Inhabitant();
        tina.setName("Tina");
        mailDeliverySystem.addInhabitant(harbor, tina);
        
        // Create and assign Letter5 for Tina (Lucy)
        Letter letter5 = new Letter();
        letter5.setAddressee(tina);
        letter5.setMailman(lucy);
        harbor.addRegisteredMail(letter5);
        
        // Create and assign Parcel3 for Tina (Rick)
        Parcel parcel3 = new Parcel();
        parcel3.setAddressee(tina);
        parcel3.setMailman(rick);
        harbor.addRegisteredMail(parcel3);
        
        // Action: Retrieve deliveries for Harbor
        List<RegisteredMail> deliveries = mailDeliverySystem.getRegisteredMailDeliveries(harbor);
        
        // Expected Output: List containing both mail items with respective carriers
        assertNotNull("Deliveries list should not be null", deliveries);
        assertEquals("Should contain 2 mail items", 2, deliveries.size());
        assertTrue("Should contain letter5", deliveries.contains(letter5));
        assertTrue("Should contain parcel3", deliveries.contains(parcel3));
        assertEquals("Letter5 should have Lucy as mailman", lucy, letter5.getMailman());
        assertEquals("Parcel3 should have Rick as mailman", rick, parcel3.getMailman());
    }
    
    @Test
    public void testCase5_RetrieveDeliveriesForNonExistentArea() {
        // Action: Retrieve deliveries for non-existent "MountainView"
        GeographicalArea mountainView = new GeographicalArea();
        mountainView.setName("MountainView");
        // Note: mountainView is not added to mailDeliverySystem
        
        List<RegisteredMail> deliveries = mailDeliverySystem.getRegisteredMailDeliveries(mountainView);
        
        // Expected Output: Empty list
        assertNotNull("Deliveries list should not be null", deliveries);
        assertTrue("Deliveries list should be empty for non-existent area", deliveries.isEmpty());
    }
}