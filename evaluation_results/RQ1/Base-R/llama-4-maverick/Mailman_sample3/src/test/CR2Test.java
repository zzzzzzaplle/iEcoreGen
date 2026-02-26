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
        
        // Initialize geographical areas
        uptown = new GeographicalArea();
        uptown.setName("Uptown");
        
        suburb = new GeographicalArea();
        suburb.setName("Suburb");
        
        midtown = new GeographicalArea();
        midtown.setName("Midtown");
        
        harbor = new GeographicalArea();
        harbor.setName("Harbor");
        
        // Add areas to manager
        manager.addGeographicalArea(uptown);
        manager.addGeographicalArea(suburb);
        manager.addGeographicalArea(midtown);
        manager.addGeographicalArea(harbor);
    }
    
    @Test
    public void testCase1_RetrieveDeliveriesForBusyArea() {
        // SetUp:
        // 1. Create GeographicalArea "Uptown" - already done in setUp()
        // 2. Add Mailman "James" to Uptown
        Mailman james = new Mailman();
        james.setName("James");
        uptown.addMailman(james);
        james.setGeographicalArea(uptown);
        
        // 3. Add Inhabitants "Grace" and "Henry" to Uptown
        Inhabitant grace = new Inhabitant();
        grace.setName("Grace");
        uptown.addInhabitant(grace);
        grace.setGeographicalArea(uptown);
        
        Inhabitant henry = new Inhabitant();
        henry.setName("Henry");
        uptown.addInhabitant(henry);
        henry.setGeographicalArea(uptown);
        
        // 4. Create and assign:
        //    - Letter1 for Grace (James)
        Letter letter1 = new Letter();
        letter1.setAddressee(grace);
        letter1.setContent("Letter content 1");
        uptown.addRegisteredMail(letter1);
        letter1.assignMailman(james);
        
        //    - Parcel1 for Henry (James)
        Parcel parcel1 = new Parcel();
        parcel1.setAddressee(henry);
        parcel1.setWeight(2.5);
        uptown.addRegisteredMail(parcel1);
        parcel1.assignMailman(james);
        
        // Action: Retrieve all deliveries for Uptown
        List<RegisteredMail> result = manager.getRegisteredMailDeliveries(uptown);
        
        // Expected Output: List containing both mail items with James as carrier
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter1", result.contains(letter1));
        assertTrue("Should contain parcel1", result.contains(parcel1));
        
        // Verify James is the carrier for both items
        for (RegisteredMail mail : result) {
            assertEquals("Mailman should be James", james, mail.getMailman());
        }
    }
    
    @Test
    public void testCase2_RetrieveDeliveriesForEmptyArea() {
        // SetUp:
        // 1. Create empty GeographicalArea "Suburb" - already done in setUp()
        
        // Action: Retrieve deliveries for Suburb
        List<RegisteredMail> result = manager.getRegisteredMailDeliveries(suburb);
        
        // Expected Output: Empty list
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase3_RetrieveDeliveriesForAreaWithUnassignedMail() {
        // SetUp:
        // 1. Create GeographicalArea "Midtown" - already done in setUp()
        // 2. Add Inhabitant "Oscar" to Midtown
        Inhabitant oscar = new Inhabitant();
        oscar.setName("Oscar");
        midtown.addInhabitant(oscar);
        oscar.setGeographicalArea(midtown);
        
        // 3. Create RegisteredLetter "Letter4" for Oscar (unassigned)
        Letter letter4 = new Letter();
        letter4.setAddressee(oscar);
        letter4.setContent("Letter content 4");
        midtown.addRegisteredMail(letter4);
        // Note: No mailman assigned
        
        // Action: Retrieve deliveries for Midtown
        List<RegisteredMail> result = manager.getRegisteredMailDeliveries(midtown);
        
        // Expected Output: Empty list (mail not assigned to any mailman)
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveDeliveriesForAreaWithMultipleMailmen() {
        // SetUp:
        // 1. Create GeographicalArea "Harbor" - already done in setUp()
        // 2. Add Mailmen "Lucy" and "Rick" to Harbor
        Mailman lucy = new Mailman();
        lucy.setName("Lucy");
        harbor.addMailman(lucy);
        lucy.setGeographicalArea(harbor);
        
        Mailman rick = new Mailman();
        rick.setName("Rick");
        harbor.addMailman(rick);
        rick.setGeographicalArea(harbor);
        
        // 3. Add Inhabitant "Tina" to Harbor
        Inhabitant tina = new Inhabitant();
        tina.setName("Tina");
        harbor.addInhabitant(tina);
        tina.setGeographicalArea(harbor);
        
        // 4. Create and assign:
        //    - Letter5 for Tina (Lucy)
        Letter letter5 = new Letter();
        letter5.setAddressee(tina);
        letter5.setContent("Letter content 5");
        harbor.addRegisteredMail(letter5);
        letter5.assignMailman(lucy);
        
        //    - Parcel3 for Tina (Rick)
        Parcel parcel3 = new Parcel();
        parcel3.setAddressee(tina);
        parcel3.setWeight(3.7);
        harbor.addRegisteredMail(parcel3);
        parcel3.assignMailman(rick);
        
        // Action: Retrieve deliveries for Harbor
        List<RegisteredMail> result = manager.getRegisteredMailDeliveries(harbor);
        
        // Expected Output: List containing both mail items with respective carriers
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter5", result.contains(letter5));
        assertTrue("Should contain parcel3", result.contains(parcel3));
        
        // Verify respective carriers
        boolean foundLucyCarrier = false;
        boolean foundRickCarrier = false;
        
        for (RegisteredMail mail : result) {
            if (mail.equals(letter5) && mail.getMailman().equals(lucy)) {
                foundLucyCarrier = true;
            }
            if (mail.equals(parcel3) && mail.getMailman().equals(rick)) {
                foundRickCarrier = true;
            }
        }
        
        assertTrue("Letter5 should be carried by Lucy", foundLucyCarrier);
        assertTrue("Parcel3 should be carried by Rick", foundRickCarrier);
    }
    
    @Test
    public void testCase5_RetrieveDeliveriesForNonExistentArea() {
        // Action: Retrieve deliveries for non-existent "MountainView"
        GeographicalArea mountainView = new GeographicalArea();
        mountainView.setName("MountainView");
        // Note: MountainView is not added to manager
        
        List<RegisteredMail> result = manager.getRegisteredMailDeliveries(mountainView);
        
        // Expected Output: Empty list
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
}