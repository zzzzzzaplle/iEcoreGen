import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private MailDeliveryManager manager;
    
    @Before
    public void setUp() {
        manager = new MailDeliveryManager();
    }
    
    @Test
    public void testCase1_RetrieveDeliveriesForBusyArea() {
        // SetUp: Create GeographicalArea "Uptown"
        GeographicalArea uptown = new GeographicalArea();
        uptown.setName("Uptown");
        
        // Add Mailman "James" to Uptown
        Mailman james = new Mailman();
        james.setName("James");
        james.setGeographicalArea(uptown);
        uptown.addMailman(james);
        
        // Add Inhabitants "Grace" and "Henry" to Uptown
        Inhabitant grace = new Inhabitant();
        grace.setName("Grace");
        grace.setGeographicalArea(uptown);
        uptown.addInhabitant(grace);
        
        Inhabitant henry = new Inhabitant();
        henry.setName("Henry");
        henry.setGeographicalArea(uptown);
        uptown.addInhabitant(henry);
        
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
        List<RegisteredMail> deliveries = manager.getRegisteredMailDeliveries(uptown);
        
        // Expected Output: List containing both mail items with James as carrier
        assertEquals("Should contain 2 deliveries", 2, deliveries.size());
        assertTrue("Should contain letter1", deliveries.contains(letter1));
        assertTrue("Should contain parcel1", deliveries.contains(parcel1));
        assertEquals("Letter1 should have James as mailman", james, deliveries.get(deliveries.indexOf(letter1)).getMailman());
        assertEquals("Parcel1 should have James as mailman", james, deliveries.get(deliveries.indexOf(parcel1)).getMailman());
    }
    
    @Test
    public void testCase2_RetrieveDeliveriesForEmptyArea() {
        // SetUp: Create empty GeographicalArea "Suburb"
        GeographicalArea suburb = new GeographicalArea();
        suburb.setName("Suburb");
        
        // Action: Retrieve deliveries for Suburb
        List<RegisteredMail> deliveries = manager.getRegisteredMailDeliveries(suburb);
        
        // Expected Output: Empty list
        assertTrue("Should return empty list", deliveries.isEmpty());
    }
    
    @Test
    public void testCase3_RetrieveDeliveriesForAreaWithUnassignedMail() {
        // SetUp: Create GeographicalArea "Midtown"
        GeographicalArea midtown = new GeographicalArea();
        midtown.setName("Midtown");
        
        // Add Inhabitant "Oscar" to Midtown
        Inhabitant oscar = new Inhabitant();
        oscar.setName("Oscar");
        oscar.setGeographicalArea(midtown);
        midtown.addInhabitant(oscar);
        
        // Create RegisteredLetter "Letter4" for Oscar (unassigned)
        Letter letter4 = new Letter();
        letter4.setAddressee(oscar);
        // Note: mailman is not assigned
        midtown.addRegisteredMail(letter4);
        
        // Action: Retrieve deliveries for Midtown
        List<RegisteredMail> deliveries = manager.getRegisteredMailDeliveries(midtown);
        
        // Expected Output: Empty list (mail not assigned to any mailman)
        assertTrue("Should return empty list for unassigned mail", deliveries.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveDeliveriesForAreaWithMultipleMailmen() {
        // SetUp: Create GeographicalArea "Harbor"
        GeographicalArea harbor = new GeographicalArea();
        harbor.setName("Harbor");
        
        // Add Mailmen "Lucy" and "Rick" to Harbor
        Mailman lucy = new Mailman();
        lucy.setName("Lucy");
        lucy.setGeographicalArea(harbor);
        harbor.addMailman(lucy);
        
        Mailman rick = new Mailman();
        rick.setName("Rick");
        rick.setGeographicalArea(harbor);
        harbor.addMailman(rick);
        
        // Add Inhabitant "Tina" to Harbor
        Inhabitant tina = new Inhabitant();
        tina.setName("Tina");
        tina.setGeographicalArea(harbor);
        harbor.addInhabitant(tina);
        
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
        List<RegisteredMail> deliveries = manager.getRegisteredMailDeliveries(harbor);
        
        // Expected Output: List containing both mail items with respective carriers
        assertEquals("Should contain 2 deliveries", 2, deliveries.size());
        assertTrue("Should contain letter5", deliveries.contains(letter5));
        assertTrue("Should contain parcel3", deliveries.contains(parcel3));
        
        RegisteredMail letter5Delivery = deliveries.get(deliveries.indexOf(letter5));
        RegisteredMail parcel3Delivery = deliveries.get(deliveries.indexOf(parcel3));
        
        assertEquals("Letter5 should have Lucy as mailman", lucy, letter5Delivery.getMailman());
        assertEquals("Parcel3 should have Rick as mailman", rick, parcel3Delivery.getMailman());
        assertEquals("Both items should have Tina as addressee", tina, letter5Delivery.getAddressee());
        assertEquals("Both items should have Tina as addressee", tina, parcel3Delivery.getAddressee());
    }
    
    @Test
    public void testCase5_RetrieveDeliveriesForNonExistentArea() {
        // Action: Retrieve deliveries for non-existent "MountainView"
        GeographicalArea mountainView = new GeographicalArea();
        mountainView.setName("MountainView");
        
        List<RegisteredMail> deliveries = manager.getRegisteredMailDeliveries(mountainView);
        
        // Expected Output: Empty list
        assertTrue("Should return empty list for non-existent area", deliveries.isEmpty());
    }
}