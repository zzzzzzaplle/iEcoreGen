import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    
    private MailDeliverySystem mailDeliverySystem;
    
    @Before
    public void setUp() {
        mailDeliverySystem = new MailDeliverySystem();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside"
        GeographicalArea riverside = new GeographicalArea();
        riverside.setName("Riverside");
        mailDeliverySystem.addGeographicalArea(riverside);
        
        // Action: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        boolean result = mailDeliverySystem.addInhabitant(riverside, linda);
        
        // Expected Output: true (successful addition)
        assertTrue(result);
        assertTrue(riverside.getInhabitants().contains(linda));
        assertEquals(riverside, linda.getGeographicalArea());
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp: Create GeographicalArea "Lakeside"
        GeographicalArea lakeside = new GeographicalArea();
        lakeside.setName("Lakeside");
        mailDeliverySystem.addGeographicalArea(lakeside);
        
        // Add Mailman "Ken" to Lakeside
        Mailman ken = new Mailman();
        ken.setName("Ken");
        mailDeliverySystem.addMailman(lakeside, ken);
        
        // Add Inhabitant "Paul" to Lakeside
        Inhabitant paul = new Inhabitant();
        paul.setName("Paul");
        mailDeliverySystem.addInhabitant(lakeside, paul);
        
        // Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter();
        letter6.setAddressee(paul);
        letter6.setMailman(ken);
        lakeside.addRegisteredMail(letter6);
        
        // Action: Remove Paul from Lakeside
        boolean result = mailDeliverySystem.removeInhabitant(lakeside, paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue(result);
        assertFalse(lakeside.getInhabitants().contains(paul));
        assertFalse(lakeside.getRegisteredMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        GeographicalArea downtown = new GeographicalArea();
        downtown.setName("Downtown");
        mailDeliverySystem.addGeographicalArea(downtown);
        
        // Action & Expected Output: Add new Inhabitant "Linda" to Downtown, true
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        boolean result1 = mailDeliverySystem.addInhabitant(downtown, linda);
        assertTrue(result1);
        assertTrue(downtown.getInhabitants().contains(linda));
        
        // Action & Expected Output: Add new Inhabitant "Becy" to Downtown, true
        Inhabitant becy = new Inhabitant();
        becy.setName("Becy");
        boolean result2 = mailDeliverySystem.addInhabitant(downtown, becy);
        assertTrue(result2);
        assertTrue(downtown.getInhabitants().contains(becy));
        
        // Action & Expected Output: Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = mailDeliverySystem.removeInhabitant(downtown, linda);
        assertTrue(result3);
        assertFalse(downtown.getInhabitants().contains(linda));
        assertTrue(downtown.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        GeographicalArea hillside = new GeographicalArea();
        hillside.setName("Hillside");
        mailDeliverySystem.addGeographicalArea(hillside);
        
        // Action: Remove non-existent "Victor" from Hillside
        Inhabitant victor = new Inhabitant();
        victor.setName("Victor");
        
        // Remove should return false when removing non-existent inhabitant
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse(result);
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp: Create GeographicalArea "Beachfront"
        GeographicalArea beachfront = new GeographicalArea();
        beachfront.setName("Beachfront");
        mailDeliverySystem.addGeographicalArea(beachfront);
        
        // Add Mailman "Amy" to Beachfront
        Mailman amy = new Mailman();
        amy.setName("Amy");
        mailDeliverySystem.addMailman(beachfront, amy);
        
        // Add Inhabitant "Rachel" to Beachfront
        Inhabitant rachel = new Inhabitant();
        rachel.setName("Rachel");
        mailDeliverySystem.addInhabitant(beachfront, rachel);
        
        // Create and assign Letter7 for Rachel (Amy)
        Letter letter7 = new Letter();
        letter7.setAddressee(rachel);
        letter7.setMailman(amy);
        beachfront.addRegisteredMail(letter7);
        
        // Create and assign Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel();
        parcel4.setAddressee(rachel);
        parcel4.setMailman(amy);
        beachfront.addRegisteredMail(parcel4);
        
        // Verify initial state
        assertEquals(2, beachfront.getRegisteredMails().size());
        assertTrue(beachfront.getRegisteredMails().contains(letter7));
        assertTrue(beachfront.getRegisteredMails().contains(parcel4));
        
        // Action: Remove Rachel from Beachfront
        boolean result = mailDeliverySystem.removeInhabitant(beachfront, rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue(result);
        assertFalse(beachfront.getInhabitants().contains(rachel));
        assertFalse(beachfront.getRegisteredMails().contains(letter7));
        assertFalse(beachfront.getRegisteredMails().contains(parcel4));
        assertEquals(0, beachfront.getRegisteredMails().size());
    }
}