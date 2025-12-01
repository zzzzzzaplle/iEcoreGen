import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    
    private MailDeliveryManager manager;
    private GeographicalArea uptown;
    private GeographicalArea suburb;
    private GeographicalArea midtown;
    private GeographicalArea harbor;
    
    @Before
    public void setUp() {
        manager = new MailDeliveryManager();
    }
    
    @Test
    public void testCase1_RetrieveDeliveriesForBusyArea() {
        // SetUp: Create GeographicalArea "Uptown"
        uptown = new GeographicalArea();
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
        
        // Create and assign: Letter1 for Grace (James)
        Letter letter1 = new Letter();
        letter1.setContent("Letter content for Grace");
        letter1.setAddressee(grace);
        letter1.assignMailman(james);
        uptown.addRegisteredMail(letter1);
        
        // Create and assign: Parcel1 for Henry (James)
        Parcel parcel1 = new Parcel();
        parcel1.setWeight(2.5);
        parcel1.setAddressee(henry);
        parcel1.assignMailman(james);
        uptown.addRegisteredMail(parcel1);
        
        // Action: Retrieve all deliveries for Uptown
        List<RegisteredMail> deliveries = manager.getRegisteredMailDeliveries(uptown);
        
        // Expected Output: List containing both mail items with James as carrier
        assertEquals(2, deliveries.size());
        assertTrue(deliveries.contains(letter1));
        assertTrue(deliveries.contains(parcel1));
        assertEquals(james, letter1.getMailman());
        assertEquals(james, parcel1.getMailman());
        assertEquals(grace, letter1.getAddressee());
        assertEquals(henry, parcel1.getAddressee());
    }
    
    @Test
    public void testCase2_RetrieveDeliveriesForEmptyArea() {
        // SetUp: Create empty GeographicalArea "Suburb"
        suburb = new GeographicalArea();
        suburb.setName("Suburb");
        
        // Action: Retrieve deliveries for Suburb
        List<RegisteredMail> deliveries = manager.getRegisteredMailDeliveries(suburb);
        
        // Expected Output: Empty list
        assertNotNull(deliveries);
        assertTrue(deliveries.isEmpty());
    }
    
    @Test
    public void testCase3_RetrieveDeliveriesForAreaWithUnassignedMail() {
        // SetUp: Create GeographicalArea "Midtown"
        midtown = new GeographicalArea();
        midtown.setName("Midtown");
        
        // Add Inhabitant "Oscar" to Midtown
        Inhabitant oscar = new Inhabitant();
        oscar.setName("Oscar");
        oscar.setGeographicalArea(midtown);
        midtown.addInhabitant(oscar);
        
        // Create RegisteredLetter "Letter4" for Oscar (unassigned)
        Letter letter4 = new Letter();
        letter4.setContent("Letter content for Oscar");
        letter4.setAddressee(oscar);
        midtown.addRegisteredMail(letter4);
        
        // Action: Retrieve deliveries for Midtown
        List<RegisteredMail> deliveries = manager.getRegisteredMailDeliveries(midtown);
        
        // Expected Output: Empty list (mail not assigned to any mailman)
        assertNotNull(deliveries);
        assertTrue(deliveries.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveDeliveriesForAreaWithMultipleMailmen() {
        // SetUp: Create GeographicalArea "Harbor"
        harbor = new GeographicalArea();
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
        
        // Create and assign: Letter5 for Tina (Lucy)
        Letter letter5 = new Letter();
        letter5.setContent("Letter content for Tina");
        letter5.setAddressee(tina);
        letter5.assignMailman(lucy);
        harbor.addRegisteredMail(letter5);
        
        // Create and assign: Parcel3 for Tina (Rick)
        Parcel parcel3 = new Parcel();
        parcel3.setWeight(3.7);
        parcel3.setAddressee(tina);
        parcel3.assignMailman(rick);
        harbor.addRegisteredMail(parcel3);
        
        // Action: Retrieve deliveries for Harbor
        List<RegisteredMail> deliveries = manager.getRegisteredMailDeliveries(harbor);
        
        // Expected Output: List containing both mail items with respective carriers
        assertEquals(2, deliveries.size());
        assertTrue(deliveries.contains(letter5));
        assertTrue(deliveries.contains(parcel3));
        assertEquals(lucy, letter5.getMailman());
        assertEquals(rick, parcel3.getMailman());
        assertEquals(tina, letter5.getAddressee());
        assertEquals(tina, parcel3.getAddressee());
    }
    
    @Test
    public void testCase5_RetrieveDeliveriesForNonExistentArea() {
        // Action: Retrieve deliveries for non-existent "MountainView"
        GeographicalArea mountainView = new GeographicalArea();
        mountainView.setName("MountainView");
        
        List<RegisteredMail> deliveries = manager.getRegisteredMailDeliveries(mountainView);
        
        // Expected Output: Empty list
        assertNotNull(deliveries);
        assertTrue(deliveries.isEmpty());
    }
}