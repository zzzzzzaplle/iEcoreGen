import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    private MailDeliverySystem system;
    
    @Before
    public void setUp() {
        system = new MailDeliverySystem();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside"
        Area riverside = new Area();
        riverside.setName("Riverside");
        system.getAreas().add(riverside);
        
        // Action: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        boolean result = system.addInhabitant(linda, riverside);
        
        // Expected Output: true (successful addition)
        assertTrue("Adding Linda to Riverside should return true", result);
        assertTrue("Riverside should contain Linda", riverside.getInhabitants().contains(linda));
        assertEquals("Linda's area should be Riverside", riverside, linda.getArea());
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp: Create GeographicalArea "Lakeside"
        Area lakeside = new Area();
        lakeside.setName("Lakeside");
        system.getAreas().add(lakeside);
        
        // Add Mailman "Ken" to Lakeside
        Mailman ken = new Mailman();
        ken.setName("Ken");
        system.addMailman(ken, lakeside);
        
        // Add Inhabitant "Paul" to Lakeside
        Inhabitant paul = new Inhabitant();
        paul.setName("Paul");
        system.addInhabitant(paul, lakeside);
        
        // Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter();
        system.assignMailmanToDeliverMail(letter6, ken, paul);
        
        // Verify setup: Paul should have one mail item
        List<RegisteredMail> paulsMail = system.getMailForInhabitant(paul);
        assertNotNull("Paul should have mail items", paulsMail);
        assertEquals("Paul should have exactly one mail item", 1, paulsMail.size());
        assertTrue("Lakeside deliveries should contain letter6", lakeside.getDeliveries().contains(letter6));
        
        // Action: Remove Paul from Lakeside
        boolean result = system.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removing Paul should return true", result);
        assertFalse("Lakeside should not contain Paul", lakeside.getInhabitants().contains(paul));
        assertFalse("Lakeside deliveries should not contain letter6", lakeside.getDeliveries().contains(letter6));
        assertNull("Paul's area should be null", paul.getArea());
        
        // Verify mail deletion
        List<RegisteredMail> mailAfterRemoval = system.getMailForInhabitant(paul);
        assertNull("Paul should have no mail items after removal", mailAfterRemoval);
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        Area downtown = new Area();
        downtown.setName("Downtown");
        system.getAreas().add(downtown);
        
        // Action & Expected Output: Add new Inhabitant "Linda" to Downtown, true
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        boolean result1 = system.addInhabitant(linda, downtown);
        assertTrue("Adding Linda to Downtown should return true", result1);
        assertTrue("Downtown should contain Linda", downtown.getInhabitants().contains(linda));
        
        // Action & Expected Output: Add new Inhabitant "Becy" to Downtown, true
        Inhabitant becy = new Inhabitant();
        becy.setName("Becy");
        boolean result2 = system.addInhabitant(becy, downtown);
        assertTrue("Adding Becy to Downtown should return true", result2);
        assertTrue("Downtown should contain Becy", downtown.getInhabitants().contains(becy));
        
        // Action & Expected Output: Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = system.removeInhabitant(linda);
        assertTrue("Removing Linda from Downtown should return true", result3);
        assertFalse("Downtown should not contain Linda", downtown.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain Becy", downtown.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        Area hillside = new Area();
        hillside.setName("Hillside");
        system.getAreas().add(hillside);
        
        // Action: Remove non-existent "Victor" from Hillside
        Inhabitant victor = new Inhabitant();
        victor.setName("Victor");
        // Note: Victor is not added to Hillside
        
        // Expected Output: false (inhabitant doesn't exist)
        boolean result = system.removeInhabitant(victor);
        assertFalse("Removing non-existent Victor should return false", result);
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp: Create GeographicalArea "Beachfront"
        Area beachfront = new Area();
        beachfront.setName("Beachfront");
        system.getAreas().add(beachfront);
        
        // Add Mailman "Amy" to Beachfront
        Mailman amy = new Mailman();
        amy.setName("Amy");
        system.addMailman(amy, beachfront);
        
        // Add Inhabitant "Rachel" to Beachfront
        Inhabitant rachel = new Inhabitant();
        rachel.setName("Rachel");
        system.addInhabitant(rachel, beachfront);
        
        // Create and assign: Letter7 for Rachel (Amy)
        Letter letter7 = new Letter();
        system.assignMailmanToDeliverMail(letter7, amy, rachel);
        
        // Create and assign: Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel();
        system.assignMailmanToDeliverMail(parcel4, amy, rachel);
        
        // Verify setup: Rachel should have two mail items
        List<RegisteredMail> rachelsMail = system.getMailForInhabitant(rachel);
        assertNotNull("Rachel should have mail items", rachelsMail);
        assertEquals("Rachel should have exactly two mail items", 2, rachelsMail.size());
        assertTrue("Beachfront deliveries should contain letter7", beachfront.getDeliveries().contains(letter7));
        assertTrue("Beachfront deliveries should contain parcel4", beachfront.getDeliveries().contains(parcel4));
        
        // Action: Remove Rachel from Beachfront
        boolean result = system.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removing Rachel should return true", result);
        assertFalse("Beachfront should not contain Rachel", beachfront.getInhabitants().contains(rachel));
        assertFalse("Beachfront deliveries should not contain letter7", beachfront.getDeliveries().contains(letter7));
        assertFalse("Beachfront deliveries should not contain parcel4", beachfront.getDeliveries().contains(parcel4));
        assertNull("Rachel's area should be null", rachel.getArea());
        
        // Verify mail deletion
        List<RegisteredMail> mailAfterRemoval = system.getMailForInhabitant(rachel);
        assertNull("Rachel should have no mail items after removal", mailAfterRemoval);
    }
}